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

<script type="text/javascript">
function selectReport(reportBindid,version){
	var reportBindidLink = reportBindid+"-"+version;
	if(window.opener.document.reportDataSave){
		window.opener.afterSelectReport(reportBindidLink);
		window.close();
	}else{
		//window.location.reload(content_path+'/reportData/reportData_inputPage.do?dataReportModel.repotBindid='+reportBindidLink);
		window.location.href=content_path+'/reportData/reportData_inputPage.do?dataReportModel.repotBindid='+reportBindidLink;
	}
	
}
</script>

</head>
<body>
<form name="reportForm" action="/reportData/reportConfig/reportConfig_deletes.action" method="post">
	<display:table name="reportList" id="report" class='list' cellspacing="1" cellpadding="1" pagesize="10" requestURI="/reportData/reportConfig/reportConfig_listPage.action" form="reportForm">
	<%
		ReportConfigModel model = (ReportConfigModel)report;
	%>     
		<display:column property="reportNo" style="width:15%"  title="编号"></display:column>
		<display:column property="rtyear" title="年份" style="width:15%" ></display:column>
		<display:column property="rttypeName" title="类型" style="width:15%" ></display:column>
	    <display:column title="报表名称" style="width:25%"  property="reportName"></display:column>
	    <display:column property="rtversion2" title="版本号" style="width:15%" ></display:column>
		<display:column style="width:5%">
			<button class="cdrecordButton" onclick="selectReport('<%=model.getBindId() %>','<%=model.getRtversion2() %>');return false;">选择</button>
		</display:column>
	</display:table>
</form>
</body>
</html>