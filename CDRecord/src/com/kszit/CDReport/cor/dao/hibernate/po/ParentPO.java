package com.kszit.CDReport.cor.dao.hibernate.po;

import java.util.Date;

import com.kszit.CDReport.cor.service.StaticIndexService;

public class ParentPO {

	private Long id;
	
	protected Long bindId;
	
	private int orderIndex;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(int orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Long getBindId() {
		return bindId;
	}

	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}
	/**
	 * 初始化bindid，添加数据时必须调用
	 * 返回值：true 生成新的bindid，false 不用生成
	 */
	public boolean initBindId(){
		if(bindId==null || bindId==0){
			bindId = StaticIndexService.getBindidIndex();
			return true;
		}
		return false;
	}
	
	public String getDaySqlFileValue(Date d){
		if(d==null){
			return "null";
		}else{
			return "'"+d+"'";
		}
	}
}
