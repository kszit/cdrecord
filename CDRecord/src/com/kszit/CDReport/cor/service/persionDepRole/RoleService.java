/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.persionDepRole;

import com.kszit.CDReport.cor.dao.RoleDao;
import com.kszit.CDReport.cor.dao.hibernate.RoleDaoImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.RolePO;
import com.kszit.CDReport.cor.model.persionDepRole.Role2Model;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;

/**
 * 角色
 * 
 * @author Administrator
 */
public class RoleService {
	RoleDao roleDao = new RoleDaoImpl();

	public Role2Model getRoleByBindId(String bindid) {
		RolePO po = roleDao.getOneByBindid(Long.parseLong(bindid));
		Role2Model model = new Role2Model(po);
		return model;
	}

	/**
	 * 
	 * @param jsondata
	 * @return
	 */
	public String saveOrUpdateRole(String jsondata) {
		Role2Model role = jsonToObject(jsondata);
		return saveOrUpdateRole(role);
	}

	public String saveOrUpdateRole(Role2Model role) {
		RolePO p = new RolePO(role);
		p.initBindId();
		return roleDao.saveOrUpdate(p);
	}

	public int deleteRole(String ids) {
		roleDao.deletes(ids);
		return 1;
	}

	/**
	 * 
	 * @return
	 */
	public List<Role2Model> getRoles() {
		List<Role2Model> roleModels = new ArrayList<Role2Model>();
		List<RolePO> rolepos = roleDao.getRoleList();
		for (RolePO rolepo : rolepos) {
			Role2Model model = new Role2Model(rolepo);
			roleModels.add(model);
		}
		return roleModels;
	}

	/**
	 * json 转换为model
	 * 
	 * @param jsonObject
	 * @return
	 */
	private Role2Model jsonToObject(String jsonObject) {
		String jsonObject2 = jsonObject.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		Role2Model role2Model = (Role2Model) JSONObject.toBean(jsonDict,
				Role2Model.class);
		return role2Model;
	}
}
