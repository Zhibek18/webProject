<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/21/19
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >
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
    <input type="submit" value="<fmt:message key="SignUp"/>">
</form>
<c:if test="${not empty errorSignUpMessage}">
    <fmt:message key="${errorSignUpMessage}" /><br/>
</c:if>
<c:if test="${not empty nullpage}">
    <fmt:message key="${nullpage}" /><br/>
</c:if>

</body>
</html>
</fmt:bundle>