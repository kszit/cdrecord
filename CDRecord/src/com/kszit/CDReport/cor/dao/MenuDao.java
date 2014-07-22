package com.kszit.CDReport.cor.dao;

import java.util.List;


import com.kszit.CDReport.cor.dao.hibernate.po.MenuPO;

public interface MenuDao extends DaoParent{
	
	public String insert(MenuPO menu);
	
	public String update(MenuPO menu);
	
	public String saveOrUpdate(MenuPO menu);
	
	public boolean delete(long id);
	
	public boolean deletes(String ids);
	
	public List<MenuPO> getListByParent(long parentid);
	
	public List<MenuPO> getAllMenuList();
	
	public MenuPO getOneById(long id);
	
	
	
}
