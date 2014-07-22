package com.kszit.CDReport.cor.dao.hibernate;

import java.util.List;

import com.kszit.CDReport.cor.dao.DictionaryDao;
import com.kszit.CDReport.cor.dao.hibernate.po.DictCategoryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;

public class DictionaryDaoHiberImpl extends HibernateActionParent implements
		DictionaryDao {

	public String insert(DictionaryPO dictionary) {
		super.openSession();
		String id = "";
		id = session.save(dictionary)+"";
		super.closeSession();
		return id;
	}

	public void update(DictionaryPO dictionary) {
		super.openSession();
		session.update(dictionary);
		super.closeSession();
	}

	public void delete(long id) {
		super.openSession();
		DictionaryPO po = new DictionaryPO();
		po.setId(new Long(id));
		session.delete(po);
		super.closeSession(); 
	}

	@SuppressWarnings("unchecked")
	public List<DictionaryPO> getListByType(long type) {
		super.openSession();
		List<DictionaryPO> relist = session.createQuery("from DictionaryPO as po where po.type=:type order by orderIndex").setLong("type",type).list();
		super.closeSession();  
		return relist;
	}

	@SuppressWarnings("unchecked")
	public List<DictionaryPO> getListOffAll() {
		super.openSession();
		List<DictionaryPO> relist = session.createQuery("from DictionaryPO as po").list();
		super.closeSession(); 
		return relist;
	}
	
	public DictionaryPO getDicById(long id) {
		super.openSession();
		DictionaryPO dict = (DictionaryPO) session.get(DictionaryPO.class,new Long(id));
		super.closeSession();
		return dict;
	}
	public String saveOrUpdate(DictionaryPO dictionary) {
		if(dictionary.getId()!=null){
			super.update(dictionary);
			return dictionary.getId()+"";
		}else{
			return super.insert(dictionary);
		}
	}
	
	public void deletes(String ids) {
		super.deleteByIds("DictionaryPO", ids);
	}
	
	public DictionaryPO getDicByBindid(String bindid){
		super.openSession();
		DictionaryPO po = (DictionaryPO)session.createQuery("from DictionaryPO as po where po.bindId="+bindid).uniqueResult();
		return po;
	}


}
