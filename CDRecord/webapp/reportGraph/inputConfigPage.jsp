<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>  
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>  
<%@page import="com.kszit.CDReport.cor.model.graph.GraphConfigSubModel,com.kszit.CDReport.util.*"%>
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


<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />

<script type="text/javascript" src="<%=JS_PATH %>/graph/selectTable.js"></script>
</head>
<body>


<script type="text/javascript">  

window.onbeforeunload = function(){
	window.opener.refresh();
}

function refresh()  //任意你想刷新时调用的方法
{
	this.location = this.location;
}
	var reportid = '<s:text name="reportConfig.reportBindidLink"/>';


	
    $.subscribe('clearError', function(event,data) {  
		//alert('提交数据');
    });  
    $.subscribe('complete', function(event,data) {  
        var json = $.parseJSON(event.originalEvent.request.responseText); 
       	//alert(event.originalEvent.request.responseText);
       
        $("#actionMessage").html("");//先将上次认证的错误消息清除掉  
        if(json.actionMessages && json.actionMessages.length>0){
			$.each(json.actionMessages,function(index,data){
				if(data!=''){
					alert('保存成功！');
					$("#insertConfig_graphCMModel_bindId").val(data);
				}
				
                //$("#actionMessage").append("<li>"+data+"</li>");  
				});  
            return;  
        }  
    });  

    function addReportTable(reportBindid){
    	var tableBindid = $("#insertConfig_graphCMModel_bindId").val();
    	if(tableBindid=='0'){
    		alert("请先保存表单");
    		return;
    	}else{
    		if(reportBindid!=''){
            	var requestParams = {
           			url:content_path+"/graphConfig/graphC_getGraphSubAction.do",
           			callback:function(options,success,response){//回调函数
           				var t = Ext.util.JSON.decode(response.responseText); 
           				graphSubId = t.graphSubId;
           				graphSubBindid = t.graphSubBindid;
           				graphSubdeptIds = t.graphSubdeptIds;
           				graphSubdeptNames = t.graphSubdeptNames;
           				graphSubTableBindid = t.graphSubTableBindid;
           				graphSubTableSelectCell = t.graphSubTableSelectCell;
           				graphSubStartDay = t.graphSubStartDay;
           				graphSubEndDay = t.graphSubEndDay;
           				
           				
           				selectTableReport(2);
           			},
           			params: { 
           				'graphCSModel.bindId':reportBindid
           			}
               	};
               Ext.Ajax.request(requestParams);//发送请求
    		}else{
    			graphSubBindid = "";
   				graphSubdeptIds = "";
   				graphSubdeptNames = "";
   				graphSubTableBindid = "";
   				graphSubTableSelectCell = "";
   				
    			selectTableReport(2);
    		}
    	}
    }
    
    /**
    *  删除
    *
    */
    function deleteGraphConfigSub(subBindid){
    	var setAppearDepartment = {
			url:content_path+"/graphConfig/graphC_deleteGraphSubAction.do",
   			callback:function(options,success,response){//回调函数
   				//alert(response.responseText);
   				//refresh();
   				this.location = this.location;
   			},
   			params: { 
   				'graphCSModel.bindId':subBindid
   			}
    	};
   		Ext.Ajax.request(setAppearDepartment);//发送请求
    }
    
 
    
    //设置图表可查询单位
    function setGraphQueryDept(){
    	typeOfDept = "1";
    	querytreeloadurl = content_path+'/graphConfig_json/setQueryDepartment.do?graphCMModel.bindId='+getGraphBindid()+"&id=";
    	showDepartmentTree();
    }
    //设置特定表的来源单位
    function setSpecificReportFromDept(){//graphSubdeptIds
    	typeOfDept = "2";
    	querytreeloadurl = content_path+'/graphConfig_json/setDeptOfReportSub.do?param1='+graphSubdeptIds+'&graphCMModel.bindId='+getGraphBindid()+"&id=";
    	showDepartmentTree();
    }
    //设置所有表来源就单位
    function setReportFromDept(){
    	typeOfDept = "3";
    	graphMaindeptIds = $("#insertConfig_graphCMModel_deptIds").val();
    	querytreeloadurl = content_path+'/graphConfig_json/setDeptOfReportMain.do?param1='+graphMaindeptIds+'&graphCMModel.bindId='+getGraphBindid()+"&id=";
    	showDepartmentTree();
    }
    //查看图表
    function seeGraphPic(){
    	var bindid = getGraphBindid();
    	window.open(content_path+'/graphData/graphD_graphDataPage.do?graphCMModel.bindId='+bindid);
    	return false;
    }
    //获得图表的bindid
    function getGraphBindid(){
    	return $("#insertConfig_graphCMModel_bindId").val();
    }
//====================================================可查询的单位
	var typeOfDept = "";//1:图表的查询单位列表  2：特定表数据来源-部门   3：整个图表的数据来源-部门
    var querytreeloadurl = "";
    var setQueryDepartmenttWin = null;
    function showDepartmentTree(){
    	  var querytree = new Ext.tree.TreePanel({
    	        title: '单位列表',
    	        height: 600,
    	        width: 300,
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
    	        dataUrl: querytreeloadurl,
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
                width:350,  
                height:500,  
                closeAction:'hide',
                plain: true,  
      			modal:true,
                items: querytree,
                buttons: [{
                    text:'确定',
                    disabled:false,
                    handler:function(){
                        var departmentIds = '', deptNames = '',selNodes = querytree.getChecked();
                        Ext.each(selNodes, function(node){
                        	deptNames += node.text+",";
                        	departmentIds += '|'+node.id;
                        });
                        departmentIds += '|';
                        if(typeOfDept==1){
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
                        }else if(typeOfDept==2){
                        	graphSubdeptIds = departmentIds;
                        	setgraphSubDeptDivContent(deptNames);
                        }else if(typeOfDept==3){
                        	$("#insertConfig_graphCMModel_deptIds").val(departmentIds);
                        	$("#insertConfig_graphCMModel_deptNames").val(deptNames);
                        }
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
    

    
    
    
    Ext.BLANK_IMAGE_URL = '../R_framework/ext-3_0_0/resources/images/default/s.gif';


</script>
<div id="hello-win"></div>
<div id="queryDetpDiv"></div>
<div id="collectDetpDiv"></div>

<div id="configDlg"></div>
<div id="configDlgv"></div>
<table width=100%>
	<tr>
		<td align='right'>
			<button class="cdrecordButton" onclick="seeGraphPic();">查看图表</button>
			<button class="cdrecordButton" onclick="addReportTable('');">添加表</button>
			<button class="cdrecordButton" onclick="setGraphQueryDept();">设定查询查询部门</button>
			<button class="cdrecordButton" onclick='seeReport();'>查看图表</button>
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
			<s:form action="/graphConfig_json/insertConfig.action" method="post" theme='simple'> 
			<s:hidden name="graphCMModel.id"></s:hidden>
			<s:hidden name="graphCMModel.bindId"></s:hidden>
			<s:hidden name="graphCMModel.createUserId"></s:hidden>
			<s:hidden name="graphCMModel.createDate"></s:hidden>
			<s:hidden name="graphCMModel.deptIds"></s:hidden>
			<table>
				<tr>
					<td>
						名称
					</td>
					<td>
						<s:textfield name="graphCMModel.name" size="40"  cssClass="cdrecordInput"/>
					</td>
					<td>
						类型
					</td>
					<td>
						<s:select cssClass="cdrecordInput" onchange=''
							name='graphCMModel.graphType'
					       	list="graphTypeList"
					       	listKey="bindId"
					       	listValue="name"
					       	value="graphCMModel.rttype"
				      	/>
					</td>
				<td>
						开始日期
					</td>
					<td>
						<s:textfield name="graphCMModel.startDay" size="40"  cssClass="cdrecordInput"/>
					</td>
					<td>
						截止日期
					</td>
					<td>
						<s:textfield name="graphCMModel.endDay" size="40"  cssClass="cdrecordInput"/>
					</td>
					</tr>
					<tr>
					<td>
						报表单位
					</td>
					<td colspan='3'>
						<button class="cdrecordButton" onclick="setReportFromDept();">选择</button><s:textfield name="graphCMModel.deptNames" size="40"  cssClass="cdrecordInput"/>
					</td>
								<td>
						X轴
					</td>
					<td>
						<s:textfield name="graphCMModel.XAxis" size="40"  cssClass="cdrecordInput"/>
					</td>
								<td>
						Y轴
					</td>
					<td>
						<s:textfield name="graphCMModel.YAxis" size="40"  cssClass="cdrecordInput"/>
					</td>
					</tr>
				<tr>
					<td colspan=6>
						<sj:submit onCompleteTopics="complete" onBeforeTopics="clearError"  targets="show" cssClass="cdrecordButton" value="保存"/>
					</td>
				</tr>				
			</table>
			</s:form>
		</td>
	</tr>
</table>
<%

    org.displaytag.decorator.CheckboxTableDecorator decorator = new org.displaytag.decorator.CheckboxTableDecorator();
    decorator.setId("id");
    decorator.setFieldName("id");
    pageContext.setAttribute("checkboxDecorator", decorator);

%>
	<display:table name="graphConfigSubList" id="reportData" decorator="checkboxDecorator" class='list' cellspacing="1" cellpadding="1" pagesize="10" requestURI="/reportData/reportDat/reportData_listPage.action" export="true"  form="reportDataForm" excludedParams="id">
	<%
	GraphConfigSubModel model = (GraphConfigSubModel)reportData;
	%>     
		<display:column property="checkbox" style="width:2%"  title="选择"/>

		<display:column property="tableName" title="表名称" style="width:7%" ></display:column>
		<display:column property="deptNames" title="部门名称" style="width:10%" ></display:column>
		<display:column title="时间段" style="width:10%" >
			<%
				String startDay = model.getStartDay()+"";
				String endDay = model.getEndDay()+"";
				String startToEnd = (startDay==null?"":startDay)+"-->"+(endDay==null?"":endDay);
				//startToEnd = startToEnd==null?"":startToEnd;
			%>
			<%=startToEnd %>
		</display:column>
		<display:column title="数据个数" style="width:5%" >
			<%
				String selectedCells = model.getTableCells();
			
			String num = StringUtil.includeCharsNum(selectedCells,"$")+"";
			%>
			<%=num %>
		</display:column>
			
	     <display:column title="操作" style="width:10%" >
	     	   
	     <a href='#' onclick="addReportTable(<%=model.getBindId() %>);return false;">编辑</a>
	     &nbsp;&nbsp;
	     <a href='#' onclick="window.open(content_path+'/reportData/reportData_seePage.do?dataReportModel.repotBindid=<%=model.getBindId() %>&dataReportModel.bindId=<%=model.getBindId() %>');return false;">查看</a>
	     	&nbsp;&nbsp;
	     <a href='#' onclick="deleteGraphConfigSub(<%=model.getBindId() %>);return false;">删除</a>
	     	&nbsp;&nbsp;
	     	  
	     
	     
	     </display:column>
	</display:table>


</body>
</html>