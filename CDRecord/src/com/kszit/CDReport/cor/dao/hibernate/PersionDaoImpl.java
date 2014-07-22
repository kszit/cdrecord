/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.dao.hibernate;

import com.kszit.CDReport.cor.dao.PersionDao;
import com.kszit.CDReport.cor.dao.hibernate.po.PersionPO;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class PersionDaoImpl extends HibernateActionParent implements PersionDao{

    @Override
    public String insert(PersionPO persion) {
        return super.insert(persion);
    }

    @Override
    public String update(PersionPO persion) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String saveOrUpdate(PersionPO persion) {
                if (persion.getId() != null) {
            super.update(persion);
            return persion.getId() + "";
        } else {
            return super.insert(persion);
        }
    }

    @Override
    public boolean deletes(String ids) {
        super.deleteByIds("PersionPO", ids);
        return true;
    }

    @Override
    public PersionPO getOneById(long id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PersionPO> getPersionByDepid(String depid) {
                super.openSession();
        List<PersionPO> relist = session.createQuery("from PersionPO as po where po.depId=:depid").setLong("depid",Long.parseLong(depid)).list();
        return relist;
        
    }

    @Override
    public PersionPO getPersionByLoninName(String loginName) {
        super.openSession();
        PersionPO po = (PersionPO)session.createQuery("from PersionPO as po where po.loginName=:longinname").setString("longinname",loginName).uniqueResult();
        return po;
    }

	@Override
	public PersionPO getOneByBindid(long bindid) {
        super.openSession();
        PersionPO po = (PersionPO)session.createQuery("from PersionPO as po where po.bindId=:bindid").setLong("bindid",bindid).uniqueResult();
        return po;
	}
    
}
