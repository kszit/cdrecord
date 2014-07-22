package com.kszit.CDReport.cor.model.graph;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigSubPO;
import com.kszit.CDReport.cor.model.ParentModel;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.openserv.department.serviceToReport.DeportmentServiceToReportI;
import com.kszit.CDReport.util.DepartmentUtil;

/**
 * 
 * @author Administrator
 *
 */
public class GraphConfigSubModel extends ParentModel{

	/**
	 * 主表id
	 */
	private Long mainBindid;
	/**
	 * 报表id
	 */
	private String tableLinkBindid;
	/**
	 * 报表名称
	 */
	private String tableName;
	/**
	 * 选择单元格
	 */
	private String tableCells;

	/**
	 * 单期、特定多期、变动多期
	 */
	private int periodsType;
	/**
	 * 多期时，是否累计
	 */
	private boolean isAddUp;
	/**
	 * 开始日期
	 */
	private Date startDay;
	/**
	 * 开始日期
	 */
	private Date endDay;
	/**
	 * 报表来自部门
	 */
	private String deptIds;
	
	/**
	 * 部门名称
	 */
	private String deptNames;
	
	public GraphConfigSubModel(){}
	
	public GraphConfigSubModel(GraphConfigSubPO po){
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
	public Long getMainBindid() {
		return mainBindid;
	}
	public void setMainBindid(Long mainBindid) {
		this.mainBindid = mainBindid;
	}
	public String getTableCells() {
		return tableCells;
	}
	public void setTableCells(String tableCells) {
		this.tableCells = tableCells;
	}
	public int getPeriodsType() {
		return periodsType;
	}
	public void setPeriodsType(int periodsType) {
		this.periodsType = periodsType;
	}
	public boolean getIsAddUp() {
		return isAddUp;
	}
	public void setIsAddUp(boolean isAddUp) {
		this.isAddUp = isAddUp;
	}
	
	public Date getStartDay() {
		return startDay;
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public String getDeptIds() {
		return deptIds;
	}
	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getTableName() {
		if(tableName==null && tableLinkBindid!=null){
			ReportConfigPO reportConfig = ReportConfigService.getReportConfigPO(this.tableLinkBindid+"");
			if(reportConfig!=null){
				tableName = reportConfig.getReportName();
				//reportType = reportConfig.getRttype();
				//reportVersion = reportConfig.getRtversion2()+"";
			}
		}
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableLinkBindid() {
		return tableLinkBindid;
	}

	public void setTableLinkBindid(String tableLinkBindid) {
		this.tableLinkBindid = tableLinkBindid;
	}

	public String getDeptNames() {
		if(deptNames==null && deptIds!=null && !deptIds.equals("")){
			if(deptIds.contains("|")){
				String[] deptids = deptIds.split("\\|");
				DeportmentServiceToReportI deptS = DepartmentUtil.instance().getDepartmentService();
				deptNames = "";
				for(String s:deptids){
					if(s!=null && !"".equals(s)){
						deptNames += deptS.getDepartmentById(s).getDepartmentName()+",";
					}
				}
			}
		}
		return deptNames;
	}

	public void setDeptNames(String deptNames) {
		this.deptNames = deptNames;
	}
	
	

}
