package com.kszit.CDReport.cor.model;

public class TreeNodeModel {
	
	private int id;
	
	public long bindId;
	
	private String text;
	
	private boolean isleaf;
	
	private String cls;
	
	private String attributes;
	private String href;
	private String hrefTarget;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isIsleaf() {
		return isleaf;
	}

	public void setIsleaf(boolean isleaf) {
		this.isleaf = isleaf;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}

	public long getBindId() {
		return bindId;
	}

	public void setBindId(long bindId) {
		this.bindId = bindId;
	}
	
	
}
