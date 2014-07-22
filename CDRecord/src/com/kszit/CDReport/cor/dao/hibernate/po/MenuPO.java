package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;


import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.MenuModel;

public class MenuPO extends TreeParentPO {

	private String text;
	
	private String href;
	
	private String hrefTarget;
	
	public MenuPO(){}
	
	public MenuPO(MenuModel model){
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

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getHrefTarget() {
		return hrefTarget;
	}

	public void setHrefTarget(String hrefTarget) {
		this.hrefTarget = hrefTarget;
	}
}
