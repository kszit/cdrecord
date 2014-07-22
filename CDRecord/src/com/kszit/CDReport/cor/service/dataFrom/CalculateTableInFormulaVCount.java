package com.kszit.CDReport.cor.service.dataFrom;

import java.util.Iterator;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
/**
 * 纵向计算公式
 * @author Administrator
 *
 */
public class CalculateTableInFormulaVCount extends CalculateAbatract{
	public CalculateTableInFormulaVCount(){
		super();
	}
	public CalculateTableInFormulaVCount(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	@Override
	public String calculate() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getDataFromHtml(String reportLinkBindid) {
StringBuffer sb = new StringBuffer();
		
		
		sb.append("CalculateTableInFormulaVCount.js****");
		
		
		sb.append("<table width=100%>			" +
				"<tr>				<td>计算公式：</td>			</tr>			<tr>				<td><input id='headerRowModel_dataRule_temp' type='text' value='' name='headerRowModel.dataRule.temp'/></td>			</tr>			" +
				"<tr>				<td>数据项：</td>			</tr>			" +
				"<tr>				<td>" +
				"<select name='allcells' id='allcells'	size=20 style='width: 200px'>");
			TableColumn colum = TableColumn.getTableColumn(reportLinkBindid);
					Iterator<TableColumnCell> iter = colum.getAllHeaderCellList().iterator();
					while(iter.hasNext()){
						TableColumnCell cell = iter.next();
						if(cell.isLeaf()){
							String value = colum.getNamPathWithParent(cell);
							String hvBindid = reportLinkBindid+"$"+"0#"+cell.getVerticalColumnConfigPO().getBindId();
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
	public String getCellDataFromHtml(String reportLinkBindid) {
StringBuffer sb = new StringBuffer();
		
		
		sb.append("CalculateTableInFormulaVCountOfCell.js****");
		
		
		sb.append("<table width=100%>			" +
				"<tr>				<td>计算公式：</td>			</tr>			<tr>				<td><input id='headerRowModel_dataRule_temp' type='text' value='' name='headerRowModel.dataRule.temp'/></td>			</tr>			" +
				"<tr>				<td>数据项：</td>			</tr>			" +
				"<tr>				<td>" +
				"<select name='allcells' id='allcells'	size=20 style='width: 200px'>");
			TableColumn colum = TableColumn.getTableColumn(reportLinkBindid);
					Iterator<TableColumnCell> iter = colum.getAllHeaderCellList().iterator();
					while(iter.hasNext()){
						TableColumnCell cell = iter.next();
						if(cell.isLeaf()){
							String value = colum.getNamPathWithParent(cell);
							String hvBindid = reportLinkBindid+"$"+"0#"+cell.getVerticalColumnConfigPO().getBindId();
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
