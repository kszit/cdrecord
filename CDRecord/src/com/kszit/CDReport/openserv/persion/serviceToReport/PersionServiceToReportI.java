package com.kszit.CDReport.openserv.persion.serviceToReport;

import javax.servlet.http.HttpSession;

import com.kszit.CDReport.openserv.persion.model.PersionToReport;

public interface PersionServiceToReportI {

	public PersionToReport getCurrentUser(HttpSession session);
	
	public PersionToReport getUserById(String id);
	
	
}
