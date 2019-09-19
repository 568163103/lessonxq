package com.socket.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.Channel;
import io.netty.channel.socket.SocketChannel;

public class NettyChannelMap {
	 private static String userid = null;
	 private static Map<String,SocketChannel> map=new ConcurrentHashMap<String, SocketChannel>();  
	    
	 public static void add(String clientId,SocketChannel socketChannel){  
	        map.put(clientId,socketChannel);  	    
	 }  
	    
	 public static SocketChannel get(String clientId){  
	       return map.get(clientId);  	    
	 }  
	   
	 public static String remove(SocketChannel socketChannel){  
	        for (Map.Entry entry:map.entrySet()){  
	            if (entry.getValue()==socketChannel){
	            	String clientId = (String) entry.getKey();
	                map.remove(entry.getKey());  
	                return clientId;
	            }  
	        } 
	        return null;	   
	 } 
	    
	    
	 public static boolean hasValue(SocketChannel socketChannel){	       
	        return map.containsValue(socketChannel);	   
	 }

	public static String getUserid() {
		return userid;
	}

	public static void setUserid(String userid) {
		NettyChannelMap.userid = userid;
	} 
	    
	 
}
