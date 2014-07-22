package com.kszit.CDReport.cor.filter;

import com.kszit.CDReport.util.Constants;
import java.io.IOException;
import javax.servlet.Filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserLoginFilter implements Filter {

    protected FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hRequest = (HttpServletRequest) request;
        HttpServletResponse hResponse = (HttpServletResponse) response;
        String requesturi = ((HttpServletRequest) request).getRequestURI();
        if (requesturi.endsWith(".js") || requesturi.endsWith(".css")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = hRequest.getSession(true);
        Object user = session.getAttribute(Constants.LOGIN_USERNAME);
        if (user == null) {
            if (requesturi.endsWith("login.jsp") || requesturi.endsWith("login.do")) {
                chain.doFilter(request, response);
            } else {
               hResponse.sendRedirect(hRequest.getContextPath()+"/jsp/persionDepRole/login.jsp");
                return;
            }
        } else {
            chain.doFilter(request, response);
        }
    }
}
