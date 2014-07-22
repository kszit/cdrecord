<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="/common/comm.jsp" %>




</head>
<body>

<form id="ff" action="<%=CONTEXT_PATH %>/reportConfig/cellDefaultValue_oneCellValueSave.do" method="post">
			<s:hidden name='cellValue.reportBindid'></s:hidden>
			<s:hidden name='cellValue.hbindid'></s:hidden>
			<s:hidden name='cellValue.vbindid'></s:hidden>
            <table>
                <tr>
                    <td><textarea rows="5" cols="50" name="cellValue.defaultValue"></textarea></td>
                </tr>
                <tr>
                    <td><input type="submit" value="确定"></input></td>
                </tr>
            </table>
        </form>
    <script type="text/javascript">
        $(function(){
            $('#ff').form({
                success:function(data){
                	//$.messager.alert('Info', '设置成功', 'info');
                	 var id = '<s:text name="cellValue.hbindid"/>#<s:text name="cellValue.vbindid"/>';
                	 setNewDefaultValue(id,data);
                }
            });
        });
    </script>




<s:hidden name='reportConfig.bindId'></s:hidden>


</body>
</html>