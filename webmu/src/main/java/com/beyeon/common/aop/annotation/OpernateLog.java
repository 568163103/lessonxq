package com.beyeon.common.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * User: lwf
 * Date: 12-5-25
 * Time: 上午11:20
 */
@Target({METHOD,TYPE})
@Retention(RUNTIME)
public @interface OpernateLog {

	String handle() default "";

	short operType() default 0;
}
