package com.beyeon.hibernate.fivsdb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "record_plan")
public class RecordPlan {
	private String oname;
	private String name;
	private Integer resolution;
	private Integer frametype;
	private String timespan;
	private Integer cycleDate;
	private String description;

	@Id
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Transient
	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	@Basic
	@Column(name = "resolution")
	public Integer getResolution() {
		return resolution;
	}

	@Transient
	public String getResolutionName() {
		return Resolution.getTypeName(resolution.toString());
	}

	public void setResolution(Integer resolution) {
		this.resolution = resolution;
	}

	@Basic
	@Column(name = "frametype")
	public Integer getFrametype() {
		return frametype;
	}

	public void setFrametype(Integer frametype) {
		this.frametype = frametype;
	}

	@Basic
	@Column(name = "timespan")
	public String getTimespan() {
		return timespan;
	}

	public void setTimespan(String timespan) {
		this.timespan = timespan;
	}

	@Basic
	@Column(name = "cycle_date")
	public Integer getCycleDate() {
		return cycleDate;
	}

	public void setCycleDate(Integer cycleDate) {
		this.cycleDate = cycleDate;
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

		RecordPlan that = (RecordPlan) o;

		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		if (resolution != null ? !resolution.equals(that.resolution) : that.resolution != null) return false;
		if (frametype != null ? !frametype.equals(that.frametype) : that.frametype != null) return false;
		if (timespan != null ? !timespan.equals(that.timespan) : that.timespan != null) return false;
		if (cycleDate != null ? !cycleDate.equals(that.cycleDate) : that.cycleDate != null) return false;
		if (description != null ? !description.equals(that.description) : that.description != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name != null ? name.hashCode() : 0;
		result = 31 * result + (resolution != null ? resolution.hashCode() : 0);
		result = 31 * result + (frametype != null ? frametype.hashCode() : 0);
		result = 31 * result + (timespan != null ? timespan.hashCode() : 0);
		result = 31 * result + (cycleDate != null ? cycleDate.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		return result;
	}
}
