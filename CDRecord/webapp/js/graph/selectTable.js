

	
	var copyFromChildReport = content_path+"/reportConfig/reportConfig_selectHVPartPage.action?reportConfig.reportLink=";
	var copyFromOthorReport = content_path+"/reportConfig/reportConfig_selectReportPartPage.action?reportConfig.reportLink=";
	
	var graphSubId = '';
	//bindid
	var graphSubBindid = '';
	//选择的部门id，
	var graphSubdeptIds = '';
	//选择的部门名称
	var graphSubdeptNames = '';
	//报表的linkbindid
	var graphSubTableBindid = '';
	//报表 选择的单元格
	var graphSubTableSelectCell = '';
	//报表 开始日期
	var graphSubStartDay = '';
	//报表 截止日期
	var graphSubEndDay = '';
	
	
	function setgraphSubDeptDivContent(deptNames){
			var graphSubDept = Ext.getDom('graphSubDept');
	    	graphSubDept.innerHTML = deptNames;
	    	
	}
	
	var reportPageUrl = "";
	function selectTableReport (type) {  //移动文章到指定栏目的调用方法
		if(type==1){
			reportPageUrl = copyFromChildReport;
		}else if(type==2){
			reportPageUrl = copyFromOthorReport;
		}
        var viewWinPanel = createViewWinPanel();
        viewWinPanel.show();
    }
	
	
	function createViewWinPanel() {// 移动文章到指定栏目的窗口
		var currentTreeNode = '';
		var currentPartType = 'hv';
		function reloadConfigReport(){
			if(currentTreeNode==''){
				return;
			}
			var selectTableCellPage2 = document.getElementById('selectTableCellPage');
			var graphSubTableSelectCell_temp = graphSubTableSelectCell;
			while(graphSubTableSelectCell_temp.indexOf("#", 0)!=-1){
				 graphSubTableSelectCell_temp = graphSubTableSelectCell_temp.replace( "#","**");
			}
			selectTableCellPage2.src = reportPageUrl+currentTreeNode.id+"&param1="+currentPartType+"&param2="+graphSubTableSelectCell_temp;
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
				checked : false
			}), new Ext.form.Radio({
				boxLabel : '全表格',
				name : 'HorV',
				inputValue : 'hv',
				checked : true
			}) ],
			listeners : {
				'change' : function(rg, r) {
					currentPartType = r.inputValue;
					reloadConfigReport();//加载数据
				}
			}
		});
		//var reportConfigTree = createReportConfigTree(radiogroup);

		var form1 = new Ext.form.FormPanel({
		height : 50,
		width : 900,
		region : 'south',
		
		items:[{
		       layout: 'column',
		       height:25,
		       itemCls:'required',
		       style:'margin-bottom:0px;',
			items : [ {
				layout: 'form',
				//height:25,
				items:new Ext.form.DateField({
					labelStyle:'color:#000',
					fieldLabel : '开始日期',
					hideLabel:false,
					width : 100,
					format : 'Y-m-d',
					value:graphSubStartDay,
					allowBlank : true,
					name : 'startDay',
					id : 'startDay'
			})},{
				layout: 'form',
				//height:25,
				items:new Ext.form.DateField({
					labelStyle:'color:#000',
					fieldLabel : '结束日期',
					value:graphSubEndDay,
					hideLabel:false,
					width : 100,
					format : 'Y-m-d',
					allowBlank : true,
					name : 'endDay',
					id : 'endDay'
			})}
			]
		},{
		       layout: 'column',
		       height:25,
				items : [{
					layout: 'form',
					height:25,
					items:[{html:"<a href='#' onclick='setSpecificReportFromDept();return false;'>选择单位</a>"}]},{
						layout: 'form',
						height:25,
						items:[{html:"<div id='graphSubDept'>"+graphSubdeptNames+"</div>"}]}
				]
		}
	]
	});
		
		
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
								html : '<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=yes frameborder=0 width=100% height=410 src=></iframe>'
							} ]
				});

		var win = new Ext.Window(
				{
					layout : "border",
					width : 1000,
					height : 550,
					modal : true,
					closeAction : 'hide',
					buttonAlign : "center",
					title : "从下级表选择指标",
					items : [ pnWest, pnCenter,form1 ],
					buttons : [
							{
								text : '确定',
								handler : function(button, evn) {
									var graphBindid = getGraphBindid();//图表bindid
									var selectedCell = window.frames["selectTableCellPage"].window.getSelectedCellHVIds();//选择的单元格
									var reportTableLinkBindid = reporttree2.currentNode.id;//选择的报表id
									var startDay = Ext.getCmp('startDay').value;
									var endDay = Ext.getCmp('endDay').value;

		                        	var setAppearDepartment = {
		                           			url:content_path+"/graphConfig/graphC_insertGraphSubAction.do",
		                           			callback:function(options,success,response){//回调函数
		                           				alert(response.responseText);
		                           				var msg = ["请求是否成功：" ,success,"\n",
		                           							"服务器返回值：",response.responseText,
		                           							"本地自定义属性：",options.customer];
		                           				//alert(msg.join(''));
		                           				refresh();
		                           			},
		                           			params: { 
		                           				'graphCSModel.id':graphSubId,
		                           				'graphCSModel.mainBindid':graphBindid,
		                           				'graphCSModel.tableLinkBindid':reportTableLinkBindid,
		                           				'graphCSModel.deptIds':graphSubdeptIds,
		                           				'graphCSModel.tableCells':selectedCell,
		                           				'graphCSModel.startDay':startDay,
		                           				'graphCSModel.endDay':endDay
		                           			}
		                            	};
		                           Ext.Ajax.request(setAppearDepartment);//发送请求
									
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
