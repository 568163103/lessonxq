package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * ScfgMenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_menu")
public class TMenu implements java.io.Serializable {

	private static final long serialVersionUID = -4584476363446660807L;

	private Integer mid;
	private Integer preid;
	private String fullMid = "";
	private String name;
	private String iconPath = "";
	private String url;
	private Short lel;
	private Short dmlflag = 0;
	private Date dmltime = new Date();
	private Short isFunc;
	private Short status = 1;
	private String modName;
	private Integer serialNo = 0;

	public TMenu() {
	}

	public TMenu(Integer preid, String name, String url, Short lel,
			Short dmlflag, Date dmltime) {
		this.preid = preid;
		this.name = name;
		this.url = url;
		this.lel = lel;
		this.dmlflag = dmlflag;
		this.dmltime = dmltime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "mid")
	public Integer getMid() {
		return this.mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	@Column(name = "preid")
	public Integer getPreid() {
		return this.preid;
	}

	public void setPreid(Integer preid) {
		this.preid = preid;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "icon_path")
	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "lel")
	public Short getLel() {
		return this.lel;
	}

	public void setLel(Short lel) {
		this.lel = lel;
	}

	@Column(name = "dmlflag")
	public Short getDmlflag() {
		return this.dmlflag;
	}

	public void setDmlflag(Short dmlflag) {
		this.dmlflag = dmlflag;
	}

	@Column(name = "dmltime")
	public Date getDmltime() {
		return this.dmltime;
	}

	public void setDmltime(Date dmltime) {
		this.dmltime = dmltime;
	}
	
	@Column(name = "is_func")
	public Short getIsFunc() {
		return isFunc;
	}

	public void setIsFunc(Short isFunc) {
		this.isFunc = isFunc;
	}

	@Column(name = "status")
	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	@Transient
	public String getModName() {
		return modName;
	}

	public void setModName(String modName) {
		this.modName = modName;
	}

	@Column(name = "full_mid")
	public String getFullMid() {
		return fullMid;
	}

	public void setFullMid(String fullMid) {
		this.fullMid = fullMid;
	}

	@Column(name = "serial_no")
	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

}