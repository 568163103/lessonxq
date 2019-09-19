package com.beyeon.general.common.model.dto;

import java.lang.reflect.Type;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * User: Administrator
 * Date: 2016/1/27
 * Time: 15:07
 */
public class TransferData<T> {
	public static Type defaultType = new TypeReference<TransferData<Map<String,String>>>(){}.getType();
	private String cmd;
	private T data;
	private String message = "success";

	public TransferData() {
	}

	public TransferData(String cmd) {
		this.cmd = cmd;
	}

	public TransferData(TransferData transferData) {
		this.cmd = transferData.getCmd();
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getObject(Class<T> clazz){
		return ((JSONObject)data).toJavaObject(clazz);
	}

	public Object get(String key){
		return ((JSONObject)data).get(key);
	}

}
