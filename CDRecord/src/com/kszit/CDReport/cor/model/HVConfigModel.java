package com.kszit.CDReport.cor.model;

import java.lang.reflect.InvocationTargetException;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.HVConfigDataFromPO;

public class HVConfigModel extends TreeParentModel{

	private String reportBindid;
	
	private long hbindid;
	
	private long vbindid;
	
	private String vName;
	
	private int _id;
	
	private int _parent;
	
	private boolean _is_leaf;
	
	private String orderIndexStr;
	
	public HVConfigModel(){}
	
	public HVConfigModel(HVConfigDataFromPO po){
		try {
			BeanUtils.copyProperties(this,po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public HVConfigModel(String jsondata){
		String jsonObject2 = jsondata.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		HVConfigModel menu_temp = (HVConfigModel)JSONObject.toBean(jsonDict, HVConfigModel.class);
		try {
			BeanUtils.copyProperties(this,menu_temp);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public String getReportBindid() {
		return reportBindid;
	}

	public void setReportBindid(String reportBindid) {
		this.reportBindid = reportBindid;
	}

	public long getHbindid() {
		return hbindid;
	}

	public void setHbindid(long hbindid) {
		this.hbindid = hbindid;
	}

	public long getVbindid() {
		return vbindid;
	}

	public void setVbindid(long vbindid) {
		this.vbindid = vbindid;
	}

	public String getvName() {
		return vName;
	}

	public void setvName(String vName) {
		this.vName = vName;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_parent() {
		return _parent;
	}

	public void set_parent(int _parent) {
		this._parent = _parent;
	}

	public boolean is_is_leaf() {
		return _is_leaf;
	}

	public void set_is_leaf(boolean _is_leaf) {
		this._is_leaf = _is_leaf;
	}

	public String getOrderIndexStr() {
		return orderIndexStr;
	}

	public void setOrderIndexStr(String orderIndexStr) {
		this.orderIndexStr = orderIndexStr;
	}
	
	

}
