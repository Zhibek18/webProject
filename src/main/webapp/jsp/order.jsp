<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/24/19
  Time: 8:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Order</title>
</head>
<body>
<h3>Order list:</h3>
<c:if test="${empty orderId}">
    You haven't ordered anything yet.<br/>
</c:if>
<c:if test="${not empty orderId}">
    Order id: ${orderId}<br/>
    Recipient: ${order.firstName}<br/>
    Address: ${order.street} street ${order.house}, apartment number ${order.apartment}<br/>
    <form name="deleteOrder" method="post" action="controller">
        <input type="hidden" name="command" value="deleteOrder"/>
        <input type="submit" value="Cancel order"/>
        ${deleteOrderError}
    </form>
        <c:set var="totalCost" value="0" scope="request"/>
        <table>
            <tr>
                <td>name</td>
                <td>quantity</td>
                <td>price</td>
                <td>cost</td>
            </tr>
            <c:forEach var="orderedDish" items="${order.orderList}" varStatus="status">
                <tr>
                    <td><c:out value="${orderedDish.dishName}"/></td>
                    <td>
                        <form name="changeDishQuantity" method="post" action="controller">
                            <input type="hidden" name="command" value="changeDishQuantity"/>
                            <input type="hidden" name="dishName" value="${orderedDish.dishName}"/>
                            <input type="number" name="quantity" value="${orderedDish.quantity}" min="1" onchange="this.form.submit()"/>
                        </form>
                    <td>
                    <td><c:out value="${orderedDish.price}"/></td>
                    <td>
                        <c:set var="cost" value="${orderedDish.quantity * orderedDish.price}" scope="request"/>
                        <c:set var="totalCost" value="${totalCost + cost}" scope="request"/>
                        <c:out value="${cost}"/>
                    </td>
                    <td>
                        <form name="deleteOrderedDish" method="post" action="controller">
                            <input type="hidden" name="command" value="deleteOrderedDish"/>
                            <input type="hidden" name="dishName" value="${orderedDish.dishName}"/>
                            <input type="submit" value="delete"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    ${deleteDishError}<br/>
    ${changeQuantityError}<br/>
    Total cost: ${totalCost}<br/>
    <form name="updateOrderTotalCost" method="post" action="controller">
        <input type="hidden" name="command" value="updateOrderTotalCost"/>
        <input type="hidden" name="orderId" value="${order.orderId}"/>
        <input type="hidden" name="totalCost" value="${totalCost}"/>
        <input type="submit" value="Confirm"/>
    </form>
</c:if>

<a href="controller?command=showMenu">Back to menu</a><br/>
</body>
</html>
