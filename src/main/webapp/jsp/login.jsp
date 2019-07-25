<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/20/19
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="en_US" scope="session" />
<fmt:bundle basename="pagecontent" prefix = "label." >
<html>
<head>
    <title><fmt:message key="login.title" /></title>
</head>
<body>
    <form name="loginForm" method="post" action="controller">
        <input type="hidden" name="command" value="login"/>
        <fmt:message key="Login" /><br/>
        <input type="text" name="login" value=""/><br/>
        <fmt:message key="Password" /><br/>
        <input type="password" name="password" value=""/><br/>
        ${errorLoginPathMessage}<br/>
        ${nullpage}<br/>
        <input type="submit" value="<fmt:message key="logIn" />"/>
    </form>
    <form name="signUpLink" method="post" action="controller">
        <input type="hidden" name="command" value="forwardSignUp"/>
        <input type="submit" value="<fmt:message key="SignUp"/>"/>
    </form>
</body>

</html>
</fmt:bundle>