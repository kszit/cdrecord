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

<sx:head extraLocales="UTF-8"/>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH%>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH%>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH%>/ext-all-debug.js"></script>
<script type="text/javascript" src='<%=JS_FRAMWORK_PATH%>/My97DatePicker/WdatePicker.js'></script>
<script type="text/javascript" src='<%=JS_PATH%>/common/popDiv.js'></script>
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH%>/public.css" />
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH%>/displaytag.css" />

<script type="text/javascript">  
	/*
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
					//window.location.replace(href);
					window.location.href=href;
				}
			});  
	         return;  
	     }
    }); 
    */

    function validateForm(){
		var reportname = $('#reportDataSave_dataReportModel_reportName').val();
		if(reportname==''){
			alert("报表名称不能为空");
			return false;
		}
    	return true;
    }
    
    //上报报表
    function appearReport(){
    	document.getElementById("reportDataSave").action = content_path+"/reportData/reportData_reportDataVerify.do";
    	document.getElementById("reportDataSave").submit();
    }
    //直接归档
    function publishReport(){
    	document.getElementById("reportDataSave").action = content_path+"/reportData/reportData_reportDataPublish.do";
    	document.getElementById("reportDataSave").submit();
    }
    
    //打开报表选择页面
    function selectReport(){
    	open(content_path+'/reportConfig/reportConfig_selectReportPage.action');
    }
    //下载excel填报模版
    function exportExcelModel(reportBindid){
    	open(content_path+'/file/excelModel.do?reportBindid=${dataReportModel.repotBindid}');
    }
    
    var setUserDepartmentWin = null;
    //import Excel文件数据
    function importExcelData(){
    	//var userInputDate = dojo.widget.byId("dataReportModel.yearOfDateReport").inputNode.value;
    	//document.getElementById("dataReportModel_yearOfDateReport").value=userInputDate;
    	
    	if(!setUserDepartmentWin){
        	setUserDepartmentWin = new Ext.Window({  
                applyTo:'hello-win',  
                layout:'fit',  
                width:500,  
                height:300,  
                closeAction:'hide',
                plain: true,  
      			modal:true,
      			html:Ext.getDom('exceldataInputFromDiv').innerHTML,
                buttons: [{
                    text: '取消',
                    handler: function(){
                    	setUserDepartmentWin.hide();
                    }
                }]
            });
    	}
    	setUserDepartmentWin.show();
    }
	//由报表选择页面调用
    function afterSelectReport(reportBindid){
    	window.location.href = content_path+'/reportData/reportData_inputPage.do?dataReportModel.repotBindid='+reportBindid;
    }
    
    //文件上传回调函数,根据返回的databindid，刷新页面
    function callback(dataBindid){   
        window.location.href = content_path+'/reportData/reportData_inputPage.do?dataReportModel.repotBindid=${dataReportModel.repotBindid}&dataReportModel.bindId='+dataBindid;
    }
    //设置文件上传中 期数 ，否则上传文件时，期数不能保存
    function setExceldataInputFromOfPeriods(){
    	var dataReportModel_periods = document.getElementById("reportDataSave_dataReportModel_periods").value;
    	document.getElementById("dataReportModel_periods").value=dataReportModel_periods;
    }
    //设置文件上传中 期数 ，否则上传文件时，期数不能保存
    function setExceldataInputFromOfYear(){
    	var dataReportModel_year = document.getElementById("reportDataSave_dataReportModel_year").value;
    	document.getElementById("dataReportModel_year").value=dataReportModel_year;
    }
    
    
    //提示信息
    function promptMessageOfPage(){
    	/* var promptMessage = <s:property value="promptMessage"/>+"";
    	if(promptMessage!=""){
    		alert(promptMessage);
    	} */
    }
    
    var userPicWin;
    var currentPic;
    var currentPicHiddenFiled;
    //图片附件上传
    function picFileUpload(id){
    	currentPic = document.getElementById(id+"Img");
    	currentPicHiddenFiled = document.getElementById(id);
    	
    	if(!userPicWin){
    		userPicWin = new Ext.Window({  
                applyTo:'hello-win',  
                layout:'fit',  
                width:500,  
                height:300,  
                closeAction:'hide',
                plain: true,  
      			modal:true,
      			html:Ext.getDom('userPicInputFromDiv').innerHTML,
                buttons: [{
                    text: '取消',
                    handler: function(){
                    	userPicWin.hide();
                    }
                }]
            });
    	}
    	userPicWin.show();
    }
  //文件上传回调函数
    function userPicFileUpCallback(filePath){   
    	currentPic.src = filePath;
    	currentPicHiddenFiled.value = filePath;
    }
    
</script>


</head>
<body onload="promptMessageOfPage()">
	 <!-- excel 数据上传并导入数据时，使用 -->
	<iframe name='hidden_frame' id="hidden_frame" style='display:none'></iframe>  
    <!-- 弹出的文本输入框 -->
    <div class="box" id="box" style='display:none;'>
        <div class="quit radius_s" id="quit">关闭</div>
        <div class="clear_13"></div>
        <div class="item" id="item">
            <textarea name="popDivText" id="popDivText" rows="7" cols="53">
            </textarea>
        </div>
    </div>
    <div id='hello-win'></div>
    <!-- excel数据文件上传 -->
    <div id='exceldataInputFromDiv' style='display:none'>
		<form target="hidden_frame" id="exceldataInputFrom"
			name="exceldataInputFrom"
			action="<%=CONTEXT_PATH%>/file/excelModelUpload.do"
			enctype="multipart/form-data" method="post">
			文件:<input type="file" name="excelFile" id="excelFile" />
			<s:hidden name="dataReportModel.repotBindid"></s:hidden>
			<s:hidden name="dataReportModel.bindId"></s:hidden>
			<s:hidden name="dataReportModel.id"></s:hidden>
			<s:hidden name="dataReportModel.madeManName"></s:hidden>
			<s:hidden name="dataReportModel.madeManDate"></s:hidden>
			<s:hidden name="dataReportModel.createDepBindid"></s:hidden>
			<s:hidden name="dataReportModel.periods"></s:hidden>
			<s:hidden name="dataReportModel.year"></s:hidden>
			<s:hidden name="dataReportModel.verify"></s:hidden>
			<s:hidden name="dataReportModel.yearOfDateReport"></s:hidden>
			<input type="submit" value="确定" />
		</form>
	</div>
	
	<!-- 图片上传 -->
    <div id='userPicInputFromDiv' style='display:none'>
		<form target="hidden_frame" id="userUploadPic"
			name="userUploadPic"
			action="<%=CONTEXT_PATH%>/file/userPicUpload.do"
			enctype="multipart/form-data" method="post">
			文件:<input type="file" name="excelFile" id="excelFile" />
			<input type="submit" value="确定" />
		</form>
	</div>
	<!--  -->
	<div id='show'></div>
	<div id='actionMessage'></div>
	<div id='targetDiv'></div>
	
	
	<s:form action="/reportData_json/reportDataSave.action" method="post" theme='simple'>
		<ul id="errorMessages"></ul>
		<s:hidden name="dataReportModel.repotBindid"></s:hidden>
		<s:hidden name="dataReportModel.bindId"></s:hidden>
		<s:hidden name="dataReportModel.id"></s:hidden>
		<s:hidden name="dataReportModel.madeManName"></s:hidden>
		<s:hidden name="dataReportModel.madeManDate"></s:hidden>
		<s:hidden name="dataReportModel.createDepBindid"></s:hidden>
		<s:hidden name="dataReportModel.verify"></s:hidden>
	<div>
		<button onclick='selectReport();return false;' class='cdrecordButton'>选择报表</button>
		<button onclick='appearReport();return false;' class='cdrecordButton'>提交审核</button>
		<button onclick='publishReport();return false;' class='cdrecordButton'>直接归档(上报)</button><br><br>
        <button onclick='exportExcelModel();return false;' class='cdrecordButton'>下载模版</button>
        <button onclick='importExcelData();return false;' class='cdrecordButton'>上传数据</button>
        <button onclick='window.reload();return false;' class='cdrecordButton'>重新获取数据</button>
		<s:submit cssClass="cdrecordButton" beforeSubmit='return validateForm();' value="保存"/>
	</div>
	
	<div align="center" style="margin-top: 10px; margin-bottom: 20px;">
		<h2>
		<s:if test="reportConfigModel.rttype==207">
			<sx:datetimepicker name="dataReportModel.yearOfDateReport" id="dataReportModel.yearOfDateReport" cssClass="cdrecordInput" displayFormat="yyyy-MM-dd"/> 
		
		</s:if>
		<s:elseif test="reportConfigModel.rttype==201">
		
			<s:select cssClass="cdrecordInput" onchange='setExceldataInputFromOfPeriods();'
					name='dataReportModel.periods'
			       	list="periodList"
			       	listKey="bindId"
			       	listValue="name"
			       	value="dataReportModel.periods"
		      	/>
		</s:elseif>
		<s:else>
			<s:select cssClass="cdrecordInput" onchange='setExceldataInputFromOfYear();'
						name='dataReportModel.year'
				       	list="yearList"
				       	listKey="name"
				       	listValue="name"
				       	value="dataReportModel.year"
			      	/>年
			<s:select cssClass="cdrecordInput" onchange='setExceldataInputFromOfPeriods();'
					name='dataReportModel.periods'
			       	list="periodList"
			       	listKey="bindId"
			       	listValue="name"
			       	value="dataReportModel.periods"
		      	/>
		</s:else>
		
			<s:property value="dataReportModel.reportName"/>
			(版本号:<s:property value="dataReportModel.reportVersion"/>)</h2><br>
			<s:property value="promptMessage"/>
	</div>
	<div style="width: 80%; margin-left: auto; margin-right: auto;">
		<div style="margin-bottom: 5px;">
			部门:<s:property value="dataReportModel.createDepName"/>&nbsp;&nbsp;&nbsp;
			编制人:<s:property value="dataReportModel.madeManName2"/>&nbsp;&nbsp;&nbsp;
			编制日期:<s:date name="dataReportModel.madeManDate" format="yyyy-MM-dd"></s:date>
		</div>
		<table align='center' class='seeReportTable'><s:text name="reportTableHtmlCode"/></table>
	</div>
	</s:form>
</body>
</html>