package com.kszit.CDReport.cor.model.graph;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;

public class FunctionCharts {

	public FunctionChartsPic functionChartsPic = new FunctionChartsPic();
	
	String title;
	
	String xName;
	
	String yName;
	
	GraphData graphData = null;
	
	public String getChartsXMLData(){
		String returnStr = "";
		int type = graphData.graphType();
		switch(type){
		case 1:
			returnStr = multiDept();
			break;
		case 2:
			returnStr = multiPeriod();	
			break;
		case 3:
			returnStr = singleDetpPeroReport();
			break;
		case 4:
			returnStr = singleDetpPeroReport();
			break;
		}
		return returnStr;
	}
	
	private String multiDept(){
		Map<String,List<GraphDataTableCell>> deptGroupMap = graphData.getGroupOfDept();
		
		Map<String,StringBuffer> xmlDatasetStrMap = new HashMap<String,StringBuffer>();
		
		Map<String,List<GraphDataTableCell>> sameKindCellGroup = graphData.getSameKindCell();
		Iterator<String> sameKindGroupKey = sameKindCellGroup.keySet().iterator();
		while(sameKindGroupKey.hasNext()){
			String sameKind = sameKindGroupKey.next();
			List<GraphDataTableCell> sameKindCell = sameKindCellGroup.get(sameKind);
			
			String cellName = sameKindCell.get(0).gethName()+"("+sameKindCell.get(0).getvName()+")";
			
			xmlDatasetStrMap.put(sameKind, new StringBuffer("<dataset seriesName='"+cellName+"' showValues='0'>"));
			
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(getBomStr()+"<?xml version='1.0'?>");
		sb.append("<chart caption='Company Revenue' palette='1' showValues='0' yAxisValuesPadding='10'>");
		
		StringBuffer categoriesXML = new StringBuffer();
		categoriesXML.append("<categories>");
		
		Iterator<String> deptkeyIter = deptGroupMap.keySet().iterator();
		while(deptkeyIter.hasNext()){
			String periodkey = deptkeyIter.next();
			List<GraphDataTableCell> value = deptGroupMap.get(periodkey);
			
			categoriesXML.append("<category label='"+periodkey+"'/>");
			
			for(GraphDataTableCell dataCell:value){
				xmlDatasetStrMap.get(dataCell.getHbindid()+"#"+dataCell.getVbindid()).append("<set value='"+dataCell.getValue()+"'/>"); 
			}
				
			
			
		}
		categoriesXML.append("</categories>");
		
		sb.append(categoriesXML.toString());
		
		Iterator<String> hKeyIter = xmlDatasetStrMap.keySet().iterator();
		while(hKeyIter.hasNext()){
			String hkey = hKeyIter.next();
			StringBuffer  temp = xmlDatasetStrMap.get(hkey).append("</dataset>");
			sb.append(temp.toString());
		}
		
		sb.append("</chart>");
		return sb.toString();
		
		
	
		
		
	}
	
	private String multiPeriod(){
		Map<String,List<GraphDataTableCell>> periodGroupMap = graphData.getGroupOfDataTime();
		
		Map<String,StringBuffer> xmlDatasetStrMap = new HashMap<String,StringBuffer>();
		
		Map<String,List<GraphDataTableCell>> sameKindCellGroup = graphData.getSameKindCell();
		Iterator<String> sameKindGroupKey = sameKindCellGroup.keySet().iterator();
		while(sameKindGroupKey.hasNext()){
			String sameKind = sameKindGroupKey.next();
			List<GraphDataTableCell> sameKindCell = sameKindCellGroup.get(sameKind);
			
			String cellName = sameKindCell.get(0).gethName()+"("+sameKindCell.get(0).getvName()+")";
			
			xmlDatasetStrMap.put(sameKind, new StringBuffer("<dataset seriesName='"+cellName+"' showValues='0'>"));
			
		}
		
		
		
		StringBuffer sb = new StringBuffer();
		sb.append(getBomStr()+"<?xml version='1.0'?>");
		sb.append("<chart caption='"+this.title+"' numdivlines='9' lineThickness='2' showValues='0' anchorRadius='3' anchorBgAlpha='50' showAlternateVGridColor='1' numVisiblePlot='12' animation='0'>");
		
		StringBuffer categoriesXML = new StringBuffer();
		categoriesXML.append("<categories>");
		
		Iterator<String> periodkeyIter = periodGroupMap.keySet().iterator();
		while(periodkeyIter.hasNext()){
			String periodkey = periodkeyIter.next();
			List<GraphDataTableCell> value = periodGroupMap.get(periodkey);
			
			categoriesXML.append("<category label='"+periodkey+"'/>");
			
			for(GraphDataTableCell dataCell:value){
				xmlDatasetStrMap.get(dataCell.getHbindid()+"#"+dataCell.getVbindid()).append("<set value='"+dataCell.getValue()+"'/>"); 
			}
				
			
			
		}
		categoriesXML.append("</categories>");
		
		sb.append(categoriesXML.toString());
		
		Iterator<String> hKeyIter = xmlDatasetStrMap.keySet().iterator();
		while(hKeyIter.hasNext()){
			String hkey = hKeyIter.next();
			StringBuffer  temp = xmlDatasetStrMap.get(hkey).append("</dataset>");
			sb.append(temp.toString());
		}
		
		sb.append("</chart>");
		return sb.toString();
		
		
	
		
		
	}
	
	
	
	private String singleDetpPeroReport(){
		Map<String,Object> vBindidGroupMap = graphData.getGroupOfVbindid(false);
		
		StringBuffer sb = new StringBuffer();
		sb.append(getBomStr()+"<?xml version='1.0'?>");
		if(graphData.hbindidCount==1){
			sb.append("<chart useRoundEdges='1' formatNumberScale='0' decimals='0' showValues='0' yAxisName='"+yName+"' xAxisName='"+xName+"' caption='"+this.title+"' palette='2'> ");
			Iterator<String> keyIter = vBindidGroupMap.keySet().iterator();
			while(keyIter.hasNext()){
				String key = keyIter.next();
				String value = (String)vBindidGroupMap.get(key);
				sb.append("<set value='"+value+"' label='"+key+"'/> ");
			}
		}else{
			Map<TableHeaderCell,StringBuffer> xmlDatasetStrMap = new HashMap<TableHeaderCell,StringBuffer>();
			
			//所有横行类别，及对应的xml字符串放入map中
			FunctionChartsDataUtil.addDatasetNodeBegin(xmlDatasetStrMap,graphData.getGroupOfHbindid());
			
			sb.append("<chart palette='1' caption='"+this.title+"' shownames='1' showvalues='0' sYAxisValuesDecimals='2' connectNullData='0' PYAxisName='Revenue' SYAxisName='Quantity' numDivLines='4' formatNumberScale='0'>");
			StringBuffer categoriesXML = new StringBuffer();
			categoriesXML.append("<categories>");
			Iterator<String> vBindidGroupKey = vBindidGroupMap.keySet().iterator();
			while(vBindidGroupKey.hasNext()){//纵向
				String vName = vBindidGroupKey.next();
				categoriesXML.append("<category label='"+vName+"'/>");
				
				Map<TableHeaderCell,Object> vTohCellMap = (Map<TableHeaderCell,Object>)vBindidGroupMap.get(vName);//纵向对应的横向
				
				Iterator<TableHeaderCell> hCategoryIter = xmlDatasetStrMap.keySet().iterator();
				while(hCategoryIter.hasNext()){
					TableHeaderCell hCategory = hCategoryIter.next();
					StringBuffer hCategoryXml = xmlDatasetStrMap.get(hCategory);
					if(vTohCellMap.containsKey(hCategory)){
						Iterator<GraphDataTableCell> vKeyValueIter = ((List<GraphDataTableCell>)vTohCellMap.get(hCategory)).iterator();
						while(vKeyValueIter.hasNext()){
							GraphDataTableCell vCell = vKeyValueIter.next();
							hCategoryXml.append("<set value='"+vCell.getValue()+"'/>");
						}
					}else{
						hCategoryXml.append("<set value=''/>");
					}
				}
				
			}
			categoriesXML.append("</categories>");
			
			
			sb.append(categoriesXML.toString());
			
			FunctionChartsDataUtil.addDatasetNodeEnd(xmlDatasetStrMap,sb);
			
		}
		
	
		
		sb.append("</chart>");
		return sb.toString();
	}
	
	private String getBomStr(){
		byte[] utf8Bom = new byte[]{(byte) 0xef, (byte) 0xbb, (byte) 0xbf};  
		String utf8BomStr="";  
		try {  
		utf8BomStr = new String(utf8Bom,"UTF-8");//定义BOM标记  
		} catch (UnsupportedEncodingException e) {  
		   e.printStackTrace();  
		} 
		return utf8BomStr;
	}
	public FunctionCharts(long graphConfigBindid){
		this.graphData = new GraphData(graphConfigBindid,this);
		initFunctionChartsPic();
	}
	
	private void initFunctionChartsPic(){
		int type = graphData.graphType();
		switch(type){
		case 1:
			
			functionChartsPic.setChartName("MSColumnLine3D.swf");//多部门
			break;
		case 2:
			functionChartsPic.setChartName("ScrollLine2D.swf");//多期
			break;
		case 3:
			functionChartsPic.setChartName("Column3D.swf");
			break;
		case 4:
			functionChartsPic.setChartName("MSColumnLine3D.swf");
			break;
		}
		functionChartsPic.setChatId("chart1Id");
		functionChartsPic.setWidth(400);
		functionChartsPic.setHeight(300);
	}
	
}
