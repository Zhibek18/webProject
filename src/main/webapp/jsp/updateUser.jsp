<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/23/19
  Time: 2:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/messages_ru.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/signup-form.js"></script>
    <title><fmt:message key="updateUser.title"/></title>
</head>
<body>
<c:set var="currentPage" value="path.page.updateUser" scope="session"/>
<c:import url="navbar.jsp" charEncoding="utf-8"/>
<c:if test="${not empty updateUserError}">
    <div class="alert alert-danger" role="alert">
        <fmt:message key="${updateUserError}"/>
    </div>
</c:if>
<c:if test="${not empty nullpage}">
    <div class="alert alert-danger" role="alert">
        <fmt:message key="${nullpage}" />
    </div>
</c:if>
<c:if test="${not empty updatePasswordError}">
    <div class="alert alert-danger" role="alert">
        <fmt:message key="${updatePasswordError}"/>
    </div>
</c:if>
<c:if test="${not empty deleteUserError}">
    <div class="alert alert-danger" role="alert">
        <fmt:message key="${deleteUserError}" />
    </div>
</c:if>
<div class="jumbotron">

    <h3><fmt:message key="editProfile"/> </h3>
    <form name="deleteUser" method="post" action="controller" align="right">
        <input type="hidden" name="command" value="delete_user"/>
        <input class="btn btn-danger" type="submit" value="<fmt:message key="deleteAccount"/>"/><br/>
    </form>

    <form name="updateUser" method="post" action="controller" id="updateUserForm">
        <input type="hidden" name="command" value="update_user"/>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <fmt:message key="Name"/>
                <input type="text" name="firstName" class="form-control" placeholder="<fmt:message key="Name"/>" value="${user.firstName}" required>
            </div>
            <div class="col-md-6 mb-3">
                <fmt:message key="Phone"/>
                <input type="text" name="phone" class="form-control" placeholder="<fmt:message key="Phone"/>" value="${user.phone}" required>
            </div>

        </div>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <fmt:message key="Street"/>:
                <input type="text" name="street" id= street value="${user.street}" class="form-control" placeholder="<fmt:message key="Street"/>" required>
            </div>
            <div class="col-md-3 mb-3">
                <fmt:message key="House"/>:
                <input type="text" name="house" value="${user.house}" class="form-control" placeholder="<fmt:message key="House"/>" required>
            </div>
            <div class="col-md-3 mb-3">
                <fmt:message key="Apartment"/>:
                <input type="number" name="apartment" value="${user.apartment}" class="form-control" placeholder="<fmt:message key="Apartment"/>" required>
            </div>
        </div>
        <div align="right">
            <button type="submit" class="btn btn-primary"><fmt:message key="Save"/></button>
        </div>

    </form>
    <h5><fmt:message key="changePassword.title"/>:</h5>
    <form name="changePasswordForm" method="post" action="controller" id="updatePasswordForm">
        <input type="hidden" name="command" value="change_password"/>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <fmt:message key="CurrentPassword"/>:<br/>
                <input class="form-control" type="password" name="oldPassword" value="" required/><br/>
            </div>
            <div class="col-md-6 mb-3">
                <fmt:message key="NewPassword"/>:<br/>
                <input class="form-control" type="password" name="newPassword" value="" required/><br/>
            </div>
        </div>
        <div align="right">
            <input class="btn btn-primary" type="submit" value="<fmt:message key="Save"/>"/>
        </div>
    </form>

</div>
</body>
</html>
</fmt:bundle>