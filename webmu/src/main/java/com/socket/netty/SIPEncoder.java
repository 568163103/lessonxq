package com.socket.netty;

import com.socket.sip.bean.SIPBean;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SIPEncoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		ByteBufOutputStream writer = new ByteBufOutputStream(out);  
		byte[] info = null;  
		if (msg != null ) {  
			info = msg.toString().getBytes("utf-8"); 
            writer.write(info);  
        }  
		
	}

}
