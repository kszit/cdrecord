<%@page import="com.kszit.CDReport.util.StaticVaribles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@page import="com.kszit.CDReport.cor.model.DataReportModel"%>



<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>报表上报情况</title>
<%@ include file="/common/comm.jsp"%>
<script type="text/javascript"
	src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=JS_PATH %>/common/mapOfjs.js"></script>
<script type="text/javascript">

var reportConfigListUtl = content_path+"/reportConfig_json/configReportListJson.do?reportConfig.rtyear=";

	function selectReportByYear(yearselect) {
		var selectedYear = yearselect.value;
		var setAppearDepartment = {
			url : reportConfigListUtl + selectedYear,
			callback : function(options, success, response) {//回调函数

				var msg = [ "请求是否成功：", success, "\n", "服务器返回值：",
						response.responseText, "本地自定义属性：", options.customer ];
				var t = Ext.util.JSON.decode(response.responseText);
				var t_length = t.length;
				var obj = document.getElementById('reportNameList');
				obj.options.length = 0;
				selectAddAll(obj);
				for (i = 0; i < t_length; i++) {
					var id = t[i].id;
					var content = t[i].text;
					var rtType = t[i].rtType;
					obj.options.add(new Option(content, id));
				}
				var deadlineSelect = document.getElementById('deadline');
				deadlineSelect.options.length = 0;
				selectAddAll(deadlineSelect);
			}
		};
		Ext.Ajax.request(setAppearDepartment);//发送请求
	}

	var deadlineListUrl = content_path
			+ "/dictionary_json/listByJsonByBindid.do?type=";
	function selectDeadlineByReport(deadlineSelected) {
		var selectedDeadline = deadlineSelected.value;
		var setAppearDepartment = {
			url : deadlineListUrl + selectedDeadline,
			callback : function(options, success, response) {//回调函数
				var msg = [ "请求是否成功：", success, "\n", "服务器返回值：",
						response.responseText, "本地自定义属性：", options.customer ];
				var t = Ext.util.JSON.decode(response.responseText).data;
				var t_length = t.length;
				var obj = document.getElementById('periodsList');
				obj.options.length = 0;
				selectAddAll(obj);
				for (i = 0; i < t_length; i++) {
					var id = t[i].bindId;
					var content = t[i].name;
					obj.options.add(new Option(content, id));
				}
			}
		};
		Ext.Ajax.request(setAppearDepartment);//发送请求
	}
	
	function selectAddAll(selectObject){
		var one = new Option("全部", "0");
		one.selected = true;
		selectObject.options.add(one);
	}
	
	function initSelected(){
		
		var yearSelect = document.getElementById('reportYear');
		selectAddAll(yearSelect);
		
		var deptSelect = document.getElementById('deptList');
		selectAddAll(deptSelect);
		
		var deadlineSelect = document.getElementById('periodsList');
		selectAddAll(deadlineSelect);
		
		var reportNameSelect = document.getElementById('reportNameList');
		selectAddAll(reportNameSelect);
		
	}
</script>
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<link rel="stylesheet" type="text/css"
	href="<%=CSS_PATH %>/displaytag.css" />

</head>
<body>
	<s:form name="reportDataForm"
		action="/reportData/reportData_queryAppearReportListPage.action" method="post"
		theme="simple">







		<table>
			<tr>
				<td>单位</td>
				<td><s:select cssClass="cdrecordInput" onchange='' name='dataReportModel.createDepBindid' id='deptList'
						list="departmentAndReportList" listKey="deptBindid"
						listValue="departmentName" value="dataReportModel.createDepBindid" /></td>
				
				<td>报表名称</td>
				<td>
				<s:select label="年份" 
						cssClass="cdrecordInput" onchange='selectDeadlineByReport(this);' id="reportNameList"
						name='dataReportModel.repotBindid' list="reportConfigList" listKey="reportLink" listValue="reportName"
						value="dataReportModel.repotBindid" />
				
				<td>年份</td>
				<td><s:select label="年份" id='reportYear'
						cssClass="cdrecordInput" onchange='selectReportByYear(this);'
						name='dataReportModel.reportYear' list="yearList" listKey="name" listValue="name"
						value="dataReportModel.reportYear" />
				<!-- 
				<select name="reportName" id="reportName"
					class="cdrecordInput" onchange="selectDeadlineByReport(this);">

				</select>
				-->
				</td>


				<td>期限</td>
				<td>
				<s:select label="年份" id='periodsList'
						cssClass="cdrecordInput" onchange=''
						name='dataReportModel.periods' list="periodList" listKey="bindId" listValue="name"
						value="dataReportModel.periods" />
				<!-- 
				<select name="deadline" id="deadline" class="cdrecordInput"
					onchange="">


				</select>
				 -->
				</td>
				
				<td><button class='cdrecordButton'>&nbsp;&nbsp;查询&nbsp;&nbsp;</button></td>
			</tr>
		</table>
	
		
		<display:table name="dataReportList" id="reportData" class='list'
			cellspacing="1" cellpadding="1" pagesize="10"
			requestURI="/reportData/reportData_queryAppearReportListPage.action"
			export="true" excludedParams="id">
			<%DataReportModel reportModel = (DataReportModel)reportData;%>
			<display:column property="createDepName" style="width:15%"
				title="上报单位"></display:column>
			<display:column property="reportName" style="width:15%" title="报表名称"></display:column>
			<display:column property="year" style="width:5%" title="年份"></display:column>
			<display:column property="periodsName" style="width:5%" title="期限"></display:column>
			<display:column property="verifyName" style="width:5%" title="上报状态"></display:column>
			<display:column style="width:5%" title="操作">
				<% if(reportModel.getVerify()!=StaticVaribles.DataReportFlowState_publish){
				%>
				<a href='#' onclick="alert('测试版，不提供催报。');return false;">催报</a>
				<%
			}
			%>

			</display:column>

		</display:table>

	</s:form>
</body>
</html>