package com.socket.netty;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.socket.sip.SIPUtils;
import com.socket.sip.bean.SIPBean;
import com.socket.sip.bean.SIPErrorBean;
import com.socket.sip.bean.SIPRequestBean;
import com.socket.sip.bean.SIPResponseBean;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class SIPDecoder  extends ByteToMessageDecoder {  
  
     
    @Override  
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {  
        //标记开始读取位置  
        in.markReaderIndex(); 
        byte[] info = new byte[in.readableBytes()];  
        in.readBytes(info);  
        String content = new String(info, "utf-8");
        if (StringUtils.isNotBlank(content)){
        	List<SIPRequestBean> requestList = SIPUtils.requestValid(content);	    //验证是否是request	
            if (requestList != null && requestList.size()>0){
            	for (int i = 0 ; i < requestList.size(); i++){
            		SIPRequestBean sip = requestList.get(i);
            		out.add(sip);
            	}
            }else{
            	List<SIPResponseBean> responseList = SIPUtils.responseValid(content);  //验证是否是response
        		if (responseList!=null  && responseList.size()>0){
        			for (int i = 0 ; i < responseList.size(); i++){
        				SIPResponseBean sip = responseList.get(i);
        				out.add(sip);
        			}
        		}else{
        			SIPErrorBean sip = new SIPErrorBean();
        			out.add(sip);
        		}
            }
        }
        
    }  

}
