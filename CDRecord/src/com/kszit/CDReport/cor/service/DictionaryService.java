package com.kszit.CDReport.cor.service;

import java.util.List;

import net.sf.json.JSONObject;

import com.kszit.CDReport.cor.dao.DictCategoryDao;
import com.kszit.CDReport.cor.dao.DictionaryDao;
import com.kszit.CDReport.cor.dao.hibernate.DictCategoryDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.DictionaryDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DictCategoryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.model.DictCategoryModel;
import com.kszit.CDReport.cor.model.DictionaryModel;

public class DictionaryService {
	
	DictCategoryDao dictCateDao = null;
	DictionaryDao dicDao = null;
	public DictionaryService(){
		dictCateDao = new DictCategoryDaoHiberImpl();
		dicDao = new DictionaryDaoHiberImpl();
	}
	/**
	 * 数据字典类别列表
	 * @param parentid
	 * @return
	 */
	public List<DictCategoryPO> getDictCateChilds(long parentid){
		return dictCateDao.getListByParent(parentid);
	}
	
	/**
	 * 通过bindid获得纪录
	 * @param bindid
	 * @return
	 */
	public DictionaryPO getOneByBindid(String bindid){
		return dicDao.getDicByBindid(bindid);
	}
	
	/**
	 * 通过数据字典类别获取数据字典内容
	 * @param type
	 * @return
	 */
	public List<DictionaryPO> getDictionaryByType(long type){
		return dicDao.getListByType(type);
	}
	
	/**
	 * 获取数据字典所有内容
	 * @return
	 */
	public List<DictionaryPO> getAllDictionary(){
		return dicDao.getListOffAll();
	}
	
	/**
	 * 
	 * @param jsondata  格式：{"id":"0","name":"223","describe":"describe","used":"true","orderIndex":"2","type":""}
	 * @return
	 */
	public int saveDictionary(String jsondata){
		DictionaryModel dictionay = jsonToObject(jsondata);
		//List<DictionaryModel> dataList = new ArrayList<DictionaryModel>();
		//dataList.add(dictionay);
		return saveDictionary(dictionay);
	}
	
	/**
	 * 
	 * @param jsondata  格式：{"id":"0","name":"223","describe":"describe","used":"true","orderIndex":"2","type":""}
	 * @return
	 */
	public String saveOrUpdateDictionary(String jsondata){
		DictionaryModel dictionay = jsonToObject(jsondata);
		//List<DictionaryModel> dataList = new ArrayList<DictionaryModel>();
		//dataList.add(dictionay);
		return saveOrUpdateDictionary(dictionay);
	}
	
	public int deleteDictionary(String ids){
		dicDao.deletes(ids);
		return 1;
	}
	
	public int saveDictionary(List<DictionaryModel> dataList){
		
		return 1;
	}
	
	public String saveOrUpdateDictionary(DictionaryModel dictionary){
		DictionaryPO p = new DictionaryPO(dictionary);
		p.initBindId();
		return dicDao.saveOrUpdate(p);
	}
	
	public int saveOrUpdateDictionaryCategory(DictCategoryModel dictionaryCategory){
		DictCategoryPO p = new DictCategoryPO(dictionaryCategory);
		p.initBindId();
		dictCateDao.saveOrUpdate(p);
		return 1;
	}
	
	public int deleteDictionaryCategory(String ids){
		dictCateDao.deletes(ids);
		return 1;
	}
	
	public int saveDictionary(DictionaryModel dictionary){
		DictionaryPO p = new DictionaryPO(dictionary);
		dicDao.insert(p);
		return 1;
	}
	
	private DictionaryModel jsonToObject(String jsonObject){
		String jsonObject2 = jsonObject.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		DictionaryModel dictionay = (DictionaryModel)JSONObject.toBean(jsonDict, DictionaryModel.class);
		return dictionay;
	}
	
	public static void main(String[] a){
		String s = "{'id':'0','name':'223','describe':'describe','used':'true','orderIndex':'2','type':''}";
		
		DictionaryService service = new DictionaryService();
		service.jsonToObject(s);
	}
}
