/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao;

import com.kszit.CDReport.cor.dao.hibernate.po.RolePO;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface RoleDao extends DaoParent{
        public String insert(RolePO department);
	
	public String update(RolePO department);
	
	public String saveOrUpdate(RolePO department);
	
	public boolean delete(long id);
        
        public boolean deletes(String ids);
	
	public RolePO getOneById(long id);
        
        public RolePO getOneByBindid(long id);
        
        
        public List<RolePO> getRoleList();
}
