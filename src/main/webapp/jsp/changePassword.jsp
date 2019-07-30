<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/22/19
  Time: 12:52 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="changePassword.title"/></title>
</head>
<body>
<c:set var="currentPage" value="path.page.changePassword" scope="session"/>
<c:import url="navbar.jsp" charEncoding="utf-8"/>
<form name="changePasswordForm" method="post" action="controller">
    <input type="hidden" name="command" value="change_password"/>
    <fmt:message key="Login"/>:<br/>
    <input type="text" name="login" value=""/><br/>
    <fmt:message key="CurrentPassword"/>:<br/>
    <input type="password" name="oldPassword" value=""/><br/>
    <fmt:message key="NewPassword"/>:<br/>
    <input type="password" name="newPassword" value=""/><br/>
    <input type="submit" value="<fmt:message key="Save"/>"/>
    <c:if test="${not empty updateError}">
        <fmt:message key="${updateError}"/><br/>
    </c:if>
    <c:if test="${not empty nullpage}">
        <fmt:message key="${nullpage}" /><br/>
    </c:if>

</form>
</body>
</html>
</fmt:bundle>