package com.kszit.CDReport.cor.controler.graph;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.controler.ReportCDSupport;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigSubPO;
import com.kszit.CDReport.cor.model.graph.GraphConfigMainModel;
import com.kszit.CDReport.cor.model.graph.GraphConfigSubModel;
import com.kszit.CDReport.cor.model.persionDepRole.Department2Model;
import com.kszit.CDReport.cor.model.persionDepRole.Department2ModelWithCheck;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.graph.GraphConfigMainService;
import com.kszit.CDReport.cor.service.graph.GraphConfigSubService;
import com.kszit.CDReport.cor.service.persionDepRole.DepartmentService;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;

public class GraphConfig extends ReportCDSupport {
	
	
	
	public String configListPage(){
		GraphConfigMainService gService = new GraphConfigMainService();
		graphConfigList = gService.getAllGraphConfigOfModel();

		return "configListPage";
	}
	
	public String inputConfigPage(){
		DictionaryService dicService = new DictionaryService();
		graphTypeList = dicService.getDictionaryByType(StaticVaribles.GraphType);
		
		
		if(graphCMModel.getBindId()!=0){
			GraphConfigMainService gService = new GraphConfigMainService();
			graphCMModel = gService.getGrapConfigByBindIdOfModel(graphCMModel.getBindId());
			
			GraphConfigSubService subService = new GraphConfigSubService();
			graphConfigSubList = subService.getGraphConfigSubOfMainBindidModel(graphCMModel.getBindId());
		}
		
		return "inputConfigPage";
	}
	
	public String insertAction(){
		GraphConfigMainService gService = new GraphConfigMainService();
		String tableBindid = gService.insertOrUpdate(graphCMModel);
		
		this.addActionMessage(tableBindid);
		return Action.SUCCESS;
	}
	
	public String insertGraphSubAction(){
		GraphConfigSubService subService = new GraphConfigSubService();
		subService.insertOrUpdate(graphCSModel);
		super.outText("suc");
		return "";
	}
	
	public String deleteGraphSubAction(){
		GraphConfigSubService subService = new GraphConfigSubService();
		subService.delete(graphCSModel.getBindId());
		super.outText("suc");
		return "";
	}
	
	public String getGraphSubAction(){
		GraphConfigSubService subService = new GraphConfigSubService();
		GraphConfigSubModel model = subService.getGrapConfigByBindIdOfModel(graphCSModel.getBindId());
		 
		
		
		String graphSubId = model.getId()+"";
		String graphSubBindid = model.getBindId()+"";
		String graphSubdeptIds = model.getDeptIds();
		String graphSubdeptNames = model.getDeptNames();
		String graphSubTableBindid = model.getTableLinkBindid();
		String graphSubTableSelectCell = model.getTableCells();
		String graphSubStartDay = model.getStartDay()+"";
		String graphSubEndDay = model.getEndDay()+"";
		
		StringBuffer retunr_json = new StringBuffer();
		retunr_json.append("{");
		retunr_json.append("'graphSubId':'"+graphSubId+"',");
		retunr_json.append("'graphSubBindid':'"+graphSubBindid+"',");
		retunr_json.append("'graphSubdeptIds':'"+graphSubdeptIds+"',");
		retunr_json.append("'graphSubdeptNames':'"+graphSubdeptNames+"',");
		retunr_json.append("'graphSubTableBindid':'"+graphSubTableBindid+"',");
		retunr_json.append("'graphSubTableSelectCell':'"+graphSubTableSelectCell+"',");
		retunr_json.append("'graphSubStartDay':'"+graphSubStartDay+"',");
		retunr_json.append("'graphSubEndDay':'"+graphSubEndDay+"'");
		
		
		retunr_json.append("}");
		super.outText(retunr_json.toString());
		return "";
	}
	
	
	public String deletesConfigAction(){
		GraphConfigMainService gService = new GraphConfigMainService();
		gService.delete(graphCMModel.getBindId());
		return null;
	}
	/**
	 * 
	 * @return
	 */
	public String setDeptOfReportMain(){
		return treeDeptWithCheckbox(param1);
	}
	/**
	 * 
	 * @return
	 */
	public String setDeptOfReportSub(){
		
		return treeDeptWithCheckbox(param1);
	}
	
	/**
	 * 图表可查询单位
	 * 
	 * url:/graphConfig_json/setQueryDepartment.do?graphCMModel.bindId=&id=
	 *
	 * @return
	 */
	public String setQueryDepartment() {
		GraphConfigMainService gService = new GraphConfigMainService();
		GraphConfigMainPO po = gService.getGrapConfigByBindId(graphCMModel.getBindId());
		
		String appearDeptIdStrs = po.getCanQueryDepts();
		return treeDeptWithCheckbox(appearDeptIdStrs);
	}
	
	private String treeDeptWithCheckbox(String selectDeptid){
		//当前配置表信息
		DepartmentService service = new DepartmentService();
		List<Department2Model> models = service.getDeptByParentId(this.id);
		for (Department2Model model : models) {
			Department2ModelWithCheck departWitCheck = new Department2ModelWithCheck(model);
			if(selectDeptid!=null && selectDeptid.contains("|"+departWitCheck.getBindId()+"|")){
				departWitCheck.setChecked(true);
			}
			selectDepartmentTreeObjects.add(departWitCheck);
		}
		return Action.SUCCESS;
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
