/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.cor.service.persionDepRole;

import com.kszit.CDReport.cor.dao.PersionDao;
import com.kszit.CDReport.cor.dao.hibernate.PersionDaoImpl;
import com.kszit.CDReport.cor.dao.hibernate.po.PersionPO;
import com.kszit.CDReport.cor.model.persionDepRole.Persion2Model;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class PersionService {
    PersionDao persionDao = new PersionDaoImpl();
    
    
            	
	/**
	 * 
	 * @param jsondata  
	 * @return
	 */
	public String saveOrUpdateDept(String jsondata){
		Persion2Model role = jsonToObject(jsondata);
		return saveOrUpdateDept(role);
	}
        
	public String saveOrUpdateDept(Persion2Model model){
		PersionPO p = new PersionPO(model);
		p.initBindId();
		return persionDao.saveOrUpdate(p);
	}
        
	public int delete(String ids){
		persionDao.deletes(ids);
		return 1;
	}
        
    public Persion2Model getPersionByLoginName(String username){
        PersionPO po = persionDao.getPersionByLoninName(username);
        Persion2Model model = new Persion2Model(po);
        return model;
    }
    
    public Persion2Model getPersionByBindid(String bindid){
    	if(bindid==null){
    		return new Persion2Model();
    	}
        PersionPO po = persionDao.getOneByBindid(Long.parseLong(bindid));
        Persion2Model model = new Persion2Model(po);
        return model;
    }       
        /**
         * 
         * @return 
         */
        public List<Persion2Model> getPersionByDepid(String depid){
            List<Persion2Model> models = new ArrayList<Persion2Model>();
            List<PersionPO> persionPos = persionDao.getPersionByDepid(depid);
            for(PersionPO po:persionPos){
                Persion2Model model = new Persion2Model(po);
                model.setRoleid2(po.getRoles());
                //model.setId(po.getBindId().intValue());
                models.add(model);
            }
            return models;
        }
	

	

        /**
         * json 转换为model
         * @param jsonObject
         * @return 
         */
	private Persion2Model jsonToObject(String jsonObject){
		String jsonObject2 = jsonObject.replaceAll("\"", "'");
		JSONObject jsonDict = JSONObject.fromObject(jsonObject2);
		Persion2Model role2Model = (Persion2Model)JSONObject.toBean(jsonDict, Persion2Model.class);
		return role2Model;
	}
}
