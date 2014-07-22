package com.kszit.CDReport.cor.service.reportData;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.DBQueryUtilDao;
import com.kszit.CDReport.cor.dao.hibernate.DBQueryUtilDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigUIModelPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.HVConfigCellDValueService;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.HVConfigUIModelService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.dataFrom.DataFrom;
import com.kszit.CDReport.cor.ui.IUIModel;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.ClassInstallUtil;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;

abstract class TableContent {

	Table table = null;

	TableHeader tableHeader = null;
	TableColumn tableColumn = null;
	String reportBindid;
	String dataId;

	// 数据记录表
	DataReportPO dataReportPo = null;

	// 数据值
	Map<String, String> dataMap = new HashMap<String, String>();
	
	// 单元格默认值 数据值
	Map<String, String> cellDefaultValueMap = new HashMap<String, String>();

	// 数据来源
	Map<String, HVConfigDataFromPO> dataFromMap = new HashMap<String, HVConfigDataFromPO>();

	// UI Model
	Map<String, HVConfigUIModelPO> UIModelMap = new HashMap<String, HVConfigUIModelPO>();

	public TableContent() {
	}

	public TableContent(String reportBindid, String dataId,
			TableHeader tableHeader, TableColumn tableColumn, Table inputTable) {
		this.table = inputTable;
		this.tableHeader = tableHeader;
		this.tableColumn = tableColumn;
		this.reportBindid = reportBindid;
		this.dataId = dataId;
	}

	/**
	 * 初始化报表数据
	 * 
	 */
	void initDataTable() {
		ReportDataService reportDataService = new ReportDataService(
				this.reportBindid, this.dataId);
		dataMap = reportDataService.getDataReportCellDatas(this.dataId);
		dataReportPo = reportDataService.getDataReportByBindid(Long
				.parseLong(this.dataId));
	}

	/**
	 * 初始化数据来源
	 */
	void initDataFrom() {
		HVConfigDataFromService dataFromService = new HVConfigDataFromService();
		dataFromMap = dataFromService.getDataFromMap(reportBindid);
	}

	/**
	 * 初始化UIModel
	 * 
	 */
	void initUIModel() {
		HVConfigUIModelService uiModelService = new HVConfigUIModelService();
		UIModelMap = uiModelService.getUIModelMap(reportBindid);
	}
	
	/**
	 * 
	 * 初始化 配置的 单元格默认值
	 * 
	 */
	void initCellDefaultValue() {
		HVConfigCellDValueService cellDValueService = new HVConfigCellDValueService();
		
		cellDefaultValueMap = cellDValueService.getCellDefaultStringValues(reportBindid);
	}
	

	/**
	 * 左侧列及输入框的html代码
	 * 
	 * @return
	 */
	public String getHtml() {
		List<TableColumnCell> columnCellList = tableColumn
				.getAllHeaderCellTree();
		return getHtmlCodeByColumnList(columnCellList, 0);
	}

	/**
	 * 表格数据的 json 格式
	* @Title: getGridJsonData 
	* @Description:  
	* @param：
	* @return： String
	* @throws：
	 */
	public String getGridJsonData() {
		List<TableColumnCell> colsList = tableColumn.getAllHeaderCellList();
		int colSize = colsList.size();
		
		StringBuffer sb = new StringBuffer();
		sb.append("{\"total\":"+colSize+",\"rows\":[	");
		
		sb.append(getGridJsonByColumnList(colsList));
		
		sb.append("]}");
		
		
		return sb.toString();
	}
	
	/**
	 * 
	* @Title: getGridJsonByColumnList 
	* @Description:  
	* @param：
	* @return： String
	* @throws：
	 */
	public String getGridJsonByColumnList(List<TableColumnCell> columnCellList) {
		StringBuffer sb = new StringBuffer();
		for (TableColumnCell column : columnCellList) {
			String colName = "";
			// 单位列表
			if (column.getVerticalColumnConfigPO().getUIModule() == StaticVaribles.HTMLUIModule_Org) {
				List<DepartmentToReport> deptList = new DepartmentUtil()
						.getDepartmentService().getDepartmentList("0");
				for (DepartmentToReport dept : deptList) {
					colName = dept.getDepartmentName();
					sb.append(getRowJSON(colName, column, dept));
				}
			} else if (column.getVerticalColumnConfigPO().getUIModule() == StaticVaribles.HTMLUIModule_AreaList) {
				DictionaryService dicService = new DictionaryService();
				DictionaryPO dicPo = dicService.getOneByBindid(column.getVerticalColumnConfigPO().getUIModule()+"");
				IUIModel uiModel = (IUIModel)ClassInstallUtil.installClass(dicPo.getDescribe2());
				Map<String, String> areMap = uiModel.tableLeftOfDynamicsCell();	
				Iterator<String> iter = areMap.keySet().iterator();
				while(iter.hasNext()){
					String bindid = (String)iter.next();
					colName = areMap.get(bindid);
					DepartmentToReport dept = new DepartmentToReport();
					dept.setBindid(bindid);
					sb.append(getRowJSON(colName, column, dept));
				}
			} else {
				colName = column.getName();
				sb.append(getRowJSON(colName, column, null));
			}
		}
		sb.deleteCharAt(sb.length()-1);
		return sb.toString();
	}
	
	abstract String getRowJSON(String colName, TableColumnCell column,DepartmentToReport dept);
		
	
	/**
	 * 递归调用此方法，生成树形的行数据
	 * 
	 * @param columnCellList
	 * @param leve
	 * @return
	 */
	public String getHtmlCodeByColumnList(List<TableColumnCell> columnCellList,
			int leve) {
		StringBuffer sb = new StringBuffer();
		String leveStr = "";
		for (int i = 0; i < leve; i++) {
			leveStr += "---";
		}
		for (TableColumnCell column : columnCellList) {
			// 单位列表
			if (column.getVerticalColumnConfigPO().getUIModule() == StaticVaribles.HTMLUIModule_Org) {
				List<DepartmentToReport> deptList = new DepartmentUtil()
						.getDepartmentService().getDepartmentList("0");
				for (DepartmentToReport dept : deptList) {
					String colName = leveStr + dept.getDepartmentName();
					sb.append(getOneInputRow(colName, column, dept));
				}
			} else if (column.getVerticalColumnConfigPO().getUIModule() == StaticVaribles.HTMLUIModule_AreaList) {
				DictionaryService dicService = new DictionaryService();
				DictionaryPO dicPo = dicService.getOneByBindid(column.getVerticalColumnConfigPO().getUIModule()+"");
				IUIModel uiModel = (IUIModel)ClassInstallUtil.installClass(dicPo.getDescribe2());
				Map<String, String> areMap = uiModel.tableLeftOfDynamicsCell();	
				Iterator<String> iter = areMap.keySet().iterator();
				while(iter.hasNext()){
					String bindid = (String)iter.next();
					String colName = areMap.get(bindid);
					colName = leveStr + colName;
					DepartmentToReport dept = new DepartmentToReport();
					dept.setBindid(bindid);
					sb.append(getOneInputRow(colName, column, dept));
					
				}
				
				
			} else {
				String colName = leveStr + column.getName();
				sb.append(getOneInputRow(colName, column, null));
			}
			// 循环孩子节点
			List<TableColumnCell> childs = column.getChilds();
			if (childs != null) {
				sb.append(getHtmlCodeByColumnList(childs,
						column.getCurrentLayer()));
			}
			sb.append("</tr>");
		}

		return sb.toString();
	}

	abstract String getOneInputRow(String colName, TableColumnCell column,
			DepartmentToReport dept);

	/**
	 * 获得数据
	 * 
	 * @param hcell
	 *            横向单元格
	 * @param vcell
	 *            纵向单元格
	 * @return
	 */
	public String getCellData(TableHeaderCell hcell, TableColumnCell vcell,
			DepartmentToReport dept) {
		String defaultValue = getCellData(hcell.getHeaderRowConfigPO(),
				vcell.getVerticalColumnConfigPO(), dept);
		
		if(defaultValue==null){
			defaultValue = getCellDefaultValue(hcell.getHeaderRowConfigPO(),
					vcell.getVerticalColumnConfigPO(), dept);
		}
		
		return defaultValue;
	}

	/**
	 * 列项配置的单元格默认值， 获得数据
	 * 
	 * @param hconfig
	 * @param vconfig
	 * @return
	 */
	public String getCellDefaultValue(HeaderRowConfigPO hconfig,
			VerticalColumnConfigPO vconfig, DepartmentToReport dept) {
		String hBindid = hconfig.getBindId() + "";
		String vBindid = vconfig.getBindId() + "";
		if (dept != null) {
			vBindid = dept.getBindid();
		}
		String data = "";
		if (cellDefaultValueMap != null) {
			data = cellDefaultValueMap.get(hBindid + "#" + vBindid);
		}
		return data;
	}

	/**
	 * 获得数据    用户填写的数据
	 * 
	 * @param hconfig
	 * @param vconfig
	 * @return
	 */
	public String getCellData(HeaderRowConfigPO hconfig,
			VerticalColumnConfigPO vconfig, DepartmentToReport dept) {
		String hBindid = hconfig.getBindId() + "";
		String vBindid = vconfig.getBindId() + "";
		if (dept != null) {
			vBindid = dept.getBindid();
		}
		String data = "";
		if (dataMap != null) {
			data = dataMap.get(hBindid + "#" + vBindid);
		}
		return data;
	}
	
	/**
	 * 获得数据
	 * 
	 * @param hconfig
	 * @param vconfig
	 * @return
	 */
	public String getCellData(String hbindid, String vbindid) {
		String data = "";
		if (dataMap != null) {
			data = dataMap.get(hbindid + "#" + vbindid);
		}
		return data;
	}

	/**
	 * 从其他业务表中获取数据
	 * @param vbindid
	 * @param busessessTaInfo
	 * @return
	 */
	public String getCellDataFrombusinessTable(String vbindid,String busessessTaInfo) {
		String returnValue = null;
		String[] businessTables = busessessTaInfo.split("\\|");
		String tableName = businessTables[0];
		String tableCol = businessTables[1];
		String tableId = businessTables[2];
		String sql = "select "+tableCol +" from "+ tableName +" where "+tableId +"=" + vbindid; 
		DBQueryUtilDao dbQueryUtil = new DBQueryUtilDaoHiberImpl();
		List<Object[]> resule = dbQueryUtil.exceleSql(sql);
		if(resule!=null && resule.size()==0){
			returnValue = "";
		}else{
			//String[] o = (String[])resule.get(0);
			System.out.println("");
			returnValue = resule.get(0)+"";
		}
		return returnValue;
	}
	/**
	 * 从其他表中获取数据
	 * 
	 * @param hconfig
	 * @param vconfig
	 * @return
	 */
	public String getCellDataFromOthorTable(HeaderRowConfigPO hconfig,
			VerticalColumnConfigPO vconfig, DepartmentToReport dept) {
		String data = "";
		HVConfigDataFromPO dataFromPo = getCellDataFromWithHV(hconfig, vconfig);
		String dataFromType = dataFromPo.getDataFromType();
		if (dataFromType != null
				&& dataFromType.equals(StaticVaribles.DataFrom_LowerLevelCell
						+ "")) {
			if (dataReportPo == null) {
				return "";
			}

			String repotBindid = dataFromPo.getFormulaOfLowerReportBindid();
			String dataFromHVBindid = dataFromPo
					.getFormulaOfLowerReportHVBindid();
			String dataFromReprotDataBindid = "";

			String dataRule = dataFromPo.getFormula();
			String depId = dept.getBindid();

			String periods = dataReportPo.getPeriods();

			// 参数：单位id，期数，报表链接id
			DataReportPO po = new DataReportPO();
			po.setVerify(StaticVaribles.DataReportFlowState_publish);// 报表状态
			po.setCreateDepBindid(depId);// 部门id
			po.setPeriods(periods);// 期数
			po.setRepotBindid(repotBindid);// 报表id

			// 查询下级表的数据
			ReportDataService reportDataService = new ReportDataService();
			List<DataReportPO> poList = reportDataService
					.getDataReportListByLike(po);
			if (poList.size() >= 1) {
				DataReportPO dataReportPo = poList.get(0);
				dataFromReprotDataBindid = dataReportPo.getBindId() + "";
			} else {
				return "";
			}

			ReportDataService dataCellService = new ReportDataService(
					repotBindid, dataFromReprotDataBindid);
			Map<String, String> dataCellValue = dataCellService
					.getDataReportCellDatas(dataFromReprotDataBindid);

			String value = dataCellValue.get(dataFromHVBindid);
			data = value;

		} else if (dataFromType != null
				&& dataFromType
						.equals(StaticVaribles.DataFrom_BrothersReportData + "")) {

			String deptid = "901";
			String peroids = dataReportPo.getPeriods();
			String talbeBindidAndHVBindid = dataFromPo.getFormula() + "#"
					+ dept.getBindid();

			String repotBindid = StringUtil
					.getReportBindid(talbeBindidAndHVBindid);
			String dataFromHVBindid = StringUtil
					.getHVBindid(talbeBindidAndHVBindid);
			String dataFromReprotDataBindid = "";

			DataReportPO po = new DataReportPO();
			po.setVerify(StaticVaribles.DataReportFlowState_publish);// 报表状态
			po.setCreateDepBindid(deptid);// 部门id
			po.setPeriods(peroids);// 期数
			po.setRepotBindid(repotBindid);// 报表id

			// 查询下级表的数据
			ReportDataService reportDataService = new ReportDataService();
			List<DataReportPO> poList = reportDataService
					.getDataReportListByLike(po);
			if (poList.size() >= 1) {
				DataReportPO dataReportPo = poList.get(0);
				dataFromReprotDataBindid = dataReportPo.getBindId() + "";
			} else {
				return "";
			}

			ReportDataService dataCellService = new ReportDataService(
					repotBindid, dataFromReprotDataBindid);
			Map<String, String> dataCellValue = dataCellService
					.getDataReportCellDatas(dataFromReprotDataBindid);

			String value = dataCellValue.get(dataFromHVBindid);
			data = value;

			/*
			 * CalculateBrothersReport brothersReport = new
			 * CalculateBrothersReport(); DataCellPO dataCellPO =
			 * brothersReport.getCellValueFromOtherTable(talbeBindidAndHVBindid,
			 * deptid, peroids); if(dataCellPO==null){ data = ""; }else{ data =
			 * dataCellPO.getDatavalue(); }
			 * System.out.println(talbeBindidAndHVBindid+":"+data);
			 */
		}

		return data;
	}

	/**
	 * 结合横行、纵向和单元格数据来源的配置情况，获得单元格的实际数据来源关系
	 * 
	 * @param hcell
	 * @param vcell
	 * @return
	 */
	public HVConfigDataFromPO getCellDataFromWithHV(HeaderRowConfigPO hconfig,
			VerticalColumnConfigPO vconfig) {
		HVConfigDataFromPO dataFromPo = DataFrom.getDataFromPO(hconfig,
				vconfig, getCellDataFrom(hconfig, vconfig));
		return dataFromPo;
	}

	/**
	 * 获得数据来源,具体某个单元格的数据源配置
	 * 
	 * @param hcell
	 *            横向单元格
	 * @param vcell
	 *            纵向单元格
	 * @return
	 */
	public HVConfigDataFromPO getCellDataFrom(TableHeaderCell hcell,
			TableColumnCell vcell) {
		return getCellDataFrom(hcell.getHeaderRowConfigPO(),
				vcell.getVerticalColumnConfigPO());
	}

	/**
	 * 获得数据来源,具体某个单元格的数据源配置
	 * 
	 * @param hconfig
	 * @param vconfig
	 * @return
	 */
	public HVConfigDataFromPO getCellDataFrom(HeaderRowConfigPO hconfig,
			VerticalColumnConfigPO vconfig) {
		HVConfigDataFromPO po = null;
		if (dataFromMap != null) {
			po = dataFromMap.get(hconfig.getBindId() + "#"
					+ vconfig.getBindId());
		}
		return po;
	}

	/**
	 * 获得UI model  特定单元格
	 * 
	 * @param hcell
	 *            横向单元格
	 * @param vcell
	 *            纵向单元格
	 * @return
	 */
	public HVConfigUIModelPO getCellUIModel(TableHeaderCell hcell,
			TableColumnCell vcell) {
		return getCellUIModel(hcell.getHeaderRowConfigPO(),
				vcell.getVerticalColumnConfigPO());
	}

	/**
	 * 获得UI model   特定单元格
	 * 
	 * @param hconfig
	 * @param vconfig
	 * @return
	 */
	public HVConfigUIModelPO getCellUIModel(HeaderRowConfigPO hconfig,
			VerticalColumnConfigPO vconfig) {
		HVConfigUIModelPO po = null;
		if (UIModelMap != null) {
			po = UIModelMap
					.get(hconfig.getBindId() + "#" + vconfig.getBindId());
		}
		return po;
	}
	
	/**
	 * 综合横向配置和单元格配置情况，获得单元格的UI model
	* @Title: getCellUIModelOfAll 
	* @Description:  
	* @param：
	* @return： HVConfigUIModelPO
	* @throws：
	 */
	public HVConfigUIModelPO getCellUIModelOfAll(HeaderRowConfigPO hconfig,
			VerticalColumnConfigPO vconfig) {
		
		HVConfigUIModelPO cellUiModel = this.getCellUIModel(hconfig, vconfig);
		HVConfigUIModelPO uiModel = new HVConfigUIModelPO(hconfig, null, cellUiModel);
		
		return uiModel;
	}
	
	
}
