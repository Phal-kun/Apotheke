<%-- 
    Document   : CreatePost
    Created on : Sep 22, 2024, 9:53:02 PM
    Author     : ACER
--%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Create New Blog Post</title>
    </head>
    <body>

        <h1>Create New Blog Post</h1>

        <form action="CreateBlog" method="post">
            <!-- Fake userID -->
            <input type="hidden" name="userID" value="1">  <!-- Hardcoded userID -->
            
            <label for="title">Title:</label>
            <input type="text" name="title"><br>
            
            <label for="content">Content:</label><br>
            <textarea name="content" rows="4" cols="50"></textarea><br>
            
            Tags: 
            <c:forEach var="tag" items="${tagList}">
                <input type="checkbox" name="tagID" value="${tag.tagID}">${tag.tagName}
            </c:forEach><br>
            <input type="hidden" name="userID" value="${sessionScope.userID}">
            <input type="submit" value="Create">
        </form>

        <button onclick="location.href = 'BlogManager'">Cancel</button>

    </body>
</html>

