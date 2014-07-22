package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.DictCategoryPO;

public class DictCategoryModel extends TreeParentModel{

	private String text;
	
	private String cls;
	
	private int order;

	public DictCategoryModel(){}
	
	public DictCategoryModel(DictCategoryPO po){
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
	
	public static DictCategoryModel getCategoryByJson(String jsonstr){
		String jsonObject2 = jsonstr.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		DictCategoryModel dictionay = (DictCategoryModel)JSONObject.toBean(jsonDict, DictCategoryModel.class);
		return dictionay;
		
	}

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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}
	
}
