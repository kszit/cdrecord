package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.model.DictionaryModel;
/**
 * 数据字典model
 * @author Administrator
 */
public class DictionaryPO extends ParentPO{

	private String name;
	
	private String describe2;
	
	private boolean used;
	

	
	private int type;

	public DictionaryPO(){
		
	}
	
	public DictionaryPO(DictionaryModel model){
		try {
			BeanUtils.copyProperties(this,model);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(model.getId()==0){
			this.setId(null);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescribe2() {
		return describe2;
	}

	public void setDescribe2(String describe) {
		this.describe2 = describe;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
