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
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/CheckColumn.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/TreeGrid/TreeGrid.js"></script>
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/treegrid/TreeGrid.css" />
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/treegrid/TreeGridLevels.css" />


<script type="text/javascript">
var reportid = '<s:text name="headerRowModel.reportBindid"/>';
var hbindid = '<s:text name="headerRowModel.bindId"/>';
var dataFrombindid = '<s:text name="dataFromTypeBindid"/>';
Ext.BLANK_IMAGE_URL = '../R_framework/ext-3_0_0/resources/images/default/s.gif';

Ext.onReady(
	function(){
		createGrid();
	}
);


function submitForm(){
	var options = { 
			success: function(){ alert('更新成功');} //显示操作提示 
		}; 
	$('#uiModelSaveH').ajaxSubmit(options);
	return false;
}
</script>
<script type="text/javascript" src="<%=JS_PATH %>/config/report/DataFromConfigHSpec.js"></script>

<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />

</head>
<body>
<s:form action="" method="post" theme='simple'> 
<s:hidden name='headerRowModel.bindId'></s:hidden>
<table width=100%>
	<tr>
		<td>
			<div id="VerticalColumnGrid"></div>
		</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					<td>数据来源</td>
					<td><s:select cssClass="cdrecordInput" onchange=''
						name='headerRowModel.UIModule'
				       list="uimodeList"
				       listKey="bindId"
				       listValue="name"
				       value="headerRowModel.UIModule"
				      /></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table border=1>
				<s:iterator value="verticalColumns">
					<tr>
						<td>
							<s:property value="content"/>
						</td>
						<td>
							<s:select cssClass="cdrecordInput" onchange=''
							name='headerRowModel.UIModule'
				      		list="uimodeList"
				       		listKey="bindId"
				       		listValue="name"
				       		value="headerRowModel.UIModule"
				      		/>
						</td>
						<td>
							<button>dd</button>
						</td>
					</tr>
				</s:iterator>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table>
				<tr>
					<td>
						<s:checkboxlist name="selectedVertical" list="verticalColumns" listKey="bindId" listValue="content"></s:checkboxlist>
					</td>
					<td></td>
				</tr>
			</table>
		</td>
	</tr>
	<tr style='display:none'>
		<td><sj:submit name='sumbitbutton1' onCompleteTopics="submitForm" onBeforeTopics="beforeSubmit"  targets="show" cssClass="cdrecordButton" value="保存"/></td>
	</tr>
	
</table>
</s:form>
</body>
</html>