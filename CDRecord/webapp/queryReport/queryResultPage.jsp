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

<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />

<script type="text/javascript">  
	
    $.subscribe('beforeSubmit', function(event,data) { 
    	var validatorResult = validateForm();  
    	if(validatorResult){  
    	    event.originalEvent.options.submit = true;  
    	}else{  
    	    event.originalEvent.options.submit = false;  
    	}   
    });  
    
    $.subscribe('submitForm', function(event,data) {  
	     var json = $.parseJSON(event.originalEvent.request.responseText); 
	     $("#actionMessage").html("");//先将上次认证的错误消息清除掉  
	     if(json.actionMessages && json.actionMessages.length>0){
			$.each(json.actionMessages,function(index,data){
				alert(data);
				var dataBindid = json.dataReportModel.bindId;
				var href = window.location.href;
				if(href.indexOf('dataReportModel.bindId')!=-1){
					window.location.reload();
				}else{
					href = href+"&dataReportModel.bindId="+dataBindid;
					window.location.replace(href);
				}

			});  
	         return;  
	     }  
    }); 
    

    function validateForm(){
		var reportname = $('#reportDataSave_dataReportModel_reportName').val();
		if(reportname==''){
			alert("报表名称不能为空");
			return false;
		}
		
    	return true;
    }
    
	//导出excel文件
    function downloadExcelData(reportBindid){
    	open(content_path+'/file/excelData.do?reportBindid=${dataReportModel.bindId}');
    }
    
	//回退
	function setReportToBack(bindid){
    	var setAppearDepartment = {
       			url:"<%=CONTEXT_PATH %>/reportData/reportData_setReportToBack.do?dataReportModel.bindId="+bindid,
       			callback:function(options,success,response){//回调函数
       				
       				var msg = ["请求是否成功：" ,success,"\n",
       							"服务器返回值：",response.responseText,
       							"本地自定义属性：",options.customer];
       				alert(response.responseText);
       				window.location.reload();
       			}
        	};
       		Ext.Ajax.request(setAppearDepartment);//发送请求
	}
	
	//上报
	function setReportToPublish(bindid){
    	var setAppearDepartment = {
       			url:"<%=CONTEXT_PATH %>/reportData/reportData_setReportToPublish.do?dataReportModel.bindId="+bindid,
       			callback:function(options,success,response){//回调函数
       				
       				var msg = ["请求是否成功：" ,success,"\n",
       							"服务器返回值：",response.responseText,
       							"本地自定义属性：",options.customer];
       				alert(response.responseText);
       				window.location.reload();
       			}
        	};
       		Ext.Ajax.request(setAppearDepartment);//发送请求
	}
	
	


</script>
</head>
<body>
<table width=100%>
	<s:form action="/reportData_json/reportDataSave.action" method="post" theme='simple'>
	<tr>
		<td>
			<s:hidden name="dataReportModel.repotBindid"></s:hidden>
			<s:hidden name="dataReportModel.bindId"></s:hidden>
			<s:hidden name="dataReportModel.id"></s:hidden>
			<s:hidden name="dataReportModel.madeManName"></s:hidden>
			<s:hidden name="dataReportModel.madeManDate"></s:hidden>
			<s:hidden name="dataReportModel.createDepBindid"></s:hidden>
		</td>
	</tr>
	</s:form>
</table>



	

	<div>
		<button onclick='downloadExcelData();return false;' class='cdrecordButton'>导出excel文件</button>
	</div>
	<div align="center" style="margin-top: 10px; margin-bottom: 20px;">
		<h2><s:property value="dataReportModel.year"/>年<s:property value="dataReportModel.periodsName"/>
		<s:property value="dataReportModel.reportName"/>(版本号:<s:property value="dataReportModel.reportVersion"/>)</h2>
	</div>
	<div style="width: 80%; margin-left: auto; margin-right: auto;">
		<div style="margin-bottom: 5px;">
			部门:<s:property value="department.departmentName"/>  
			上报日期：<s:property value="department.appearDate"/>  
		</div>
		<table width=100% align='center' class='seeReportTable'><s:text name="queryResultHtml"/></table>
		<div style="margin-top: 5px;">
			编制人:<s:property value="dataReportModel.madeManName2"/> 编制日期:<s:property value="dataReportModel.madeManDate"/><br> 
			审核人：<s:property value="dataReportModel.verifyManName2"/> 审核日期：<s:property value="dataReportModel.verifyManDate"/>
		</div>
	</div>













</body>
</html>