package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.util.DateUtil;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 配置表格的表头model
 * @author Administrator
 *
 */
public class ReportConfigModel extends ParentModel{

	private String reportName;

	private String reportNo;
	
	private String rttype;
	
	private String type;//填报表、汇总表
	
	private String rttypeName;
	
	private String rtyear;
	
	private String rtdeclare;

	//版本号
	private int rtversion2;
	
	private Date inputDate = new Date();
	
	private String inputUser;
	
	private String userDeptBindid;
	
	private String appearUser;
	
	private String appearDepartment;
	
	private Integer reportState;
	
	private String reportStateStr;
	
	private String reportLink = null;
	
	
	private Integer aheadOrDelayType;//推迟类型  -1提前上报  1  推迟上报
	
	private Integer aheadOrDelayDates;//推迟或提前天数
	
	
	//汇总单位列表，单位id之间用|隔开
	private String collectDept;
	
	//查询单位列表，单位id之间用|隔开
	private String queryDept;
	
	
	
	public ReportConfigModel(){}
	
	public ReportConfigModel(ReportConfigPO po){
		try {
			BeanUtils.copyProperties(this,po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	
	public String getReportStateStr() {
		if(reportStateStr==null){
			reportStateStr = StaticVaribles.getDicContent(this.reportState.intValue()+"");
		}
		return reportStateStr;
	}

	public Integer getReportState() {
		return reportState;
	}

	public void setReportState(Integer reportState) {
		this.reportState = reportState;
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

	public String getRttypeName() {
		if(rttype!=null && !"".equals(rttype)){
			if(rttypeName==null){
				rttypeName = StaticVaribles.getDicContent(rttype);
			}
		}
		return rttypeName;
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
		if(reportLink==null){
			reportLink = this.bindId+"-"+this.rtversion2;
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
