<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/25/19
  Time: 12:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >
<html>
<head>
    <title><fmt:message key="orders.title"/></title>
</head>
<body>
<h3><fmt:message key="order.title"/></h3><br/>
<c:forEach var="order" items="${orders}">

    <fmt:message key="OrderId"/>: ${order.orderId}<br/>
    <fmt:message key="Created"/>: ${order.timestamp}<br/>
    <fmt:message key="Recipient"/>: ${order.firstName}<br/>
    <fmt:message key="Address"/>:<br/>
    <fmt:message key="Street"/>: ${order.street}<br/>
    <fmt:message key="House"/>: ${order.house}<br/>
    <fmt:message key="Apartment"/>: ${order.apartment}<br/>

    <table>
        <tr>
            <td><fmt:message key="DishName"/></td>
            <td><fmt:message key="DishQuantity"/></td>
            <td><fmt:message key="DishPrice"/></td>
        </tr>
        <c:forEach var="orderedDish" items="${order.orderList}" varStatus="status">
            <tr>
                <td><c:out value="${orderedDish.dishName}"/></td>
                <td><c:out value="${orderedDish.quantity}"/></td>
                <td><c:out value="${orderedDish.price}"/></td>
            </tr>
        </c:forEach>
    </table>
    <fmt:message key="TotalCost"/>:${order.totalCost}<br/>
    <br/>
</c:forEach>
</body>
</html>
</fmt:bundle>