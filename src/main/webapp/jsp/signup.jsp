<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/21/19
  Time: 12:33 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >
<html>
<head>
    <title><fmt:message key="signUp.title"/></title>
    <link href="${pageContext.request.contextPath}/css/signup.css" rel="stylesheet" type="text/css" >
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.4.1.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/messages_ru.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/signup-form.js"></script>
</head>
<body>
<div class = "container">
    <div class = "form">
        <form name="signup" method="post" action="controller" id="signupForm">
            <h1 class="header"><fmt:message key="SignUp"/></h1><br/>
            <input type="hidden" name="command" value="signup"/>
            <div class="inputs">
                    <div class="text"><fmt:message key="Login"/>:</div>
                    <input class="input" type="text" name="login" value=""/>

                    <div class="text"><fmt:message key="Password"/>:</div>
                    <input class="input" type="password" name="password" value=""/>

                    <div class="text"><fmt:message key="Name"/>:</div>
                    <input class="input" type="text" name="firstName" value="">

                    <div class="text"><fmt:message key="Street"/>:</div>
                    <input class="input" type="text" name="street" value="">

                    <div class="text"><fmt:message key="House"/>:</div>
                    <input class="input" type="text" name="house" value="">

                    <div class="text"><fmt:message key="Apartment"/>:</div>
                    <input class="input" type="number" name="apartment" value="">

                    <div class="text"><fmt:message key="Phone"/>:</div>
                    <input class="input" type="text" name="phone" value="">

            </div>
            <input class="button" type="submit" value="<fmt:message key="SignUp"/>">
        </form>
        <c:if test="${not empty errorSignUpMessage}">
            <fmt:message key="${errorSignUpMessage}" /><br/>
        </c:if>
        <c:if test="${not empty nullpage}">
            <fmt:message key="${nullpage}" /><br/>
        </c:if>
    </div>
</div>
</body>
</html>
</fmt:bundle>