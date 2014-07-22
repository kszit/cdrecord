

	

	

	

	function selectTableReport () {  //
        var viewWinPanel = createSelectReportPanel();
        viewWinPanel.show();

    }
	
	
	function createSelectReportPanel() {// 
		var currentTreeNode = '1';
		var currentDeptNode = '1';
		var currentPeriodNode = '1';
		
		var selectReportBindid = Ext.getDom('reportBindid').value;
		var selectDeptIds = Ext.getDom('selectDept').value;
		var selectPeroids = Ext.getDom('selectPeriod').value;
		
		//已填报时期的url
		function getPeriodTreeUrl(){
			var reportBindid = currentTreeNode.id;
			var deptid = '';
			if(currentDeptNode!='1'){
				deptid = currentDeptNode.id;
			}
			var periodTreeLoadUrl = content_path+'/reportData_json/getPeriodTreeOfappearReport.do?param3='+deptid+'&param2='+reportBindid+'&param1=';
			return periodTreeLoadUrl;
		}
		//已填报的时期树
		function createPeriodTree(){
			var periodTreeLoadUrl = getPeriodTreeUrl();
			 var periodTreePanel = new Ext.tree.TreePanel({
	    	        title: '期数列表',
	    	        height: 300,
	    	        width: 400,
	    	        useArrows:true,
	    	        autoScroll:true,
	    	        animate:true,
	    	        enableDD:true,
	    	        containerScroll: true,
	    	        rootVisible: true,
	    	        root:new Ext.tree.AsyncTreeNode({
	    	    		text : 'XXX集团',
	    	    		draggable : false,
	    	    		id : '0'	
	    	    	}),
	    	        dataUrl: periodTreeLoadUrl,
	    	        listeners: {
	    	        	'click':function(node) {
	    	        		currentPeriodNode = node;
	    	        		reloadDept();
						},
	    	            'checkchange': function(node, checked){
	    	                if(checked){
	    	                    node.getUI().addClass('complete');
	    	                }else{
	    	                    node.getUI().removeClass('complete');
	    	                }
	    	            },
						'load':function(node) {//加载子节点后，选中子节点的第一个节点
							var nodeId = node.id
							if(nodeId!=0 && selectPeroids.indexOf(nodeId)!=-1){
								var checkBox = node.getUI().checkbox;
								node.attributes.checked = true;
								checkBox.checked = true;
							}
							
						}
	    	        }
	    	    });
			 periodTreePanel.on('beforeload', function(node) {
				 periodTreePanel.loader.dataUrl = periodTreePanel.loader.dataUrl + node.id; // 定义子节点的Loader
		    	});
			 periodTreePanel.getRootNode().expand(true);
			return periodTreePanel;
		}
		
		var periodTree = '1';
		function reloadPeriod(){
			if(periodTree=='1'){
				periodTree = createPeriodTree();
				var eastPanel = Ext.getCmp('eastPanel');
				eastPanel.removeAll();
				eastPanel.add({
					layout:'fit',
					items : [periodTree]
				});
				eastPanel.doLayout();
			}else{
				var TreeLoader = periodTree.getLoader();  //得到Ext.tree.TreeLoader
			       TreeLoader.dataUrl = getPeriodTreeUrl();                    //更新数据源
			       var treeroot = periodTree.getRootNode();    //得到根节点                
			       treeroot.reload();                                 //重新加载根节点
			       treeroot.expand(true,false);              //展开树  
			}
		}
		
		
		
		
		
		
		
		
		
		
		
		//已上报单位的url
		function getDeptTreeUtl(){
			var reportBindid = currentTreeNode.id;
			var period = '';
			if(currentPeriodNode!='1'){
				period = currentPeriodNode.id;
			}
			var deptTreeLoadUrl = content_path+'/reportData_json/getDeptTreeOfappearReport.do?param3='+period+'&param2='+reportBindid+'&param1=';
			return deptTreeLoadUrl;
		}
		//已上报单位树
		function createDeptTree(){
			var deptTreeLoadUrl = getDeptTreeUtl();
			 var deptTreePanel = new Ext.tree.TreePanel({
	    	        title: '组织机构列表',
	    	        height: 300,
	    	        width: 400,
	    	        useArrows:true,
	    	        autoScroll:true,
	    	        animate:true,
	    	        enableDD:true,
	    	        containerScroll: true,
	    	        rootVisible: true,
	    	        root:new Ext.tree.AsyncTreeNode({
	    	    		text : 'XXX集团',
	    	    		draggable : false,
	    	    		id : '0'	
	    	    	}),
	    	        dataUrl: deptTreeLoadUrl+'0',
	    	        listeners: {
	    	        	'click':function(node) {
	    	        		currentDeptNode = node;
	    	        		reloadPeriod();
						},
	    	            'checkchange': function(node, checked){
	    	                if(checked){
	    	                    node.getUI().addClass('complete');
	    	                }else{
	    	                    node.getUI().removeClass('complete');
	    	                }
	    	            },
						'load':function(node) {//加载子节点后
							var nodeId = node.id
							if(nodeId!=0 && selectDeptIds.indexOf(nodeId)!=-1){
								var checkBox = node.getUI().checkbox;
								checkBox.checked = true;
								node.attributes.checked = true;
								
							}
							
						}
	    	        }
	    	    });
			 deptTreePanel.on('beforeload', function(node) {
				 deptTreePanel.loader.dataUrl = deptTreePanel.loader.dataUrl + node.id; // 定义子节点的Loader
		    	});
			 deptTreePanel.getRootNode().expand(true);
			return deptTreePanel;
		}
		
		var deptTree = '1';
		function reloadDept(){
			if(deptTree=='1'){
				deptTree = createDeptTree();
				var pnCenter = Ext.getCmp('pnCenter');
				pnCenter.removeAll();
				pnCenter.add({
					layout:'fit',
					items : [deptTree]
				});
				pnCenter.doLayout();
			}else{
				var TreeLoader = deptTree.getLoader();  //得到Ext.tree.TreeLoader
			       TreeLoader.dataUrl = getDeptTreeUtl();                    //更新数据源
			       var treeroot = deptTree.getRootNode();    //得到根节点                
			       treeroot.reload();                                 //重新加载根节点
			       treeroot.expand(true,false);              //展开树  
			}
			

			
			//deptTree.loader.dataUrl = deptTreeLoadUrl + node.id; // 定义子节点的Loader
			
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
							currentDeptNode = '1';
							currentPeriodNode = '1';
							
							reloadDept();
							reloadPeriod();
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
								
								if(cNode.id==selectReportBindid){
									reportTree.selectPath(cNode.getPath());
									me.currentNode = cNode;
									currentTreeNode = cNode;
									
									currentDeptNode = '1';
									currentPeriodNode = '1';
									reloadDept();
									reloadPeriod();
								}
								
						});
						}
					}
				}
			});
			reportTree.getRootNode().expand(true);//自动加载所有节点
			return reportTree;
		}
		
	
		var reporttree2 = reportTreeWithYear();
		var reportTree = new Ext.Panel({
			id : 'reportTree',
			title : '报表列表',
			width : 250,
			heigth : 'auto',
			split : true,// 显示分隔条
			region : 'west',
			collapsible : true,
			items : [ reporttree2 ]
		});
		
		

		var eastPanel = new Ext.Panel(
				{
					id:'eastPanel',
					layout : 'anchor',
					items : [
							{
								id : 'html2',
								html : '<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=yes frameborder=0 width=100% height=410 src=></iframe>'
							} ]
				});

		
		var pnCenter = new Ext.Panel(
				{
					id:'pnCenter',
					region : 'center',
					layout : 'anchor',
					items : [
							{
								id : 'iframeHtml',
								html : '<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=yes frameborder=0 width=100% height=410 src=></iframe>'
							} ]
				});


		var win = new Ext.Window(
				{
					layout : "hbox",
					width : 850,
					height : 450,
					modal : true,
					closeAction : 'hide',
					buttonAlign : "center",
					title : "从下级表选择指标",
					items : [ reportTree, pnCenter,eastPanel ],
					buttons : [
							{
								text : '确定',
								handler : function(button, evn) {
									var reportBindid = currentTreeNode.id;
									var reportName = currentTreeNode.text;
									Ext.getDom('reportBindid').value = reportBindid;
									Ext.getDom('reportBindidName').innerHTML = reportName;
									
									var selectDetp = '';
									var selectDetpName = '';
									var checkedDepts = deptTree.getChecked();
									if(checkedDepts.length==0){
										checkedDepts = deptTree.getRootNode().childNodes;
									}
									Ext.each(checkedDepts,function(node){
										selectDetp += node.id +"|"
										selectDetpName += node.text+",";
									});
									Ext.getDom('selectDept').value = selectDetp;
									Ext.getDom('selectDeptName').innerHTML = selectDetpName;
									
									
									var selectPeriod = '';
									var selectPeriodName = '';
									var checkedPeriods = periodTree.getChecked();
									if(checkedPeriods.length==0){
										checkedPeriods = periodTree.getRootNode().childNodes;
									}
									Ext.each(checkedPeriods,function(node){
										selectPeriod += node.id+"|";
										selectPeriodName += node.text+",";
									});
									Ext.getDom('selectPeriod').value = selectPeriod;
									Ext.getDom('selectPeriodName').innerHTML = selectPeriodName;
									
									
									loadReport();
									win.hide();
									win.destroy();
								}
							}, {
								text : '取消',
								handler : function(button, evn) {
									win.hide();
									win.destroy();
								}
							} ]
				});
		return win;
	}
