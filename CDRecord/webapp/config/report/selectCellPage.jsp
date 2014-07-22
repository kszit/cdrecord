<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportId"/></title>
<%@ include file="/common/comm.jsp" %>
<sj:head jquerytheme="cupertino" ajaxcache="false" compressed="false"/>
<sx:head extraLocales="UTF-8"/>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>

<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />

<script type="text/javascript">  
	
 	 //选择单元格页面
    function selectCell(){
    	var selectCells = document.getElementsByName('tablecell');
    	for(i=0;i<selectCells.length;i++){
    		if(selectCells[i].checked){
    			alert(selectCells[i].value);
    		}
    	}
    	window.opener.addCellItem();
    } 
	 //选择单元格页面
    function clickCell(cell){
    	var reportbindid = document.getElementById('headerRowModel_reportBindid').value;
    	var dataFromType = window.opener.getSelectedDataFrom();
    	var nameAndValue = cell.value.split('|');
    	window.opener.addCellItem(nameAndValue[0],reportbindid+"#"+nameAndValue[1]+"#"+dataFromType,cell.checked);
    }
</script>
</head>
<body>
<table width=100%>
	
<s:hidden name='headerRowModel.reportBindid'></s:hidden>
	<tr>
		<td><button onclick='selectCell();'>确定</button></td>
	</tr>
	<tr>
		<td>
			<table><s:text name="selectCellHTML"/></table>
		</td>
	</tr>
</table>
</body>
</html>