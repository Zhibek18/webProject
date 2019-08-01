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
<c:set var="currentPage" value="path.page.main" scope="session"/>
<c:import url="/jsp/navbar.jsp" charEncoding="utf-8"/>
<div class="jumbotron">
    <h3><fmt:message key="welcome" /></h3>
    <hr/>
    <fmt:message key="hello"/>, ${login}!
    <hr/>
        <c:if test="${not empty showMenuError}">
            <fmt:message key="${showMenuError}"/><br/>
        </c:if>


    <c:if test="${not empty nullpage}">
        <fmt:message key="${nullpage}" /><br/>
    </c:if>
</div>
</body>
</html>
</fmt:bundle>