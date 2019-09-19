package com.beyeon.common.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;




/**
 * 
 *    
 * 项目名称：mu   
 * 类名称：JaxbUtils   
 * 类描述：Jaxb辅助类
 * 创建人：gm   
 * 日期：2015年4月3日 下午2:57:13   
 * 版本：V2.9.4
 *
 */
public class JaxbUtils {

	private static final String DEFAULT_ENCODING = "GB2312";

	
	
	
	public static String getDefaultEncoding() {
		return DEFAULT_ENCODING;
	}


	/**
	 * 
	 * 方法名： marshaller 
	 * 方法描述： 将对象转换成XML
	 * 参数说明：
	 * 返回类型： String 
	 *
	 */
	public static String marshaller(Object root) {
		return marshaller(root, null);
	}

	
	/**
	 * 
	 * 方法名： marshaller 
	 * 方法描述： 将对象转换成XML(自定义encoding)
	 * 参数说明：
	 * 返回类型： String 
	 *
	 */
	public static String marshaller(Object root, String encoding) {
		return marshaller(root, root.getClass(), encoding);
	}
	
	
	/**
	 * 
	 * 方法名： marshaller 
	 * 方法描述： 将Collection接口子类转换为xml对象
	 * 参数说明：
	 * 返回类型： String 
	 *
	 */
	public static String marshaller(Collection<?> root, String rootName,
			Class<?> targetClass) {
		return marshaller(root, rootName, targetClass, null);
	}
	
	/**
	 * 
	 * 方法名： marshaller 
	 * 方法描述： 
	 * 参数说明：
	 * 返回类型： String 
	 *
	 */
	public static String marshaller(Collection<?> root, String rootName,
			Class<?> targetClass, String encoding) {
		try {
			CollectionWrapper wrapper = new CollectionWrapper();
			wrapper.collection = root;
			JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(
					new QName(rootName), CollectionWrapper.class, wrapper);
			StringWriter writer = new StringWriter();
			createMarshaller(targetClass, encoding).marshal(wrapperElement,
					writer);
			return writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * 方法名： unmarshal 
	 * 方法描述： 将XML转换成对象
	 * 参数说明：
	 * 返回类型： T 
	 *
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(String xml, Class<T> targetClass) {
		try {
			StringReader reader = new StringReader(xml);
			return (T) createUnmarshaller(targetClass).unmarshal(reader);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * 方法名： unmarshal 
	 * 方法描述： 将XML转换成对象
	 * 参数说明：
	 * 返回类型： T 
	 *
	 */
	@SuppressWarnings("unchecked")
	public static <T> T unmarshal(byte[] xml, Class<T> targetClass) {
		if(xml != null) {
			try {
				InputStream is = new ByteArrayInputStream(xml);
				return (T) createUnmarshaller(targetClass).unmarshal(is);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private static String marshaller(Object root, Class<?> targetClass,
			String encoding) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(targetClass, encoding).marshal(root, writer);
			return writer.toString();
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Marshaller createMarshaller(Class<?> targetClass,
			String encoding) {
		try {
			JAXBContext jaxbContext = createJAXBContext(targetClass);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_ENCODING, !StringUtils
					.isNotBlank(encoding) ? DEFAULT_ENCODING : encoding);

			return marshaller;
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static Unmarshaller createUnmarshaller(Class<?> targetClass) {
		try {
			JAXBContext jaxbContext = createJAXBContext(targetClass);
			return jaxbContext.createUnmarshaller();
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected static JAXBContext createJAXBContext(Class<?> classesToBeBound) {
		try {
			return JAXBContext.newInstance(classesToBeBound,
					CollectionWrapper.class);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return null;
	}

	static class CollectionWrapper {
		@XmlAnyElement
		protected Collection<?> collection;
	}
}