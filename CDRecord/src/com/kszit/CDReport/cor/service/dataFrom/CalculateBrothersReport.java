package com.kszit.CDReport.cor.service.dataFrom;

import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;
/**
 * 兄弟表数据取数据
 * @author Administrator
 *
 */
public class CalculateBrothersReport extends CalculateAbatract{

	
	public CalculateBrothersReport(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	public CalculateBrothersReport(){
		super();
	}
	@Override
	public String calculate() {

		
		String deptid = "901";
		String peroids = dataReportModel.getPeriods();
		
		List<DepartmentToReport> deptList = DepartmentUtil.instance().getDepartmentService().getDepartmentListOfChild("0");
		for(DepartmentToReport dept:deptList){
			String talbeBindidAndHVBindid = datafromPo.getFormula() + "#" + dept.getBindid();
			String repotBindid = StringUtil.getReportBindid(talbeBindidAndHVBindid);//配置中的数据
			String dataFromHVBindid = StringUtil.getHVBindid(talbeBindidAndHVBindid);//hbindid#vbindid,hbindid是配置中的数据
			String dataFromReprotDataBindid = "";

			DataReportPO po = new DataReportPO();
			po.setVerify(StaticVaribles.DataReportFlowState_publish);// 报表状态
			po.setCreateDepBindid(deptid);// 部门id
			po.setPeriods(peroids);// 期数
			po.setRepotBindid(repotBindid);// 报表id

			// 查询下级表的数据
			ReportDataService reportDataService = new ReportDataService();
			List<DataReportPO> poList = reportDataService.getDataReportListByLike(po);
			if (poList.size() >= 1) {
				DataReportPO dataReportPo = poList.get(0);
				dataFromReprotDataBindid = dataReportPo.getBindId() + "";//数据表的bindid，
			} else {
				return "";
			}

			ReportDataService dataCellService = new ReportDataService(
					repotBindid, dataFromReprotDataBindid);
			Map<String, String> dataCellValue = dataCellService
					.getDataReportCellDatas(dataFromReprotDataBindid);

			String value = dataCellValue.get(dataFromHVBindid);
			dataMap.put(datafromPo.getHbindid()+"#"+dept.getBindid(), value);
		}
		

		
		return CalculateAbatract.NOT_PUT_DATA;
	}
	
	/**
	 * 
	 * @param talbeBindidAndHVBindid 格式：reportlinkid|hbindid#vbindid
	 * @param deptid
	 * @param peroids
	 * @return
	 */
	public DataCellPO getCellValueFromOtherTable(String talbeBindidAndHVBindid,String deptid,String year,String peroids){
		String reportLinkBindid = StringUtil.getReportBindid(talbeBindidAndHVBindid);
		String hvBindid = StringUtil.getHVBindid(talbeBindidAndHVBindid);
		
		ReportDataService dataService = new ReportDataService(reportLinkBindid,deptid,year,peroids);
		DataCellPO dataCellValue = dataService.getDataReportCellValue(hvBindid);
		return dataCellValue;
	}

	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		String url = "/config/report/selectReportPart.jsp";
		StringBuffer sb = new StringBuffer();
		sb.append("CalculateBrothersReport.js****");
		
		sb.append("<table width=100%>			" +
				"<tr><td><iframe name=selectTableCellPageMain id=selectTableCellPageMain  scrolling=yes frameborder=0 width=100% height=410 src="+url+"></iframe></td>			</tr>			" +
				"</table>");
		return sb.toString();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
		String url = "/config/report/selectReportPart.jsp";
		StringBuffer sb = new StringBuffer();
		sb.append("CalculateBrothersReportOfCell.js****");
		
		sb.append("<table width=100%>			" +
				"<tr><td><iframe name=selectTableCellPageMain id=selectTableCellPageMain  scrolling=yes frameborder=0 width=100% height=410 src="+url+"></iframe></td>			</tr>			" +
				"</table>");
		return sb.toString();
	}

	
}
