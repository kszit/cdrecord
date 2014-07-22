<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<!-- 报表填报页面 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><s:text name="reportId" /></title>
<%@ include file="/common/comm.jsp"%>


<link rel="stylesheet" type="text/css"
	href="<%=EXTJS3_PATH%>/resources/css/ext-all.css" />

<link rel="stylesheet" type="text/css" href="<%=CSS_PATH%>/public.css" />
<link rel="stylesheet" type="text/css"
	href="<%=CSS_PATH%>/displaytag.css" />

<script type="text/javascript">  
	
</script>


</head>
<body>

	<div class="admin_r">
		<div class="admin_r_neirong">
			<div class="admin_r_left">
<s:if test="role.roleName=='填报人'">
				<div class="admin_r_left_a">
					<div class="admin_r_title">
						<span>报表上报情况(本月)</span>
					</div>
					<div class="admin_r_left_a_neirong">
						<ul>
							<s:iterator value='dataReportList'  id="report">
								<li>
								
								
								<s:if test="#report.verify==327"><!-- 未填报 -->
									<s:property value='#report.reportName'/>(<s:property value='#report.reportYear'/>年<s:property value='#report.periodsName'/>)&nbsp;&nbsp;&nbsp; 状态：<s:property value='#report.verifyName'/>
								</s:if>
								<s:else>
								<s:property value='#report.reportName'/>(<s:property value='#report.reportYear'/>年<s:property value='#report.periodsName'/>)&nbsp;&nbsp;&nbsp; 状态：<s:property value='#report.verifyName'/>
								</s:else>
								

								</li>
							</s:iterator>
						</ul>
						<s:if test="dataReportList.size==0">
							无
						</s:if>
					</div>
				</div>

				<div class="admin_r_left_b">
					<div class="admin_r_title">
						<span>未上报报表(近半年)</span>
					</div>
					<div class="admin_r_left_b_neirong">
						无
					</div>
				</div>
</s:if>
<s:elseif test="role.roleName=='审核人'">
				<div class="admin_r_left_a">
					<div class="admin_r_title">
						<span>待审核报表</span>
					</div>
					<div class="admin_r_left_b_neirong">
					<ul>
						<s:iterator value='dataReportList'  id="report"  status='reportId'>
							<li>
							<a href='#' onclick="window.open('<%=CONTEXT_PATH %>/reportData/reportData_seePage.do?dataReportModel.repotBindid=<s:property value='#report.repotBindid'/>&dataReportModel.bindId=<s:property value='#report.bindId'/> ');return false;">
							<s:property value='#report.reportName'/>(<s:property value='#report.reportYear'/>年<s:property value='#report.periodsName'/>) </a>
							</li>
						</s:iterator>
					</ul>
					<s:if test="dataReportList.size==0">
						无
					</s:if>
					</div>
				</div>
</s:elseif>
<s:elseif test="role.roleName=='报表汇总人'">
		
				<div class="admin_r_left_a">
					<div class="admin_r_title">
						<span>各单位报表上报情况(本月)</span>
					</div>
					<div class="admin_r_left_b_neirong">
					<ul>
						<s:iterator value='departmentAndReportList'  id="deptAndReport">
							<li>
							<s:property value='#deptAndReport.departmentName'/>&nbsp;&nbsp;&nbsp;
							总数量：<s:property value='#deptAndReport.allCount'/>&nbsp;&nbsp;&nbsp;
							已上报：<s:property value='#deptAndReport.appearCount'/>&nbsp;&nbsp;&nbsp;
							未上报：<s:property value='#deptAndReport.noAppearCount'/>&nbsp;&nbsp;&nbsp;
							
							<a href='#' onclick="window.open('<%=CONTEXT_PATH %>/reportData/reportData_appearReportListOfDeptAndMonthPage.do?dataReportModel.createDepBindid=<s:property value='#deptAndReport.deptBindid'/>');return false;">
								查看
							</a>
							 
							</li>
						</s:iterator>
					</ul>
					<s:if test="dataReportList.size==0">
						无
					</s:if>
					</div>
				</div>
</s:elseif>
			</div>
			
			<div class="admin_r_right">
				<div class="admin_r_right_a">
					<div class="admin_r_title">
						<span>公&nbsp;&nbsp;&nbsp;告</span>
					</div>
					<div class="admin_r_left_b_neirong">
						报表填报系统
					</div>

				</div>
			</div>
		</div>
	</div>
</body>
</html>