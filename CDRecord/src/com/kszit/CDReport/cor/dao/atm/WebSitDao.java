package com.kszit.CDReport.cor.dao.atm;

import java.util.List;

import com.kszit.CDReport.cor.dao.DaoParent;
import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.atm.WebSitPo;

public interface WebSitDao extends DaoParent{

	
	public String insertOrUpdate(WebSitPo data);
	
	public boolean delete(long id);
	
	public boolean deletes(String ids);
	
	public List<WebSitPo> getListOfAll();
	
}
