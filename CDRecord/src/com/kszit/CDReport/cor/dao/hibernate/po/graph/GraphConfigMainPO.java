package com.kszit.CDReport.cor.dao.hibernate.po.graph;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.ParentPO;
import com.kszit.CDReport.cor.model.graph.GraphConfigMainModel;

public class GraphConfigMainPO extends ParentPO {

	/**
	 * 
	 */
	private String name;
	/**
	 * 图表类型
	 */
	private int graphType;
	
	private String XAxis;
	
	private String YAxis;
	
	private String createUserId;
	
	private Date createDate;
	/**
	 * 报表来自部门
	 */
	private String deptIds;
	/**
	 * 开始日期
	 */
	private Date startDay;
	/**
	 * 开始日期
	 */
	private Date endDay;
	/**
	 * 可查看报表的单位
	 */
	private String canQueryDepts;

	
	
	public GraphConfigMainPO(){}
	
	public GraphConfigMainPO(GraphConfigMainModel po){
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
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGraphType() {
		return graphType;
	}
	public void setGraphType(int graphType) {
		this.graphType = graphType;
	}
	public String getXAxis() {
		return XAxis;
	}
	public void setXAxis(String xAxis) {
		XAxis = xAxis;
	}
	public String getYAxis() {
		return YAxis;
	}
	public void setYAxis(String yAxis) {
		YAxis = yAxis;
	}
	public String getCanQueryDepts() {
		return canQueryDepts;
	}
	public void setCanQueryDepts(String canQueryDepts) {
		this.canQueryDepts = canQueryDepts;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getCreateDateSql() {
		return super.getDaySqlFileValue(createDate);
		
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getStartDay() {
		return startDay;
	}
	
	public String getStartDaySql() {
		if(startDay==null){
			return "null";
		}else{
			return "'"+getStartDay()+"'";
		}
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
	
	
	public String getEndDaySql() {
		if(endDay==null){
			return "null";
		}else{
			return "'"+getEndDay()+"'";
		}
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}
	
}
