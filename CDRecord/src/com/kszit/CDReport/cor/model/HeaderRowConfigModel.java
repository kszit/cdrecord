package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
/**
 * 横向表头配置信息model
 * @author Administrator
 */
public class HeaderRowConfigModel{
	
	private String content;
	
	public long bindId;
	
	private String dataCode;
	//数据类型
	private int dataType;	
	//数据长度
	private float dataLength;	
	//是否允许为空
	private boolean isEmpty;
	//数据默认值
	private String defaultValue;
	
	private int UIModule;
	
	private String UIModuleDS;
	//数据来源
	private int dataFrom;
	//数据来源的计算公式
	private String dataRule;
	//计算公式的数据项
	private String dataRuleItems;
	
	private String dataRelation;
	
	private String dataRelationItems;
	
	private int width;
	
	private int height;
	
	private int id;
	
	private int _id;
	
	private int _parent;
	
	private boolean _is_leaf;

	private String orderIndexStr;

	String reportBindid;
	//uiModel的子项是否设定
	private int uiModelChildItemSet;
	//数据来源的子项是否设定
	private int dataFromChildItemSet;
	
	//private int inputType;
	
	public HeaderRowConfigModel(){}
	
	public HeaderRowConfigModel(String jsondata){
		String jsonObject2 = jsondata.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		HeaderRowConfigModel temp_mode = (HeaderRowConfigModel)JSONObject.toBean(jsonDict, HeaderRowConfigModel.class);
		
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
	
	public HeaderRowConfigModel(HeaderRowConfigPO po){
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

	public float getDataLength() {
		return dataLength;
	}

	public void setDataLength(float dataLength) {
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

	public long getBindId() {
		return bindId;
	}

	public void setBindId(long bindId) {
		this._id = (int) bindId;
		this.bindId = bindId;
	}

	public String getUIModuleDS() {
		return UIModuleDS;
	}

	public void setUIModuleDS(String uIModuleDS) {
		UIModuleDS = uIModuleDS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUiModelChildItemSet() {
		return uiModelChildItemSet;
	}

	public void setUiModelChildItemSet(int uiModelChildItemSet) {
		this.uiModelChildItemSet = uiModelChildItemSet;
	}

	public int getDataFromChildItemSet() {
		return dataFromChildItemSet;
	}

	public void setDataFromChildItemSet(int dataFromChildItemSet) {
		this.dataFromChildItemSet = dataFromChildItemSet;
	}

	public String getDataRuleItems() {
		return dataRuleItems;
	}

	public void setDataRuleItems(String dataRuleItems) {
		this.dataRuleItems = dataRuleItems;
	}

	public String getDataRelationItems() {
		return dataRelationItems;
	}

	public void setDataRelationItems(String dataRelationItems) {
		this.dataRelationItems = dataRelationItems;
	}

	
}
