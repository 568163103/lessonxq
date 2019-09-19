package com.beyeon.nvss.ext.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.CaseInfo;
import com.beyeon.hibernate.fivsdb.CaseInformant;
import com.beyeon.hibernate.fivsdb.CaseVideoFile;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

/**
 * User: Administrator
 * Date: 2016/4/5
 * Time: 11:25
 */
@Component("caseInfoDao")
public class CaseInfoDao extends NvsBaseDao {
	public List<CaseVideoFile> findCaseVideoFile (String cid) {
		return this.getFivs_r().find("from CaseVideoFile where caseid = ? ",new Object[]{cid});
	}
	public List<CaseInformant> findCaseInformant (String cid) {
		return this.getFivs_r().find("from CaseInformant where caseid = ? ",new Object[]{cid});
	}

	public List<CaseInfo> findPage(PageObject pageObject) {
		StringBuilder sb = new StringBuilder();
		List params = new ArrayList();
		sb.append("from CaseInfo where 1=1 ");
		String users = ResourceUtil.getCoreConf("app.user");
		if(null != SpringUtil.getCurrUser() && !users.contains(SpringUtil.getCurrUser().getUsername()+",")){
			/*sb.append("and uid = ? ");
			params.add(SpringUtil.getCurrUser().getId());*/
		}
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("cid"))) {
			params.add(paramMap.get("cid"));
			sb.append("and cid = ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("name"))) {
			params.add("%" + paramMap.get("name") + "%");
			sb.append("and name like ? ");
		}
		return this.getFivs_r().find(pageObject,sb.toString(),params.toArray());
	}

	public void deleteCaseinformant(String cid) {
		this.getFivs_w().bulkUpdate("delete from CaseInformant where caseid = ? ",new Object[]{cid});
	}

	public void deleteCasevideofile(String cid) {
		this.getFivs_w().bulkUpdate("delete from CaseVideoFile where caseid = ? ",new Object[]{cid});
	}

	public void findPageVideo(PageObject pageObject) {
		List params = new ArrayList();
		StringBuilder sb = new StringBuilder("from CaseVideoFile c where 1=1 ");
		Map<String, String> paramMap = pageObject.getParams();
		if (StringUtils.isNotBlank(paramMap.get("beginTime"))) {
			params.add(DateUtil.parse(paramMap.get("beginTime"),DateUtil.yyyyMMddSpt));
			sb.append("and c.downloadtime > ? ");
		}
		if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
			params.add(DateUtil.parse(paramMap.get("endTime"),DateUtil.yyyyMMddSpt));
			sb.append("and c.downloadtime < ? ");
		}
		this.getFivs_r().find(pageObject,sb.toString(),params.toArray());
	}
}
