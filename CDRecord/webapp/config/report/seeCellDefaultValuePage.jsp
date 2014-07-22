<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/common/comm.jsp" %>




</head>
<body>
<table width=100%>
<s:hidden name='reportConfig.bindId'></s:hidden>
	<tr>
		<td>
			<table><s:text name="seeDataFromAllTableHTML"/></table>
		</td>
	</tr>
</table>
</body>
</html>