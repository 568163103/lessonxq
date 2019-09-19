package com.beyeon.nvss.bussiness.model.bpo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Action;
import com.beyeon.hibernate.fivsdb.ActionType;
import com.beyeon.hibernate.fivsdb.PreScheme;
import com.beyeon.hibernate.fivsdb.PreSchemeAction;
import com.beyeon.hibernate.fivsdb.Resolution;
import com.beyeon.nvss.bussiness.model.dao.PreSchemeDao;
import com.beyeon.nvss.bussiness.model.dto.PreSchemeDto;

@Cache(cacheName="预案")
@Component("preSchemeBpo")
public class PreSchemeBpo extends BaseBpo {
	private PreSchemeDao preSchemeDao;
	
	public void setPreSchemeDao(PreSchemeDao preSchemeDao) {
		this.preSchemeDao = preSchemeDao;
	}

	@Cache(cacheName = "初始化预案相关基础数据")
	public void initAllType(){
		List<Resolution> resolutions = this.preSchemeDao.getAllFivs(Resolution.class);
		for (Resolution resolution : resolutions) {
			Resolution.getTypeMap().put(resolution.getId().toString(),resolution.getName());
		}
	}

	@Cache(cacheName = "预案",refreshExpre = Cache.Bs)
	public void findPreScheme(){
		List<PreScheme> preSchemes = this.preSchemeDao.getAllFivs(PreScheme.class);
		Map<String,String> currObjectlMap = new HashMap<String, String>();
		for (PreScheme preScheme : preSchemes) {
			currObjectlMap.put(preScheme.getId().toString(), preScheme.getName());
		}
		PreScheme.setObjectMap(currObjectlMap);
	}

	public PreSchemeDto get(Integer id,String actionId) {
		PreSchemeDto preSchemeDto = new PreSchemeDto();
		preSchemeDto.setPreScheme(this.preSchemeDao.getFivs(PreScheme.class, id));
		preSchemeDto.setActionObjects(this.preSchemeDao.getAction(id));
		if(StringUtils.isNotBlank(actionId))
			preSchemeDto.setActionObject(this.preSchemeDao.getFivs(Action.class, Integer.valueOf(actionId)));
		return preSchemeDto;
	}
	
	public void savePreScheme(PreSchemeDto preSchemeDto) {
		if(null == preSchemeDto.getPreScheme().getId())
        	this.preSchemeDao.saveFivs(preSchemeDto.getPreScheme());
		else
			this.preSchemeDao.updateFivs(preSchemeDto.getPreScheme());
	}

	public void saveAction(PreSchemeDto preSchemeDto) {
		preSchemeDto.getActionObject().setName(ActionType.getTypeName(preSchemeDto.getActionObject().getType()));
		this.preSchemeDao.saveFivs(preSchemeDto.getActionObject());
		this.preSchemeDao.saveFivs(new PreSchemeAction(preSchemeDto.getPreScheme().getId(), preSchemeDto.getActionObject().getId()));
	}
	
	public void updateAction(PreSchemeDto preSchemeDto) {
		preSchemeDto.getActionObject().setName(ActionType.getTypeName(preSchemeDto.getActionObject().getType()));
		this.preSchemeDao.updateFivs(preSchemeDto.getActionObject());
	}
	
	public void delete(String[] preSchemes) {
		this.preSchemeDao.deletePreSchemeById(preSchemes);
	}

	public void find(PageObject pageObject) {
		this.preSchemeDao.find(pageObject);
	}

	public boolean checkUnique(String attrName,String value,String id) {
		return this.preSchemeDao.checkPreSchemeUnique(id, value);
	}

	public List getPresetByChannelId(String channelId) {
		return this.preSchemeDao.getPresetByChannelId(channelId);
	}

	public void deleteAction(String[] aid) {
		this.preSchemeDao.deleteActionById(aid);
	}
}
