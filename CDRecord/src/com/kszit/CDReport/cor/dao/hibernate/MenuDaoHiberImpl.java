package com.kszit.CDReport.cor.dao.hibernate;



import java.util.List;

import com.kszit.CDReport.cor.dao.MenuDao;
import com.kszit.CDReport.cor.dao.hibernate.po.MenuPO;

public class MenuDaoHiberImpl extends HibernateActionParent implements MenuDao {

	public String insert(MenuPO menu) {
		return super.insert(menu);
	}

	public String update(MenuPO menu) {
		return menu.getId()+"";
	}

	public String saveOrUpdate(MenuPO menu) {
		if(menu.getId()!=null){
			super.update(menu);
			return menu.getId()+"";
		}else{
			return super.insert(menu);
		}
	}

	public boolean delete(long id) {
		return this.deletes(id+",0");
	}

	public boolean deletes(String ids) {
		super.deleteByIds("MenuPO", ids);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List<MenuPO> getListByParent(long parentid) {
		super.openSession();
		List<MenuPO> relist = session.createQuery("from MenuPO as po where po.parentid="+parentid +" order by orderIndex").list();
		return relist;
	}
	
	@SuppressWarnings("unchecked")
	public List<MenuPO> getAllMenuList() {
		super.openSession();
		List<MenuPO> relist = session.createQuery("from MenuPO as po ").list();
		return relist;
	}

	public MenuPO getOneById(long id) {
		super.openSession();
		MenuPO po = (MenuPO) session.get(MenuPO.class,new Long(id));
		super.closeSession();
		return po;
	}



}
