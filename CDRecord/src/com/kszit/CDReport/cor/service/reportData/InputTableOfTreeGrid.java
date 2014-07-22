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
public class InputTableOfTreeGrid extends InputTableOfGrid {

	TableContent tableContent = null;

	public InputTableOfTreeGrid() {
	}

	/**
	 * 
	 * @param reportBindid
	 *            报表bindid
	 * @param dataBindid
	 */
	public InputTableOfTreeGrid(String reportBindid, String dataBindid) {
		super(reportBindid,dataBindid);
		
		
	}
	
	void initTableHeader(){
		HeaderRowService hService = new HeaderRowService();
		List<HeaderRowConfigPO> hList = hService
				.getHeaderRowCByReportOfPO(reportBindid);
		super.tableHeader = new TableHeaderOfTreeGrid(hList);
	}
	
	/**
	 * 表格数据  JSON格式
	* (non-Javadoc)  
	* @see com.kszit.CDReport.cor.service.reportData.InputTableOfGrid#getTableData()
	 */
	public String getTableData(){
		tableContent = new TableContentOfTreeGrid(reportBindid, dataBindid,
				tableHeader, tableColumn,(Table)this);
		return tableContent.getGridJsonData();
	}
	
	/**
	 * TreeGrid root 节点
	* @Title: treeNodeBindid 
	* @Description:  
	* @param：
	* @return： String
	* @throws：
	 */
	public String treeNodeBindid(){
		 List<TableHeaderCell> hList = super.tableHeader.allHeaderCellList;
		 TableHeaderCell small = null;
		 for(TableHeaderCell hCell:hList){
			 if(small==null){
				 small=hCell;
				 continue;
			 }
			 String key1 = small.getHeaderRowConfigPO().getOrderIndexStr();
			 String key2 = hCell.getHeaderRowConfigPO().getOrderIndexStr();
			 if(key1.contains(key2)){
				 small = hCell;
			 }
		 }
		 return small.getHeaderRowConfigPO().bindId+"";
	}
}
