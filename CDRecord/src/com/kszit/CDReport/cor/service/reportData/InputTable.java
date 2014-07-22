package com.kszit.CDReport.cor.service.reportData;

public class InputTable extends Table {

	String dataBindid = null;

	InputTableContent tableContent = null;

	public InputTable() {
	}

	/**
	 * 
	 * @param reportBindid
	 *            报表bindid
	 * @param dataBindid
	 */
	public InputTable(String reportBindid, String dataBindid) {
		super(reportBindid,true);
		this.dataBindid = dataBindid;

		tableContent = new InputTableContent(reportBindid, dataBindid,
				tableHeader, tableColumn,this);
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
