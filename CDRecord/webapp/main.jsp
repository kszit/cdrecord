<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<HTML>
<HEAD>
<TITLE>信息系统</TITLE>

<META content=no-cache http-equiv=pragma>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</HEAD>
<%@ include file="/common/comm.jsp" %>
<FRAMESET id=topFrameSet frameSpacing=0 cols=* frameBorder=0 rows=80,* width="765">
	<FRAME noResize src="mainPage/main_headerPage.action" frameBorder=0 name=topFrame scrolling=no>
	<FRAMESET id=thisFrameSet frameSpacing=2 border=0 cols=180,*,-1 frameBorder=no>
		<FRAMESET id=leftFrameSet border=0 cols=*,20 frameBorder=0>
			<FRAME id=leftFrame noResize marginHeight=0 border=0 src="menu/menu.jsp" frameBorder=0 name=leftFrame marginWidth=0>
			<FRAME id=dynccenter noResize marginHeight=0 border=0 src="common/dynccenter.jsp" frameBorder=0 name=dynccenter marginWidth=0 scrolling=no>
		</FRAMESET>
		<FRAME src="<%=CONTEXT_PATH %>/reportData/reportData_userMainPage.do" frameBorder=0 name=mainFrame>
		<FRAME noResize src="#" frameBorder=0 name=funFrame>
	</FRAMESET>
</FRAMESET>
</HTML>