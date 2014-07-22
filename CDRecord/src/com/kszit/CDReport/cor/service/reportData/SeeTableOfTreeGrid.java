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
public class SeeTableOfTreeGrid extends InputTableOfTreeGrid {


	public SeeTableOfTreeGrid() {
	}

	/**
	 * 
	 * @param reportBindid
	 *            报表bindid
	 * @param dataBindid
	 */
	public SeeTableOfTreeGrid(String reportBindid, String dataBindid) {
		super(reportBindid,dataBindid);
	}
	
	void initTableHeader(){
		HeaderRowService hService = new HeaderRowService();
		List<HeaderRowConfigPO> hList = hService
				.getHeaderRowCByReportOfPO(reportBindid);
		super.tableHeader = new TableHeaderOfTreeGridNoEdit(hList);
	}
	
}
