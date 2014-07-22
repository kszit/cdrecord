package com.kszit.CDReport.cor.controler.atm;

import java.util.HashMap;
import java.util.Map;

import com.kszit.CDReport.cor.controler.ReportCDSupport;
import com.kszit.CDReport.cor.model.atm.WebSitModel;
import com.kszit.CDReport.cor.service.atm.WebSitService;
import com.kszit.CDReport.cor.service.persionDepRole.RoleService;
import com.opensymphony.xwork2.Action;


/**
 * 
 * @author Administrator
 *
 */
public class WebSitControle extends ReportCDSupport {

	public String listByJson(){
		WebSitService service = new WebSitService();
        webSitMap.put("totalCount", new Integer(11));
        webSitMap.put("data", service.getAllWebsits());
        return Action.SUCCESS;
	}
	
    /**
     * 数据保存和更新，格式为json
     *
     * @return
     */
    public String saveOrUpdate() {
    	WebSitService service = new WebSitService();
        String id = service.saveOrUpdateWebsit(this.data);
        super.outText(id);
        return null;

    }
    
	//角色
	private WebSitModel webSitModel;
    //待保存的json数据
    private String data = "";
    //ids
    private String ids = "";
    //grid中的数据
    private Map<String, Object> webSitMap = new HashMap<String, Object>();
	public WebSitModel getWebSitModel() {
		return webSitModel;
	}

	public void setWebSitModel(WebSitModel webSitModel) {
		this.webSitModel = webSitModel;
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

	public Map<String, Object> getWebSitMap() {
		return webSitMap;
	}

	public void setWebSitMap(Map<String, Object> webSitMap) {
		this.webSitMap = webSitMap;
	}
	
}
