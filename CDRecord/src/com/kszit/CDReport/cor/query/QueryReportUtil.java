package com.kszit.CDReport.cor.query;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.util.DateUtil;
import com.kszit.CDReport.util.StaticVaribles;

public class QueryReportUtil {

	public static List<QueryCondition> getQeriodsByTimeQuantum(Date start,
			Date end, int qeriodsType) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();

		switch (qeriodsType) {
		case StaticVaribles.ReportFreq_YearBindId:
			queryConditions = getQeriodsByTimeQuantumOfYear(start,end);
			break;
		case StaticVaribles.ReportFreq_PeriodBindId:
			queryConditions = getQeriodsByTimeQuantumOfPeriod(start,end);
			break;
		case StaticVaribles.ReportFreq_HalfYearBindId:
			queryConditions = getQeriodsByTimeQuantumOfHalfYear(start,end);
			break;
		case StaticVaribles.ReportFreq_MonthBindId:
			queryConditions = getQeriodsByTimeQuantumOfMonth(start,end);
			break;
		case StaticVaribles.ReportFreq_HalfMonthBindId:
			
			break;
		case StaticVaribles.ReportFreq_WeekBindId:
			
			break;
		case StaticVaribles.ReportFreq_DayBindId:
			
			break;

		}

		return queryConditions;
	}
	
	private static List<QueryCondition> getQeriodsByTimeQuantumOfMonth(
			Date start, Date end) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		
		int startYear = DateUtil.getYear(start);
		int endYear = DateUtil.getYear(end);
		
		int startYearOfMonth = DateUtil.getMonth(start);
		int endYearOfMonth = DateUtil.getMonth(end);
		
		int count = endYear - startYear;
		int monthTypeBegin = 301;
		for (int i=0; i <= count; i++) {
			if(i==0 && count==0){//非跨年
				int monthCount = endYearOfMonth-startYearOfMonth+1;
				int beginMonth = monthTypeBegin+startYearOfMonth-1;
				getQeriodsByTimeQuantumUtil(queryConditions,startYear,beginMonth,monthCount);
			}else if(i==0 && count!=0){//跨年，第一年
				int monthCount = 12-startYearOfMonth;
				int beginMonth = monthTypeBegin+startYearOfMonth-1;
				getQeriodsByTimeQuantumUtil(queryConditions,startYear,beginMonth,monthCount);
			}else if(i!=0 && i==count){//跨年，最后一年
				int monthCount = endYearOfMonth;
				int beginMonth = monthTypeBegin;
				getQeriodsByTimeQuantumUtil(queryConditions,startYear,beginMonth,monthCount);
			}else{//中间年份
				int monthCount = 12;
				int beginMonth = monthTypeBegin;
				getQeriodsByTimeQuantumUtil(queryConditions,startYear,beginMonth,monthCount);
			}
		}
		return queryConditions;
	}
	
	
	private static void getQeriodsByTimeQuantumUtil(List<QueryCondition> queryConditions,int year,int start,int count){
		QueryCondition queryCondition = null;
		for(int i=0;i<count;i++){
			queryCondition = new QueryCondition();
			queryCondition.setYear(year);
			queryCondition.setPeriods(start+i);
			queryConditions.add(queryCondition);
		}
	}
	/**
	 * 
	* getQeriodsByTimeQuantumOfHalfYear(时间段内半年报)  
	* TODO(这里描述这个方法适用条件 – 可选)  
	* TODO(这里描述这个方法的执行流程 – 可选)  
	* TODO(这里描述这个方法的使用方法 – 可选)  
	* TODO(这里描述这个方法的注意事项 – 可选)  
	* @param name  
	* @param @return 设定文件  
	* @return String DOM对象  
	* @Exception 异常对象  
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	private static List<QueryCondition> getQeriodsByTimeQuantumOfHalfYear(
			Date start, Date end) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		
		int startYear = DateUtil.getYear(start);
		int endYear = DateUtil.getYear(end);
		
		int startYearOfMonth = DateUtil.getMonth(start);
		int endYearOfMonth = DateUtil.getMonth(end);
		
		int count = endYear - startYear;
		
		for (int i=0; i <= count; i++) {
			if(i==0 && count==0){//非跨年
				if(startYearOfMonth<=6){
					qeriodsByTimeQuantumOfHalfYearUtil(queryConditions,startYear,1);
				}
				if(endYearOfMonth>6){
					qeriodsByTimeQuantumOfHalfYearUtil(queryConditions,startYear,2);
				}
			}else if(i==0 && count!=0){//跨年，第一年
				if(startYearOfMonth<=6){
					qeriodsByTimeQuantumOfHalfYearUtil(queryConditions,startYear,3);
				}else if(startYearOfMonth>6){
					qeriodsByTimeQuantumOfHalfYearUtil(queryConditions,startYear,2);
				}
			}else if(i!=0 && i==count){//跨年，最后一年
				if(endYearOfMonth<=6){
					qeriodsByTimeQuantumOfHalfYearUtil(queryConditions,startYear,1);
				}else if(endYearOfMonth>6){
					qeriodsByTimeQuantumOfHalfYearUtil(queryConditions,startYear,2);
				}
			}else{//中间年份
				qeriodsByTimeQuantumOfHalfYearUtil(queryConditions,startYear,3);
			}
		}
		return queryConditions;
	}
	
	private static void qeriodsByTimeQuantumOfHalfYearUtil(List<QueryCondition> queryConditions,int year,int type){
		QueryCondition queryCondition = null;
		switch (type) {
		case 1://上半年
			queryCondition = new QueryCondition();
			queryCondition.setYear(year);
			queryCondition.setPeriods(251);
			queryConditions.add(queryCondition);
			break;
		case 2://下半年
			queryCondition = new QueryCondition();
			queryCondition.setYear(year);
			queryCondition.setPeriods(252);
			queryConditions.add(queryCondition);
			break;
		case 3://全年
			queryCondition = new QueryCondition();
			queryCondition.setYear(year);
			queryCondition.setPeriods(251);
			queryConditions.add(queryCondition);
			
			queryCondition = new QueryCondition();
			queryCondition.setYear(year);
			queryCondition.setPeriods(252);
			queryConditions.add(queryCondition);
			break;
		}
	}

	/**
	 * 
	* getQeriodsByTimeQuantumOfPeriod(时间段内季报)  
	* TODO(这里描述这个方法适用条件 – 可选)  
	* TODO(这里描述这个方法的执行流程 – 可选)  
	* TODO(这里描述这个方法的使用方法 – 可选)  
	* TODO(这里描述这个方法的注意事项 – 可选)  
	* @param name  
	* @param @return 设定文件  
	* @return String DOM对象  
	* @Exception 异常对象  
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	private static List<QueryCondition> getQeriodsByTimeQuantumOfPeriod(
			Date start, Date end) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();
		
		int startYear = DateUtil.getYear(start);
		int endYear = DateUtil.getYear(end);
		
		int startYearOfMonth = DateUtil.getMonth(start);
		int endYearOfMonth = DateUtil.getMonth(end);
		
		int count = endYear - startYear;
		
		for (int i=0; i <= count; i++) {
			if(i==0 && count==0){//非跨年
				QueryCondition queryCondition = null;
				for(;startYearOfMonth<endYearOfMonth;startYearOfMonth+=3){
					int quantum = startYearOfMonth/3;
					queryCondition = new QueryCondition();
					queryCondition.setYear(startYear);
					queryCondition.setPeriods(291+quantum);
					queryConditions.add(queryCondition);
				}
				if(startYearOfMonth<=3){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-4);
				}else if(startYearOfMonth>3 && startYearOfMonth<=6){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-3);
				}else if(startYearOfMonth>6 && startYearOfMonth<9){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-2);
				}else if(startYearOfMonth>9){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-1);
				}
			}else if(i==0 && count!=0){//跨年，第一年
				if(startYearOfMonth<=3){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-4);
				}else if(startYearOfMonth>3 && startYearOfMonth<=6){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-3);
				}else if(startYearOfMonth>6 && startYearOfMonth<9){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-2);
				}else if(startYearOfMonth>9){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,-1);
				}
			}else if(i!=0 && i==count){//跨年，最后一年
				if(endYearOfMonth<=3){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,1);
				}else if(endYearOfMonth>3 && endYearOfMonth<=6){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,2);
				}else if(endYearOfMonth>6 && endYearOfMonth<9){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,3);
				}else if(endYearOfMonth>9){
					qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,4);
				}
			}else{//中间年份
				qeriodsByTimeQuantumOfPeriodUtil(queryConditions,startYearOfMonth,4);
			}
		}
		return queryConditions;
	}
	
	private static void qeriodsByTimeQuantumOfPeriodUtil(List<QueryCondition> queryConditions,int year,int type){
		if(type>0){
			int count = 0;
			switch (type) {
			case 1://1季度
				count = 0;
				break;
			case 2://1-2季度
				count = 1;
				break;
			case 3://1-3季度
				count = 2;
				break;
			case 4://1-4季度
				count = 3;
				break;
			}
			
			QueryCondition queryCondition = null;
			for(int i=0;i<=count;i++){
				queryCondition = new QueryCondition();
				queryCondition.setYear(year);
				queryCondition.setPeriods(291+i);
				queryConditions.add(queryCondition);
			}
		}else if(type<0){
			int count = 0;
			switch (type) {
			case -1://4季度
				count = 0;
				break;
			case -2://4-3季度
				count = -1;
				break;
			case -3://4-2季度
				count = -2;
				break;
			case -4://4-1季度
				count = -3;
				break;
			}
			
			QueryCondition queryCondition = null;
			for(;count<=0;count++){
				queryCondition = new QueryCondition();
				queryCondition.setYear(year);
				queryCondition.setPeriods(294+count);
				queryConditions.add(queryCondition);
			}
		}
		
	}
	
	/**
	 * 
	* getQeriodsByTimeQuantumOfYear(时间段内年报 期数)  
	* TODO(这里描述这个方法适用条件 – 可选)  
	* TODO(这里描述这个方法的执行流程 – 可选)  
	* TODO(这里描述这个方法的使用方法 – 可选)  
	* TODO(这里描述这个方法的注意事项 – 可选)  
	* @param start，end 
	* @param @return 设定文件  
	* @return String DOM对象  
	* @Exception 异常对象  
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	private static List<QueryCondition> getQeriodsByTimeQuantumOfYear(
			Date start, Date end) {
		List<QueryCondition> queryConditions = new ArrayList<QueryCondition>();

		int startYear = DateUtil.getYear(start);
		int endYear = DateUtil.getYear(end);
		int count = endYear - startYear;
		for (int i = 0; i < count; i++) {
			QueryCondition queryCon = new QueryCondition();
			queryCon.setYear((startYear + i));
			queryCon.setPeriods(startYear + i);
			queryConditions.add(queryCon);
		}
		return queryConditions;
	}
}
