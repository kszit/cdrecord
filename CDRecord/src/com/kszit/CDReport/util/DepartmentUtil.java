package com.kszit.CDReport.util;

import com.kszit.CDReport.openserv.department.serviceToReport.DeportmentServiceToReportI;

public class DepartmentUtil {

	private static DepartmentUtil departmentUtil = null;
	
	public static DepartmentUtil instance(){
		if(departmentUtil ==null){
			departmentUtil = new DepartmentUtil();
		}
		return departmentUtil;
	}
	
	
	
	public DeportmentServiceToReportI getDepartmentService(){
		DeportmentServiceToReportI departmentService = null;
		departmentService = (DeportmentServiceToReportI)ClassInstallUtil.installClass(Constants.DEPARTMENT_INTERFACE);
		
		return departmentService;
	}
}
