<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/20/19
  Time: 1:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent" prefix = "label." >
<html>
<head>
    <title><fmt:message key="main.title" /></title>
</head>
<body>
<form name="changeLanguage" method="post" action="controller">
    <input type="hidden" name="command" value="changeLanguage">
    <input type="hidden" name="page" value="/jsp/main.jsp">
    <select id="language" name="language" onchange="this.form.submit()">
        <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
        <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
</form>
<h3><fmt:message key="welcome" /></h3>
<hr/>
<fmt:message key="hello"/>, ${login}!
<hr/>
<a href="controller?command=showMenu"><fmt:message key="Menu"/></a><br/>
<c:if test="${isAdmin}">
    <a href="controller?command=showUsers"><fmt:message key="ShowUsers"/></a><br/>
    <a href="controller?command=showOrders"><fmt:message key="ShowOrders"/></a><br/>
</c:if>

<a href="controller?command=forwardChangePassword"><fmt:message key="ChangePassword"/></a><br/>
<c:if test="${not isAdmin}">
<a href="controller?command=forwardChangeAddress"><fmt:message key="ChangeAddress"/></a><br/>
</c:if>
<form name="logout" method="post" action="controller">
    <input type="hidden" name="command" value="logout"/>
    <input type="submit" value="<fmt:message key="logOut"/>"/><br/>
</form>

<form name="deleteUser" method="post" action="controller">
    <input type="hidden" name="command" value="deleteUser"/>
    ${deleteError}<br/>
    <input type="submit" value="<fmt:message key="deleteAccount"/>"/><br/>
</form>


</body>
</html>
</fmt:bundle>