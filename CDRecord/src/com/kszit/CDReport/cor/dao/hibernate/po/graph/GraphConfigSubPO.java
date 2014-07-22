package com.kszit.CDReport.cor.dao.hibernate.po.graph;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.ParentPO;
import com.kszit.CDReport.cor.model.graph.GraphConfigSubModel;

public class GraphConfigSubPO extends ParentPO {

	/**
	 * 主表id
	 */
	private Long mainBindid;
	/**
	 * 报表id
	 */
	private String tableLinkBindid;
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
	
	
	public GraphConfigSubPO(){}
	
	public GraphConfigSubPO(GraphConfigSubModel model){
		try {
			BeanUtils.copyProperties(this,model);
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
	
	public String getTableLinkBindid() {
		return tableLinkBindid;
	}

	public void setTableLinkBindid(String tableLinkBindid) {
		this.tableLinkBindid = tableLinkBindid;
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
	public String getStartDaySql() {
		return super.getDaySqlFileValue(startDay);
	}

	public void setStartDay(Date startDay) {
		this.startDay = startDay;
	}

	public Date getEndDay() {
		return endDay;
	}
	public String getEndDaySql() {
		return super.getDaySqlFileValue(endDay);
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
	
	
	
	
}
