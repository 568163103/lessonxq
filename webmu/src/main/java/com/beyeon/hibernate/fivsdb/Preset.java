package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.beyeon.general.baseinfo.model.bpo.DictBpo;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "preset")
@IdClass(PresetPK.class)
public class Preset {
	private String channelId;
	private Integer num;
	private String name;
	private Integer flag;

	@Id
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Id
	@Column(name = "num")
	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
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
	@Column(name = "flag")
	public Integer getFlag() {
		return flag;
	}

	@Transient
	public String getFlagName() {
		if (null != flag) {
			return DictBpo.getDictName(TDict.PRESET_FLAG,flag.toString());
		}
		return "";
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Preset preset = (Preset) o;

		if (channelId != null ? !channelId.equals(preset.channelId) : preset.channelId != null) return false;
		if (num != null ? !num.equals(preset.num) : preset.num != null) return false;
		if (name != null ? !name.equals(preset.name) : preset.name != null) return false;
		if (flag != null ? !flag.equals(preset.flag) : preset.flag != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = channelId != null ? channelId.hashCode() : 0;
		result = 31 * result + (num != null ? num.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (flag != null ? flag.hashCode() : 0);
		return result;
	}
}
