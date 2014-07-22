package com.kszit.CDReport.cor.dao.atm;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.HibernateActionParent;
import com.kszit.CDReport.cor.dao.hibernate.po.RolePO;
import com.kszit.CDReport.cor.dao.hibernate.po.atm.WebSitPo;

public class WebSitDaoHiberImpl extends HibernateActionParent implements
WebSitDao {

	@Override
	public String insertOrUpdate(WebSitPo data) {
		if(data.getId()!=null){
			super.update(data);
			return data.getId()+"";
		}else{
			return super.insert(data);
		}
	}

	@Override
	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deletes(String ids) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<WebSitPo> getListOfAll() {

        super.openSession();
        List<WebSitPo> relist = session.createQuery("from WebSitPo as po").list();
        return relist;
	}

	

}
