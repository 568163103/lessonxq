package com.beyeon.hibernate.fivsdb;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "position_code")
public class PositionCode {
	private String code;

	private String oldcode;
	private String name ;
	private String oldname;
	
	private static Map<String,String> typeMap = new HashMap<String,String>();
	
	public static String getTypeName(String code){
		return typeMap.get(code);
	}
	
	public static String getCode(String name ){
		for (Entry<String, String> positionCode :typeMap.entrySet() ){
			String value = positionCode.getValue();
			if (StringUtils.isNotBlank(name) && name.equals(value)){
				return positionCode.getKey();
			}
		}
		return null;
	}
	public static Map<String,String> getTypeMap(){
		return typeMap;
	}
	@Id
	@Column(name = "code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Transient
	public String getOldcode() {
		return oldcode;
	}

	public void setOldcode(String oldcode) {
		this.oldcode = oldcode;
	}
	
	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public String getOldname() {
		return oldname;
	}

	public void setOldname(String oldname) {
		this.oldname = oldname;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		PositionCode other = (PositionCode) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
