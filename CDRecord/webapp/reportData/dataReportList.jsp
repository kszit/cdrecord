<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>  
<%@page import="com.kszit.CDReport.cor.model.DataReportModel,com.kszit.CDReport.util.*"%>



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


<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />
<%

    org.displaytag.decorator.CheckboxTableDecorator decorator = new org.displaytag.decorator.CheckboxTableDecorator();
    decorator.setId("id");
    decorator.setFieldName("id");
    pageContext.setAttribute("checkboxDecorator", decorator);

%>
</head>
<body>
<form name="reportDataForm" action="<%=CONTEXT_PATH %>/reportData/reportData_deletes.action" method="post">

    <div class="button_div">
<button class="cdrecordButton" onclick="window.open(content_path+'/reportConfig/reportConfig_selectReportPage.action?reportConfig.reportState=<%=StaticVaribles.ReportState_UsingBindId %>');return false;">创建新报表</button>
<input type='submit' class="cdrecordButton" value='删除报表'></input>
    </div>

	<display:table name="dataReportList" id="reportData" decorator="checkboxDecorator" class='list' cellspacing="1" cellpadding="1" pagesize="18" requestURI="/reportData/reportData_listPageOfMy.action" export="true"  excludedParams="id">
	<%
	DataReportModel model = (DataReportModel)reportData;
	%>    
		<display:column property="checkbox" style="width:5%"  title="选择"/>
		<display:column property="reportName" style="width:15%"  title="报表名称"></display:column>
		<display:column property="year" style="width:5%"  title="年份"></display:column>
		<display:column property="periodsName" style="width:5%"  title="期限"></display:column>
	     <display:column property="madeManName2" title="填报人" style="width:15%" ></display:column>
	     <display:column property="madeManDate" title="创建时间" style="width:15%" ></display:column>
	     <display:column title="操作" style="width:15%" >
	     	   
	     <a href='#' onclick="window.open(content_path+'/reportData/reportData_inputPage.do?dataReportModel.repotBindid=<%=model.getRepotBindid() %>&dataReportModel.bindId=<%=model.getBindId() %>');return false;">编辑</a>
	     &nbsp;&nbsp;
	     <a href='#' onclick="window.open(content_path+'/reportData/reportData_seePage.do?dataReportModel.repotBindid=<%=model.getRepotBindid() %>&dataReportModel.bindId=<%=model.getBindId() %>');return false;">查看</a>
	     	&nbsp;&nbsp;
	     	  删除
	     
	     
	     </display:column>
	</display:table>
	
</form>
</body>
</html>