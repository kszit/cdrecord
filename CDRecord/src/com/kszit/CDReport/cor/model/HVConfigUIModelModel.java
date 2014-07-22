package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;

public class HVConfigUIModelModel extends HVConfigModel{

	private int UIModule;
	
	private String UIModuleDS;
	
	public HVConfigUIModelModel(){}
	
	public HVConfigUIModelModel(HVConfigUIModelPO po){
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
	
	public HVConfigUIModelModel(String jsondata){
		String jsonObject2 = jsondata.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		HVConfigUIModelModel menu_temp = (HVConfigUIModelModel)JSONObject.toBean(jsonDict, HVConfigUIModelModel.class);
		try {
			BeanUtils.copyProperties(this,menu_temp);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getUIModule() {
		return UIModule;
	}

	public void setUIModule(int uIModule) {
		UIModule = uIModule;
	}

	public String getUIModuleDS() {
		return UIModuleDS;
	}

	public void setUIModuleDS(String uIModuleDS) {
		UIModuleDS = uIModuleDS;
	}
	
	

}
