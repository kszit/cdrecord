package com.kszit.CDReport.openserv.department.serviceToReport;

import com.kszit.CDReport.cor.model.persionDepRole.Department2Model;
import com.kszit.CDReport.cor.service.persionDepRole.DepartmentService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.openserv.persion.model.PersionToReport;
import com.kszit.CDReport.openserv.persion.serviceToReport.MyPersionServiceToReportInCom;
import com.kszit.CDReport.openserv.persion.serviceToReport.PersionServiceToReportI;
import javax.servlet.http.HttpSession;

public class MyDeportmentServiceToReportInCom implements DeportmentServiceToReportI {

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
                
                PersionServiceToReportI persionS = new MyPersionServiceToReportInCom();
                PersionToReport persion = persionS.getCurrentUser(session);
                
                DepartmentService deptService = new DepartmentService();
                Department2Model deptModel = deptService.getDeptByBindid(persion.getUserDepId());
                
                return toDepartmentToReport(deptModel);
	}

	public DepartmentToReport getDepartmentById(String bindid) {
		DepartmentService deptService = new DepartmentService();
		Department2Model model = deptService.getDeptByBindid(bindid);
		return toDepartmentToReport(model);
	}

	
	public List<DepartmentToReport> getDepartmentListOfChild(String bindid){
		List<DepartmentToReport> departmentList = new ArrayList<DepartmentToReport>();

        DepartmentService deptService = new DepartmentService();
        List<Department2Model> depts = deptService.getDeptByParentId("0");
        
        for(Department2Model model:depts){
        	if(model.getName().contains("集团")){
        		continue;
        	}
            departmentList.add(toDepartmentToReport(model));
        }

return departmentList;
	}
	public List<DepartmentToReport> getDepartmentList(String parentBindid) {
		List<DepartmentToReport> departmentList = new ArrayList<DepartmentToReport>();

                DepartmentService deptService = new DepartmentService();
                List<Department2Model> depts = deptService.getDeptByParentId("0");
                
                for(Department2Model model:depts){
                    departmentList.add(toDepartmentToReport(model));
                }

		return departmentList;
	}
	
	private DepartmentToReport toDepartmentToReport(Department2Model model){
            DepartmentToReport dept = new DepartmentToReport();
            dept.setBindid(model.getBindId()+"");
            dept.setDepartmentName(model.getName());
           dept.setOrgOrder(model.getOrderIndex());
            return dept;
        }

}
