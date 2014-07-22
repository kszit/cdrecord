package com.kszit.CDReport.cor.ui.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.ui.IUIModel;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;

public class DepartmentListOfLeftCell implements IUIModel {

	@Override
	public Map<String, String> tableLeftOfDynamicsCell() {
		Map<String, String> return_map = new HashMap<String,String>();
		
		List<DepartmentToReport> departList = new DepartmentUtil().getDepartmentService().getDepartmentList("0");
		for(DepartmentToReport po:departList){
			return_map.put(po.getBindid()+"", po.getDepartmentName());
		}
		return return_map;
	}

}
