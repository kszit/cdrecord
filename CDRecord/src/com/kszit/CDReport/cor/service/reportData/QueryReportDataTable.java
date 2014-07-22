package com.kszit.CDReport.cor.service.reportData;

import com.kszit.CDReport.cor.model.QueryReportConditionModel;
import com.kszit.CDReport.cor.service.ReportDataService;

/**
 * 查看报表
 * @author Administrator
 *
 */
public class QueryReportDataTable extends Table {
	
	String dataBindid = null;
	
	QueryReportDataTableContent tableContent = null;
	
	public QueryReportConditionModel queryModel = null;
	
	public QueryReportDataTable(QueryReportConditionModel queryModel){

	}
	
	/**
	 * 
	 * @param reportBindid 报表bindid
	 * @param dataBindid 
	 */
	public QueryReportDataTable(QueryReportConditionModel queryModel,String dataBindid){
		super(queryModel.getReportBindid(),false);
		this.dataBindid = dataBindid;
		this.queryModel = queryModel;
		
		this.tableHeader = TableHeader.getTableHeader(reportBindid);
		this.tableColumn = TableColumn.getTableColumn(reportBindid);
		
		tableContent = new QueryReportDataTableContent(reportBindid,dataBindid,tableHeader,tableColumn,this);
	}
	
	/**
	 * 拼表体
	 * @return
	 */
	public String getTableContent(){
		return tableContent.getHtml();
	}
	
	
	public String getTable(){
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader()+"<tr></tr><tr></tr><tr></tr>");
		sb.append(getTableContent());
		return sb.toString();
	}
	
}
