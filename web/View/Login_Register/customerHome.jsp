<%-- 
    Document   : customerHome
    Created on : Sep 22, 2024, 5:24:51 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:if test="${not empty sessionScope.account}">
              <jsp:include page="${pageContext.request.contextPath}/View/pagecontrol/header.jsp"></jsp:include>
              <h1>Submit Order Successful</h1>
            
        </c:if>
        <c:if test="${empty sessionScope.account}">
            <h1>You are not logged in!</h1>
            <script>
                window.location.href = '${pageContext.request.contextPath}/View/Login_Register/active.jsp';
            </script>
        </c:if>        
       
    </body>
    <script>
        window.addEventListener('beforeunload', function (e) {
            navigator.sendBeacon('<%= request.getContextPath() %>/Logout'); 
        });
    </script>

</html>
