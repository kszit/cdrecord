<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<HTML>
<%@ include file="/common/comm.jsp" %>
<style>
  .dynccenter{
cursor: w-resize;
background-image: url("<%=IMG_PATH %>/dynccenter/n0002.gif");
}
  </style>
<script language="javascript">
<!--resize the first frame-->
function doResize(obj){
  var tstr=obj.src;
  var tname=tstr.substring(tstr.lastIndexOf("/")+1,tstr.length);
  var vname=(tname=="dotright.gif"?"dotleft.gif":"dotright.gif");
  obj.src="<%=IMG_PATH %>/dynccenter/"+vname;
	with(window.parent.thisFrameSet)
        cols=(tname=="dotright.gif" ? "132,*,-1" : "20,*,-1");
}

function doTopFrameResize(obj,tag){
  var tstr=obj.src;
  var tname=tstr.substring(tstr.lastIndexOf("/")+1,tstr.length);
  var vname=(tname=="dotdown.gif"?"dotup.gif":"dotdown.gif");

  with(window.parent.topFrameSet){
      rows=(tname=="dotup.gif" ? "0,30,*" : "86,30,*");
  }
    if(tag=="1"){
    vname="dotdown.gif";
    rows="0,30,*";
  }
   obj.src="<%=IMG_PATH %>/dynccenter/"+vname;

}
function doTop(){
  var tstr=updownsrc.src;
  var tname=tstr.substring(tstr.lastIndexOf("/")+1,tstr.length);
  var vname="dotdown.gif";
  with(window.parent.topFrameSet){
    rows="0,30,*";
  }
  updownsrc.src="<%=IMG_PATH %>/dynccenter/"+vname;
}

var beginResize=0;
function doLeftFrameResize()
{
	if(beginResize==0) return true;
	if(window.event.button!=1)
	{
		beginResize=1;
		return true;
	}
	window.parent.thisFrameSet.cols=window.event.screenX+20+",*,-1";
	window.parent.leftFrameSet.cols="*,20";
}

function over(obj){
	obj.style.cursor="hand";
}
function out(obj){
	obj.style.cursor="default";
}

</script>

<META http-equiv=Content-Type content="text/html; charset=uft-8">

</head>

<BODY class="dynccenter" onmousedown='javascript:beginResize=1; document.selection.empty();' onmouseup='javascript:beginResize=0;' onmousemove=doLeftFrameResize()>
<table border=0 cellSpacing=0 cellPadding=0 height="16">
<tr>
  <td height="46" background="<%=IMG_PATH %>/dynccenter/n0001.gif">
    <p align="center"><img border="0" src="<%=IMG_PATH %>/dynccenter/handle_005.gif" width="21" height="30"><br>
    <img src="<%=IMG_PATH %>/dynccenter/dotup.gif" id="updownsrc" onmouseover="over(this)" onmouseout="out(this)" border="0" onclick="doTopFrameResize(this)" width="13" height="14"><br>
    <img src="<%=IMG_PATH %>/dynccenter/dotleft.gif" onmouseover="over(this)" onmouseout="out(this)" border="0" onclick="doResize(this)" width="13" height="14"><br>
    <img border="0" src="<%=IMG_PATH %>/dynccenter/handle_006.gif" width="21" height="17"></p>
  </td>
</tr>
</table>
</BODY>
</HTML>

