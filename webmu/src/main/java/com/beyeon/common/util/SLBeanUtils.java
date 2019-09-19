package com.beyeon.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.Assert;

/**
 * User: lwf
 * Date: 13-8-30
 * Time: 上午11:09
 */
public abstract class SLBeanUtils extends BeanUtils{

	public static void copyProperties(Object source, Object target) throws BeansException {
		copyProperties(source, target, true);
	}

	public static void copyProperties(Object source, Object target,Boolean ignoreNull) throws BeansException {
		copyProperties(source, target, null, null,ignoreNull);
	}

	public static void copyProperties(Object source, Object target, Class<?> editable)
			throws BeansException {
		copyProperties(source, target, editable, null,false);
	}

	public static void copyProperties(Object source, Object target, String[] ignoreProperties)
			throws BeansException {
		copyProperties(source, target, null, ignoreProperties,false);
	}

	private static void copyProperties(Object source, Object target, Class<?> editable, String[] ignoreProperties,Boolean ignoreNull)
			throws BeansException {

		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "Target must not be null");

		Class<?> actualEditable = target.getClass();
		if (editable != null) {
			if (!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() +
						"] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null) ? Arrays.asList(ignoreProperties) : null;

		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null &&
					(ignoreProperties == null || (!ignoreList.contains(targetPd.getName())))) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						if(ignoreNull && null == value){
							continue;
						}
						Method writeMethod = targetPd.getWriteMethod();
						if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
							writeMethod.setAccessible(true);
						}
						writeMethod.invoke(target, value);
					}
					catch (Throwable ex) {
						throw new FatalBeanException("Could not copy properties from source to target", ex);
					}
				}
			}
		}
	}

}
