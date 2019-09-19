package com.beyeon.common.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.beyeon.common.config.ResourceUtil;
import com.socket.netty.NettyServerBootstrap;

public class SIPSocketListener  implements ServletContextListener {
	
	 private MyThread myThread;
	 private NettyServerBootstrap server = null;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		String str=null;
		if (str==null&&myThread==null) {
			myThread=new MyThread();
			myThread.start();//servlet 上下文初始化时启动socket
		}
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		if (myThread!=null&&myThread.isInterrupted()) {
			 server = null;
			 myThread.interrupt();
		}
		
	}
	 class MyThread extends Thread{
		 int port = ResourceUtil.getSocketPort();
		 public void run(){
			 
			 while(!this.isInterrupted()){
				 try {
					 Thread.sleep(2000);
				 }catch (Exception e) {
					 e.printStackTrace();
				 }
				 if (server == null){
					 try {
							server = new NettyServerBootstrap(port);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				 }
				
				
				
			 }
			 
		 }
	 }
	
	
//	private SIPMainListenSocketThread socketThread;
//
//	@Override
//	public void contextDestroyed(ServletContextEvent arg0) {
//		// TODO Auto-generated method stub
//		 if(null != socketThread && !socketThread.isInterrupted()){
//	            socketThread.closeSocketServer();//关闭线程
//	            socketThread.interrupt();//中断线程
//	     }
//		
//	}
//
//	@Override
//	public void contextInitialized(ServletContextEvent sce) {		
//		if(null == socketThread){
//            socketThread = new SIPMainListenSocketThread();
//            socketThread.start();
//        }
//	}
		
	

}
