package com.kszit.CDReport.cor.ui.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.model.atm.WebSitModel;
import com.kszit.CDReport.cor.service.atm.WebSitService;
import com.kszit.CDReport.cor.ui.IUIModel;

public class AtmWebsitOfLeftCell implements IUIModel {

	/**
	 * 
	* (non-Javadoc)  
	* @see com.kszit.CDReport.cor.ui.IUIModel#tableLeftOfDynamicsCell()
	 */
	@Override
	public Map<String, String> tableLeftOfDynamicsCell() {
		Map<String, String> return_map = new HashMap<String,String>();
		
		WebSitService wsService = new WebSitService();
		List<WebSitModel> wsList = wsService.getAllWebsits();
		
		for(WebSitModel website:wsList){
			
			return_map.put(website.getBindId()+"", website.getWsName());
		}
		return return_map;
	}

}
