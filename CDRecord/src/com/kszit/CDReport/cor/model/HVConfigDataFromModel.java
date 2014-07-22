package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;

public class HVConfigDataFromModel extends HVConfigModel{

	private String dataFromType;
	//公式
	private String formula;
	//数据项
	private String dataTerm;
	
	public HVConfigDataFromModel(){}
	
	public HVConfigDataFromModel(HVConfigDataFromPO po){
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
	
	public HVConfigDataFromModel(String jsondata){
		String jsonObject2 = jsondata.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		HVConfigDataFromModel menu_temp = (HVConfigDataFromModel)JSONObject.toBean(jsonDict, HVConfigDataFromModel.class);
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
	public String getDataFromType() {
		return dataFromType;
	}

	public void setDataFromType(String dataFromType) {
		this.dataFromType = dataFromType;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getDataTerm() {
		return dataTerm;
	}

	public void setDataTerm(String dataTerm) {
		this.dataTerm = dataTerm;
	}

}
