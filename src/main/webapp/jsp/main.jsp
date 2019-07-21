<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/20/19
  Time: 1:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>Welcome!</h3>
<hr/>
Hello, ${user}!
<hr/>
<form name="showUsers" method="post" action="controller">
    <input type="hidden" name="command" value="showUsers"/>
    <input type="submit" value="Show users"/><br/>
</form>
<form name="logout" method="post" action="controller">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="log out"/><br/>
</form>
<form name="changePassword" method="post" action="controller">
    <input type="hidden" name="command" value="forwardChangePassword"/>
    <input type="submit" value="Change password"/><br/>
</form>
<%--DOES NOT WORK WHITHOUT SESSIONS--%>
<form name="deleteUser" method="post" action="controller">
    <input type="hidden" name="command" value="deleteUser"/>
    <input type="hidden" name="login" value=${user}/>
    ${deleteError}<br/>
    <input type="submit" value="Delete account"/><br/>
</form>
</body>
</html>
