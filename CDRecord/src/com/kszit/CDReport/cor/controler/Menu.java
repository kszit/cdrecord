package com.kszit.CDReport.cor.controler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.dao.hibernate.po.MenuPO;
import com.kszit.CDReport.cor.model.MenuModel;
import com.kszit.CDReport.cor.service.DictionaryService;
import com.kszit.CDReport.cor.service.MenuService;
import com.kszit.CDReport.cor.service.persionDepRole.RoleService;
import com.kszit.CDReport.openserv.persion.model.PersionToReport;
import com.opensymphony.xwork2.Action;

/**
 * 菜单
 * <code>Set welcome message.</code>
 */
public class Menu extends ReportCDSupport {

	private static final long serialVersionUID = 2218808309202173713L;

	private String data;
	
	private String parentid;
	
	private String deleteids;
	
	List<MenuModel> treeNodes = new ArrayList<MenuModel>();

	private Map<String,Object> map = new HashMap<String,Object>();

	/**
	 * 所有菜单列表  格式 :bindid=菜单名称|bindid=菜单名称|bindid=菜单名称
	 * @return
	 */
	public String getAllMenu(){
		MenuService service = new MenuService();
		List<MenuPO> poList = service.getAllMenuList();
		Iterator<MenuPO> iter = poList.iterator();
		StringBuffer sb = new StringBuffer();
		while(iter.hasNext()){
			MenuPO po = iter.next();
			sb.append(po.getBindId()+"="+po.getText());
			sb.append("|");
		}
		sb.append("1=1");
		super.outText(sb.toString());
		return null;
	}
	/**
	 * 菜单的json数据，主页左侧菜单的数据源
	 * @return
	 */
	public String listTreeNodeByJson(){
		PersionToReport  persion = super.getUser();
		RoleService roleService = new RoleService();
		String roleMenus = roleService.getRoleByBindId(persion.getRoleId()).getMenus();
		
		MenuService service = new MenuService();
		List<MenuPO> poList = service.getChilds(Integer.parseInt(parentid));
		Iterator<MenuPO> iter = poList.iterator();
		while(iter.hasNext()){
			MenuPO po = iter.next();
			if("admin".equals(persion.getUserAccount())){
				MenuModel model = new MenuModel(po);
				treeNodes.add(model);
			}else{
				if(roleMenus!=null && roleMenus.contains(""+po.getBindId())){
					MenuModel model = new MenuModel(po);
					treeNodes.add(model);
				}
			}

		}
		return Action.SUCCESS;
	}
	
	public String menuGridDatas(){
		List<MenuModel> menuList = new ArrayList<MenuModel>();
		MenuService service = new MenuService();
		List<MenuPO> poList = service.getChilds(Integer.parseInt(parentid));
		Iterator<MenuPO> iter = poList.iterator();
		while(iter.hasNext()){
			MenuPO po = iter.next();
			MenuModel model = new MenuModel(po);
			menuList.add(model);
		}
		map.put("totalCount", new Integer(11));
		map.put("data", menuList);
		return Action.SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String save(){
		MenuService service = new MenuService();
		service.saveOrUpdateMenu(this.data);
		super.outText("1");
		return null;
	}
	/**
	 * 
	 * @return
	 */
	public String delete(){
		MenuService service = new MenuService();
		service.deletes(this.deleteids);
		super.outText("");
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String saveOrUpdate(){
		DictionaryService service = new DictionaryService();
		service.saveOrUpdateDictionary(this.data);
		super.outText("");
		return null;

	}
	
	
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}


	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getDeleteids() {
		return deleteids;
	}

	public void setDeleteids(String deleteids) {
		this.deleteids = deleteids;
	}

	public List<MenuModel> getTreeNodes() {
		return treeNodes;
	}

	public void setTreeNodes(List<MenuModel> treeNodes) {
		this.treeNodes = treeNodes;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}


	
	

}
