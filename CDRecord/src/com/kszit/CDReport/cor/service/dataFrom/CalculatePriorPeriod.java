package com.kszit.CDReport.cor.service.dataFrom;

import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.util.StringUtil;
/**
 * 上期数据
 * @author Administrator
 *
 */
public class CalculatePriorPeriod extends CalculateAbatract{
	public CalculatePriorPeriod(){
		super();
	}
	public CalculatePriorPeriod(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String calculate() {
		String year = dataReportModel.getYear();
		String peroids = dataReportModel.getPeriods();
		String deptid = dataReportModel.getCreateDepBindid();
		String talbeBindidAndHVBindid = datafromPo.getFormula();
		if(!talbeBindidAndHVBindid.contains("#")){
			talbeBindidAndHVBindid += "#" + datafromPo.getVbindid();
		}
		
		String reportLinkBindid = StringUtil.getReportBindid(talbeBindidAndHVBindid);
		String hvBindid = StringUtil.getHVBindid(talbeBindidAndHVBindid);
		
		ReportDataService dataService = new ReportDataService(reportLinkBindid,deptid,year,peroids);
		DataCellPO dataCellValue = dataService.getDataReportCellValue(hvBindid);
		
		
		
		

		
		return dataCellValue==null?"":dataCellValue.getDatavalue();
	}
	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		String url = "/config/report/selectReportPart.jsp";
		StringBuffer sb = new StringBuffer();
		sb.append("CalculatePriorPeriod.js****");
		
		sb.append("<table width=100%>			" +
				"<tr><td><iframe name=selectTableCellPageMain id=selectTableCellPageMain  scrolling=yes frameborder=0 width=100% height=410 src="+url+"></iframe></td>			</tr>			" +
				"</table>");
		return sb.toString();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
		String url = "/config/report/selectReportPart.jsp";
		StringBuffer sb = new StringBuffer();
		sb.append("CalculatePriorPeriodOfCell.js****");
		
		sb.append("<table width=100%>			" +
				"<tr><td><iframe name=selectTableCellPageMain id=selectTableCellPageMain  scrolling=yes frameborder=0 width=100% height=410 src="+url+"></iframe></td>			</tr>			" +
				"</table>");
		return sb.toString();
	}

	
}
