package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.MenuPO;

public class MenuModelWithCheck extends MenuModel {


    private boolean checked = false;

    public MenuModelWithCheck(){}
    
    
    
    public MenuModelWithCheck(MenuPO parent){
        try {
		    BeanUtils.copyProperties(this, parent);
		} catch (IllegalAccessException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (InvocationTargetException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
    }
    public boolean getChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
