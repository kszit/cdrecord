<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kszit.CDReport.cor.model.graph.GraphConfigSubModel"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/common/comm.jsp" %>

<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>

<script type="text/javascript" src="<%=JS_PATH %>/queryReport/selectReportOfAppear.js"></script>
<script type="text/javascript" src="<%=JS_PATH %>/queryReport/setQueryCondition.js"></script>
<script type="text/javascript" src="<%=JS_PATH %>/queryReport/setSort.js"></script>

<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />

<script type="text/javascript">

function getReportDataUrl(dept,period){
	var reportBindid = Ext.getDom('reportBindid').value;
	var hQueryCondition = Ext.getDom('hQueryCondition').value;
	var hSort = Ext.getDom('hSort').value;
	
	hSort
	var url = '/queryReport/queryReport_queryResultPage.do?queryCondition.reportBindid='+
			reportBindid+'&queryCondition.dept='+dept+'&queryCondition.periodWithYear='+period+
			'&queryCondition.hCondition='+hQueryCondition+"&queryCondition.hSort="+hSort;
	return url;
}

function loadReport(){
	var tabsWidth = 1000;
	var tabsHeight = 600;

	Ext.getDom('reports').innerHTML = '';
	
	var mainTabs = new Ext.TabPanel({
	    renderTo: 'reports',
	    height:tabsHeight,
	    plain:true,
	    defaults:{autoScroll: true}
	});
	
	
	var periodNames = Ext.getDom('selectPeriodName').innerHTML;
	var periodNameArr = periodNames.split(',');
	
	var periodIds = Ext.getDom('selectPeriod').value;
	var periodIdArr = periodIds.split('|');
	
	var periodLength = periodNameArr.length;
	
	
	
	
	var deptNames = Ext.getDom('selectDeptName').innerHTML;
	var deptNameArr = deptNames.split(',');
	
	var deptIds = Ext.getDom('selectDept').value;
	var deptIdArr = deptIds.split('|');
	
	var deptLength = deptNameArr.length;
	for(i=0;i<deptLength-1;i++){
		var deptName = deptNameArr[i];
		var deptId = deptIdArr[i];
		
		
		var deptTabs = mainTabs.add(new Ext.TabPanel({
			title:deptName,
		    activeTab: 0,
		    plain:true,
		    defaults:{autoScroll: true}
		}));
		

		
		for(j=0;j<periodLength-1;j++){
			var periodName = periodNameArr[j];
			var periodId = periodIdArr[j];
			deptTabs.add({
	            title: periodName,
	            iconCls: 'tabs',
	            autoLoad:getReportDataUrl(deptId,periodId),
	            closable:true
	        });
			
		}
		
	}
	

	
	mainTabs.activate(0);
}


</script>
</head>
<body>


<form>
	<input type='hidden' name='reportBindid' id='reportBindid' value=''/>
	<input type='hidden' name='reportBindid' id='selectDept' value=''/>
	<input type='hidden' name='reportBindid' id='selectPeriod' value=''/>
	<input type='hidden' name='reportBindid' id='hQueryCondition' value=''/>
	<input type='hidden' name='reportBindid' id='hSort' value=''/>
</form>


<table width=100%>
<tr>
<td>

<table>
	<tr><td>报表名称:</td><td><div id="reportBindidName"></div></td></tr>
	<tr><td>部门：</td><td><div id="selectDeptName"></div></td></tr>
	<tr><td>时期：</td><td><div id="selectPeriodName"></div></td></tr>
	<tr><td></td><td><button class="cdrecordButton" onclick="selectTableReport();">选择报表</button></td></tr>
</table>

</td>
<td>

<div id="hQueryConditionText"></div>
<button class="cdrecordButton" onclick="setQueryConfition();">设置查询条件</button>
<button class="cdrecordButton" onclick="chearQueryConfition();">清空查询条件</button>

</td>
<td>


<div id="hSortText"></div>
<button class="cdrecordButton" onclick="setSort();">设置排序条件</button>
<button class="cdrecordButton" onclick="clearSort();">清空排序条件</button>

</td>

<td>

<button class="cdrecordButton" onclick="loadReport();">刷新</button>

</td>
</tr>

</table>


<div id='reports'></div>


</body>
</html>