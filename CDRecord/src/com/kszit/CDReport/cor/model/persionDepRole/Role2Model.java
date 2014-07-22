/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.model.persionDepRole;

import com.kszit.CDReport.cor.dao.hibernate.po.RolePO;
import com.kszit.CDReport.cor.model.ParentModel;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Administrator
 */
public class Role2Model extends ParentModel {

    private String name;
    private String remark;
	//角色拥有的菜单，格式：|菜单bindid|菜单bindid|
	private String menus;
    public Role2Model() {
    }

    public Role2Model(RolePO rolePO) {
        try {
            BeanUtils.copyProperties(this, rolePO);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}
    
}
