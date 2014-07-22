package com.kszit.CDReport.cor.model.graph;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.model.ParentModel;
import com.kszit.CDReport.openserv.department.serviceToReport.DeportmentServiceToReportI;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.beanUtilEx.BeanUtilEx;

/**
 * 
 * @author Administrator
 *
 */
public class GraphConfigMainModel extends ParentModel{

	/**
	 * 
	 */
	private String name;
	/**
	 * 图表类型
	 */
	private int graphType;
	
	private String graphTypeName;
	
	private String XAxis;
	
	private String YAxis;
	/**
	 * 报表来自部门
	 */
	private String deptIds;
	
	/**
	 * 部门名称
	 */
	private String deptNames;
	/**
	 * 开始日期
	 */
	private Date startDay;
	/**
	 * 开始日期
	 */
	private Date endDay;
	
	private String createUserId;
	
	private Date createDate;
	/**
	 * 可查看报表的单位
	 */
	private String canQueryDepts;
	
	public GraphConfigMainModel(){}
	public GraphConfigMainModel(GraphConfigMainPO po){
		try {
			BeanUtilEx.copyProperties(this,po);
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
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getGraphTypeName() {
		if(graphTypeName==null && graphType!=0){
			graphTypeName = StaticVaribles.getDicContent(this.graphType+"");
		}
		return graphTypeName;
	}
	public void setGraphTypeName(String graphTypeName) {
		this.graphTypeName = graphTypeName;
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
