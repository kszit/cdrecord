package com.kszit.CDReport.cor.service.reportData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.QueryReportConditionModel;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.QueryReportUtil;
import com.kszit.CDReport.util.StaticVaribles;



public class QueryReportDataTableContent extends TableContent{
	
	
	
	public QueryReportDataTableContent(){}
	
	public QueryReportDataTableContent(String reportBindid,String dataId,TableHeader tableHeader,TableColumn tableColumn,
			Table inputTable){
		super(reportBindid, dataId,tableHeader, tableColumn,inputTable);
		initDataTable();
		initDataFrom();
		initUIModel();
		
		
		this.tableColumn.changeColumnName();
		
		this.tableColumn.ridRelationship();
		
		queryDataByHCondition();
		
		QueryReportDataTable queryTable = (QueryReportDataTable)this.table;
		String hSortBindid = queryTable.queryModel.getHSort();
		if(!"".equals(hSortBindid)){
			sortData();
		}
		this.tableColumn.setAllHeaderCellTree(this.tableColumn.getAllHeaderCellList());
		
	}
	
	public void sortData(){
		
		
		QueryReportDataTable queryTable = (QueryReportDataTable)this.table;
		String hSortBindid = queryTable.queryModel.getHSort();
		Iterator<TableColumnCell> vIter  = this.tableColumn.getAllHeaderCellList().iterator();
		while(vIter.hasNext()){
			TableColumnCell cCell = vIter.next();
			String key = hSortBindid+"#"+cCell.getVerticalColumnConfigPO().getBindId();
			String value = this.dataMap.get(key);
			cCell.setHeaderOrder(value);
			System.out.println(cCell.getName()+"====>"+value);
		}
		
		
		Collections.sort(this.tableColumn.getAllHeaderCellList(), new Comparator() {

			  public int compare(Object o1, Object o2) {

				  TableColumnCell obj1 = (TableColumnCell) o1;

				  TableColumnCell obj2 = (TableColumnCell) o2;
				  System.out.println("obj1.getHeaderOrder():"+obj1.getHeaderOrder()+"====obj2.getHeaderOrder():"+obj2.getHeaderOrder());
				  double query1 = 0;
				  if(obj1.getHeaderOrder()!=null && !"".equals(obj1.getHeaderOrder())){
					  query1 = Double.parseDouble(obj1.getHeaderOrder());
				  }
				  double query2 = 0;
				  if(obj2.getHeaderOrder()!=null && !"".equals(obj2.getHeaderOrder())){
					  query2 = Double.parseDouble(obj2.getHeaderOrder());
				  }
				  
				  return (int)(query1-query2);

			  }

			 });
	}
	
	
	//查询数据
	public void queryDataByHCondition(){
		QueryReportDataTable queryTable = (QueryReportDataTable)this.table;
		QueryReportConditionModel queryModel = queryTable.queryModel;
		if(queryModel.getHCondition()!=null){
			filterDataWithHCondition();
		}
	}
	
	/**
	 * 
	* filterDataWithHCondition(根据设置的列表头查询条件，删除不符合条件的行) <br> 
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
	public void filterDataWithHCondition(){
		QueryReportDataTable queryTable = (QueryReportDataTable)this.table;
		QueryReportConditionModel queryModel = queryTable.queryModel;
		
		Iterator<TableHeaderCell> headerIter = tableHeader.allHeaderCellList.iterator();
		while(headerIter.hasNext()){
			TableHeaderCell header = headerIter.next();
			if(!header.isLeaf()){//跳过非叶子节点
				continue;
			}
			
			HeaderRowConfigPO headerPo = header.getHeaderRowConfigPO();
			String headerBindid = headerPo.getBindId()+"";
			Map<String,String> querCondtions = queryModel.gethConditions(headerBindid);
			if(querCondtions.size()==0){//跳过未设置查询条件的列头
				continue;
			}
			
			List<TableColumnCell> copyColumnList = new ArrayList<TableColumnCell>(this.tableColumn.getAllHeaderCellList());
			
			//针对此列，从列中查找不符合条件的行，并删除此行
			Iterator<TableColumnCell> columnIter = copyColumnList.iterator();
			while(columnIter.hasNext()){
				VerticalColumnConfigPO conlumPo = columnIter.next().getVerticalColumnConfigPO();
				String columnBindid = conlumPo.getBindId()+"";
				String dataKey = headerBindid+"#"+columnBindid;
				String dataValue = this.dataMap.get(dataKey);
				if(QueryReportUtil.correspondQueryCondition(querCondtions, dataValue)){
					continue;
				}else{
					this.tableColumn.removeFromAllHeaderCellList(columnBindid);
				}
			}
		}
	}
	
	/**
	 * 生成一行数据
	 * 
	 * @param colName
	 * @param column
	 * @return
	 */
	String getOneInputRow(String colName, TableColumnCell column,DepartmentToReport dept) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>");
		sb.append("<td class='seeReportTableTd'>" + colName + "</td>");
		List<TableHeaderCell> headerCellList = tableHeader
				.getInputHeaderCellList();// 表头
		for (int i = 1, headerlength = headerCellList.size(); i < headerlength; i++) {// 除第一列外的其他单元格,第一列为纵向配置数据对应列
			TableHeaderCell headerCell = headerCellList.get(i);
			if (headerCell.isLeaf()) {
				String cellDataValue = "";
				
				//HVConfigDataFromPO dataFromPo = getCellDataFromWithHV(headerCell.getHeaderRowConfigPO(), column.getVerticalColumnConfigPO());
				//String dataFromType = dataFromPo.getDataFromType();
				
				if (column.getVerticalColumnConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_Org || 
						column.getVerticalColumnConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_AreaList ) {
					
					cellDataValue = getCellData(headerCell.getHeaderRowConfigPO().getBindId()+"",dept.getBindid());
				}else{
					cellDataValue = getCellData(headerCell,column,null);
				}
				
				
				sb.append("<td class='seeReportTableTd'>");
				sb.append(cellDataValue==null?"":cellDataValue);//输出每个单元格数据
				sb.append("</td>");
			}
		}
		return sb.toString();
	}

	@Override
	String getRowJSON(String colName, TableColumnCell column,
			DepartmentToReport dept) {
		// TODO Auto-generated method stub
		return null;
	}

}
