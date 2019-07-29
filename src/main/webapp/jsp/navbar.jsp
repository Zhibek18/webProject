<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/29/19
  Time: 2:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >
<html>
<head>
    <title>Navbar</title>
    <link href="${pageContext.request.contextPath}/css/navbar.css" rel="stylesheet" type="text/css" >
</head>
<body>
<ul>
    <li><a href="controller?command=forward_main"><fmt:message key="main.title"/></a></li>
    <li><a href="controller?command=show_menu"><fmt:message key="menu.title"/></a></li>

    <li><form name="logout" method="post" action="controller">
        <input type="hidden" name="command" value="logout"/>
        <input type="submit" value="<fmt:message key="logOut"/>"/><br/>
    </form>
    </li>
</ul>
</body>
</html>
</fmt:bundle>