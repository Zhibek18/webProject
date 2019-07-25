<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/25/19
  Time: 12:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<c:forEach var="order" items="${orders}">

    Order id: ${order.orderId}<br/>
    Created: ${order.timestamp}<br/>
    Recipient: ${order.firstName}<br/>
    Address: ${order.street} street ${order.house}, apartment number ${order.apartment}.<br/>

    <table>
        <tr>
            <td>name</td>
            <td>quantity</td>
            <td>price</td>
        </tr>
        <c:forEach var="orderedDish" items="${order.orderList}" varStatus="status">
            <tr>
                <td><c:out value="${orderedDish.dishName}"/></td>
                <td><c:out value="${orderedDish.quantity}"/></td>
                <td><c:out value="${orderedDish.price}"/></td>
            </tr>
        </c:forEach>
    </table>
    Total cost:${order.totalCost}<br/>
</c:forEach>
</body>
</html>
