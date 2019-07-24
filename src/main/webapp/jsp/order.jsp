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
Order id: ${orderId}
<form name="deleteOrder" method="post" action="controller">
    <input type="hidden" name="command" value="deleteOrder"/>
    <input type="submit" value="Cancel order"/>
    ${deleteOrderError}
</form>
<table>
    <c:forEach var="orderedDish" items="${orderList}" varStatus="status">
        <tr>
            <td><c:out value="${orderedDish.dishName}"/></td>
            <td><c:out value="${orderedDish.quantity}"/></td>
            <td>
                <form name="deleteOrderedDish" method="post" action="controller">
                    <input type="hidden" name="command" value="deleteOrderedDish"/>
                    <input type="hidden" name="dishName" value="${orderedDish.dishName}"/>
                    <input type="submit" value="delete"/>
                </form>
            </td>
            <td>
                ${deleteDishError}
            </td>
        </tr>
    </c:forEach>
</table>
<a href="controller?command=showMenu">Back to menu</a><br/>
</body>
</html>
