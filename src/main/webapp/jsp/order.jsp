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
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="order.title"/></title>
</head>
<body>
    <c:set var="currentPage" value="path.page.order" scope="session"/>
    <c:import url="navbar.jsp"/>
        <div class="jumbotron">
        <h3><fmt:message key="OrderList"/>:</h3>
        <c:if test="${empty orderId}">
            <fmt:message key="EmptyMessage"/>
        </c:if>
        <c:if test="${not empty deleteDishError}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="${deleteDishError}" />
        </div>
        </c:if>
        <c:if test="${not empty changeQuantityError}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="${changeQuantityError}" />
        </div>
        </c:if>
        <c:if test="${not empty deleteOrderError}">
            <div class="alert alert-danger" role="alert">
                    <fmt:message key="${deleteOrderError}" />
            </div>
        </c:if>
        <c:if test="${not empty nullpage}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="${nullpage}" /><br/>
        </div>
        </c:if>
        <c:if test="${not empty orderConfirmError}">
            <div class="alert alert-danger" role="alert">
            <fmt:message key="${orderConfirmError}" />
            </div>
        </c:if>

            <c:if test="${not empty orderId}">
                <fmt:message key="OrderId"/>: ${orderId}<br/>
                <fmt:message key="Recipient"/>: ${order.firstName}<br/>
                <fmt:message key="Address"/>:<br/>
                <fmt:message key="Street"/>: ${order.street}<br/>
                <fmt:message key="House"/>: ${order.house}<br/>
                <fmt:message key="Apartment"/>: ${order.apartment}<br/>
                <fmt:message key="Phone"/>: ${order.phone}<br/>

            <form name="deleteOrder" method="post" action="controller">
                <input type="hidden" name="command" value="delete_order"/>
                <input class="btn btn-outline-danger" type="submit" value="<fmt:message key="CancelOrder"/>"/>

            </form>
                <c:set var="totalCost" value="0" scope="request"/>


            <table class="table table-borderless">
                <thead>
                <tr>
                    <th scope="col"><fmt:message key="DishName"/></th>
                    <th scope="col"><fmt:message key="DishQuantity"/></th>
                    <th scope="col"><fmt:message key="DishPrice"/></th>
                    <th scope="col"><fmt:message key="DishCost"/></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="orderedDish" items="${order.orderList}" varStatus="status">
                    <tr>
                        <th scope="row">
                            <c:if test="${language == 'ru_RU'}">
                                <h5 class="card-title">${orderedDish.dishNameRu}</h5>
                            </c:if>
                            <c:if test="${language == 'en_US'}">
                                <h5 class="card-title">${orderedDish.dishNameEn}</h5>
                            </c:if>
                        </th>
                        <td>
                            <form name="changeDishQuantity" method="post" action="controller">
                                <input type="hidden" name="command" value="change_dish_quantity"/>
                                <input type="hidden" name="dishName" value="${orderedDish.dishName}"/>
                                <input type="number" name="quantity" value="${orderedDish.quantity}" min="1" onchange="this.form.submit()"/>
                            </form>
                        </td>
                        <td><c:out value="${orderedDish.price}"/></td>
                        <td><c:set var="cost" value="${orderedDish.quantity * orderedDish.price}" scope="request"/>
                            <c:set var="totalCost" value="${totalCost + cost}" scope="request"/>
                            <c:out value="${cost}"/>
                        </td>
                        <td>
                            <form name="deleteOrderedDish" method="post" action="controller">
                                <input type="hidden" name="command" value="delete_ordered_dish"/>
                                <input type="hidden" name="dishName" value="${orderedDish.dishName}"/>
                                <input class="btn btn-outline-danger" type="submit" value="<fmt:message key="Delete"/>"/>
                            </form>
                        </td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

            <fmt:message key="TotalCost"/>: ${totalCost}<br/>

            <form name="updateOrderTotalCost" method="post" action="controller">
                <input type="hidden" name="command" value="update_order_total_cost"/>
                <input type="hidden" name="orderId" value="${order.orderId}"/>
                <input type="hidden" name="totalCost" value="${totalCost}"/>
                <input class="btn btn-success" type="submit" value="<fmt:message key="Confirm"/>"/>
            </form>
    </c:if>

    <a class="btn btn-primary" href="controller?command=show_menu"><fmt:message key="BackToMenu"/></a><br/>
        </div>
</body>
</html>
</fmt:bundle>