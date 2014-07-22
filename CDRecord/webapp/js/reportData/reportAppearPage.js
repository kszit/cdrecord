Ext.BLANK_IMAGE_URL = '../R_framework/ext-3_0_0/resources/images/default/s.gif';

var treeloadurl = content_path+'/department_json/departmentJson.do';

var departmentReportAppearUtl = content_path+"/reportData/reportData_departmentAppearListPage.do";

var yearConfigListUtl = content_path+"/reportConfig_json/configYearList.do";
var reportConfigListUtl = content_path+"/reportConfig_json/configReportListJson.do?reportConfig.rtyear=";
var isfirst = true;
var currentNode = null;
Ext.onReady(function() {
	// shorthand
	var Tree = Ext.tree;
	var fm = Ext.form;
	
	var tree = new Tree.TreePanel({
		region : 'west',
		autoScroll : true,
		height : 385,
		width : 155,
		autoHeight : false,
		animate : true,
		enableDD : true,// 不允许子节点拖动
		containerScroll : true,
		rootVisible : false,
		loader : new Tree.TreeLoader({
			dataUrl : yearConfigListUtl
		})
	/*,
		tbar : [{
			text : '刷新',
			handler : function() {
				tree.getRootNode().reload();
			}
		}]
		*/
	});
	tree.on('click', function(node) {
		currentNode = node;
		reloadIFRAME();
	});
	tree.on('beforeload', function(node) {
		if(isfirst){
			tree.loader.dataUrl = yearConfigListUtl;
			isfirst = false;
		}else{
			tree.loader.dataUrl = reportConfigListUtl+currentNode.id;
		}
	});
	tree.on('load', function(node) {//加载子节点后，选中子节点的第一个节点
		if(node.childNodes.length!=0){
			currentNode = node.childNodes[0];
			tree.selectPath(node.childNodes[0].getPath());
			//reloadIFRAME();
		}
	});

	function reloadIFRAME(){
		document.getElementById('appearReport').src = departmentReportAppearUtl+"?reportAppareModel.dataReportModel.createDepBindid="+currentNode.id+
		"&reportAppareModel.reportConfigModel.rtyear=2013";
	}
	
	//设置父节点
	var root = new Tree.AsyncTreeNode({
		text : '123',
		draggable : false,
		id : '0'
	});
	tree.setRootNode(root);

	//展开父节点
	root.expand();

	var htmlStr = '<iframe'+
	' frameborder="0" name="appearReport" id="appearReport" width="100%" height="100%" scrolling="auto"'+
	'src="">'+
	'</iframe>';
	
	var p = new Ext.Panel({
	    region : 'center',
	    layout:'fit',
	    items: {
	        html: htmlStr,
	        border: false
	    }
	});

	
	
	// border layout。数据字典类别树和类别子节点管理
	new Ext.Viewport({
		layout : 'border',
		title : 'Ext Layout Browser',
		items : [ tree, p],
		renderTo : 'bordergrid'
	});

});