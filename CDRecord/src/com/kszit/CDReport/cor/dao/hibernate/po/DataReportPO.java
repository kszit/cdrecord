package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;

import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.util.beanUtilEx.BeanUtilEx;


public class DataReportPO extends ParentPO{

	public final static int PASS = 1;
	public final static int PASS_NOt = 0;
	
	private String repotBindid;
	
	private String year;
	
	private String periods;

	private String madeManName;//创建人
	private Date madeManDate = null;//创建日期
	
	private String verifyManName;//审核人
	private Date verifyManDate = null;//审核日期
	
	private String updateManName;//更新人
	private Date updateDate = null;//更新日期

	private Date appearDate = null;//发布日期
	
	
	
	/**
	 * 报表创建单位bindid
	 */
	private String createDepBindid;
	
	/**
	 * 流程状态
	 */
	private Integer verify = 0;
	
	public DataReportPO(){}
	
	public DataReportPO(DataReportModel model){
		try {
			BeanUtilEx.copyProperties(this,model);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(model.getId()==0){
			this.setId(null);
		}
	}

	

	public String getRepotBindid() {
		return repotBindid;
	}

	public void setRepotBindid(String repotBindid) {
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

	public Integer getVerify() {
		return verify;
	}

	public void setVerify(Integer verify) {
		this.verify = verify;
	}

	public Date getAppearDate() {
		return appearDate;
	}

	public void setAppearDate(Date appearDate) {
		this.appearDate = appearDate;
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

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}


}
