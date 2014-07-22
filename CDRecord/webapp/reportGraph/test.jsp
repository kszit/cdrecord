<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
<center>
		<div id="chart1div"></div>
		<button onclick='reloadData();'>234</button>
</center>
</body>
</html>

<script type="text/javascript">
	var chart1 = new FusionCharts("<%=FUNCTIONCHARS_PATH %>/Charts/Column3D.swf", "chart1Id", "400", "300", "0", "1");		   			
	chart1.setDataXML("<chart><set label='A' value='10' /><set label='B' value='11' /></chart>");
	chart1.render("chart1div");
	
	function reloadData(){
		chart1.setDataXML("<chart><set label='C' value='102' /><set label='D' value='101' /></chart>");
		chart1.
	}
	
</script>