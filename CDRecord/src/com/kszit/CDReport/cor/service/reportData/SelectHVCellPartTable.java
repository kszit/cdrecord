package com.kszit.CDReport.cor.service.reportData;

public class SelectHVCellPartTable extends Table {

	SelectHVCellPartTableContent tableContent = null;

	public SelectHVCellPartTable() {
	}


	/**
	 * 
	* 创建一个新的实例 SelectHVCellPartTable.  
	*  
	* @param reportBindid
	* @param selectedCells
	 */
	public SelectHVCellPartTable(String reportBindid,String selectedCells) {
		super(reportBindid,true);

		tableContent = new SelectHVCellPartTableContent(reportBindid, null,
				tableHeader, tableColumn,this,selectedCells);
	}

	/**
	 * 拼表体
	 * 
	 * @return
	 */
	public String getTableContent() {
		return tableContent.getHtml();
	}

	public String getTable() {
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader()+"<tr></tr><tr></tr><tr></tr>");
		sb.append(getTableContent());
		return sb.toString();
	}

}
