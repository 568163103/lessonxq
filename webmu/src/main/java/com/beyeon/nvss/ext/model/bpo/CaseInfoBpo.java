package com.beyeon.nvss.ext.model.bpo;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.CaseInfo;
import com.beyeon.nvss.ext.model.dao.CaseInfoDao;
import com.beyeon.nvss.ext.model.dto.CaseInfoDto;

/**
 * User: Administrator
 * Date: 2016/4/5
 * Time: 11:25
 */
@Component("caseInfoBpo")
public class CaseInfoBpo {
	private CaseInfoDao caseInfoDao;

	public void setCaseInfoDao(CaseInfoDao caseInfoDao) {
		this.caseInfoDao = caseInfoDao;
	}

	public void saveOrUpdate(Object object) {
		this.caseInfoDao.saveOrUpdateFivs(object);
	}

	public <T> T get(Class<T> clases, Serializable id) {
		return this.caseInfoDao.getFivs(clases, id);
	}

	public CaseInfoDto get(String id) {
		CaseInfoDto caseInfoDto = new CaseInfoDto();
		caseInfoDto.setCaseinfo(this.caseInfoDao.getFivs(CaseInfo.class, id));
		if (null != caseInfoDto.getCaseinfo()){
			caseInfoDto.setCasevideofiles(this.caseInfoDao.findCaseVideoFile(id));
			caseInfoDto.setCaseinformants(this.caseInfoDao.findCaseInformant(id));
		}
		return caseInfoDto;
	}

	public void save(CaseInfoDto caseInfoDto) {
		this.caseInfoDao.saveOrUpdateFivs(caseInfoDto.getCaseinfo());
		this.caseInfoDao.deleteCaseinformant(caseInfoDto.getCaseinfo().getCid());
		this.caseInfoDao.saveAllFivs(caseInfoDto.getCaseinformants());
		this.caseInfoDao.saveAllFivs(caseInfoDto.getCasevideofiles());
	}

	public List<CaseInfo> findPage(PageObject pageObject) {
		return this.caseInfoDao.findPage(pageObject);
	}

	public void findPageVideo(PageObject pageObject) {
		this.caseInfoDao.findPageVideo(pageObject);
	}
}
