package com.kszit.CDReport.cor.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.kszit.CDReport.cor.dao.hibernate.exception.MyHibernateException;
import com.kszit.CDReport.cor.dao.hibernate.util.HibernateUtil2;

public class HibernateSessionRequestFilter implements Filter {
 
	protected FilterConfig filterConfig = null;
	public void init(FilterConfig filterConfig)throws ServletException{
		this.filterConfig = filterConfig;
	}
	public void destroy(){
		this.filterConfig = null;
	}
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain)throws IOException, ServletException {
		chain.doFilter(request, response);
		//System.out.println("in HibernateSessionRequestFilter---------------");
		String requesturi = ((HttpServletRequest)request).getRequestURI();
		if(requesturi.endsWith(".js") || requesturi.endsWith(".css")){
			//System.out.println("js or css");
			return;
		}
		
		try{
			HibernateUtil2.commitTransaction();
			//System.out.println("HibernateSessionRequestFilter:commit");
		}catch (MyHibernateException e){
			HibernateUtil2.rollbackTransaction();
		}finally{
			HibernateUtil2.closeSession();
			//System.out.println("HibernateSessionRequestFilter:close session");
		}
	}
}
