/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao;

import com.kszit.CDReport.cor.dao.hibernate.po.DepartmentPO;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface DepartmentDao extends DaoParent{
        public String insert(DepartmentPO department);
	
	public String update(DepartmentPO department);
	
	public String saveOrUpdate(DepartmentPO department);
	
	public boolean delete(long id);
        
        public boolean deletesByBindid(String ids);
	
	public DepartmentPO getOneById(long id);
        
        public DepartmentPO getOneByBindid(long bingid);
        
        
        public List<DepartmentPO> getDeptByParentId(long parentId);
}
