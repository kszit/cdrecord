package com.kszit.CDReport.cor.service.balanced;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromDataItemPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.VerticalColumnService;
import com.kszit.CDReport.cor.service.dataFrom.CalculateAbatract;
import com.kszit.CDReport.cor.service.dataFrom.CalculateColumAllAdd;
import com.kszit.CDReport.cor.service.dataFrom.CalculateColumChildAdd;
import com.kszit.CDReport.cor.service.dataFrom.CalculateTableInFormulaHCount;
import com.kszit.CDReport.cor.service.dataFrom.CalculateTableInFormulaHVCount;
import com.kszit.CDReport.cor.service.dataFrom.CalculateTableInFormulaVCount;
import com.kszit.CDReport.cor.service.dataFrom.CalculateTableOutCount;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 验证报表的平衡关系
 * @author Administrator
 *
 */
public class DalancedChecking {
		TableHeader tableHeader = null;
		TableColumn tableColum = null;
		/**
		 * 配置单元格平衡关系
		 */
		Map<String,HVConfigDataFromPO> dataFromMap = new HashMap<String,HVConfigDataFromPO>();
		List<HeaderRowConfigPO> headerPoList = null;
		List<VerticalColumnConfigPO>  columnPoList = null;
		
		public DalancedChecking(){}
		public DalancedChecking(String reportBindid){
			HeaderRowService hService = new HeaderRowService();
			headerPoList = hService.getHeaderRowCByReportOfPO(reportBindid+"");
			
			VerticalColumnService vService = new VerticalColumnService();
			columnPoList = vService.getVerticalColumnCByReportOfPO(reportBindid+"");
			
			tableHeader = new TableHeader(headerPoList);
			tableColum = new TableColumn(columnPoList);
			
			HVConfigDataFromService dataFromService = new HVConfigDataFromService();
			List<HVConfigDataFromPO> dataFromPoList = dataFromService.getDataFromList(reportBindid);
			for(HVConfigDataFromPO po:dataFromPoList){
				dataFromMap.put(po.getHbindid()+"#"+po.getVbindid(), po);
			}
		}
		
		/**
		 * 计算配置的公式，并将数据放到基础数据对象中
		 * @param dataMap 填报的基础数据
		 */
		public void calculateDataCell(Map<String,Object> dataMap){
			Queue<HVConfigDataFromPO> queue = getDataFromQueue();
			
			HVConfigDataFromPO datafromPo = null;
			while(!queue.isEmpty()){
				datafromPo = queue.poll();
				String result = calculateDataCell(datafromPo,dataMap);
				if(result!=null){
					if(result==CalculateAbatract.LAST_CALCULATE){
						queue.offer(datafromPo);
						continue;
					}
					dataMap.put(datafromPo.getHbindid()+"#"+datafromPo.getVbindid(), result);
				}
			}
		}
		
		/**
		 * 计算公式项的队列
		 * @return
		 */
		private Queue<HVConfigDataFromPO> getDataFromQueue(){
			Queue<HVConfigDataFromPO> queue = new LinkedList<HVConfigDataFromPO>();
			for(VerticalColumnConfigPO colPo:columnPoList){
				for(HeaderRowConfigPO headerPo:headerPoList){
					HVConfigDataFromPO datafromPo = getDataFromPO(headerPo,colPo,dataFromMap);
					if(datafromPo != null){
						queue.offer(datafromPo);
					}
				}
			}
			return queue;
		}
		
		
		/**
		 * 计算一个单元格数据
		 * @param datafromPo 单元格的计算公式等数据
		 * @param dataMap  填报的基础数据
		 */
		public String calculateDataCell(HVConfigDataFromPO datafromPo,Map<String,Object> dataMap){
			String result = null;
			CalculateAbatract calculate = null; 
			/*int dataFromType = Integer.parseInt(datafromPo.getDataFromType());
			switch(dataFromType){
			case StaticVaribles.DataFrom_ColumChildAddBindId://列孩子子项和
				calculate = new CalculateColumChildAdd(datafromPo,dataMap,tableColum);
				break;
			case StaticVaribles.DataFrom_ColumAllAddBindId://所有列子项和
				calculate = new CalculateColumAllAdd(datafromPo,dataMap,tableColum);
				break;
			case StaticVaribles.DataFrom_TableInFormulaHCountBindId://横向计算公式
				calculate = new CalculateTableInFormulaHCount(datafromPo,dataMap);
				break;
			case StaticVaribles.DataFrom_TableInFormulaVCountBindId://纵向计算公式
				calculate = new CalculateTableInFormulaVCount(datafromPo,dataMap);
				break;
			case StaticVaribles.DataFrom_TableInFormulaHVCountBindId://横纵向计算公式
				calculate = new CalculateTableInFormulaHVCount(datafromPo,dataMap);
				break;
			case StaticVaribles.DataFrom_TableOutCountBindId:
				calculate = new CalculateTableOutCount(datafromPo,dataMap);
				break;

				/*
			case StaticVaribles.DataFrom_HandInputBindId:
				break;
				
			default:
			
			}
			*/
			if(calculate!=null){
				result = calculate.calculate();
			}
			return result;
		}
		
		/**
		 * 每个单元格的计算公式
		 * @param headerPo 横向配置对象
		 * @param conlumPo 纵向配置对象
		 * 
		 * @return
		 */
		public static HVConfigDataFromPO getDataFromPO(HeaderRowConfigPO headerPo,VerticalColumnConfigPO conlumPo,Map<String,HVConfigDataFromPO> dataFromMap){
			long hbindid = headerPo.getBindId();
			long vbindid = conlumPo.getBindId();
			//使用特定单元格中的计算公式
			if(dataFromMap != null && dataFromMap.containsKey(hbindid+"#"+vbindid)){
				HVConfigDataFromPO dataFromPO = dataFromMap.get(hbindid+"#"+vbindid);
				return getDataFromPO(headerPo,conlumPo,dataFromPO);
			}else{
				return getDataFromPO(headerPo,conlumPo,new HVConfigDataFromPO());
			}
		}
		/**
		 * 根据横向配置、纵向配置和单元格配置获得单元格的计算公式，都为配置时 返回null
		 * @param headerPo
		 * @param conlumPo
		 * @param dataFrom
		 * @return
		 */
		public static HVConfigDataFromPO getDataFromPO(HeaderRowConfigPO headerPo,VerticalColumnConfigPO conlumPo,HVConfigDataFromPO dataFrom){
			HVConfigDataFromPO dataFromPO = null;
			long hbindid = headerPo.getBindId();
			long vbindid = conlumPo.getBindId();
			
			//使用header中配置的计算公式   
			if(headerPo.getDataFrom()!=0){//&& headerPo.getDataFrom()!=StaticVaribles.DataFrom_HandInputBindId
				dataFromPO = new HVConfigDataFromPO();
				dataFromPO.setHbindid(hbindid);
				dataFromPO.setVbindid(vbindid);
				dataFromPO.setDataFromType(headerPo.getDataFrom()+"");
				dataFromPO.setFormula(headerPo.getDataRule());
				dataFromPO.setDataTerm(headerPo.getDataRuleItems());
			}
			
			if(dataFrom != null){
				String dataFromType = dataFrom.getDataFromType();
				if(dataFromType!=null && !"".equals(dataFromType)){
					dataFromPO = dataFrom;
				}
			}

			initDataItemDeclare(dataFromPO);
			return dataFromPO;
		}
		
		/**
		 * 初始化单元格数据获取规则
		 * @param dataFromPO
		 */
		public static void initDataItemDeclare(HVConfigDataFromPO dataFromPO){
			if(dataFromPO == null){
				return;
			}
			StringBuffer sb = new StringBuffer();
			String dataTerm = dataFromPO.getDataTerm();
			if(dataTerm==null || "".equals(dataTerm)){
				return;
			}
			String[] dataItems = dataFromPO.getDataTerm().split("\\|");
			for(String dataItem:dataItems){
				HVConfigDataFromDataItemPO item = new HVConfigDataFromDataItemPO(dataItem);
				if(item.getReportBindid()!=null){
					String reportName = ReportConfigService.getReportConfigPO(item.getReportBindid()).getReportName();
					String headerName = "";
					if(!"0".equals(item.gethBindid())){
						headerName = TableHeader.getOneHeader(item.getReportBindid()+"",item.gethBindid()).getHeaderRowConfigPO().getContent();
					}
					String columnName = "";
					if(!"0".equals(item.getvBindid())){
						columnName = TableColumn.getOneColumn(item.getReportBindid()+"",item.getvBindid()).getVerticalColumnConfigPO().getContent();
					}
					sb.append(reportName+"==>"+headerName+":"+columnName+"    ");
				}
			}
			dataFromPO.setDataItemDeclare(sb.toString());
		}
		
		
		
		

}
