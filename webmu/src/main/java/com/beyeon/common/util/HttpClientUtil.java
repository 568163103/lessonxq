package com.beyeon.common.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLHandshakeException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import com.beyeon.common.thread.ThreadPoolManager;

public class HttpClientUtil {
	private static Logger rootlog = LoggerFactory.getLogger(HttpClientWrapper.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String SSL_DEFAULT_SCHEME = "https";
	private static final int SSL_DEFAULT_PORT = 443;
	private static final String PARAMETER_SEPARATOR = "&";
	private static final String NAME_VALUE_SEPARATOR = "=";
	private static final int CONNECT_TIMEOUT = 3 * 1000;
	private static final int RECEIVE_TIMEOUT = 20 * 1000;

	private static HttpClientWrapper httpClientWrapper = getPoolHttpClientWrapper();

	public static class HttpClientWrapper {
		private Logger logger =LoggerFactory.getLogger(HttpClientWrapper.class);

		private boolean shutDown = true;
		private int connectionTimeout = CONNECT_TIMEOUT;
		private int receiveTimeout = RECEIVE_TIMEOUT;
		private ClientConnectionManager conman = null;

		// 异常自动恢复处理, 使用HttpRequestRetryHandler接口实现请求的异常恢复
		private HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
			// 自定义的恢复策略
			public boolean retryRequest(IOException exception, int executionCount,
										HttpContext context) {
				// 设置恢复策略，在发生异常时候将自动重试3次
				if (executionCount >= 3) {
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					return false;
				}
				HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
				boolean idempotent = (request instanceof HttpEntityEnclosingRequest);
				return !idempotent;
			}
		};

		// 使用ResponseHandler接口处理响应，HttpClient使用ResponseHandler会自动管理连接的释放
		private ResponseHandler<String> stringResponseHandler = new ResponseHandler<String>() {
			// 自定义响应处理
			public String handleResponse(HttpResponse response)
					throws IOException {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String charset = EntityUtils.getContentCharSet(entity) == null ? DEFAULT_CHARSET
							: EntityUtils.getContentCharSet(entity);
					return EntityUtils.toString(entity, charset);
				} else {
					return null;
				}
			}
		};

		private ResponseHandler<byte[]> bytesResponseHandler = new ResponseHandler<byte[]>() {
			// 自定义响应处理
			public byte[] handleResponse(HttpResponse response)
					throws IOException {
				HttpEntity entity = response.getEntity();
				if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode() && null != entity) {
					return EntityUtils.toByteArray(entity);
				} else {
					return null;
				}
			}
		};

		public HttpClientWrapper() {
			this.conman = new SingleClientConnManager();
		}

		public HttpClientWrapper(ClientConnectionManager conman) {
			this.shutDown = false;
			this.conman = conman;
		}

		/**
		 * 获取DefaultHttpClient实例
		 *
		 * @param charset 参数编码集, 可空
		 * @return DefaultHttpClient 对象
		 */
		private DefaultHttpClient getDefaultHttpClient(final String charset) {
			DefaultHttpClient defaultHttpClient = new DefaultHttpClient(conman);
			defaultHttpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			// 模拟浏览器，解决一些服务器程序只允许浏览器访问的问题
			defaultHttpClient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1)");
			defaultHttpClient.getParams().setParameter(CoreProtocolPNames.USE_EXPECT_CONTINUE, Boolean.FALSE);
			defaultHttpClient.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, charset == null ? DEFAULT_CHARSET : charset);
			defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
			defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, receiveTimeout);
			defaultHttpClient.setHttpRequestRetryHandler(requestRetryHandler);
			return defaultHttpClient;
		}

		/**
		 * 释放HttpClient连接
		 *
		 * @param hrb        请求对象
		 * @param httpclient client对象
		 */
		private void shutdown(HttpRequestBase hrb, HttpClient httpclient) {
			if (hrb != null) {
				hrb.releaseConnection();
			}
			if (httpclient != null && shutDown) {
				httpclient.getConnectionManager().shutdown();
			}
		}

		public void shutdown() {
			conman.shutdown();
		}

		private String decode(String content, String encoding) {
			try {
				return URLDecoder.decode(content,
						encoding != null ? encoding : DEFAULT_CHARSET);
			} catch (UnsupportedEncodingException problem) {
				throw new RuntimeException("encode 字符串出错！");
			}
		}

		private String encode(String content, String encoding) {
			try {
				return URLEncoder.encode(content,
						encoding != null ? encoding : DEFAULT_CHARSET);
			} catch (UnsupportedEncodingException problem) {
				throw new RuntimeException("encode 字符串出错！");
			}
		}

		/**
		 * 将传入的键/值对参数转换为NameValuePair参数集
		 *
		 * @param paramsMap 参数集, 键/值对
		 * @return NameValuePair参数集
		 */
		private List<NameValuePair> getNameValuePairs(Map paramsMap) {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			if (paramsMap == null || paramsMap.size() == 0) {
				return params;
			}
			for (Object entry : paramsMap.entrySet()) {
				Map.Entry<Object, Object> map = (Map.Entry<Object, Object>) entry;
				if (map.getValue() instanceof Collection) {
					Collection values = (Collection) map.getValue();
					for (Object value : values) {
						params.add(new BasicNameValuePair((String) map.getKey(), (String) value));
					}
				} else {
					params.add(new BasicNameValuePair((String) map.getKey(), (String) map.getValue()));
				}
			}
			return params;
		}

		private HttpEntity buildEntity(Map paramsMap, String charset) {
			ContentType contentType = ContentType.MULTIPART_FORM_DATA.withCharset(charset);
			MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create().setContentType(contentType);
			if (paramsMap == null || paramsMap.size() == 0) {
				return multipartEntityBuilder.build();
			}
			for (Object entry : paramsMap.entrySet()) {
				Map.Entry<Object, Object> map = (Map.Entry<Object, Object>) entry;
				List value = null;
				if (map.getValue() instanceof Collection) {
					value = (List) map.getValue();
				} else {
					value = new ArrayList();
					value.add(map.getValue());
				}
				for (Object o : value) {
					if (o instanceof File) {
						File file = (File)o;
						multipartEntityBuilder.addBinaryBody((String)map.getKey(), file, contentType, file != null ? file.getName() : null);
					} else {
						multipartEntityBuilder.addTextBody((String)map.getKey(),(String)o,contentType);
					}
				}
			}
			return multipartEntityBuilder.build();
		}

		private List<BasicHeader> getBasicHeaders(Map<String, String> headsMap) {
			List<BasicHeader> params = new ArrayList<BasicHeader>();
			if (headsMap == null || headsMap.size() == 0) {
				return params;
			}
			for (Map.Entry<String, String> map : headsMap.entrySet()) {
				params.add(new BasicHeader(map.getKey(), map.getValue()));
			}
			return params;
		}

		public String getCookies(Map<String, String> cookies) {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String> cookie : cookies.entrySet()) {
				sb.append(cookie.getKey() + "=" + cookie.getValue() + ";");
			}
			return sb.toString();
		}

		public String mapToString(Map<String, List<String>> paramsMap, String encoding) {
			StringBuilder result = new StringBuilder();
			for (Map.Entry<String, List<String>> parameter : paramsMap.entrySet()) {
				String encodedName = encode(parameter.getKey(), encoding);
				List<String> values = parameter.getValue();
				for (String value : values) {
					String encodedValue = value != null ? encode(value, encoding) : "";
					if (result.length() > 0)
						result.append(PARAMETER_SEPARATOR);
					result.append(encodedName);
					result.append(NAME_VALUE_SEPARATOR);
					result.append(encodedValue);
				}
			}
			return result.toString();
		}

		public String mapToString(Map<String, List<String>> paramsMap) {
			return mapToString(paramsMap, DEFAULT_CHARSET);
		}

		public Map<String, List<String>> stringToMap(String params, String encoding) {
			Map<String, List<String>> result = new HashMap<String, List<String>>();
			String[] nameValues = params.split(PARAMETER_SEPARATOR);
			for (String param : nameValues) {
				String[] nameValue = param.split(NAME_VALUE_SEPARATOR);
				String name = decode(nameValue[0], encoding);
				String value = null;
				if (nameValue.length > 1 && null != nameValue[1])
					value = decode(nameValue[1], encoding);
				List<String> values = result.get(name);
				if (null == values) {
					values = new ArrayList<String>();
					result.put(name, values);
				}
				values.add(value);
			}
			return result;
		}

		public Map<String, List<String>> stringToMap(String params) {
			return stringToMap(params, DEFAULT_CHARSET);
		}

		/**
		 * Get方式提交
		 * @param url 提交地址
		 * @return 响应消息
		 */
		public String get(String url) {
			return get(url, null);
		}

		/**
		 * Get方式提交
		 * @param url    提交地址
		 * @param params 查询参数集, 键/值对
		 * @return 响应消息
		 */
		public String get(String url, Map params) {
			return get(url, params, DEFAULT_CHARSET);
		}

		public String get(String url, Map params, String charset) {
			return get(url, params, null, charset);
		}

		/**
		 * @param url    提交地址
		 * @param params 查询参数集, 键/值对
		 * @param heads  头消息
		 * @return 响应消息
		 */
		public String get(String url, Map params, Map<String, String> heads) {
			return get(url, params, heads, DEFAULT_CHARSET);
		}

		public String get(String url, Map params, Map<String, String> heads,
						  String charset) {
			return get(url, params, heads, charset, stringResponseHandler);
		}

		public byte[] getBytes(String url, Map params, Map<String, String> heads,
							   String charset) {
			return get(url, params, heads, charset, bytesResponseHandler);
		}

		/**
		 * Get方式提交
		 * @param url     提交地址
		 * @param params  查询参数集, 键/值对
		 * @param charset 参数提交编码集
		 * @return 响应消息
		 */
		public <T> T get(String url, Map params, Map<String, String> heads,
						 String charset, ResponseHandler<? extends T> responseHandler) {
			if (StringUtils.isBlank(url)) {
				return null;
			}
			List<NameValuePair> qparams = getNameValuePairs(params);
			if (qparams != null && qparams.size() > 0) {
				charset = (charset == null ? DEFAULT_CHARSET : charset);
				String formatParams = URLEncodedUtils.format(qparams, charset);
				url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams) : (url + "&" + formatParams);
			}
			DefaultHttpClient httpclient = getDefaultHttpClient(charset);
			HttpGet hg = new HttpGet(url);
			// 发送请求，得到响应
			T response = null;
			try {
				if (null != heads) {
					hg.setHeaders(getBasicHeaders(heads).toArray(new BasicHeader[0]));
				}
				response = httpclient.execute(hg, responseHandler);
			} catch (ClientProtocolException e) {
				throw new RuntimeException("客户端连接协议错误", e);
			} catch (IOException e) {
				throw new RuntimeException("IO操作异常", e);
			} finally {
				shutdown(hg, httpclient);
			}
			return response;
		}

		/**
		 * Post方式提交
		 *
		 * @param url    提交地址
		 * @param params 提交参数集, 键/值对
		 * @return 响应消息
		 */
		public String post(String url, Map params) {
			return post(url, params, DEFAULT_CHARSET);
		}

		public String post(String url, String params) {
			return post(url, params, DEFAULT_CHARSET);
		}

		public String post(String url, Map params, String charset) {
			return post(url, params, charset, stringResponseHandler);
		}

		public String post(String url, String params, String charset) {
			return post(url, params, charset, stringResponseHandler);
		}

		public byte[] postBytes(String url, Map params, String charset) {
			return post(url, params, charset, bytesResponseHandler);
		}

		/**
		 * Post方式提交
		 *
		 * @param url     提交地址
		 * @param params  提交参数集, 键/值对
		 * @param charset 参数提交编码集
		 * @return 响应消息
		 */
		public <T> T post(String url, Map params, String charset, ResponseHandler<? extends T> responseHandler) {
			if (StringUtils.isBlank(url)) {
				return null;
			}
			// 创建HttpClient实例
			DefaultHttpClient httpclient = getDefaultHttpClient(charset);
			HttpEntity httpEntity = buildEntity(params,charset);
			HttpPost hp = new HttpPost(url);
			hp.setEntity(httpEntity);
			// 发送请求，得到响应
			T response = null;
			try {
				response = httpclient.execute(hp, responseHandler);
			} catch (ClientProtocolException e) {
				throw new RuntimeException("客户端连接协议错误", e);
			} catch (IOException e) {
				throw new RuntimeException("IO操作异常", e);
			} finally {
				shutdown(hp, httpclient);
			}
			return response;
		}

		public <T> T post(String url, String params, String charset, ResponseHandler<? extends T> responseHandler) {
			if (StringUtils.isBlank(url)) {
				return null;
			}
			// 创建HttpClient实例
			DefaultHttpClient httpclient = getDefaultHttpClient(charset);
			ByteArrayEntity entity = null;
			try {
				entity = new ByteArrayEntity(params.getBytes(charset));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("不支持的编码集", e);
			}
			HttpPost hp = new HttpPost(url);
			hp.setEntity(entity);
			// 发送请求，得到响应
			T response = null;
			try {
				response = httpclient.execute(hp, responseHandler);
			} catch (ClientProtocolException e) {
				throw new RuntimeException("客户端连接协议错误", e);
			} catch (IOException e) {
				logger.debug(url);
				throw new RuntimeException("IO操作异常", e);
			} finally {
				shutdown(hp, httpclient);
			}
			return response;
		}

	}

	public static HttpClientWrapper getHttpClientWrapper() {
		return new HttpClientWrapper();
	}

	public static HttpClientWrapper getPoolHttpClientWrapper() {
		try {
			ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();
			threadSafeClientConnManager.setMaxTotal(1000);
			threadSafeClientConnManager.setDefaultMaxPerRoute(100);
			return new HttpClientWrapper(threadSafeClientConnManager);
		} catch (Exception e) {
			rootlog.error("加载密钥库失败！", e);
		}
		return null;
	}

	public static String getCookies(Map<String, String> cookies) {
		return httpClientWrapper.getCookies(cookies);
	}

	public static String mapToString(Map<String, List<String>> paramsMap, String encoding) {
		return httpClientWrapper.mapToString(paramsMap, encoding);
	}

	public static String mapToString(Map<String, List<String>> paramsMap) {
		return httpClientWrapper.mapToString(paramsMap);
	}

	public static Map<String, List<String>> stringToMap(String params, String encoding) {
		return httpClientWrapper.stringToMap(params, encoding);
	}

	public static Map<String, List<String>> stringToMap(String params) {
		return httpClientWrapper.stringToMap(params);
	}

	/**
	 * 从给定的路径中加载此 KeyStore
	 *
	 * @param url      keystore URL路径
	 * @param password keystore访问密钥
	 * @return keystore 对象
	 */
	private static KeyStore createKeyStore(final String url, final String password)
			throws KeyStoreException, NoSuchAlgorithmException,
			CertificateException, IOException {
		if (url == null) {
			throw new IllegalArgumentException("Keystore url may not be null");
		}
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		InputStream is = null;
		try {
			is = new ClassPathResource(url).getInputStream();
			keystore.load(is, password != null ? password.toCharArray() : null);
		} finally {
			if (is != null) {
				is.close();
				is = null;
			}
		}
		return keystore;
	}

	/**
	 * Get方式提交
	 *
	 * @param url 提交地址
	 * @return 响应消息
	 */
	public static String get(String url) {
		return httpClientWrapper.get(url);
	}

	/**
	 * Get方式提交
	 *
	 * @param url    提交地址
	 * @param params 查询参数集, 键/值对
	 * @return 响应消息
	 */
	public static String get(String url, Map params) {
		return httpClientWrapper.get(url, params);
	}

	public static String get(String url, Map params, String charset) {
		return httpClientWrapper.get(url, params, charset);
	}

	/**
	 * @param url    提交地址
	 * @param params 查询参数集, 键/值对
	 * @param heads  头消息
	 * @return 响应消息
	 */
	public static String get(String url, Map params, Map<String, String> heads) {
		return httpClientWrapper.get(url, params, heads);
	}

	public static String get(String url, Map params, Map<String, String> heads, String charset) {
		return httpClientWrapper.get(url, params, heads, charset);
	}

	public static byte[] getBytes(String url) {
		return httpClientWrapper.getBytes(url, null, null, DEFAULT_CHARSET);
	}

	/**
	 * Get方式提交
	 *
	 * @param url     提交地址
	 * @param params  查询参数集, 键/值对
	 * @param charset 参数提交编码集
	 * @return 响应消息
	 */
	public static <T> T get(String url, Map params, Map<String, String> heads, String charset,
							ResponseHandler<? extends T> responseHandler) {
		return httpClientWrapper.get(url, params, heads, charset, responseHandler);
	}

	/**
	 * Post方式提交
	 *
	 * @param url    提交地址
	 * @param params 提交参数集, 键/值对
	 * @return 响应消息
	 */
	public static String post(String url, Map params) {
		return httpClientWrapper.post(url, params);
	}

	public static String post(String url, String params) {
		return httpClientWrapper.post(url, params);
	}

	public static String post(String url, Map params, String charset) {
		return httpClientWrapper.post(url, params, charset);
	}

	public static String post(String url, String params, String charset) {
		return httpClientWrapper.post(url, params, charset);
	}

	public static byte[] postBytes(String url, Map params) {
		return httpClientWrapper.postBytes(url, params, DEFAULT_CHARSET);
	}

	/**
	 * Post方式提交
	 *
	 * @param url     提交地址
	 * @param params  提交参数集, 键/值对
	 * @param charset 参数提交编码集
	 * @return 响应消息
	 */
	public static <T> T post(String url, Map params, String charset, ResponseHandler<? extends T> responseHandler) {
		return httpClientWrapper.post(url, params, charset, responseHandler);
	}

	public static <T> T post(String url, String params, String charset, ResponseHandler<? extends T> responseHandler) {
		return httpClientWrapper.post(url, params, charset, responseHandler);
	}

	public static void main(String[] args) {
		int i = 0;
		try {
			final long l = System.currentTimeMillis();
			for (i = 0; i < 200; i++) {
				final int m = i;
				ThreadPoolManager.excuteDefault(new Runnable() {
					public void run() {
						for (int j = 0; j < 100; j++) {
							if (j % 99 == 9) {
								System.err.println(m + "=====" + j + "=====" + (System.currentTimeMillis() - l) / 1000);
							}
							HttpClientUtil.getBytes("http://www.baidu.com");
						}
					}
				});
			}
			byte[] datas = HttpClientUtil.getBytes("http://192.168.0.101:8080");
			FileUtils.writeByteArrayToFile(new File("E:\\tempdb\\mv.mp4"), datas);
			//httpClientWrapper.shutdown();
			System.err.println((System.currentTimeMillis() - l) / 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}