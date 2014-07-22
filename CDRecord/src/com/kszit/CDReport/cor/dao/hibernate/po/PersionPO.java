/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao.hibernate.po;

import com.kszit.CDReport.cor.dao.RoleDao;
import com.kszit.CDReport.cor.dao.hibernate.RoleDaoImpl;
import com.kszit.CDReport.cor.model.persionDepRole.Persion2Model;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author Administrator
 */
public class PersionPO extends ParentPO {

    public PersionPO() {
    }

    public PersionPO(Persion2Model model) {
        try {
            BeanUtils.copyProperties(this, model);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (model.getId() == 0) {
            this.setId(null);
        }
        RoleDao roleDao = new RoleDaoImpl();
        Set<RolePO> roleSet = new HashSet<RolePO>();
        roleSet.add(roleDao.getOneByBindid(Long.parseLong(model.getRoleid())));
        this.setRoles(roleSet);
    }
    private String name;
    private String loginName;
    private String password;
    private Set<RolePO> roles;
    private Long depId;

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

    public Long getDepId() {
        return depId;
    }

    public void setDepId(Long depId) {
        this.depId = depId;
    }

    public Set<RolePO> getRoles() {
        
        return roles;
    }

    public void setRoles(Set<RolePO> roles) {
        this.roles = roles;
    }
}
