<%-- 
    Document   : header
    Created on : 2012-11-30, 11:38:53
    Author     : Administrator
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <%@ include file="/common/comm.jsp" %>
        
    </head>
    <body>
<div class="header_div">
      <div class="top_head center">
              <div class="adminlogo"><a href="#"><!--<img src="<%=CONTEXT_PATH %>/image/admin_logo2.jpg" />--></a></div>
              <div class="adminmenu">
                     <a href="../persion2/Persion_logout.do" target="_parent">退出</a>
                     <!--
                     <a href="#"><img src="<%=CONTEXT_PATH %>/image/top_menu_3.jpg" /></a>
                     <a href="#"><img src="<%=CONTEXT_PATH %>/image/top_menu_4.jpg" /></a>     
                     -->
              </div>
      </div>
      <div class="adminnews center">
          <span><img src="<%=CONTEXT_PATH %>/image/49_32.jpg" /></span>
          <span>用户名：</span><span><a href="#"><s:property value="user.userName"/></a></span>
          <span>部门：</span><span><a href="#"><s:property value="department.departmentName"/></a></span>
          <span>角色：</span><span><a href="#"><s:property value="role.roleName"/></a></span>
      </div>
</div>
    </body>
</html>
