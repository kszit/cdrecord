package com.kszit.CDReport.cor.service.reportData;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.VerticalColumnService;
import com.kszit.CDReport.cor.ui.IUIModel;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.ClassInstallUtil;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 
 * 从已有报表中选择部分横向配置
 * @author Administrator
 *
 */
public class SelectVPartTable extends Table {
	//1:checkbox,2:radio
	int type;
	public SelectVPartTable(String repartLinkId,int type){
		super(repartLinkId,true);
		this.type = type;
	}

	/**
	 * 递归调用此方法，生成树形的行数据
	 * 
	 * @param columnCellList
	 * @param leve
	 * @return
	 */
	public String getHtmlCodeByColumnList(List<TableColumnCell> columnCellList,
			int leve) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tr class='content_header_tr'>");
		
		String leveStr = "";
		for (int i = 0; i < leve; i++) {
			leveStr += "---";
		}
		for (TableColumnCell column : columnCellList) {
			String colName = leveStr+column.getVerticalColumnConfigPO().getContent();
			String vBindid = "0#"+column.getVerticalColumnConfigPO().getBindId();
			/*
			// 单位列表
			if (column.getVerticalColumnConfigPO().getUIModule() == StaticVaribles.HTMLUIModule_Org) {
				List<DepartmentToReport> deptList = new DepartmentUtil()
						.getDepartmentService().getDepartmentList("0");
				for (DepartmentToReport dept : deptList) {
					colName = leveStr + dept.getDepartmentName();
					vBindid = 
					sb.append(getOneInputRow(colName, column, dept));
				}
			} else if (column.getVerticalColumnConfigPO().getUIModule() == StaticVaribles.HTMLUIModule_AreaList) {
				DictionaryService dicService = new DictionaryService();
				DictionaryPO dicPo = dicService.getOneByBindid(column.getVerticalColumnConfigPO().getUIModule()+"");
				IUIModel uiModel = (IUIModel)ClassInstallUtil.installClass(dicPo.getDescribe2());
				Map<String, String> areMap = uiModel.tableLeftOfDynamicsCell();	
				Iterator<String> iter = areMap.keySet().iterator();
				while(iter.hasNext()){
					String bindid = (String)iter.next();
					colName = areMap.get(bindid);
					colName = leveStr + colName;
					DepartmentToReport dept = new DepartmentToReport();
					dept.setBindid(bindid);
					sb.append(getOneInputRow(colName, column, dept));
					
				}
				
				
			} else {
				colName = leveStr + column.getName();
				sb.append(getOneInputRow(colName, column, null));
			}
			*/
			sb.append("<td class='seeReportTableTd'>");
			sb.append(colName);
			sb.append("</td>");
			sb.append("<td class='seeReportTableTd'>");
			
			String htmltype = "checkbox";
			if(type==1){
				htmltype = "checkbox";
			}else if(type==2){
				htmltype = "radio";
			}
			
			
			sb.append("<input name='tableHVCell' onclick type='"+htmltype+"' value='"+super.reportBindid+"$"+vBindid+"'></input>");
			sb.append("</td>");
			// 循环孩子节点
			List<TableColumnCell> childs = column.getChilds();
			if (childs != null) {
				sb.append(getHtmlCodeByColumnList(childs,
						column.getCurrentLayer()));
			}
			sb.append("</tr>");
		}

		return sb.toString();
	}
	
	/**
	 * 
	 */
	public String getTable(){
		
		return getTableContent();
	}
	
	/**
	 * 设置选中的单元格的父节点也被选中，
	 * 
	 * @param selectedCell
	 */
	public void setCellParentSelected(TableHeaderCell selectedCell){
		TableHeaderCell selectedCellParent = selectedCell.getParent();
		while(selectedCellParent!=null){
			selectedCellParent.setSelected(true);
			selectedCellParent = selectedCellParent.getParent();
		}
	}

	@Override
	String getTableContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("<table align='center' class='seeReportTable'>");
		//sb.append(getTableHeader());
		List<TableColumnCell> columnCellList = tableColumn
				.getAllHeaderCellTree();
		sb.append(getHtmlCodeByColumnList(columnCellList, 0));
		sb.append("</table>");
		return sb.toString();
	}
}

