<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/21/19
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign up</title>
</head>
<body>
<form name="signup" method="post" action="controller">
    <h3>Sign up</h3><br/>
    <input type="hidden" name="command" value="signup"/>
    Login:<br/>
    <input type="text" name="login" value=""/><br/>
    Password:<br/>
    <input type="password" name="password" value=""/><br/>
    ${errorSignUpMessage}<br/>
    <input type="submit" value="Sign up"></form>

</form>
</body>
</html>
