package com.kszit.CDReport.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.service.DictionaryService;
/**
 * 数据字典，存有数据字典的缓存数据，及数据字典的bind值
 * @author Administrator
 */
public class StaticVaribles {

	private static Map<String,String> allDic = null;
	static{
		if(allDic==null){
			allDic = new HashMap<String,String>();
			DictionaryService service = new DictionaryService();
			List<DictionaryPO> all = service.getAllDictionary();
			for(DictionaryPO dicPo:all){
				allDic.put(dicPo.getBindId()+"", dicPo.getName());
			}
		}
	}
	/**
	 * 根据数据字典内容的bindid获得内容
	 * @param dictbindid
	 * @return
	 */
	public static String getDicContent(String dictbindid){

		return allDic.get(dictbindid);
		
	}
	
	public static String getDicBindidByContent(String dicContent){
		String returnValue = "";
		Iterator<String> iter = allDic.keySet().iterator();
		while(iter.hasNext()){
			String key = iter.next();
			String content = allDic.get(key);
			if(content!=null && content.equals(dicContent)){
				returnValue = key;
				break;
			}
		}
		return returnValue;
	}
	
	
	
	/**
	 * 转换期数的bindid为期数数值的类别bindid
	 * @param eriodBindid
	 * @return
	 */
	public static int changePeriodToPeriodDataBindid(int eriodBindid){
		int periodDataBindid = 0;
		switch(eriodBindid){
		case StaticVaribles.ReportFreq_YearBindId://年报
			periodDataBindid = ConfigYearBindId;
			break;
		case StaticVaribles.ReportFreq_PeriodBindId://季报
			periodDataBindid = ConfigReportFreqBindIdOfQuarter;
			break;
		case StaticVaribles.ReportFreq_HalfYearBindId://半年报
			periodDataBindid = ConfigReportFreqBindIdOfHalfYear;
			break;
		case StaticVaribles.ReportFreq_MonthBindId://月报
			periodDataBindid = ConfigReportFreqBindIdOfMonth;
			break;
		case StaticVaribles.ReportFreq_HalfMonthBindId://半月报
			periodDataBindid = ConfigReportFreqBindIdOfHalfMonth;
			break;
		}
		
		return periodDataBindid;
	}
	
	
	public static final String dataTableNameFore = "DataTable_";
	//数据类型id
	public static final int ConfigHVdataTypeBindId = 100;
	//数据来源id
	public static final int ConfigHVdataFromBindId = 110;
	//数据UIid
	public static final int ConfigHVUIModeleBindId = 140;
	//数据期数
	public static final int ConfigDataPeriodsBindId = 160;
	//是否设定
	public static final int ConfigIsSettingBindId = 180;
	//年份
	public static final int ConfigYearBindId = 190;
	//频次
	public static final int ConfigReportFreqBindId = 200;
	/**
	 * 期数
	 */
	public static final int ConfigReportPeriodBindId = 220;
	//报表状态
	public static final int ConfigReportStateBindId = 240;
	//频次--半年报列表
	public static final int ConfigReportFreqBindIdOfHalfYear = 250;
	//频次--半月报列表
	public static final int ConfigReportFreqBindIdOfHalfMonth = 260;
	//频次--季报列表
	public static final int ConfigReportFreqBindIdOfQuarter = 290;
	/**
	 * 频次--月报列表
	 */
	public static final int ConfigReportFreqBindIdOfMonth = 300;
	
	//报表流程状态
	public static final int DataReportFlowState = 320;
	//报表类型   填报表、汇总表
	public static final int DataReportType = 340;
	
	//图表类型
	public static final int GraphType = 350;
	
	//====================================================================
	
	
	
	//日期
	public static final int DataType_DateBindId = 101;
	//数值
	public static final int DataType_NumberBindId = 102;
	//文本
	public static final int DataType_TextBindId = 103;
        /**
         * 日期时间
         */
        public static final int DataType_DateTimeBindId = 104;
        /**
         * 时间
         */
        public static final int DataType_TimeBindId = 105;
	
	//表间计算
	public static final int DataFrom_TableOutCountBindId = 111;
	/**
	 * 手工录入
	 */
	public static final int DataFrom_HandInputBindId = 112;
	//表内计算-横向
	public static final int DataFrom_TableInFormulaHCountBindId = 114;
	//表内计算-纵向
	public static final int DataFrom_TableInFormulaVCountBindId = 117;
	//表内计算--横纵向
	public static final int DataFrom_TableInFormulaHVCountBindId = 118;
	//列子项和
	public static final int DataFrom_ColumChildAddBindId = 115;
	//列求和
	public static final int DataFrom_ColumAllAddBindId = 116;
	/**
	 * 下级表的单元格中取数据
	 */
	public static final int DataFrom_LowerLevelCell = 119;
	/**
	 * 去年同期
	 */
	public static final int DataFrom_SamePeriodLastYear = 120;
	
	/**
	 * 兄弟表数据
	 */
	public static final int DataFrom_BrothersReportData = 121;
	/**
	 * 上一期数据
	 */
	public static final int DataFrom_PriorPeriodReportData = 122;
	/**
	 * 业务表
	 */
	public static final int DataFrom_ServiceTable = 123;
	/**
	 * 无
	 */
	public static final int DataFrom_No = 124;
	 
	//文本框
	public static final int HTMLUIModule_InputBindid = 141;
	//下拉列表
	public static final int HTMLUIModule_SelectBindid = 142;
	//数据字典
	public static final int HTMLUIModule_DictionaryBindid = 143;
	//空
	public static final int HTMLUIModule_NullBindid = 144;
        /**
         * 弹出框
         */
	public static final int HTMLUIModule_PopWin = 145;
    /**
     * 组织机构
     */
	public static final int HTMLUIModule_Org = 146;	
    /**
     * 隐藏域
     */
	public static final int HTMLUIModule_Hiden = 147;	
    /**
     * 地区列表
     */
	public static final int HTMLUIModule_AreaList = 148;	
	/**
     *  图片
     */
	public static final int HTMLUIModule_Picture = 149;	
        
	//本期
	public static final int ConfigDataPeriods_CrrentBindId = 161;
	//上一期
	public static final int ConfigDataPeriods_PeriodBindId = 162;
	//去年同期
	public static final int ConfigDataPeriods_YearOnYBindId = 164;
	//年初
	public static final int ConfigDataPeriods_BeginYBindId = 165;
	//年末
	public static final int ConfigDataPeriods_EndYBindId = 163;
	
	//设定
	public static final int IsSetting_SettingBindId = 181;
	//未设定
	public static final int IsSetting_NotSettingBindId = 182;
	
	/**
	 * 年报
	 */
	public static final int ReportFreq_YearBindId = 201;
	/**
	 * 季报
	 */
	public static final int ReportFreq_PeriodBindId = 202;
	/**
	 * 半年报
	 */
	public static final int ReportFreq_HalfYearBindId = 204;
	/**
	 * 月报
	 */
	public static final int ReportFreq_MonthBindId = 203;
	/**
	 * 半月报
	 */
	public static final int ReportFreq_HalfMonthBindId = 205;
	/**
	 * 周报
	 */
	public static final int ReportFreq_WeekBindId = 206;
	/**
	 * 天报
	 */
	public static final int ReportFreq_DayBindId = 207;
	
	//配置
	public static final int ReportState_ConfigBindId = 241;
	//启用
	public static final int ReportState_UsingBindId = 242;
	//废弃
	public static final int ReportState_DiscardBindId = 243;
	
	//报表流程状态-编制中
	public static final int DataReportFlowState_weave = 321;
	//报表流程状态-审核中
	public static final int DataReportFlowState_verify = 322;
	/**
	 * 报表流程状态-已发表
	 */
	public static final int DataReportFlowState_publish = 323;
	//报表流程状态-回退
	public static final int DataReportFlowState_back = 324;
	//报表流程状态-编制中和审核中   在部门管理员的查询中使用
	public static final int DataReportFlowState_weaveAndverify = 325;
	//报表流程状态-审核中和已经上报   在查看报表是否已经上报时使用
	public static final int DataReportFlowState_verifyAndpublish = 326;
	/**
	 * 报表流程状态-未填报
	 */
	public static final int DataReportFlowState_noWrite = 327;
	
	//报表类型   填报表
	public static final int DataReportType_Input = 341;
	//报表类型  汇总表
	public static final int DataReportType_Collect= 342;
	
	//柱形图
	public static final int GraphType_Bar = 351;
	//曲线图
	public static final int GraphType_Curve = 352;
	//饼图
	public static final int GraphType_Pie = 353;
		
	public static final String HTMLInputCSS = "cdrecordInput";
	public static final String HTMLInputCSSStr = "cdrecordInputStr";
	public static final String HTMLInputCSSNum = "cdrecordInputNum";
	public static final String HTMLSelectCSS = "cdrecordInput";
	//public static final String HTMLInputCSS = "";
	//public static final String HTMLInputCSS = "";
	//提前上报
	public static final int AHEAD = -1;
	//推迟上报
	public static final int DELAY = 1;
	
}
