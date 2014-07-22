<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<!-- 从报表中选择一个单元格，页面为左侧报表树，右侧为对应的报表内容（横向表头、纵向表头、全表）
	
	js接口：
	setCellSelected（），设置默认选择的报表和选中的报表单元格；

	getSelectedCellHVIds（），返回选中的单元格；


 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportConfig.reportName"/></title>
<%@ include file="/common/comm.jsp" %>
<sj:head jquerytheme="cupertino" ajaxcache="false" compressed="false"/>
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>



<script type="text/javascript">

Ext.BLANK_IMAGE_URL = content_path+'/R_framework/ext-3_0_0/resources/images/default/s.gif';

Ext.onReady(
	function(){
		createViewWinPanel();
	}
);


//获得选择的单元格
function getSelectedCellHVIds(){
	var selectedCell = window.frames["selectTableCellPage"].window.getSelectedCellHVIds();
	return selectedCell;
}

//设置已经选择的报表id和单元格列表
function setCellSelected(reportbindid,cells){
	//alert(reportbindid);
	graphSubTableSelectCell = cells;
	graphSubTableBindid = reportbindid;
	//alert(graphSubTableSelectCell);
	//window.frames["selectTableCellPage"].window.setCellSelected(graphSubTableSelectCell);
	
}


//设置单元格被选中
function setCellSelected2(){

	window.frames["selectTableCellPage"].window.setCellSelected(graphSubTableSelectCell);
	
}


var reportPageUrl = content_path+"/reportConfig/reportConfig_selectReportPartPage.action?param3=2&reportConfig.reportLink=";


//报表的linkbindid
var graphSubTableBindid = '';
//报表 选择的单元格
var graphSubTableSelectCell = '';


function createViewWinPanel() {// 
	var currentTreeNode = '';
	var currentPartType = 'h';
	function reloadConfigReport(){
		if(currentTreeNode==''){
			return;
		}
		
		//将#替换为**
		var graphSubTableSelectCell_temp = graphSubTableSelectCell;
		while(graphSubTableSelectCell_temp.indexOf("#", 0)!=-1){
			 graphSubTableSelectCell_temp = graphSubTableSelectCell_temp.replace( "#","**");
		}
		
		var selectTableCellPage2 = document.getElementById('selectTableCellPage');
		selectTableCellPage2.src = reportPageUrl+currentTreeNode.id+"&param1="+currentPartType+"&param2="+graphSubTableSelectCell_temp;
		
		setTimeout("setCellSelected2()",500); 
	
	}
	
	function reportTreeWithYear(){
		
		var yearConfigListUtl = content_path+"/reportConfig_json/configYearList.do";
		var reportConfigListUtl = content_path+"/reportConfig_json/configReportListJson.do?reportConfig.rtyear=";
		var Tree = Ext.tree;
		var isfirst = true;
		var currentNode;
		
		var reportTree = new Tree.TreePanel({
			autoHeight : true,
			animate : true,
			enableDD : true,// 不允许子节点拖动
			containerScroll : true,
			rootVisible : false,
			expand:true,
			loader : new Tree.TreeLoader({
				dataUrl : yearConfigListUtl
			}),
			root:new Tree.AsyncTreeNode({
				text : '123',
				draggable : false,
				id : '0'
			}),
			listeners:{
				click:function(node) {
					this.currentNode = node;
					var nodeId = node.id;
					if(nodeId.indexOf('-')>=0){
						currentTreeNode = node;
						reloadConfigReport();
					}else{
						//alert('年份');
					}
				},
				beforeload:function(node) {
					if(isfirst){
						reportTree.loader.dataUrl = yearConfigListUtl;
						isfirst = false;
					}else{
						reportTree.loader.dataUrl = reportConfigListUtl+node.id;
					}
				},
				load:function(node) {//加载子节点后，选中子节点的第一个节点
					var me = this;
					if(node.childNodes.length!=0){
						Ext.each(node.childNodes, function(cNode) {
							if(cNode.id==graphSubTableBindid){
								reportTree.selectPath(cNode.getPath());
								me.currentNode = cNode;
								currentTreeNode = cNode;
								reloadConfigReport();
							}
					});
					}
				}
			}
		});
		reportTree.getRootNode().expand(true);//自动加载所有节点
		return reportTree;
	}
	var radiogroup = new Ext.form.RadioGroup({
		fieldLabel : "radioGroup",
		name : 'HorV',
		height : 15,
		items : [ new Ext.form.Radio({
			boxLabel : '纵向表头',
			name : 'HorV',
			inputValue : 'v',
			checked : false
		}), new Ext.form.Radio({
			boxLabel : '横向表头',
			name : 'HorV',
			inputValue : 'h',
			checked : true
		}), new Ext.form.Radio({
			boxLabel : '全表格',
			name : 'HorV',
			inputValue : 'hv',
			checked : false
		}) ],
		listeners : {
			'change' : function(rg, r) {
				currentPartType = r.inputValue;
				reloadConfigReport();//加载数据
			}
		}
	});
	//var reportConfigTree = createReportConfigTree(radiogroup);

	
	var reporttree2 = reportTreeWithYear();
	var pnWest = new Ext.Panel({
		id : 'pnWest',
		title : '报表列表',
		width : 250,
		heigth : 'auto',
		split : true,// 显示分隔条
		region : 'west',
		collapsible : true,
		items : [ reporttree2 ]
	});
	
	

	
	
	var pnCenter = new Ext.Panel(
			{
				region : 'center',
				layout : 'anchor',
				items : [
						radiogroup,

						{
							id : 'iframeHtml',
							html : '<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=auto frameborder=0 width="100%" height=410 src=></iframe>'
						} ]
			});

	var win = new Ext.Viewport(
			{
				layout : "border",
				renderTo : 'main',
				title : "从下级表选择指标",
				items : [ pnWest, pnCenter ]
				
			});

}








</script>
</head>
<body>
<div id='main'></div>
</body>
</html>