<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/20/19
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
    <form name="loginForm" method="post" action="controller">
        <input type="hidden" name="command" value="login"/>
        Login:<br/>
        <input type="text" name="login" value=""/><br/>
        Password:<br/>
        <input type="password" name="password" value=""/><br/>
        ${errorLoginPathMessage}<br/>
        <input type="submit" value="Log in"/>
    </form>
    <form name="signUpLink" method="post" action="controller">
        <input type="hidden" name="command" value="forwardSignUp"/>
        <input type="submit" value="Sign Up"/>
    </form>
</body>
</html>
