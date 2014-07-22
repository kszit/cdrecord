

	
	var copyFromChildReport = content_path+"/reportConfig/reportConfig_selectHVPartPage.action?reportConfig.reportLink=";
	var copyFromOthorReport = content_path+"/reportConfig/reportConfig_selectReportPartPage.action?reportConfig.reportLink=";

	var queryConditionUrl = '/queryReport/queryReport_setQueryConditionOfTable.do?param1=';
	
	//获得已经设置的查询条件，子窗口中调用
	function getHaveSetItemValue(){
        var selectQueryCondition = Ext.getDom('hQueryCondition').value;
        return selectQueryCondition;
	}
	
	//清空查询条件,并加载数据
	function chearQueryConfition(){
		Ext.getDom('hQueryCondition').value = '';
		Ext.getDom('hQueryConditionText').innerHTML = '';
		loadReport();
	}
	
	function setQueryConfition () {  //移动文章到指定栏目的调用方法
		var reportBindid = Ext.getDom('reportBindid').value;
		if(!reportBindid){
			alert('请先选择报表');
			return;
		}
        var viewWinPanel = createSetQueryConditPanel();
        viewWinPanel.show();
    }
	
	
	function createSetQueryConditPanel() {// 移动文章到指定栏目的窗口
		var currentPartType = 'h';
		function reloadConfigReport(){
			var reportBindid = Ext.getDom('reportBindid').value;
			var selectTableCellPage2 = document.getElementById('selectTableCellPage');
			selectTableCellPage2.src = queryConditionUrl+currentPartType+"&param2="+reportBindid;
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
			}) ],
			listeners : {
				'change' : function(rg, r) {
					currentPartType = r.inputValue;
					reloadConfigReport();//加载数据
				}
			}
		});
		
		

		
		
		var pnCenter = new Ext.Panel(
				{
					region : 'center',
					layout : 'anchor',
					items : [
							radiogroup,
							{
								id : 'iframeHtml',
								html : "<iframe name=selectTableCellPage id=selectTableCellPage  scrolling=yes frameborder=0 width=100% height=410 src="+queryConditionUrl+currentPartType+"&param2="+Ext.getDom('reportBindid').value+"></iframe>"
							} ]
				});

		var win = new Ext.Window(
				{
					layout : "border",
					width : 650,
					height : 450,
					modal : true,
					closeAction : 'hide',
					buttonAlign : "center",
					title : "从下级表选择指标",
					items : [  pnCenter ],
					buttons : [
							{
								text : '确定',
								handler : function(button, evn) {
									
									
									var selectTableCellPage2 = window.frames["selectTableCellPage"].window;
									var hQueryCondition = selectTableCellPage2.getUserInputValue();
									
									var hQueryConditions = hQueryCondition.split('######');
									
									Ext.getDom('hQueryCondition').value = hQueryConditions[0];
									Ext.getDom('hQueryConditionText').innerHTML = hQueryConditions[1];
									
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
