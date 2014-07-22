package com.kszit.CDReport.cor.model.graph;

import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;

public class GraphDataTableCell {

	private String deptId;
	
	private String detpaName;
	
	private String dataTime;
	
	private String dataTimeStr;
	
	
	private String reportLinkbindid;
	
	private String hbindid;
	
	private TableHeaderCell headerCell;
	
	
	private String hName;
	
	private String vbindid;
	
	private TableColumnCell columnCell;
	
	private String vName;
	
	private String value;

	public GraphDataTableCell(){}
	
	public GraphDataTableCell(String deptId,String dataTime,String hbindid,String vbindid,String value,String reportLinkbindid ){
		this.deptId = deptId;
		this.dataTime = dataTime;
		this.hbindid = hbindid;
		this.vbindid = vbindid;
		this.value = value;
		this.reportLinkbindid = reportLinkbindid;
	}
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDetpaName() {
		if(detpaName==null){
			detpaName = DepartmentUtil.instance().getDepartmentService().getDepartmentById(this.deptId).getDepartmentName();
		}
		return detpaName;
	}

	public void setDetpaName(String detpaName) {
		this.detpaName = detpaName;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	public String getHbindid() {
		return hbindid;
	}

	public void setHbindid(String hbindid) {
		this.hbindid = hbindid;
	}

	public String gethName() {
		if(hName==null){
			hName = this.getHeaderCell().getName().trim();
		}
		return hName;
	}

	public void sethName(String hName) {
		this.hName = hName;
	}

	public String getVbindid() {
		return vbindid;
	}

	public void setVbindid(String vbindid) {
		this.vbindid = vbindid;
	}

	public String getvName() {
		if(vName==null){
			vName = this.getColumnCell().getName().trim();
		}
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getReportLinkbindid() {
		return reportLinkbindid;
	}

	public void setReportLinkbindid(String reportLinkbindid) {
		this.reportLinkbindid = reportLinkbindid;
	}

	public String getDataTimeStr() {
		if(dataTimeStr==null){
			String[] temp = StringUtil.splits(this.dataTime, "-");
			dataTimeStr = temp[0]+"年"+StaticVaribles.getDicContent(temp[1]);
		}
		return dataTimeStr;
	}

	public void setDataTimeStr(String dataTimeStr) {
		this.dataTimeStr = dataTimeStr;
	}

	public TableHeaderCell getHeaderCell() {
		if(headerCell==null){
			headerCell = TableHeader.getOneHeader(this.reportLinkbindid, this.hbindid);
		}
		return headerCell;
	}

	public void setHeaderCell(TableHeaderCell headerCell) {
		this.headerCell = headerCell;
	}

	public TableColumnCell getColumnCell() {
		if(columnCell==null){
			columnCell = TableColumn.getOneColumn(this.reportLinkbindid, this.vbindid);
		}
		return columnCell;
	}

	public void setColumnCell(TableColumnCell columnCell) {
		this.columnCell = columnCell;
	}


	
	
}
