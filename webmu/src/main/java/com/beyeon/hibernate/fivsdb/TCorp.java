package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2016/8/5.
 */
@Entity
@Table(name = "t_corp")
public class TCorp {
	private Integer cid;
	private String cname;
	private String director;
	private String mobile;
	private String address;

	@Id
	@Column(name = "cid")
	@GeneratedValue(strategy = IDENTITY)
	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	@Basic
	@Column(name = "cname")
	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Basic
	@Column(name = "director")
	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	@Basic
	@Column(name = "mobile")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Basic
	@Column(name = "address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TCorp corp = (TCorp) o;

		if (cid != null ? !cid.equals(corp.cid) : corp.cid != null) return false;
		if (cname != null ? !cname.equals(corp.cname) : corp.cname != null) return false;
		if (director != null ? !director.equals(corp.director) : corp.director != null) return false;
		if (mobile != null ? !mobile.equals(corp.mobile) : corp.mobile != null) return false;
		if (address != null ? !address.equals(corp.address) : corp.address != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = cid != null ? cid.hashCode() : 0;
		result = 31 * result + (cname != null ? cname.hashCode() : 0);
		result = 31 * result + (director != null ? director.hashCode() : 0);
		result = 31 * result + (mobile != null ? mobile.hashCode() : 0);
		result = 31 * result + (address != null ? address.hashCode() : 0);
		return result;
	}
}
