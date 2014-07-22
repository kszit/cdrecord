<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>  
<%@page import="com.kszit.CDReport.cor.model.graph.GraphConfigMainModel,com.kszit.CDReport.util.*"%>



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

function refresh()  //任意你想刷新时调用的方法
{
this.location = this.location;
}
function deleteGraphConfig(bindid){

	var setAppearDepartment = {
   			url:"<%=CONTEXT_PATH %>/graphConfig/graphC_deletesConfigAction.do?graphCMModel.bindId="+bindid,
   			callback:function(options,success,response){//回调函数
   				window.location.reload();
   			}
    	};
   Ext.Ajax.request(setAppearDepartment);//发送请求

}


                       </script>

</head>
<body>
<form name="reportDataForm" action="<%=CONTEXT_PATH %>/graphConfig/graphC_deletesConfigAction.action" method="post">

    <div class="button_div">
<button class="cdrecordButton" onclick="window.open(content_path+'/graphConfig/graphC_inputConfigPage.action');return false;">创建图表</button>
<input type='submit' class="cdrecordButton" value='删除图表'></input>
    </div>

	<display:table name="graphConfigList" id="reportData" decorator="checkboxDecorator" class='list' cellspacing="1" cellpadding="1" pagesize="10" requestURI="/reportData/reportDat/reportData_listPage.action" export="true"  form="reportDataForm" excludedParams="id">
	<%
	GraphConfigMainModel model = (GraphConfigMainModel)reportData;
	%>     
		<display:column property="checkbox" style="width:5%"  title="选择"/>
		<display:column property="name" title="名称" style="width:10%" ></display:column>
		<display:column property="graphTypeName" title="图表类型" style="width:10%" ></display:column>

	     <display:column title="操作" style="width:15%" >
	     	   
	     <a href='#' onclick="window.open(content_path+'/graphConfig/graphC_inputConfigPage.action?graphCMModel.bindId=<%=model.getBindId() %>');return false;">编辑</a>
	     &nbsp;&nbsp;
	     <a href='#' onclick="window.open(content_path+'/reportData/reportData_seePage.do?dataReportModel.repotBindid=<%=model.getBindId() %>&dataReportModel.bindId=<%=model.getBindId() %>');return false;">查看</a>
	     	&nbsp;&nbsp;
	     <a href='#' onclick="deleteGraphConfig(<%=model.getBindId() %>);return false;">删除</a>
	     	&nbsp;&nbsp;
	     	  
	     
	     
	     </display:column>
	</display:table>
	
</form>
</body>
</html>