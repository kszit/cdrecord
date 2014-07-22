package com.kszit.CDReport.cor.dao.hibernate;



import java.util.List;

import com.kszit.CDReport.cor.dao.DictCategoryDao;
import com.kszit.CDReport.cor.dao.hibernate.po.DataDBTableIndexPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DictCategoryPO;

public class DictCategoryDaoHiberImpl extends HibernateActionParent implements DictCategoryDao {

	public String insert(DictCategoryPO dicCategory) {
		super.openSession();
		String id = "";
		id = session.save(dicCategory)+"";
		super.closeSession();
		return id;
	}

	public void update(DictCategoryPO dicCategory){
		/*
		DictCategoryPO newpo = getDicById(3);
		try {
			BeanUtils.copyProperties(newpo,dicCategory);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		*/
		super.openSession();
		session.update(dicCategory);
		super.closeSession();
	}

	public void delete(long id) {
		super.openSession();
		DictCategoryPO po = new DictCategoryPO();
		po.setId(new Long(id));
		session.delete(po);
		super.closeSession(); 
	}

	@SuppressWarnings("unchecked")
	public List<DictCategoryPO> getListByParent(long parentid) {
		super.openSession();
		List<DictCategoryPO> relist = session.createQuery("from DictCategoryPO as po where po.parentid=:parentid").setLong("parentid",parentid).list();
		super.closeSession(); 
		return relist;
	}

	public DictCategoryPO getDicById(long id) {
		super.openSession();
		DictCategoryPO dict = (DictCategoryPO) session.get(DictCategoryPO.class,new Long(id));
		super.closeSession();
		return dict;
	}

	public DictCategoryPO getDicByBindid(String bindid){
		super.openSession();
		DictCategoryPO po = (DictCategoryPO)session.createQuery("from DictCategoryPO as po where po.bindId="+bindid).uniqueResult();
		return po;
	}
	

	
	public void saveOrUpdate(DictCategoryPO dicCategory) {
		super.saveOrUpdate(dicCategory);
	}

	public void deletes(String ids) {
		super.deleteByIds("DictCategoryPO", ids);
	}
	


}
