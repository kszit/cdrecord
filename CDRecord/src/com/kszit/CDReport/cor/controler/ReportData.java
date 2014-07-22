package com.kszit.CDReport.cor.controler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.model.DepartmentAndReport;
import com.kszit.CDReport.cor.model.ReportAppareModel;
import com.kszit.CDReport.cor.model.ReportConfigModel;
import com.kszit.CDReport.cor.model.TreeModelWithCheck;
import com.kszit.CDReport.cor.model.persionDepRole.Department2Model;
import com.kszit.CDReport.cor.model.persionDepRole.Department2ModelWithCheck;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.balanced.DalancedChecking;
import com.kszit.CDReport.cor.service.persionDepRole.DepartmentService;
import com.kszit.CDReport.cor.service.reportData.InputTable;
import com.kszit.CDReport.cor.service.reportData.InputTableOfGrid;
import com.kszit.CDReport.cor.service.reportData.InputTableOfTreeGrid;
import com.kszit.CDReport.cor.service.reportData.SeeTable;
import com.kszit.CDReport.cor.service.reportData.SeeTableOfTreeGrid;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;


/**
 * 数据填报
 * @author Administrator
 *
 */
public class ReportData extends ReportCDSupport {
	private static final long serialVersionUID = 6030160388955596627L;
	
	private DataReportModel dataReportModel = new DataReportModel();
	private List<DataReportModel> dataReportList = new ArrayList<DataReportModel>();
	private ReportConfigModel reportConfigModel = new ReportConfigModel();
	private List<ReportConfigModel> reportConfigList = new ArrayList<ReportConfigModel>();
	private List<DepartmentAndReport> departmentAndReportList = new ArrayList<DepartmentAndReport>();
	
	//报表已经上报的单位
	private List<Object> selectDepartmentTreeObjects = new ArrayList<Object>();
	private String reportTableHtmlCode = "";
	/**
	 * 月份或季度列表
	 */
	List<DictionaryPO> periodList = new ArrayList<DictionaryPO>();
	/**
	 * 上报情况列表
	 */
	List<ReportAppareModel> appareList = new ArrayList<ReportAppareModel>();
	ReportAppareModel reportAppareModel = new ReportAppareModel();
	
	//删除id列表
	private String _chk;
	
	//页面提示信息，报表上报页面的提示信息
	private String promptMessage;
	//已经配置报表的年份列表
	List<DictionaryPO> yearList = new ArrayList<DictionaryPO>();
	
	//trrGrid 根节点
	private String rootBindid = "";
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 设置报表为回退
	 * @return
	 */
	public String setReportToBack(){
		ReportDataService rdService = new ReportDataService();
		rdService.backReport(dataReportModel.getBindId());
		super.outText("回退成功");
		return "";
	}
	
	/**
	 * 设置报表为上报
	 * @return
	 */
	public String setReportToPublish(){
		setReportToPublishCommon();
		super.outText("上报成功");
		return "";
	}
	
	
	/**
	 * 填报人 退回的报表
	 * @return
	 */
	public String listPageOfMyRollback(){
		dataReportModel.setMadeManName(super.getUser().getUserid());
		dataReportModel.setVerify(StaticVaribles.DataReportFlowState_back);
		listPage();
		
		return "listPageOfMyRollback";
	}	
	
	
	/**
	 * 填报人 审核中的报表
	 * @return
	 */
	public String listPageOfMyVerify(){
		dataReportModel.setMadeManName(super.getUser().getUserid());
		dataReportModel.setVerify(StaticVaribles.DataReportFlowState_verify);
		listPage();
		
		return "listPageOfMyVerify";
	}	

	/**
	 * 填报人 编辑中的报表
	 * @return
	 */
	public String listPageOfMy(){
		dataReportModel.setMadeManName(super.getUser().getUserid());
		dataReportModel.setVerify(StaticVaribles.DataReportFlowState_weave);
		listPage();
		
		return "listPage";
	}
	
	/**
	 * 填报人 已归档的报表
	 * @return
	 */
	public String listPageOfMyArchive(){
		dataReportModel.setMadeManName(super.getUser().getUserid());
		dataReportModel.setVerify(StaticVaribles.DataReportFlowState_publish);
		listPage();
		
		return "listPageOfMyArchive";
	}
	
	/**
	 * 本部门待审核报表，审核人使用
	 * @return
	 */
	public String listPageOfDepVerify(){
		deptVerifyReportList();
		return "listPageVerify";
	}
	/**
	 * 本部门上报中的报表
	 * @return
	 */
	public String listPageOfDep(){
		dataReportModel.setCreateDepBindid(super.getDepartment().getBindid());
		//dataReportModel.setVerify(StaticVaribles.DataReportFlowState_weaveAndverify);
		listPage();
		return "listPage";
	}
	
	/**
	 * 已填报报表列表页面
	 * @return
	 */
	private void listPage(){
		
		ReportDataService rdService = new ReportDataService();
		List<DataReportPO> poList = rdService.getDataReportList(dataReportModel);
		for(DataReportPO po:poList){
			DataReportModel model = new DataReportModel(po);
			dataReportList.add(model);
		}
	}
	
	/**
	 * 报表上报情况           主页页面
	 * @return
	 */
	public String reportAppearMainPage(){
		return "reportAppearMainPage";
	}
	
	/**
	 * 各报表上报情况页面
	 * @return
	 */
	public String reportAppearPage(){
		
		
		return "reportAppearPage";
	}
	
	/**
	 * 各单位上报情况页面---主页面
	 * @return
	 */
	public String departmentAppearPage(){
		return "departmentAppearPage";
	}
	
	/**
	 * 各单位上报情况列表页面
	 * @return
	 */
	public String departmentAppearListPage(){
		ReportDataService service = new ReportDataService();
		Calendar cal = Calendar.getInstance();
                /*
	    int year = cal.get(Calendar.YEAR);
	    if(reportAppareModel.getReportConfigModel().getRtyear()==null){
	    	reportAppareModel.getReportConfigModel().setRtyear(""+year);
	    }
	    */
		appareList = service.getReportAppear(reportAppareModel);
		//ReportAppareModel appearModel = new ReportAppareModel();
		///appearModel.setDepid("333");
		
		//appareList.add(appearModel);
		return "departmentAppareListPage";
	}


	/**
	 * 
	* @Title: inputPageOfGrid 
	* @Description:  数据填报页面，使用jeasyui作为
	* @param：
	* @return： String
	* @throws：
	 */
	public String inputPageOfGrid(){
		String reportBindid = dataReportModel.getRepotBindid();
		if(reportBindid != null){
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportBindid);
			this.reportConfigModel = new ReportConfigModel(reportConfig);
			dataReportModel.setRepotBindid(reportConfig.getReportBindidLink());
			dataReportModel.setReportName(reportConfig.getReportName());
			InputTableOfGrid inputReport = new InputTableOfGrid(reportBindid+"",dataReportModel.getBindId()+"");
			reportTableHtmlCode = inputReport.getTable();
			
			setPeriodListByType(reportConfig.getRttype());
			setYearListByType(StaticVaribles.ConfigYearBindId);
		}else{
			reportTableHtmlCode = "";
		}
		
		//初始化已有数据
		ReportDataService rdService = new ReportDataService();
		DataReportPO po = rdService.getDataReportByBindid(dataReportModel.getBindId());
		if(po!=null){
			dataReportModel = new DataReportModel(po);
		}else{
			dataReportModel.setCreateDepBindid(super.getDepartment().getBindid());
			dataReportModel.setMadeManName2(super.getUser().getUserName());
			dataReportModel.setMadeManName(super.getUser().getUserid());
			dataReportModel.setMadeManDate(new Date(System.currentTimeMillis()));
			dataReportModel.setVerify(StaticVaribles.DataReportFlowState_weave);
		}
		return "inputPageOfGrid";
	}
	
	/**
	 * 
	* @Title: inputPageDataOfGrid 
	* @Description:  
	* @param：
	* @return： String
	* @throws：
	 */
	public String inputPageDataOfGrid(){
		String reportBindid = dataReportModel.getRepotBindid();
		if(reportBindid != null){
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportBindid);
			this.reportConfigModel = new ReportConfigModel(reportConfig);
			dataReportModel.setRepotBindid(reportConfig.getReportBindidLink());
			dataReportModel.setReportName(reportConfig.getReportName());
			InputTableOfGrid inputReport = new InputTableOfGrid(reportBindid+"",dataReportModel.getBindId()+"");
			param1 = inputReport.getTableData();
		}else{
//			reportTableHtmlCode = "";
		}
//		param1 = "{\"total\":1,\"rows\":[	{\"580\":\"580\",\"581\":\"档案部门有明确的分管领<br>导管理。分管领导切实解决档<br>案管理实际问题\",\"582\":\"10.00\",\"583\":\"P\",\"584\":\"36.50\",\"585\":\"Large\",\"586\":\"EST-1\"}]}";
		return "jsonDataPage";
	}
	
	
	
	
	
	/**
	 * 
	* @Title: inputPageOfGrid 
	* @Description:  数据填报页面，使用jeasyui作为
	* @param：
	* @return： String
	* @throws：
	 */
	public String inputPageOfTreeGrid(){
		String reportBindid = dataReportModel.getRepotBindid();
		if(reportBindid != null){
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportBindid);
			this.reportConfigModel = new ReportConfigModel(reportConfig);
			dataReportModel.setRepotBindid(reportConfig.getReportBindidLink());
			dataReportModel.setReportName(reportConfig.getReportName());
			InputTableOfTreeGrid inputReport = new InputTableOfTreeGrid(reportBindid+"",dataReportModel.getBindId()+"");
			reportTableHtmlCode = inputReport.getTable();
			
			rootBindid = inputReport.treeNodeBindid();
			
			setPeriodListByType(reportConfig.getRttype());
			setYearListByType(StaticVaribles.ConfigYearBindId);
		}else{
			reportTableHtmlCode = "";
		}
		
		//初始化已有数据
		ReportDataService rdService = new ReportDataService();
		DataReportPO po = rdService.getDataReportByBindid(dataReportModel.getBindId());
		if(po!=null){
			dataReportModel = new DataReportModel(po);
		}else{
			dataReportModel.setCreateDepBindid(super.getDepartment().getBindid());
			dataReportModel.setMadeManName2(super.getUser().getUserName());
			dataReportModel.setMadeManName(super.getUser().getUserid());
			dataReportModel.setMadeManDate(new Date(System.currentTimeMillis()));
			dataReportModel.setVerify(StaticVaribles.DataReportFlowState_weave);
		}
		return "inputPageOfTreeGrid";
	}
	
	public String seePageOfTreeGrid(){
		String reportBindid = dataReportModel.getRepotBindid();
		if(reportBindid != null){
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportBindid);
			this.reportConfigModel = new ReportConfigModel(reportConfig);
			dataReportModel.setRepotBindid(reportConfig.getReportBindidLink());
			dataReportModel.setReportName(reportConfig.getReportName());
			SeeTableOfTreeGrid inputReport = new SeeTableOfTreeGrid(reportBindid+"",dataReportModel.getBindId()+"");
			reportTableHtmlCode = inputReport.getTable();
			
			rootBindid = inputReport.treeNodeBindid();
			
			setPeriodListByType(reportConfig.getRttype());
			setYearListByType(StaticVaribles.ConfigYearBindId);
		}else{
			reportTableHtmlCode = "";
		}
		
		//初始化已有数据
		ReportDataService rdService = new ReportDataService();
		DataReportPO po = rdService.getDataReportByBindid(dataReportModel.getBindId());
		if(po!=null){
			dataReportModel = new DataReportModel(po);
		}else{
			dataReportModel.setCreateDepBindid(super.getDepartment().getBindid());
			dataReportModel.setMadeManName2(super.getUser().getUserName());
			dataReportModel.setMadeManName(super.getUser().getUserid());
			dataReportModel.setMadeManDate(new Date(System.currentTimeMillis()));
			dataReportModel.setVerify(StaticVaribles.DataReportFlowState_weave);
		}
		return "seePageOfTreeGrid";
	}
	
	
	
	/**
	 * 
	* @Title: inputPageDataOfGrid 
	* @Description:  
	* @param：
	* @return： String
	* @throws：
	 */
	public String inputPageDataOfTreeGrid(){
		String reportBindid = dataReportModel.getRepotBindid();
		if(reportBindid != null){
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportBindid);
			this.reportConfigModel = new ReportConfigModel(reportConfig);
			dataReportModel.setRepotBindid(reportConfig.getReportBindidLink());
			dataReportModel.setReportName(reportConfig.getReportName());
			InputTableOfTreeGrid inputReport = new InputTableOfTreeGrid(reportBindid+"",dataReportModel.getBindId()+"");
			param1 = inputReport.getTableData();
		}else{
//			reportTableHtmlCode = "";
		}
//		param1 = "{\"total\":1,\"rows\":[	{\"580\":\"580\",\"581\":\"档案部门有明确的分管领<br>导管理。分管领导切实解决档<br>案管理实际问题\",\"582\":\"10.00\",\"583\":\"P\",\"584\":\"36.50\",\"585\":\"Large\",\"586\":\"EST-1\"}]}";
		return "jsonDataPage";
	}
	
	
	public String saveReportOfGrid(){
		Map<String,String[]> dataMap = super.getRequest().getParameterMap();
		Map<String,Object> newDataMap = trancDataMap(dataMap);
		ReportDataService dataService = new ReportDataService(dataReportModel.getRepotBindid()+"");
		int dataBindid = dataService.saveDataReport(dataReportModel,newDataMap);
		dataReportModel.setBindId(dataBindid);
		return inputPageOfTreeGrid();
	}
	
	
	
	
	/**
	 * 报表填报页面
	 * @return
	 */
	public String inputPage(){
		String reportBindid = dataReportModel.getRepotBindid();
		if(reportBindid != null){
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportBindid);
			this.reportConfigModel = new ReportConfigModel(reportConfig);
			dataReportModel.setRepotBindid(reportConfig.getReportBindidLink());
			dataReportModel.setReportName(reportConfig.getReportName());
			InputTable inputReport = new InputTable(reportBindid+"",dataReportModel.getBindId()+"");
			reportTableHtmlCode = inputReport.getTable();
			
			setPeriodListByType(reportConfig.getRttype());
			setYearListByType(StaticVaribles.ConfigYearBindId);
		}else{
			reportTableHtmlCode = "";
		}
		
		//初始化已有数据
		ReportDataService rdService = new ReportDataService();
		DataReportPO po = rdService.getDataReportByBindid(dataReportModel.getBindId());
		if(po!=null){
			dataReportModel = new DataReportModel(po);
		}else{
			dataReportModel.setCreateDepBindid(super.getDepartment().getBindid());
			dataReportModel.setMadeManName2(super.getUser().getUserName());
			dataReportModel.setMadeManName(super.getUser().getUserid());
			dataReportModel.setMadeManDate(new Date(System.currentTimeMillis()));
			dataReportModel.setVerify(StaticVaribles.DataReportFlowState_weave);
		}
		return Action.INPUT;
	}
	       
	/**
	 * 报表查看页面。url:/reportData/reportData/reportData_seePage.do参数：dataReportModel.repotBindid=1302-8&dataReportModel.bindId=1523
	 * @return
	 */
	public String seePage(){
		commSeeReport();
		return "seePage";
	}
	

	
	public String reportDataPublish(){
		commSaveData();//先保存用户填写的主表和子表数据
		
		//验证报表平衡关系
		DalancedChecking dalanced = new DalancedChecking(dataReportModel.getRepotBindid()+"");
		//dalanced
		
		ReportDataService dataService = new ReportDataService(dataReportModel.getRepotBindid()+"");
		//查看报表是否已经上报
		DataReportModel hasApperarModel = dataService.hasAppearReport(dataReportModel);
		if(hasApperarModel==null){

			setReportToPublishCommon();
			promptMessage = "已经提交成功";
			return seePage();
		}else{
			//
			promptMessage = hasApperarModel.getCreateDepName()+"的"+hasApperarModel.getReportName()+"-->"+hasApperarModel.getYear()+"年"+hasApperarModel.getPeriodsName()+"已经上报！"+"编制人："+hasApperarModel.getMadeManName2();
			return inputPage();
		}
	}
	/**
	 * 上报报表：先保存，然后验证保单平衡关系，最后提交审核。
	 * @return
	 */
	public String reportDataVerify(){
		commSaveData();//先保存用户填写的主表和子表数据
		
		//验证报表平衡关系
		DalancedChecking dalanced = new DalancedChecking(dataReportModel.getRepotBindid()+"");
		//dalanced
		
		ReportDataService dataService = new ReportDataService(dataReportModel.getRepotBindid()+"");
		//查看报表是否已经上报
		DataReportModel hasApperarModel = dataService.hasAppearReport(dataReportModel);
		if(hasApperarModel==null){
			//设置报表到审核人
			dataService.verifyReport(dataReportModel);
			promptMessage = "已经提交到审核人";
			return seePage();
		}else{
			//
			promptMessage = hasApperarModel.getCreateDepName()+"的"+hasApperarModel.getReportName()+"-->"+hasApperarModel.getYear()+"年"+hasApperarModel.getPeriodsName()+"已经上报！"+"编制人："+hasApperarModel.getMadeManName2();
			return inputPage();
		}

		//
		
		
	}
	
	/**
	 * 保存报表数据  json数据
	 * @return
	 */
	public String saveData(){
		
		
		
		
		commSaveData();
		this.addActionMessage("保存成功");
		//return Action.SUCCESS;
		return inputPage();
	}
	
        
        /**
	 * 保存主表数据。在excel导入数据时调用。
	 * @return
	 */
	public String saveDataZhu(){
		commSaveData();
        super.outText(""+dataReportModel.getBindId());
		return "";
                //return SUCCESS;
	}
        
	/**
	 * 删除
	 * @return
	 */
	public String deletes(){
		if(this._chk!=null){
			ReportDataService rdService = new ReportDataService();
			rdService.deletes(_chk);
		}
		return listPageOfMy();
	}
	
	/**
	 * 用户的后台主页面
	 * @return
	 */
	public String userMainPage(){
		String deptid = super.getUser().getUserDepId();
		ReportDataService rdService = new ReportDataService();
		if("填报人".equals(super.getRole().getRoleName())){
			//填报人  本月报表上报情况
			List<DataReportModel> monthDataReport = rdService.appearReportOfMonthWithDep(deptid,null);
			dataReportList = monthDataReport;
		}else if("审核人".equals(super.getRole().getRoleName())){
			deptVerifyReportList();//审核人待审核报表
			
		}else if("部门管理员".equals(super.getRole().getRoleName())){
			
		}else if("报表汇总人".equals(super.getRole().getRoleName())){
			departmentAndReportList = rdService.appearSituationOfChileDept("0");
		} 
		
		return "userMainPage";
	}
	
	/**
	 * 上级查看 下级单位当前月的报表上报情况
	 * 
	 * 参数：单位id,dataReportModel.getCreateDepBindid();
	 * @return
	 */
	public String appearReportListOfDeptAndMonthPage(){
		String deptid = dataReportModel.getCreateDepBindid();
		
		ReportDataService rdService = new ReportDataService();
		List<DataReportModel> monthDataReport = rdService.appearReportOfMonthWithDep(deptid,null);
		dataReportList = monthDataReport;
		return "appearReportListOfDeptAndMonthPage";
	}
	
	public String getPeriodTreeOfappearReport(){
		String deptParentId = this.param1;
		String reportBindid = this.param2;
		String selectDeptId = this.param3;
		if(deptParentId!=null && deptParentId.equals("0")){
			ReportDataService rdService = new ReportDataService();
			dataReportModel.setVerify(StaticVaribles.DataReportFlowState_publish);
			dataReportModel.setRepotBindid(reportBindid);
			if(selectDeptId!=null && !"".equals(selectDeptId)){
				dataReportModel.setCreateDepBindid(selectDeptId);
			}
			
			List<DataReportPO> appearReports = rdService.getDataReportList(dataReportModel);
			for(DataReportPO dataReport:appearReports){
				String treeId = dataReport.getYear()+dataReport.getPeriods();
				
				boolean isHas = false;
				for(Object o:selectDepartmentTreeObjects){
					TreeModelWithCheck treeNode_temp = (TreeModelWithCheck)o;
					String id_temp = treeNode_temp.getId()+"";
					if(id_temp.equals(treeId)){
						isHas = true;
					}
				}
				if(isHas){
					continue;
				}
				
				TreeModelWithCheck treeNode = new TreeModelWithCheck();
				treeNode.setId(treeId);
				treeNode.setText(dataReport.getYear()+"年"+StaticVaribles.getDicContent(dataReport.getPeriods()));
				treeNode.setIsleaf(true);
				selectDepartmentTreeObjects.add(treeNode);
				
			}
			
		}
		
		
		
		return Action.SUCCESS;
	}
	
	/**
	 * 
	* getDeptTreeOfappearReport(这里用一句话描述这个方法的作用) <br> 
	* TODO(这里描述这个方法适用条件 – 无)  <br>
	* TODO(这里描述这个方法的执行流程 – 无)  <br>
	* TODO(这里描述这个方法的使用方法 – 无)  <br>
	* TODO(这里描述这个方法的注意事项 – 无)  <br>
	* @param name  
	* @param @return 设定文件  - 无
	* @return String DOM对象  - 无
	* @Exception 异常对象  - 无
	* @since  -
	 */
	public String getDeptTreeOfappearReport(){
		String deptParentId = this.param1;
		String reportBindid = this.param2;
		String yearAndPeriod = this.param3;
		String year = "";
		String period = "";
		if(yearAndPeriod!=null && !"".equals(yearAndPeriod)){
			year = yearAndPeriod.substring(0, 4);
			period = yearAndPeriod.substring(4,yearAndPeriod.length());
		}
		
		DepartmentService service = new DepartmentService();
		List<Department2Model> models = service.getDeptByParentId(deptParentId);
		if(models.size()!=0){
			ReportDataService rdService = new ReportDataService();
			dataReportModel.setVerify(StaticVaribles.DataReportFlowState_publish);
			dataReportModel.setRepotBindid(reportBindid);
			if(!year.equals("")){
				dataReportModel.setYear(year);
				dataReportModel.setPeriods(period);
			}
			List<DataReportPO> appearReports = rdService.getDataReportList(dataReportModel);
			for (Department2Model model : models) {
				for(DataReportPO dataReport:appearReports){
					String appearDeptId = dataReport.getCreateDepBindid();
					if(model.getBindId()==Long.parseLong(appearDeptId)){
						Department2ModelWithCheck departWitCheck = new Department2ModelWithCheck(model);
						selectDepartmentTreeObjects.add(departWitCheck);
					}
				}
			}
		}
		return Action.SUCCESS;
	}
	
	
	public String queryAppearReportListPage(){
		
		//单位列表
		this.setDepartmentAndReportList2(new DepartmentUtil().getDepartmentService().getDepartmentListOfChild("0"));
		DepartmentAndReport allDept = new DepartmentAndReport();
		allDept.setDeptBindid("0");
		allDept.setDepartmentName("全部");
		departmentAndReportList.add(allDept);

		//报表列表
		ReportConfigService confService = new ReportConfigService();
		reportConfigList = confService.getReportConfigsOfModel();
		ReportConfigModel allReportConfigModel = new ReportConfigModel();
		allReportConfigModel.setBindId("0");
		allReportConfigModel.setReportName("全部");
		reportConfigList.add(allReportConfigModel);
		
		if(dataReportModel.getYear()==null && dataReportModel.getRepotBindid()==null && dataReportModel.getCreateDepBindid()==null && dataReportModel.getPeriods()==null){
			dataReportModel.setRepotBindid("0-0");
			dataReportModel.setCreateDepBindid("0");
		}else{
//			//年份
//			ReportConfigService rcService = new ReportConfigService();
//			Set<String> yearSet = rcService.getConfigYearList();
//			this.setYearList(yearSet);
//			
//			
//			Long periType = null;
//			if(priodBindid.equals("0")){
//				ReportConfigService cfgSerive = new ReportConfigService();
//				ReportConfigPO confPo = cfgSerive.getReportConfigByBindidLink(reportLinkbindid);
//				
//				//DictionaryService dicService = new DictionaryService();
//				periType = new Long(StaticVaribles.changePeriodToPeriodDataBindid(Integer.parseInt(confPo.getRttype())));
//			
//			}else{
//				DictionaryService dicService = new DictionaryService();
//				DictionaryPO dicPo = dicService.getOneByBindid(dataReportModel.getPeriods());
//				periType = new Long(dicPo.getType());
//			}
//			//期限
//			
//			DictionaryService dicservice = new DictionaryService();
//			List<DictionaryPO> poList = dicservice.getDictionaryByType(periType);
//
//			periodList = poList;
				
		}
		
		String year = dataReportModel.getYear();
		String reportLinkbindid = dataReportModel.getRepotBindid();
		String deptBindid = dataReportModel.getCreateDepBindid();
		String priodBindid = dataReportModel.getPeriods();
		

		

		

		ReportDataService rdService = new ReportDataService();
		dataReportModel.setVerify(-1);
		
		
		
		if((deptBindid==null || deptBindid.equals("0")) && (reportLinkbindid!=null && (reportLinkbindid.equals("0-0") || reportLinkbindid.equals("0")))){
			dataReportList = rdService.getAllDataReport("341");
		}else if((deptBindid==null ||deptBindid.equals("0")) && (priodBindid!=null &&!priodBindid.equals("0"))){//查看某期报表的所有单位的上报情况
			for(DepartmentAndReport dept:departmentAndReportList){
				dataReportModel.setCreateDepBindid(dept.getDeptBindid());
				DataReportModel querModel = new DataReportModel(dataReportModel);
				DataReportModel dataReport = rdService.getDataReportModelByLike(querModel);
				if(dataReport.getBindId()==0){
					DataReportModel newDataReportModel = new DataReportModel(dataReport);
					newDataReportModel.setCreateDepName(dept.getDepartmentName());
					dataReportList.add(newDataReportModel);
				}else{
					dataReportList.add(dataReport);
				}
			}
			dataReportModel.setCreateDepBindid(deptBindid);
		}else if((priodBindid==null ||priodBindid.equals("0")) && (deptBindid!=null && !deptBindid.equals("0"))){//查看某单位的所有时期的报表
			//DataReportModel querModel = new DataReportModel(dataReportModel);
			for(DictionaryPO period:periodList){
				DataReportModel querModel = new DataReportModel(dataReportModel);
				querModel.setPeriods(period.getBindId()+"");
				DataReportModel dataReport = rdService.getDataReportModelByLike(querModel);
				if(dataReport.getBindId()==0){
					DataReportModel newDataReportModel = new DataReportModel(dataReport);
					newDataReportModel.setPeriods(period.getBindId()+"");
					newDataReportModel.setPeriodsName(null);
					dataReportList.add(newDataReportModel);
				}else{
					dataReportList.add(dataReport);
				}
			}
		}else {
			//查询数据
			DataReportModel dataReport = rdService.getDataReportModelByLike(dataReportModel);
			dataReportList.add(dataReport);	
		}
		//periodList
		

		

		DictionaryPO allPeriod = new DictionaryPO();
		allPeriod.setBindId(0L);
		allPeriod.setName("全部");
		periodList.add(allPeriod);


		return "appearReportListOfDeptAndMonthPage";
	}
	
	
	
	/**
	 * 查看报表公共方法
	 */
	private void commSeeReport(){
		String reportBindid = dataReportModel.getRepotBindid();
		
		if(reportBindid != null){
			ReportConfigService rcService = new ReportConfigService();
			ReportConfigPO  reportConfig = rcService.getReportConfigByBindidLink(reportBindid);
			dataReportModel.setRepotBindid(reportConfig.getReportBindidLink());
			dataReportModel.setReportName(reportConfig.getReportName());
			SeeTable seeReport = new SeeTable(reportBindid+"",dataReportModel.getBindId()+"");
			reportTableHtmlCode = seeReport.getTable();
		}else{
			reportTableHtmlCode = "";
		}
		
		//初始化已有数据
		ReportDataService rdService = new ReportDataService();
		DataReportPO po = rdService.getDataReportByBindid(dataReportModel.getBindId());
		if(po!=null){
			dataReportModel = new DataReportModel(po);
		}
	}
	
	private void setReportToPublishCommon(){
		ReportDataService rdService = new ReportDataService();
		rdService.appearReport(dataReportModel.getBindId(),super.getUser().getUserid());
	}
	
	/**
	 * 保存报表数据公共方法
	 */
	@SuppressWarnings("unchecked")
	private void commSaveData(){
		Map<String,String[]> dataMap = super.getRequest().getParameterMap();
		Map<String,Object> newDataMap = trancDataMap(dataMap);
		ReportDataService dataService = new ReportDataService(dataReportModel.getRepotBindid()+"");
		int dataBindid = dataService.saveDataReport(dataReportModel,newDataMap);
		dataReportModel.setBindId(dataBindid);
	}
	
	/**
	 * 初始化期数值
	 * @param type
	 */
	private void setPeriodListByType(String type){
		
		int typeLong = Integer.parseInt(type);
		typeLong = StaticVaribles.changePeriodToPeriodDataBindid(typeLong);
		
		DictionaryService dicService = new DictionaryService();
		periodList = dicService.getDictionaryByType(typeLong);
	}
	/**
	 * 初始化期数值
	 * @param type
	 */
	private void setYearListByType(int type){
		DictionaryService dicService = new DictionaryService();
		yearList = dicService.getDictionaryByType(type);
	}

	/**
	 * 登陆人部门下待审核的报表
	 */
	private void deptVerifyReportList(){
		dataReportModel.setCreateDepBindid(super.getDepartment().getBindid());
		dataReportModel.setVerify(StaticVaribles.DataReportFlowState_verify);
		listPage();
	}
	
	
	public DataReportModel getDataReportModel() {
		return dataReportModel;
	}

	public void setDataReportModel(DataReportModel dataReportModel) {
		this.dataReportModel = dataReportModel;
	}

	public String getReportTableHtmlCode() {
		return reportTableHtmlCode;
	}

	public void setReportTableHtmlCode(String reportTableHtmlCode) {
		this.reportTableHtmlCode = reportTableHtmlCode;
	}

	public List<DataReportModel> getDataReportList() {
		return dataReportList;
	}

	public void setDataReportList(List<DataReportModel> dataReportList) {
		this.dataReportList = dataReportList;
	}
	public String get_chk() {
		return _chk;
	}
	public void set_chk(String _chk) {
		this._chk = _chk;
	}
	
	/**
	 * 转换数据，不转换不能添加合计数据
	 * @param oldDataMap
	 * @return
	 */
	private Map<String,Object> trancDataMap(Map<String,String[]> oldDataMap){
		Map<String,Object> newDataMap = new HashMap<String,Object>();
		for(Object key:oldDataMap.keySet()){
			String keyStr = key.toString();
			String value = ((String[])oldDataMap.get(keyStr))[0];
			newDataMap.put(keyStr, value);
		}
		return newDataMap;
	}

	public List<DictionaryPO> getPeriodList() {
		return periodList;
	}

	public void setPeriodList(List<DictionaryPO> periodList) {
		this.periodList = periodList;
	}

	public ReportAppareModel getReportAppareModel() {
		return reportAppareModel;
	}

	public void setReportAppareModel(ReportAppareModel reportAppareModel) {
		this.reportAppareModel = reportAppareModel;
	}

	public List<ReportAppareModel> getAppareList() {
		return appareList;
	}

	public void setAppareList(List<ReportAppareModel> appareList) {
		this.appareList = appareList;
	}
	public String getPromptMessage() {
		return promptMessage;
	}

	public void setPromptMessage(String promptMessage) {
		this.promptMessage = promptMessage;
	}

	public List<DepartmentAndReport> getDepartmentAndReportList() {
		return departmentAndReportList;
	}

	public void setDepartmentAndReportList(List<DepartmentAndReport> departmentAndReportList) {
		this.departmentAndReportList = departmentAndReportList;
	}
	
	public void setDepartmentAndReportList2(List<DepartmentToReport> deptList) {
		for (DepartmentToReport dept : deptList) {
			DepartmentAndReport  department = new DepartmentAndReport();
			department.setDeptBindid(dept.getBindid());
			department.setDepartmentName(dept.getDepartmentName());
			this.departmentAndReportList.add(department);
		}
	}
	public List<DictionaryPO> getYearList() {
		return yearList;
	}

	public void setYearList(List<DictionaryPO> yearList) {
		this.yearList = yearList;
	}
	public void setYearList(Set<String> yearList) {
		for(String year:yearList){
			DictionaryPO dicPo = new DictionaryPO();
			dicPo.setName(year);
			this.yearList.add(dicPo);
		}
		
	}

	public List<ReportConfigModel> getReportConfigList() {
		return reportConfigList;
	}

	public void setReportConfigList(List<ReportConfigModel> reportConfigList) {
		this.reportConfigList = reportConfigList;
	}

	public ReportConfigModel getReportConfigModel() {
		return reportConfigModel;
	}

	public void setReportConfigModel(ReportConfigModel reportConfigModel) {
		this.reportConfigModel = reportConfigModel;
	}

	public List<Object> getSelectDepartmentTreeObjects() {
		return selectDepartmentTreeObjects;
	}

	public void setSelectDepartmentTreeObjects(
			List<Object> selectDepartmentTreeObjects) {
		this.selectDepartmentTreeObjects = selectDepartmentTreeObjects;
	}

	public String getRootBindid() {
		return rootBindid;
	}

	public void setRootBindid(String rootBindid) {
		this.rootBindid = rootBindid;
	}

}
