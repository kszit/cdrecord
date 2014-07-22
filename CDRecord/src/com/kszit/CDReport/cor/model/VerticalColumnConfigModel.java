package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
/**
 * 纵向单元格配置的model
 * @author Administrator
 */
public class VerticalColumnConfigModel extends TreeParentModel{
	
	private String content;
	
	public Long bindId;
	
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
	
	private int id;
	
	private int _id;
	
	private int _parent;
	
	private boolean _is_leaf;

	private String orderIndexStr;
	
	private String reportBindid;

	public VerticalColumnConfigModel(){}
	
	public VerticalColumnConfigModel(String jsondata){
		String jsonObject2 = jsondata.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		VerticalColumnConfigModel temp_mode = (VerticalColumnConfigModel)JSONObject.toBean(jsonDict, VerticalColumnConfigModel.class);
		
		try {
			BeanUtils.copyProperties(this,temp_mode);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VerticalColumnConfigModel(VerticalColumnConfigPO po){
		try {
			BeanUtils.copyProperties(this,po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
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

	public void setBindId(Long bindId) {
		this.bindId = bindId;
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

	public String getUIModuleDS() {
		return UIModuleDS;
	}

	public void setUIModuleDS(String uIModuleDS) {
		UIModuleDS = uIModuleDS;
	}

	public long getBindId() {
		return bindId;
	}

	public void setBindId(long bindId) {
		this._id = (int) bindId;
		this.bindId = bindId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
}
