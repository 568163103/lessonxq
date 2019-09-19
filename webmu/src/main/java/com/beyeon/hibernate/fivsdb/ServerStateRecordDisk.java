package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.beyeon.common.web.model.entity.BaseEntity;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "server_state_record_disk")
@GenericGenerator(name="genID", strategy="increment")
public class ServerStateRecordDisk extends BaseEntity{
	private Integer id;
	private Integer recordid;
	private String driver;
	private String total;	
	private String used;
	private String left;
	
	public ServerStateRecordDisk (){
		
	}
	
	public ServerStateRecordDisk (ServerStateRecordDisk disk){
		this.driver = disk.getDriver();
		this.total = disk.getTotal();
		this.used  = disk.getUsed();
		this.left = disk.getLeft();
	} 


	@Id
	@GeneratedValue(generator="genID")
	@Column(name = "id")
	public Integer  getId() {
		return id;
	}

	public void setId(Integer  id) {
		this.id = id;
	}
	
	@Basic
	@Column(name = "recordid")
	public Integer getRecordid() {
		return recordid;
	}

	public void setRecordid(Integer recordid) {
		this.recordid = recordid;
	}
	@Basic
	@Column(name = "driver")
	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}
	@Basic
	@Column(name = "total")
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}
	@Basic
	@Column(name = "used")
	public String getUsed() {
		return used;
	}

	public void setUsed(String used) {
		this.used = used;
	}
	@Basic
	@Column(name = "leftdisk")
	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((recordid == null) ? 0 : recordid.hashCode());
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		result = prime * result + ((used == null) ? 0 : used.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerStateRecordDisk other = (ServerStateRecordDisk) obj;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (recordid == null) {
			if (other.recordid != null)
				return false;
		} else if (!recordid.equals(other.recordid))
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		if (used == null) {
			if (other.used != null)
				return false;
		} else if (!used.equals(other.used))
			return false;
		return true;
	}
	
	
	
	

	
}
