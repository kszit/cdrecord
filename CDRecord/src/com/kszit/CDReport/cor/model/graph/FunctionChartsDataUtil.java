package com.kszit.CDReport.cor.model.graph;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;

public class FunctionChartsDataUtil {

	public static void addDatasetNodeBegin(Map<TableHeaderCell,StringBuffer> xmlDatasetStrMap,Map<TableHeaderCell,List<GraphDataTableCell>> hBindidGroupMap){
		Iterator<TableHeaderCell> hBindidGroupKey = hBindidGroupMap.keySet().iterator();
		boolean isS = false;
		while(hBindidGroupKey.hasNext()){
			TableHeaderCell key = hBindidGroupKey.next();
			String vName = key.getName();
			if(!isS){
				xmlDatasetStrMap.put(key, new StringBuffer("<dataset seriesName='"+vName+"' showValues='0' parentYAxis='P' renderAs='COLUMN'>"));
				isS = true;
			}else{
				xmlDatasetStrMap.put(key, new StringBuffer("<dataset seriesName='"+vName+"' showValues='0' parentYAxis='S' renderAs='COLUMN'>"));
			}
			
		}
	}
	
	public static void addDatasetNodeEnd(Map<TableHeaderCell,StringBuffer> xmlDatasetStrMap,StringBuffer sb){
		Iterator<TableHeaderCell> hKeyIter = xmlDatasetStrMap.keySet().iterator();
		while(hKeyIter.hasNext()){
			TableHeaderCell hkey = hKeyIter.next();
			StringBuffer  temp = xmlDatasetStrMap.get(hkey).append("</dataset>");
			sb.append(temp.toString());
		}
	}
}
