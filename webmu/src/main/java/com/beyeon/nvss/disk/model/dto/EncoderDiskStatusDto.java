package com.beyeon.nvss.disk.model.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EncoderDiskStatusDto {
	private String num;
	private Integer freeSpace;
	private Integer totalSpace;
	private String status;
	private String statusName;
	private List childNames = new ArrayList();
	private List childDataes = new ArrayList<>();
	
	public EncoderDiskStatusDto(String [] childNames, Integer [] datas) {
		this.childNames = Arrays.asList(childNames);
		this.childDataes = Arrays.asList(datas);
	}
	
	public String getNum() {
		return num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	
	public Integer getFreeSpace() {
		return freeSpace;
	}
	
	public void setFreeSpace(Integer freeSpace) {
		this.freeSpace = freeSpace;
	}
	
	public Integer getTotalSpace() {
		return totalSpace;
	}
	
	public void setTotalSpace(Integer totalSpace) {
		this.totalSpace = totalSpace;
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		if (status.equals("0")) {
			return "正常";
		} else if (status.equals("1")) {
			return "未格式化";
		} else if (status.equals("2")) {
			return "异常";
		}
		return "未知";
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public List getChildNames() {
		return childNames;
	}

	public void setChildNames(List childNames) {
		this.childNames = childNames;
	}

	public List getChildDataes() {
		return childDataes;
	}

	public void setChildDataes(List childDataes) {
		this.childDataes = childDataes;
	}
	
}
