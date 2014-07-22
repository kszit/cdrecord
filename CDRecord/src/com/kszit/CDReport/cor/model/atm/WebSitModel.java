package com.kszit.CDReport.cor.model.atm;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.hibernate.po.atm.WebSitPo;
import com.kszit.CDReport.cor.model.ParentModel;
import com.kszit.CDReport.util.StaticVaribles;
/**
 * 配置表格的表头model
 * @author Administrator
 *
 */
public class WebSitModel extends ParentModel{

	private String wsName;

	private String wsNumber;
	
	private String belongBank;
	
	public WebSitModel(){}
	
	public WebSitModel(WebSitPo po){
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
