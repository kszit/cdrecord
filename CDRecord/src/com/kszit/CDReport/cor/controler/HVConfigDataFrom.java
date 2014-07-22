package com.kszit.CDReport.cor.controler;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.HVConfigDataFromModel;
import com.kszit.CDReport.cor.model.HeaderRowConfigModel;
import com.kszit.CDReport.cor.model.VerticalColumnConfigModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.HVConfigDataFromService;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.cor.service.VerticalColumnService;
import com.kszit.CDReport.cor.service.dataFrom.DataFrom;
import com.kszit.CDReport.cor.service.dataFrom.DataFromHtml;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;


/**
 * 数据关系配置
 * <code>Set welcome message.</code>
 */
public class HVConfigDataFrom extends ReportCDSupport {
	
	private static final long serialVersionUID = 5769706251309447318L;
	/**
	 * 数据源可选择项（下拉列表中的项）
	 */
	List<DictionaryPO> uimodeList = new ArrayList<DictionaryPO>();
	//要配置的列名称（即，横向顶部列名称）
	HeaderRowConfigModel headerRowModel = new HeaderRowConfigModel();
	//横向指标（即，左侧列名称）
	private List<VerticalColumnConfigModel> verticalColumns = new ArrayList<VerticalColumnConfigModel>();
	private Long[] selectedVertical;
	//数据关系类型bindid
	private int dataFromTypeBindid = StaticVaribles.ConfigHVdataFromBindId;
	
	private List<HVConfigDataFromModel> dataFromList = new ArrayList<HVConfigDataFromModel>();
	
	private HVConfigDataFromModel cellDataFrom = new HVConfigDataFromModel();
	
	private String saveDataJson;
	
	/**
	 * 
	* saveCellDataFrom(   保存配置的单个单元格的数据来源   ) <br> 
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
	public String saveCellDataFrom(){
		String reportBindid = cellDataFrom.getReportBindid();
		long hBindid = cellDataFrom.getHbindid();
		long vBindid = cellDataFrom.getVbindid();
		
		HVConfigDataFromService service = new HVConfigDataFromService();
		HVConfigDataFromPO cellDataFrom2 = service.getCellDataFrom(reportBindid,hBindid,vBindid);
		if(cellDataFrom2!=null){
			cellDataFrom2.setDataFromType(cellDataFrom.getDataFromType());
			cellDataFrom2.setHbindid(cellDataFrom.getHbindid());
			cellDataFrom2.setVbindid(cellDataFrom.getVbindid());
			service.update(cellDataFrom2);
		}else{
			
			service.save(cellDataFrom);
		}
		
		return this.SUCCESS;
	}
	
	
	/**
	 * 
	* oneCellDateFromConfigPage(单个单元格的数据来源配置页面，) <br> 
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
	public String oneCellDateFromConfigPage(){ 
		String reportBindid = cellDataFrom.getReportBindid();
		long hBindid = cellDataFrom.getHbindid();
		long vBindid = cellDataFrom.getVbindid();
		String dataFromType = cellDataFrom.getDataFromType();
		
		HVConfigDataFromService service = new HVConfigDataFromService();
		HVConfigDataFromPO cellDataFrom = service.getCellDataFrom(reportBindid,hBindid,vBindid);
		
		HeaderRowService hRowService = new HeaderRowService();
		HeaderRowConfigPO headerRow = hRowService.getHeaderRowConfigPoByBindId(reportBindid, hBindid);
		
		VerticalColumnService vColumnSerivce = new VerticalColumnService();
		VerticalColumnConfigPO vColumn = vColumnSerivce.getOneByReportIdAndBindid(reportBindid, vBindid);
		
		if(cellDataFrom==null){
			cellDataFrom = new HVConfigDataFromPO();
		}
		
		HVConfigDataFromPO dataFrom = DataFrom.getDataFromPO(headerRow,vColumn,cellDataFrom);
		
		this.cellDataFrom = new HVConfigDataFromModel(dataFrom);
		 
		return "dataFromConfigHVCell";
	}
	
	/**
	 * 数据关系配置页面
	 * @return
	 */
	public String dataFromConfigPage(){
		//数据来源类型列表
		DictionaryService service = new DictionaryService();
		uimodeList = service.getDictionaryByType(StaticVaribles.ConfigHVdataFromBindId);
		//更新横行配置的是否已经配置特定数据关系为 设定 
		HeaderRowService headerRow = new HeaderRowService();
		headerRowModel = headerRow.getHeaderRowConfigModelByBindId(headerRowModel.getReportBindid(),headerRowModel.getBindId());
		headerRowModel.setDataFromChildItemSet(StaticVaribles.IsSetting_SettingBindId);
		headerRow.updateDataFromSetSpel(headerRowModel);
		
		//纵向数据
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		verticalColumns = new ArrayList<VerticalColumnConfigModel>(verticalColumnService.getVerticalColumnCByReport(headerRowModel.getReportBindid()+""));
		//checkbox 被选中的项
		selectedVertical = new Long[]{new Long(390),new Long(393)};
		return "dataFromConfig";
	}
	
	/**
	 * 
	* dataFromConfigHtml(列表头数据来源配置时，每类数据来源的配置) <br> 
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
	public String dataFromConfigHtml(){
		String dataFromBindid = super.param1;
		String reportLinkBindid = super.param2;
		
		String htmlCode = DataFromHtml.getHtml(dataFromBindid,reportLinkBindid);
		//super.outText("<center><br><br><br><br><h4>无配置</h4>");
		super.outText(htmlCode);
		return Action.SUCCESS;
	}
	
	public String cellDataFromConfigHtml(){
		String dataFromBindid = super.param1;
		String reportLinkBindid = super.param2;
		
		String htmlCode = DataFromHtml.getCellHtml(dataFromBindid,reportLinkBindid);
		//super.outText("<center><br><br><br><br><h4>无配置</h4>");
		super.outText(htmlCode);
		return Action.SUCCESS;
	}
	
	/**
	 * 数据关系json数据
	 * @return
	 */
	public String dataFromJson(){
		HVConfigDataFromService service = new HVConfigDataFromService();
		
		dataFromList = service.getDataFromList(headerRowModel.getReportBindid(), headerRowModel.getBindId());
		return Action.SUCCESS;
	}
	/**
         * 保存数据关系
         * @return 
         */
	public String save(){
		HVConfigDataFromService service = new HVConfigDataFromService();
		String result = service.save(saveDataJson);
		super.outText(result);
		return Action.SUCCESS;
	}
	
	
	
	public List<DictionaryPO> getUimodeList() {
		return uimodeList;
	}
	public void setUimodeList(List<DictionaryPO> uimodeList) {
		this.uimodeList = uimodeList;
	}
	public HeaderRowConfigModel getHeaderRowModel() {
		return headerRowModel;
	}
	public void setHeaderRowModel(HeaderRowConfigModel headerRowModel) {
		this.headerRowModel = headerRowModel;
	}

	public List<VerticalColumnConfigModel> getVerticalColumns() {
		return verticalColumns;
	}

	public void setVerticalColumns(List<VerticalColumnConfigModel> verticalColumns) {
		this.verticalColumns = verticalColumns;
	}
	public Long[] getSelectedVertical() {
		return selectedVertical;
	}
	public void setSelectedVertical(Long[] selectedVertical) {
		this.selectedVertical = selectedVertical;
	}
	public List<HVConfigDataFromModel> getDataFromList() {
		return dataFromList;
	}
	public void setDataFromList(List<HVConfigDataFromModel> dataFromList) {
		this.dataFromList = dataFromList;
	}

	public int getDataFromTypeBindid() {
		return dataFromTypeBindid;
	}

	public void setDataFromTypeBindid(int dataFromTypeBindid) {
		this.dataFromTypeBindid = dataFromTypeBindid;
	}

	public String getSaveDataJson() {
		return saveDataJson;
	}

	public void setSaveDataJson(String saveDataJson) {
		this.saveDataJson = saveDataJson;
	}

	public HVConfigDataFromModel getCellDataFrom() {
		return cellDataFrom;
	}

	public void setCellDataFrom(HVConfigDataFromModel cellDataFrom) {
		this.cellDataFrom = cellDataFrom;
	}

	
}
