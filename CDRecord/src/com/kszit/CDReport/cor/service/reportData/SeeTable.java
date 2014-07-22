package com.kszit.CDReport.cor.service.reportData;

/**
 * 查看报表
 * @author Administrator
 *
 */
public class SeeTable extends Table {
	
	String dataBindid = null;
	
	SeeTableContent tableContent = null;
	
	public SeeTable(){}
	
	/**
	 * 
	 * @param reportBindid 报表bindid
	 * @param dataBindid 
	 */
	public SeeTable(String reportBindid,String dataBindid){
		super(reportBindid,true);
		this.dataBindid = dataBindid;

		tableContent = new SeeTableContent(reportBindid,dataBindid,tableHeader,tableColumn,this);
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
