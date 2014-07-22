

	
	var copyFromChildReport = content_path+"/reportConfig/reportConfig_selectHVPartPage.action?reportConfig.reportLink=";
	var copyFromOthorReport = content_path+"/reportConfig/reportConfig_selectReportPartPage.action?reportConfig.reportLink=";
	
	
	
	
	var reportPageUrl = "";
	function copyFromTableReport (type) {  //移动文章到指定栏目的调用方法
		if(type==1){
			reportPageUrl = copyFromChildReport;
		}else if(type==2){
			reportPageUrl = copyFromOthorReport;
		}
		alert('df');
        var viewWinPanel = createViewWinPanel();
        viewWinPanel.show();
    }
	
	
	function createViewWinPanel() {// 移动文章到指定栏目的窗口
		var currentTreeNode = '';
		var currentPartType = 'h';
		function reloadConfigReport(){
			if(currentTreeNode==''){
				return;
			}
			var selectTableCellPage2 = document.getElementById('selectTableCellPage');
			selectTableCellPage2.src = reportPageUrl+currentTreeNode.id+"&param1="+currentPartType;
		}
		
		function reportTreeWithYear(){
			
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
							currentTreeNode = node;
							reloadConfigReport();
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
		var radiogroup = new Ext.form.RadioGroup({
			fieldLabel : "radioGroup",
			name : 'HorV',
			height : 15,
			items : [ new Ext.form.Radio({
				boxLabel : '纵向表头',
				name : 'HorV',
				inputValue : 'v',
				checked : true
			}), new Ext.form.Radio({
				boxLabel : '横向表头',
				name : 'HorV',
				inputValue : 'h'
			}), new Ext.form.Radio({
				boxLabel : '全表格',
				name : 'HorV',
				inputValue : 'hv'
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
								html : '<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=yes frameborder=0 width=100% height=385 src=></iframe>'
							} ]
				});

		var win = new Ext.Window(
				{
					layout : "border",
					width : 1000,
					height : 500,
					modal : true,
					closeAction : 'hide',
					buttonAlign : "center",
					title : "从下级表选择指标",
					items : [ pnWest, pnCenter ],
					buttons : [
							{
								text : '确定',
								handler : function(button, evn) {
									
									var selectedCell = window.frames["selectTableCellPage"].window.getSelectedCellHVIds();
									var oldReportBindid = reporttree2.currentNode.id;
									
									saveSelectTablePart(oldReportBindid,selectedCell);
								}
							}, {
								text : '取消',
								handler : function(button, evn) {
									win.hide();
								}
							} ]
				});
		return win;
	}
