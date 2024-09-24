<%-- 
    Document   : adminHome
    Created on : Sep 22, 2024, 5:24:20 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello admin</h1>
        <a href="${pageContext.request.contextPath}/View/Home.jsp">
            <button>Logout</button>
        </a>
    </body>
</html>
