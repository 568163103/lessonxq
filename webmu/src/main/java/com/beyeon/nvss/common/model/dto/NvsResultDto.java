package com.beyeon.nvss.common.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Administrator
 * Date: 2016/1/27
 * Time: 15:07
 */
public class NvsResultDto<T> {
	private int result = -1;
	private String message;
	private List<T> itemList = new ArrayList<T>();

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}
}
