package com.kszit.CDReport.cor.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kszit.CDReport.cor.dao.ReportConfigDao;
import com.kszit.CDReport.cor.dao.hibernate.ReportConfigDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.exception.MyHibernateException;
import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.util.HibernateUtil2;
import com.kszit.CDReport.cor.model.ReportConfigModel;
import com.kszit.CDReport.util.Constants;
import com.kszit.CDReport.util.StaticVaribles;

public class ReportConfigService {
	/**
	 * 报表列表     bindid-->报表主表对象
	 */
	private static Map<String,ReportConfigPO> reportConfigPoMap = new HashMap<String,ReportConfigPO>();
	static{
		ReportConfigDao rcDao2 = new ReportConfigDaoHiberImpl();
		List<ReportConfigPO> poList = rcDao2.getReportConfigs();
		for(ReportConfigPO po:poList){
			reportConfigPoMap.put(po.getBindId()+"-"+po.getRtversion2(), po);
		}
	}
	
	/**
	 * 
	* getReportConfigPO(报表的主配置信息)  
	* TODO(这里描述这个方法适用条件 – 可选)  
	* TODO(这里描述这个方法的执行流程 – 可选)  
	* TODO(这里描述这个方法的使用方法 – 可选)  
	* TODO(这里描述这个方法的注意事项 – 可选)  
	* @param reportBindidLink：报表的连接bindid  
	* @param @return 设定文件  
	* @return String DOM对象  
	* @Exception 异常对象  
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public static ReportConfigPO getReportConfigPO(String reportBindidLink){
		if(Constants.IS_DEVELEP){
			reportConfigPoMap.clear();
			ReportConfigDao rcDao2 = new ReportConfigDaoHiberImpl();
			List<ReportConfigPO> poList = rcDao2.getReportConfigs();
			for(ReportConfigPO po:poList){
				reportConfigPoMap.put(po.getBindId()+"-"+po.getRtversion2(), po);
			}
		}
		return reportConfigPoMap.get(reportBindidLink);
	}
	
	ReportConfigDao rcDao = null;
	public ReportConfigService(){
		rcDao = new ReportConfigDaoHiberImpl();
	}
	
	/**
	 * 某年的报表
	 * @param model
	 * @return
	 */
	public List<ReportConfigPO> getReportConfigOfYear(ReportConfigModel model){
		String year = model.getRtyear();
		List<ReportConfigPO> configList = rcDao.getReportConfigsByYear(year);
		return configList;
	}
	
	/**
	 * 某年的报表
	 * @param model
	 * @return
	 */
	public List<ReportConfigModel> getReportConfigModelOfYear(ReportConfigModel model){
		List<ReportConfigModel> models = new ArrayList<ReportConfigModel>();
		List<ReportConfigPO> pos = this.getReportConfigOfYear(model);
		for(ReportConfigPO po:pos){
			ReportConfigModel modelte = new ReportConfigModel(po);
			models.add(modelte);
		}
		return models;
	}
	
	/**
	 * 
	 * @param model
	 * @return
	 */
	public List<ReportConfigPO> getReportConfigByLike(ReportConfigModel model){
		ReportConfigPO config = new ReportConfigPO(model);
		return this.getReportConfigByLike(config);
	}
	
	
	/**
	 * 模糊查询配置报表，
	 * 已经设置的查询条件为：reportState、rttype、rtyear、reportName like
	 * @param model
	 * @return
	 */
	public List<ReportConfigPO> getReportConfigByLike(ReportConfigPO config){
		List<ReportConfigPO> configList = rcDao.getReportConfigByLike(config);
		return configList;
	}
	
	/**
	 * 保存或更新配置主表
	 * @param model
	 * @return
	 */
	public String saveOrUpdate(ReportConfigModel model){
		ReportConfigPO po = new ReportConfigPO(model);
		boolean iscreateBindid = po.initBindId();
		String id = rcDao.saveOrUpdate(po);
		if(iscreateBindid){
			DataDBTableIndexService tableservice = new DataDBTableIndexService();
			tableservice.insert(po.getBindId()+"");
		}
		return id;
	}
	
	/**
	 * 保存或更新配置主表
	 * @param po
	 * @return
	 */
	public String saveOrUpdate(ReportConfigPO po){
		po.initBindId();
		String id = rcDao.saveOrUpdate(po);
		if(po.getRtversion2()==0){
			DataDBTableIndexService tableservice = new DataDBTableIndexService();
			tableservice.insert(po.getBindId()+"");
		}
		return id;
	}
	/**
	 * 保存或更新配置主表，不生成数据表。
	 * @param po
	 * @return
	 */
	public String saveOrUpdateNoCreateT(ReportConfigPO po){
		po.initBindId();
		String id = rcDao.saveOrUpdate(po);
		return id;
	}
	/**
	 * 生成新的主表
	 * @param oldReportBindid
	 * @return
	 */
	public ReportConfigPO copyReportConfig(ReportConfigPO oldConfigPo){
		
		ReportConfigPO oldReportConfig = getReportConfigByBindidLink(oldConfigPo.getReportBindidLink());
		oldReportConfig.setReportState(StaticVaribles.ReportState_DiscardBindId);//修改旧版本不可用
		ReportConfigPO newReportConfig = new ReportConfigPO(oldReportConfig);
		int newVersion = rcDao.getNewVersion(oldConfigPo.getBindId().longValue());
		//修改版本号
		newReportConfig.setRtversion2(newVersion);
		String id = saveOrUpdate(newReportConfig);
		newReportConfig.setReportState(StaticVaribles.ReportState_ConfigBindId);
		ReportConfigPO newConfigPo = getReportConfigById(Integer.parseInt(id));
		
		new HeaderRowService().copyHeaderRow(oldConfigPo, newConfigPo);
		new VerticalColumnService().copyVerticalColumn(oldConfigPo, newConfigPo);
		return newConfigPo;
	}
	
	/**
	 * 通过id获得配置主表信息
	 * @param id
	 * @return ReportConfigPO
	 */
	public ReportConfigPO getReportConfigById(int id){
		return rcDao.getReportConfigById(id);
	}
	
	/**
	 * 已经配置的年份列表
	 * @return
	 */
	public Set<String> getConfigYearList(){
		List<ReportConfigPO> poList = rcDao.getReportConfigs();
		
		Set<String> yearSet = new HashSet<String>();
		for(ReportConfigPO configPo:poList){
			yearSet.add(configPo.getRtyear());
		}
		return yearSet;
	}
	
	/**
	 * 通过id获得配置主表信息
	 * @param id
	 * @return  ReportConfigModel
	 */
	public ReportConfigModel getReportConfigByIdWithModel(int id){
		return new ReportConfigModel(getReportConfigById(id));
	}
	
	/**
	 *  通过bindid获得配置主表信息
	 * @param bindid
	 * @return
	 */
	public List<ReportConfigPO> getReportConfigByBindid(String reportbindid){
		
		return rcDao.getReportConfigByBindid(reportbindid);
	}
	
	/**
	 *  通过bindid和version获得配置主表信息
	 * @param reportbindid  格式 reportbindid-version
	 * @return
	 */
	public ReportConfigPO getReportConfigByBindidLink(String reportbindid){
		if(Constants.IS_DEVELEP){
			String[] temp = reportbindid.split("-");
			return rcDao.getReportConfigByBindid(temp[0],temp[1]);
		}else{
			return reportConfigPoMap.get(reportbindid);
		}

	}
	/**
	 * 所有配置报表列表
	 * @return
	 */
	public List<ReportConfigPO> getReportConfigs(){
		List<ReportConfigPO> poList = rcDao.getReportConfigs();
		return poList;
	}
	
	/**
	 * 所有配置报表列表
	 * @return
	 */
	public List<ReportConfigModel> getReportConfigsOfModel(){
		List<ReportConfigModel> models = new ArrayList<ReportConfigModel>();
		List<ReportConfigPO> pos = this.getReportConfigs();
		for(ReportConfigPO po:pos){
			ReportConfigModel modelte = new ReportConfigModel(po);
			models.add(modelte);
		}
		return models;
	}
	
	/**
	 *  
	 *  根据报表获得， 报表的期数列表
	* @Exception 异常对象  - 无
	* @since  -
	 */
	public List<DictionaryPO> getReportPeriodList(ReportConfigModel config){
		if(config==null || config.getRttype()==null){
			return null;
		}
		String type = StaticVaribles.changePeriodToPeriodDataBindid(Integer.parseInt(config.getRttype()))+"";
		DictionaryService dicservice = new DictionaryService();
		List<DictionaryPO> poList = dicservice.getDictionaryByType(Long.parseLong(type));
		return poList;
	}
	

	
	/**
	 * 删除单个
	 * @param id
	 * @return
	 */
	public boolean delete(String id){
		DataDBTableIndexService tableservice = new DataDBTableIndexService();
		boolean isdeleteTableIndex = tableservice.deleteByReportid(id);
		
		boolean isdelete = rcDao.delete(Long.parseLong(id));
		
		return isdeleteTableIndex && isdelete;
	}
	
	/**
	 * 删除多个
	 * @param id
	 * @return
	 */
	public boolean deletes(String ids){
		DataDBTableIndexService tableservice = new DataDBTableIndexService();
		String[] ids_temp = ids.split("\\,");
		for(String id:ids_temp){
			ReportConfigPO configPo = getReportConfigById(Integer.parseInt(id.trim()));
			String reportBindid = configPo.getReportBindidLink();
			new HeaderRowService().deletes(reportBindid);
			new VerticalColumnService().deletesByReport(reportBindid);
			new HVConfigDataFromService().deletes(reportBindid);
			new HVConfigUIModelService().deletes(reportBindid);
			tableservice.dropTable(configPo.getBindId()+"");
		}
		
		
		
		boolean isdeleteTableIndex = tableservice.deleteByReportids(ids);
		boolean isdelete = rcDao.deletes(ids);
		try {
			HibernateUtil2.commitTransaction();
		} catch (MyHibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isdeleteTableIndex && isdelete;
	}

	/**
	 * 生成上级报表的汇总表时 使用。从下级的报表中选择部门指标形成股份公司的汇总表，指定报表的期限后，汇总表会自动抓取已经上报的数据。
	 * @param newReportBindid  新报表的id，
	 * @param oldReportBindid  老报表到id
	 * @param selectedCell 选择的单元格，格式：hbindid#vbindid|hbindid#vbindid|hbindid#vbindid|
	 * @param parentId 父节点id    在新报表的哪个节点下 挂在选择的下级指标。
	 */
	public void copyHVPartFromOthor(String newReportBindid,String oldReportBindid,String selectedCell,String parentId){
		new HeaderRowService().copyHVPartFromOthor(newReportBindid,oldReportBindid,selectedCell,parentId);
		
	}
	
	public void copyHPartFromOthor(String newReportBindid,String oldReportBindid,String selectedCell,String parentId){
		new HeaderRowService().copyHPartFromOthor(newReportBindid,oldReportBindid,selectedCell,parentId);
		
	}
	
	

}
