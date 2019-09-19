package com.beyeon.common.web.model.dto;

public class AutoCompleteDto {
	private String label;
	private String value;
	private String desc;
	private String icon;

	public AutoCompleteDto() {
		super();
	}

	public AutoCompleteDto(String label, String value) {
		super();
		this.label = label;
		this.value = value;
	}

	public AutoCompleteDto(String label, String value, String desc, String icon) {
		super();
		this.label = label;
		this.value = value;
		this.desc = desc;
		this.icon = icon;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setValueI(Integer value) {
		this.value = value.toString();
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}

}
