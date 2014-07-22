package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.HVConfigDataFromModel;
import com.kszit.CDReport.util.StaticVaribles;

/**
 * 单个单元格的数据来源 配置信息
*  
* 项目名称：CDRecord  
* 类名称：HVConfigDataFromPO  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月8日 下午12:33:20  
* 修改人：hanxiaowei  
* 修改时间：2014年3月8日 下午12:33:20  
* 修改备注：  
* @version  
*
 */
public class HVConfigDataFromPO extends HVConfigPO{

	private String dataFromType;
	//公式
	private String formula;
	//数据项
	private String dataTerm;
	//数据项的文字说明
	private String dataItemDeclare;
	
	public HVConfigDataFromPO(){}
	
	public HVConfigDataFromPO(HVConfigDataFromModel model){
		try {
			BeanUtils.copyProperties(this,model);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(model.getId()==0){
			this.setId(null);
		}
	}

	public HVConfigDataFromPO(HVConfigDataFromPO po){
		try {
			BeanUtils.copyProperties(this,po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setId(null);
	}

	
	public String getDataFromType() {
		return dataFromType;
	}

	public void setDataFromType(String dataFromType) {
		this.dataFromType = dataFromType;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public String getDataTerm() {
		return dataTerm;
	}

	public void setDataTerm(String dataTerm) {
		this.dataTerm = dataTerm;
	}
	
	@Override 
	public boolean equals(Object arg0) { 
		return super.equals(arg0);
	}

	public String getDataItemDeclare() {
		return dataItemDeclare;
	}

	public void setDataItemDeclare(String dataItemDeclare) {
		this.dataItemDeclare = dataItemDeclare;
	} 
	
	/**
	 * 是否需要在页面中 填写
	* @Title: isSetInPage 
	* @Description:  
	* @param：
	* @return： boolean
	* @throws：
	 */
	public boolean isSetInPage(){
		if(StaticVaribles.DataFrom_HandInputBindId==Integer.parseInt(this.dataFromType)){
			return true;
		}
		return false;
	}
	
	/**
	 * 从下级部门的单元格中获取数据时，单元格的所在表的bindid
	 * @return
	 */
	public String getFormulaOfLowerReportBindid(){
		String[] s = formula.split("\\|");
		if(s.length>1){
			return s[0];
		}else{
			return null;
		}
	}
	
	/**
	 * 从下级部门的单元格中获取数据时，单元格的hBindid#vBindid
	 * @return
	 */
	public String getFormulaOfLowerReportHVBindid(){
		String[] s = formula.split("\\|");
		if(s.length>1){
			return s[1];
		}else{
			return null;
		}
	}

}
