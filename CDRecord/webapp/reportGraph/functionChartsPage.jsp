<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page import="com.kszit.CDReport.cor.model.graph.GraphConfigSubModel,com.kszit.CDReport.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/common/comm.jsp" %>




<script type="text/javascript" src="<%=FUNCTIONCHARS_PATH %>/FusionCharts.js"></script>

<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />


</head>
<body>
<input type='hidden' id='graphConfigBindid' name='graphConfigBindid' value='<s:property value="graphCMModel.bindId"/>'/>
<center>
		<div id="chart1div"></div>
		<button onclick='reloadData();'>234</button>
</center>
</body>
</html>

<script type="text/javascript">
	var chart1 = new FusionCharts("<%=FUNCTIONCHARS_PATH %>/Charts/<s:property value="functionChartsPic.chartName"/>", "<s:property value="functionChartsPic.chatId"/>", "<s:property value="functionChartsPic.width"/>", "<s:property value="functionChartsPic.height"/>", "0", "1");		   			
	chart1.setDataURL(content_path+'/graphData/graphD_getGraphDataAction.do?graphCMModel.bindId=<s:property value="graphCMModel.bindId"/>');	
	chart1.render("chart1div");
	

	function reloadData(){
		var graphConfigBindid = document.getElementById('graphConfigBindid').value;
		chart1.setDataURL(content_path+'/graphData/graphD_getGraphDataAction.do?graphCMModel.bindId='+graphConfigBindid);	
	}
</script>