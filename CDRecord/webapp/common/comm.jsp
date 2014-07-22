<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 

String CONTEXT_PATH = request.getContextPath(); 
String IMG_PATH = CONTEXT_PATH + "/image";
String JS_PATH = CONTEXT_PATH + "/js"; 
String CSS_PATH = CONTEXT_PATH + "/css";
String JS_FRAMWORK_PATH = CONTEXT_PATH + "/R_framework";
String EXTJS3_PATH = CONTEXT_PATH + "/R_framework/ext-3_0_0";
String EASYUI_PATH = CONTEXT_PATH + "/R_framework/easyui";
String FUNCTIONCHARS_PATH = CONTEXT_PATH + "/R_framework/functioncharts";


%> 
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<script type="text/javascript">
    var content_path = '<%=CONTEXT_PATH %>';
</script>

	<link rel="stylesheet" type="text/css" href="<%=EASYUI_PATH %>/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=EASYUI_PATH %>/themes/icon.css">
	<script type="text/javascript" src="<%=EASYUI_PATH %>/jquery.min.js"></script>
	<script type="text/javascript" src="<%=EASYUI_PATH %>/jquery.easyui.min.js"></script>
	
	<script type="text/javascript">
	//公共方法，打开弹出窗口
		function openDialogWin(title,url,width,height){
			$('#popDialog').dialog({title:title});
			$('#popDialog').dialog('refresh',url);
			$('#popDialog').dialog('open');
		}
	</script>
	
	<!-- 弹出框 -->
	<div id="popDialog" class="easyui-dialog" title="" data-options="iconCls:'icon-save',modal:true,closed:true" 
		style="width:700px;height:400px;padding:10px;">
    </div>