<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/25/19
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title><fmt:message key="check.title"/></title>
    <link href="${pageContext.request.contextPath}/css/order.css" rel="stylesheet" type="text/css" >
</head>
<body>
<c:set var="currentPage" value="path.page.check" scope="session"/>
<c:import url="/jsp/navbar.jsp" charEncoding="utf-8"/>
    <div class="jumbotron">
        <c:if test="${not empty nullpage}">
            <div class="alert alert-danger" role="alert">
            <fmt:message key="${nullpage}" />
            </div>
        </c:if>

        <div class="row">
            <div class="col-md-6 col-centered">
                <div class="card">
                    <div class="card-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="card-body">
                                    <div class="news-content">
                                        <a href="#"><h6><fmt:message key="OrderId"/>: ${confirmedOrder.orderId}  </h6></a>
                                        <p><fmt:message key="Created"/>: ${confirmedOrder.timestamp}</p>

                                        <table class="table">

                                            <tbody>

                                            <tr>
                                                <th scope="row"><fmt:message key="Recipient"/></th>
                                                <td><c:out value="${confirmedOrder.firstName}"/></td>
                                            </tr>
                                            <tr>
                                                <th scope="row"><fmt:message key="Street"/></th>
                                                <td><c:out value="${confirmedOrder.street}"/></td>
                                            </tr>
                                            <tr>
                                                <th scope="row"><fmt:message key="House"/></th>
                                                <td><c:out value="${confirmedOrder.house}"/></td>
                                            </tr>
                                            <tr>
                                                <th scope="row"><fmt:message key="Apartment"/></th>
                                                <td><c:out value="${confirmedOrder.apartment}"/></td>
                                            </tr>
                                            <tr>
                                                <th scope="row"><fmt:message key="Phone"/></th>
                                                <td><c:out value="${confirmedOrder.phone}"/></td>
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
                                            <c:forEach var="orderedDish" items="${confirmedOrder.orderList}" varStatus="status">
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
                                        <div class="news-footer" align="right">
                                            <fmt:message key="TotalCost"/>:${confirmedOrder.totalCost}<br/>
                                            <a class="btn btn-primary" href="controller?command=show_menu"><fmt:message key="BackToMenu"/></a><br/>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</body>

</html>
</fmt:bundle>