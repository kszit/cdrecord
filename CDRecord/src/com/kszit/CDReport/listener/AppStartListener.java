/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kszit.CDReport.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import ognl.OgnlRuntime;

import com.kszit.CDReport.cor.service.excel.ExcelService;
import com.kszit.CDReport.util.Constants;

/**
 *
 * @author Administrator
 */
public class AppStartListener implements ServletContextListener{
    
    private ServletContext context = null; 
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        context = sce.getServletContext();
        Constants.APP_ROOT_PATH = context.getRealPath("/");
        
        //生成excel文件相关的文件夹
        ExcelService.initFolde();
        OgnlRuntime.setSecurityManager(null);
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.context = null;
    }
    
}
