package com.kszit.CDReport.cor.model.page;

import com.kszit.CDReport.util.Constants;

/**
 *
 *web页面配置参数
*  
* 项目名称：CDRecord  
* 类名称：PageParam  
* 类描述：  
* 创建人：hanxiaowei  
* 创建时间：2014年3月10日 下午5:15:05  
* 修改人：hanxiaowei  
* 修改时间：2014年3月10日 下午5:15:05  
* 修改备注：  
* @version  
*
 */
public class PageParam {

	//列表页 显示条数
	private String pageListSize = Constants.PAGE_LIST_SIEZ;

	public String getPageListSize() {
		return pageListSize;
	}

	public void setPageListSize(String pageListSize) {
		this.pageListSize = pageListSize;
	}
	
	
	
}
