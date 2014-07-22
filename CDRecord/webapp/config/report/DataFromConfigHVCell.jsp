<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page
	import="java.util.*,com.kszit.CDReport.cor.dao.hibernate.po.DictionaryPO"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportConfig.reportName" /></title>
<%@ include file="/common/comm.jsp"%>
<sj:head jquerytheme="cupertino" ajaxcache="false" compressed="false" />

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>

<script type="text/javascript">

var TableInFormulaHCountBindId = 114;//表内横向计算
var SamePeriodLastYear = 120;//去年同期数据
var BrothersReportData = 121;//兄弟表数据

	function submitForm() {
		

		//var dataRule = getDataRule();
		//var dataItems = getDataItems();
		
		document.getElementById('cellDataFrom_formula').value = '1';
		document.getElementById('cellDataFrom_dataTerm').value = '12';
		
		var dataFrom = document.getElementById('dataFromSaveH');
		Ext.Ajax.request({form:dataFrom, success:function(){alert('更新成功');}});

		return false;
	}
	//选择单元格页面
	function openSelectCellPage() {
		var bindId = document
				.getElementById('headerRowModel_bindId').value;
		//var dataFromType = document
		//		.getElementById('dataFromSaveH_headerRowModel_dataFrom').value;
		var dataFromType = getSelectedDataFrom();
		var seletedItems = document
				.getElementById('headerRowModel_dataRuleItems').value;
		var selectedItems2 = escape(seletedItems);
		var reportBindid = document
				.getElementById('headerRowModel_reportBindid').value;
		
		/*
		window
				.open(content_path
						+ '/reportConfig/reportConfig_selectCellPage.do?headerRowModel.reportBindid='
						+ reportBindid + '&headerRowModel.dataFrom='
						+ dataFromType + '&headerRowModel.dataRuleItems='
						+ selectedItems2 + '&headerRowModel.bindId=' + bindId);
		*/
		window
		.open(content_path
				+ '/reportConfig/reportConfig_selectReportPartPage.do?reportConfig.reportLink='
				+ reportBindid + '&param1=h&param2=');
		
	}
	function addCellItem(name, value, ischeked) {
		var oSelect1 = document.all.selectcells;
		if (ischeked) {
			var addoption1 = new Option(name, value, false, false);
			oSelect1.options[oSelect1.options.length] = addoption1;
		} else {
			for ( var i = 0; i < oSelect1.length; i++) {
				if (oSelect1[i].value == value) {
					oSelect1.remove(i);
				}
			}
		}
	}
	

	
	
	
	
	
	
	
	//===============================================
			
	function copyFromTableReport () {  //移动文章到指定栏目的调用方法
        var viewWinPanel = createViewWinPanel();
        viewWinPanel.show();
        //var form = viewWinPanel.getComponent(0).form;
   
    }
	function createViewWinPanel() {//移动文章到指定栏目的窗口
		
		getSelectedDataFrom();
		
		
		var adminRadio=new Ext.form.Radio({
	        boxLabel:'纵向为主',
	        name:'HorV',
	        inputValue:'admin',
	        checked:true,
	        listeners:{
	            'check':function(){
	                //alert(adminRadio.getValue());
	                if(adminRadio.getValue()){
	                    userRadio.setValue(false);
	                    adminRadio.setValue(true);
	                }
	            }
	        }
	    });
	    var userRadio=new Ext.form.Radio({
	        boxLabel:'横向为主',
	        name:'HorV',
	        inputValue:'user',
	        listeners:{
	            'check':function(){
	                if(userRadio.getValue()){
	                    adminRadio.setValue(false);
	                    userRadio.setValue(true);
	                }
	            }
	        }
	    });
		
		var radiogroup= new Ext.form.RadioGroup({
            fieldLabel : "radioGroup",
            name:'HorV',
            height:15,
            items : [adminRadio,userRadio ],
            dd:function(){
            	alert(userRadio.getValue());
            	alert(adminRadio.getValue());
            	alert(this.getValue());
            }
        });
		
		var reportConfigTree = createReportConfigTree(radiogroup);
		
		var reporttree2 = reportTreeWithYear(reportConfigTree);
		var pnWest=new Ext.Panel({  
		    id:'pnWest',  
		    title:'报表列表',  
		    width:250,  
		    heigth:'auto',  
		    split:true,//显示分隔条  
		    region:'west',  
		    collapsible:true,
		    items:[  
		           reporttree2
			    ] 
		});  
		var pnCenter=new Ext.Panel({  
		    region:'center',
		    layout:'anchor',
		    items:[  
radiogroup,

{	
    html : '<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=yes frameborder=0 width=100% height=385 src=></iframe>'
  } 
			    ] 
		}); 
		
		
        var win = new Ext.Window({
        	layout:"border",  
            width: 800,
            height: 400,
            modal: true,
            closeAction: 'hide',
            buttonAlign: "center",
            title: "从下级表选择指标",
		    items:[  
		           	pnWest,
		           	pnCenter
			    ] ,
			    buttons:[{
		            text:'确定',
		            handler:function(button,evn){
		            	var selectedCell = window.frames["selectTableCellPage"].window.getSelectedCellHVIds();
		            	if(selectedCell==''){
		            		alert("请选择一列");
		            		return ;
		            	}
		            	var seletctDataFromType = getSelectedDataFrom();
		            	if(seletctDataFromType==SamePeriodLastYear){//去年同期的数据
		            		var selectedData = selectedCell.split('|')[0];
			            	selectedData = selectedData.replace("$","|");
			            	document.getElementById('headerRowModel_DataRule').value = selectedData;
		            	}else if(seletctDataFromType==BrothersReportData){//兄弟表数据
		            		var selectedData = selectedCell.split('|')[0];
			            	selectedData = selectedData.replace("$","|");
			            	document.getElementById('headerRowModel_DataRule').value = selectedData;
		            	}else if(seletctDataFromType==TableInFormulaHCountBindId){//表内横向计算
		            		var obj = document.getElementById('selectcells');
		            		var selectedDatas = selectedCell.split('|');
		            		var selectedDatas_length = selectedDatas.length-1;
		            		for(i=0;i<selectedDatas_length;i++){
		            			var selectData = selectedDatas[i];
		            			selectData = selectData.replace("$","#")+"#0#0";
		            			obj.options.add(new Option(selectData, selectData));
		            		}
		            	}
		            }
		        },
		        {
		            text:'取消',
		            handler:function(button,evn){
		            	win.hide();
		            }
		        }] 
        });
        return win;
    }
	
	
	
	
	function createReportConfigTree(radiogroup){
		var reportConfigTree = new Ext.tree.TreePanel({
			title : 'My Task List',
			useArrows : true,
			autoScroll : true,
			animate : true,
			enableDD : true,
			containerScroll : true,
			rootVisible : true,
			root : new Ext.tree.AsyncTreeNode({
				text : 'XXX集团',
				draggable : false,
				id : '0'
			}),
			listeners : {
				'checkchange' : function(node, checked) {
					if (checked) {
						node.getUI().addClass('complete');
					} else {
						node.getUI().removeClass('complete');
					}
				}
			},
			myReloadDate:function(nodeId){
				alert(radiogroup.dd());
				this.on('beforeload', function(node) {
					//alert("232:"+nodeId);
					this.loader.dataUrl = content_path+'/reportConfig_json/headerConfigFromOtherHVPageTreeDate.do?reportid='+nodeId+'&parentid='+node.id; // 定义子节点的Loader
					
				});//展开树 
				var TreeLoader = this.getLoader(); //得到Ext.tree.TreeLoader
				TreeLoader.dataUrl = content_path+'/reportConfig_json/headerConfigFromOtherHVPageTreeDate.do?reportid='+nodeId+'&parentid='; //更新数据源
				var treeroot = this.getRootNode(); //得到根节点                
				treeroot.reload(); //重新加载根节点
				treeroot.expand(true, false);
			}
		});
		return reportConfigTree;
	}
	
	
	function reportTreeWithYear(reportConfigTree){
		
		var yearConfigListUtl = content_path+"/reportConfig_json/configYearList.do";
		var reportConfigListUtl = content_path+"/reportConfig_json/configReportListJson.do?reportConfig.rtyear=";
		var Tree = Ext.tree;
		var isfirst = true;
		var currentNode;
		
		var reportTree = new Tree.TreePanel({
			//region : 'west',
			autoScroll : true,
			autoHeight : false,
			animate : true,
			enableDD : true,// 不允许子节点拖动
			containerScroll : true,
			rootVisible : false,
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
						//reportConfigTree.myReloadDate("node.id:"+node.id);
						var selectTableCellPage2 = document.getElementById('selectTableCellPage');
						selectTableCellPage2.src = "/reportConfig/reportConfig_selectHPartPage.action?reportConfig.reportLink="+node.id;
						selectTableCellPage2.load();
					}else{
						alert('年份');
					}
					//reloadIFRAME();
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
					if(node.childNodes.length!=0){
						currentNode = node.childNodes[0];
						reportTree.selectPath(node.childNodes[0].getPath());
						//reloadIFRAME();
					}
				}
			}
		});
		return reportTree;
	}
	
	function getSelectedDataFrom(){
		var dataFrom = document.getElementById('cellDataFrom_dataFromType').value;
		return dataFrom;
	}
	
	var tts = '';
	var treeloadurl = content_path+'/reportConfig_json/dataFromTypeTreeJson.do?param1=';
Ext.onReady(function() {	
	
	// shorthand
	var Tree = Ext.tree;
	var fm = Ext.form;
	
	var tree = new Tree.TreePanel({
		title : '类别',
		region : 'west',
		autoScroll : true,
		height : 385,
		width : 155,
		autoHeight : false,
		animate : true,
		enableDD : true,// 不允许子节点拖动
		containerScroll : true,
		rootVisible : true,
		loader : new Tree.TreeLoader({
			dataUrl : treeloadurl + '0'
		})
	});
	tree.on('click', function(node) {
		setDataFromMainPage(node);
	});
	tree.on('beforeload', function(node) {
		tree.loader.dataUrl = treeloadurl + node.id; // 定义子节点的Loader
	});
	tree.on('load', function(node) {//加载子节点后，选中子节点的第一个节点
		var selectDataFromId = getSelectedDataFrom();
		var me = this;
		if(node.childNodes.length!=0){
			Ext.each(node.childNodes, function(cNode) {
				
				if(cNode.id==selectDataFromId){
					tree.selectPath(cNode.getPath());
					//me.currentNode = cNode;
					//currentTreeNode = cNode;
					//reloadConfigReport();
					setDataFromMainPage(cNode);
				}
				
		});
		}
	});

	//设置父节点
	var root = new Tree.AsyncTreeNode({
		text : '数据关系',
		draggable : true,
		id : '0'
	});
	tree.setRootNode(root);
	//展开父节点
	root.expand();
	
	var mainPage = new Ext.Panel({
		region : 'center',
		items : [  {html:"<div id='dataFromConfigHtml' style='height:100%;'></div>"}]
	});
	
	// border layout。数据字典类别树和类别子节点管理
	tts = new Ext.Viewport({
		layout : 'border',
		title : 'Ext Layout Browser',
		items : [  tree ,mainPage],
		renderTo : 'bordergrid'
	});
	
	function setDataFromMainPage(node){
		var bindId = document.getElementById('cellDataFrom_reportBindid').value;
		var getDataFromHtmlUrl = content_path+"/reportConfig/dataFromConfig_cellDataFromConfigHtml.do?param2="+bindId+"&param1="+node.id
		Ext.Ajax.request({
			url : getDataFromHtmlUrl,
			method : 'POST',
			failure : function(response, options) {
				return false;
			},// end failure block
			success : function(response, options) {
				var content_re = response.responseText.trim();
				var content = content_re.split('****');
				var jsName = content[0];
				loadjs(jsName);
				Ext.getDom('dataFromConfigHtml').innerHTML = content[1];
				
				setTimeout("setHaveSaveData()",100); 
				
				
			}// end success block
		});
		
		document.getElementById('cellDataFrom_dataFromType').value=node.id;
		//currentNode = node;
		return false;
	}
	
});

function jsRemoveItemFromSelect(objSelect, objItemValue) {        
    //判断是否存在        
    if (jsSelectIsExitItem(objSelect, objItemValue)) {        
        for (var i = 0; i < objSelect.options.length; i++) {        
            if (objSelect.options[i].value == objItemValue) {        
                objSelect.options.remove(i);        
                break;        
            }        
        }        
        alert("成功删除");        
    } else {        
        alert("该select中 不存在该项");        
    }        
}    

function jsSelectIsExitItem(objSelect, objItemValue) {        
    var isExit = false;        
    for (var i = 0; i < objSelect.options.length; i++) {        
        if (objSelect.options[i].value == objItemValue) {        
            isExit = true;        
            break;        
        }        
    }        
    return isExit;        
}   

function jsSelectRemoveAll(objSelect){
	objSelect.options.length = 0;  
}

function loadjs(jsName){
	var aa=document.getElementsByTagName('HEAD').item(0);
	var nn=document.createElement("script"); 
	nn.src="/js/config/dataFrom/"+jsName;
	nn.type="text/javascript";
	nn.charset="utf-8";
	aa.appendChild(nn);
}
</script>
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH%>/public.css" />
<link rel="stylesheet" type="text/css"	href="<%=CSS_PATH%>/displaytag.css" />

</head>
<body>
    <div id="bordergrid"></div>
	<!--/reportData/reportConfig/headerRowC_dataFromSaveH.action  -->
	<s:form id='dataFromSaveH' action="/reportConfig_json/saveCellDataFrom.action" method="post"
		theme='simple'>
		<s:hidden name='cellDataFrom.reportBindid' id='cellDataFrom_reportBindid'></s:hidden>
		<s:hidden name='cellDataFrom.hbindid' id='cellDataFrom_hbindid'></s:hidden>
		<s:hidden name='cellDataFrom.vbindid' id='cellDataFrom_vbindid'></s:hidden>
		<s:hidden name='cellDataFrom.dataFromType' id='cellDataFrom_dataFromType'></s:hidden>
		<s:hidden name='cellDataFrom.formula' id='cellDataFrom_formula'></s:hidden>
		<s:hidden name='cellDataFrom.dataTerm' id='cellDataFrom_dataTerm'></s:hidden>
	
	</s:form>
</body>
</html>