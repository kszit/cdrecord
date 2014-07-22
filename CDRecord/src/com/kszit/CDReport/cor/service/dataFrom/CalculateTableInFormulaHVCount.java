package com.kszit.CDReport.cor.service.dataFrom;

import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
/**
 * 横纵向计算公式
 * @author Administrator
 *
 */
public class CalculateTableInFormulaHVCount extends CalculateAbatract{

	public CalculateTableInFormulaHVCount(){
		super();
	}
	public CalculateTableInFormulaHVCount(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String calculate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		return "CalculateTableInFormulaHVCount.js****"+DataFromHtml.notConfig();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
		return "CalculateTableInFormulaHVCountOfCell.js****"+DataFromHtml.notConfig();
	}

	
}
