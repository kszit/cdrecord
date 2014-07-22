package com.kszit.CDReport.cor.service.dataFrom;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromDataItemPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.ReportConfigService;
import com.kszit.CDReport.cor.service.VerticalColumnService;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.util.ClassInstallUtil;
import com.kszit.CDReport.util.StaticVaribles;
/**
 * 保存单元格数据前，计算表格中的合计项和计算公式项
 * @author Administrator
 *
 */
public class DataFrom {
	TableHeader tableHeader = null;
	TableColumn tableColum = null;
	DataReportModel model = null;
	
	/**
	 * 配置单元格数据来源
	 */
	Map<String,HVConfigDataFromPO> dataFromMap = new HashMap<String,HVConfigDataFromPO>();
	List<HeaderRowConfigPO> headerPoList = null;
	List<VerticalColumnConfigPO>  columnPoList = null;
	
	//需要计算的单元格
	Queue<HVConfigDataFromPO> queue = new LinkedList<HVConfigDataFromPO>();
	
	public DataFrom(){}
	public DataFrom(String reportBindid,DataReportModel model){
		this.model = model;
		
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
	 * 若输入框为数字类型，但未填写，传过来的值为“”，需要将“”转换为0
	 * @param dataMap
	 */
	public void initNullNumber(Map<String,Object> dataMap){
		for(HeaderRowConfigPO headerPo:headerPoList){
			int hType = headerPo.getDataType();
			//数值类型的列
			if(hType==StaticVaribles.DataType_NumberBindId){
				String hBindid = headerPo.getBindId()+"";
				for(VerticalColumnConfigPO colPo:columnPoList){
					String vBindid = colPo.getBindId()+"";
					String value = dataMap.get(hBindid+"#"+vBindid)+"";
					if("".equals(value)){
						dataMap.put(hBindid+"#"+vBindid, "0");
					}
				}
			}
			
		}
	}
	
	/**
	 * 查看计算公式列表中是否包括此单元格
	 * @param hbindid
	 * @param vbindid
	 * @return
	 */
	public boolean queueContainKey(long hbindid,long vbindid){
		boolean return_value = false;
		Iterator<HVConfigDataFromPO> iter =  queue.iterator();
		while(iter.hasNext()){
			HVConfigDataFromPO dataFromPo = iter.next();
			long dataFromPoHbindid = dataFromPo.getHbindid();
			long dataFromPoVbindid = dataFromPo.getVbindid();
			if(dataFromPoHbindid==hbindid && dataFromPoVbindid==vbindid){
				return_value = true;
				break;
			}
		}
		return return_value;
	}
	
	/**
	 * 计算配置的公式，并将数据放到基础数据对象中
	 * @param dataMap 填报的基础数据
	 */
	public void calculateDataCell(Map<String,Object> dataMap){
		getDataFromQueue();
		
		HVConfigDataFromPO datafromPo = null;
		while(!queue.isEmpty()){
			datafromPo = queue.poll();//删除一条记录
			String result = calculateDataCell(datafromPo,dataMap);
			if(result!=null){
				if(result==CalculateAbatract.LAST_CALCULATE){
					queue.offer(datafromPo);//添加一条记录
					continue;
				}else if(result==CalculateAbatract.NOT_PUT_DATA){
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
		for(VerticalColumnConfigPO colPo:columnPoList){
			for(HeaderRowConfigPO headerPo:headerPoList){
				HVConfigDataFromPO datafromPo = getDataFromPO(headerPo,colPo,dataFromMap);
				if(datafromPo != null){
					//System.out.println("添加数据:"+colPo.getContent()+"-->"+headerPo.getContent());
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
		int dataFromType = Integer.parseInt(datafromPo.getDataFromType());
		if(dataFromType==0){
			return null;
		}
		
		Class[] classesParam = new Class[]{HVConfigDataFromPO.class,Map.class,TableHeader.class ,TableColumn.class ,DataReportModel.class,DataFrom.class};
		Object[] objectsParam = new Object[]{datafromPo,dataMap,tableHeader,tableColum,model,this};
		DictionaryService dicService = new DictionaryService();
		
		DictionaryPO dicPo = dicService.getOneByBindid(dataFromType+"");
		String classPathAndName = dicPo.getDescribe2();
		if(classPathAndName!=null && !"".equals(classPathAndName) && dicPo.isUsed()){
			calculate = (CalculateAbatract)ClassInstallUtil.installClass(classPathAndName, classesParam, objectsParam);
		}
		
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
	 * 根据横向配置、纵向配置和单元格配置获得单元格的计算公式，都未配置时 返回null
	 * @param headerPo
	 * @param conlumPo
	 * @param dataFrom
	 * @return
	 */
	public static HVConfigDataFromPO getDataFromPO(HeaderRowConfigPO headerPo,VerticalColumnConfigPO conlumPo,HVConfigDataFromPO dataFrom){
		HVConfigDataFromPO dataFromPO = null;
		long hbindid = headerPo.getBindId();
		long vbindid = conlumPo.getBindId();
		
		if(dataFrom == null){
			dataFromPO = new HVConfigDataFromPO();
			dataFromPO.setReportBindid(headerPo.getReportBindid());
			dataFromPO.setHbindid(hbindid);
			dataFromPO.setVbindid(vbindid);
			dataFromPO.setDataFromType(headerPo.getDataFrom()+"");
			dataFromPO.setFormula(headerPo.getDataRule());
			dataFromPO.setDataTerm(headerPo.getDataRuleItems());
		}else{
			String dataFromType = dataFrom.getDataFromType();
			if(dataFromType!=null && !"".equals(dataFromType)){
				dataFromPO = dataFrom;
			}else{
				dataFromPO = new HVConfigDataFromPO();
				dataFromPO.setReportBindid(headerPo.getReportBindid());
				dataFromPO.setHbindid(hbindid);
				dataFromPO.setVbindid(vbindid);
				dataFromPO.setDataFromType(headerPo.getDataFrom()+"");
				dataFromPO.setFormula(headerPo.getDataRule());
				dataFromPO.setDataTerm(headerPo.getDataRuleItems());
			}
		}
		/*
		//使用header中配置的计算公式   列表头不为手工录入
		if((headerPo.getDataFrom()!=0 && headerPo.getDataFrom()!=StaticVaribles.DataFrom_HandInputBindId)||dataFrom == null){//
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
*/
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
