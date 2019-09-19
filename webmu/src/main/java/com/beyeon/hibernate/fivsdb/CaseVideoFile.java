package com.beyeon.hibernate.fivsdb;

import java.io.File;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.beyeon.common.util.DateUtil;


/**
 * The persistent class for the case_video_file database table.
 * 
 */
@Entity
@Table(name="case_video_file")
@IdClass(CaseVideoFilePK.class)
public class CaseVideoFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "caseid")
	private String caseid;

	@Id
	@Column(name = "casefileseq")
	private String casefileseq;

	@Temporal(TemporalType.TIMESTAMP)
	private Date downloadtime = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date filebegintime = new Date();

	@Temporal(TemporalType.TIMESTAMP)
	private Date fileendtime = new Date();

	private String filepath = "";

	private Integer deletetorecycle = 0;
	private String casefileMD5;
	private Float longitude = 0F;
	private Float latitude = 0F;

	@Transient
	private String filesourceFileName;
	@Transient
	private File filesource;

	public CaseVideoFile() {
	}

	public String getCaseid() {
		return caseid;
	}

	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}

	public String getCasefileseq() {
		return casefileseq;
	}

	public void setCasefileseq(String casefileseq) {
		this.casefileseq = casefileseq;
	}

	public Date getDownloadtime() {
		return this.downloadtime;
	}

	public void setDownloadtime(Date downloadtime) {
		this.downloadtime = downloadtime;
	}

	public Date getFilebegintime() {
		return this.filebegintime;
	}

	public String getFilebegintimeStr() {
		return DateUtil.format(this.filebegintime,DateUtil.yyyyMMddHHmmssSpt);
	}

	public void setFilebegintime(Date filebegintime) {
		this.filebegintime = filebegintime;
	}

	public Date getFileendtime() {
		return this.fileendtime;
	}

	public String getFileendtimeStr() {
		return DateUtil.format(this.fileendtime,DateUtil.yyyyMMddHHmmssSpt);
	}

	public void setFileendtime(Date fileendtime) {
		this.fileendtime = fileendtime;
	}

	public String getFilepath() {
		return this.filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public Integer getDeletetorecycle() {
		return deletetorecycle;
	}

	public void setDeletetorecycle(Integer deletetorecycle) {
		this.deletetorecycle = deletetorecycle;
	}

	public String getCasefileMD5() {
		return casefileMD5;
	}

	public void setCasefileMD5(String casefileMD5) {
		this.casefileMD5 = casefileMD5;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public String getFilesourceFileName() {
		return filesourceFileName;
	}

	public void setFilesourceFileName(String fileSourceFileName) {
		this.filesourceFileName = fileSourceFileName;
	}

	public File getFilesource() {
		return filesource;
	}

	public void setFilesource(File fileSource) {
		this.filesource = fileSource;
	}

	public String getFileName() {
		return this.filepath.substring(this.filepath.lastIndexOf("/")+1);
	}
}