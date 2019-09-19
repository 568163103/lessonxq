package com.beyeon.common.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.beyeon.common.config.ResourceUtil;

public class ThreadPoolManager {
	private static Executor threadPoolDefault = getDefaultExecutor();

	public static Executor getDefaultExecutor(){
		return new ThreadPoolExecutor(
				ResourceUtil.getCoreConfInt("threadpool.corePoolSize"),
				ResourceUtil.getCoreConfInt("threadpool.maximumPoolSize"),
				ResourceUtil.getCoreConfInt("threadpool.keepAliveTime"), TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>());
	}
	
	public static void excuteDefault(Runnable command) {
		threadPoolDefault.execute(command);
	}

}
