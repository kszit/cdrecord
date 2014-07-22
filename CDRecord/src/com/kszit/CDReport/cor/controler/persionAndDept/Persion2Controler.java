/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.controler.persionAndDept;

import com.kszit.CDReport.cor.controler.ReportCDSupport;
import com.kszit.CDReport.cor.model.persionDepRole.Persion2Model;
import com.kszit.CDReport.cor.service.persionDepRole.PersionService;
import com.kszit.CDReport.util.Constants;
import com.opensymphony.xwork2.Action;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组件内部人员
 *
 * @author Administrator
 */
public class Persion2Controler extends ReportCDSupport {

    
    public String listByJson(){
            
            PersionService service = new PersionService();
            List models = service.getPersionByDepid(depid);
		
                
		persionMap.put("totalCount", models.size());
		persionMap.put("data", models);
		return Action.SUCCESS;
	}
	

	/**
	 * 
	 * @return
	 */
	public String delete(){
                PersionService service = new PersionService();
                service.delete(this.deleteids);
		return null;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String saveOrUpdate(){
		PersionService service = new PersionService();
		String id = service.saveOrUpdateDept(this.data);
		super.outText(id);
		return null;

	}
    
    public String login(){
        PersionService service = new PersionService();
        Persion2Model persionModel = service.getPersionByLoginName(username);
        if(persionModel.getLoginName()==null){
            return "loginPage";
        }else{
            super.getRequest().getSession().setAttribute(Constants.LOGIN_USERNAME, this.username);
            return "mainPage";
        }
        
    }
    public String logout(){
        super.getRequest().getSession().invalidate();
        return "loginPage";
    }
    
    
    
    
    
    private String username;
    
    private String password;
    
    
    private String data;
    private String deleteids;
    //grid数据
    private Map<String, Object> persionMap = new HashMap<String, Object>();
    //
    private String depid;

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

    public Map<String, Object> getPersionMap() {
        return persionMap;
    }

    public void setPersionMap(Map<String, Object> persionMap) {
        this.persionMap = persionMap;
    }

    public String getDepid() {
        return depid;
    }

    public void setDepid(String depid) {
        this.depid = depid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
