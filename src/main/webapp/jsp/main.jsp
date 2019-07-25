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
Hello, ${login}!
<hr/>
<a href="controller?command=showMenu">Menu</a><br/>
<a href="controller?command=showUsers">Show users</a><br/>
<a href="controller?command=showOrders">Show orders</a><br/>
<a href="controller?command=forwardChangePassword">Change password</a><br/>
<a href="controller?command=forwardChangeAddress">Change address</a><br/>

<form name="logout" method="post" action="controller">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="log out"/><br/>
</form>

<form name="deleteUser" method="post" action="controller">
    <input type="hidden" name="command" value="deleteUser"/>
    ${deleteError}<br/>
    <input type="submit" value="Delete account"/><br/>
</form>


</body>
</html>
