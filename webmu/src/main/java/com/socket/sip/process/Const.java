package com.socket.sip.process;

import java.nio.channels.SelectionKey;
import java.util.HashMap;
import java.util.Map;

import com.socket.sip.bean.SIPSessionBean;


public class Const {
	  	public static HashMap<String,SIPSessionBean> id_User_Map = new HashMap<String,SIPSessionBean>();
	    public static HashMap<String,String> ip_Userid_Map = new HashMap<String,String>();
	    public static Map<String,SelectionKey> ip_Keys_Map = new HashMap<String,SelectionKey>();	
}
