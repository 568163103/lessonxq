package com.beyeon.hibernate.fivsdb;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The primary key class for the case_video_file database table.
 * 
 */
@Embeddable
public class CaseVideoFilePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String caseid;

	private String casefileseq;

	public CaseVideoFilePK() {
	}
	public String getCaseid() {
		return this.caseid;
	}
	public void setCaseid(String caseid) {
		this.caseid = caseid;
	}
	public String getCasefileseq() {
		return this.casefileseq;
	}
	public void setCasefileseq(String casefileseq) {
		this.casefileseq = casefileseq;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CaseVideoFilePK)) {
			return false;
		}
		CaseVideoFilePK castOther = (CaseVideoFilePK)other;
		return 
			this.caseid.equals(castOther.caseid)
			&& this.casefileseq.equals(castOther.casefileseq);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.caseid.hashCode();
		hash = hash * prime + this.casefileseq.hashCode();
		
		return hash;
	}
}