package com.kszit.CDReport.cor.controler;

import org.apache.commons.logging.Log;

import com.kszit.CDReport.cor.log.LogService;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.model.QueryReportConditionModel;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.QueryConditionHTable;
import com.kszit.CDReport.cor.service.reportData.QueryConditionVTable;
import com.kszit.CDReport.cor.service.reportData.QueryReportDataTable;
import com.kszit.CDReport.util.StaticVaribles;


/**
 * 报表配置
 * @author Administrator
 *
 */
public class QueryReportControler extends ReportCDSupport {
    private static Log log = LogService.getLogger(QueryReportControler.class);
	
    private String queryConditonHtml = "";
    
    private String queryResultHtml = "";
    private QueryReportConditionModel queryCondition = new QueryReportConditionModel();
    private DataReportModel dataReportModel = new DataReportModel();
    public String allReportMainPage(){
    	
    	
    	return "allReportMainPage";
    }
    
    /**
     * 
    * setQueryConditionOfTable( 设置报表横向或纵向的查询条件页面 ) <br> 
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
    public String setQueryConditionOfTable(){
    	String type = this.param1;
    	String reportBindid = this.param2;
    	

    	if("h".equals(type)){
    		QueryConditionHTable queryTable = new QueryConditionHTable(reportBindid);
    		queryConditonHtml = queryTable.getTable();
		}else if("v".equals(type)){
			QueryConditionVTable queryTable = new QueryConditionVTable(reportBindid);
			queryConditonHtml = queryTable.getTable();
		}
    	

    	return "setQueryConditonPage";
    }
    
    public String queryResultPage(){
		
		ReportDataService reportDataServer = new ReportDataService();
		dataReportModel = reportDataServer.getDataReportModelByLike(queryCondition.getReportBindid()+"",queryCondition.getYear(),queryCondition.getPeriod(),queryCondition.getDept(),StaticVaribles.DataReportFlowState_publish+"");
    	if(dataReportModel==null){
    		queryResultHtml = "无数据";
    		return "queryResultPage";
    	}
    	QueryReportDataTable queryTable = new QueryReportDataTable(queryCondition,dataReportModel.getBindId()+"");
    	queryResultHtml = queryTable.getTable();
    	return "queryResultPage";
    }
    
    
    
    
    
    
    
    
    
    
    
    

	public String getQueryConditonHtml() {
		return queryConditonHtml;
	}

	public void setQueryConditonHtml(String queryConditonHtml) {
		this.queryConditonHtml = queryConditonHtml;
	}

	public String getQueryResultHtml() {
		return queryResultHtml;
	}

	public void setQueryResultHtml(String queryResultHtml) {
		this.queryResultHtml = queryResultHtml;
	}

	public QueryReportConditionModel getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(QueryReportConditionModel queryCondition) {
		this.queryCondition = queryCondition;
	}

	public DataReportModel getDataReportModel() {
		return dataReportModel;
	}

	public void setDataReportModel(DataReportModel dataReportModel) {
		this.dataReportModel = dataReportModel;
	}
    
    
    
}
