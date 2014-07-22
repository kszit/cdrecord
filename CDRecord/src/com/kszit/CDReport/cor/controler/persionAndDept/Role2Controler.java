/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.controler.persionAndDept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.controler.ReportCDSupport;
import com.kszit.CDReport.cor.dao.hibernate.po.MenuPO;
import com.kszit.CDReport.cor.model.MenuModelWithCheck;
import com.kszit.CDReport.cor.model.persionDepRole.Role2Model;
import com.kszit.CDReport.cor.service.MenuService;
import com.kszit.CDReport.cor.service.persionDepRole.RoleService;
import com.opensymphony.xwork2.Action;

/**
 * 组件内部角色
 *
 * @author Administrator
 */
public class Role2Controler extends ReportCDSupport {
	//角色
	private Role2Model role2Model;
    //待保存的json数据
    private String data = "";
    //ids
    private String ids = "";
    //grid中的数据
    private Map<String, Object> roleMap = new HashMap<String, Object>();
    
	// 角色关联的菜单列表
	private List<Object> selectMenuTreeObjects = new ArrayList<Object>();;
	//menu的父id
	private String menuParentId;

	
	
    public String listByJson() {
        RoleService service = new RoleService();
        roleMap.put("totalCount", new Integer(11));
        roleMap.put("data", service.getRoles());
        return Action.SUCCESS;
    }

    public String delete(){
        RoleService service = new RoleService();
        service.deleteRole(this.ids);
        super.outText("");
        return null;
    }
    
    /**
     * 
     * @return
     */
    public String setRoleMenuTreeData(){
    	//System.out.println("角色id："+role2Model.getBindId());
    	//System.out.println("菜单父："+menuParentId);
    	//查询当前选中的role的数据
    	RoleService roleService = new RoleService();
    	Role2Model roleModel =  roleService.getRoleByBindId(role2Model.getBindId()+"");
    	
		MenuService menuService = new MenuService();
		List<MenuPO> poList = menuService.getChilds(Integer.parseInt(menuParentId));
		Iterator<MenuPO> iter = poList.iterator();
		while(iter.hasNext()){
			MenuPO po = iter.next();
			MenuModelWithCheck model = new MenuModelWithCheck(po);
			model.setId((int)model.getBindId());
			model.setIsleaf(true);
			String seletedMenu = roleModel.getMenus();
			if(seletedMenu!=null && seletedMenu.contains("|"+model.getBindId()+"|")){
				model.setChecked(true);
			}
			selectMenuTreeObjects.add(model);
		}
    	return Action.SUCCESS;
    }
    
    public String saveMenuOfRole(){
    	RoleService roleService = new RoleService();
    	Role2Model roleModel =  roleService.getRoleByBindId(role2Model.getBindId()+"");
    	roleModel.setMenus(role2Model.getMenus());
    	roleService.saveOrUpdateRole(roleModel);
		super.outText("22");
		return "";
    }
    
    
    
    
    
    /**
     * 数据保存和更新，格式为json
     *
     * @return
     */
    public String saveOrUpdate() {
        RoleService service = new RoleService();
        String id = service.saveOrUpdateRole(this.data);
        super.outText(id);
        return null;

    }

    public Map<String, Object> getRoleMap() {
        return roleMap;
    }

    public void setRoleMap(Map<String, Object> roleMap) {
        this.roleMap = roleMap;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

	public Role2Model getRole2Model() {
		return role2Model;
	}

	public void setRole2Model(Role2Model role2Model) {
		this.role2Model = role2Model;
	}

	public List<Object> getSelectMenuTreeObjects() {
		return selectMenuTreeObjects;
	}

	public void setSelectMenuTreeObjects(List<Object> selectMenuTreeObjects) {
		this.selectMenuTreeObjects = selectMenuTreeObjects;
	}

	public String getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(String menuParentId) {
		this.menuParentId = menuParentId;
	}
    
}
