<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/20/19
  Time: 1:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

    <html>
<head>
    <title><fmt:message key="main.title" /></title>
    <link href="${pageContext.request.contextPath}/css/order.css" rel="stylesheet" type="text/css" >
</head>
<body>
<c:set var="currentPage" value="path.page.main" scope="session"/>
<c:import url="/jsp/navbar.jsp" charEncoding="utf-8"/>
<c:if test="${not empty showMenuError}">
    <fmt:message key="${showMenuError}"/><br/>
</c:if>

<c:if test="${not empty nullpage}">
    <fmt:message key="${nullpage}" /><br/>
</c:if>
<div class="jumbotron">
    <h3><fmt:message key="welcome" /></h3>
    <ctg:hello name="${user.firstName}" language="${language}"/>

    <div class="container">
        <div class="row">
            <div class="col-md-6 col-centered">
                <h2 align="center"><fmt:message key="orders.title"/></h2>
            </div>
        </div>
        <c:forEach var="order" items="${orders}">
            <div class="row">
                <div class="col-md-6 col-centered">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card-body">
                                        <div class="news-content">
                                            <h6><fmt:message key="OrderId"/>: ${order.orderId}  </h6>
                                            <p><fmt:message key="Created"/>: ${order.timestamp}</p>
                                            <p><fmt:message key="Status"/>: <fmt:message key="${order.status}"/></p>
                                            <table class="table">
                                                <tbody>
                                                <tr>
                                                    <th scope="row"><fmt:message key="Recipient"/></th>
                                                    <td><c:out value="${order.firstName}"/></td>
                                                </tr>
                                                <tr>
                                                    <th scope="row"><fmt:message key="Street"/></th>
                                                    <td><c:out value="${order.street}"/></td>
                                                </tr>
                                                <tr>
                                                    <th scope="row"><fmt:message key="House"/></th>
                                                    <td><c:out value="${order.house}"/></td>
                                                </tr>
                                                <tr>
                                                    <th scope="row"><fmt:message key="Apartment"/></th>
                                                    <td><c:out value="${order.apartment}"/></td>
                                                </tr>
                                                <tr>
                                                    <th scope="row"><fmt:message key="Phone"/></th>
                                                    <td><c:out value="${order.phone}"/></td>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th scope="col"><fmt:message key="DishName"/></th>
                                                    <th scope="col"><fmt:message key="DishQuantity"/></th>
                                                    <th scope="col"><fmt:message key="DishPrice"/></th>
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
                                                        <td><c:out value="${orderedDish.quantity}"/></td>
                                                        <td><c:out value="${orderedDish.price}"/></td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                            <div class="news-footer">
                                                <fmt:message key="TotalCost"/>:${order.totalCost}<br/>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
</fmt:bundle>