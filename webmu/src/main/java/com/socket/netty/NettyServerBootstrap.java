package com.socket.netty;

import java.util.concurrent.TimeUnit;

import com.beyeon.common.config.ResourceUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyServerBootstrap {
	 private int port;  
	    private SocketChannel socketChannel;  
	    public NettyServerBootstrap(int port) throws InterruptedException {  
	        this.port = port;  
	        bind();  
	    }  
	  
	    private void bind() throws InterruptedException {
	    	int keep = ResourceUtil.getSocketKeepAlive();
	        EventLoopGroup boss=new NioEventLoopGroup();  
	        EventLoopGroup worker=new NioEventLoopGroup();  
	        ServerBootstrap bootstrap=new ServerBootstrap();  
	        bootstrap.group(boss,worker);  
	        bootstrap.channel(NioServerSocketChannel.class);  
	        bootstrap.option(ChannelOption.SO_BACKLOG, 128);  
	        //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去  
	        bootstrap.option(ChannelOption.TCP_NODELAY, true);  
	        //保持长连接状态  
	        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);  
	        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {  
	            @Override  
	            protected void initChannel(SocketChannel socketChannel) throws Exception {  
	            	ByteBuf buf = Unpooled.copiedBuffer("</request>".getBytes());
	            	ByteBuf buf1 = Unpooled.copiedBuffer("</response>".getBytes());
	            	ByteBuf[] buf2 = new ByteBuf[2];
	            	buf2[0] = buf;
	            	buf2[1] = buf1;
	                ChannelPipeline p = socketChannel.pipeline();  
	                p.addLast(new DelimiterBasedFrameDecoder(2*1024*1024,false,true,buf2));
	                p.addLast(new IdleStateHandler(keep, 0, 0));
//	                p.addLast(new DelimiterBasedFrameDecoder(1024, buf1));
	                p.addLast(new SIPEncoder());  
	                p.addLast(new SIPDecoder());  
//	                p.addLast(new HeartBeatServerHandler());
	                p.addLast(new SIPServerHandler());  
	            }  
	        });  
	        ChannelFuture f= bootstrap.bind(port).sync();  
	        if(f.isSuccess()){  
	            System.out.println("server start---------------");  
	        }  
	    }  
	    
}
