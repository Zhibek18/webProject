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
    <c:set var="currentPage" value="path.page.editMenu" scope="session"/>
    <c:import url="adminNavbar.jsp" charEncoding="utf-8"/>
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
    <div class="jumbotron">
        <h3><fmt:message key="EditMenu"/> </h3>
        <form name="addDishToMenu" method="post" action="controller">
            <h5><fmt:message key="AddDish"/></h5>
            <input type="hidden" name="command" value="add_dish_to_menu"/>
            <div class="form-row">
                <div class="col-md-3 mb-3">
                    <input class="form-control" type="text" name="dishName" value="" placeholder="<fmt:message key="DishName"/>" required/>
                </div>
                <div class="col-md-3 mb-3">
                    <input class="form-control" type="text" name="dishNameRu" value="" placeholder="<fmt:message key="DishNameRu"/>" required/>
                </div>
                <div class="col-md-3 mb-3">
                    <input class="form-control" type="text" name="dishNameEn" value="" placeholder="<fmt:message key="DishNameEn"/>" required/>
                </div>
                <div class="col-md-3 mb-3">
                    <input class="form-control" type="text" name="dishPrice" value="" placeholder="<fmt:message key="DishPrice"/>" required/>
                </div>
                <div class="col-md-6 mb-3">
                    <textarea class="form-control md-textarea" name="descriptionRu" placeholder="<fmt:message key="DescriptionRu"/>" rows="3" required></textarea>
                </div>
                <div class="col-md-6 mb-3">
                    <textarea class="form-control md-textarea" name="descriptionEn" placeholder="<fmt:message key="DescriptionEn"/>" rows="3" required></textarea>
                </div>
                <div class="col-md-4">
                    <input class="btn btn-success" type="submit" value="<fmt:message key="add"/>"/>
                </div>
            </div>
        </form>
        <h5><fmt:message key="Menu"/> </h5>
        <c:forEach var="dish" items="${menu}" varStatus="status">
            <form name="editMenu" method="post" action="controller">
                <input type="hidden" name="command" value="edit_dish"/>
                <div class="form-row">
                    <div class="col-md-3 mb-3">
                        <fmt:message key="DishName"/>:<br/>
                        <input class="form-control" type="text" name="dishName" value="${dish.dishName}" readonly/>
                    </div>
                    <div class="col-md-3 mb-3">
                        <fmt:message key="DishNameRu"/>:<br/>
                        <input class="form-control" type="text" name="dishNameRu" value="${dish.dishNameRu}" placeholder="<fmt:message key="DishNameRu"/>" required/>
                    </div>
                    <div class="col-md-3 mb-3">
                        <fmt:message key="DishNameEn"/>:<br/>
                        <input class="form-control" type="text" name="dishNameEn" value="${dish.dishNameEn}" placeholder="<fmt:message key="DishNameEn"/>" required/>
                    </div>
                    <div class="col-md-3 mb-3">
                        <fmt:message key="DishPrice"/>:<br/>
                        <input class="form-control" type="number" step="0.01" name="dishPrice" value="${dish.price}" placeholder="<fmt:message key="DishPrice"/>" required/>
                    </div>
                    <div class="col-md-6 mb-3">
                        <fmt:message key="DescriptionRu"/>:<br/>
                        <textarea class="form-control md-textarea" name="descriptionRu" placeholder="<fmt:message key="DescriptionRu"/>" rows="3" required>${dish.descriptionRu}</textarea>
                    </div>
                    <div class="col-md-6 mb-3">
                        <fmt:message key="DescriptionEn"/>:<br/>
                        <textarea class="form-control md-textarea" name="descriptionEn" placeholder="<fmt:message key="DescriptionEn"/>" rows="3" required>${dish.descriptionEn}</textarea>
                    </div>

                    <div class="col-md-4">
                        <input class="btn btn-primary" type="submit" value="<fmt:message key="Save"/>" />
                    </div>
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