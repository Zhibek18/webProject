<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/23/19
  Time: 5:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >
    <fmt:setLocale value="${language}" scope="session"/>
<html>
<head>
    <title><fmt:message key="menu.title"/></title>
</head>
<body>
<fmt:message key="Menu"/>:
<table>
    <tr>
        <td><fmt:message key="DishName"/></td>
        <td><fmt:message key="DishPrice"/></td>
    </tr>
    <c:forEach var="dish" items="${menu}" varStatus="status">
        <tr>
            <td><fmt:message key="${dish.dishName}"/></td>
            <td><c:out value="${dish.price}"/></td>
            <td>
                <form name="addDish" method="post" action="controller">
                    <input type="hidden" name="command" value="add_dish"/>
                    <input type="hidden" name="dishName" value="${dish.dishName}"/>
                    <input type="submit" value="<fmt:message key="add"/>"/>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<c:if test="${not empty addingStatus}">
    <fmt:message key="${addingStatus}" /><br/>
</c:if>
<a href="controller?command=show_order"><fmt:message key="ShowOrder"/></a><br/>

</body>
</html>
</fmt:bundle>