<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/25/19
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><fmt:message key="check.title"/></title>
</head>
<body>
<fmt:message key="OrderId"/>: ${confirmedOrder.orderId}<br/>
<fmt:message key="Created"/>: ${confirmedOrder.timestamp}<br/>
<fmt:message key="Recipient"/>: ${confirmedOrder.firstName}<br/>
<fmt:message key="Address"/>:<br/>
<fmt:message key="Street"/>: ${confirmedOrder.street}<br/>
<fmt:message key="House"/>: ${confirmedOrder.house}<br/>
<fmt:message key="Apartment"/>: ${confirmedOrder.apartment}.<br/>

<table>
    <tr>
        <td><fmt:message key="DishName"/></td>
        <td><fmt:message key="DishQuantity"/></td>
        <td><fmt:message key="DishPrice"/></td>
    </tr>
    <c:forEach var="orderedDish" items="${confirmedOrder.orderList}" varStatus="status">
        <tr>
            <td><fmt:message key="${orderedDish.dishName}"/></td>
            <td><c:out value="${orderedDish.quantity}"/></td>
            <td><c:out value="${orderedDish.price}"/></td>
            <td><c:out value="${orderedDish.price * orderedDish.quantity}"/></td>
        </tr>
    </c:forEach>
</table>

<fmt:message key="TotalCost"/>: ${confirmedOrder.totalCost}<br/>
</body>
<a href="controller?command=show_menu"><fmt:message key="BackToMenu"/></a><br/>
<c:if test="${not empty nullpage}">
    <fmt:message key="${nullpage}" /><br/>
</c:if>

</html>
</fmt:bundle>