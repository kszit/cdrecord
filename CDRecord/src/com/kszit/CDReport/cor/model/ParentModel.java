package com.kszit.CDReport.cor.model;
/**
 * Model的父类
 * @author Administrator
 *
 */
public class ParentModel {

	private int id;
	
	public long bindId=0;
	
	private int orderIndex;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setId(String id) {
		if(id!=null && !"".equals(id)){
			this.id = Integer.parseInt(id);
		}
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public long getBindId() {
		return bindId;
	}

	public void setBindId(long bindId) {
		this.bindId = bindId;
	}
	public void setBindId(String bindId) {
		this.bindId = Long.parseLong(bindId);
	}
	
	
}
