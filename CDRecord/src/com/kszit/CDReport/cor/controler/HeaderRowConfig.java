package com.kszit.CDReport.cor.controler;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.model.HeaderRowConfigModel;
import com.kszit.CDReport.cor.model.ReportConfigModel;
import com.kszit.CDReport.cor.model.TreeNodeModel;
import com.kszit.CDReport.cor.model.TreeNodeWithCheckModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;


/**
 * 横向配置
 * @author Administrator
 *
 */
public class HeaderRowConfig extends ReportCDSupport {

	private static final long serialVersionUID = 1252306234202277444L;

	private ReportConfigModel reportConfigM = new ReportConfigModel();
	
	private List<HeaderRowConfigModel> headerRows = new ArrayList<HeaderRowConfigModel>();
	
	HeaderRowConfigModel headerRowModel = new HeaderRowConfigModel();
	
	//UI模型列表
	List<DictionaryPO> uimodeList = new ArrayList<DictionaryPO>();
	
	public String deleteIds;
	
	public String parentid;
	
	private String reportid;
	
	public String saveDataJson;
	
	//配置报表时，引用其他报表的配置信息
	List<Object> reportItemTree = null;
	
	public String headerConfigFromOtherHVPageTreeDate(){
		String parnetId = parentid;
		String fromHeaderRoleBindid = reportid;
		String toHeaderRoleBindid = "";
		HeaderRowService headerRow = new HeaderRowService();
		List<HeaderRowConfigPO> fromHeaderRole = headerRow.getHeaderRowByParentId(fromHeaderRoleBindid,parnetId);
		//List<HeaderRowConfigPO> toHeaderRole = headerRow.getHeaderRowCByReportOfPO(toHeaderRoleBindid);
		
		reportItemTree = new ArrayList<Object>();
		for(HeaderRowConfigPO fromPo:fromHeaderRole){
			TreeNodeWithCheckModel treeModel = new TreeNodeWithCheckModel();
			//[{"bindId":100,"checked":false,"href":"http:\/\/www.sina.com.cn","hrefTarget":"mainFrame","id":100,"isleaf":true,"orderIndex":10,"parentid":0,"rid":0,"text":"新浪"},
			treeModel.setId(fromPo.getBindId().intValue());
			treeModel.setText(fromPo.getContent());
			treeModel.setIsleaf(fromPo.is_is_leaf());
			treeModel.setChecked(false);
			reportItemTree.add(treeModel);
		}
		return Action.SUCCESS;
	}
	
	/**
	 * UIModel属性页面
	 * @return
	 */
	public String uiModelConfigPage(){
		DictionaryService service = new DictionaryService();
		uimodeList = service.getDictionaryByType(StaticVaribles.ConfigHVUIModeleBindId);
		
		HeaderRowService headerRow = new HeaderRowService();
		headerRowModel = headerRow.getHeaderRowConfigModelByBindId(headerRowModel.getReportBindid(),headerRowModel.getBindId());

		return "uiModelConfig";
	}
	
	/**
	 * 保存UIModel属性
	 * @return
	 */
	public String uiModelConfigSave(){
		HeaderRowService headerRow = new HeaderRowService();
		headerRow.updateUIModel(headerRowModel);
		return Action.SUCCESS;
	}
	/**
	 * 数据源属性页面
	 * @return
	 */
	public String dataFromConfigPage(){
		DictionaryService service = new DictionaryService();
		uimodeList = service.getDictionaryByType(StaticVaribles.ConfigHVdataFromBindId);
		
		HeaderRowService headerRow = new HeaderRowService();
		headerRowModel = headerRow.getHeaderRowConfigModelByBindId(headerRowModel.getReportBindid(),headerRowModel.getBindId());

		return "dataFromConfigPage";
	}
	
	/**
	 * 
	* dataFromTypeTreeJson(  数据来源的tree数据     ) <br> 
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
	public String dataFromTypeTreeJson(){
		if(param1!=null && "0".equals(param1)){
			DictionaryService service = new DictionaryService();
			uimodeList = service.getDictionaryByType(StaticVaribles.ConfigHVdataFromBindId);
			for(DictionaryPO dicPo:uimodeList){
				TreeNodeModel treeNode = new TreeNodeModel();
				treeNode.setId(dicPo.getBindId().intValue());
				treeNode.setText(dicPo.getName());
				treeNode.setIsleaf(true);
				//treeNode.setHref(dicPo.getDescribe2().split("\\|")[1]+"");
				//treeNode.setHrefTarget("setDataFromPage");
				super.treeObjects.add(treeNode);
			}
		}
		
		
		return Action.SUCCESS;
	}
	
	/**
	 * 保存数据源属性
	 * @return
	 */
	public String dataFromConfigSave(){
		HeaderRowService headerRow = new HeaderRowService();
		headerRow.updateDataFrom(headerRowModel);
		return Action.SUCCESS;
	}
	
	/**
	 * 横向表头的json代码
	 * @return
	 */
	public String headerRowsJson(){
		HeaderRowService headerRow = new HeaderRowService();
		headerRows = new ArrayList<HeaderRowConfigModel>(headerRow.getHeaderRowCByReport(this.reportid));
		//headerRow.closeSession();
		return Action.SUCCESS;
	}
	/**
	 * 横行表头 保存
	 * @return
	 */
	public String save(){
		HeaderRowService headerRow = new HeaderRowService();
//System.out.println("保存表头："+saveDataJson);
		String result = headerRow.saveOrUpdate(saveDataJson);
		super.outText(result);
		return Action.SUCCESS;
	}
	
	/**
	 * 横向表头 删除
	 * @return
	 */
	public String delete(){
		HeaderRowService headerRow = new HeaderRowService();
		headerRow.deletes(reportid,deleteIds);
		super.outText("1");
		return Action.SUCCESS;
	}
	/**
	 * 获得新添加的行id
	 * @return
	 */
	public String newHeaderRow(){
		HeaderRowService headerRow = new HeaderRowService();
		String indexOrder = headerRow.nextHeaderRowIndexOrder(reportid,parentid);
		HeaderRowConfigPO po = new HeaderRowConfigPO();
		po.setOrderIndexStr(indexOrder);
		po.setReportBindid(reportid);
		po.set_is_leaf(true);
		po.set_parent(Integer.parseInt(parentid));
                po.initParam();

                
                
                
                
                
                
		
		String newId = headerRow.getNewHeaderRowId(po);
		
		super.outText(newId+"@@"+indexOrder+"@@"+po.getBindId());
		return Action.SUCCESS;
	}
	
	public ReportConfigModel getReportConfigM() {
		return reportConfigM;
	}

	public void setReportConfigM(ReportConfigModel reportConfigM) {
		this.reportConfigM = reportConfigM;
	}

	public List<HeaderRowConfigModel> getHeaderRows() {
		return headerRows;
	}

	public void setHeaderRows(List<HeaderRowConfigModel> headerRows) {
		this.headerRows = headerRows;
	}
	public String getDeleteIds() {
		return deleteIds;
	}
	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}
	public String getSaveDataJson() {
		return saveDataJson;
	}
	public void setSaveDataJson(String saveDataJson) {
		this.saveDataJson = saveDataJson;
	}
	public HeaderRowConfigModel getHeaderRowModel() {
		return headerRowModel;
	}
	public void setHeaderRowModel(HeaderRowConfigModel headerRowModel) {
		this.headerRowModel = headerRowModel;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getReportid() {
		return reportid;
	}
	public void setReportid(String reportid) {
		this.reportid = reportid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public List<DictionaryPO> getUimodeList() {
		return uimodeList;
	}
	public void setUimodeList(List<DictionaryPO> uimodeList) {
		this.uimodeList = uimodeList;
	}
	
	public List<Object> getReportItemTree() {
		return reportItemTree;
	}

	public void setReportItemTree(List<Object> reportItemTree) {
		this.reportItemTree = reportItemTree;
	}
	
}
