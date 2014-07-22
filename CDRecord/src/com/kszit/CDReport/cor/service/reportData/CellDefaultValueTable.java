package com.kszit.CDReport.cor.service.reportData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigCellDValuePO;
import com.kszit.CDReport.cor.service.HVConfigCellDValueService;


public class CellDefaultValueTable extends Table {

	//数据来源
	Map<String,HVConfigCellDValuePO> dataFromMap = new HashMap<String,HVConfigCellDValuePO>();
	
	public CellDefaultValueTable(){}
	
	/**
	 * 
	 * @param reportBindid 报表bindid
	 * 
	 */
	public CellDefaultValueTable(String reportBindid){
		super(reportBindid,true);
		HVConfigCellDValueService dataFromService = new HVConfigCellDValueService();
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
					HVConfigCellDValuePO dataFromPo = getCellDefaultValue(headerCell,column);
					
					sb.append("<a id='"+dataFromPo.getHbindid()+"#"+dataFromPo.getVbindid()+"' href='#' onclick=\"openCellDefaultValueSetWin('"+dataFromPo.getReportBindid()+"',"+dataFromPo.getHbindid()+","+dataFromPo.getVbindid()+");return false;\" title='"+dataFromPo.getDefaultValue()+""+"'>");
					sb.append(dataFromPo.getDefaultValue()+"");
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
	
	private HVConfigCellDValuePO getCellDefaultValue(TableHeaderCell headerCell,TableColumnCell columnCell){
		long hbindid = headerCell.getHeaderRowConfigPO().getBindId();
		long vbindid = columnCell.getVerticalColumnConfigPO().getBindId();
		//
		if(dataFromMap != null && dataFromMap.containsKey(hbindid+"#"+vbindid)){
			HVConfigCellDValuePO dataFromPO = dataFromMap.get(hbindid+"#"+vbindid);
			return dataFromPO;
		}
		HVConfigCellDValuePO dataFromPO = new HVConfigCellDValuePO(super.reportBindid,hbindid,vbindid,"未设置");
		return dataFromPO;
	}
	
	public String outTable(){
		StringBuffer sb = new StringBuffer();
		sb.append(getTableHeader()+"<tr></tr><tr></tr><tr></tr>");
		sb.append(getTableContent());
		return sb.toString();
	}
}
