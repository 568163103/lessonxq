package com.beyeon.common.config;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.context.support.AbstractMessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.ObjectUtils;
import org.springframework.util.PropertiesPersister;
import org.springframework.util.StringUtils;

public class ResourceBundle extends AbstractMessageSource {

	public static final String PROPERTIES_SUFFIX = ".properties";
	public static final String XML_SUFFIX = ".xml";
	public static Locale root = new Locale("","","");
	private String[] basenames ;
	private String defaultEncoding;
	private Properties fileEncodings;
	private boolean fallbackToSystemLocale = false;

	private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();
	private ResourceLoader resourceLoader = new FileSystemResourceLoader();
	private final Map cachedFilenames = new HashMap();
	private final Map cachedProperties = new HashMap();
	private final Map cachedMergedProperties = new HashMap();

	public void setBasename(String basename) {
		setBasenames(new String[] {basename});
	}
	public void setBasenames(String[] basenames) {
		if (basenames != null) {
			this.basenames = new String[basenames.length];
			for (int i = 0; i < basenames.length; i++) {
				String basename = basenames[i];
				Assert.hasText(basename, "Basename must not be empty");
				this.basenames[i] = basename.trim();
			}
		} else {
			this.basenames = new String[0];
		}
	}

	public void setDefaultEncoding(String defaultEncoding) {
		this.defaultEncoding = defaultEncoding;
	}

	public void setFileEncodings(Properties fileEncodings) {
		this.fileEncodings = fileEncodings;
	}

	public void setFallbackToSystemLocale(boolean fallbackToSystemLocale) {
		this.fallbackToSystemLocale = fallbackToSystemLocale;
	}

	public void setPropertiesPersister(PropertiesPersister propertiesPersister) {
		this.propertiesPersister =
				(propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister());
	}

	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = (resourceLoader != null ? resourceLoader : new FileSystemResourceLoader());
	}
	
	public ResourceLoader getResourceLoader(){
		return this.resourceLoader;
	}

	public void init(Locale locale){
		for (int i = 0; i < this.basenames.length; i++) {
				refreshProperties(basenames[i],locale);
		}
	}
	
	protected String getProperty(String fileName ,String code, Object[] args, Locale locale) {
		if (code == null) {
			return null;
		}
		if (locale == null) {
			locale = root;
		}
		Object[] argsToUse = args;

		if (!isAlwaysUseMessageFormat() && ObjectUtils.isEmpty(args)) {
			String message = resolveCodeWithoutArguments(fileName, code, locale);
			if (message != null) {
				return message;
			}
		} else {
			argsToUse = resolveArguments(args, locale);

			MessageFormat messageFormat = resolveCode(fileName, code, locale);
			if (messageFormat != null) {
				synchronized (messageFormat) {
					return messageFormat.format(argsToUse);
				}
			}
		}
		return null;
	}

	public List getMoreProperty(String fileName, String code, Object object, Locale locale) {
		PropertiesHolder propHolder = null;
		propHolder = getProperties(fileName,locale);
		ArrayList list = new ArrayList();
		Set set = propHolder.getProperties().keySet();
		for (Iterator iterator = set.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(key.indexOf(code) >= 0)
				list.add(propHolder.getProperty(key));
		}
		return list;
	}
	
	protected String resolveCodeWithoutArguments(String code, Locale locale) {
		for (int i = 0; i < this.basenames.length; i++) {
			PropertiesHolder propHolder = getProperties(this.basenames[i],locale);
			String result = propHolder.getProperty(code);
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	protected String resolveCodeWithoutArguments(String fileName ,String code, Locale locale) {
		PropertiesHolder propHolder = getProperties(fileName,locale);
		String result = propHolder.getProperty(code);
		return result;
	}

	protected MessageFormat resolveCode(String code, Locale locale) {
		for (int i = 0; i < this.basenames.length; i++) {
				PropertiesHolder propHolder = getProperties(basenames[i],locale);
				MessageFormat result = propHolder.getMessageFormat(code, locale);
				if (result != null) {
					return result;
				}
		}
		return null;
	}
	
	protected MessageFormat resolveCode(String fileName ,String code, Locale locale) {
		PropertiesHolder propHolder = getProperties(fileName,locale);
		MessageFormat result = propHolder.getMessageFormat(code, locale);
		return result;
	}

	protected List calculateAllFilenames(String basename, Locale locale) {
		synchronized (this.cachedFilenames) {
			Map localeMap = (Map) this.cachedFilenames.get(basename);
			if (localeMap != null) {
				List filenames = (List) localeMap.get(locale);
				if (filenames != null) {
					return filenames;
				}
			}
			List filenames = new ArrayList(7);
			filenames.addAll(calculateFilenamesForLocale(basename, locale));
			if (this.fallbackToSystemLocale && !locale.equals(Locale.getDefault())) {
				List fallbackFilenames = calculateFilenamesForLocale(basename, Locale.getDefault());
				for (Iterator it = fallbackFilenames.iterator(); it.hasNext();) {
					String fallbackFilename = (String) it.next();
					if (!filenames.contains(fallbackFilename)) {
						filenames.add(fallbackFilename);
					}
				}
			}
			filenames.add(basename);
			if (localeMap != null) {
				localeMap.put(locale, filenames);
			}
			else {
				localeMap = new HashMap();
				localeMap.put(locale, filenames);
				this.cachedFilenames.put(basename, localeMap);
			}
			return filenames;
		}
	}

	protected List calculateFilenamesForLocale(String basename, Locale locale) {
		List result = new ArrayList(3);
		String language = locale.getLanguage();
		String country = locale.getCountry();
		String variant = locale.getVariant();
		StringBuffer temp = new StringBuffer(basename);

		if (language.length() > 0) {
			temp.append('_').append(language);
			result.add(0, temp.toString());
		}

		if (country.length() > 0) {
			temp.append('_').append(country);
			result.add(0, temp.toString());
		}

		if (variant.length() > 0) {
			temp.append('_').append(variant);
			result.add(0, temp.toString());
		}

		return result;
	}

	public PropertiesHolder getProperties(String fileName,Locale locale) {
		PropertiesHolder propHolder = null;
		List filenames = calculateAllFilenames(fileName, locale);		
		for (int j = 0; j < filenames.size(); j++) {
			String filenameTemp = (String) filenames.get(j);
			propHolder = (PropertiesHolder) this.cachedProperties.get(filenameTemp);
			if (null != propHolder && null != propHolder.properties) {
				break;
			}
		}
		return propHolder;
	}

	public boolean isModified(String filename,Locale local) {
		boolean flag = false;
		PropertiesHolder propHolder = getProperties(filename,local);
		Resource resource = this.resourceLoader.getResource(filename + PROPERTIES_SUFFIX);
		if (!resource.exists()) {
			resource = this.resourceLoader.getResource(filename + XML_SUFFIX);
		}
		if (resource.exists()) {
			try {
				long fileTimestamp = -1;
				File file = null;
				try {
					file = resource.getFile();
				}
				catch (IOException ex) {
					file = null;
				}
				if (file != null) {
					fileTimestamp = file.lastModified();
					if (fileTimestamp == 0) {
						throw new IOException("File [" + file.getAbsolutePath() + "] does not exist");
					}
					if (propHolder != null && propHolder.getFileTimestamp() == fileTimestamp) {
						return flag;
					}
				}
				Properties props = loadProperties(resource, filename);
				propHolder = new PropertiesHolder(props, fileTimestamp);
				flag = true;
			} catch (IOException ex) {
				propHolder = new PropertiesHolder();
			}
		} else {
			propHolder = new PropertiesHolder();
		}
		this.cachedProperties.put(filename, propHolder);
		return flag;
	}
	
	public PropertiesHolder refreshProperties(String filename,Locale local) {
		PropertiesHolder propHolder = getProperties(filename,local);
		Resource resource = this.resourceLoader.getResource(filename + PROPERTIES_SUFFIX);
		if (!resource.exists()) {
			resource = this.resourceLoader.getResource(filename + XML_SUFFIX);
		}
		if (resource.exists()) {
			try {
				long fileTimestamp = -1;
				File file = null;
				try {
					file = resource.getFile();
				}
				catch (IOException ex) {
					file = null;
				}
				if (file != null) {
					fileTimestamp = file.lastModified();
					if (fileTimestamp == 0) {
						throw new IOException("File [" + file.getAbsolutePath() + "] does not exist");
					}
					if (propHolder != null && propHolder.getFileTimestamp() == fileTimestamp) {
						return propHolder;
					}
				}
				Properties props = loadProperties(resource, filename);
				propHolder = new PropertiesHolder(props, fileTimestamp);
			}

			catch (IOException ex) {
				propHolder = new PropertiesHolder();
			}
		} else {
			propHolder = new PropertiesHolder();
		}
		this.cachedProperties.put(filename, propHolder);
		return propHolder;
	}

	protected Properties loadProperties(Resource resource, String filename) throws IOException {
		InputStream is = resource.getInputStream();
		Properties props = new Properties();
		try {
			if (resource.getFilename().endsWith(XML_SUFFIX)) {
				this.propertiesPersister.loadFromXml(props, is);
			}
			else {
				String encoding = null;
				if (this.fileEncodings != null) {
					encoding = this.fileEncodings.getProperty(filename);
				}
				if (encoding == null) {
					encoding = this.defaultEncoding;
				}
				if (encoding != null) {
					this.propertiesPersister.load(props, new InputStreamReader(is, encoding));
				}
				else {
					this.propertiesPersister.load(props, is);
				}
			}
			return props;
		}
		finally {
			is.close();
		}
	}

	public void clearCache() {
		synchronized (this.cachedProperties) {
			this.cachedProperties.clear();
		}
		synchronized (this.cachedMergedProperties) {
			this.cachedMergedProperties.clear();
		}
	}

	public void clearCacheIncludingAncestors() {
		clearCache();
		if (getParentMessageSource() instanceof ReloadableResourceBundleMessageSource) {
			((ReloadableResourceBundleMessageSource) getParentMessageSource()).clearCacheIncludingAncestors();
		}
	}

	public Set getKey(String fileName,Locale local) {
		PropertiesHolder propHolder = getProperties(fileName,local);
		return propHolder.getProperties().keySet();
	}

	public String toString() {
		return getClass().getName() + ": basenames=[" + StringUtils.arrayToCommaDelimitedString(this.basenames) + "]";
	}

	protected class PropertiesHolder {

		private Properties properties;

		private long fileTimestamp = -1;

		private long refreshTimestamp = -1;

		private final Map cachedMessageFormats = new HashMap();

		public PropertiesHolder(Properties properties, long fileTimestamp) {
			this.properties = properties;
			this.fileTimestamp = fileTimestamp;
		}

		public PropertiesHolder() {
		}

		public Properties getProperties() {
			return properties;
		}

		public long getFileTimestamp() {
			return fileTimestamp;
		}

		public void setRefreshTimestamp(long refreshTimestamp) {
			this.refreshTimestamp = refreshTimestamp;
		}

		public long getRefreshTimestamp() {
			return refreshTimestamp;
		}

		public String getProperty(String code) {
			if (this.properties == null) {
				return null;
			}
			return this.properties.getProperty(code);
		}

		public MessageFormat getMessageFormat(String code, Locale locale) {
			if (this.properties == null) {
				return null;
			}
			synchronized (this.cachedMessageFormats) {
				Map localeMap = (Map) this.cachedMessageFormats.get(code);
				if (localeMap != null) {
					MessageFormat result = (MessageFormat) localeMap.get(locale);
					if (result != null) {
						return result;
					}
				}
				String msg = this.properties.getProperty(code);
				if (msg != null) {
					if (localeMap == null) {
						localeMap = new HashMap();
						this.cachedMessageFormats.put(code, localeMap);
					}
					MessageFormat result = createMessageFormat(msg, locale);
					localeMap.put(locale, result);
					return result;
				}
				return null;
			}
		}
	}
	
	public class FileSystemResourceLoader extends DefaultResourceLoader {
		
		public FileSystemResourceLoader() {
			super();
		}
		
		protected Resource getResourceByPath(String path) {
			if (path != null && path.startsWith("/")) {
				path = path.substring(1);
			}
			if(null != getClassLoader().getResource("/")){
				return new FileSystemResource(getClassLoader().getResource("/").getFile()+path);
			}
			return new FileSystemResource(getClassLoader().getResource("").getFile()+path);
		}
	}

}
