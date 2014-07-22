package com.kszit.CDReport.cor.service.reportData;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.service.HeaderRowService;

/**
 * 
*  
* 项目名称：CDRecord  
* 类名称：InputTableOfGrid  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月23日 下午10:33:38  
* 修改人：hanxiaowei  
* 修改时间：2014年3月23日 下午10:33:38  
* 修改备注：  
* @version  
*
 */
public class InputTableOfGrid extends Table {

	String dataBindid = null;

	TableContent tableContent = null;

	public InputTableOfGrid() {
	}

	/**
	 * 
	 * @param reportBindid
	 *            报表bindid
	 * @param dataBindid
	 */
	public InputTableOfGrid(String reportBindid, String dataBindid) {
		super(reportBindid,true);
		this.dataBindid = dataBindid;
		
		
		initTableHeader();
	}
	
	void initTableHeader(){
		HeaderRowService hService = new HeaderRowService();
		List<HeaderRowConfigPO> hList = hService
				.getHeaderRowCByReportOfPO(reportBindid);
		super.tableHeader = new TableHeaderOfGrid(hList);
	}

	public String getTableData(){
		tableContent = new TableContentOfGrid(reportBindid, dataBindid,
				tableHeader, tableColumn,(Table)this);
		return tableContent.getGridJsonData();
		
	}
	
	/**
	 * 拼表体
	 * 
	 * @return
	 */
	public String getTableContent() {
		return "";
	}

	public String getTable() {
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader()+"");
		sb.append(getTableContent());
		return sb.toString();
	}

}
