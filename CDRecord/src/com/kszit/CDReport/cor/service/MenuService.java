package com.kszit.CDReport.cor.service;

import java.util.List;

import com.kszit.CDReport.cor.dao.MenuDao;
import com.kszit.CDReport.cor.dao.hibernate.MenuDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.MenuPO;
import com.kszit.CDReport.cor.model.MenuModel;

public class MenuService {
	
	MenuDao menuDao = null;
	public MenuService(){
		menuDao = new MenuDaoHiberImpl();
	}
	
	public List<MenuPO> getAllMenuList(){
		return menuDao.getAllMenuList();
	}
	
	public List<MenuPO> getChilds(long parentid){
		return menuDao.getListByParent(parentid);
	}
	
	/**
	 * 
	 * @param jsondata
	 * @return
	 */
	public String saveOrUpdateMenu(String jsondata){
		MenuModel model = new MenuModel(jsondata);
		MenuPO po = new MenuPO(model);
		po.initBindId();
		return menuDao.saveOrUpdate(po);
	}
	
	public int deletes(String ids){
		menuDao.deletes(ids);
		return 1;
	}
	


}
