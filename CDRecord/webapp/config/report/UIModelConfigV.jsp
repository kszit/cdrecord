<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportConfig.reportName"/></title>
<%@ include file="/common/comm.jsp" %>
<sj:head jquerytheme="cupertino" ajaxcache="false" compressed="false"/>

<script type="text/javascript">
function submitForm(){
	var options = { 
			success: function(){ alert('更新成功');} //显示操作提示 
		}; 
	$('#uiModelSaveV').ajaxSubmit(options);
	return false;
}
</script>
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />

</head>
<body>
<!--/reportData/reportConfig/headerRowC_uiModelConfigSave.action  -->
<s:form action="/reportConfig_json/uiModelSaveV.action" method="post" theme='simple'> 
<s:hidden name='verticalColumnModel.bindId'></s:hidden>
<table width=100%>
	<tr>
		<td>组建名称</td>
		<td>
		<s:select cssClass="cdrecordInput" onchange=''
		name='verticalColumnModel.UIModule'
       list="uimodeList"
       listKey="bindId"
       listValue="name"
       value="verticalColumnModel.UIModule"
      />
	</tr>
	<tr>
		<td>组件数据值(下拉列表、数据字典)</td>
		<td><s:textarea name='verticalColumnModel.UIModuleDS' cssClass="cdrecordInput" cols="30" rows="8"></s:textarea></td>
	</tr>
	<tr style='display:none'>
		<td></td>
		<td><sj:submit name='sumbitbutton1' onCompleteTopics="submitForm" onBeforeTopics="beforeSubmit"  targets="show" cssClass="cdrecordButton" value="保存"/></td>
	</tr>
	
</table>
</s:form>
</body>
</html>