package com.beyeon;

import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
 * 测试基类
 * 
 */
public class TestBase {

	protected static String[] config = { "classpath:/spring/applicationContext*.xml" };

	protected Logger log = LoggerFactory.getLogger(getClass());

	public ApplicationContext ctx;

	@Before
	public void setUp() throws Exception {
		ctx = new FileSystemXmlApplicationContext(config);
	}

}