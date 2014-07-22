<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<%@ include file="/common/comm.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/examples/grid-examples.css" />
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/examples/shared/examples.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/CheckColumn.js"></script>

<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/CenterLayout.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/RowLayout.js"></script> 

<script type="text/javascript" src="<%=JS_PATH %>/persionDepRole/persion.js" charset="utf-8"></script>


</head>
<body>
    <div id="header"><h1>Ext Layout Browser</h1></div>
    <div id="bordergrid"></div>
    <div id="AWS_ORG_Operate_Div"></div>

</body>
</html>