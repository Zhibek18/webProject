<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/21/19
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru_RU" scope="session" />
<fmt:bundle basename="pagecontent" prefix = "label." >
<html>
<head>
    <title><fmt:message key="signUp.title"/></title>
</head>
<body>
<form name="signup" method="post" action="controller">
    <h3><fmt:message key="SignUp"/></h3><br/>
    <input type="hidden" name="command" value="signup"/>
    <fmt:message key="Login"/>:<br/>
    <input type="text" name="login" value=""/><br/>
    <fmt:message key="Password"/>:<br/>
    <input type="password" name="password" value=""/><br/>
    <fmt:message key="Name"/>:<br/>
    <input type="text" name="first_name" value=""><br/>
    <fmt:message key="Street"/>:<br/>
    <input type="text" name="street" value=""><br/>
    <fmt:message key="House"/><br/>
    <input type="number" name="house" value=""><br/>
    <fmt:message key="Apartment"/><br/>
    <input type="number" name="apartment" value=""><br/>
    <fmt:message key="Phone"/>:<br/>
    <input type="text" name="phone" value=""><br/>
    ${errorSignUpMessage}<br/>
    <input type="submit" value="<fmt:message key="signUp"/>">
</form>


</body>
</html>
</fmt:bundle>