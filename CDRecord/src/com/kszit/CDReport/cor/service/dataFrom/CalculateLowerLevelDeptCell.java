package com.kszit.CDReport.cor.service.dataFrom;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
/**
 * 所有列子项和
 * @author Administrator
 *
 */
public class CalculateLowerLevelDeptCell extends CalculateAbatract{

	public CalculateLowerLevelDeptCell(){
		super();
	}
	public CalculateLowerLevelDeptCell(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 功能：从下级表中获得数据，
	 * 
	 * 实现：在配置（datafromPo.formula）中，只包括了下级配置表的reportLink的id和单元格的hbindid、vbindid,<br>
	 * 期数：从当前报表中获取，<br>
	 * 部门id:从当期报表中获得部门的id，通过接口查找部门下的子部门。
	 * 
	* (non-Javadoc)  
	* @see com.kszit.CDReport.cor.service.dataFrom.CalculateAbatract#calculate()
	 */
	@SuppressWarnings("static-access")
	@Override
	public String calculate() {
		long addResult = 0;
		String repotBindid = datafromPo.getFormulaOfLowerReportBindid();
		String dataFromHVBindid = datafromPo.getFormulaOfLowerReportHVBindid();
		
		String periods = dataFrom.model.getPeriods();
		long hBindid = datafromPo.getHbindid();
		
		// 参数：单位id，期数，报表链接id
		DataReportPO po = new DataReportPO();
		po.setVerify(StaticVaribles.DataReportFlowState_publish);// 报表状态
		po.setPeriods(periods);// 期数
		po.setRepotBindid(repotBindid);// 报表id
		
		List<DepartmentToReport> deptList = DepartmentUtil.instance().getDepartmentService().getDepartmentListOfChild(dataFrom.model.getCreateDepBindid()+"");
		for(DepartmentToReport dept:deptList){
			String depId = dept.getBindid();
			po.setCreateDepBindid(depId);// 部门id
			

			// 查询下级表的数据
			ReportDataService reportDataService = new ReportDataService();
			List<DataReportPO> poList = reportDataService.getDataReportListByLike(po);
			String dataFromReprotDataBindid = "";
			if (poList.size() >= 1) {
				DataReportPO dataReportPo = poList.get(0);
				dataFromReprotDataBindid = dataReportPo.getBindId() + "";
			} else {
				return "";
			}

			ReportDataService dataCellService = new ReportDataService(repotBindid, dataFromReprotDataBindid);
			Map<String, String> dataCellValue = dataCellService.getDataReportCellDatas(dataFromReprotDataBindid);

			String value = dataCellValue.get(dataFromHVBindid);
			dataMap.put(hBindid+"#"+depId, value);
		}
		

		
		return CalculateAbatract.NOT_PUT_DATA;
	}

	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		return "CalculateLowerLevelDeptCell.js****"+DataFromHtml.notConfig();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
		return "CalculateLowerLevelDeptCellOfCell.js****"+DataFromHtml.notConfig();
	}
}
