package com.kszit.CDReport.openserv.persion.serviceToReport;

import com.kszit.CDReport.cor.model.persionDepRole.Persion2Model;
import com.kszit.CDReport.cor.service.persionDepRole.PersionService;
import com.kszit.CDReport.openserv.persion.model.PersionToReport;
import com.kszit.CDReport.util.Constants;
import javax.servlet.http.HttpSession;

/**
 * 测试类，获取当前登陆用户名，在配置文件中配置。application.properties
 * 
 * 
 * @author Administrator
 */
public class MyPersionServiceToReportInCom implements PersionServiceToReportI {

	public PersionToReport getCurrentUser(HttpSession session) {
            String loginUserName = (String) session.getAttribute(Constants.LOGIN_USERNAME);
            PersionService service = new PersionService();
            Persion2Model model = service.getPersionByLoginName(loginUserName);
		return toPersionToReport(model);
	}

        
        
	public PersionToReport getUserById(String id) {
		PersionToReport user = new PersionToReport();
		user.setUserid("1");
		user.setUserAccount("kszit");
		user.setUserName("韩晓伟");
		user.setUserDepId("1");
		return user;
	}
        
        
private PersionToReport toPersionToReport(Persion2Model model){
            		PersionToReport user = new PersionToReport();
		user.setUserid(model.getBindId()+"");
		user.setUserAccount(model.getLoginName());
		user.setUserName(model.getName());
		user.setUserDepId(model.getDepId()+"");
                user.setRoleId(model.getRoleid());
                return user;
        }
}
