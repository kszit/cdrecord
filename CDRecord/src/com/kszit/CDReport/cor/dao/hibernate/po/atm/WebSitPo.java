package com.kszit.CDReport.cor.dao.hibernate.po.atm;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.ParentPO;
import com.kszit.CDReport.cor.model.atm.WebSitModel;

public class WebSitPo extends ParentPO {

	private String wsName;

	private String wsNumber;
	
	private String belongBank;
	
	
	public WebSitPo(){}
	
	public WebSitPo(WebSitModel model){
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

	public String getWsName() {
		return wsName;
	}

	public void setWsName(String wsName) {
		this.wsName = wsName;
	}

	public String getWsNumber() {
		return wsNumber;
	}

	public void setWsNumber(String wsNumber) {
		this.wsNumber = wsNumber;
	}

	public String getBelongBank() {
		return belongBank;
	}

	public void setBelongBank(String belongBank) {
		this.belongBank = belongBank;
	}
	
	
	
}
