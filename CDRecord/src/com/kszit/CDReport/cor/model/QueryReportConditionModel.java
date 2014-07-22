package com.kszit.CDReport.cor.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 报表查询条件
 * @author Administrator
 *
 */
public class QueryReportConditionModel extends ParentModel{
	
	private String reportBindid;
	
	private String dept;
	
	private String periodWithYear;

	private String year;
	
	private String period;
	
	private String hCondition;
	
	private Map<String,String> hConditions;
	
	private String hSort;
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 
	* gethConditions( 查找是否设置了查询条件，没有设置返回空的map，) <br> 
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
	public Map<String,String> gethConditions(String bindid) {
		Map<String,String> reMap = new HashMap<String,String>();
		
		Map<String,String> quCondition = getHConditions();

		Iterator<String> keyIter = quCondition.keySet().iterator();
		while(keyIter.hasNext()){
			String key = keyIter.next();
			if(key.contains(bindid)){
				String value = quCondition.get(key);
				reMap.put(key, value);
			}
		}
				
		return reMap;
	}
	
	
	public String getReportBindid() {
		return reportBindid;
	}

	public void setReportBindid(String reportBindid) {
		this.reportBindid = reportBindid;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPeriod() {
		if(period==null && periodWithYear!=null){
			period = periodWithYear.substring(4,periodWithYear.length());
		}
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPeriodWithYear() {
		return periodWithYear;
	}

	public void setPeriodWithYear(String periodWithYear) {
		this.periodWithYear = periodWithYear;
	}

	public String getYear() {
		if(year==null && periodWithYear!=null){
			year = periodWithYear.substring(0,4);
		}
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getHCondition() {
		return hCondition;
	}

	public void setHCondition(String hCondition) {
		this.hCondition = hCondition;
	}

	public Map<String,String> getHConditions() {
		if(hCondition!=null && hConditions==null){
			hConditions = new HashMap<String,String>();
			String[] hConditionsTemp = hCondition.split("\\|");
			for(String s:hConditionsTemp){
				if(s!=null && !"".equals(s) && s.contains("=")){
					String[] keyAndValue = s.split("=");
					hConditions.put(keyAndValue[0], keyAndValue[1]);
				}
			}
		}
		return hConditions;
	}

	
	public void setHConditions(Map<String,String> hConditions) {
		this.hConditions = hConditions;
	}


	public String getHSort() {
		return hSort;
	}


	public void setHSort(String hSort) {
		this.hSort = hSort;
	}

	

	
	
	

}
