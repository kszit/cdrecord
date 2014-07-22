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
public class CalculateHandInput extends CalculateAbatract{

	
	public CalculateHandInput(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	
	
	public CalculateHandInput(){
		super();
	}
	
	
	@Override
	public String calculate() {
		return "";
	}
	

	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		return "CalculateHandInput.js****"+DataFromHtml.notConfig();
	}


	@Override
	public String getCellDataFromHtml(String reportLinkid) {
		return "CalculateHandInputOfCell.js****"+DataFromHtml.notConfig();
	}

	
}
