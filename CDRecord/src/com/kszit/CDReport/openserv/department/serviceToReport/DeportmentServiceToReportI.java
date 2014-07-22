package com.kszit.CDReport.openserv.department.serviceToReport;

import java.util.List;

import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import javax.servlet.http.HttpSession;

public interface DeportmentServiceToReportI {

	public DepartmentToReport getCurrentDepartment(HttpSession session);
	
	public DepartmentToReport getDepartmentById(String bindid);
	
	public List<DepartmentToReport> getDepartmentList(String parentBindid);
	
	public List<DepartmentToReport> getDepartmentListOfChild(String parentBindid);
	
}
