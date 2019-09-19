package com.beyeon;

import org.junit.Test;

import com.beyeon.hibernate.fivsdb.CaseVideoFile;
import com.beyeon.nvss.ext.model.bpo.CaseInfoBpo;
import com.beyeon.nvss.ext.model.dto.CaseInfoDto;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 17:52
 */
public class UserTest extends TestBase {

	@Test
	public void test(){
		CaseInfoBpo caseInfoBpo = (CaseInfoBpo) ctx.getBean("caseInfoBpo");
		CaseInfoDto caseInfoDto = new CaseInfoDto();
		caseInfoDto.getCaseinfo().setCid("963");
		caseInfoDto.getCaseinfo().setCasetype(1);
		caseInfoDto.getCaseinfo().setCodenum("33333");
		caseInfoDto.getCaseinfo().setCreator("kk");
		caseInfoDto.getCaseinfo().setDescription("rrrrrrrrrrr");
		caseInfoDto.getCaseinfo().setDetectstate(1);
		caseInfoDto.getCaseinfo().setHappeningplace("dddddd");
		caseInfoDto.getCaseinfo().setJurisdictionarea("t");
		caseInfoDto.getCaseinfo().setName("ee");

		CaseVideoFile caseVideoFile = new CaseVideoFile();
		caseVideoFile.setCaseid(caseInfoDto.getCaseinfo().getCid());
		caseVideoFile.setCasefileseq("1");
		caseInfoDto.getCasevideofiles().add(caseVideoFile);
		caseInfoBpo.save(caseInfoDto);
	}
}
