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
    <title><fmt:message key="updateUser.title"/></title>
</head>
<body>
<c:set var="currentPage" value="path.page.updateUser" scope="session"/>
<c:import url="navbar.jsp" charEncoding="utf-8"/>

<div class="jumbotron">
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
    <h3><fmt:message key="editProfile"/> </h3>
    <form name="deleteUser" method="post" action="controller" align="right">
        <input type="hidden" name="command" value="delete_user"/>
        <input class="btn btn-danger" type="submit" value="<fmt:message key="deleteAccount"/>"/><br/>
    </form>
    <form class="needs-validation" novalidate name="updateUser" method="post" action="controller">
        <input type="hidden" name="command" value="update_user"/>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="validationCustom01"><fmt:message key="Name"/></label>
                <input type="text" name="firstName" class="form-control" id="validationCustom01" placeholder="Name" value="${user.firstName}" required>
                <div class="valid-feedback">
                    Looks good!
                </div>
            </div>
            <div class="col-md-6 mb-3">
                <label for="validationCustom02"><fmt:message key="Phone"/></label>
                <input type="text" name="phone" class="form-control" id="validationCustom02" placeholder="Phone" value="${user.phone}" required>
                <div class="valid-feedback">
                    Looks good!
                </div>
            </div>

        </div>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="validationCustom03"><fmt:message key="Street"/>:</label>
                <input type="text" name="street" value="${user.street}" class="form-control" id="validationCustom03" placeholder="Street" required>
                <div class="invalid-feedback">
                    Please provide a valid street.
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <label for="validationCustom04"><fmt:message key="House"/>:</label>
                <input type="text" name="house" value="${user.house}" class="form-control" id="validationCustom04" placeholder="House" required>
                <div class="invalid-feedback">
                    Please provide a valid house number.
                </div>
            </div>
            <div class="col-md-3 mb-3">
                <label for="validationCustom05"><fmt:message key="Apartment"/>:</label>
                <input type="number" name="apartment" value="${user.apartment}" class="form-control" id="validationCustom05" placeholder="Apartment" required>
                <div class="invalid-feedback">
                    Please provide a valid apartment number.
                </div>
            </div>
        </div>
        <div align="right">
            <button type="submit" class="btn btn-primary"><fmt:message key="Save"/></button>
        </div>

    </form>

    <script>
        // Example starter JavaScript for disabling form submissions if there are invalid fields
        (function() {
            'use strict';
            window.addEventListener('load', function() {
                // Fetch all the forms we want to apply custom Bootstrap validation styles to
                var forms = document.getElementsByClassName('needs-validation');
                // Loop over them and prevent submission
                var validation = Array.prototype.filter.call(forms, function(form) {
                    form.addEventListener('submit', function(event) {
                        if (form.checkValidity() === false) {
                            event.preventDefault();
                            event.stopPropagation();
                        }
                        form.classList.add('was-validated');
                    }, false);
                });
            }, false);
        })();
    </script>
    <h5><fmt:message key="changePassword.title"/>:</h5>
    <form class="needs-validation" novalidate name="changePasswordForm" method="post" action="controller">
        <input type="hidden" name="command" value="change_password"/>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <fmt:message key="CurrentPassword"/>:<br/>
                <input class="form-control" type="password" name="oldPassword" value=""/><br/>
            </div>
            <div class="col-md-6 mb-3">
                <fmt:message key="NewPassword"/>:<br/>
                <input class="form-control" type="password" name="newPassword" value=""/><br/>
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