<%-- 
    Document   : saleHome
    Created on : Sep 22, 2024, 5:24:37 PM
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
        <c:if test="${not empty sessionScope.account}">
            
                <c:if test="${sessionScope.account.role.roleID == 3}">
                    <h1>Welcome Sale Member</h1>
                    <a href="${pageContext.request.contextPath}/logout">
                        <button>Logout</button>
                    </a>
                </c:if>
                <c:if test="${sessionScope.account.role.roleID != 3}">
                    <h1>You are not authorized to access this page!</h1>
                    <script>
                        window.location.href = '${pageContext.request.contextPath}/View/Login_Register/active.jsp';
                    </script>
                </c:if>
            
        </c:if>
    </body>
</html>
