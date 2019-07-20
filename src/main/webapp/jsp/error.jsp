<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/20/19
  Time: 7:37 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage ="true"%>
<html>
<head>
    <title>Error</title>
</head>
<body>
Request from ${pageContext.errorData.requestURI} is failed <br/>
Servlet name or type ${pageContext.errorData.servletName}<br/>
Status code: ${pageContext.errorData.statusCode}<br/>
Exception: ${pageContext.errorData.throwable}<br/>
</body>
</html>
