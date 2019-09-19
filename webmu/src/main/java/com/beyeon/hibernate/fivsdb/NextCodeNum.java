package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:40
 */
@Entity
@Table(name = "next_code_num")
public class NextCodeNum {
	private String name;
	private String typeCode;
	private Integer num;

	@Id
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "type_code")
	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Basic
	@Column(name = "num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		NextCodeNum that = (NextCodeNum) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (typeCode != null ? !typeCode.equals(that.typeCode) : that.typeCode != null) return false;
		if (num != null ? !num.equals(that.num) : that.num != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (typeCode != null ? typeCode.hashCode() : 0);
		result = 31 * result + (num != null ? num.hashCode() : 0);
		return result;
	}
}
