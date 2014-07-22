package com.kszit.CDReport.cor.service.reportData;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.service.ReportConfigService;


abstract class Table {
	
	String reportBindid = null;
	
	public TableHeader tableHeader = null;
	public TableColumn tableColumn = null;
	
    public ReportConfigPO reportConfigPo = null;
        
        
	public Table(){}
	
	/**
	 * 
	 * @param reportBindid 报表bindid
	 * 
	 */
	public Table(String reportBindid,boolean isInit){
		this.reportBindid = reportBindid;
		
                reportConfigPo = new ReportConfigService().getReportConfigByBindidLink(reportBindid);
                
		if(isInit){
			this.tableHeader = TableHeader.getTableHeader(reportBindid);
			this.tableColumn = TableColumn.getTableColumn(reportBindid);
		}
		
	}
	
	/**
	 * 拼表头
	 * @return
	 */
	public String getTableHeader(){
		return tableHeader.getAllHeaderHtmlCode();
	}
	
	/**
	 * 拼表体
	 * @return
	 */
	abstract String getTableContent();
	
	
	public String getTable(){
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader());
		sb.append(getTableContent());
		return sb.toString();
	}
	
}
