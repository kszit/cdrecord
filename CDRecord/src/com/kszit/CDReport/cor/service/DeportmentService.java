package com.kszit.CDReport.cor.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.kszit.CDReport.cor.model.DeportmentModel;
import com.kszit.CDReport.cor.model.util.ModelSortComparator;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;

public class DeportmentService {
	
	public List<DeportmentModel> getDeportMentList(){
		List<DeportmentModel> deportmentModel = new ArrayList<DeportmentModel>();
		List<DepartmentToReport> department2 = new DepartmentUtil().getDepartmentService().getDepartmentList("");
		for(DepartmentToReport dep:department2){
			DeportmentModel depModel = new DeportmentModel();
			depModel.setOrderIndex(dep.getOrgOrder());
			depModel.setText(dep.getDepartmentName());
			depModel.setId(Integer.parseInt(dep.getBindid()));
			depModel.setCls("file");
			depModel.setIsleaf(true);
			deportmentModel.add(depModel);
		}
		Collections.sort(deportmentModel,new ModelSortComparator());
		return deportmentModel;
	}
	
	
	
}
