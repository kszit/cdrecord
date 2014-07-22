package com.kszit.CDReport.openserv.department.serviceToReport;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import javax.servlet.http.HttpSession;

public class MyDeportmentServiceToReport implements DeportmentServiceToReportI {

	public static Map<String,DepartmentToReport> depMap = new HashMap<String,DepartmentToReport>();
	
	static{
		DepartmentToReport depart = new DepartmentToReport();
		depart.setOrgOrder(1);
		depart.setBindid("1001");
		depart.setDepartmentName("水电一局");
		
		DepartmentToReport depart2 = new DepartmentToReport();
		depart2.setOrgOrder(2);
		depart2.setBindid("1002");
		depart2.setDepartmentName("水电二局");
		
		DepartmentToReport depart3 = new DepartmentToReport();
		depart3.setOrgOrder(3);
		depart3.setBindid("1003");
		depart3.setDepartmentName("水电三局");
		
		DepartmentToReport depart4 = new DepartmentToReport();
		depart4.setOrgOrder(4);
		depart4.setBindid("1004");
		depart4.setDepartmentName("水电四局");
		
		DepartmentToReport depart5 = new DepartmentToReport();
		depart5.setOrgOrder(5);
		depart5.setBindid("1005");
		depart5.setDepartmentName("水电五局");
		
		DepartmentToReport depart6 = new DepartmentToReport();
		depart6.setOrgOrder(6);
		depart6.setBindid("1006");
		depart6.setDepartmentName("水电六局");
		
		DepartmentToReport depart7 = new DepartmentToReport();
		depart7.setOrgOrder(7);
		depart7.setBindid("1007");
		depart7.setDepartmentName("水电七局");
		
		DepartmentToReport depart8 = new DepartmentToReport();
		depart8.setOrgOrder(8);
		depart8.setBindid("1008");
		depart8.setDepartmentName("水电八局");
		
		DepartmentToReport depart9 = new DepartmentToReport();
		depart9.setOrgOrder(9);
		depart9.setBindid("1009");
		depart9.setDepartmentName("水电九局");
		
		DepartmentToReport depart10 = new DepartmentToReport();
		depart10.setOrgOrder(10);
		depart10.setBindid("1010");
		depart10.setDepartmentName("水电十局");
		
		depMap.put(depart.getBindid(), depart);
		depMap.put(depart2.getBindid(), depart2);
		depMap.put(depart3.getBindid(), depart3);
		depMap.put(depart4.getBindid(), depart4);
		depMap.put(depart5.getBindid(), depart5);
		depMap.put(depart6.getBindid(), depart6);
		depMap.put(depart7.getBindid(), depart7);
		depMap.put(depart8.getBindid(), depart8);
		depMap.put(depart9.getBindid(), depart9);
		depMap.put(depart10.getBindid(), depart10);
		
	}
	
	public DepartmentToReport getCurrentDepartment(HttpSession session) {
		int index = (int)(Math.random()*10);
		return depMap.get("100"+index);
	}

	public DepartmentToReport getDepartmentById(String bindid) {
		return depMap.get(bindid);
	}
public List<DepartmentToReport> getDepartmentListOfChild(String bindid){
		return null;
	}
	public List<DepartmentToReport> getDepartmentList(String parentBindid) {
		List<DepartmentToReport> departmentList = new ArrayList<DepartmentToReport>();

		departmentList.addAll(depMap.values());

		return departmentList;
	}
	
	

}
