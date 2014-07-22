/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao.hibernate;

import com.kszit.CDReport.cor.dao.DepartmentDao;
import com.kszit.CDReport.cor.dao.hibernate.po.DepartmentPO;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DepartmentDaoImpl extends HibernateActionParent implements DepartmentDao{

    @Override
    public String insert(DepartmentPO department) {
        return super.insert(department);
    }

    @Override
    public String update(DepartmentPO department) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String saveOrUpdate(DepartmentPO department) {
                if (department.getId() != null) {
            super.update(department);
            return department.getId() + "";
        } else {
            return super.insert(department);
        }
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public boolean deletesByBindid(String ids) {
        super.deleteByBindids2("DepartmentPO", ids);
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public DepartmentPO getOneById(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<DepartmentPO> getDeptByParentId(long parentId) {
        super.openSession();
        List<DepartmentPO> relist = session.createQuery("from DepartmentPO as po where po.parentid=:parentid order by orderIndex").setLong("parentid",parentId).list();
        return relist;
    }

    @Override
    public DepartmentPO getOneByBindid(long bingid) {
                super.openSession();
        DepartmentPO dept = (DepartmentPO)session.createQuery("from DepartmentPO as po where po.bindId=:bingid").setLong("bingid",bingid).uniqueResult();
        return dept;
    }
    
    
    
}
