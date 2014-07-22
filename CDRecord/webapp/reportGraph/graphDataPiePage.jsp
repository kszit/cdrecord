<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.kszit.CDReport.cor.model.graph.GraphConfigSubModel,com.kszit.CDReport.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/common/comm.jsp" %>



<link rel="stylesheet" type="text/css" href="<%=EXTJS3_PATH %>/resources/css/ext-all.css" />
<script type="text/javascript" src="<%=EXTJS3_PATH %>/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=EXTJS3_PATH %>/ext-all-debug.js"></script>


<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/public.css" />
<link rel="stylesheet" type="text/css" href="<%=CSS_PATH %>/displaytag.css" />

<script type="text/javascript">
/*!
 * Ext JS Library 3.0.0
 * Copyright(c) 2006-2009 Ext JS, LLC
 * licensing@extjs.com
 * http://www.extjs.com/license
 */
Ext.chart.Chart.CHART_URL = '<%=EXTJS3_PATH %>/resources/charts.swf';

Ext.onReady(function(){
    var store = new Ext.data.JsonStore({
        fields: ['season', 'total'],
        data: [{
            season: 'Summer',
            total: 150
        },{
            season: 'Fall',
            total: 245
        },{
            season: 'Winter',
            total: 117
        },{
            season: 'Spring',
            total: 184
        }]
    });
    
    new Ext.Panel({
        width: 400,
        height: 400,
        title: 'Pie Chart with Legend - Favorite Season',
        renderTo: 'container',
        items: {
            store: store,
            xtype: 'piechart',
            dataField: 'total',
            categoryField: 'season',
            //extra styles get applied to the chart defaults
            extraStyle:
            {
                legend:
                {
                    display: 'bottom',
                    padding: 5,
                    font:
                    {
                        family: 'Tahoma',
                        size: 13
                    }
                }
            }
        }
    });
});

</script>
</head>
<body>


<div id="container"></div>

</body>
</html>