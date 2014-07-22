package com.kszit.CDReport.cor.service.atm;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.kszit.CDReport.cor.dao.atm.WebSitDao;
import com.kszit.CDReport.cor.dao.atm.WebSitDaoHiberImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.atm.WebSitPo;
import com.kszit.CDReport.cor.model.atm.WebSitModel;

public class WebSitService {

	WebSitDao websitDao = new WebSitDaoHiberImpl();
	/**
	 * 
	 * @param jsondata
	 * @return
	 */
	public String saveOrUpdateWebsit(String jsondata) {
		WebSitModel websit = jsonToObject(jsondata);
		return saveOrUpdateWebsit(websit);
	}
	
	public String saveOrUpdateWebsit(WebSitModel websit) {
		WebSitPo p = new WebSitPo(websit);
		p.initBindId();
		return websitDao.insertOrUpdate(p);
	}
	
	public List<WebSitModel> getAllWebsits(){
		List<WebSitModel> models = new ArrayList<WebSitModel>();
		List<WebSitPo> pos = websitDao.getListOfAll();
		for(WebSitPo po:pos){
			WebSitModel model = new WebSitModel(po);
			models.add(model);
		}
		return models;
	}
	
	
	/**
	 * json 转换为model
	 * 
	 * @param jsonObject
	 * @return
	 */
	private WebSitModel jsonToObject(String jsonObject) {
		String jsonObject2 = jsonObject.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		WebSitModel role2Model = (WebSitModel) JSONObject.toBean(jsonDict,
				WebSitModel.class);
		return role2Model;
	}
}
