<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/22/19
  Time: 12:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="changePasswordForm" method="post" action="controller">
    <input type="hidden" name="command" value="changePassword"/>
    Login:<br/>
    <input type="text" name="login" value=""/><br/>
    Current password:<br/>
    <input type="password" name="oldPassword" value=""/><br/>
    New password:<br/>
    <input type="password" name="newPassword" value=""/><br/>
    ${updateError}<br/>
    <input type="submit" value="save"/>
</form>
</body>
</html>
