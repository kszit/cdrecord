package com.kszit.CDReport.cor.controler;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.DictCategoryPO;
import com.kszit.CDReport.cor.model.DictCategoryModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.opensymphony.xwork2.Action;

/**
 * 数据字典类别
 * <code>Set welcome message.</code>
 */
public class DicCategory extends ReportCDSupport {
	private static final long serialVersionUID = -758096369431782237L;

	private List<DictCategoryModel> dicCateList = new ArrayList<DictCategoryModel>();
	
	private String data;
	
	private String ids;
	
	/**
	 *	extjs 树形结构json编码
	 * @return
	 */
	public String dicCateChilds(){
		DictionaryService service = new DictionaryService();
		String id = super.getRequest().getParameter("id");
		long parentid = Long.parseLong(id);
		List<DictCategoryPO> dictCatePoList = service.getDictCateChilds(parentid);
		Iterator<DictCategoryPO> iter = dictCatePoList.iterator();
		while(iter.hasNext()){
			DictCategoryPO po = iter.next();
			DictCategoryModel dictCate = new DictCategoryModel(po);
			dictCate.setId(po.getBindId().intValue());
			//dictCate.setCls("folder");
			dictCate.setCls("file");
			
			dictCate.setIsleaf(true);
			dicCateList.add(dictCate);
		}
		return Action.SUCCESS;
	}
	/**
	 * ���»����
	 * @return
	 */
	public String saveOrUpdate(){
		DictCategoryModel mode = DictCategoryModel.getCategoryByJson(data);
		DictionaryService service = new DictionaryService();
		service.saveOrUpdateDictionaryCategory(mode);
		super.outText("");
		return "";
	}
	public String delete(){
		DictionaryService service = new DictionaryService();
		service.deleteDictionaryCategory(ids);
		super.outText("");
		return "";
	}
	public List<DictCategoryModel> getDicCateList() {
		return dicCateList;
	}

	public void setDicCateList(List<DictCategoryModel> dicCateList) {
		this.dicCateList = dicCateList;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
