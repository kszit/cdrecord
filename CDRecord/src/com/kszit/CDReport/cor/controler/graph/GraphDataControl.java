package com.kszit.CDReport.cor.controler.graph;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.controler.ReportCDSupport;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.model.graph.FunctionCharts;
import com.kszit.CDReport.cor.model.graph.FunctionChartsPic;
import com.kszit.CDReport.cor.model.graph.GraphConfigMainModel;
import com.kszit.CDReport.cor.model.graph.GraphConfigSubModel;
import com.kszit.CDReport.cor.model.graph.GraphData;
import com.kszit.CDReport.cor.service.graph.GraphDataService;

public class GraphDataControl extends ReportCDSupport {
	
	
	
	public String graphDataPage(){

		FunctionCharts functionCharts = new FunctionCharts(graphCMModel.getBindId());
		functionChartsPic = functionCharts.functionChartsPic;
		//functionChartsPic.setXmlData(functionCharts.getChartsXMLData());
		
		return "functionChartsPage";
		
	}
	
	public String getGraphDataAction(){
		 
		FunctionCharts functionCharts = new FunctionCharts(graphCMModel.getBindId());
		super.outText(functionCharts.getChartsXMLData());
		
		return this.SUCCESS;
	}
	
	
	
	
	
	
	
	private FunctionChartsPic functionChartsPic = new FunctionChartsPic();
	
	
	
	public FunctionChartsPic getFunctionChartsPic() {
		return functionChartsPic;
	}

	public void setFunctionChartsPic(FunctionChartsPic functionChartsPic) {
		this.functionChartsPic = functionChartsPic;
	}








		// 报表上报机构配置对象
		private List<Object> selectDepartmentTreeObjects = new ArrayList<Object>();;
		//获取树形机构时，父节点id
		private String id = "";
	
	private GraphConfigMainModel graphCMModel = new GraphConfigMainModel();
	
	private GraphConfigSubModel graphCSModel = new GraphConfigSubModel();
	
	private List<GraphConfigMainModel> graphConfigList = new ArrayList<GraphConfigMainModel>();

	private List<DictionaryPO> graphTypeList = new ArrayList<DictionaryPO>();

	private List<GraphConfigSubModel> graphConfigSubList = new ArrayList<GraphConfigSubModel>();
	
	private String param1;
	private String param2;
	private String param3;

	public List<DictionaryPO> getGraphTypeList() {
		return graphTypeList;
	}

	public void setGraphTypeList(List<DictionaryPO> graphTypeList) {
		this.graphTypeList = graphTypeList;
	}

	public GraphConfigMainModel getGraphCMModel() {
		return graphCMModel;
	}
	public void setGraphCMModel(GraphConfigMainModel graphCMModel) {
		this.graphCMModel = graphCMModel;
	}
	public List<GraphConfigMainModel> getGraphConfigList() {
		return graphConfigList;
	}
	public void setGraphConfigList(List<GraphConfigMainModel> graphConfigList) {
		this.graphConfigList = graphConfigList;
	}

	public List<GraphConfigSubModel> getGraphConfigSubList() {
		return graphConfigSubList;
	}

	public void setGraphConfigSubList(List<GraphConfigSubModel> graphConfigSub) {
		this.graphConfigSubList = graphConfigSub;
	}

	public List<Object> getSelectDepartmentTreeObjects() {
		return selectDepartmentTreeObjects;
	}

	public void setSelectDepartmentTreeObjects(
			List<Object> selectDepartmentTreeObjects) {
		this.selectDepartmentTreeObjects = selectDepartmentTreeObjects;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GraphConfigSubModel getGraphCSModel() {
		return graphCSModel;
	}

	public void setGraphCSModel(GraphConfigSubModel graphCSModel) {
		this.graphCSModel = graphCSModel;
	}

	public String getParam1() {
		return param1;
	}

	public void setParam1(String param1) {
		this.param1 = param1;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public String getParam3() {
		return param3;
	}

	public void setParam3(String param3) {
		this.param3 = param3;
	}
	
	
}
