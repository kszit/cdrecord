<%-- 
    Document   : login
    Created on : 2013-3-29, 14:50:43
    Author     : Administrator
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>信息管理系统</title>
        <script type="text/javascript">
            function checkLogin(loginFrom){
                var username = loginFrom.username.value;
                if(username==''){
                    alert("用户名不能为空");
                    return false;
                }
                var password = loginFrom.password.value;
                if(password==''){
                    alert("密码不能为空");
                    return false;
                }
                return true;
            }
            
        </script>
    </head>
    <body>
        <h1>用户登录</h1>
        <form name="loginFrom" id="loginFrom" action="../../persion2/Persion_login.do" method="post" onsubmit="return checkLogin(this)">
            用户名：<input type="text" name="username"></input>
            密码：<input type="password" name="password"></input>
            <button type="submit">登陆</button>
        </form>
    </body>
</html>
