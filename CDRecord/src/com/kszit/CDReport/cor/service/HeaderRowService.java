package com.kszit.CDReport.cor.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kszit.CDReport.cor.dao.HVConfigDataFromDao;
import com.kszit.CDReport.cor.dao.HeaderRowConfigDao;
import com.kszit.CDReport.cor.dao.hibernate.HVConfigDataFromDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.HeaderRowConfigDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.HeaderRowConfigPO;
import com.kszit.CDReport.cor.dao.hibernate.po.ReportConfigPO;
import com.kszit.CDReport.cor.model.HeaderRowConfigModel;
import com.kszit.CDReport.cor.service.reportData.SelectHPartTable;
import com.kszit.CDReport.cor.service.reportData.SelectHVPartTable;
import com.kszit.CDReport.cor.service.reportData.TableHeaderCell;
import com.kszit.CDReport.util.StaticVaribles;

public class HeaderRowService {
	static Logger logger = LoggerFactory.getLogger(HeaderRowService.class);
	HeaderRowConfigDao hrcDao = null;
	
	
	public HeaderRowService(){
		hrcDao = new HeaderRowConfigDaoHiberImpl();
	}
	/**
	 * json数据格式保存
	 * @param jsondata
	 * @return
	 */
	public String saveOrUpdate(String jsondata){
		HeaderRowConfigModel model = new HeaderRowConfigModel(jsondata);
		return this.saveOrUpdate(model);
	}
	/**
	 * model对象形式保存
	 * @param model
	 * @return
	 */
	public String saveOrUpdate(HeaderRowConfigModel model){
		HeaderRowConfigPO po = new HeaderRowConfigPO(model);
		po.initBindId();
		return hrcDao.saveOrUpdate(po);
	}
	/**
	 * model对象形式保存
	 * @param model
	 * @return
	 */
	public String saveOrUpdate(HeaderRowConfigPO po){
		po.initBindId();
		return hrcDao.saveOrUpdate(po);
	}
	
	public String save(HeaderRowConfigPO po,String newReportBindid,Long parentid){

		po.setOrderIndexStr(this.nextHeaderRowIndexOrder(newReportBindid, parentid+""));
		this.saveOrUpdate(po);
		return po.getBindId()+"";
	}
	
	/**
	 * 复制横向配置信息
	 * @param oldConfigPo
	 * @param newConfigPo
	 * @return
	 */
	public String copyHeaderRow(ReportConfigPO oldConfigPo,ReportConfigPO newConfigPo){
		List<HeaderRowConfigPO> oldRowList = hrcDao.getList(oldConfigPo.getReportBindidLink());
		for(HeaderRowConfigPO oldrowpo:oldRowList){
			HeaderRowConfigPO newRowPo = new HeaderRowConfigPO(oldrowpo);
			newRowPo.setReportBindid(newConfigPo.getReportBindidLink());
			if(newRowPo.getDataRuleItems()!=null){
				newRowPo.setDataRuleItems(newRowPo.getDataRuleItems().replaceAll(oldConfigPo.getReportBindidLink(), newConfigPo.getReportBindidLink()));
			}
			saveOrUpdate(newRowPo);
		}
		new HVConfigDataFromService().copyDataFrom(oldConfigPo, newConfigPo);
		new HVConfigUIModelService().copyUIModel(oldConfigPo, newConfigPo);
		return "";
		
	}
	/**
	 * 此单元格通过计算公式被其他列引用
	 * @param bindid
	 * @return
	 */
	public String dataFromBackRefBindids(long bindid){
		List<HeaderRowConfigPO> backRefList = dataFromBackRefList(bindid);
		StringBuffer sb = new StringBuffer();
		for(HeaderRowConfigPO po:backRefList){
			sb.append("$");
			sb.append(po.getBindId());
		}
		return sb.toString();
	}
	
	/**
	 * 此单元格通过计算公式被其他列引用
	 * @param bindid
	 * @return
	 */
	public List<HeaderRowConfigPO> dataFromBackRefList(long bindid){
		List<HeaderRowConfigPO> backRefList = hrcDao.getDataFromBackRefList(bindid);
		return backRefList;
	}
	
	/**
	 * 更新uimodel
	 * @param model
	 * @return
	 */
	public String updateUIModel(HeaderRowConfigModel model){
		HeaderRowConfigModel modelNew = getHeaderRowConfigModelByBindId(model.getReportBindid(),model.getBindId());
		modelNew.setUIModule(model.getUIModule());
		modelNew.setUIModuleDS(model.getUIModuleDS());
		return saveOrUpdate(modelNew);
	}
	
	/**
	 * 是否修改了特定ui Module属性 
	 * @param model
	 * @return
	 */
	public String updateUIModelSetSpel(HeaderRowConfigModel model){
		HeaderRowConfigModel modelNew = getHeaderRowConfigModelByBindId(model.getReportBindid(),model.getBindId());
		modelNew.setUiModelChildItemSet(model.getUiModelChildItemSet());
		return saveOrUpdate(modelNew);
	}

	/**
	 * 更新数据源属性
	 * @param model
	 * @return
	 */
	public String updateDataFrom(HeaderRowConfigModel model){
		HeaderRowConfigModel modelNew = getHeaderRowConfigModelByBindId(model.getReportBindid(),model.getBindId());
		modelNew.setDataFrom(model.getDataFrom());
		modelNew.setDataRule(model.getDataRule());
		modelNew.setDataRuleItems(model.getDataRuleItems());
		return saveOrUpdate(modelNew);
	}
	/**
	 * 是否修改了特定数据源属性 
	 * @param model
	 * @return
	 */
	public String updateDataFromSetSpel(HeaderRowConfigModel model){
		HeaderRowConfigModel modelNew = getHeaderRowConfigModelByBindId(model.getReportBindid(),model.getBindId());
		modelNew.setDataFromChildItemSet(model.getDataFromChildItemSet());
		return saveOrUpdate(modelNew);
	}
	
	public String saveOrUpdate(List<HeaderRowConfigModel> models){
		
		return "";
	}
	/**
	 * 更改是否为叶子节点，
	 * @param id
	 */
	public void changeIsLeaf(String reportBindid,long bindid){
		HeaderRowConfigPO po = hrcDao.getOneByBindid(reportBindid,bindid);
		if(po.is_is_leaf()){
			po.set_is_leaf(false);
                        po.initParntParam();
		}else{
			po.set_is_leaf(true);
                        po.initParam();
		}
		hrcDao.update(po);
	}
	
	public boolean delete(String id){
		return hrcDao.delete(Long.parseLong(id));
	}
	/**
	 * 删除
	 * @param ids  3,5,6
	 * @return
	 */
	public boolean deletes(String reportbindiid,String ids){
		List<HeaderRowConfigPO> deleteObj = new ArrayList<HeaderRowConfigPO>();
		String[] array_ids = ids.split("\\,");
		for(int i=0,ids_length=array_ids.length;i<ids_length;i++){
			long id = Long.parseLong(array_ids[i]);
			HeaderRowConfigPO po = hrcDao.getOneByBindid(reportbindiid,id);
			po.getId();
			deleteObj.add(po);
		}
		boolean result = hrcDao.deleteByBindids(reportbindiid,ids);
		HVConfigDataFromDao hvDataFromDao = new HVConfigDataFromDaoHiberImpl();
		for(HeaderRowConfigPO p:deleteObj){//父项无孩子时 设置父项为叶子节点；删除对应的数据关系项
			hvDataFromDao.deletesByReportIdAndHid(p.getReportBindid(),p.getBindId());//删除对应的数据关系项
			int count = hrcDao.childCount(p.get_parent());
			if(count==0){
				changeIsLeaf(reportbindiid,p.get_parent());
			}
		}
		return result;
	}
	
	/**
	 * 删除
	 * @param reportBindid  
	 * @return
	 */
	public void deletes(String reportBindid){
		hrcDao.deleteByBindids(reportBindid);
	}
	
	
	public List<HeaderRowConfigModel> getAllHeaderRowC(){
		List<HeaderRowConfigModel> headerRows = new ArrayList<HeaderRowConfigModel>();
		List<HeaderRowConfigPO> pos = hrcDao.getList();
		for(HeaderRowConfigPO po:pos){
			HeaderRowConfigModel model = new HeaderRowConfigModel(po);
			headerRows.add(model);
		}
		return headerRows;
	}
	/**
	 * 由主表bindid 获得list列表，列表内容为HeaderRowConfigPO
	 * @param reportid
	 * @return
	 */
	public List<HeaderRowConfigPO> getHeaderRowCByReportOfPO(String reportBindid){
		return hrcDao.getList(reportBindid);
	}
	
	/**
	 * 获得配置报表的子项
	 * @param reportid
	 * @return
	 */
	public List<HeaderRowConfigPO> getHeaderRowByParentId(String reportBindid,String parentid){
		return hrcDao.getListChilds(reportBindid,Integer.parseInt(parentid));
	}
	/**
	 * 由主表bindid 获得list列表，列表内容为HeaderRowConfigModel
	 * @param reportid
	 * @return
	 */
	public List<HeaderRowConfigModel> getHeaderRowCByReport(String reportBindid){
		List<HeaderRowConfigModel> headerRows = new ArrayList<HeaderRowConfigModel>();
		List<HeaderRowConfigPO> pos = hrcDao.getList(reportBindid);
		for(HeaderRowConfigPO po:pos){
			HeaderRowConfigModel model = new HeaderRowConfigModel(po);
			headerRows.add(model);
		}
		return headerRows;
	}
	/**
	 * 新纪录的id
	 * @return
	 */
	public String getNewHeaderRowId(){
		HeaderRowConfigPO po = new HeaderRowConfigPO();
		return this.getNewHeaderRowId(po);
	}
	/**
	 * 新纪录的id
	 * @return
	 */
	public String getNewHeaderRowId(HeaderRowConfigPO po){
		po.initBindId();
		return hrcDao.insert(po);
	}
	
	/**
	 * 通过bindid获得对象
	 * @param bindid
	 * @return
	 */
	public HeaderRowConfigModel getHeaderRowConfigModelByBindId(String reportBindid,long bindid){
		HeaderRowConfigPO po = hrcDao.getOneByBindid(reportBindid,bindid);
		HeaderRowConfigModel model = new HeaderRowConfigModel(po);
		return model;
	}
	
	/**
	 * 通过bindid获得对象
	 * @param bindid
	 * @return
	 */
	public HeaderRowConfigPO getHeaderRowConfigPoByBindId(String reportBindid,long bindid){
		HeaderRowConfigPO po = hrcDao.getOneByBindid(reportBindid,bindid);
		return po;
	}
	
	/**
	 * 只生成了排序号，无其他操作
	 * 下一个节点的排序号
	 * @param reportid  报表id
	 * @param parentid  父项bindid
	 * @return
	 */
	public String nextHeaderRowIndexOrder(String reportBindid,String parentid){
		String nextIndexOrder = "";
		List<HeaderRowConfigPO> pos = hrcDao.getListChilds(reportBindid,Integer.parseInt(parentid));
		if(!pos.isEmpty()){//已经有子节点
			String indexOrder = pos.get(pos.size()-1).getOrderIndexStr();
			char currentChar = indexOrder.charAt(indexOrder.length()-1);
			currentChar++;
			StringBuilder strb = new StringBuilder(indexOrder);
			strb.replace(indexOrder.length()-1, indexOrder.length(), currentChar+"");
			nextIndexOrder = strb.toString();
		}else{
			if("0".equals(parentid)){
				nextIndexOrder = "a";
			}else{
				HeaderRowConfigPO parent = hrcDao.getOneByBindid(reportBindid,Long.parseLong(parentid));
				if(parent!=null){
					changeIsLeaf(reportBindid,Long.parseLong(parentid));
					nextIndexOrder = parent.getOrderIndexStr()+"#a";
				}else{
					nextIndexOrder = "a";
				}
			}
		}
		return nextIndexOrder;
	}
	
	/**
	 * 上级部门的汇总表的配置项从下级上报表中选取部门指标生成，
	 * @param newReportBindid 新汇总表的bindid
	 * @param oldReportBindid 下级报表的bindid
	 * @param selectedCells 选取的单元格 格式：hbindid#vbindid|hbindid#vbindid|hbindid#vbindid| 
	 * @param ParentId  父节点id    在新报表的哪个节点下 挂在选择的下级指标。
	 */
	public void copyHVPartFromOthor(String newReportBindid,String oldReportBindid,String selectedCells,String parentId){
		Long headerParent = new Long(parentId);//挂接的单元格bindid
		String selectedCellKeys = selectedCells;//待添加单元格
		SelectHVPartTable selectHVPartTable = new SelectHVPartTable(oldReportBindid);
		//将选择的节点 的标志位设置为以选中
		List<TableHeaderCell> allHeadCells = selectHVPartTable.tableHeader.allHeaderCellList;
		for(TableHeaderCell hCell : allHeadCells){
			HeaderRowConfigPO po = hCell.getHeaderRowConfigPO();
			String key = po.getBindId()+"#"+po.get_parent();
			if(selectedCellKeys.contains(key)){
				hCell.setSelected(true);
				selectHVPartTable.setCellParentSelected(hCell);
			}
		}
		//向队列中放入初始化数据
		Queue<TableHeaderCell> saveHeaderQueue = new LinkedList<TableHeaderCell>(); 
		List<TableHeaderCell> treeHeadCells = selectHVPartTable.tableHeader.allHeaderCellTree;
		for(TableHeaderCell hCell : treeHeadCells){
			if(hCell.isSelected()){
				hCell.getHeaderRowConfigPO().setId(headerParent);//借用bindid作为 新数据的父bindid
				saveHeaderQueue.add(hCell);
			}
		}
		//向数据库中插入数据，并查找叶子节点中的待插入数据放入队列中
		while(!saveHeaderQueue.isEmpty()){
			TableHeaderCell oneCell = saveHeaderQueue.poll();
			HeaderRowConfigPO oneCellPo = oneCell.getHeaderRowConfigPO();
			List<TableHeaderCell> childCells = oneCell.getChilds();
			// =======设置新配置单元格的默认属性
			HeaderRowConfigPO insertPo = new HeaderRowConfigPO();
			insertPo.setReportBindid(newReportBindid);
			insertPo.setContent(oneCellPo.getContent());
			insertPo.set_parent(oneCellPo.getId().intValue());
			insertPo.set_is_leaf(true);
			insertPo.setDataFrom(StaticVaribles.DataFrom_LowerLevelCell);
			insertPo.setDataType(oneCellPo.getDataType());
			insertPo.setDataLength(oneCellPo.getDataLength());
			insertPo.setWidth(oneCellPo.getWidth());
			insertPo.setHeight(oneCellPo.getHeight());
			if(oneCell.getParent()!=null){
				insertPo.setDataRule(oldReportBindid+"|"+oneCellPo.getBindId()+"#"+oneCell.getParent().getHeaderRowConfigPO().bindId);
			}
			
			String newBindid = save(insertPo,newReportBindid,oneCellPo.getId());//保存数据
			
			//孩子节点存放到保存队列中
			if(childCells!=null && !childCells.isEmpty()){
				for(TableHeaderCell childCell:childCells){
					if(childCell.isSelected()){
						childCell.getHeaderRowConfigPO().setId(new Long(newBindid));//借用bindid作为 新数据的父bindid
						saveHeaderQueue.add(childCell);
					}
				}
			}
		}
	}
	
	/**
	 * 从其他报表中选择部分配置，添加到新的报表中。
	 * @param newReportBindid
	 * @param oldReportBindid
	 * @param selectedCells
	 * @param parentId
	 */
	public void copyHPartFromOthor(String newReportBindid,String oldReportBindid,String selectedCells,String parentId){
		Long headerParent = new Long(parentId);//挂接的单元格bindid
		String selectedCellKeys = selectedCells;//待添加单元格
		SelectHPartTable selectHPartTable = new SelectHPartTable(oldReportBindid,1);
		//将选择的节点 的标志位设置为以选中
		List<TableHeaderCell> allHeadCells = selectHPartTable.tableHeader.allHeaderCellList;
		for(TableHeaderCell hCell : allHeadCells){
			HeaderRowConfigPO po = hCell.getHeaderRowConfigPO();
			String key = "$"+po.getBindId();
			if(selectedCellKeys.contains(key)){
				hCell.setSelected(true);
				selectHPartTable.setCellParentSelected(hCell);
			}
		}
		//向队列中放入初始化数据
		Queue<Object[]> saveHeaderQueue = new LinkedList<Object[]>(); 
		List<TableHeaderCell> treeHeadCells = selectHPartTable.tableHeader.allHeaderCellTree;
		for(TableHeaderCell hCell : treeHeadCells){
			if(hCell.isSelected()){
				//hCell.getHeaderRowConfigPO().setId(headerParent);//借用bindid作为 新数据的父bindid
				Object[] parentIdAndrowConfig = {headerParent,hCell};
				saveHeaderQueue.add(parentIdAndrowConfig);
			}
		}
		//向数据库中插入数据，并查找叶子节点中的待插入数据放入队列中
		while(!saveHeaderQueue.isEmpty()){
			Object[] parentIdAndrowConfigs = saveHeaderQueue.poll();
			
			Long parentBindid = (Long)parentIdAndrowConfigs[0];
			TableHeaderCell oneCell = (TableHeaderCell)parentIdAndrowConfigs[1];
			
			HeaderRowConfigPO oneCellPo = oneCell.getHeaderRowConfigPO();
			
			// =======设置新配置单元格的默认属性
			HeaderRowConfigPO insertPo = new HeaderRowConfigPO();
			insertPo.setReportBindid(newReportBindid);
			insertPo.setContent(oneCellPo.getContent());
			insertPo.set_parent(parentBindid.intValue());
			insertPo.set_is_leaf(true);
			
			//insertPo.setDataFrom(StaticVaribles.DataFrom_LowerLevelCell);
			insertPo.setDataType(oneCellPo.getDataType());
			insertPo.setDataLength(oneCellPo.getDataLength());
			insertPo.setWidth(oneCellPo.getWidth());
			insertPo.setHeight(oneCellPo.getHeight());
			insertPo.setDataFrom(StaticVaribles.DataFrom_HandInputBindId);
			insertPo.setUIModule(StaticVaribles.HTMLUIModule_InputBindid);
			insertPo.setUiModelChildItemSet(StaticVaribles.IsSetting_NotSettingBindId);
			insertPo.setDataFromChildItemSet(StaticVaribles.IsSetting_NotSettingBindId);
			if(oneCell.getParent()!=null){
				//insertPo.setDataRule(oldReportBindid+"|"+oneCellPo.getBindId()+"#0#0");
			}
			
			String newBindid = save(insertPo,newReportBindid,new Long(insertPo.get_parent()));//保存数据
			
			//孩子节点存放到保存队列中
			List<TableHeaderCell> childCells = oneCell.getChilds();
			if(childCells!=null && !childCells.isEmpty()){
				for(TableHeaderCell childCell:childCells){
					if(childCell.isSelected()){
						//childCell.getHeaderRowConfigPO().setId(new Long(newBindid));//借用bindid作为 新数据的父bindid
						Object[] parentIdAndrowConfig = {new Long(newBindid),childCell};
						saveHeaderQueue.add(parentIdAndrowConfig);
					}
				}
			}
		}
	}
}
