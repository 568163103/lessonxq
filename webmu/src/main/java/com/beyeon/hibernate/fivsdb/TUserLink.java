package com.beyeon.hibernate.fivsdb;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * ScfgMenu entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_link")
public class TUserLink extends BaseEntity implements java.io.Serializable {
	private static final long serialVersionUID = -6011444369262878585L;
	private String amid;
	private String preamid;
	private String fullAmid = "";

	public TUserLink() {
	}

	@Id
	@GeneratedValue(generator = "generator")
    @GenericGenerator(name = "generator", strategy = "assigned")
	@Column(name = "full_amid")
	public String getFullAmid() {
		return fullAmid;
	}

	public void setFullAmid(String fullAmid) {
		this.fullAmid = fullAmid;
	}

	@Column(name = "amid")
	public String getAmid() {
		return this.amid;
	}

	public void setAmid(String amid) {
		this.amid = amid;
	}

	@Column(name = "preamid")
	public String getPreamid() {
		return this.preamid;
	}

	public void setPreamid(String preamid) {
		this.preamid = preamid;
	}

}