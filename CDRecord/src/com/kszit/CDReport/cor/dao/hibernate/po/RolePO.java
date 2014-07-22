/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao.hibernate.po;

import com.kszit.CDReport.cor.model.persionDepRole.Role2Model;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;

/**
 * 角色
 * @author Administrator
 */
public class RolePO extends ParentPO{
    
	private String name;
	
	private String remark;
	//角色拥有的菜单，格式：|菜单bindid|菜单bindid|
	private String menus;

        private Set<PersionPO> persions;

        public RolePO(){}
        
        public RolePO(Role2Model model){
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

    public Set<PersionPO> getPersions() {
        return persions;
    }

    public void setPersions(Set<PersionPO> persions) {
        this.persions = persions;
    }

	public String getMenus() {
		return menus;
	}

	public void setMenus(String menus) {
		this.menus = menus;
	}


	
	
}
