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
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

    <html>
<head>
    <title><fmt:message key="main.title" /></title>
</head>
<body>
<c:import url="/jsp/navbar.jsp" charEncoding="utf-8"/>
<form name="changeLanguage" method="post" action="controller">
    <input type="hidden" name="command" value="change_language">
    <input type="hidden" name="page" value="path.page.main" >
    <select id="language" name="language" onchange="this.form.submit()">
        <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
        <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
    </select>
</form>
<h3><fmt:message key="welcome" /></h3>
<hr/>
<fmt:message key="hello"/>, ${login}!
<hr/>
    <c:if test="${not empty showMenuError}">
        <fmt:message key="${showMenuError}"/><br/>
    </c:if>

<a href="controller?command=forward_change_address"><fmt:message key="ChangeAddress"/></a><br/>

<form name="deleteUser" method="post" action="controller">
    <input type="hidden" name="command" value="delete_user"/>
    <input type="submit" value="<fmt:message key="deleteAccount"/>"/><br/>
</form>
<c:if test="${not empty deleteUserError}">
    <fmt:message key="${deleteUserError}" /><br/>
</c:if>
<c:if test="${not empty nullpage}">
    <fmt:message key="${nullpage}" /><br/>
</c:if>

</body>
</html>
</fmt:bundle>