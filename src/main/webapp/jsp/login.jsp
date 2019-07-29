<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/20/19
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="ru_RU" scope="session"/>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >

<html>
<head>
    <title><fmt:message key="login.title" /></title>
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" type="text/css" >
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/messages_ru.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/login-form.js"></script>
</head>
<body>
    <div class = "container">
        <div class = "form">
            <form name="loginForm" method="post" action="controller" id="loginForm">
                <input type="hidden" name="command" value="login"/>
                <h1 class="header"><fmt:message key="logIn" /></h1>
                <div class="inputs">
                    <input class="input" type="text" name="login" value="" placeholder="<fmt:message key="Login" />"/>
                    <input class="input" type="password" name="password" value="" placeholder="<fmt:message key="Password" />"/>
                </div>
                    <input class="button" type="submit" value="<fmt:message key="logIn" />"/>

            </form>
            <form name="signUpLink" method="post" action="controller">
                <input type="hidden" name="command" value="forward_signup"/>
                <input class="button" type="submit" value="<fmt:message key="SignUp"/>"/>
            </form>
            <c:if test="${not empty errorLogin}">
                <fmt:message key="${errorLogin}" /><br/>
            </c:if>
            <c:if test="${not empty nullpage}">
                <fmt:message key="${nullpage}" /><br/>
            </c:if>
        </div>
    </div>
</body>

</html>
</fmt:bundle>