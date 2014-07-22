<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="com.kszit.CDReport.cor.model.ReportConfigModel"%>


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
<%
    org.displaytag.decorator.CheckboxTableDecorator decorator = new org.displaytag.decorator.CheckboxTableDecorator();
    decorator.setId("id");
    decorator.setFieldName("id");
    pageContext.setAttribute("checkboxDecorator", decorator);
%>
</head>

<body>
<form name="reportForm" action="../../reportConfig/reportConfig_deletes.action" method="post">
    <div class="button_div">
        <button class="cdrecordButton" onclick="window.parent.addItem('新报表','-1');return false;">添加</button>
        <input type='submit' class="cdrecordButton" value='删除'></input>
    </div>
	<display:table name="reportList" id="report" decorator="checkboxDecorator" class='list' cellspacing="1" cellpadding="1" pagesize="10" requestURI="/reportData/reportConfig/reportConfig_listPage.action" export="true"  form="reportForm" excludedParams="id">
	<%
		ReportConfigModel model = (ReportConfigModel)report;
	%>     
		<display:column property="checkbox" style="width:5%"  title="选择"/>
		<display:column property="reportNo" style="width:15%"  title="编号"></display:column>
	     <display:column title="报表名称" style="width:25%" >
	     	<a href='#' onclick="window.parent.addItem('<%=model.getReportName() %>-<%=model.getRtversion2() %>','<%=model.getId() %>');return false;"><%=model.getReportName() %></a>
	     </display:column>
	     <display:column property="rtyear" title="年份" style="width:10%" ></display:column>
	     <display:column property="rtversion2" title="版本号" style="width:5%" ></display:column>
	     <display:column property="reportStateStr" title="状态" style="width:10%" >
	     </display:column>
             <display:column  title="操作" style="width:10%" >基本信息
	     横行
	     纵向
	     </display:column>
	</display:table>
</form>
</body>
</html>