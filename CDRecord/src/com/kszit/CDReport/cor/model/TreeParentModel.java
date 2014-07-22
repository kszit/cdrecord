package com.kszit.CDReport.cor.model;

public class TreeParentModel extends ParentModel {
	
	private int rid;
	
	private int parentid;
	
	private boolean isleaf;

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

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
