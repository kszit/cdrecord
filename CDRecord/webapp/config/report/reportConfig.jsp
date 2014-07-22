<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>  
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportConfig.reportName"/></title>
<%@ include file="/common/comm.jsp" %>
<sj:head jquerytheme="cupertino" ajaxcache="false" compressed="false"/>
<sx:head extraLocales="UTF-8"/>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/RowExpander.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/examples/ux/CheckColumn.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/TreeGrid/TreeGrid.js"></script>
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/treegrid/TreeGrid.css" />
<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/treegrid/TreeGridLevels.css" />


<script type="text/javascript" src="<%=JS_PATH %>/config/report/reportConfigH.js"></script>
<script type="text/javascript" src="<%=JS_PATH %>/config/report/reportConfigV.js"></script>
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />

</head>
<body>


<script type="text/javascript">  
	var reportid = '<s:text name="reportConfig.reportBindidLink"/>';
	var dataTypebindid = '<s:text name="DataTypeBindId"/>';
	var dataFrombindid = '<s:text name="DataFromBindId"/>';
	var UIModelebindid = '<s:text name="UIModelBindId"/>';
	var isSettingbindid = '<s:text name="IsSettingBindId"/>';

	function setReportId(reportId,reportBindId,version){
		reportid = reportBindId+'-'+version;
		HeaderRowConfig.setReportId(reportBindId+'-'+version);
		VerticalColumnConfig.setReportId(reportBindId+'-'+version);

		$("#reportConfigSave_reportConfig_id").val(reportId);
		$("#reportConfigSave_reportConfig_bindId").val(reportBindId);
		$("#reportConfigSave_reportConfig_rtversion2").val(version);
		
	}
	
    $.subscribe('clearError', function(event,data) {  
		//alert('提交数据');
    });  
    $.subscribe('complete', function(event,data) {  
        var json = $.parseJSON(event.originalEvent.request.responseText); 
       	//alert(event.originalEvent.request.responseText);
       	setReportId(json.reportConfig.id,json.reportConfig.bindId,json.reportConfig.rtversion2)
        $("#actionMessage").html("");//先将上次认证的错误消息清除掉  
        if(json.actionMessages && json.actionMessages.length>0){
			$.each(json.actionMessages,function(index,data){
				alert(data);
                //$("#actionMessage").append("<li>"+data+"</li>");  
				});  
            return;  
        }  
    });  

    function seeDataFrom(){
    	var reportbindId = $("#reportConfigSave_reportConfig_bindId").val();
    	var version = $("#reportConfigSave_reportConfig_rtversion2").val();
    	open(content_path+'/reportConfig/reportConfig_seeDataFromAllTablePage.do?reportConfig.bindId='+reportbindId+'&reportConfig.rtversion2='+version);
    }
    
    //打开单个单元格的属性配置页面
    function openSingleCellConfigPage(){
    	var reportbindId = $("#reportConfigSave_reportConfig_bindId").val();
    	var version = $("#reportConfigSave_reportConfig_rtversion2").val();
    	open(content_path+'/reportConfig/reportConfig_singleCellProperyConfigPage.do?reportConfig.bindId='+reportbindId+'&reportConfig.rtversion2='+version);
    }
    
    function seeReport(){
    	var reportbindId = $("#reportConfigSave_reportConfig_bindId").val();
    	var version = $("#reportConfigSave_reportConfig_rtversion2").val();
    	open(content_path+'/reportData/reportData_inputPage.do?dataReportModel.repotBindid='+reportbindId+'-'+version);
    }
    
    function copyReport(){
    	var reportbindId = $("#reportConfigSave_reportConfig_bindId").val();
    	var version = $("#reportConfigSave_reportConfig_rtversion2").val();
    	Ext.MessageBox.confirm('询问', '生成新版本后，当前版本会失效。', function (opt) {
            if (opt == 'yes') {
            	var requestConfig = {
               			url:'<%=CONTEXT_PATH %>/reportConfig/reportConfig_copyReportTable.do?reportConfig.bindId='+reportbindId+"&reportConfig.rtversion2="+version,//请求的服务器地址
               			callback:function(options,success,response){//回调函数
               				var msg = ["请求是否成功：" ,success,"\n",
               							"服务器返回值：",response.responseText,
               							"本地自定义属性：",options.customer];
               				var data = response.responseText.split('|');
               				window.parent.addItem(data[1],data[0]);
               			}
                	}
                Ext.Ajax.request(requestConfig);//发送请求
            }
        });
    	
    	
    }
    //====================================================================可填报的单位
    var treeloadurl = '../../reportConfig_json/setAppearDepartment.do?reportConfig.reportLink='+reportid+'&id=';
    var setUserDepartmentWin = null;
    function setUseDepartment(){
    	  var tree = new Ext.tree.TreePanel({
    	        title: '可填报数据表的单位',
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
    	        dataUrl: treeloadurl+'0',
    	        listeners: {
    	            'checkchange': function(node, checked){
    	                if(checked){
    	                    node.getUI().addClass('complete');
    	                }else{
    	                    node.getUI().removeClass('complete');
    	                }
    	            }
    	        }
    	    });
	      	tree.on('beforeload', function(node) {
	    		tree.loader.dataUrl = treeloadurl + node.id; // 定义子节点的Loader
	    	});
    	    tree.getRootNode().expand(true);
    	if(!setUserDepartmentWin){
        	setUserDepartmentWin = new Ext.Window({  
                applyTo:'hello-win',  
                layout:'fit',  
                width:500,  
                height:300,  
                closeAction:'hide',
                plain: true,  
      			modal:true,
                items: tree,
                buttons: [{
                    text:'确定',
                    disabled:false,
                    handler:function(){
                        var departmentIds = '', selNodes = tree.getChecked();
                        Ext.each(selNodes, function(node){
                        	departmentIds += '|'+node.id;
                        });
                        departmentIds += '|';
                        $("#reportConfigSave_reportConfig_appearDepartment").val(departmentIds);
                    	var setAppearDepartment = {
                       			url:"<%=CONTEXT_PATH %>/reportConfig/reportConfig_saveAppearDepartment.do?reportConfig.reportLink="+reportid+"&reportConfig.appearDepartment="+departmentIds,
                       			callback:function(options,success,response){//回调函数
                       				
                       				var msg = ["请求是否成功：" ,success,"\n",
                       							"服务器返回值：",response.responseText,
                       							"本地自定义属性：",options.customer];
                       				//alert(msg.join(''));
                       			}
                        	};
                       Ext.Ajax.request(setAppearDepartment);//发送请求
                       setUserDepartmentWin.hide();
                    }
                },{
                    text: '取消',
                    handler: function(){
                    	setUserDepartmentWin.hide();
                    }
                }]
            });
    	}
    	setUserDepartmentWin.show();
    }
    
//====================================================可查询的单位
    var querytreeloadurl = '../../reportConfig_json/setQueryDepartment.do?reportConfig.reportLink='+reportid+'&id=';
    var setQueryDepartmenttWin = null;
    function setQueryDepartment(){
    	  var querytree = new Ext.tree.TreePanel({
    	        title: '可查询数据表的单位',
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
    	        dataUrl: querytreeloadurl+'0',
    	        listeners: {
    	            'checkchange': function(node, checked){
    	                if(checked){
    	                    node.getUI().addClass('complete');
    	                }else{
    	                    node.getUI().removeClass('complete');
    	                }
    	            }
    	        }
    	    });
    	  querytree.on('beforeload', function(node) {
    		  querytree.loader.dataUrl = querytreeloadurl + node.id; // 定义子节点的Loader
	    	});
    	  querytree.getRootNode().expand(true);
    	if(!setQueryDepartmenttWin){
    		setQueryDepartmenttWin = new Ext.Window({  
                applyTo:'queryDetpDiv',  
                layout:'fit',  
                width:500,  
                height:300,  
                closeAction:'hide',
                plain: true,  
      			modal:true,
                items: querytree,
                buttons: [{
                    text:'确定',
                    disabled:false,
                    handler:function(){
                        var departmentIds = '', selNodes = querytree.getChecked();
                        Ext.each(selNodes, function(node){
                        	departmentIds += '|'+node.id;
                        });
                        departmentIds += '|';
                         $("#reportConfigSave_reportConfig_queryDept").val(departmentIds);
                    	var setAppearDepartment = {
                       			url:"<%=CONTEXT_PATH %>/reportConfig/reportConfig_saveQueryDepartment.do?reportConfig.reportLink="+reportid+"&reportConfig.queryDept="+departmentIds,
                       			callback:function(options,success,response){//回调函数
                       				
                       				var msg = ["请求是否成功：" ,success,"\n",
                       							"服务器返回值：",response.responseText,
                       							"本地自定义属性：",options.customer];
                       				//alert(msg.join(''));
                       			}
                        	};
                       Ext.Ajax.request(setAppearDepartment);//发送请求
                       setQueryDepartmenttWin.hide();
                    }
                },{
                    text: '取消',
                    handler: function(){
                    	setQueryDepartmenttWin.hide();
                    }
                }]
            });
    	}
    	setQueryDepartmenttWin.show();
    }
    

    
  //====================================================可汇总的单位
    var collecttreeloadurl = '../../reportConfig_json/setColectDepartment.do?reportConfig.reportLink='+reportid+'&id=';
    var setCollectDepartmentWin = null;
    function setCollectDepartment(){
    	  var collecttree = new Ext.tree.TreePanel({
    	        title: '可汇总数据表的单位',
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
    	        dataUrl: collecttreeloadurl+'0',
    	        listeners: {
    	            'checkchange': function(node, checked){
    	                if(checked){
    	                    node.getUI().addClass('complete');
    	                }else{
    	                    node.getUI().removeClass('complete');
    	                }
    	            }
    	        }
    	    });
    	  collecttree.on('beforeload', function(node) {
    		  collecttree.loader.dataUrl = collecttreeloadurl + node.id; // 定义子节点的Loader
	    	});
    	  collecttree.getRootNode().expand(true);
    	if(!setCollectDepartmentWin){
    		setCollectDepartmentWin = new Ext.Window({  
                applyTo:'collectDetpDiv',  
                layout:'fit',  
                width:500,  
                height:300,  
                closeAction:'hide',
                plain: true,  
      			modal:true,
                items: collecttree,
                buttons: [{
                    text:'确定',
                    disabled:false,
                    handler:function(){
                        var departmentIds = '', selNodes = collecttree.getChecked();
                        Ext.each(selNodes, function(node){
                        	departmentIds += '|'+node.id;
                        });
                        departmentIds += '|';
                        $("#reportConfigSave_reportConfig_collectDept").val(departmentIds);
                    	var setAppearDepartment = {
                       			url:"<%=CONTEXT_PATH %>/reportConfig/reportConfig_saveCollectDepartment.do?reportConfig.reportLink="+reportid+"&reportConfig.collectDept="+departmentIds,
                       			callback:function(options,success,response){//回调函数
                       				
                       				var msg = ["请求是否成功：" ,success,"\n",
                       							"服务器返回值：",response.responseText,
                       							"本地自定义属性：",options.customer];
                       				//alert(msg.join(''));
                       			}
                        	};
                       Ext.Ajax.request(setAppearDepartment);//发送请求
                       setCollectDepartmentWin.hide();
                    }
                },{
                    text: '取消',
                    handler: function(){
                    	setCollectDepartmentWin.hide();
                    }
                }]
            });
    	}
    	setCollectDepartmentWin.show();
    }
        
    
    
    
    
    
    
    Ext.BLANK_IMAGE_URL = '../R_framework/ext-3_0_0/resources/images/default/s.gif';

    Ext.onReady(
    	function(){
    		HeaderRowConfig.init(reportid,dataTypebindid,dataFrombindid,UIModelebindid);
    		VerticalColumnConfig.init(reportid,dataTypebindid,dataFrombindid,UIModelebindid);
    	}
    	
 
    	
    );

</script>
<div id="hello-win"></div>
<div id="queryDetpDiv"></div>
<div id="collectDetpDiv"></div>

<div id="configDlg"></div>
<div id="configDlgv"></div>
<table width=100%>
	<tr>
		<td align='right'>
			<button class="cdrecordButton" onclick="setCollectDepartment();">设定报表汇总部门</button>
			<button class="cdrecordButton" onclick="setQueryDepartment();">设定报表查询部门</button>
			<button class="cdrecordButton" onclick="setUseDepartment();">设定报表上报部门</button>
			<button class="cdrecordButton" onclick='copyReport();'>生成新版本</button>
			<button class="cdrecordButton" onclick='openSingleCellConfigPage();'>单元格配置</button>
			<button class="cdrecordButton" onclick='seeDataFrom();'>查看数据关系</button>
			<button class="cdrecordButton" onclick='seeReport();'>查看报表</button>
		</td>
	</tr>
	<tr>
		<td>
			<table width=100%>
				<tr>
					<td>
						<div id='show'></div>
						<div id='actionMessage'></div>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
		 	<ul id="errorMessages"></ul>
			<s:form action="/reportConfig_json/reportConfigSave.action" method="post" theme='simple'> 
			<s:hidden name="reportConfig.userDeptBindid"></s:hidden>
			<s:hidden name="reportConfig.collectDept"></s:hidden>
			<s:hidden name="reportConfig.queryDept"></s:hidden>
			<s:hidden name="reportConfig.appearDepartment"></s:hidden>
			
			<table>
				<tr>
					<td>
						年份
					</td>
					<td>
						<s:select cssClass="cdrecordInput" onchange=''
							name='reportConfig.rtyear'
					       	list="yearList"
					       	listKey="name"
					       	listValue="name"
					       	value="reportConfig.rtyear"
				      	/>
					</td>
					<td>
						类型
					</td>
					<td>
						<s:select cssClass="cdrecordInput" onchange=''
							name='reportConfig.rttype'
					       	list="reportFreqList"
					       	listKey="bindId"
					       	listValue="name"
					       	value="reportConfig.rttype"
				      	/>
					</td>
					<td>
						报表状态
					</td>
					<td>
						<s:select cssClass="cdrecordInput" onchange=''
							name='reportConfig.reportState'
					       	list="reportStateList"
					       	listKey="bindId"
					       	listValue="name"
					       	value="reportConfig.reportState"
				      	/>
					</td>
				</tr>
				<tr>
					<td>
						名称
					</td>
					<td>
						<s:textfield name="reportConfig.reportName" size="40"  cssClass="cdrecordInput"/>
					</td>
					<td>
						编号
					</td>
					<td>
						<s:textfield name="reportConfig.reportNo" size="40"  cssClass="cdrecordInput"/>
					</td>
					<td>
						填报说明
					</td>
					<td>
						<s:textfield name="reportConfig.rtdeclare"  size="40" cssClass="cdrecordInput"></s:textfield>
					</td>			
				</tr>
				<tr>
					<td>
						推迟或提前
					</td>
					<td>
						<select name="reportConfig.aheadOrDelayType">
							<option value='1' <s:if test="reportConfig.aheadOrDelayType=='-1'">selected</s:if>>推迟</option>
							<option value='-1' <s:if test="reportConfig.aheadOrDelayType=='1'">selected</s:if>>提前</option>
						</select>
					</td>
					<td>
						天数
					</td>
					<td>
						<s:textfield name="reportConfig.aheadOrDelayDates" size="40"  cssClass="cdrecordInput" /> 
					</td>
					<td>
						类别
					</td>
					<td>
						<s:select cssClass="cdrecordInput" onchange=''
							name='reportConfig.type'
					       	list="typeList"
					       	listKey="bindId"
					       	listValue="name"
					       	value="reportConfig.type"
				      	/>
					</td>
				</tr>	
				<tr>
					<td>
						添加人
					</td>
					<td>
						<s:textfield name="reportConfig.inputUser" size="40"  cssClass="cdrecordInput"></s:textfield>
					</td>
					<td>
						添加日期
					</td>
					<td>
						<sx:datetimepicker name="reportConfig.inputDate" cssClass="cdrecordInput" displayFormat="yyyy-MM-dd"/> 
					</td>
				</tr>	
				<tr>
					<td colspan=6>
						<sj:submit onCompleteTopics="complete" onBeforeTopics="clearError"  targets="show" cssClass="cdrecordButton" value="保存"/>
					</td>
				</tr>				
			</table>
				<s:hidden name="reportConfig.orderIndex"></s:hidden>
				<s:hidden name="reportConfig.id"></s:hidden>
				<s:hidden name="reportConfig.rtversion2"></s:hidden>
				<s:hidden name="reportConfig.bindId"></s:hidden>
			</s:form>
		</td>
	</tr>
	<tr>
		<td>
			<table width=100%>
				<tr>
					<td>
						<div id="headerRowGrid"></div>
					</td>
				</tr>
				<tr>
					<td>
						<div id="VerticalColumnGrid"></div>
					</td>
				</tr>				
			</table>
		</td>
	</tr>
</table>

</body>
</html>