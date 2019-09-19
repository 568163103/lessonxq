package com.beyeon.nvss.external.model.bpo;

import com.beyeon.bean.xml.cInterface.SetUserCamManageReqBean;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.security.model.dao.UserDao;
import com.beyeon.hibernate.fivsdb.*;
import com.beyeon.nvss.device.model.dao.ChannelDao;
import com.beyeon.nvss.device.model.dao.EncoderDao;
import com.beyeon.nvss.external.model.dao.ChannelDistributeDao;
import com.beyeon.nvss.external.model.dto.ChannelDistributeDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component("channelDistributeBpo")
public class ChannelDistributeBpo {

	private ChannelDistributeDao channelDistributeDao;

	public ChannelDistributeDao getChannelDistributeDao() {
		return channelDistributeDao;
	}

	public void setChannelDistributeDao(ChannelDistributeDao channelDistributeDao) {
		this.channelDistributeDao = channelDistributeDao;
	}

	public ChannelDistributeDto get(String id) {
		ChannelDistributeDto channelDistributeDto = new ChannelDistributeDto();
		channelDistributeDto.setChannelDistribute(this.channelDistributeDao.getFivs(ChannelDistribute.class, Integer.parseInt(id)));
		return channelDistributeDto;
	}

	public void save(List<ChannelDistribute> channelDistribute) {
		this.channelDistributeDao.saveAllFivs(channelDistribute);

	}
	public void save(ChannelDistribute channelDistribute) {
		this.channelDistributeDao.saveFivs(channelDistribute);

	}

	public void update (ChannelDistributeDto channelDistributeDto){
		this.channelDistributeDao.updateFivs(channelDistributeDto.getChannelDistribute());
	}
	public void find(PageObject pageObject) {
		this.channelDistributeDao.find(pageObject);
	}

	public void findRes(PageObject pageObject) {
		this.channelDistributeDao.findRes(pageObject);
	}
	public void delete(String[] ids) {
		this.channelDistributeDao.delete(ids);
	}

	public void delete(String id) {
		String[] ids = new String[1];
		ids[0] = id;
		this.channelDistributeDao.delete(ids);
	}

	public ChannelDistribute findById(int id){
		return this.channelDistributeDao.findById(id);
	}
}
