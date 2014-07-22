package com.kszit.CDReport.cor.model;

import com.kszit.CDReport.util.Constants;
import com.kszit.CDReport.util.DepartmentUtil;


/**
 * 报表上报情况
 * @author Administrator
 *
 */
public class ReportAppareModel extends ParentModel{
	
	private String depName;
	
	private String appearStateStr;
	
	private String seeDataUrl;
	
	private ReportConfigModel reportConfigModel;
	
	private DataReportModel dataReportModel;
	
	public ReportAppareModel(){}

	public String getDepName() {
		if(depName==null && dataReportModel!=null){
			depName = new DepartmentUtil().getDepartmentService().getDepartmentById(dataReportModel.getCreateDepBindid()).getDepartmentName();
		}
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public ReportConfigModel getReportConfigModel() {
		return reportConfigModel;
	}

	public void setReportConfigModel(ReportConfigModel reportConfigModel) {
		this.reportConfigModel = reportConfigModel;
	}

	public DataReportModel getDataReportModel() {
		return dataReportModel;
	}

	public void setDataReportModel(DataReportModel dataReportModel) {
		this.dataReportModel = dataReportModel;
	}

	public String getAppearStateStr() {
		if(appearStateStr==null && dataReportModel!=null){
			if(dataReportModel.getBindId()!=0){
				if(dataReportModel.getVerify()==0){
					appearStateStr = "上报中";
				}else{
					seeDataUrl = Constants.APP_NAME+"/reportData/reportData_seePage.do?dataReportModel.repotBindid="+dataReportModel.getRepotBindid()+"&dataReportModel.bindId="+dataReportModel.getBindId();
					appearStateStr = "<a href=\""+seeDataUrl+" target='_blank'\">已上报</a>";
				}
			}else{
				appearStateStr = "未上报";
			}
		}
		return appearStateStr;
	}

	public String getSeeDataUrl() {
		return seeDataUrl;
	}
	
	

}
