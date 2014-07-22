package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.ReportConfigModel;
import com.kszit.CDReport.util.DepartmentUtil;


public class ReportConfigPO extends ParentPO{
        
	private String reportName;

	private String reportNo;
	
	private String rttype;//年报、季报 、月报
	
	private String type;//填报表、汇总表
	
	private String rtyear;
	
	private String rtdeclare;
	
	private int rtversion2;
	
	private Date inputDate;
	
	private String inputUser;
	
	private String userDeptBindid;
	
	private String appearUser;
	
	private String appearDepartment;
	
	private String reportLink;
	
	private Integer reportState;
	
	private Integer aheadOrDelayType;//推迟类型  -1提前上报  1  推迟上报
	
	private Integer aheadOrDelayDates;//推迟或提前天数
	
	//汇总单位列表，单位id之间用|隔开
	private String collectDept;
	
	//查询单位列表，单位id之间用|隔开
	private String queryDept;
	
	public ReportConfigPO(){}
	
	public ReportConfigPO(ReportConfigModel model){
            if(model==null){
                 return;
            }
		try {
			BeanUtils.copyProperties(this,model);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.reportLink = model.getBindId()+"-"+model.getRtversion2();
		if(model.getId()==0){
			this.setId(null);
		}
	}
	
	public ReportConfigPO(ReportConfigPO po){
		try {
			BeanUtils.copyProperties(this,po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setId(null);
	}
	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportNo() {
		return reportNo;
	}

	public void setReportNo(String reportNo) {
		this.reportNo = reportNo;
	}



	public Integer getReportState() {
		return reportState;
	}

	public void setReportState(Integer reportState) {
		this.reportState = reportState;
	}

	public String getRttype() {
		return rttype;
	}

	public void setRttype(String rttype) {
		this.rttype = rttype;
	}

	public String getRtyear() {
		return rtyear;
	}

	public void setRtyear(String rtyear) {
		this.rtyear = rtyear;
	}

	public String getRtdeclare() {
		return rtdeclare;
	}

	public void setRtdeclare(String rtdeclare) {
		this.rtdeclare = rtdeclare;
	}

	

	public Date getInputDate() {
		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputUser() {
		return inputUser;
	}

	public void setInputUser(String inputUser) {
		this.inputUser = inputUser;
	}

	public int getRtversion2() {
		return rtversion2;
	}

	public void setRtversion2(int rtversion2) {
		this.rtversion2 = rtversion2;
	}

	public String getReportBindidLink() {
		return this.bindId+"-"+this.rtversion2;
	}

	public String getUserDeptBindid() {
		return userDeptBindid;
	}

	public void setUserDeptBindid(String userDeptBindid) {
		this.userDeptBindid = userDeptBindid;
	}

	public String getAppearUser() {
		return appearUser;
	}

	public void setAppearUser(String appearUser) {
		this.appearUser = appearUser;
	}

	public String getAppearDepartment() {
		return appearDepartment;
	}

	public void setAppearDepartment(String appearDepartment) {
		this.appearDepartment = appearDepartment;
	}

	public String getReportLink() {
            if(reportLink==null||"0-0".equals(reportLink)){
                this.reportLink = this.bindId+"-"+this.rtversion2;
            }
		return reportLink;
	}

	public void setReportLink(String reportLink) {
		this.reportLink = reportLink;
	}

	public Integer getAheadOrDelayType() {
		return aheadOrDelayType;
	}

	public void setAheadOrDelayType(Integer aheadOrDelayType) {
		this.aheadOrDelayType = aheadOrDelayType;
	}

	public Integer getAheadOrDelayDates() {
		return aheadOrDelayDates;
	}

	public void setAheadOrDelayDates(Integer aheadOrDelayDates) {
		this.aheadOrDelayDates = aheadOrDelayDates;
	}


	public String getCollectDept() {
		return collectDept;
	}

	public void setCollectDept(String collectDept) {
		this.collectDept = collectDept;
	}

	public String getQueryDept() {
		return queryDept;
	}

	public void setQueryDept(String queryDept) {
		this.queryDept = queryDept;
	}
	
	
}
