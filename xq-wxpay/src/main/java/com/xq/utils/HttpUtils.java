package com.xq.utils;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpsConfigurator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.net.www.http.HttpClient;

import java.util.HashMap;
import java.util.Map;

/**
 * http 工具类
 *
 * @author xq
 * @Date 2019-11-7 22:39:20
 */
public class HttpUtils {

    public static final Gson gson = new Gson();


    /**
     * get 请求
     *
     * @param url 链接地址
     * @param timeout 设置超时时间
     * @return
     */
    public static Map<String, Object> doGet(String url,int timeout) {
        Map<String, Object> map = new HashMap<>();
        CloseableHttpClient httpClients = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        //设置http 超时链接配置 避免占用资源
        RequestConfig requestConfig =  setRequestConfig(timeout);
        httpGet.setConfig(requestConfig);
        try {

            HttpResponse httpResponse = httpClients.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String jsonData = EntityUtils.toString(httpResponse.getEntity());
                map = gson.fromJson(jsonData, map.getClass());
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                //关闭http链接
                httpClients.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }

        return map;
    }

    /**
     * post方法封装
     *
     * @param url  链接地址
     * @param data 数据
     * @param timeout 设置超时时间
     * @return
     */
    public static String doPost(String url, String data,int timeout) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig =  setRequestConfig(timeout);
        httpPost.setConfig(requestConfig);
        httpPost.addHeader("Content-Type", "text/html;charset=UTF-8");
        if (data != null && data instanceof String) {
            StringEntity stringEntity = new StringEntity(data, "UTF-8");
            httpPost.setEntity(stringEntity);
        }
        try {
            CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String result = EntityUtils.toString(httpResponse.getEntity());
                return result;
            }
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (Exception e) {
                e.getStackTrace();
            }
        }
        return null;

    }


    /**
     * 设置超时时间
     * @param timeout  超时时间
     * @return
     */
    public static RequestConfig setRequestConfig(int timeout) {
        RequestConfig requestConfig = null;
        if (timeout > 0) {
            requestConfig = RequestConfig.custom().setConnectTimeout(timeout)
                    .setConnectionRequestTimeout(timeout)
                    .setRedirectsEnabled(true)
                    .setSocketTimeout(timeout)
                    .build();
        }

        return requestConfig;
    }
}

