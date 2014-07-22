/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao.hibernate;

import com.kszit.CDReport.cor.dao.RoleDao;
import com.kszit.CDReport.cor.dao.hibernate.po.RolePO;
import java.util.List;

/**
 *
 * @author Administrator
 */

public class RoleDaoImpl extends HibernateActionParent implements RoleDao {
    @Override
    public String insert(RolePO rolePO) {
        return super.insert(rolePO);
    }

    @Override
    public String update(RolePO department) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String saveOrUpdate(RolePO rolePo) {
        if (rolePo.getId() != null) {
            super.update(rolePo);
            return rolePo.getId() + "";
        } else {
            return super.insert(rolePo);
        }
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public RolePO getOneById(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
   @Override
    public RolePO getOneByBindid(long bindid) {
               super.openSession();
        RolePO role = (RolePO)session.createQuery("from RolePO as po where po.bindId=:bindid").setLong("bindid", bindid).uniqueResult();
        return role;
       
    }
    @Override
    public List<RolePO> getRoleList() {
        super.openSession();
        List<RolePO> relist = session.createQuery("from RolePO as po").list();
        return relist;
    }

    @Override
    public boolean deletes(String ids) {
        super.deleteByIds("RolePO", ids);
        return true;
    }
}
