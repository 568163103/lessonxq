package com.beyeon.hibernate.fivsdb;

import com.beyeon.common.web.model.entity.BaseEntity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "channel_distribute")
@GenericGenerator(name="genID", strategy="increment")
public class ChannelDistribute extends BaseEntity{
	private Integer id;
	private String serverId;
	private String channelId;
	private String createTime;
	private String createUserId;
	private String createUserName;


	public ChannelDistribute(){

	}

	public ChannelDistribute(ChannelDistribute channelDistribute){
		this.id = channelDistribute.getId();
		this.serverId = channelDistribute.getServerId();
		this.channelId = channelDistribute.getChannelId();
		this.createTime = channelDistribute.getCreateTime();
		this.createUserId = channelDistribute.getCreateUserId();
		this.createUserName = channelDistribute.getCreateUserName();
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
	@Column(name = "server_id")
	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	@Basic
	@Column(name = "channel_id")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	@Basic
	@Column(name = "create_time")
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Basic
	@Column(name = "create_user_id")
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	@Basic
	@Column(name = "create_user_name")
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
