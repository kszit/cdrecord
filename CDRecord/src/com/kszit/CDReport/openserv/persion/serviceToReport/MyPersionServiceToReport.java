package com.kszit.CDReport.openserv.persion.serviceToReport;

import com.kszit.CDReport.openserv.persion.model.PersionToReport;
import javax.servlet.http.HttpSession;

/**
 * 测试类，获取当前登陆用户名，在配置文件中配置。application.properties
 * 
 * 
 * @author Administrator
 */
public class MyPersionServiceToReport implements PersionServiceToReportI {

	public PersionToReport getCurrentUser(HttpSession session) {
		PersionToReport user = new PersionToReport();
		user.setUserid("1");
		user.setUserAccount("kszit");
		user.setUserName("韩晓伟");
		user.setUserDepId("1");
		return user;
	}

	public PersionToReport getUserById(String id) {
		PersionToReport user = new PersionToReport();
		user.setUserid("1");
		user.setUserAccount("kszit");
		user.setUserName("韩晓伟");
		user.setUserDepId("1");
		return user;
	}

}
