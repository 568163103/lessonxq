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
@Table(name = "encoder_model")
public class EncoderModel {
	private String model;
	private String provider;

	@Id
	@Column(name = "model")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Basic
	@Column(name = "provider")
	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		EncoderModel that = (EncoderModel) o;

		if (model != null ? !model.equals(that.model) : that.model != null) return false;
		if (provider != null ? !provider.equals(that.provider) : that.provider != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = model != null ? model.hashCode() : 0;
		result = 31 * result + (provider != null ? provider.hashCode() : 0);
		return result;
	}
}
