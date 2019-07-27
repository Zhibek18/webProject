<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/23/19
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="changeAddress.title"/></title>
</head>
<body>
<form name="changeAddress" method="post" action="controller">
    <input type="hidden" name="command" value="change_address"/>
    <fmt:message key="Street"/>:
    <input type="text" name="street" value=""/><br/>
    <fmt:message key="House"/>:
    <input type="number" name="house" value=""/><br/>
    <fmt:message key="Apartment"/>:
    <input type="number" name="apartment" value=""/><br/>
    <c:if test="${not empty changeAddressError}">
        <fmt:message key="${changeAddressError}"/><br/>
    </c:if>
    <input type="submit" value="<fmt:message key="Save"/>"><br/>
</form>
</body>
</html>
</fmt:bundle>