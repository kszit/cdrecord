package com.kszit.CDReport.cor.service.dataFrom;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.DBQueryUtilDao;
import com.kszit.CDReport.cor.dao.hibernate.DBQueryUtilDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DataReportPO;
import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.ReportDataService;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;
/**
 * 所有列子项和
 * @author Administrator
 *
 */
public class CalculateDataFromBusinessTable extends CalculateAbatract{

	public CalculateDataFromBusinessTable(){
		super();
	}
	public CalculateDataFromBusinessTable(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("static-access")
	@Override
	public String calculate() {
		String[] businessTables = datafromPo.getFormula().split("\\|");
		String tableName = businessTables[0];
		String tableCol = businessTables[1];
		String tableId = businessTables[2];
		
		long hbindid = datafromPo.getHbindid();
		Iterator<String> keyIter = dataMap.keySet().iterator();
		while(keyIter.hasNext()){
			String key = keyIter.next();
			if(key.startsWith(hbindid+"#")){
				String vbindid = StringUtil.getVBindid(key);
				String sql = "select "+tableCol +" from "+ tableName +" where "+tableId +"=" + vbindid; 
				DBQueryUtilDao dbQueryUtil = new DBQueryUtilDaoHiberImpl();
				List<Object[]> resule = dbQueryUtil.exceleSql(sql);
				String value = "";
				if(resule!=null && resule.size()==0){
					value = "";
				}else{
					value = resule.get(0)+"";
				}
				dataMap.put(key, value);
			}
		}
		return CalculateAbatract.NOT_PUT_DATA;
	}

	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("CalculateDataFromBusinessTable.js****");
		
		
		sb.append("<table width=100%>			" +
				"<tr><td>配置项：</td><td><input id='headerRowModel_dataRule_temp' type='text' value='' name='headerRowModel.dataRule.temp'/></td>			</tr>			" +
				"<tr><td colspan=2>格式：表名称|要取数据的字段名称|数据行的id</td></tr></table>");
		
		return sb.toString();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
StringBuffer sb = new StringBuffer();
		
		sb.append("CalculateDataFromBusinessTableOfCell.js****");
		
		
		sb.append("<table width=100%>			" +
				"<tr><td>配置项：</td><td><input id='headerRowModel_dataRule_temp' type='text' value='' name='headerRowModel.dataRule.temp'/></td>			</tr>			" +
				"<tr><td colspan=2>格式：表名称|要取数据的字段名称|数据行的id</td></tr></table>");
		
		return sb.toString();
	}
}
