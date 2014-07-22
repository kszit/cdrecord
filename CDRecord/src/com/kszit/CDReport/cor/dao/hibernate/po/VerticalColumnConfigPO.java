package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.VerticalColumnConfigModel;
import com.kszit.CDReport.cor.service.StaticIndexService;

public class VerticalColumnConfigPO {
	
	public Long id;
	
	public Long bindId;

	private String content;
	
	private String dataCode;
	
	private int dataType;	
	
	private int dataLength;	
	
	private boolean isEmpty;
	
	private String defaultValue;
	
	private int UIModule;
	
	private String UIModuleDS;
	
	private int dataFrom;
	
	private String dataRule;
	
	private String dataRelation;
	
	private int width;
	
	private int height;
	
	private int _parent;
	
	private boolean _is_leaf;
	
	private String orderIndexStr;
	
	String reportBindid;

	public VerticalColumnConfigPO(){}
	
	public VerticalColumnConfigPO(VerticalColumnConfigModel model){
		try {
			BeanUtils.copyProperties(this,model);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(model.getId()==0){
			this.setId(null);
		}
	}
	
	public VerticalColumnConfigPO(VerticalColumnConfigPO oldPo){
		try {
			BeanUtils.copyProperties(this,oldPo);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setId(null);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int get_parent() {
		return _parent;
	}

	public void set_parent(int _parent) {
		this._parent = _parent;
	}

	public boolean is_is_leaf() {
		return _is_leaf;
	}

	public void set_is_leaf(boolean _is_leaf) {
		this._is_leaf = _is_leaf;
	}

	public String getOrderIndexStr() {
		return orderIndexStr;
	}

	public void setOrderIndexStr(String orderIndexStr) {
		this.orderIndexStr = orderIndexStr;
	}



	public String getReportBindid() {
		return reportBindid;
	}

	public void setReportBindid(String reportBindid) {
		this.reportBindid = reportBindid;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getDataLength() {
		return dataLength;
	}

	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}

	public boolean isIsEmpty() {
		return isEmpty;
	}

	public void setIsEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getUIModule() {
		return UIModule;
	}

	public void setUIModule(int uIModule) {
		UIModule = uIModule;
	}

	public int getDataFrom() {
		return dataFrom;
	}

	public void setDataFrom(int dataFrom) {
		this.dataFrom = dataFrom;
	}

	public String getDataRule() {
		return dataRule;
	}

	public void setDataRule(String dataRule) {
		this.dataRule = dataRule;
	}

	public String getDataRelation() {
		return dataRelation;
	}

	public void setDataRelation(String dataRelation) {
		this.dataRelation = dataRelation;
	}

	public Long getBindId() {
		return bindId;
	}

	public void setBindId(Long bindId) {
		this.bindId = bindId;
	}
	/**
	 * 初始化bindid，添加数据时必须调用
	 */
	public void initBindId(){
		if(bindId==null || bindId==0){
			bindId = StaticIndexService.getBindidIndex();
		}
	}

	public String getUIModuleDS() {
		return UIModuleDS;
	}

	public void setUIModuleDS(String uIModuleDS) {
		UIModuleDS = uIModuleDS;
	}
	
}
