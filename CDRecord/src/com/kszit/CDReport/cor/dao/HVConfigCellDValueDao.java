package com.kszit.CDReport.cor.dao;

import java.util.List;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigCellDValuePO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;

/**
 * cell默认值
*  
* 项目名称：CDRecord  
* 类名称：HVConfigCellDValueDao  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月8日 下午5:43:52  
* 修改人：hanxiaowei  
* 修改时间：2014年3月8日 下午5:43:52  
* 修改备注：  
* @version  
*
 */
public interface HVConfigCellDValueDao extends DaoParent{

	public String insertOrUpdate(HVConfigCellDValuePO data);
	
	public HVConfigCellDValuePO getCellValue(String reportBindid,long hbindid,long vbindid);
	
	public List<HVConfigCellDValuePO> getListOfReportId(String reportbindid);
	

}
