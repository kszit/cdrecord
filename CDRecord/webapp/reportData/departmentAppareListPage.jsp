<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>  
<%@page import="com.kszit.CDReport.cor.model.ReportAppareModel"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportConfig.reportName"/></title>
<%@ include file="/common/comm.jsp" %>
<sj:head jquerytheme="cupertino" ajaxcache="false" compressed="false"/>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/TreeGrid/TreeGrid.js"></script>
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/treegrid/TreeGrid.css" />
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/treegrid/TreeGridLevels.css" />



<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />
</head>
<body>
<form name="reportDataForm" action="/reportData/reportData/reportData_deletes.action" method="post">
<span class="pagelinks">
</span>
</form>
<br>

<display:table name="appareList" id="appearReport" class='list' cellspacing="1" cellpadding="1" pagesize="10" requestURI="/reportData/reportData_listPage.action" export="true"> 
	 <display:column property="reportConfigModel.reportName" style="width:15%"  title="报表名称"></display:column>
     <display:column property="reportConfigModel.rttypeName" style="width:15%" title="报表类型"></display:column>
     <display:column property="reportConfigModel.rtyear" style="width:15%" title="年份"></display:column>
     <display:column property="depName" style="width:15%"  title="单位名称"></display:column>
     <display:column property="appearStateStr" style="width:15%"  title="上报情况"></display:column>
     
     
</display:table>
	

</body>
</html>