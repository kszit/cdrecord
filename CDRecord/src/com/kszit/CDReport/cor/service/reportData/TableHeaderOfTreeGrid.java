package com.kszit.CDReport.cor.service.reportData;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;

/**
 * input TreeGrid 的头部，
*  
* 项目名称：CDRecord  
* 类名称：TableHeaderOfTreeGrid  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月25日 下午10:07:26  
* 修改人：hanxiaowei  
* 修改时间：2014年3月25日 下午10:07:26  
* 修改备注：  
* @version  
*
 */
public class TableHeaderOfTreeGrid extends TableHeaderOfGrid{

	
	public TableHeaderOfTreeGrid(List<HeaderRowConfigPO> hList) {
		super(hList);
	}


	/**
	 * 调用此方法，生成grid的头部；
	 * 全部表头，包括一级、二级、或者三级表头
	 * 
	 * @return
	 */
	public String getAllHeaderHtmlCode() {
		StringBuffer sb = new StringBuffer();
		sb.append("<table id='roleEditGrid' data-options=\"toolbar:toolbar,striped:true\" pagination='false' idField='id' rownumbers='true'	fitColumns='true' singleSelect='true' fit='true' border='0' >	<thead>");
		sb.append(super.getHtmlCodeByCellList(allHeaderCellList));
		sb.append("</thead></table>");
		return sb.toString(); 
	}
	
	

}
