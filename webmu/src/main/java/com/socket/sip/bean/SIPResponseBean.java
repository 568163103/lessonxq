package com.socket.sip.bean;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;

import com.socket.sip.SIPSocketUtil;
import com.socket.sip.XmlUtil;

public class SIPResponseBean extends SIPBean{
	private SIPResponseHead responseHead;
	private Map<String, Object> map;
	
	public SIPResponseBean (){
		super();
		setType(SIPType.RESPONSE);
	}
	public SIPResponseBean (SIPResponseHead head,Map<String, Object> content){
		this.responseHead = head;
		this.map = content;
		setType(SIPType.RESPONSE);
	}
	
	public SIPResponseHead getResponseHead() {
		return responseHead;
	}

	public void setResponseHead(SIPResponseHead responseHead) {
		this.responseHead = responseHead;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public String toString(){
		if (responseHead!=null  && map!=null && map.size()>0){			
			try {
				String xml = XmlUtil.formatXml(XmlUtil.map2xml(map, "response"));
				xml = xml.replace("?>", "standalone=\"yes\" ?>");
				byte[] by = xml.getBytes("UTF-8");							
				responseHead.setContent_length(String.valueOf(by.length));
				String head = responseHead.toString();
				return head+xml;
			} catch (DocumentException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
}
