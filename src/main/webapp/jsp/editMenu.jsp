<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/30/19
  Time: 11:55 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >
<html>
<head>
    <title><fmt:message key="menu.title"/></title>

</head>
<body>
    <c:set var="currentPage" value="path.page.admin" scope="session"/>
    <c:import url="adminNavbar.jsp" charEncoding="utf-8"/>
    <div class="jumbotron">

        <c:if test="${not empty editMenuError}">
            <div class="alert alert-danger" role="alert">
                <fmt:message key="${editMenuError}" />
            </div>
        </c:if>
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

        <form name="addDishToMenu" method="post" action="controller">
            <input type="hidden" name="command" value="add_dish_to_menu"/>
            <div class="col-md-4 mb-3">
                <input class="form-control" type="text" name="dishName" value="" placeholder="Name"/>
            </div>
            <div class="col-md-4 mb-3">
                <input class="form-control" type="text" name="dishPrice" value="" placeholder="Price"/>
            </div>
            <div class="col-md-4">
                <input class="btn btn-success" type="submit" value="<fmt:message key="Save"/>"/>
            </div>
        </form>
        <c:forEach var="dish" items="${menu}" varStatus="status" step="1">
            <form name="editMenu" method="post" action="controller">
                <input type="hidden" name="command" value="edit_dish_price"/>
                <div class="col-md-4 mb-3">
                    <input class="form-control" type="text" name="dishName" value="${dish.dishName}" readonly/>
                </div>
                <div class="col-md-4 mb-3">
                    <input class="form-control" type="number" step="any" name="dishPrice" value="${dish.price}"/>
                </div>
                <div class="col-md-4">
                    <input class="btn btn-primary" type="submit" value="<fmt:message key="Save"/>"/>
                </div>

            </form>
            <form name="deleteDishFromMenu" method="post" action="controller">
                <input type="hidden" name="command" value="delete_dish_from_menu"/>
                <input type="hidden" name="dishName" value="${dish.dishName}"/>
                <input class="btn btn-danger" type="submit" value="<fmt:message key="Delete"/>"/>
            </form>
        </c:forEach>
    </div>
</body>
</html>
</fmt:bundle>