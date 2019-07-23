<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/23/19
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form name="changeAddress" method="post" action="controller">
    <input type="hidden" name="command" value="changeAddress"/>
    Street:
    <input type="text" name="street" value=""/><br/>
    House:
    <input type="number" name="house" value=""/><br/>
    Apartment:
    <input type="number" name="apartment" value=""/><br/>
    ${changeAddressError}<br/>
    <input type="submit" value="Change address"><br/>
</form>
</body>
</html>
