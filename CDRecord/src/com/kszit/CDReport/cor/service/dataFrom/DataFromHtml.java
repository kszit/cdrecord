package com.kszit.CDReport.cor.service.dataFrom;

import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.util.ClassInstallUtil;

public class DataFromHtml {

	public static String getHtml(String bindid,String reportLinkBindid){
		DictionaryService dictService = new DictionaryService();
		DictionaryPO dictPo = dictService.getOneByBindid(bindid);
		
		CalculateAbatract calculate = null;	
		Class[] classesParam = new Class[]{};
		Object[] objectsParam = new Object[]{};
		String classPathAndName = dictPo.getDescribe2();
		if(classPathAndName!=null && !"".equals(classPathAndName)){
			calculate = (CalculateAbatract)ClassInstallUtil.installClass(classPathAndName, null,null);
		}
		String result = "";
		if(calculate!=null){
			result = calculate.getDataFromHtml(reportLinkBindid);
		}
		return result==null?"":result;
	}
	
	public static String getCellHtml(String bindid,String reportLinkBindid){
		DictionaryService dictService = new DictionaryService();
		DictionaryPO dictPo = dictService.getOneByBindid(bindid);
		
		CalculateAbatract calculate = null;	
		Class[] classesParam = new Class[]{};
		Object[] objectsParam = new Object[]{};
		String classPathAndName = dictPo.getDescribe2();
		if(classPathAndName!=null && !"".equals(classPathAndName)){
			calculate = (CalculateAbatract)ClassInstallUtil.installClass(classPathAndName, null,null);
		}
		String result = "";
		if(calculate!=null){
			result = calculate.getCellDataFromHtml(reportLinkBindid);
		}
		return result==null?"":result;
	}
	
	
	public static String notConfig(){
		String noConfig = "<center><br><br><br><br><h4>无配置</h4>";
		return noConfig;
	}
	
}
