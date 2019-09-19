package com.beyeon.general.baseinfo.model.bpo;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.DmuAlarmInfo;
import com.beyeon.hibernate.fivsdb.Equipment;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.baseinfo.model.dao.CorpDao;
import com.beyeon.hibernate.fivsdb.TCorp;

@Cache(cacheName = "公司")
@Component("corpBpo")
public class CorpBpo extends BaseBpo {
	public static Map<Integer,TCorp> corpMap = new HashMap<Integer, TCorp>();

	public static TCorp getCorp(Integer corpId){
		return corpMap.get(corpId);
	}

	public static Collection<TCorp> getCorps(){
		return corpMap.values();
	}

	private CorpDao corpDao;
	
	public void setCorpDao(CorpDao corpDao) {
		this.corpDao = corpDao;
	}

	public TCorp get(Integer mid) {
		return this.corpDao.getFivs(TCorp.class, mid);
	}
	
	public void save(TCorp corp) {
        this.corpDao.saveFivs(corp);
	}
	
	public void update(TCorp corp) {
        this.corpDao.updateFivs(corp);
	}

	public void delete(String[] ids) {
		this.corpDao.delete(ids);
	}

	public void find(PageObject pageObject) {
		this.corpDao.find(pageObject);
	}

	public boolean checkUnique(String attrName, String value, String id) {
		return corpDao.checkCorpUnique(value,id);
	}

	@Cache(type = Cache.REFRESH,cacheName = "公司")
	public void initCorps() {
		Map<Integer,TCorp> tempCorpMap = new HashMap<Integer, TCorp>();
		List<TCorp> corps = corpDao.getAllFivs(TCorp.class);
		for (Iterator iterator = corps.iterator(); iterator.hasNext();) {
			TCorp corp = (TCorp) iterator.next();
			tempCorpMap.put(corp.getCid(), corp);
		}
		Map<Integer,TCorp> trCorpMap = corpMap;
		corpMap = tempCorpMap;
		trCorpMap.clear();
	}

	public void saveAlarmInfo (DmuAlarmInfo dmuAlarmInfo){
		this.corpDao.saveFivs(dmuAlarmInfo);
	}

	public Equipment findIdByIp(String ip){
		return this.corpDao.findIdByIp(ip);
	}
}
