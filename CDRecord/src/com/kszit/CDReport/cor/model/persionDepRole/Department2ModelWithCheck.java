/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.model.persionDepRole;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.DepartmentPO;

/**
 *	带有checkbox的属性组织机构返回此对象
 * @author Administrator
 */
public class Department2ModelWithCheck extends Department2Model {


    private boolean checked = false;

    public Department2ModelWithCheck(){}
    
    
    
    public Department2ModelWithCheck(Department2Model parent){
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
