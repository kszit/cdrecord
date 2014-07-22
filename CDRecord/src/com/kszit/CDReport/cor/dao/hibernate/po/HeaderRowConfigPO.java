package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;


import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.HeaderRowConfigModel;
import com.kszit.CDReport.cor.service.StaticIndexService;
import com.kszit.CDReport.util.FloatUtil;
import com.kszit.CDReport.util.StaticVaribles;

public class HeaderRowConfigPO {

	public Long id;
	
	public Long bindId;
	
	private String content;
	
	private String dataCode;
	
	private int dataType;	
	
	private float dataLength;	
	
	private boolean isEmpty;
	
	private String defaultValue;
	
	private int UIModule;
	
	private String UIModuleDS;
	
	private int dataFrom;
	
	private String dataRule;

	private String dataRuleItems;
	
	private String dataRelation;
	
	private String dataRelationItems;
	
	private int width;
	
	private int height;
	
	private int _parent;
	
	private boolean _is_leaf;
	
	private String orderIndexStr;

	private String reportBindid;
	//uiModel的子项是否设定
	private int uiModelChildItemSet;
	//数据来源的子项是否设定
	private int dataFromChildItemSet;
	public HeaderRowConfigPO(){}
	
	public HeaderRowConfigPO(HeaderRowConfigModel model){
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
	
	public HeaderRowConfigPO(HeaderRowConfigPO po){
		try {
			BeanUtils.copyProperties(this,po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setId(null);
	}
	
	
	public HeaderRowConfigPO(VerticalColumnConfigPO po){
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
        /**
         * 新记录的初始化变量值
         */
        public void initParam(){
                this.setDataFrom(StaticVaribles.DataFrom_HandInputBindId);
                this.setUIModule(StaticVaribles.HTMLUIModule_InputBindid);
                this.setDataLength(50);
                this.setDataType(StaticVaribles.DataType_TextBindId);
                this.setUiModelChildItemSet(StaticVaribles.IsSetting_NotSettingBindId);
                this.setDataFromChildItemSet(StaticVaribles.IsSetting_NotSettingBindId);
                this.setWidth(100);
                this.setHeight(100);
        }
        
       /**
         * 父节点的属性值
         */
        public void initParntParam(){
                this.setDataFrom(0);
                this.setUIModule(0);
                this.setDataLength(0);
                this.setDataType(0);
                this.setUiModelChildItemSet(0);
                this.setDataFromChildItemSet(0);
                this.setWidth(0);
                this.setHeight(0);
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
	
	/**
	 * 报表填报中，数据类型的说明文字
	 * @return
	 */
	public String getDataTypeExplain(){
		String shuoming = "";
        switch (this.getDataType()) {
        case StaticVaribles.DataType_NumberBindId:
        	int decimalsLength = FloatUtil.getDecimalsPart(this.dataLength);
        	if(decimalsLength>0){
        		shuoming = "(保留"+decimalsLength+"位小数)";
        	}
        	break;
        case StaticVaribles.DataType_DateBindId:
        	shuoming = "(格式：yyyy-mm-dd)";
            break;
        case StaticVaribles.DataType_TimeBindId:
        	
            break;
        case StaticVaribles.DataType_DateTimeBindId:
            
            break;
        default:
        	
        }
		return StaticVaribles.getDicContent(this.getDataType()+"")+shuoming;
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
