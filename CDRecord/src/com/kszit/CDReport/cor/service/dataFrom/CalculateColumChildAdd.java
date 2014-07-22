package com.kszit.CDReport.cor.service.dataFrom;

import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;
import com.kszit.CDReport.cor.model.DataReportModel;
import com.kszit.CDReport.cor.service.reportData.TableColumn;
import com.kszit.CDReport.cor.service.reportData.TableColumnCell;
import com.kszit.CDReport.cor.service.reportData.TableHeader;
import com.kszit.CDReport.util.StringUtil;
/**
 * 列孩子 子项和
 * @author Administrator
 *
 */
public class CalculateColumChildAdd extends CalculateAbatract{
	public CalculateColumChildAdd(){
		super();
	}
	public CalculateColumChildAdd(HVConfigDataFromPO datafromPo,
			Map<String, Object> dataMap, TableHeader tableHeader,
			TableColumn tableColum, DataReportModel dataReportModel,DataFrom dataFrom) {
		super(datafromPo, dataMap, tableHeader, tableColum, dataReportModel,dataFrom);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 功能：计算孩子节点的合计值<br>
	 * 设计思路：首先获得hbindid，查找包含父子关系的行表头，循环行表头的子节点结合hbindid从dataMap中查找子项的数据。
	* (non-Javadoc)  dataMap
	* @see com.kszit.CDReport.cor.service.dataFrom.CalculateAbatract#calculate()
	 */
	@SuppressWarnings("static-access")
	@Override
	public String calculate() {
		long addResult = 0;
		long hbindid = datafromPo.getHbindid();
		TableColumnCell cCell = getCurrentTableColumnCell();//行表头
		if(cCell!=null && cCell.getChilds()!=null){
			for(TableColumnCell childCell:cCell.getChilds()){
				long vbindid = childCell.getVerticalColumnConfigPO().getBindId();
				if(dataFrom.queueContainKey(hbindid,vbindid)){
					return super.LAST_CALCULATE;
				}else{
					String value = (String)dataMap.get(hbindid+"#"+vbindid);
					addResult += StringUtil.stringToLong(value);
				}
			}
		}
		
		return addResult+"";
	}

	@Override
	public String getDataFromHtml(String reportLinkBindid) {
		return "CalculateColumAllAdd.js****"+DataFromHtml.notConfig();
	}
	@Override
	public String getCellDataFromHtml(String reportLinkid) {
		return "CalculateColumAllAddOfCell.js****"+DataFromHtml.notConfig();
	}



	
}
