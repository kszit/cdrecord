/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.persionDepRole;

import com.kszit.CDReport.cor.dao.DepartmentDao;
import com.kszit.CDReport.cor.dao.hibernate.DepartmentDaoImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.DepartmentPO;
import com.kszit.CDReport.cor.model.persionDepRole.Department2Model;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;

/**
 * 组织机构
 * @author Administrator
 */
public class DepartmentService {
    DepartmentDao departmentDao = new DepartmentDaoImpl();
    

    
            	
	/**
	 * 
	 * @param jsondata  
	 * @return
	 */
	public String saveOrUpdateDept(String jsondata){
		Department2Model role = jsonToObject(jsondata);
		return saveOrUpdateDept(role);
	}
        
	public String saveOrUpdateDept(Department2Model model){
		DepartmentPO p = new DepartmentPO(model);
		p.initBindId();
		return departmentDao.saveOrUpdate(p);
	}
        
	public int delete(String ids){
		departmentDao.deletesByBindid(ids);
		return 1;
	}
        
        public Department2Model getDeptByBindid(String bindid){
            DepartmentPO po =  departmentDao.getOneByBindid(Long.parseLong(bindid));
            Department2Model model = new Department2Model(po);
            return model;
        }
        
        /**
         * 
         * @return 
         */
        public List<Department2Model> getDeptByParentId(String parentid){
            List<Department2Model> models = new ArrayList<Department2Model>();
            List<DepartmentPO> departPos = departmentDao.getDeptByParentId(Long.parseLong(parentid));
            for(DepartmentPO po:departPos){
                Department2Model model = new Department2Model(po);
                model.setId(po.getBindId().intValue());
                models.add(model);
            }
            return models;
        }
	

	

        /**
         * json 转换为model
         * @param jsonObject
         * @return 
         */
	private Department2Model jsonToObject(String jsonObject){
		String jsonObject2 = jsonObject.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		Department2Model role2Model = (Department2Model)JSONObject.toBean(jsonDict, Department2Model.class);
		return role2Model;
	}
}
