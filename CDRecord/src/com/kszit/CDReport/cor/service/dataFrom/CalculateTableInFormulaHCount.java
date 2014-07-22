package com.kszit.CDReport.cor.service.dataFrom;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromDataItemPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.formula.Formula;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.lowagie.text.Cell;

/**
 * 横向计算公式
 * @author Administrator
 *
 */
public class CalculateTableInFormulaHCount extends CalculateAbatract{
	public CalculateTableInFormulaHCount(){
		super();
	}
	public CalculateTableInFormulaHCount(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 功能：计算表内的横向数据项的计算公式
	 * 实现：数据来源中的数据项的配置，在数据库中的存储时，多个数据项使用|分割，各数据项的格式为：reportLinkbindid#hbindid#vbindid#时期,但在此方法中只使用了hbindid。
	* (non-Javadoc)  
	* @see com.kszit.CDReport.cor.service.dataFrom.CalculateAbatract#calculate()
	 */
	@Override
	public String calculate() {
		//计算公式
		String formula = datafromPo.getFormula();
		long vbindid = datafromPo.getVbindid();
		int i=0;
		List<HVConfigDataFromDataItemPO> dataItemList = HVConfigDataFromDataItemPO.getPoWithConfigOf(datafromPo.getDataTerm());
		String[] dataStrs = new String[dataItemList.size()];
		for(HVConfigDataFromDataItemPO dataItem:dataItemList){
			String key = dataItem.gethBindid()+"#"+vbindid;
			if(!dataMap.containsKey(key)){
				return LAST_CALCULATE;
			}else{
				dataStrs[i] = dataMap.get(key)+"";
				i++;
			}
		}
		
		return Formula.compute(formula,dataStrs)+"";
	}

	@Override
	public String getDataFromHtml(String reportLinkid) {
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("CalculateTableInFormulaHCount.js****");
		
		
		sb.append("<table width=100%>			" +
				"<tr>				<td>计算公式：</td>			</tr>			<tr>				<td><input id='headerRowModel_dataRule_temp' type='text' value='' name='headerRowModel.dataRule.temp'/></td>			</tr>			" +
				"<tr>				<td>数据项：</td>			</tr>			" +
				"<tr>				<td>" +
				"<select name='allcells' id='allcells'	size=20 style='width: 200px'>");
					TableHeader header = TableHeader.getTableHeader(reportLinkid);
					Iterator<TableHeaderCell> iter = header.allHeaderCellList.iterator();
					while(iter.hasNext()){
						TableHeaderCell cell = iter.next();
						if(cell.isLeaf()){
							String value = header.getNamPathWithParent(cell);
							String hvBindid = reportLinkid+"$"+cell.getHeaderRowConfigPO().getBindId()+"#0";
							sb.append("<option value='"+hvBindid+"'>"+value+"</option>");
						}
					}
					sb.append("" +
				"</select>" +
				
				"</td>	<td><button onclick='addOneCell();return false;'>选择</button><br>" +
				"<button onclick='removeOneCell();return false;'>删除</button><br>" +
				"<button onclick='removeAll();return false;'>全删除</button></td>	<td>" +
				"<select name='selectedcells' id='selectedcells'	size=20 style='width: 200px'><select>" +
				"</td>	</tr></table>");
		
		return sb.toString();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
StringBuffer sb = new StringBuffer();
		
		
		sb.append("CalculateTableInFormulaHCountOfCell.js****");
		
		
		sb.append("<table width=100%>			" +
				"<tr>				<td>计算公式：</td>			</tr>			<tr>				<td><input id='headerRowModel_dataRule_temp' type='text' value='' name='headerRowModel.dataRule.temp'/></td>			</tr>			" +
				"<tr>				<td>数据项：</td>			</tr>			" +
				"<tr>				<td>" +
				"<select name='allcells' id='allcells'	size=20 style='width: 200px'>");
					TableHeader header = TableHeader.getTableHeader(reportLinkid);
					Iterator<TableHeaderCell> iter = header.allHeaderCellList.iterator();
					while(iter.hasNext()){
						TableHeaderCell cell = iter.next();
						if(cell.isLeaf()){
							String value = header.getNamPathWithParent(cell);
							String hvBindid = reportLinkid+"$"+cell.getHeaderRowConfigPO().getBindId()+"#0";
							sb.append("<option value='"+hvBindid+"'>"+value+"</option>");
						}
					}
					sb.append("" +
				"</select>" +
				
				"</td>	<td><button onclick='addOneCell();return false;'>选择</button><br>" +
				"<button onclick='removeOneCell();return false;'>删除</button><br>" +
				"<button onclick='removeAll();return false;'>全删除</button></td>	<td>" +
				"<select name='selectedcells' id='selectedcells'	size=20 style='width: 200px'><select>" +
				"</td>	</tr></table>");
		
		return sb.toString();
	}

	
}
