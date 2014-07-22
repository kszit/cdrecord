<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kszit.CDReport.cor.model.graph.GraphConfigSubModel"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/common/comm.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>

<script type="text/javascript" src="<%=JS_PATH %>/queryReport/selectReportOfAppear.js"></script>
<script type="text/javascript" src="<%=JS_PATH %>/queryReport/setQueryCondition.js"></script>
<script type="text/javascript" src="<%=JS_PATH %>/queryReport/setSort.js"></script>

<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />

<script type="text/javascript">
var reportbindId = '<s:text name="reportConfig.bindId"/>';
var version = '<s:text name="reportConfig.rtversion2"/>';

//设置单元格的默认值
function setNewDefaultValue(id,value){
	document.getElementById(id).innerHTML = value;
	$('#orgDialog').dialog('close');
}

function openCellDefaultValueSetWin(reportBinid,hBindid,vBindid){
	var hight = 500;
	var width = 500;
	var title = "单元格默认值";
	var url = content_path+'/reportConfig/cellDefaultValue_cellDefaultValueForm.do?cellValue.reportBindid='+reportBinid+'&cellValue.hbindid='+hBindid+'&cellValue.vbindid='+vBindid;
	openDialogWin(title,url,hight,width);
}



//==========================================================================单元格 数据关系 配置调用方法
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
	var reportbindid = document.getElementById('reportConfig.bindId').value;
	var dataFromType = window.opener.document.getElementById('dataFromSaveH_headerRowModel_dataFrom').value;
	var nameAndValue = cell.value.split('|');
	window.opener.addCellItem(nameAndValue[0],reportbindid+"#"+nameAndValue[1]+"#"+dataFromType,cell.checked);
}

var activeConfigDialog;
if (!activeConfigDialog) {
    activeConfigDialog = new Ext.Window({
      
      layout : 'fit',
      title : 'UI组件配置',
      width : 800,
      height : 410,
      plain : true,
      closable : false,
      closeAction : 'hide',
      resizable : false,
      modal : true,
      items : [{
        html : '<iframe name=activeConfigDlgPage id=activeConfigDlgPage frameborder=0 width=100% height=100% src=../aws_html/load.htm></iframe>'
      }],
      buttons : [{
        text : '应用',
        handler : function() {
        	//提交子页面的表单，ajax方式
        	document.getElementById('activeConfigDlgPage').contentWindow.submitForm();
        }
      }, {
        text : '关闭',
        handler : function() {
        	activeConfigDialog.hide();
        }
      }]
    });
  }
      
//弹出窗口
function openUIConfig(url) {
	var pagename='';
	var height = 600;
	var width = 1000;
		
	activeConfigDialog.setTitle(pagename);
	activeConfigDialog.purgeListeners();
	activeConfigDialog.addListener('show', function() {
		activeConfigDlgPage.location = url;
		activeConfigDialog.setHeight(height);
		activeConfigDialog.setWidth(width);
		this.center();
		return;
	});
	activeConfigDialog.show();
}
 
function openCellDataFromSetWin(reportBinid,hBindid,vBindid){
	
	openUIConfig(content_path+'/reportConfig/dataFromConfig_oneCellDateFromConfigPage.do?cellDataFrom.reportBindid='+reportBinid+'&cellDataFrom.hbindid='+hBindid+'&cellDataFrom.vbindid='+vBindid);
}







//公共方法，打开弹出窗口
function openDialogWin(title,url,width,height){
	$('#orgDialog').dialog({title:title});
	$('#orgDialog').dialog('refresh',url);
	$('#orgDialog').dialog('open');
}








</script>
</head>
<body>

<!-- 弹出框 -->
<div id="orgDialog" class="easyui-dialog" title="" data-options="iconCls:'icon-save',modal:true,closed:true" 
style="width:700px;height:400px;padding:10px;">
</div>

<div class="easyui-tabs" style="width:1000px;height:800px">
  		<div title="数据关系配置" data-options="href:'<%=CONTEXT_PATH %>/reportConfig/reportConfig_seeDataFromAllTablePage.do?reportConfig.bindId=<s:text name="reportConfig.bindId"/>&reportConfig.rtversion2=<s:text name="reportConfig.rtversion2"/>',closable:true" style="padding:10px">
        </div>
        
        <div title="单元格默认值配置" data-options="href:'<%=CONTEXT_PATH %>/reportConfig/reportConfig_seeCellDefaultValuePage.do?reportConfig.bindId=<s:text name="reportConfig.bindId"/>&reportConfig.rtversion2=<s:text name="reportConfig.rtversion2"/>',closable:true" style="padding:10px">
        </div>
        
</div>
</body>
</html>