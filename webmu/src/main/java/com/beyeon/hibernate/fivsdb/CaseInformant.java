package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.beyeon.common.util.DateUtil;


/**
 * The persistent class for the case_informant database table.
 * 
 */
@Entity
@Table(name="case_informant")
public class CaseInformant implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date birthday = new Date(0);

	private String caseid;

	private int gender = 1;

	private String name = "";

	private String nativeplace = "";

	private String tel = "";

	public CaseInformant() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public String getBirthdayStr() {
		return DateUtil.format(this.birthday,DateUtil.yyyyMMddHHmmssSpt);
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getCaseid() {
		return this.caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public int getGender() {
		return this.gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getGenderZh() {
		switch (this.gender){
			case 1 :return "男";
			default:return "女";
		}
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNativeplace() {
		return this.nativeplace;
	}

	public void setNativeplace(String nativeplace) {
		this.nativeplace = nativeplace;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}