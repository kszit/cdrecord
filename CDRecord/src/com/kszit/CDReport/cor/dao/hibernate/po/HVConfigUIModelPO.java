package com.kszit.CDReport.cor.dao.hibernate.po;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.kszit.CDReport.cor.dao.DBQueryUtilDao;
import com.kszit.CDReport.cor.dao.hibernate.DBQueryUtilDaoHiberImpl;
import com.kszit.CDReport.cor.model.HVConfigUIModelModel;
import com.kszit.CDReport.util.StaticVaribles;

public class HVConfigUIModelPO extends HVConfigPO {

	private int UIModule;

	private String UIModuleDS;

	public HVConfigUIModelPO() {
	}

	/**
	 * 
	 * @param model
	 */
	public HVConfigUIModelPO(HVConfigUIModelModel model) {
		this.copyFromModel(model);
		if (model.getId() == 0) {
			this.setId(null);
		}
	}

	/**
	 * 
	 * @param po
	 */
	public HVConfigUIModelPO(HVConfigUIModelPO po) {
		this.copyFromPo(po);
		this.setId(null);
	}

	/**
	 * 
	 * @param model
	 */
	public HVConfigUIModelPO(HeaderRowConfigPO model) {
		this.copyFromHPo(model);
	}

	/**
	 * UIModel在横向、纵向配置表及单元格配置表中 都有配置信息；在生成页面时，由优先级选定使用哪个中的配置数据生成UIModel对象
	 * 
	 * @param headerConfig
	 * @param columnConfig
	 * @param cellConfig
	 */
	public HVConfigUIModelPO(HeaderRowConfigPO headerConfig,
			VerticalColumnConfigPO columnConfig, HVConfigUIModelPO cellConfig) {
		if (headerConfig != null) {
			this.copyFromHPo(headerConfig);
		}

		if (cellConfig != null
				&& cellConfig.getUIModule() != 0
				&& cellConfig.getUIModule() != StaticVaribles.HTMLUIModule_NullBindid) {
			this.copyFromPo(cellConfig);
		}
	}

	private void copyFromHPo(HeaderRowConfigPO model) {
		this.setReportBindid(model.getReportBindid());
		this.setHbindid(model.getBindId());

		this.setUIModule(model.getUIModule());
		this.setUIModuleDS(model.getUIModuleDS());
	}

	private void copyFromModel(HVConfigUIModelModel model) {
		try {
			BeanUtils.copyProperties(this, model);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void copyFromPo(HVConfigUIModelPO po) {
		try {
			BeanUtils.copyProperties(this, po);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int getUIModule() {
		return UIModule;
	}

	public void setUIModule(int uIModule) {
		UIModule = uIModule;
	}

	public String getUIModuleDS() {
		return UIModuleDS;
	}

	public void setUIModuleDS(String uIModuleDS) {
		UIModuleDS = uIModuleDS;
	}

	@Override
	public boolean equals(Object arg0) {
		return super.equals(arg0);
	}

	/**
	 * 根据UIModelDS的值返回Map格式的列表，key--》value
	 * 
	 * @return
	 */
	public Map<String, String> getUIModuleDSMap() {
		Map<String, String> keyAndValue = new HashMap<String, String>();
		if (UIModuleDS == null || "".equals(UIModuleDS)) {
			return keyAndValue;
		}
		if (UIModuleDS.contains("|")) {
			String[] values = UIModuleDS.split("\\|");
			for (int i = 0, vlength = values.length; i < vlength; i++) {
				keyAndValue.put(values[i], values[i]);
			}
		} else if (UIModuleDS.contains("select")) {
			DBQueryUtilDao queryUtil = new DBQueryUtilDaoHiberImpl();
			List<Object[]> objects = queryUtil.exceleSql(UIModuleDS);
			for (Object[] os : objects) {
				keyAndValue.put(os[0] + "", os[1] + "");
			}
		}

		return keyAndValue;
	}
}
