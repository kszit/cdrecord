<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!-- 报表填报页面 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/common/comm.jsp" %>

<script type="text/javascript" src="<%=EASYUI_PATH %>/jquery.edatagrid.js"></script>
</head>
<body>


<s:text name="reportTableHtmlCode"/>


<div id="roleEditToolbar">
	<a
		href="javascript:void(0)" class="easyui-linkbutton"
		iconCls="icon-save" plain="true"
		onclick="javascript:$('#roleEditGrid').edatagrid('saveRow')">保存</a>
</div>

<script type="text/javascript">

	function deleteRole(){
		
		var id = $('#roleEditGrid').datagrid('getSelected').id; 
		$('#roleEditGrid').edatagrid({
			destroyUrl : '/role/deleteA/'+id
		});
		
		$('#roleEditGrid').edatagrid('destroyRow');
	}
	
	$(function() {
		$('#roleEditGrid').edatagrid({ 
			url : '/reportData/reportData_inputPageDataOfGrid.do?dataReportModel.repotBindid=3150-0',
			//url : '/testData.jsp',		
			saveUrl : '/reportData/reportData_saveReportOfGrid.do',
			updateUrl : '/reportData/reportData_saveReportOfGrid.do',
			destroyUrl : '/role/deleteA/0',
			onSelect : function(rowIndex, rowData){
				if(rowData.id){
					$('#roleSetMainPage').panel({href:'/role/setManagerPage/'+rowData.id}); 
				}
			}
		});
	});
</script>



</body>
</html>