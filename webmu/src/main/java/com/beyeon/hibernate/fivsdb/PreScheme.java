package com.beyeon.hibernate.fivsdb;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "pre_scheme")
public class PreScheme {
	private static Map<String,String> objectMap = new HashMap<String,String>();

	public static String getObjectName(String id){
		return objectMap.get(id);
	}

	public static Map<String,String> getObjectMap(){
		return objectMap;
	}

	public static void setObjectMap(Map<String, String> currObjectMap){
		Map<String,String> oldObjectMap = objectMap;
		objectMap = currObjectMap;
		oldObjectMap.clear();
	}

	private Integer id;
	private String name;
	private String description;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

		PreScheme preScheme = (PreScheme) o;

		if (id != null ? !id.equals(preScheme.id) : preScheme.id != null) return false;
		if (name != null ? !name.equals(preScheme.name) : preScheme.name != null) return false;
		if (description != null ? !description.equals(preScheme.description) : preScheme.description != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
