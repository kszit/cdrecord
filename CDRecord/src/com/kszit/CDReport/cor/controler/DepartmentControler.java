package com.kszit.CDReport.cor.controler;

import com.kszit.CDReport.cor.log.LogService;
import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.model.DeportmentModel;
import com.kszit.CDReport.cor.service.DeportmentService;
import com.opensymphony.xwork2.Action;
import org.apache.commons.logging.Log;


/**
 * 数据填报
 * @author Administrator
 *
 */
public class DepartmentControler extends ReportCDSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7250546382468968987L;
        private static Log logger = LogService.getLogger(DepartmentControler.class);
	
	List<DeportmentModel> departmentList = new ArrayList<DeportmentModel>();
	

	public String getDepartmentJSON(){
		DeportmentService service = new DeportmentService();
		
		departmentList = service.getDeportMentList();
		
		return Action.SUCCESS;
	}
	

	public List<DeportmentModel> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<DeportmentModel> departmentList) {
		this.departmentList = departmentList;
	}
	
	

}
