package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.DictCategoryModel;

public class DictCategoryPO extends TreeParentPO {

	private String text;
	
	private String cls;
	
	public DictCategoryPO(){}
	
	public DictCategoryPO(DictCategoryModel model){
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
}
