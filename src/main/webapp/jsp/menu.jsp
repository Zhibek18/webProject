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
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="menu.title"/></title>
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
</head>
<body>
<c:set var="currentPage" value="path.page.menu" scope="session"/>
<c:import url="navbar.jsp"/>

    <c:if test="${not empty added}">
        <div class="alert alert-success" role="alert">
            <fmt:message key="${added}" />
        </div>
    </c:if>

    <c:if test="${not empty notAdded}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="${notAdded}" />
        </div>
    </c:if>

    <c:if test="${not empty nullpage}">
        <div class="alert alert-danger" role="alert">
            <fmt:message key="${nullpage}" />
        </div>
    </c:if>
<div class="jumbotron">
        <h1><fmt:message key="Menu"/>:</h1>

    <a href="controller?command=show_order"><fmt:message key="ShowOrder"/></a><br/>

    <div class="card-deck">
        <c:forEach var="dish" items="${menu}" varStatus="status" step="1">
            <div class="card">
                <div class="card-body">
                    <img src="images/products/p4.png" class="img-responsive">
                    <h5 class="card-title"><fmt:message key="${dish.dishName}"/></h5>
                    <p class="price-container">
                        <span><c:out value="${dish.price}"/></span>
                    </p>
                    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                    <form name="addDish" method="post" action="controller">
                        <input type="hidden" name="command" value="add_dish"/>
                        <input type="hidden" name="dishName" value="${dish.dishName}"/>
                        <input class="btn btn-success" type="submit" value="<fmt:message key="add"/>"/>
                    </form>
                </div>
            </div>
        </c:forEach>

    </div>
</body>
</html>
</fmt:bundle>