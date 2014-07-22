package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.HVConfigCellDValueModel;

/**
 * 
 * 单元格默认值 配置信息
*  
* 项目名称：CDRecord  
* 类名称：HVConfigCellDValuePO  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月8日 下午12:34:18  
* 修改人：hanxiaowei  
* 修改时间：2014年3月8日 下午12:34:18  
* 修改备注：  
* @version  
*
 */
public class HVConfigCellDValuePO extends HVConfigPO{

	private String defaultValue;
	//
	private String dataType;
	
	public HVConfigCellDValuePO(){}
	
	public HVConfigCellDValuePO(String reportBindid,long hbindid,long vbindid,String defaultValue){
		super.setReportBindid(reportBindid);
		super.setHbindid(hbindid);
		super.setVbindid(vbindid);
		this.defaultValue = defaultValue;
	}
	
	public HVConfigCellDValuePO(HVConfigCellDValueModel model){
		try {
			BeanUtils.copyProperties(this,model);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
