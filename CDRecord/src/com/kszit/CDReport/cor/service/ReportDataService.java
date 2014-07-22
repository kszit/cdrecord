package com.kszit.CDReport.cor.service;


import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.DataReportDao;
import com.kszit.CDReport.cor.dao.ReportConfigDao;
import com.kszit.CDReport.cor.dao.hibernate.DataReportDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.ReportConfigDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataCellPO;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.model.DepartmentAndReport;
import com.kszit.CDReport.cor.model.ReportAppareModel;
import com.kszit.CDReport.cor.model.ReportConfigModel;
import com.kszit.CDReport.cor.service.dataFrom.DataFrom;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;

public class ReportDataService {
	DataReportDao dataReportDao = null;
	public int dataBindid = 0;
	DataCellService cellService = null;
	
	public ReportDataService(){
		dataReportDao = new DataReportDaoHiberImpl();
	}
	/**
	 * 通过配置报表的bindid、部门id和时期，锁定填报主表，获得填报数据的dataBindid
	 * 
	 * @param reportBindid  配置报表的bindid-lingk
	 * @param deptid        部门id
	 * @param pero          时期
	 */
	public ReportDataService(String reportBindid,String deptid,String year,String pero){
		dataReportDao = new DataReportDaoHiberImpl();
		DataReportPO queryDataReport = new DataReportPO();
		queryDataReport.setCreateDepBindid(deptid);
		queryDataReport.setRepotBindid(reportBindid);
		queryDataReport.setYear(year);
		queryDataReport.setPeriods(pero);
		queryDataReport.setVerify(StaticVaribles.DataReportFlowState_publish);
		
		
		List<DataReportPO> dataReportPos = dataReportDao.getDataReports(queryDataReport);
		if(dataReportPos.size()>0){
			DataReportPO dataReportPo = dataReportPos.get(0);
			this.dataBindid = dataReportPo.getBindId().intValue();
		}
		
		
		cellService = new DataCellService(reportBindid,this.dataBindid);
	}
	
	/**
	 * 查看报表详细数据时 调用此方法初始化对象
	 * @param reportBindid
	 */
	public ReportDataService(String reportBindid,String dataBindid){
		this.dataBindid = Integer.parseInt(dataBindid);
		cellService = new DataCellService(reportBindid,this.dataBindid);
		dataReportDao = new DataReportDaoHiberImpl();
	}
	
	
	/**
	 * 查看报表详细数据时 调用此方法初始化对象
	 * @param reportBindid
	 */
	public ReportDataService(String reportBindid){
		cellService = new DataCellService(reportBindid,this.dataBindid);
		dataReportDao = new DataReportDaoHiberImpl();
	}
	/**
	 * 通过bindid获得填报主表对象
	 * @param bindid
	 * @return
	 */
	public DataReportPO getDataReportByBindid(long bindid){
		return dataReportDao.getDataReportByBindid(bindid);
	}
	
	
	
	
	/**
	 * 获得报表单元格的数据
	 * @param reportId  配置报表的链接bindid-版本号
	 * @param dataId    数据表的bindid
	 * return Map:::key：hbindid#vbindid    value:value
	 */
	public Map<String,String> getDataReportCellDatas(String dataId){
		Map<String,String> dataMap = new HashMap<String,String>();
		List<DataCellPO> dataPoList = this.getReportDataList(Integer.parseInt(dataId));
		for(DataCellPO po:dataPoList){
			dataMap.put(po.gethId()+"#"+po.getvId(), po.getDatavalue());
		}
		return dataMap;
	}
	
	/**
	 * 获得某单元格的数据。需要查询的报表已经在类的初始化方法中锁定
	 * 
	 * @param hbindid
	 * @param vbindid
	 * @return
	 */
	/**
	 * 
	* getDataReportCellValue(获得单元的数据)  <br>
	* TODO(这里描述这个方法适用条件 – 调用ReportDataService(String reportBindid,String deptid,String year,String pero)方法初始化类)  <br>
	* @since  CodingExample　Ver(编码范例查看) 1.1
	 */
	public DataCellPO getDataReportCellValue(long hbindid,long vbindid){
		return cellService.getOneDataCell(hbindid, vbindid);
	}
	
	/**
	 * 获得某单元格的数据。需要查询的报表已经在类的初始化方法中锁定
	 * @param hvBindid  hbindid#vbindid
	 * @return
	 */
	public DataCellPO getDataReportCellValue(String hvBindid){
		long hbindid = Long.parseLong(StringUtil.getHBindid(hvBindid));
		long vbindid = Long.parseLong(StringUtil.getVBindid(hvBindid));
		return getDataReportCellValue(hbindid,vbindid);
	}
	
	/**
	 * 查询报表报送情况
	 * @param reportAppareModel
	 * @return
	 */
	public List<ReportAppareModel> getReportAppear(ReportAppareModel reportAppareModel){
		List<ReportAppareModel> appearList = new ArrayList<ReportAppareModel>();
		ReportConfigModel currentReportConfigModel = reportAppareModel.getReportConfigModel();
		DataReportModel currentDataReportModel = reportAppareModel.getDataReportModel();
		ReportConfigDao rcDao = new ReportConfigDaoHiberImpl();
		
		List<ReportConfigPO> configList = rcDao.getReportConfigByLike(new ReportConfigPO(currentReportConfigModel));
		for(ReportConfigPO config:configList){
			if(config.getRttype()!=null){
				int rtType = Integer.parseInt(config.getRttype());
				if(rtType==StaticVaribles.ReportFreq_YearBindId){//年报
					ReportAppareModel appareModel = new ReportAppareModel();
					appareModel.setReportConfigModel(new ReportConfigModel(config));
					
					DataReportModel dataReportModel = new DataReportModel();
					dataReportModel.setVerify(-1);
					dataReportModel.setRepotBindid(config.getReportBindidLink());
					dataReportModel.setCreateDepBindid(currentDataReportModel.getCreateDepBindid());
					DataReportModel queryDataModel = getDataReportModelByLike(dataReportModel);
					
					appareModel.setDataReportModel(queryDataModel);
					appearList.add(appareModel);
				}else if(rtType==StaticVaribles.ReportFreq_PeriodBindId){//季报
					for(int i=1;i<=4;i++){
						ReportAppareModel appareModel = new ReportAppareModel();
						appareModel.setReportConfigModel(new ReportConfigModel(config));
						
						DataReportModel dataReportModel = new DataReportModel();
						dataReportModel.setVerify(-1);
						dataReportModel.setRepotBindid(config.getReportBindidLink());
						dataReportModel.setCreateDepBindid(currentDataReportModel.getCreateDepBindid());
						dataReportModel.setPeriods(""+i);
						DataReportModel queryDataModel = getDataReportModelByLike(dataReportModel);
						
						appareModel.setDataReportModel(queryDataModel);
						appearList.add(appareModel);
					}

				}else if(rtType==StaticVaribles.ReportFreq_MonthBindId){//月报
					for(int i=1;i<=12;i++){
						ReportAppareModel appareModel = new ReportAppareModel();
						appareModel.setReportConfigModel(new ReportConfigModel(config));
						
						DataReportModel dataReportModel = new DataReportModel();
						dataReportModel.setVerify(-1);
						dataReportModel.setRepotBindid(config.getReportBindidLink());
						dataReportModel.setCreateDepBindid(currentDataReportModel.getCreateDepBindid());
						dataReportModel.setPeriods(""+i);
						DataReportModel queryDataModel = getDataReportModelByLike(dataReportModel);
						
						appareModel.setDataReportModel(queryDataModel);
						appearList.add(appareModel);
					}
				}
			}
		}
		return appearList;
	}
	//=========================================================================
	/**
	 * 获取所有填报的报表,此查询中  包括了 上级部门的汇总报表
	 * @param model
	 * @return
	 */
	public List<DataReportModel> getAllDataReport(){
		List<DataReportModel> modelList = new ArrayList<DataReportModel>();
		List<DataReportPO> poList = dataReportDao.getDataReports();
		for(DataReportPO po:poList){
			DataReportModel model = new DataReportModel(po);
			modelList.add(model);
		}
		return modelList;
	}
	
	/**
	 * 获取所有填报的报表,此查询中  
	 * @param reportType  341：填报报表   342：汇总报表
	 * @return
	 */
	public List<DataReportModel> getAllDataReport(String reportType){
		List<DataReportModel> modelList = new ArrayList<DataReportModel>();
		//构造查询条件
		ReportAppareModel reportAppareModel = new ReportAppareModel();
		ReportConfigModel reportConfigModel = new ReportConfigModel();
		reportConfigModel.setType(reportType);
		reportAppareModel.setReportConfigModel(reportConfigModel);
		
		List<Object> appearData = dataReportDao.getAppearReport(reportAppareModel);
		for(Object objets:appearData){
			DataReportPO dataReportPo = (DataReportPO)objets;
			DataReportModel model = new DataReportModel(dataReportPo);
			modelList.add(model);
		}
		
		return modelList;
	}
	
	
	
	/**
	 * 模糊查找，返回查找到的数据，若没有查找到数据返回条件中的对象model,只返回一个对象
	 * @param model
	 * @return
	 */
	public DataReportModel getDataReportModelByLike(DataReportModel model){
		DataReportModel returnModel = null;
		DataReportPO po = new DataReportPO(model);
		List<DataReportPO> poList = dataReportDao.getDataReports(po);
		if(poList.size()==0){
			return model;
		}else{
			returnModel = new DataReportModel(poList.get(0));
		}
		return returnModel;
	}

	/**
	 * 
	* getDataReportModelByLike(  根据配置报表的id，年份，时期，部门id，流程状态 查询，只返回一条记录，若查找出多条，只返回一条数据) <br> 
	* @since  -
	 */
	public DataReportModel getDataReportModelByLike(String reportLinkBindid,String year,String peroid,String deptid,String verify){
		DataReportPO po = new DataReportPO();
		po.setRepotBindid(reportLinkBindid);
		po.setYear(year);
		po.setPeriods(peroid);
		po.setCreateDepBindid(deptid);
		po.setVerify(Integer.parseInt(verify));
		
		DataReportModel returnModel = null;
		List<DataReportPO> poList = dataReportDao.getDataReports(po);
		if(poList.size()==0){
			return null;
		}else{
			returnModel = new DataReportModel(poList.get(0));
		}
		return returnModel;
	}
	
	/**
	 * 模糊查找，返回查找到的数据，若没有查找到数据返回条件中的对象model,只返回一个对象
	 * @param model
	 * @return
	 */
	public List<DataReportModel> getDataReportModelsByLike(DataReportModel model){
		
		List<DataReportPO> poList = this.getDataReportPOsByLike(model);
		List<DataReportModel> modelList = new ArrayList<DataReportModel>();
		for(DataReportPO po:poList){
			DataReportModel tempModel = new DataReportModel(po);
			modelList.add(tempModel);
		}
		return modelList;
	}
	
	/**
	 * 模糊查找，返回查找到的数据，若没有查找到数据返回条件中的对象model
	 * @param model
	 * @return
	 */
	public List<DataReportPO> getDataReportPOsByLike(DataReportModel model){
		DataReportPO po = new DataReportPO(model);
		List<DataReportPO> poList = dataReportDao.getDataReports(po);
		return poList;
	}
	
	/**
	 * 按照条件查询已经上报的报表，查询条件有：Verify、repotBindid、madeManName、createDepBindid、periods
	 *
	 *
	 * @return
	 */
	public List<DataReportPO> getDataReportList(DataReportModel dataReportModel){
		DataReportPO po = new DataReportPO(dataReportModel);
		return dataReportDao.getDataReports(po);
	}
	
	/**
	 * 模糊查找
	 * @return
	 */
	public List<DataReportPO> getDataReportListByLike(DataReportPO po){
		return dataReportDao.getDataReports(po);
	}
	
	//=========================================================================
	
	/**
	 * 查询报表报送情况,此方法方法为用的
	 * @param reportAppareModel
	 * @return
	 */
	public List<ReportAppareModel> getReportAppear2(ReportAppareModel reportAppareModel){
		List<ReportAppareModel> appearList = new ArrayList<ReportAppareModel>();
		List<Object> appearData = dataReportDao.getAppearReport(reportAppareModel);
		for(Object objets:appearData){
			ReportAppareModel appearModel = new ReportAppareModel();
			//appearModel.setReportConfigModel(new ReportConfigModel((ReportConfigPO)objets[0]));
			DataReportPO dataReportPo = (DataReportPO)objets;
			if(dataReportPo.getRepotBindid()!=null){
				appearModel.setDataReportModel(new DataReportModel(dataReportPo));
			}
			appearList.add(appearModel);
		}
		
		return appearList;
	}
	
	
	
	/**
	 * 
	 * @param ids
	 * @return
	 */
	public boolean deletes(String ids){
		String[] array_ids = ids.split("\\,");
		for(int i=0,ids_length=array_ids.length;i<ids_length;i++){
			long id = Long.parseLong(array_ids[i].trim());
			DataReportPO dataPo = dataReportDao.getDataReportById(id);
			new DataCellService(dataPo.getRepotBindid(),dataPo.getBindId().intValue()).deleteCellData();
		}
		
		return dataReportDao.deletes(ids);
	}
	
	/**
	 * 保存报表数据（主表和子表）===页面和excel调用
	 * @param model
	 */
	public int saveDataReport(DataReportModel model,Map<String,Object> dataMap){
		saveDataReportZhu(model);//保存主表
		
		DataFrom dataFrom = new DataFrom(model.getRepotBindid(),model);
		dataFrom.initNullNumber(dataMap);//设置未填写数字框的值为0
		dataFrom.calculateDataCell(dataMap);//计算合计项
		
		saveTableCellsData(dataMap);
		
		return this.dataBindid;
	}

	
	/**
	 * 保存主表
	 * @param model
	 */
	public void saveDataReportZhu(DataReportModel model){
		DataReportPO po = new DataReportPO(model);
		po.initBindId();
		String id = dataReportDao.saveOrUpdate(po);
		dataBindid = dataReportDao.getDataReportById(Long.parseLong(id)).getBindId().intValue();
	}
	
	/**
	 * 设置报表到审核人
	 * @param model
	 */
	public void verifyReport(DataReportModel model){
		DataReportPO po = new DataReportPO(model);
		//po.setAppearDate(new Date(System.currentTimeMillis()));
		po.setVerify(StaticVaribles.DataReportFlowState_verify);
		dataReportDao.saveOrUpdate(po);
	}
	/**
	 * 设置报表到上报
	 * @param model
	 */
	public void appearReport(long bindid,String verifyManid){
		DataReportPO po = dataReportDao.getDataReportByBindid(bindid);
		po.setVerifyManName(verifyManid);
		po.setVerifyManDate(new Date(System.currentTimeMillis()));
		po.setAppearDate(new Date(System.currentTimeMillis()));
		po.setVerify(StaticVaribles.DataReportFlowState_publish);
		dataReportDao.saveOrUpdate(po);
	}
	/**
	 * 设置报表回退
	 * @param model
	 */
	public void backReport(long bindid){
		DataReportPO po = dataReportDao.getDataReportByBindid(bindid);
		//po.setAppearDate(new Date(System.currentTimeMillis()));
		po.setVerify(StaticVaribles.DataReportFlowState_back);
		dataReportDao.saveOrUpdate(po);
	}
	
	
	/**
	 * 查看某部门此报表是否已经上报，
	 * 判定参数：配置报表的 报表id，部门id、期限、报表状态（审核或者上报）
	 * @param dataReport
	 * @return
	 */
	public DataReportModel hasAppearReport(DataReportModel dataReport){
		DataReportModel retunDataReportModel = null;
		
		DataReportPO po = new DataReportPO();
		po.setRepotBindid(dataReport.getRepotBindid());
		po.setCreateDepBindid(dataReport.getCreateDepBindid());
		po.setPeriods(dataReport.getPeriods());
		po.setVerify(StaticVaribles.DataReportFlowState_verifyAndpublish);
		List<DataReportPO> returnPoLists = dataReportDao.getDataReports(po);
		if(returnPoLists!=null && returnPoLists.size()==1){
			retunDataReportModel = new DataReportModel(returnPoLists.get(0));
		}
		return retunDataReportModel;
	}
	
	/**
	 * 
		模糊查询
	 * @param dataReport
	 * @return
	 */
	public DataReportModel getDataReportByLike(DataReportModel dataReport){
		DataReportModel retunDataReportModel = null;
		
		DataReportPO po = new DataReportPO();
		po.setRepotBindid(dataReport.getRepotBindid());
		po.setCreateDepBindid(dataReport.getCreateDepBindid());
		po.setPeriods(dataReport.getPeriods());
		po.setVerify(-1);
		List<DataReportPO> returnPoLists = dataReportDao.getDataReports(po);
		if(returnPoLists!=null && returnPoLists.size()==1){
			retunDataReportModel = new DataReportModel(returnPoLists.get(0));
		}
		return retunDataReportModel;
	}
	
	/**
	 * 上报人 本月应上报的报表的上报情况
	 * @param deptid
	 * @param deptidOfReport 发布报表的部门id （目前系统中未用到）
	 * @return
	 */
	public List<DataReportModel> appearReportOfMonthWithDep(String deptid,String deptidOfReport){
		List<DataReportModel> dataReporAppearOfDept = new ArrayList<DataReportModel>();
		String year = com.kszit.CDReport.util.DateUtil.currentYear();
		String month = com.kszit.CDReport.util.DateUtil.currentMonth();
		
		ReportConfigPO config = new ReportConfigPO();
		config.setRtyear(year);
		
		DataReportModel queryReport = new DataReportModel();
		queryReport.setCreateDepBindid(deptid);
		//本月应上报的报表，未考虑配置报表中 应上报单位的配置 情况,即 deptidOfReport 参数
		ReportConfigService reportConfigService = new ReportConfigService();
		List<ReportConfigPO> reportConfigPoListOfYear = reportConfigService.getReportConfigByLike(config);
		//循环应上报的报表，确定查看应上报的报表的上报情况
		for(ReportConfigPO configPo:reportConfigPoListOfYear){
			if(configPo.getRttype().equals(StaticVaribles.ReportFreq_MonthBindId+"")){//月报
				//组合查询条件
				queryReport.setPeriods(StaticVaribles.getDicBindidByContent(month+"月"));
				queryReport.setRepotBindid(configPo.getReportBindidLink());
				DataReportModel dataReport = getDataReportByLike(queryReport);
				if(dataReport==null){//未填报
					dataReport = new DataReportModel();
					dataReport.setVerify(StaticVaribles.DataReportFlowState_noWrite);
					dataReport.setRepotBindid(configPo.getReportBindidLink());
					dataReport.setPeriodsName(month+"月");
					dataReport.setCreateDepBindid(deptid);
				}
				dataReporAppearOfDept.add(dataReport);
			}
			
		}
		
		return dataReporAppearOfDept;
	}
	
	/**
	 * 本月 本部门制定的报表  下级上报情况
	 * 
	 * @param deptid 目前 此参数未用到，调用时设置为 0
	 * @return
	 */
	public List<DepartmentAndReport> appearSituationOfChileDept(String parentDeptid){
		List<DepartmentAndReport> returnValue = new ArrayList<DepartmentAndReport>();
		
		List<DepartmentToReport> deptList = new DepartmentUtil().getDepartmentService().getDepartmentListOfChild(parentDeptid);
		for (DepartmentToReport dept : deptList) {
			String depid = dept.getBindid();
			int appearCount = 0;
			int noAppearCount = 0;
			List<DataReportModel> dataReportList = appearReportOfMonthWithDep(depid,null);
			for(DataReportModel dataReport:dataReportList){
				if(dataReport.getVerify()==StaticVaribles.DataReportFlowState_publish){
					appearCount++;
				}else{
					noAppearCount++;
				}
			}
			
			DepartmentAndReport departMentAndReport = new DepartmentAndReport(depid,dept.getDepartmentName(),appearCount,noAppearCount);
			returnValue.add(departMentAndReport);
		}
		
		return returnValue;
	}
	
	/**
	 * 保存表格的单元格列表数据，调用此方法前 需要设定dataBindid的值
	 * @param dataMap
	 * @return
	 */
	public String saveTableCellsData(Map<String,Object> dataMap){
		cellService.setDataBindid(dataBindid);
		return cellService.saveTableCellsData(dataMap);
	}
	
	/**
	 * 由报表数据id获得此报表已经填报的数据
	 * @param dataid 报表数据id
	 * @return
	 */
	public List<DataCellPO> getReportDataList(int dataid){
		cellService.setDataBindid(dataid);
		return cellService.getDataCellList();
	}
	
	
	
	public int getDataBindid() {
		return dataBindid;
	}
	public void setDataBindid(int dataBindid) {
		this.dataBindid = dataBindid;
	}
}
