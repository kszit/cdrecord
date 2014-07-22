<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>  
<%@page import="com.kszit.CDReport.cor.model.DataReportModel"%>



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
<script type="text/javascript">
	function setReportToBack(bindid){
    	var setAppearDepartment = {
       			url:"<%=CONTEXT_PATH %>/reportData/reportData_setReportToBack.do?dataReportModel.bindId="+bindid,
       			callback:function(options,success,response){//回调函数
       				
       				var msg = ["请求是否成功：" ,success,"\n",
       							"服务器返回值：",response.responseText,
       							"本地自定义属性：",options.customer];
       				alert(response.responseText);
       				window.location.reload();
       			}
        	};
       		Ext.Ajax.request(setAppearDepartment);//发送请求
	}
	
	
	function setReportToPublish(bindid){
    	var setAppearDepartment = {
       			url:"<%=CONTEXT_PATH %>/reportData/reportData_setReportToPublish.do?dataReportModel.bindId="+bindid,
       			callback:function(options,success,response){//回调函数
       				
       				var msg = ["请求是否成功：" ,success,"\n",
       							"服务器返回值：",response.responseText,
       							"本地自定义属性：",options.customer];
       				alert(response.responseText);
       				window.location.reload();
       			}
        	};
       		Ext.Ajax.request(setAppearDepartment);//发送请求
	}
	
	


</script>



</head>
<body>
<form name="reportDataForm" action="/reportData/reportData/reportData_deletes.action" method="post">
<span class="pagelinks">
<button class="cdrecordButton" onclick="window.open('/reportData/reportConfig/reportConfig_selectReportPage.action');return false;">添加</button>
<input type='submit' class="cdrecordButton" value='删除'></input>
</span>
	<display:table name="dataReportList" id="reportData" decorator="checkboxDecorator" class='list' cellspacing="1" cellpadding="1" pagesize="10" requestURI="/reportData/reportDat/reportData_listPage.action" export="true"  form="reportDataForm" excludedParams="id">
	<%
	DataReportModel model = (DataReportModel)reportData;
	%>     
		<display:column property="checkbox" style="width:5%"  title="选择"/>
		<display:column property="reportName" style="width:15%"  title="报表名称"></display:column>
		<display:column property="year" style="width:5%"  title="年份"></display:column>
		<display:column property="periodsName" style="width:5%"  title="期限"></display:column>
	    <display:column property="madeManName2" title="填报人" style="width:15%" ></display:column>
	     <display:column property="madeManDate" title="填报时间" style="width:15%" ></display:column>
	     <display:column title="操作" style="width:20%" >
	     		     <a href='#' onclick="window.open(content_path+'/reportData/reportData_seePage.do?dataReportModel.repotBindid=<%=model.getRepotBindid() %>&dataReportModel.bindId=<%=model.getBindId() %>');return false;">查看</a>
	     	&nbsp;&nbsp;
	     	<a href='#' onclick="window.open(content_path+'/file/excelData.do?reportBindid=<%=model.getBindId() %>');return false;">导出excel</a>
	     &nbsp;&nbsp;
	     	<a href='#' onclick="setReportToBack('<%=model.getBindId() %>');return false;">回退</a>
	     &nbsp;&nbsp;
	     	<a href='#' onclick="setReportToPublish('<%=model.getBindId() %>');return false;">上报</a>
	     
	     </display:column>
	</display:table>
	
</form>
</body>
</html>