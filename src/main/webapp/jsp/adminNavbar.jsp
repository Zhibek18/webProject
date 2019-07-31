<%--
  Created by IntelliJ IDEA.
  User: sam
  Date: 7/29/19
  Time: 4:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${language}" scope="session"/>
<fmt:bundle basename="pagecontent.pagecontent" prefix = "label." >
    <html>
    <head>
        <title>Navbar</title>
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet"/>
    </head>
    <body>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="#">Food delivery</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=forward_admin"><fmt:message key="orders.title"/> <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=show_users"><fmt:message key="users.title"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="controller?command=forward_edit_menu"><fmt:message key="editMenu.title"/></a>
                    </li>
                </ul>
                <form class="form-inline my-2 my-lg-0">
                    <form name="changeLanguage" method="post" action="controller">
                        <input type="hidden" name="command" value="change_language">
                        <input type="hidden" name="page" value="${currentPage}">
                        <select class="custom-select" id="language" name="language" onchange="this.form.submit()">
                            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
                            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                        </select>
                    </form>
                    <form class="form-inline my-2 my-lg-0" name="logout" method="post" action="controller">
                        <input type="hidden" name="command" value="logout"/>
                        <input class="btn btn-outline-danger my-2 my-sm-0" type="submit" value="<fmt:message key="logOut"/>"/><br/>
                    </form>
                </form>
            </div>
        </nav>

    </body>
    </html>
</fmt:bundle>