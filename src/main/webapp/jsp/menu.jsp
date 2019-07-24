<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/23/19
  Time: 5:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Menu</title>
</head>
<body>
Menu:
<table>
    <c:forEach var="dish" items="${menu}" varStatus="status">
        <tr>
            <td><c:out value="${dish.dishName}"/></td>
            <td><c:out value="${dish.price}"/></td>
            <td>
                <form name="addDish" method="post" action="controller">
                    <input type="hidden" name="command" value="addDish"/>
                    <input type="hidden" name="dishName" value="${dish.dishName}"/>
                    <input type="submit" value="add"/>
                </form>
            </td>
            <td>
                ${addingStatus}
            </td>
        </tr>
    </c:forEach>
</table>
<a href="controller?command=showOrder">Show order</a><br/>

</body>
</html>
