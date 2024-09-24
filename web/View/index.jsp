<%-- 
    Document   : index
    Created on : Sep 23, 2024, 4:13:54 AM
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
      <%
        String id = (request.getAttribute("id") != null) ? request.getAttribute("id").toString() : "Unknown";
        String name = (request.getAttribute("name") != null) ? request.getAttribute("name").toString() : "Unknown";
        String email = (request.getAttribute("email") != null) ? request.getAttribute("email").toString() : "Unknown";
        String gender= (request.getAttribute("gender")!= null)? request.getAttribute("gender").toString():"Unknown";
            String address =(request.getAttribute("address")!=null)?request.getAttribute("address").toString():"Unknown";
        String phone_number=(request.getAttribute("phone_number")!=null)?request.getAttribute("phone_number").toString():"Unknown";
         
                
                
        out.print("Id: " + id);
        out.print("<br/>Name: " + name);
        out.print("<br/>Email: " + email);
        out.print("<br/>Gender: " + gender);
       
        out.print("<br/>address " + address);
        out.print("<br/>phone_number " + phone_number);
    %>
    </body>
</html>
