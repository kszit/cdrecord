/*!
 * Ext JS Library 3.0.0

 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
var tabs
Ext.onReady(function(){
    tabs = new Ext.TabPanel({
        //renderTo:'tabs',
        resizeTabs:true, // turn on tab resizing
        minTabWidth: 115,
        tabWidth:135,
        enableTabScroll:true,
        width:600,
        height:250,
        defaults: {autoScroll:true},
        plugins: new Ext.ux.TabCloseMenu()
    });
   
    function addMain(){
    	var departentAppearutl = content_path+"/reportData/reportData_departmentAppearPage.action";
    	var reportAppearutl = content_path+"/reportData/reportData_reportAppearPage.action";
    	addTab(departentAppearutl,'各单位上报情况','0');
    	addTab(reportAppearutl,'各报表上报情况','1');
    };
    

	// border layout
	new Ext.Viewport({
		layout : 'fit',
		items : [tabs],
		renderTo : 'tabs'
	});

    
    addMain();
    

});
function addTab(htmlUrl,title,recordId){
	var tabId = 'projTab' + recordId;
	var htmlStr = '<iframe'+
	' frameborder="0" width="100%" height="100%" scrolling="auto" '+
	'src="'+htmlUrl+'">'+
	'</iframe>';
	var projPanel = {xtype:'panel',id:tabId, html:htmlStr, title:title,iconCls:'iconDetail', closable:true, autoScroll:true};
	tabs.add(projPanel);
	tabs.setActiveTab(tabId);
	
} 

function addItem(title,recordId){
	//var urlhtml = "/reportData/reportConfig/reportConfig_inputPage.action?reportConfig.id="+recordId;
	var urlhtml = content_path+"/reportConfig/reportConfig_inputPage.action?reportConfig.id="+recordId;
	addTab(urlhtml,title,recordId);
};
