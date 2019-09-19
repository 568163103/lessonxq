package com.socket.sip.bean;

import java.io.IOException;
import java.util.Map;

import org.dom4j.DocumentException;

import com.socket.sip.SIPSocketUtil;
import com.socket.sip.XmlUtil;

public class SIPRequestBean extends SIPBean{
	private SIPRequestHead requestHead;
	private Map<String, Object> map;
	
	public SIPRequestBean (){
		super();
		setType(SIPType.REQUEST);
	}
	public SIPRequestBean (SIPRequestHead head,Map<String, Object> content){
		this.requestHead = head;
		this.map = content;
		setType(SIPType.REQUEST);
	}
	
	public SIPRequestHead getRequestHead() {
		return requestHead;
	}

	public void setRequestHead(SIPRequestHead requestHead) {
		this.requestHead = requestHead;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	@Override
	public String toString(){
		if (requestHead!=null  && map!=null && map.size()>0){			
			try {
				String xml = XmlUtil.formatXml(XmlUtil.map2xml(map, "request"));
				xml = xml.replace("?>", "standalone=\"yes\" ?>");
				byte[] by = xml.getBytes("UTF-8");							 
				requestHead.setContent_length(String.valueOf(by.length));
				String head = requestHead.toString();
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
