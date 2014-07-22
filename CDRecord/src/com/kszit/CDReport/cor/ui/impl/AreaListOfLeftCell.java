package com.kszit.CDReport.cor.ui.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.ui.IUIModel;

public class AreaListOfLeftCell implements IUIModel {

	@Override
	public Map<String, String> tableLeftOfDynamicsCell() {
		Map<String, String> return_map = new HashMap<String,String>();
		DictionaryService dictService = new DictionaryService();
		List<DictionaryPO> dicList = dictService.getDictionaryByType(2131L);
		for(DictionaryPO po:dicList){
			return_map.put(po.getBindId()+"", po.getName());
		}
		return return_map;
	}

}
