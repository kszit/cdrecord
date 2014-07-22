

	

	var sortConditionUrl = '/reportConfig/reportConfig_selectReportPartPage.do?param1=h&param3=2&reportConfig.reportLink=';
	
	//获得已经设置的排序字段，子窗口中调用
	function getHaveSetSortValue(){
        var reportBindid = Ext.getDom('reportBindid').value;
        var sortId = Ext.getDom('hSort').value;
        return reportBindid+"$"+sortId+"#0";
	}
	//清空排序条件,并加载数据
	function clearSort(){
		Ext.getDom('hSort').value = '';
		Ext.getDom('hSortText').innerHTML = '';
		loadReport();
	}
	
	function setSort () {  //移动文章到指定栏目的调用方法
		var reportBindid = Ext.getDom('reportBindid').value;
		if(!reportBindid){
			alert('请先选择报表');
			return;
		}
        var viewWinPanel = createSetSortConditPanel();
        viewWinPanel.show();
    }
	
	
	function createSetSortConditPanel() {// 移动文章到指定栏目的窗口
		
		var pnCenter = new Ext.Panel(
				{
					region : 'center',
					layout : 'anchor',
					items : [
							{
								id : 'iframeHtml',
								html : "<iframe name=setSortItemPage id=setSortItemPage  scrolling=yes frameborder=0 width=100% height=410 src="+sortConditionUrl+Ext.getDom('reportBindid').value+"></iframe>"
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
									
									
									var selectTableCellPage2 = window.frames["setSortItemPage"].window;
									var hSortId = selectTableCellPage2.getSelectedCellHVIds();
									var hSortText = selectTableCellPage2.getSelectedCellHVValues();
									
									var hSortBindid = hSortId.split('$')[1].split('#')[0];
									
									Ext.getDom('hSort').value = hSortBindid;
									Ext.getDom('hSortText').innerHTML = hSortText;
									
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
