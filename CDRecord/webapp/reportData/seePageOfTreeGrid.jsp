<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!-- 报表填报页面 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:property value="dataReportModel.year"/>年<s:property value="dataReportModel.periodsName"/><s:property value="dataReportModel.reportName"/></title>
<%@ include file="/common/comm.jsp" %>
<script type="text/javascript" src="<%=EASYUI_PATH %>/jquery.edatagrid.js"></script>
</head>
<body class="easyui-layout"><!--    /reportData_json/reportDataSave.action -->

<div data-options="region:'north',split:true" style="height:80px;">
	<form id="reportDataSave" action="/reportData/reportData_saveReportOfGrid.action" method="post">
	<h2>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<s:if test="reportConfigModel.rttype==207"><!-- 天报表 -->
				<sx:datetimepicker name="dataReportModel.yearOfDateReport" id="dataReportModel.yearOfDateReport" cssClass="cdrecordInput" displayFormat="yyyy-MM-dd"/> 
			</s:if>
			<s:elseif test="reportConfigModel.rttype==201"><!-- 年报表 -->
				<s:property value="dataReportModel.periods"/>
			</s:elseif>
			<s:else>
				<s:property value="dataReportModel.year"/>年
				<s:property value="dataReportModel.periodsName"/>
			</s:else>
			
			<s:property value="dataReportModel.reportName"/>
			(版本号:<s:property value="dataReportModel.reportVersion"/>)
		</h2>
			
			<div style="width: 99%; margin-left: auto; margin-right: auto;">
				<div style="margin-bottom: 2px;">
					部门:<s:property value="dataReportModel.createDepName"/>&nbsp;&nbsp;&nbsp;
					编制人:<s:property value="dataReportModel.madeManName2"/>&nbsp;&nbsp;&nbsp;
					编制日期:<s:date name="dataReportModel.madeManDate" format="yyyy-MM-dd"></s:date>
				</div>
			</div>
			
			<s:hidden name="dataReportModel.repotBindid"></s:hidden>
			<s:hidden name="dataReportModel.bindId"></s:hidden>
			<s:hidden name="dataReportModel.id"></s:hidden>
			<s:hidden name="dataReportModel.madeManName"></s:hidden>
			<s:hidden name="dataReportModel.madeManDate"></s:hidden>
			<s:hidden name="dataReportModel.createDepBindid"></s:hidden>
			<s:hidden name="dataReportModel.verify"></s:hidden>
	</form>
	
	<div style="position: absolute; right: 1px; top: 0px;" id="setting">
		 <div style="padding:5px;border:1px solid #ddd;">
		 	<s:if test="reportConfigModel.type==342"><!-- 汇总表 -->
				<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-grasp-data'" onclick="appearReport();return false;">抓取数据</a>
			</s:if>
			<s:else><!-- 填报表 -->
				<s:if test="user.roleId=='972'"><!-- 审核人 -->
					<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-shenhe'" onclick="setReportToBack('<s:property value="dataReportModel.bindId"/>');return false;">回退</a>
					<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-file-lock'" onclick="setReportToPublish('<s:property value="dataReportModel.bindId"/>');return false;">上报</a>
				</s:if>
				<s:elseif test="user.roleId=='971'"><!-- 报表编制人 -->
					<a href="#" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" onclick="setReportToPublish('<s:property value="dataReportModel.bindId"/>');return false;">修改</a>
				</s:elseif>
			</s:else>
		</div>
	</div>

</div>
<div data-options="region:'center'" style="padding:2px;background:#eee;">
	<s:text name="reportTableHtmlCode"/>
</div>

<script type="text/javascript">

var toolbar = [{
		text:'导出数据',
		iconCls:'icon-excel',
		handler:function(){refreshData();}
	},{
		text:'刷新',
		iconCls:'icon-reload',
		handler:function(){refreshData();}
	},'-',{
		text:'合并子项',
		iconCls:'icon-list-coll',
		handler:function(){collapseAll()}
	},{
		text:'展开子项',
		iconCls:'icon-list-expand',
		handler:function(){expandAll()}
	}];

//递归调用 所有行
function allRowHandler(items, action) {
	$.each(items, function(i, row) {
		action(i, row);

		if (row.children) {
			allRowHandler(row.children, action);
		}
	});
}

//表单中添加 单元格数据
function addItemCellToForm(){
	var rows = $('#roleEditGrid').treegrid('getData');
	allRowHandler(rows, function(i, row) {
		$('#roleEditGrid').treegrid('endEdit', row.id);
		var subItems = row.submitItemIds;
		if (subItems != '') {
			var headerItemIds = subItems.split('|');
			$.each(headerItemIds, function(i, headerId) {
				if (headerId) {
					var Itemname = headerId + "#" + row.id;
					var Itemvalue = row[headerId];
					$('<input />').attr('type', 'hidden').attr('name',
							Itemname).attr('value', Itemvalue).appendTo(
							'#reportDataSave');
				}
			});
		}
	});
}

//设置文件上传中 期数 ，否则上传文件时，期数不能保存
function setExceldataInputFromOfPeriods(){
	var dataReportModel_periods = $("#dataReportModel_periods").combobox('getValue');
	document.getElementById("upForm_dataReportModel_periods").value=dataReportModel_periods;
}
//设置文件上传中 期数 ，否则上传文件时，期数不能保存
function setExceldataInputFromOfYear(){
	var dataReportModel_year = $("#dataReportModel_year").combobox('getValue');
	document.getElementById("upForm_dataReportModel_year").value=dataReportModel_year;
}
//文件上传回调函数,根据返回的databindid，刷新页面
function callback(dataBindid){   
    window.location.href = content_path+'/reportData/reportData_inputPageOfTreeGrid.do?dataReportModel.repotBindid=${dataReportModel.repotBindid}&dataReportModel.bindId='+dataBindid;
}

	var reportModelBindid = '<s:text name="dataReportModel.repotBindid"/>';
	var dataBindid = '<s:text name="dataReportModel.bindId"/>'; 
	//刷新数据
	function refreshData(){
		
	}
	
	//合并子项
	function collapseAll(){
		$('#roleEditGrid').treegrid('collapseAll');
	}
	//展开子项
	function expandAll(){
		$('#roleEditGrid').treegrid('expandAll');
	}
	//撤销修改
	 function cancel(){
		 var rows = $('#roleEditGrid').treegrid('getData');
		 allRowHandler(rows,function(i,row){
				$('#roleEditGrid').treegrid('cancelEdit', row.id);
		 });
	}
	
	//导入数据
	function importExcelData(){
		setExceldataInputFromOfPeriods();
		setExceldataInputFromOfYear();
		$('#exceldataInputFromDiv').dialog('open');

	}
	
	//保存数据
	function saveData() {
		addItemCellToForm();
		$('#reportDataSave').submit();
	}
	 
    //上报报表
    function appearReport(){
    	addItemCellToForm();
    	document.getElementById("reportDataSave").action = content_path+"/reportData/reportData_reportDataVerify.do";
    	document.getElementById("reportDataSave").submit();
    }
    //直接归档
    function publishReport(){
    	addItemCellToForm();
    	document.getElementById("reportDataSave").action = content_path+"/reportData/reportData_reportDataPublish.do";
    	document.getElementById("reportDataSave").submit();
    }
    
    //打开报表选择页面
    function selectReport(){
    	var url = content_path+'/reportConfig/reportConfig_selectReportPage.action';
    	openDialogWin("选择报表",url,100,200);
    	
    	//open(content_path+'/reportConfig/reportConfig_selectReportPage.action');
    }
    //下载excel填报模版
    function exportExcelModel(){
    	open(content_path+'/file/excelModel.do?reportBindid=${dataReportModel.repotBindid}');
    }
	

	$(function() {
		$('#roleEditGrid')
				.treegrid(
						{
							idField : 'id',
							treeField : '<s:text name="rootBindid"/>',
							url : '/reportData/reportData_inputPageDataOfTreeGrid.do?dataReportModel.repotBindid='
									+ reportModelBindid
									+ "&dataReportModel.bindId=" + dataBindid,
							//url : '/testData.jsp',
							saveUrl : '/reportData/reportData_saveReportOfGrid.do',
							updateUrl : '/reportData/reportData_saveReportOfGrid.do',
							destroyUrl : '/role/deleteA/0',
							onSelect : function(rowIndex, rowData) {

							},
							onClickRow : function(row) {
								var subItems = row.submitItemIds;//当前行 可编辑的单元格hbindid
								var rowIndex = row.id;
								/*
								if (row.children) {
									return;
								}
								 */
								$('#roleEditGrid').treegrid('beginEdit',
										rowIndex);
								var dd = $('#roleEditGrid').datagrid(
										'getEditors', rowIndex);
								$.each(dd, function(dIndex, dItem) {
									var hBindid = dItem.field;
									if (subItems.indexOf(hBindid) == -1) {
										$(dItem.target).attr("disabled", true);
									}
									/*
									dItem.target.bind('dispale', function () {
									    return false;

									});
									 */
								})
							}
						});
	});
</script>
</body>
</html>