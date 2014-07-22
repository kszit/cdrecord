package com.kszit.CDReport.cor.service.dataFrom;

import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.openserv.department.model.DepartmentToReport;
import com.kszit.CDReport.util.DepartmentUtil;
import com.kszit.CDReport.util.StaticVaribles;
import com.kszit.CDReport.util.StringUtil;
/**
 * 所有列子项和
 * @author Administrator
 *
 */
public class CalculateColumAllAdd extends CalculateAbatract{

	public CalculateColumAllAdd(){
		super();
	}
	public CalculateColumAllAdd(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 功能：计算列项和。
	 * 设计思路：从行表头中查找叶子节点，结合hbindid从dataMap中查找数据，并计算累计到合计中。
	* (non-Javadoc)  
	* @see com.kszit.CDReport.cor.service.dataFrom.CalculateAbatract#calculate()
	 */
	@SuppressWarnings("static-access")
	@Override
	public String calculate() {
		long addResult = 0;
		long hbindid = datafromPo.getHbindid();
		List<TableColumnCell> columCells = tableColum.getAllHeaderCellList();
		for(TableColumnCell cell:columCells){
			if(cell.getChilds()==null){//所有末端节点的和，这样踢出了合计项
				if(cell.getVerticalColumnConfigPO().getUIModule()==StaticVaribles.HTMLUIModule_Org){
					List<DepartmentToReport> deptList = new DepartmentUtil().getDepartmentService().getDepartmentList("0");
					for (DepartmentToReport dept : deptList) {
						String value = (String)dataMap.get(hbindid+"#"+dept.getBindid());
						addResult += StringUtil.stringToLong(value);
					}
				}else{
					long vbindid = cell.getVerticalColumnConfigPO().getBindId();
					if(datafromPo.getVbindid()==vbindid){
						continue;
					}
					if(!dataMap.containsKey(hbindid+"#"+vbindid)){
						return super.LAST_CALCULATE;
					}else{
						String value = (String)dataMap.get(hbindid+"#"+vbindid);
						addResult += StringUtil.stringToLong(value);
					}
				}

			}
		}
		return addResult+"";
	}
	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		// TODO Auto-generated method stub
		return "CalculateColumAllAdd.js****"+DataFromHtml.notConfig();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
		// TODO Auto-generated method stub
				return "CalculateColumAllAddOfCell.js****"+DataFromHtml.notConfig();
	}
}
