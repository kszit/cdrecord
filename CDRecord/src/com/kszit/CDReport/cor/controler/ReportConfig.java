package com.kszit.CDReport.cor.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.log.LogService;
import com.kszit.CDReport.cor.model.CommonTreeNodeModel;
import com.kszit.CDReport.cor.model.HeaderRowConfigModel;
import com.kszit.CDReport.cor.model.ReportConfigModel;
import com.kszit.CDReport.cor.model.persionDepRole.Department2Model;
import com.kszit.CDReport.cor.model.persionDepRole.Department2ModelWithCheck;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.HVConfigCellDValueService;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.SelectCellTableService;
import com.kszit.CDReport.cor.service.persionDepRole.DepartmentService;
import com.kszit.CDReport.cor.service.reportData.SelectHPartTable;
import com.kszit.CDReport.cor.service.reportData.SelectHVCellPartTable;
import com.kszit.CDReport.cor.service.reportData.SelectHVPartTable;
import com.kszit.CDReport.cor.service.reportData.SelectVPartTable;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;


/**
 * 报表配置
 * @author Administrator
 *
 */
public class ReportConfig extends ReportCDSupport {
    private static Log log = LogService.getLogger(ReportConfig.class);
	private static final long serialVersionUID = 1252306234202277444L;

	private ReportConfigModel reportConfig = new ReportConfigModel();
	
	HeaderRowConfigModel headerRowModel = new HeaderRowConfigModel();
	
	private List<ReportConfigModel> reportList = new ArrayList<ReportConfigModel>();
	
	private List<CommonTreeNodeModel> yearTreeList = null;
	
	private List<CommonTreeNodeModel> configReportTreeList = null;
	
	// 报表上报机构配置对象
	private List<Object> selectDepartmentTreeObjects = new ArrayList<Object>();
	//获取树形机构时，父节点id
	private String id = "";
	
	
	
	List<DictionaryPO> yearList = null;
	/**
	 * 频次
	 */
	List<DictionaryPO> reportFreqList = null;
	
	List<DictionaryPO> reportStateList = null;
	
	List<DictionaryPO> typeList = null;
	
	
	private String DataTypeBindId;
	private String DataFromBindId;
	private String UIModelBindId;
	private String IsSettingBindId;
	
	private String selectCellHTML;
	private String seeDataFromAllTableHTML;//
	
	
	private String _chk;
	
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	
	
	/**
	 * 报表配置主页面
	 * @return
	 */
	public String mainPage(){
		return "mainPage";
	}
	
	/**
	 * 配置报表可上报的单位
	 * 
	 * @return
	 */
	public String setAppearDepartment() {
		ReportConfigService rcService = new ReportConfigService();
		//当前配置表信息
		String appearDeptIdStrs = rcService.getReportConfigByBindidLink(reportConfig.getReportLink()).getAppearDepartment();
		return treeDeptWithCheckbox(appearDeptIdStrs);
	}
	
	
	/**
	 * 配置报表可查询的单位
	 * 
	 * @return
	 */
	public String setQueryDepartment() {
		ReportConfigService rcService = new ReportConfigService();
		String appearDeptIdStrs = rcService.getReportConfigByBindidLink(reportConfig.getReportLink()).getQueryDept();
		return treeDeptWithCheckbox(appearDeptIdStrs);
	}
	/**
	 * 配置报表可查询的单位
	 * 
	 * @return
	 */
	public String setColectDepartment() {
		ReportConfigService rcService = new ReportConfigService();
		String appearDeptIdStrs = rcService.getReportConfigByBindidLink(reportConfig.getReportLink()).getCollectDept();
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
	
	
	/**
	 * 保存报表可上报的单位
	 * @return
	 */
	public String saveAppearDepartment(){
		ReportConfigService rcService = new ReportConfigService();
		ReportConfigPO reportPo = rcService.getReportConfigByBindidLink(reportConfig.getReportLink());
		reportPo.setAppearDepartment(reportConfig.getAppearDepartment());
		rcService.saveOrUpdateNoCreateT(reportPo);
		super.outText("22");
		return "";
	}
	
	/**
	 * 保存报表可上报的单位
	 * @return
	 */
	public String saveQueryDepartment(){
		ReportConfigService rcService = new ReportConfigService();
		ReportConfigPO reportPo = rcService.getReportConfigByBindidLink(reportConfig.getReportLink());
		reportPo.setQueryDept(reportConfig.getQueryDept());
		rcService.saveOrUpdateNoCreateT(reportPo);
		super.outText("22");
		return "";
	}
	
	/**
	 * 保存报表可上报的单位
	 * @return
	 */
	public String saveCollectDepartment(){
		ReportConfigService rcService = new ReportConfigService();
		ReportConfigPO reportPo = rcService.getReportConfigByBindidLink(reportConfig.getReportLink());
		reportPo.setCollectDept(reportConfig.getCollectDept());
		rcService.saveOrUpdateNoCreateT(reportPo);
		super.outText("22");
		return "";
	}
	/**
	 * 已经配置年份列表
	 * @return
	 */
	public String getConfigYearListJSON(){
		ReportConfigService rcService = new ReportConfigService();
		Set<String> yearSet = rcService.getConfigYearList();
		yearTreeList = new ArrayList<CommonTreeNodeModel>();
		for(String year:yearSet){
			CommonTreeNodeModel treeNode = new CommonTreeNodeModel();
			treeNode.setText(year);
			treeNode.setId(year);
			treeNode.setCls("file");
			treeNode.setIsleaf(true);
			yearTreeList.add(treeNode);
		}
		return Action.SUCCESS;
	}
	
	/**
	 * 已经配置报表列表    按照年份查找
	 * @return
	 */
	public String getConfigReportListJson(){
		ReportConfigService rcService = new ReportConfigService();
		List<ReportConfigPO> poList = rcService.getReportConfigOfYear(reportConfig);

		configReportTreeList = new ArrayList<CommonTreeNodeModel>();
		for(ReportConfigPO po:poList){
			CommonTreeNodeModel treeNode = new CommonTreeNodeModel();
			treeNode.setText(po.getReportName());
			treeNode.setId(po.getReportBindidLink());
			treeNode.setCls("file");
			treeNode.setIsleaf(false);
			treeNode.setRtType(po.getRttype());
			configReportTreeList.add(treeNode);
		}
		return Action.SUCCESS;
	}
	

	
	/**
	 * 配置报表的列表页面
	 * @return
	 */
	public String listPage(){
            log.info("配置报表的列表页面");
		ReportConfigService rcService = new ReportConfigService();
		List<ReportConfigPO> poList = rcService.getReportConfigs();
		for(ReportConfigPO po:poList){
			ReportConfigModel model = new ReportConfigModel(po);
			reportList.add(model);
		}
		return "listPage";
	}
	
	/**
	 * 单表修改页面
	 * @return
	 */
	public String inputPage(){
		DictionaryService dicService = new DictionaryService();
		yearList = dicService.getDictionaryByType(StaticVaribles.ConfigYearBindId);
		reportFreqList = dicService.getDictionaryByType(StaticVaribles.ConfigReportFreqBindId);
		reportStateList = dicService.getDictionaryByType(StaticVaribles.ConfigReportStateBindId);
		typeList = dicService.getDictionaryByType(StaticVaribles.DataReportType);
		
		ReportConfigService rcService = new ReportConfigService();
		DataTypeBindId = StaticVaribles.ConfigHVdataTypeBindId+"";
		DataFromBindId = StaticVaribles.ConfigHVdataFromBindId+"";
		UIModelBindId = StaticVaribles.ConfigHVUIModeleBindId+"";
		IsSettingBindId = StaticVaribles.ConfigIsSettingBindId+"";
		if(reportConfig.getId()==-1){
			reportConfig.setId(0);
			return Action.INPUT;
		}
		ReportConfigPO po = rcService.getReportConfigById(reportConfig.getId());
		reportConfig = new ReportConfigModel(po);
		return Action.INPUT;
	}
	
	/**
	 * 查看表格数据来源配置页面
	 * @return
	 */
	public String seeDataFromAllTablePage(){
		HVConfigDataFromService service = new HVConfigDataFromService();
		seeDataFromAllTableHTML = service.seeDataFromAllTable(reportConfig.getReportBindidLink());
		
		return "seeDataFromAllTablePage";
	}
	
	/**
	 * 单元格默认值 查看 页面
	 * @return
	 */
	public String seeCellDefaultValuePage(){
		HVConfigCellDValueService service = new HVConfigCellDValueService();
		seeDataFromAllTableHTML = service.seeCellDefaultValueTable(reportConfig.getReportBindidLink());
		
		return "seeCellDefaultValuePage";
	}
	
	
	/**
	 * 
	 * 每个单元格单独配置      页面
	 * 
	 * @return
	 */
	public String singleCellProperyConfigPage(){
		return "singleCellProperyConfigPage";
	}
	
	/**
	 * 生成报表的复制版本
	 * @return
	 */
	public String copyReportTable(){
		ReportConfigService repConfigService = new ReportConfigService();
		ReportConfigPO oldConfigPo = new ReportConfigPO();
		oldConfigPo.setBindId(reportConfig.getBindId());
		oldConfigPo.setRtversion2(reportConfig.getRtversion2());
		ReportConfigPO newReportConfig = repConfigService.copyReportConfig(oldConfigPo);
		
		super.outText(newReportConfig.getId()+"|"+newReportConfig.getReportName()+"-"+newReportConfig.getRtversion2());
		
		return "";
	}
	
	/**
	 * 报表 单元格 选择页面
	 * @return
	 */
	public String selectCellPage(){
		SelectCellTableService service = new SelectCellTableService();
		selectCellHTML = service.getTable(headerRowModel.getReportBindid(),headerRowModel.getBindId(),headerRowModel.getDataFrom(),headerRowModel.getDataRuleItems());
		//selectCellHTML = service.getTable(reportConfig.getReportLink()+"",headerRowModel.getDataRuleItems());
		
		
		return "selectCellPage";
	}
	
	/**
	 * 报表选择页面
	 * @return
	 */
	public String selectReportPage(){
		//reportConfig.setReportState(StaticVaribles.ReportState_UsingBindId);
		ReportConfigService rcService = new ReportConfigService();
		reportConfig.setAppearDepartment(super.getUser().getUserDepId());
		List<ReportConfigPO> poList = rcService.getReportConfigByLike(reportConfig);
		for(ReportConfigPO po:poList){
			ReportConfigModel model = new ReportConfigModel(po);
			reportList.add(model);
		}
		return "selectReport";
	}
	
	/**
	 * 保存报表配置主表信息，不会保存子表信息
	 * @return
	 */
	public String save(){
		ReportConfigService rcService = new ReportConfigService();
		String id = rcService.saveOrUpdate(reportConfig);
	
		ReportConfigModel model = rcService.getReportConfigByIdWithModel(Integer.parseInt(id));
		reportConfig.setId(model.getId());
		reportConfig.setBindId(model.getBindId());
		reportConfig.setRtversion2(model.getRtversion2());
		
		this.addActionMessage("保存成功");
		return Action.SUCCESS;
	}
	
	/**
	 * 删除配置报表
	 * @return
	 */
	public String deletes(){
		if(this._chk!=null){
			ReportConfigService rcService = new ReportConfigService();
			rcService.deletes(_chk);
		}
		return listPage();
	}
	
	/**
	 * 从下级报表中选择部分报表作为上级报表的一部分，
	 * 下级报表的横向和纵向合并为一体作为表头,
	 * 横行和纵向组合到了一起
	 * 地址：/reportConfig/reportConfig_selectHVPartPage.action
	 * 参数：reportConfig.reportLink=2099-09
	 * @return
	 */
	public String selectHVPartPage(){
		String reportBindid = reportConfig.getReportLink();
		SelectHVPartTable selectHVPartTable = new SelectHVPartTable(reportBindid);
		seeDataFromAllTableHTML = selectHVPartTable.getTable();
		return "selectHPartPage";
	}
	
	/**
	 * 选择横向表头的选择页面
	 * 地址：/reportConfig/reportConfig_selectReportPartPage.action
	 * 参数：reportConfig.reportLink=2099-09
	 * 参数：param1，取值为h、v、hv，h:列表头，v:横向表头，hv:全表
	 * 参数：param2，reportBindid$hbindid**vbindid|reportBindid$hbindid**vbindid
	 * 参数：param3：1：checkbox，2：radio
	 * 返回页面中，H:
	 * 
	 * @return
	 */
	public String selectReportPartPage(){
		String reportBindid = reportConfig.getReportLink();
		String param1 = this.param1;
		String selectedCells = this.param2;
		int type = 1;
		if(this.param3!=null){
			type = Integer.parseInt(this.param3);
		}
		
		if(selectedCells!=null && selectedCells.contains("**")){
			selectedCells = selectedCells.replaceAll("\\*\\*", "#");
		}
		
		if("h".equals(param1)){
			SelectHPartTable selectHVPartTable = new SelectHPartTable(reportBindid,type);
			seeDataFromAllTableHTML = selectHVPartTable.getTable();
		}else if("v".equals(param1)){
			SelectVPartTable selectHVPartTable = new SelectVPartTable(reportBindid,type);
			seeDataFromAllTableHTML = selectHVPartTable.getTable();
		}else if("hv".equals(param1)){
			SelectHVCellPartTable selectHVPartTable = new SelectHVCellPartTable(reportBindid,selectedCells);
			seeDataFromAllTableHTML = selectHVPartTable.getTable();
			
		}
		
		
		return "selectHVPartPage";
	}
	/**
	 * 生成上级报表的汇总表时 使用。从下级的报表中选择部门指标形成股份公司的汇总表，指定报表的期限后，汇总表会自动抓取已经上报的数据。
	 * 
	 * Url:'<%=CONTEXT_PATH %>/reportConfig/reportConfig_saveHVPart.do?param1='+
		               			newReportBindid+"&param2="+oldReportBindid+"&param3="+selectedCell
	 * 参数： 新报表的id，
	 * 老报表到id，
	 * 选择的单元格，
	 * 父节点id    在新报表的哪个节点下 挂在选择的下级指标。
	 * @return
	 */
	public String saveHVPart(){
		String newReportBindid = param1;
		String oldReportBindid = param2;
		String selectedCell = param3;
		String parentId = param4;
		if(parentId==null || "".equals(parentId)){
			parentId = "0";
		}
		ReportConfigService rcService = new ReportConfigService();
		rcService.copyHVPartFromOthor(newReportBindid,oldReportBindid,selectedCell,parentId);
		super.outText("设置成功");
		return "";
	}
	/**
	 * 
	 * @return
	 */
	public String saveHPart(){
		String newReportBindid = param1;
		String oldReportBindid = param2;
		String selectedCell = param3;
		String parentId = param4;
		if(parentId==null || "".equals(parentId)){
			parentId = "0";
		}
		ReportConfigService rcService = new ReportConfigService();
		rcService.copyHPartFromOthor(newReportBindid,oldReportBindid,selectedCell,parentId);
		super.outText("设置成功");
		return "";
	}
	
	//===================================================================================================================
	
	
	public ReportConfigModel getReportConfig() {
		return reportConfig;
	}

	public void setReportConfig(ReportConfigModel reportConfig) {
		this.reportConfig = reportConfig;
	}

	public List<ReportConfigModel> getReportList() {
		return reportList;
	}

	public void setReportList(List<ReportConfigModel> reportList) {
		this.reportList = reportList;
	}

	public String get_chk() {
		return _chk;
	}

	public void set_chk(String _chk) {
		this._chk = _chk;
	}

	public String getDataTypeBindId() {
		return DataTypeBindId;
	}

	public void setDataTypeBindId(String dataTypeBindId) {
		DataTypeBindId = dataTypeBindId;
	}

	public String getDataFromBindId() {
		return DataFromBindId;
	}

	public void setDataFromBindId(String dataFromBindId) {
		DataFromBindId = dataFromBindId;
	}

	public String getUIModelBindId() {
		return UIModelBindId;
	}

	public void setUIModelBindId(String uIModelBindId) {
		UIModelBindId = uIModelBindId;
	}

	public String getIsSettingBindId() {
		return IsSettingBindId;
	}

	public void setIsSettingBindId(String isSettingBindId) {
		IsSettingBindId = isSettingBindId;
	}

	public String getSelectCellHTML() {
		return selectCellHTML;
	}

	public void setSelectCellHTML(String selectCellHTML) {
		this.selectCellHTML = selectCellHTML;
	}

	public String getSeeDataFromAllTableHTML() {
		return seeDataFromAllTableHTML;
	}

	public void setSeeDataFromAllTableHTML(String seeDataFromAllTableHTML) {
		this.seeDataFromAllTableHTML = seeDataFromAllTableHTML;
	}

	public HeaderRowConfigModel getHeaderRowModel() {
		return headerRowModel;
	}

	public void setHeaderRowModel(HeaderRowConfigModel headerRowModel) {
		this.headerRowModel = headerRowModel;
	}

	public List<DictionaryPO> getYearList() {
		return yearList;
	}

	public void setYearList(List<DictionaryPO> yearList) {
		this.yearList = yearList;
	}

	public List<DictionaryPO> getReportFreqList() {
		return reportFreqList;
	}

	public void setReportFreqList(List<DictionaryPO> reportFreqList) {
		this.reportFreqList = reportFreqList;
	}

	public List<DictionaryPO> getReportStateList() {
		return reportStateList;
	}

	public void setReportStateList(List<DictionaryPO> reportStateList) {
		this.reportStateList = reportStateList;
	}

	public List<CommonTreeNodeModel> getYearTreeList() {
		return yearTreeList;
	}

	public void setYearTreeList(List<CommonTreeNodeModel> yearTreeList) {
		this.yearTreeList = yearTreeList;
	}

	public List<CommonTreeNodeModel> getConfigReportTreeList() {
		return configReportTreeList;
	}

	public void setConfigReportTreeList(
			List<CommonTreeNodeModel> configReportTreeList) {
		this.configReportTreeList = configReportTreeList;
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

	public String getParam4() {
		return param4;
	}

	public void setParam4(String param4) {
		this.param4 = param4;
	}

	public List<DictionaryPO> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<DictionaryPO> typeList) {
		this.typeList = typeList;
	}





}
