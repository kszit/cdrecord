package com.kszit.CDReport.cor.model.graph;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.graph.GraphConfigMainDao;
import com.kszit.CDReport.cor.dao.graph.GraphConfigSubDao;
import com.kszit.CDReport.cor.dao.hibernate.graph.GraphConfigMainDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.graph.GraphConfigSubDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigMainPO;
import com.kszit.CDReport.cor.dao.hibernate.po.graph.GraphConfigSubPO;
import com.kszit.CDReport.cor.query.QueryCondition;
import com.kszit.CDReport.cor.query.QueryReportUtil;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.kszit.CDReport.util.StringUtil;

public class GraphData {

	int deptidCount = 0;
	
	int dataTimeCount = 0;
	
	int hbindidCount = 0;
	
	int vbindidCount = 0;
	
	FunctionCharts functionChars;
	
	public int graphType(){
		if(deptidCount!=1&&dataTimeCount==1){
			return 1;
		}else if(deptidCount==1&&dataTimeCount!=1){
			return 2;
		}else if(deptidCount==1&&dataTimeCount==1){
			if(hbindidCount==1){
				return 3;
			}else if(hbindidCount!=1){
				return 4;
			}
		}
		return 0;
	}
	
	private long graphConfigBindid;
	public GraphData(long graphConfigBindid,FunctionCharts functionChars){
		this.graphConfigBindid = graphConfigBindid;
		this.functionChars = functionChars;
		initData();
		initCount();
	}
	
	void initCount(){
		String deptidTemp = "temp";
		String dataTimeTemp = "temp";
		String hbindidTemp = "temp";
		String vbinidTemp = "temp";
		for(GraphDataTableCell cell:tableCells){
			if(!deptidTemp.contains("#"+cell.getDeptId())){
				deptidCount++;
				deptidTemp += "#"+cell.getDeptId();
			}
			if(!dataTimeTemp.contains("#"+cell.getDataTime())){
				dataTimeCount++;
				dataTimeTemp += "#"+cell.getDataTime();
			}
			if(!hbindidTemp.contains("#"+cell.getHbindid())){
				hbindidCount++;
				hbindidTemp += "#"+cell.getHbindid();
			}
			if(!vbinidTemp.contains("#"+cell.getVbindid())){
				vbindidCount++;
				vbinidTemp += "#"+cell.getVbindid();
			}
		}
	}
	
	void initData(){

		GraphConfigMainDao graphConfigMainDao = new GraphConfigMainDaoHiberImpl();
		GraphConfigSubDao graphConfigSubDao = new GraphConfigSubDaoHiberImpl();
		
		GraphConfigMainPO configMainPo = graphConfigMainDao.getByBindId(graphConfigBindid);
		
		List<GraphConfigSubPO> configSubPos = graphConfigSubDao.getListOfMainBindid(graphConfigBindid);
		
		for(GraphConfigSubPO subPo:configSubPos){
			String subDeptid = subPo.getDeptIds();
			Date subStartDay = subPo.getStartDay();
			Date subEndDay = subPo.getEndDay();
			if(subDeptid==null || "".equals(subDeptid)){
				subPo.setDeptIds(configMainPo.getDeptIds());
			}
			if(subStartDay==null){
				subPo.setStartDay(configMainPo.getStartDay());
			}
			if(subEndDay==null){
				subPo.setEndDay(configMainPo.getEndDay());
			}
		}
		
		//
		List<GraphDataTableCell> tableCells = new ArrayList<GraphDataTableCell>();
		for(GraphConfigSubPO subPo:configSubPos){
			Date subStartDay = subPo.getStartDay();
			Date subEndDay = subPo.getEndDay();
			String reportConfigLinkbindid = subPo.getTableLinkBindid();
			ReportConfigPO reportConfigPo = ReportConfigService.getReportConfigPO(reportConfigLinkbindid);
			int type = Integer.parseInt(reportConfigPo.getRttype());//报表类型：年报、月报、季报等
			
			//期数列表
			List<QueryCondition> queryConditions = QueryReportUtil.getQeriodsByTimeQuantum(subStartDay, subEndDay, type);
			//单位列表
			String[] deptIds = StringUtil.splits(subPo.getDeptIds(),"|");
			
			String reportLinkId = subPo.getTableLinkBindid();
			for(QueryCondition queryCond:queryConditions){
				for(String depid:deptIds){
					if(depid==null || "".equals(depid)){
						continue;
					}
					String year = queryCond.getYear()+"";
					String periods = queryCond.getPeriods()+"";
					ReportDataService dataService = new ReportDataService(reportLinkId,depid,year,periods);
					
					if(dataService.getDataBindid()==0){//未找到数据
						continue;
					}
					//单元格类表
					String[] cells = StringUtil.splits(subPo.getTableCells(),"|");
					for(String cell:cells){
						if(cell==null || "".equals(cell)){
							continue;
						}
						String hvBindid = StringUtil.splits(cell,"$")[1];
						DataCellPO dataCellPo = dataService.getDataReportCellValue(hvBindid);
						String hbindid = dataCellPo.gethId()+"";
						String vbindid = dataCellPo.getvId()+"";
						String value = dataCellPo.getDatavalue();
						GraphDataTableCell graphCell = new GraphDataTableCell(depid,year+"-"+periods,hbindid,vbindid,value,reportLinkId);
						
						tableCells.add(graphCell);
					}
				}
			}
		}
		this.tableCells = tableCells;
	}
	
	
	public Map<String,List<GraphDataTableCell>> getGroupOfDept(){
		Map<String,List<GraphDataTableCell>> deptGroup = new HashMap<String,List<GraphDataTableCell>>();
		
		for(GraphDataTableCell cell:tableCells){
			String deptName = cell.getDetpaName();
			if(deptGroup.containsKey(deptName)){
				deptGroup.get(deptName).add(cell);
			}else{
				List<GraphDataTableCell> cell_temp = new ArrayList<GraphDataTableCell>();
				cell_temp.add(cell);
				deptGroup.put(deptName,cell_temp);
			}
		}
		
		if(deptGroup.size()==0){
			return null;
		}
		return deptGroup;
	}
	
	
	
	public Map<String,List<GraphDataTableCell>> getGroupOfDataTime(){
		Map<String,List<GraphDataTableCell>> hbindidGroup = new HashMap<String,List<GraphDataTableCell>>();
		
		for(GraphDataTableCell cell:tableCells){
			String dataTime = cell.getDataTimeStr();
			if(hbindidGroup.containsKey(dataTime)){
				hbindidGroup.get(dataTime).add(cell);
			}else{
				List<GraphDataTableCell> cell_temp = new ArrayList<GraphDataTableCell>();
				cell_temp.add(cell);
				hbindidGroup.put(dataTime,cell_temp);
			}
		}
		
		if(hbindidGroup.size()==0){
			return null;
		}
		return hbindidGroup;
	}
	
	public Map<String,List<GraphDataTableCell>> getSameKindCell(){
		Map<String,List<GraphDataTableCell>> sameKindCellGroup = new HashMap<String,List<GraphDataTableCell>>();
		
		for(GraphDataTableCell cell:tableCells){
			String sameKindKey = cell.getHbindid()+"#"+cell.getVbindid();
			if(sameKindCellGroup.containsKey(sameKindKey)){
				sameKindCellGroup.get(sameKindKey).add(cell);
			}else{
				List<GraphDataTableCell> cell_temp = new ArrayList<GraphDataTableCell>();
				cell_temp.add(cell);
				sameKindCellGroup.put(sameKindKey,cell_temp);
			}
		}
		
		return sameKindCellGroup;
	}
	
	public Map<TableHeaderCell,List<GraphDataTableCell>> getGroupOfHbindid(){
		Map<TableHeaderCell,List<GraphDataTableCell>> hbindidGroup = new HashMap<TableHeaderCell,List<GraphDataTableCell>>();
		
		for(GraphDataTableCell cell:tableCells){
			TableHeaderCell headerCell = cell.getHeaderCell();
			if(hbindidGroup.containsKey(headerCell)){
				hbindidGroup.get(headerCell).add(cell);
			}else{
				List<GraphDataTableCell> cell_temp = new ArrayList<GraphDataTableCell>();
				cell_temp.add(cell);
				hbindidGroup.put(headerCell,cell_temp);
			}
		}
		
		if(hbindidGroup.size()==0){
			return null;
		}
		return hbindidGroup;
	}
	

	
	public Map<TableColumnCell,List<GraphDataTableCell>> getGroupOfVbindid(){
		Map<TableColumnCell,List<GraphDataTableCell>> vbindidGroup = new HashMap<TableColumnCell,List<GraphDataTableCell>>();
		
		for(GraphDataTableCell cell:tableCells){
			TableColumnCell columnCell = cell.getColumnCell();
			if(vbindidGroup.containsKey(columnCell)){
				vbindidGroup.get(columnCell).add(cell);
			}else{
				List<GraphDataTableCell> cell_temp = new ArrayList<GraphDataTableCell>();
				cell_temp.add(cell);
				vbindidGroup.put(columnCell,cell_temp);
			}
		}
		
		if(vbindidGroup.size()==0){
			return null;
		}
		return vbindidGroup;
	}
	
	
	public Map<String,Object> getGroupOfVbindid(boolean isAddUp){
		Map<String,Object> returnMap = new HashMap<String,Object>();
		
		Map<TableColumnCell,List<GraphDataTableCell>> vbindidGroup = getGroupOfVbindid();
		if(vbindidGroup==null){
			return null;
		}
		
		Iterator<TableColumnCell> keyIter = vbindidGroup.keySet().iterator();
		GraphDataTableCell cell = null;
		while(keyIter.hasNext()){
			TableColumnCell columnKey = keyIter.next();
			List<GraphDataTableCell> cells = vbindidGroup.get(columnKey);
			
			if(isAddUp){
				
			}else{
				if(this.hbindidCount==1){
					if(cells.size()>0){
						cell = cells.get(0);
						returnMap.put(cell.getvName(), cell.getValue());
					}
				}else{
					Iterator<GraphDataTableCell> vCellsIter = cells.iterator();
					Map<TableHeaderCell,Object> vTohbindidMap = new HashMap<TableHeaderCell,Object>();
					while(vCellsIter.hasNext()){
						GraphDataTableCell vCell = vCellsIter.next();
						TableHeaderCell headerCell = vCell.getHeaderCell();
						if(vTohbindidMap.containsKey(headerCell)){
							((List)vTohbindidMap.get(headerCell)).add(vCell);
						}else{
							List<GraphDataTableCell> cells_temp = new ArrayList<GraphDataTableCell>();
							cells_temp.add(vCell);
							vTohbindidMap.put(headerCell,cells_temp);
						}
					}
					returnMap.put(columnKey.getName(), vTohbindidMap);
				}
			}
		}
		
		if(cell!=null){
			String title = cell.getDetpaName()+"  " + cell.getDataTimeStr();
			functionChars.title = title;
			
			functionChars.yName = cell.gethName();
		}
		
		return returnMap;
	} 
	
	
	
	private List<GraphDataTableCell> tableCells = new ArrayList<GraphDataTableCell>();

	public List<GraphDataTableCell> getTableCells() {
		return tableCells;
	}

	public void setTableCells(List<GraphDataTableCell> tableCells) {
		this.tableCells = tableCells;
	}
	
	
}
