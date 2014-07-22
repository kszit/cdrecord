package com.kszit.CDReport.cor.service;

import com.kszit.CDReport.cor.service.reportData.SelectCellTable;
import com.kszit.CDReport.util.StaticVaribles;

public class SelectCellTableService {

	
	public String getTable(String reportBindid,long bindid,int dataFromType,String selectedCell){
		
		int outHtmlType = getOutHtmlType(dataFromType);
		
		SelectCellTable selectCellTalble = new SelectCellTable(reportBindid,bindid,outHtmlType,selectedCell);
		return selectCellTalble.getTable();
	}
	
	public String getTable(String reportBindid,String selectedCell){
		
		
		SelectCellTable selectCellTalble = new SelectCellTable(reportBindid,selectedCell);
		return selectCellTalble.outAllTable();
	}
	
	/**
	 * 
	 * @param dataFromType
	 * @return
	 */
	private int getOutHtmlType(int dataFromType){
		int outHtmlType = 0;
		switch(dataFromType){
		case StaticVaribles.DataFrom_TableInFormulaHCountBindId://横向计算公式
			outHtmlType = SelectCellTable.HEADER_ONLY;
			break;
		case StaticVaribles.DataFrom_TableInFormulaVCountBindId://纵向计算公式
			outHtmlType = SelectCellTable.COLUMN_ONLY;
			break;
		case StaticVaribles.DataFrom_TableInFormulaHVCountBindId://横纵向计算公式
			outHtmlType = SelectCellTable.TABLE_ALL;
			break;
		}
		return outHtmlType;
		
	}
	
}
