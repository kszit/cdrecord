package com.kszit.CDReport.cor.service;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;

public class DictionaryHtmlService {
	
	DictionaryService dictService = null;
	public DictionaryHtmlService(){
		dictService = new DictionaryService();
	}
	/**
	 * 数据字 下拉列表
	 * @param parentid
	 * @return
	 */
	public String getDictionaryByType(long type,String name,String dipaly){
		StringBuffer sb = new StringBuffer();
		List<DictionaryPO> dicList = dictService.getDictionaryByType(type);
		sb.append("<select name='"+name+"' id='"+name+"' style='display: "+dipaly+";'>");
		for(DictionaryPO po:dicList){
			sb.append("<option value='"+po.getBindId()+"'>"+po.getName()+"</option>");
		}
		sb.append("</select>");
		return sb.toString();
	}
}
