package com.kszit.CDReport.util;

import com.kszit.CDReport.openserv.department.serviceToReport.DeportmentServiceToReportI;
import com.kszit.CDReport.openserv.role.serviceToReport.RoleServiceToReportI;

public class RoleUtil {

	
	public RoleServiceToReportI getRoleService(){
		RoleServiceToReportI roleService = null;
		try {
			Class<?> myClass = Class.forName(Constants.ROLE_INTERFACE);
			roleService = (RoleServiceToReportI)myClass.newInstance();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return roleService;
	}
}
