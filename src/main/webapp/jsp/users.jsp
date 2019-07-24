<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/21/19
  Time: 6:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>
</head>
<body>
<h3>Users</h3><br/>
<table>
    <c:forEach var="userName" items="${userNames}" varStatus="status">
        <tr>
            <td><c:out value="${userName}"/></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>
