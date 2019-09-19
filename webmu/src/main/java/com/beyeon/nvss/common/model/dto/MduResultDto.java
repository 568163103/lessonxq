package com.beyeon.nvss.common.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Administrator
 * Date: 2015/11/17
 * Time: 9:25
 */
public class MduResultDto<T> {
	private int errorCode = 1;
	private String errorMsg = "FAIL";
	private String sn ="123456789";
	private int totalNum = 0;
	private List<T> itemList = new ArrayList<T>();

	public List<T> getItemList() {
		return itemList;
	}

	public void setItemList(List<T> itemList) {
		this.itemList = itemList;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public int getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
}
