package com.socket.sip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;

import com.socket.sip.bean.SIPBean;
import com.socket.sip.bean.SIPRequestBean;
import com.socket.sip.bean.SIPRequestHead;
import com.socket.sip.bean.SIPResponseBean;
import com.socket.sip.bean.SIPResponseHead;

public class SIPUtils {
	
	public static List<SIPRequestBean> requestValid(String content){
		List<SIPRequestBean>  list = new ArrayList<SIPRequestBean> ();
		try{						
			while (StringUtils.isNotBlank(content)){
				
				HashMap<String, SIPRequestBean> map=null;
				try {
					map = castRequest(content);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (map!=null  && map.size()>0){
					Set<String> key = map.keySet();
					Iterator<String> iter = key.iterator();
					if (iter.hasNext()){
						content = iter.next();
						SIPRequestBean sip = map.get(content);
						if (sip!=null)
							list.add(sip);
					}
				}else{
					content = null;
				}
			}
			return list;
		} catch (Exception e){
			e.printStackTrace();			
		}
		
		return null;
	}
	
	
	public static List<SIPResponseBean> responseValid(String content){
		List<SIPResponseBean> list = new ArrayList<SIPResponseBean>();
		try{
			while (StringUtils.isNotBlank(content)){
				HashMap<String, SIPResponseBean> map = castResponse(content);
				if (map!=null  && map.size()>0){
					Set<String> key = map.keySet();
					Iterator<String> iter = key.iterator();
					if (iter.hasNext()){
						content = iter.next();
						SIPResponseBean sip = map.get(content);
						if (sip!=null)
							list.add(sip);
					}
				}else{
					content = null;
				}
			}
			return list;
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap <String,SIPRequestBean> castRequest(String content) {
			try {				
				String sip = content.substring(content.indexOf("INVITE"), content.indexOf("SIP/2.0"));
				String via = content.substring(content.indexOf("Via: SIP/2.0/TCP ")+17, content.indexOf("To:"));
				String to = content.substring(content.indexOf("To:")+3, content.indexOf("From:"));
				String from = content.substring(content.indexOf("From:")+5, content.indexOf("Max-Forwards:"));
				String max_forwards = content.substring(content.indexOf("Max-Forwards:")+13, content.indexOf("Call-ID:"));
				String call_id = content.substring(content.indexOf("Call-ID:")+8, content.indexOf("CSeq:"));
				String cseq = content.substring(content.indexOf("CSeq:")+5, content.indexOf("Content-Type:"));
				String content_type = content.substring(content.indexOf("Content-Type:")+13, content.indexOf("Content-Length:"));
				String content_length = content.substring(content.indexOf("Content-Length:")+15, content.indexOf("<?"));
				if (StringUtils.isNotBlank(sip) && StringUtils.isNotBlank(via) && StringUtils.isNotBlank(to) 
						&& StringUtils.isNotBlank(from) && StringUtils.isNotBlank(max_forwards) && StringUtils.isNotBlank(call_id) 
						&& StringUtils.isNotBlank(cseq) && StringUtils.isNotBlank(content_type) && StringUtils.isNotBlank(content_length)){
					content_length = content_length.replace("\n", "").trim();
					SIPRequestHead requestHead = new SIPRequestHead();
					requestHead.setSip(sip);
					requestHead.setVia(via);
					requestHead.setCall_id(call_id);
					requestHead.setContent_length(content_length);
					requestHead.setContent_type(content_type);
					requestHead.setCseq(cseq);
					requestHead.setFrom(from);
					requestHead.setMax_forwards(max_forwards);
					requestHead.setTo(to);
					
					String xmlContent = content.substring(content.indexOf("<?"),content.indexOf("request>")+8);
					Map<String, Object> map = XmlUtil.xml2mapWithAttr(xmlContent, false);
					content = content.substring(content.indexOf("request>")+8);					
					SIPRequestBean SIPBean = new SIPRequestBean(requestHead, map);
					HashMap <String,SIPRequestBean> hash = new HashMap<String,SIPRequestBean>();
					hash.put(content,SIPBean);
					return hash;
				}			
			} catch (Exception e ){
				
			}			
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public static HashMap <String,SIPResponseBean> castResponse (String content){
		try {	
				String code = content.substring(content.indexOf("SIP/2.0"), content.indexOf("OK"));
				String via = content.substring(content.indexOf("Via: SIP/2.0/TCP ")+17, content.indexOf("To:"));
				String to = content.substring(content.indexOf("To:")+3, content.indexOf("From:"));
				String from = content.substring(content.indexOf("From:")+5, content.indexOf("Call-ID:"));
				String call_id = content.substring(content.indexOf("Call-ID:")+8, content.indexOf("CSeq:"));
				String cseq = content.substring(content.indexOf("CSeq:")+5, content.indexOf("Content-Type:"));
				String content_type = content.substring(content.indexOf("Content-Type:")+13, content.indexOf("Content-Length:"));
				String content_length = content.substring(content.indexOf("Content-Length:")+15, content.indexOf("<?"));
				if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(via) && StringUtils.isNotBlank(to) 
						&& StringUtils.isNotBlank(from) && StringUtils.isNotBlank(call_id) && StringUtils.isNotBlank(cseq)
						&& StringUtils.isNotBlank(content_type) && StringUtils.isNotBlank(content_length)){
					content_length = content_length.replace("\n", "").trim();
					SIPResponseHead responseHead = new SIPResponseHead();
					responseHead.setCall_id(call_id);
					responseHead.setContent_length(content_length);
					responseHead.setContent_type(content_type);
					responseHead.setCseq(cseq);
					responseHead.setFrom(from);
					responseHead.setTo(to);
					responseHead.setVia(via);
					
					String xmlContent = content.substring(content.indexOf("<?"),content.indexOf("response>")+9);
					Map<String, Object> map = XmlUtil.xml2mapWithAttr(xmlContent, false);  
					content = content.substring(content.indexOf("response>")+9);					
					SIPResponseBean SIPBean = new SIPResponseBean(responseHead, map);
					HashMap <String,SIPResponseBean> hash = new HashMap<String,SIPResponseBean>();
					hash.put(content,SIPBean);
					return hash;
				}
			}  catch (Exception e ){
						
			}
		return null;
	}
	
	

}
