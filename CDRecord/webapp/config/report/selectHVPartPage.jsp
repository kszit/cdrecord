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
    
    function setCellSelected(cells){
		var tableHVCell = document.getElementsByName('tableHVCell');
		for(i=0;i<tableHVCell.length;i++){
			var currentValue = tableHVCell[i].value;
			if(cells.indexOf(currentValue)!=-1){
				tableHVCell[i].checked = true;
			}
		}
    }
    
    //选择的单元格的横向和纵向ids
    function getSelectedCellHVValues(){
		var tableHVCell = document.getElementsByName('tableHVCell');
		var selectedTableHVCellIds = '';
		for(i=0;i<tableHVCell.length;i++){
			if(tableHVCell[i].checked){
				selectedTableHVCellIds += tableHVCell[i].title+",";
			}
		}
    	return selectedTableHVCellIds;
    }
    
    function initItem(){
    	if(!parent.getHaveSetSortValue()){
    		return;
    	}
    	var setSortCondition = parent.getHaveSetSortValue();
 			var elements = document.forms['selectReportPartFrom'];
 			for(var j=0;j<elements.length;j++){
 				var textName = elements[j].value;
 				if(textName==setSortCondition){
 					elements[j].checked = true;
 				}
 			}
    }
    
</script>


</head>
<body onload="initItem();">


	<s:form action="/reportConfig/reportConfig_saveHVPart.action" method="post" theme='simple' name='selectReportPartFrom' id='selectReportPartFrom' onsubmit="alert(getSelectedCellHVIds());return false;">

		<table align='center' class='seeReportTable'><s:text name="seeDataFromAllTableHTML"/></table>
	</s:form>
</body>
</html>