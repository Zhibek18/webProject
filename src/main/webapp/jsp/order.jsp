<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/24/19
  Time: 8:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="ru_RU" scope="session" />
<fmt:bundle basename="pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="order.title"/></title>
</head>
<body>
<h3><fmt:message key="OrderList"/>:</h3>
<c:if test="${empty orderId}">
    <fmt:message key="EmptyMessage"/><br/>
</c:if>
<c:if test="${not empty orderId}">
    <fmt:message key="OrderId"/>: ${orderId}<br/>
    <fmt:message key="Recipient"/>: ${order.firstName}<br/>
    <fmt:message key="Address"/>:<br/>
    <fmt:message key="Street"/>: ${order.street}<br/>
    <fmt:message key="House"/>: ${order.house}<br/>
    <fmt:message key="Apartment"/>: ${order.apartment}<br/>
    <form name="deleteOrder" method="post" action="controller">
        <input type="hidden" name="command" value="deleteOrder"/>
        <input type="submit" value="<fmt:message key="CancelOrder"/>"/>
        ${deleteOrderError}
    </form>
        <c:set var="totalCost" value="0" scope="request"/>
        <table>
            <tr>
                <td><fmt:message key="DishName"/></td>
                <td><fmt:message key="DishQuantity"/></td>
                <td><fmt:message key="DishPrice"/></td>
                <td><fmt:message key="DishCost"/></td>
            </tr>
            <c:forEach var="orderedDish" items="${order.orderList}" varStatus="status">
                <tr>
                    <td><c:out value="<fmt:message key=${orderedDish.dishName}/>"/></td>
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
                            <input type="submit" value="<fmt:message key="Delete"/>"/>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
    ${deleteDishError}<br/>
    ${changeQuantityError}<br/>
    <fmt:message key="TotalCost"/>: ${totalCost}<br/>
    <form name="updateOrderTotalCost" method="post" action="controller">
        <input type="hidden" name="command" value="updateOrderTotalCost"/>
        <input type="hidden" name="orderId" value="${order.orderId}"/>
        <input type="hidden" name="totalCost" value="${totalCost}"/>
        <input type="submit" value="<fmt:message key="Confirm"/>"/>
    </form>
</c:if>

<a href="controller?command=showMenu"><fmt:message key="BackToMenu"/></a><br/>
</body>
</html>
</fmt:bundle>