package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "groups")
public class Groups {
	public static Integer TYPE_VIDEO = 1;
	public static Integer TYPE_PLATFORM = 14;
	private String id;
	private String name;
	private Integer type;
	private Integer subnum = 0;
	private String description;
	
	private String position;	
	
	@Transient
	public String getPosition() {
		if (StringUtils.isBlank(position) && StringUtils.isNotBlank(id)){
			String pos = id.substring(0, 6);
			setPosition(pos);			
		}
		return position;
	}
	@Transient
	public String getPositionZH(){
		String pos = getPosition();
		return PositionCode.getTypeName(pos);
	}
	public void setPosition(String position) {
		this.position = position;
	}
	@Id
	@Column(name = "id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	@Transient
	public String getTypeName() {
		return DictBpo.getDictName(TDict.GROUPS_TYPE,type.toString());
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Basic
	@Column(name = "subnum")
	public Integer getSubnum() {
		return subnum;
	}

	public void setSubnum(Integer subnum) {
		this.subnum = subnum;
	}

	@Basic
	@Column(name = "description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Groups groups = (Groups) o;

		if (id != null ? !id.equals(groups.id) : groups.id != null) return false;
		if (name != null ? !name.equals(groups.name) : groups.name != null) return false;
		if (type != null ? !type.equals(groups.type) : groups.type != null) return false;
		if (subnum != null ? !subnum.equals(groups.subnum) : groups.subnum != null) return false;
		if (description != null ? !description.equals(groups.description) : groups.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (subnum != null ? subnum.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
