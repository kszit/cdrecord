/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.model.persionDepRole;

import com.kszit.CDReport.cor.dao.hibernate.po.PersionPO;
import com.kszit.CDReport.cor.dao.hibernate.po.RolePO;
import com.kszit.CDReport.cor.model.ParentModel;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Administrator
 */
public class Persion2Model extends ParentModel {

    
    
    
    
    
        private String name;
    
    private String loginName;
    
    private String password;
    
    private Set<Role2Model> roles;
    
    private String roleid;
    
    private Long depId;

    public Persion2Model() {
    }

    public Persion2Model(PersionPO po) {
        try {
            if(po==null){
                return;
            }
            BeanUtils.copyProperties(this, po);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.setRoleid2(po.getRoles());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role2Model> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role2Model> roles) {
        this.roles = roles;
    }

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }
    
    /**
     * 一个用户对应角色，通过《set》中的元素初始化
     */
    public void setRoleid2(Set<RolePO> rolepos){
        if(roleid==null && rolepos!=null && rolepos.size()>0){
            Iterator <RolePO> iter = rolepos.iterator();
            RolePO rolePO = iter.next();
            this.roleid = rolePO.getBindId()+"";
            
        }
    }

}
