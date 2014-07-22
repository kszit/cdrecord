package com.kszit.CDReport.cor.dao.hibernate.po;

public class TreeParentPO extends ParentPO {
	private int parentid;
	
	private boolean isleaf;

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public boolean isIsleaf() {
		return isleaf;
	}

	public void setIsleaf(boolean isleaf) {
		this.isleaf = isleaf;
	}
	
	
}
