package com.kszit.CDReport.cor.model;

/**
 * 树形结构model的父类
 * @author Administrator
 */
public class CommonTreeNodeModel{
	private String id;
	
	public long bindId;
	
	private int orderIndex;
	
	private int rid;
	
	private int parentid;
	
	private boolean isleaf;

	private String text;
	
	private String cls;
	
	private String rtType;

	public CommonTreeNodeModel(){}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getBindId() {
		return bindId;
	}

	public void setBindId(long bindId) {
		this.bindId = bindId;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

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

	public String getRtType() {
		return rtType;
	}

	public void setRtType(String rtType) {
		this.rtType = rtType;
	}
	

	
	
	
}
