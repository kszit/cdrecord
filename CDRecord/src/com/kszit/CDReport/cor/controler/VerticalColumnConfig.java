package com.kszit.CDReport.cor.controler;

import java.util.ArrayList;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.VerticalColumnConfigPO;
import com.kszit.CDReport.cor.model.VerticalColumnConfigModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.VerticalColumnService;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;


/**
 * 纵向配置
 * @author Administrator
 *
 */
public class VerticalColumnConfig extends ReportCDSupport {

	private static final long serialVersionUID = 1252306234202277444L;

	private List<VerticalColumnConfigModel> verticalColumns = new ArrayList<VerticalColumnConfigModel>();
	
	private VerticalColumnConfigModel verticalColumnModel = new VerticalColumnConfigModel();
	//UI模型列表
	List<DictionaryPO> uimodeList = new ArrayList<DictionaryPO>();
	public String deleteIds;
	
	public String parentid;
	
	public String saveDataJson;
	
	public String reportid;
	
	private String param1;
	private String param2;
	private String param3;
	private String param4;
	
	/**
	 * 修改UIModel属性页面
	 * @return
	 */
	public String uiModelConfigPage(){
		DictionaryService service = new DictionaryService();
		uimodeList = service.getDictionaryByType(StaticVaribles.ConfigHVUIModeleBindId);
		
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		verticalColumnModel = verticalColumnService.getVerticalColByBindid(verticalColumnModel.getBindId());
		return "uiModelConfig";
	}
	/**
	 * 数据来源配置页面
	 * @return
	 */
	public String dataFromConfigPage(){
		DictionaryService service = new DictionaryService();
		uimodeList = service.getDictionaryByType(StaticVaribles.ConfigHVdataFromBindId);
		
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		verticalColumnModel = verticalColumnService.getVerticalColByBindid(verticalColumnModel.getBindId());
		
		
		return "dataFromConfig";
	}
	/**
	 * 数据字典或者业务表  生成行表头
	* leftItemFromDictOrBuess( 数据字典或者业务表  生成行表头  ) <br> 
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
	public String leftItemFromDictOrBuess(){
		String dictid = param1;
		String reportLinkId = param2;
		
		VerticalColumnService vService = new VerticalColumnService();
		vService.createLeftItemFromDictOrBuess(dictid,reportLinkId);
		
		super.outText("suc");
		return Action.SUCCESS;
	}
	
	/**
	 * 保存UIModel属性
	 * @return
	 */
	public String uiModelConfigSave(){
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		verticalColumnService.updateUIModel(verticalColumnModel);
		return Action.SUCCESS;
	}
	/**
	 * 获取纵向列的json代码
	 * @return
	 */
	public String vertiCalcolumnJson(){
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		verticalColumns = new ArrayList<VerticalColumnConfigModel>(verticalColumnService.getVerticalColumnCByReport(this.reportid));
		return Action.SUCCESS;
	}
	/**
	 * 横行表头 保存
	 * @return
	 */
	public String save(){
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		String result = verticalColumnService.saveOrUpdate(saveDataJson);
		super.outText(result);
		return Action.SUCCESS;
	}
	
	/**
	 * 横向表头 删除
	 * @return
	 */
	public String delete(){
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		verticalColumnService.deletes(deleteIds);
		super.outText("1");
		return Action.SUCCESS;
	}
	/**
	 * 获得新添加的行id
	 * @return
	 */
	public String newVertical(){
		VerticalColumnService verticalColumnService = new VerticalColumnService();
		String indexOrder = verticalColumnService.nextVerticalColumnIndexOrder(reportid,parentid);
		VerticalColumnConfigPO po = new VerticalColumnConfigPO();
		po.setOrderIndexStr(indexOrder);
		po.set_is_leaf(true);
		po.setReportBindid(reportid);
		po.set_parent(Integer.parseInt(parentid));
                po.setDataFrom(StaticVaribles.DataFrom_HandInputBindId);
                po.setUIModule(StaticVaribles.HTMLUIModule_InputBindid);
                po.setDataLength(50);
                po.setDataType(StaticVaribles.DataType_TextBindId);
               // po.setUiModelChildItemSet(StaticVaribles.IsSetting_NotSettingBindId);
                //po.setDataFromChildItemSet(StaticVaribles.IsSetting_NotSettingBindId);
                po.setWidth(100);
                po.setHeight(100);
                
                
                
		
		String newId = verticalColumnService.getNewVerticalColumnId(po);
		super.outText(newId+"@@"+indexOrder+"@@"+po.getBindId());
		return Action.SUCCESS;
	}
	public List<VerticalColumnConfigModel> getVerticalColumns() {
		return verticalColumns;
	}
	public void setVerticalColumns(List<VerticalColumnConfigModel> verticalColumns) {
		this.verticalColumns = verticalColumns;
	}
	public VerticalColumnConfigModel getVerticalColumnModel() {
		return verticalColumnModel;
	}
	public void setVerticalColumnModel(VerticalColumnConfigModel verticalColumnModel) {
		this.verticalColumnModel = verticalColumnModel;
	}
	public String getDeleteIds() {
		return deleteIds;
	}
	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getSaveDataJson() {
		return saveDataJson;
	}
	public void setSaveDataJson(String saveDataJson) {
		this.saveDataJson = saveDataJson;
	}
	public String getReportid() {
		return reportid;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	public List<DictionaryPO> getUimodeList() {
		return uimodeList;
	}
	public void setUimodeList(List<DictionaryPO> uimodeList) {
		this.uimodeList = uimodeList;
	}
	public String getParam1() {
		return param1;
	}
	public void setParam1(String param1) {
		this.param1 = param1;
	}
	public String getParam2() {
		return param2;
	}
	public void setParam2(String param2) {
		this.param2 = param2;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}

	
	
}
