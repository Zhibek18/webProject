
<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/25/19
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Check</title>
</head>
<body>
Order id: ${confirmedOrder.orderId}<br/>
Created: ${confirmedOrder.timestamp}<br/>
Recipient: ${confirmedOrder.firstName}<br/>
Address: ${confirmedOrder.street} street ${confirmedOrder.house}, apartment number ${confirmedOrder.apartment}.<br/>

<table>
    <tr>
        <td>name</td>
        <td>quantity</td>
        <td>price</td>
    </tr>
    <c:forEach var="orderedDish" items="${confirmedOrder.orderList}" varStatus="status">
        <tr>
            <td><c:out value="${orderedDish.dishName}"/></td>
            <td><c:out value="${orderedDish.quantity}"/></td>
            <td><c:out value="${orderedDish.price}"/></td>
            <td><c:out value="${orderedDish.price * orderedDish.quantity}"/></td>
        </tr>
    </c:forEach>
</table>

Total cost: ${confirmedOrder.totalCost}<br/>
</body>
<a href="controller?command=showMenu">Back to menu</a><br/>
</html>
