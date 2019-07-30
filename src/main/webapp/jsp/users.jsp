<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/21/19
  Time: 6:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="users.title"/></title>
</head>
<body>
<c:set var="currentPage" value="path.page.users" scope="session"/>
<c:import url="adminNavbar.jsp" charEncoding="utf-8"/>
<h3><fmt:message key="users.title"/></h3><br/>
<table>
    <c:forEach var="userName" items="${userNames}" varStatus="status">
        <tr>
            <td><c:out value="${userName}"/></td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty nullpage}">
    <fmt:message key="${nullpage}" /><br/>
</c:if>
</body>
</html>
</fmt:bundle>