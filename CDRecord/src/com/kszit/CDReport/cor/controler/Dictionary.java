package com.kszit.CDReport.cor.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.model.DictionaryModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;

/**
 * <code>Set welcome message.</code>
 */
public class Dictionary extends ReportCDSupport {
	private static final long serialVersionUID = -758096369431782237L;

	private String data;
	
	private String type;
	
	private String deleteids;
	
	private Map<String,Object> dictMap = new HashMap<String,Object>();

	public String listByJson(){
		List<DictionaryModel> dicList = new ArrayList<DictionaryModel>();
		
		DictionaryService service = new DictionaryService();
		List<DictionaryPO> poList = service.getDictionaryByType(Long.parseLong(type));
		
		Iterator<DictionaryPO> iter = poList.iterator();
		while(iter.hasNext()){
			DictionaryPO po = iter.next();
			dicList.add(new DictionaryModel(po));
		}
		
		dictMap.put("totalCount", new Integer(11));
		dictMap.put("data", dicList);
		return Action.SUCCESS;
		//return "{totalCount:11,data:[{'id':'0','name':'223','describe':'describe','used':'true','orderIndex':'2','type':''}]}";
	}
	
	/**
	 * 通过具体类别纪录，获得与此纪录相同类别的其他记录
	 * @return
	 */
	public String listByJsonByBindid(){
		String reportLinkBindid = type;
		ReportConfigService confService = new ReportConfigService();
		ReportConfigPO configPo = confService.getReportConfigByBindidLink(reportLinkBindid);
		
		
		type = StaticVaribles.changePeriodToPeriodDataBindid(Integer.parseInt(configPo.getRttype()))+"";
		return listByJson();
	}
	
	/**
	 * 
	 * @return
	 */
	public String save(){
		getResponse().setCharacterEncoding("utf-8");
		PrintWriter pw=null;
		try {
			DictionaryService service = new DictionaryService();
			service.saveDictionary(this.data);
			pw = getResponse().getWriter();
			pw.write("JSON");
		} catch (IOException e) {
			
		}
	
		pw.flush();
		pw.close();
		return null;

	}
	/**
	 * 
	 * @return
	 */
	public String delete(){
		DictionaryService service = new DictionaryService();
		service.deleteDictionary(this.deleteids);
		super.outText("");
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String saveOrUpdate(){
		DictionaryService service = new DictionaryService();
		String id = service.saveOrUpdateDictionary(this.data);
		super.outText(id);
		return null;

	}
	
	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getDictMap() {
		return dictMap;
	}
	public void setDictMap(Map<String, Object> dictMap) {
		this.dictMap = dictMap;
	}

	public String getDeleteids() {
		return deleteids;
	}

	public void setDeleteids(String deleteids) {
		this.deleteids = deleteids;
	}

}
