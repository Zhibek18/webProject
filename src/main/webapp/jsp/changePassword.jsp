<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/22/19
  Time: 12:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="changePassword.title"/></title>
</head>
<body>
<form name="changePasswordForm" method="post" action="controller">
    <input type="hidden" name="command" value="changePassword"/>
    <fmt:message key="Login"/>:<br/>
    <input type="text" name="login" value=""/><br/>
    <fmt:message key="CurrentPassword"/>:<br/>
    <input type="password" name="oldPassword" value=""/><br/>
    <fmt:message key="NewPassword"/>:<br/>
    <input type="password" name="newPassword" value=""/><br/>
    ${updateError}<br/>
    <input type="submit" value="<fmt:message key="Save"/>"/>
</form>
</body>
</html>
</fmt:bundle>