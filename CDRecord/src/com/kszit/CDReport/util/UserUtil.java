package com.kszit.CDReport.util;

import com.kszit.CDReport.openserv.persion.serviceToReport.PersionServiceToReportI;


public class UserUtil {
	public PersionServiceToReportI getUserService(){
		PersionServiceToReportI userService = null;
		try {
			Class<?> myClass = Class.forName(Constants.USER_INTERFACE);
			userService = (PersionServiceToReportI)myClass.newInstance();
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
		return userService;
	}
}
