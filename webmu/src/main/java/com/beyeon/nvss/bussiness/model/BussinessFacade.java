package com.beyeon.nvss.bussiness.model;

import com.beyeon.nvss.external.model.bpo.ChannelDistributeBpo;
import org.springframework.stereotype.Component;

import com.beyeon.nvss.bussiness.model.bpo.AlarmPreSchemeBpo;
import com.beyeon.nvss.bussiness.model.bpo.GroupsBpo;
import com.beyeon.nvss.bussiness.model.bpo.PreSchemeBpo;
import com.beyeon.nvss.bussiness.model.bpo.RecordPlanBpo;
import com.beyeon.nvss.bussiness.model.bpo.UserGroupBpo;
import com.beyeon.nvss.bussiness.model.bpo.UserTreeBpo;

/**
 * User: Administrator
 * Date: 2016/4/22
 * Time: 11:24
 */
@Component("bussinessFacade")
public class BussinessFacade {
	private AlarmPreSchemeBpo alarmPreSchemeBpo;
	private GroupsBpo groupsBpo;
	private UserGroupBpo userGroupBpo;
	private PreSchemeBpo preSchemeBpo;
	private RecordPlanBpo recordPlanBpo;
	private UserTreeBpo userTreeBpo;
	private ChannelDistributeBpo channelDistributeBpo;

	public AlarmPreSchemeBpo getAlarmPreSchemeBpo() {
		return alarmPreSchemeBpo;
	}

	public void setAlarmPreSchemeBpo(AlarmPreSchemeBpo alarmPreSchemeBpo) {
		this.alarmPreSchemeBpo = alarmPreSchemeBpo;
	}

	public GroupsBpo getGroupsBpo() {
		return groupsBpo;
	}

	public void setGroupsBpo(GroupsBpo groupsBpo) {
		this.groupsBpo = groupsBpo;
	}

	public PreSchemeBpo getPreSchemeBpo() {
		return preSchemeBpo;
	}

	public void setPreSchemeBpo(PreSchemeBpo preSchemeBpo) {
		this.preSchemeBpo = preSchemeBpo;
	}

	public RecordPlanBpo getRecordPlanBpo() {
		return recordPlanBpo;
	}

	public void setRecordPlanBpo(RecordPlanBpo recordPlanBpo) {
		this.recordPlanBpo = recordPlanBpo;
	}

	public UserTreeBpo getUserTreeBpo() {
		return userTreeBpo;
	}

	public void setUserTreeBpo(UserTreeBpo userTreeBpo) {
		this.userTreeBpo = userTreeBpo;
	}

	public UserGroupBpo getUserGroupBpo() {
		return userGroupBpo;
	}

	public void setUserGroupBpo(UserGroupBpo userGroupBpo) {
		this.userGroupBpo = userGroupBpo;
	}


	public ChannelDistributeBpo getChannelDistributeBpo() {
		return channelDistributeBpo;
	}

	public void setChannelDistributeBpo(ChannelDistributeBpo channelDistributeBpo) {
		this.channelDistributeBpo = channelDistributeBpo;
	}
}
