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
    function getUserInputValue(){
    	var hQueryCondition = '';
    	var hQueryConditionText = '';
		var elements = document.forms['queryConditionForm'];
		for(var i=0;i<elements.length;i++){
			
			var textName = elements[i].name;
			if(textName!=''){
				var textValue = elements[i].value;
				if(textValue==''){
					
				}else{
					hQueryConditionText += document.getElementById(textName).value+":"+textValue+",<br>";
					hQueryCondition += textName+'='+textValue+'|';
				}
			}
	
			
			
			/*
		   //alert(elements[i].type);
		   switch(elements[i].type){ //对于不同的元素做不同的处理
		     case "text":
			   str += '<input type="text" name="'+elements[i].name+'" value="' + elements[i].value + '"/>';
			   break;
			 default:break;
		   }
		   */
		}
		return hQueryCondition+"######"+hQueryConditionText;
    }
    
    //初始化已经设置的查询条件
    function initItem(){
    	var selectQueryCondition = parent.getHaveSetItemValue();
    	var selectQueryConditions = selectQueryCondition.split('|');
    	var haveSetItemLength = selectQueryConditions.length;
    	
    	for(i=0;i<haveSetItemLength;i++){
    		var hasSetitem = selectQueryConditions[i];
    		if(hasSetitem.indexOf('=')!=-1){
    			var items = hasSetitem.split('=');
    			var key = items[0];
    			var value = items[1];
    			var elements = document.forms['queryConditionForm'];
    			for(var j=0;j<elements.length;j++){
    				var textName = elements[j].name;
    				if(textName==key){
    					elements[j].value = value;
    				}
    			}
    			
    		}
    	}
    }
    
</script>


</head>
<body onload='initItem();'>


	<s:form action="" method="post" id='queryConditionForm' name='queryConditionForm'>

		<table align='center' class='seeReportTable'><s:text name="queryConditonHtml"/></table>
	</s:form>
</body>
</html>