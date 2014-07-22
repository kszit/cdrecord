package com.kszit.CDReport.cor.dao.hibernate.po;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Administrator
 *
 */
public class HVConfigDataFromDataItemPO extends ParentPO{

	private String reportBindid;
	
	private String hBindid;
	
	private String vBindid;
	
	private int periods;
	
	public HVConfigDataFromDataItemPO(){}
	
	/**
	 * 
	* 创建一个新的实例 HVConfigDataFromDataItemPO.  
	*  
	* @param config :reportLinkbindid#hbindid#vbindid#时期
	 */
	public HVConfigDataFromDataItemPO(String config){
		String[] datas = config.split("#");
		if(datas.length>=4){
			this.reportBindid = datas[0];
			this.hBindid = datas[1];
			this.vBindid = datas[2];
			this.periods = Integer.parseInt(datas[3]);
		}
	}
	
	/**
	 * 
	* 创建一个新的实例 HVConfigDataFromDataItemPO.  
	*  
	* @param reportLinkBindid  报表的bindid
	* @param hvbindid   hbindid和vbindid，格式：hbindid#vbindid
	 */
	public HVConfigDataFromDataItemPO(String reportLinkBindid,String hvbindid){
		String[] datas = hvbindid.split("#");
		if(datas.length>=2){
			this.reportBindid = reportLinkBindid;
			this.hBindid = datas[0];
			this.vBindid = datas[1];
		}
	}
	/**
	 * 
	* getPoWithConfig(通过配置项，获得HVConfigDataFromDataItemPO对象列表，配置项的为：reportLinkbindid#hbindid#vbindid#时期|reportLinkbindid#hbindid#vbindid#时期  ) <br> 
	* TODO(这里描述这个方法适用条件 – 无)  <br>
	* TODO(这里描述这个方法的执行流程 – 无)  <br>
	* TODO(这里描述这个方法的使用方法 – 无)  <br>
	* TODO(这里描述这个方法的注意事项 – 无)  <br>
	* @param config:reportLinkbindid#hbindid#vbindid#时期|reportLinkbindid#hbindid#vbindid#时期  
	* @param @return 设定文件  - 无
	* @return String DOM对象  - 无
	* @Exception 异常对象  - 无
	* @since  -
	 */
	public static List<HVConfigDataFromDataItemPO> getPoWithConfig(String config){
		List<HVConfigDataFromDataItemPO> dataItemList = new ArrayList<HVConfigDataFromDataItemPO>();
		String[] configs = config.split("\\|");
		for(String c:configs){
			HVConfigDataFromDataItemPO item = new HVConfigDataFromDataItemPO(c);
			dataItemList.add(item);
		}
		return dataItemList;
	}
	
	public static List<HVConfigDataFromDataItemPO> getPoWithConfigOf(String config){
		List<HVConfigDataFromDataItemPO> dataItemList = new ArrayList<HVConfigDataFromDataItemPO>();
		String[] configs = config.split("\\|");
		for(String c:configs){
			String[] temp = c.split("\\$");
			HVConfigDataFromDataItemPO item = new HVConfigDataFromDataItemPO(temp[0],temp[1]);
			dataItemList.add(item);
		}
		return dataItemList;
	}
	
	
	public String getReportBindid() {
		return reportBindid;
	}

	public void setReportBindid(String reportBindid) {
		this.reportBindid = reportBindid;
	}

	public String gethBindid() {
		return hBindid;
	}

	public void sethBindid(String hBindid) {
		this.hBindid = hBindid;
	}

	public String getvBindid() {
		return vBindid;
	}

	public void setvBindid(String vBindid) {
		this.vBindid = vBindid;
	}

	public int getPeriods() {
		return periods;
	}

	public void setPeriods(int periods) {
		this.periods = periods;
	}
}
