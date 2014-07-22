package com.kszit.CDReport.cor.controler;

import java.util.ArrayList;
import java.util.List;

import com.kszit.CDReport.cor.model.HVConfigUIModelModel;
import com.kszit.CDReport.cor.model.HeaderRowConfigModel;
import com.kszit.CDReport.cor.service.HVConfigUIModelService;
import com.kszit.CDReport.cor.service.HeaderRowService;
import com.kszit.CDReport.util.StaticVaribles;
import com.opensymphony.xwork2.Action;


/**
 * 数据关系配置
 * <code>Set welcome message.</code>
 */
public class HVConfigUIModel extends ReportCDSupport {
	
	private static final long serialVersionUID = 5769706251309447318L;
	
	HeaderRowConfigModel headerRowModel = new HeaderRowConfigModel();
	
	private List<HVConfigUIModelModel> uiModelList = new ArrayList<HVConfigUIModelModel>();
	
	private int uiModelTypeBindid = StaticVaribles.ConfigHVUIModeleBindId;
	
	private String saveDataJson;
	/**
	 * 数据关系配置页面
	 * @return
	 */
	public String uiModelConfigPage(){

		HeaderRowService headerRow = new HeaderRowService();
		headerRowModel = headerRow.getHeaderRowConfigModelByBindId(headerRowModel.getReportBindid(),headerRowModel.getBindId());
		headerRowModel.setUiModelChildItemSet(StaticVaribles.IsSetting_SettingBindId);
		headerRow.updateUIModelSetSpel(headerRowModel);
		
		return "uiModelConfigPage";
	}
	
	/**
	 * 数据关系json数据
	 * @return
	 */
	public String uiModelJson(){
		HVConfigUIModelService service = new HVConfigUIModelService();
		
		uiModelList = service.getUIModelList(headerRowModel.getReportBindid(), headerRowModel.getBindId());
		return Action.SUCCESS;
	}
	/**
         * 保存
         * @return 
         */
	public String save(){
		HVConfigUIModelService service = new HVConfigUIModelService();
		String result = service.save(saveDataJson);
		super.outText(result);
		return Action.SUCCESS;
	}

	public HeaderRowConfigModel getHeaderRowModel() {
		return headerRowModel;
	}
	public void setHeaderRowModel(HeaderRowConfigModel headerRowModel) {
		this.headerRowModel = headerRowModel;
	}

	public List<HVConfigUIModelModel> getUiModelList() {
		return uiModelList;
	}

	public void setUiModelList(List<HVConfigUIModelModel> uiModelList) {
		this.uiModelList = uiModelList;
	}

	public String getSaveDataJson() {
		return saveDataJson;
	}

	public void setSaveDataJson(String saveDataJson) {
		this.saveDataJson = saveDataJson;
	}

	public int getUiModelTypeBindid() {
		return uiModelTypeBindid;
	}

	public void setUiModelTypeBindid(int uiModelTypeBindid) {
		this.uiModelTypeBindid = uiModelTypeBindid;
	}

	
}
