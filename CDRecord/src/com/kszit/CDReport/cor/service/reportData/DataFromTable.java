package com.kszit.CDReport.cor.service.reportData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.dataFrom.DataFrom;
import com.kszit.CDReport.util.StaticVaribles;


public class DataFromTable extends Table {

	//数据来源
	Map<String,HVConfigDataFromPO> dataFromMap = new HashMap<String,HVConfigDataFromPO>();
	
	public DataFromTable(){}
	
	/**
	 * 
	 * @param reportBindid 报表bindid
	 * 
	 */
	public DataFromTable(String reportBindid){
		super(reportBindid,true);
		HVConfigDataFromService dataFromService = new HVConfigDataFromService();
		dataFromMap = dataFromService.getDataFromMap(reportBindid);
	}
	
	/**
	 * 拼表体
	 * @return
	 */
	String getTableContent(){
		
		List<TableColumnCell> columnCellList = tableColumn.getAllHeaderCellTree();
		return getHtmlCodeByColumnList(columnCellList,0);
	}
	
	/**
	 * 输出 表体的每一行
	 * 
	* getHtmlCodeByColumnList(这里用一句话描述这个方法的作用) <br> 
	* TODO(这里描述这个方法适用条件 – 无)  <br>
	* TODO(这里描述这个方法的执行流程 – 无)  <br>
	* TODO(这里描述这个方法的使用方法 – 无)  <br>
	* TODO(这里描述这个方法的注意事项 – 无)  <br>
	* @param name  
	* @param @return 设定文件  - 无
	* @return String DOM对象  - 无
	* @Exception 异常对象  - 无
	* @since  -
	 */
	private String getHtmlCodeByColumnList(List<TableColumnCell> columnCellList,int leve){
		StringBuffer sb = new StringBuffer();
		String leveStr = "";
		for(int i=0;i<leve;i++){
			leveStr += "---";
		}
		for(TableColumnCell column:columnCellList){
			sb.append("<tr>");
			sb.append("<td>"+leveStr+column.getName()+"</td>");
			List<TableHeaderCell> headerCellList = tableHeader.getInputHeaderCellList();//表头
			for(int i=1,headerlength=headerCellList.size();i<headerlength;i++){//除第一列外的其他单元格
				TableHeaderCell headerCell = headerCellList.get(i);
				if(headerCell.isLeaf()){
					sb.append("<td>");
//System.out.println(leveStr+column.getName()+"==="+headerCell.getName());
					HVConfigDataFromPO dataFromPo = getCellDataFromType(headerCell,column);
					String dataItemDeclare = dataFromPo.getDataItemDeclare();
					sb.append("<a href='#' onclick=\"openCellDataFromSetWin('"+dataFromPo.getReportBindid()+"',"+dataFromPo.getHbindid()+","+dataFromPo.getVbindid()+");return false;\" title='"+dataItemDeclare+"'>");
					sb.append(StaticVaribles.getDicContent(dataFromPo.getDataFromType()+""));
					sb.append("</a>");
					sb.append("</td>");
				}
			}
			List<TableColumnCell> childs = column.getChilds();
			if(childs!=null){
				sb.append(getHtmlCodeByColumnList(childs,column.getCurrentLayer()));
			}
			sb.append("</tr>");
		}
		return sb.toString();
	}
	
	private HVConfigDataFromPO getCellDataFromType(TableHeaderCell headerCell,TableColumnCell columnCell){
		HVConfigDataFromPO dataFromPo = DataFrom.getDataFromPO(headerCell.getHeaderRowConfigPO(), columnCell.getVerticalColumnConfigPO(), dataFromMap);
		return dataFromPo;
	}
	
	public String outTable(){
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader()+"<tr></tr><tr></tr><tr></tr>");
		sb.append(getTableContent());
		return sb.toString();
	}
}
