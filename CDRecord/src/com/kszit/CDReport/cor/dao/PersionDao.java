/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao;

import com.kszit.CDReport.cor.dao.hibernate.po.PersionPO;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersionDao extends DaoParent{
    	public String insert(PersionPO persion);
	
	public String update(PersionPO persion);
	
	public String saveOrUpdate(PersionPO persion);
	
	public boolean deletes(String ids);
	
	public PersionPO getOneById(long id);
	
	public PersionPO getOneByBindid(long bindid);
        
        public List<PersionPO> getPersionByDepid(String depid);
        
        public PersionPO getPersionByLoninName(String loginName);
}
