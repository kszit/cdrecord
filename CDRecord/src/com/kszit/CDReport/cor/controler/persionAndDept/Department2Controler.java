/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.controler.persionAndDept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kszit.CDReport.cor.controler.ReportCDSupport;
import com.kszit.CDReport.cor.model.persionDepRole.Department2Model;
import com.kszit.CDReport.cor.model.persionDepRole.Department2ModelWithCheck;
import com.kszit.CDReport.cor.service.persionDepRole.DepartmentService;
import com.opensymphony.xwork2.Action;

/**
 * 组件内部组织机构
 * 
 * @author Administrator
 */
public class Department2Controler extends ReportCDSupport {
	private String data;

	private String deleteids;

	// grid数据
	private Map<String, Object> departmentMap = new HashMap<String, Object>();
	// tree数据
	private List<Object> departmentList = new ArrayList<Object>();
	// 加载tree子节点的父id
	private String id;

	public String listByJson() {

		DepartmentService service = new DepartmentService();
		List models = service.getDeptByParentId(this.id);

		departmentMap.put("totalCount", models.size());
		departmentMap.put("data", models);
		return Action.SUCCESS;
	}

	/**
	 * 
	 * @return
	 */
	public String save() {

		return null;

	}

	/**
	 * 
	 * @return
	 */
	public String delete() {
		DepartmentService service = new DepartmentService();
		service.delete(this.deleteids);
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String saveOrUpdate() {
		DepartmentService service = new DepartmentService();
		String id = service.saveOrUpdateDept(this.data);
		super.outText(id);
		return null;
	}

	/**
	 * extjs 树形结构json编码
	 * 
	 * @return
	 */
	public String treeByJson() {
		DepartmentService service = new DepartmentService();
		List models = service.getDeptByParentId(this.id);
		departmentList = new ArrayList(models);
		return Action.SUCCESS;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDeleteids() {
		return deleteids;
	}

	public void setDeleteids(String deleteids) {
		this.deleteids = deleteids;
	}

	public Map<String, Object> getDepartmentMap() {
		return departmentMap;
	}

	public void setDepartmentMap(Map<String, Object> departmentMap) {
		this.departmentMap = departmentMap;
	}

	public List<Object> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Object> departmentList) {
		this.departmentList = departmentList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
