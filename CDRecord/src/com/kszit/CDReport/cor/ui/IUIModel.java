package com.kszit.CDReport.cor.ui;

import java.util.Map;

public interface IUIModel {

	/**
	 * 动态生成表格左侧的单元格，像地区列表、合同列表等
	 * 返回值为Map，key: bindid,例如 合同编号，地区编号,value：实际值，例如：合同名称，地区名称等
	 * 
	 * 
	 * @return
	 */
	public Map<String,String> tableLeftOfDynamicsCell();
	
	
}
