package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.*;

import com.beyeon.common.web.model.entity.BaseEntity;


/**
 * The persistent class for the t_user_trace database table.
 * 
 */
@Entity
@Table(name="t_user_trace")
public class TUserTrace  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "auid")
	private int auid;

	@Column(name="user_name")
	private String userName;

	@Column(name="user_type")
	private short userType = 1;

	private String amid;

	private int mid;

	@Column(name="menu_name")
	private String menuName;

	@Column(name="user_trace")
	private String userTrace;

	@Column(name="operate_date")
	private Date operateDate;

	@Column(name="operate_status")
	private Short operateStatus = 1;
	@Column(name="terminal_ip")
	private String terminalIp;
	@Transient
	private String objectTime;
	public TUserTrace() {
	}


	public int getAuid() {
		return this.auid;
	}

	public void setAuid(int auid) {
		this.auid = auid;
	}

	public Date getOperateDate() {
		return this.operateDate;
	}

	public void setOperateDate(Date operateDate) {
		this.operateDate = operateDate;
	}
	
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String user) {
		this.userName = user;
	}

	public short getUserType() {
		return this.userType;
	}

	public void setUserType(short userType) {
		this.userType = userType;
	}

	public String getAmid() {
		return this.amid;
	}

	public void setAmid(String amid) {
		this.amid = amid;
	}

	public int getMid() {
		return this.mid;
	}

	public void setMid(int mid) {
		this.mid = mid;
	}

	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String name) {
		this.menuName = name;
	}

	public String getUserTrace() {
		return this.userTrace;
	}

	public void setUserTrace(String userTrace) {
		this.userTrace = userTrace;
	}

	public String getTerminalIp() {
		return terminalIp;
	}

	public void setTerminalIp(String terminalIp) {
		this.terminalIp = terminalIp;
	}

	public Short getOperateStatus() {
		return operateStatus;
	}

	public void setOperateStatus(Short operateStatus) {
		this.operateStatus = operateStatus;
	}
	public String getObjectTime() {
		return objectTime;
	}

	public void setObjectTime(String objectTime) {
		this.objectTime = objectTime;
	}

	public String getOperateStatusZh() {
		if(null == operateStatus || 1!=operateStatus){
			return "失败";
		}
		return "成功";
	}
}