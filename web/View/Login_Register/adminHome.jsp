<%-- 
    Document   : adminHome
    Created on : Sep 22, 2024, 5:24:20 PM
    Author     : Dell
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        
        <c:if test="${not empty sessionScope.account and sessionScope.account.role.roleID == 5}">
            <h1>Welcome, Admin!</h1>
            <a href="${pageContext.request.contextPath}/CRUDUserList">
            <a href="${pageContext.request.contextPath}/View/Home.jsp">
                <button>Logout</button>
            </a>
        </c:if>

        <c:if test="${empty sessionScope.account or sessionScope.account.role.roleID != 5}">
                <h1>Access Denied!</h1>
        
                <c:redirect url="View/Login_Register/accessDenied.jsp">
                   <c:param name="roleName" value="admin"/>
               </c:redirect>
        </c:if>
    </body>
</html>


