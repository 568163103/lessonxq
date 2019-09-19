package com.beyeon.nvss.ext.model.dto;

import java.util.ArrayList;
import java.util.List;

import com.beyeon.hibernate.fivsdb.CaseInfo;
import com.beyeon.hibernate.fivsdb.CaseInformant;
import com.beyeon.hibernate.fivsdb.CaseVideoFile;

/**
 * User: Administrator
 * Date: 2016/4/5
 * Time: 11:12
 */
public class CaseInfoDto {
	private CaseInfo caseinfo = new CaseInfo();
	private CaseVideoFile casevideofile = new CaseVideoFile();
	private CaseInformant caseinformant = new CaseInformant();
	private List<CaseVideoFile> casevideofiles = new ArrayList<CaseVideoFile>();
	private List<CaseInformant> caseinformants = new ArrayList<CaseInformant>();

	public CaseInfo getCaseinfo() {
		return caseinfo;
	}

	public void setCaseinfo(CaseInfo caseinfo) {
		this.caseinfo = caseinfo;
	}

	public CaseVideoFile getCasevideofile() {
		return casevideofile;
	}

	public void setCasevideofile(CaseVideoFile casevideofile) {
		this.casevideofile = casevideofile;
	}

	public CaseInformant getCaseinformant() {
		return caseinformant;
	}

	public void setCaseinformant(CaseInformant caseinformant) {
		this.caseinformant = caseinformant;
	}

	public List<CaseVideoFile> getCasevideofiles() {
		return casevideofiles;
	}

	public void setCasevideofiles(List<CaseVideoFile> casevideofiles) {
		this.casevideofiles = casevideofiles;
	}

	public List<CaseInformant> getCaseinformants() {
		return caseinformants;
	}

	public void setCaseinformants(List<CaseInformant> caseinformants) {
		this.caseinformants = caseinformants;
	}
}
