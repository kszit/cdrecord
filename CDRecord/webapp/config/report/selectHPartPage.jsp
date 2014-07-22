<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- 报表填报页面 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportId"/></title>
<%@ include file="/common/comm.jsp" %>

	
<script type="text/javascript">  
	
    //选择的单元格的横向和纵向ids
    function getSelectedCellHVIds(){
		var tableHVCell = document.getElementsByName('tableHVCell');
		var selectedTableHVCellIds = '';
		for(i=0;i<tableHVCell.length;i++){
			if(tableHVCell[i].checked){
				selectedTableHVCellIds += tableHVCell[i].value+"|";
			}
		}
    	return selectedTableHVCellIds;
    }
    
    //选择的单元格的横向和纵向ids
    function getSelectedCellHVValues(){
		var tableHVCell = document.getElementsByName('tableHVCell');
		var selectedTableHVCellIds = '';
		for(i=0;i<tableHVCell.length;i++){
			if(tableHVCell[i].checked){
				selectedTableHVCellIds += tableHVCell[i].text+"|";
			}
		}
    	return selectedTableHVCellIds;
    }
    
</script>


</head>
<body>


	<s:form action="/reportConfig/reportConfig_saveHVPart.action" method="post" theme='simple'  onsubmit="alert(getSelectedCellHVIds());return false;">

		<table align='center' class='seeReportTable'><s:text name="seeDataFromAllTableHTML"/></table>
	</s:form>
</body>
</html>