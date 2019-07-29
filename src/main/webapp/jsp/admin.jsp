<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/29/19
  Time: 3:52 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <title>Admin</title>
<%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous"/>--%>
<%--    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>--%>
<%--    <link href="${pageContext.request.contextPath}/css/order.css" rel="stylesheet" type="text/css" >--%>
</head>
<body>

    <c:import url="adminNavbar.jsp" charEncoding="utf-8"/>


    <a href="controller?command=forward_change_password"><fmt:message key="ChangePassword"/></a><br/>
    <h3><fmt:message key="orders.title"/></h3><br/>
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
                    <td><fmt:message key="${orderedDish.dishName}"/></td>
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