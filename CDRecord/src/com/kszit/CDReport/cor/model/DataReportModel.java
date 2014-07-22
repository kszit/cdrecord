package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;

import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.persionDepRole.DepartmentService;
import com.kszit.CDReport.cor.service.persionDepRole.PersionService;
import com.kszit.CDReport.util.DateUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.beanUtilEx.BeanUtilEx;

/**
 * 上报报表记录对象
 * @author Administrator
 *
 */
public class DataReportModel extends ParentModel{
	
	private String repotBindid;
	
	private String year;
	
	private String periods;//期限代码
	
	private String yearOfDateReport;//日报的填报页面，
	
	private String periodsName;//期限代码名称
	
	private String reportType;
	
	private String rttypeName;
	
	private String reportName;
	
	private String reportVersion;

	//制表人id
	private String madeManName;
	//审核人id
	private String verifyManName;
	//填报人姓名
	private String madeManName2;
	//审核人姓名
	private String verifyManName2;
	
	private Date appearDate = null;
	
	private Date madeManDate = null;

	private Date verifyManDate = null;
	
	private String createDepBindid;
	
	private String createDepName;
	
	private String updateManName;//更新id
	private String updateManName2;//更新人姓名
	private Date updateDate = null;//更新日期
	/**
	 * 流程状态
	 */
	private int verify = 0;
	
	private String verifyName;
	
	public DataReportModel(){}
	
	public DataReportModel(DataReportPO po){
		try {
			if(po != null){
				BeanUtilEx.copyProperties(this,po);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public DataReportModel(DataReportModel po){
		try {
			if(po != null){
				BeanUtilEx.copyProperties(this,po);
			}
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	

	public String getRepotBindid() {
		return repotBindid;
	}

	public void setRepotBindid(String repotBindid) {
		ReportConfigPO reportConfig = ReportConfigService.getReportConfigPO(repotBindid+"");
		if(reportConfig!=null){
			reportName = reportConfig.getReportName();
			reportType = reportConfig.getRttype();
			reportVersion = reportConfig.getRtversion2()+"";
		}
		this.repotBindid = repotBindid;
	}

	public String getMadeManName() {
		return madeManName;
	}

	public void setMadeManName(String madeManName) {
		this.madeManName = madeManName;
	}

	public String getVerifyManName() {
		return verifyManName;
	}

	public void setVerifyManName(String verifyManName) {
		this.verifyManName = verifyManName;
	}

	public Date getMadeManDate() {
		return madeManDate;
	}

	public void setMadeManDate(Date madeManDate) {
		this.madeManDate = madeManDate;
	}

	public Date getVerifyManDate() {
		return verifyManDate;
	}

	public void setVerifyManDate(Date verifyManDate) {
		this.verifyManDate = verifyManDate;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	public String getRttypeName() {
		if(reportType!=null && !"".equals(reportType)){
			if(rttypeName==null){
				rttypeName = StaticVaribles.getDicContent(reportType);
			}
		}
		return rttypeName;
	}

	public String getPeriodsName() {
		if(periodsName==null){
			
			String dateReport = getYearOfDateReport();
			if(dateReport!=null){
				if(dateReport!=null){
					periodsName = dateReport.substring(dateReport.indexOf("-")+1);
				}
			}else{
				periodsName = StaticVaribles.getDicContent(periods);
			}
			
			
		}
		return periodsName;
	}
	
	

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getYearOfDateReport() {
		String reportLink = this.getRepotBindid();
		ReportConfigService rcService = new ReportConfigService();
		ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportLink);
		
		String rttype = reportConfig.getRttype();
		if(rttype.equals(""+StaticVaribles.ReportFreq_DayBindId)){//天报
			yearOfDateReport = DateUtil.getDateByDaysOfYear(this.year,Integer.parseInt(this.periods));
			//periods = DateUtil.getDatesOfYear(year)+"";
			//this.year = year.split("-")[0];
			//year = "2011-09-03";
		}
		return yearOfDateReport;
	}

	public void setYearOfDateReport(String yearOfDateReport) {
		if(yearOfDateReport.contains("-")){//
			String reportLink = this.getRepotBindid();
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportLink);
			
			String rttype = reportConfig.getRttype();
			if(rttype.equals(""+StaticVaribles.ReportFreq_DayBindId)){//天报
				periods = DateUtil.getDatesOfYear(yearOfDateReport)+"";
				this.year = yearOfDateReport.split("-")[0];
			}
		}
		this.yearOfDateReport = yearOfDateReport;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportVersion() {
		return reportVersion;
	}

	public void setReportVersion(String reportVersion) {
		this.reportVersion = reportVersion;
	}

	public void setRttypeName(String rttypeName) {
		this.rttypeName = rttypeName;
	}

	public String getPeriods() {
		return periods;
	}

	public void setPeriods(String periods) {
		this.periods = periods;
	}

	public String getCreateDepBindid() {
		return createDepBindid;
	}

	public void setCreateDepBindid(String createDepBindid) {
		this.createDepBindid = createDepBindid;
	}

	public int getVerify() {
		return verify;
	}

	public void setVerify(int verify) {
		this.verify = verify;
	}

	public Date getAppearDate() {
		return appearDate;
	}

	public void setAppearDate(Date appearDate) {
		this.appearDate = appearDate;
	}

	public String getMadeManName2() {
		if(madeManName2==null){
			madeManName2 = new PersionService().getPersionByBindid(this.madeManName).getName();
		}
		return madeManName2;
	}

	public void setMadeManName2(String madeManName2) {
		this.madeManName2 = madeManName2;
	}

	public String getVerifyManName2() {
		if(verifyManName2==null){
			verifyManName2 = new PersionService().getPersionByBindid(this.verifyManName).getName();
		}
		return verifyManName2;
	}

	public void setVerifyManName2(String verifyManName2) {
		this.verifyManName2 = verifyManName2;
	}

	public String getCreateDepName() {
		if(createDepName==null){
			createDepName = new DepartmentService().getDeptByBindid(this.createDepBindid).getName();
		}
		return createDepName;
	}

	public void setCreateDepName(String createDepName) {
		this.createDepName = createDepName;
	}

	public String getVerifyName() {
		if(verifyName==null){
			verifyName = StaticVaribles.getDicContent(verify+"");
		}
		return verifyName;
	}

	public String getUpdateManName() {
		return updateManName;
	}

	public void setUpdateManName(String updateManName) {
		this.updateManName = updateManName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateManName2() {
		if(updateManName2==null){
			updateManName2 = new PersionService().getPersionByBindid(this.updateManName).getName();
		}
		return updateManName2;
	}

	public void setPeriodsName(String periodsName) {
		this.periodsName = periodsName;
	}

	public void setUpdateManName2(String updateManName2) {
		this.updateManName2 = updateManName2;
	}

	public void setVerifyName(String verifyName) {
		this.verifyName = verifyName;
	}
	
	

}
