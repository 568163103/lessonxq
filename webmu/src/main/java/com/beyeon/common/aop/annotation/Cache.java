package com.beyeon.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * User: lwf
 * Date: 12-6-14
 * Time: 上午11:45
 * 定时任务
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
	int GET = 1;
	int REFRESH = 2;
	int GETORREFRESH = 3;

	String Bs ="Bs";
	String Bm5 ="Bm5";
	String Bm10 ="Bm10";
	String Bm ="Bm";
	String BH ="BH";
	String Bd ="Bd";
	String BM ="BM";
	String Bw ="Bw";
	String BH_4 ="BH_4";
	String Bw_1 ="Bw_1";

	/*
	* 有三种值：
	* 1、只做缓存，当第一次加载数据成功后将数据缓存，以后不再刷新缓存数据，startRun一定要为true,
	* 这个一般不常用，可以用GETORREFRESH替代，把refreshExpre设大点即可。
	* 2、只做定时执行，不做缓存
	* 3、缓存和定时刷新缓存，
	* */
	int type() default GETORREFRESH;

	/*
	* 这里的值会按配制文件里具体表达式执行
	* */
	String refreshExpre() default BH;

	/*
	* 定时任务是不是在服务起动时执行一次
	* */
	boolean startRun() default true;

	String cacheName();

	/*
	* 定时任务是不是有只在一台机器执行的限制
	* */
	boolean exclusive() default false;
}
