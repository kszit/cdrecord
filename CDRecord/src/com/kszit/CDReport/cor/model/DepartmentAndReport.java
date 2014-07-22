package com.kszit.CDReport.cor.model;
/**
 * 部门和报表的组合，
 * 统计部门的报表上报情况时 使用
 * @author Administrator
 *
 */
public class DepartmentAndReport {
	private String deptBindid;
	
	private String departmentName;
	
	private int allCount;
	
	//已经上报报表数量
	private int appearCount;
	//未上报报表数量
	private int noAppearCount;
	
	public DepartmentAndReport(){}
	
	public DepartmentAndReport(String deptid,String deptName,int appearCount,int noAppearCount){
		this.deptBindid = deptid;
		this.departmentName = deptName;
		this.appearCount = appearCount;
		this.noAppearCount = noAppearCount;
	}
	public String getDeptBindid() {
		return deptBindid;
	}
	public void setDeptBindid(String deptBindid) {
		this.deptBindid = deptBindid;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public int getAppearCount() {
		return appearCount;
	}
	public void setAppearCount(int appearCount) {
		this.appearCount = appearCount;
	}
	public int getNoAppearCount() {
		return noAppearCount;
	}
	public void setNoAppearCount(int noAppearCount) {
		this.noAppearCount = noAppearCount;
	}

	public int getAllCount() {
		allCount = appearCount + noAppearCount;
		return allCount;
	}

	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	
	
}
