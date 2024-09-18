<%-- 
    Document   : BlogManager
    Created on : Sep 18, 2024, 8:12:07 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h1>List of Blogs</h1>

        <table border="1">
            <thead>
                <tr>
                    <th>Blog ID</th>
                    <th>Title</th>
                    <th>Content</th>
                    <th>Date</th>
                    <th>User ID</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="blog" items="${blogList}">
                    <tr>
                        <td>${blog.blogID}</td>
                        <td>${blog.title}</td>
                        <td>${blog.content}</td>
                        <td>${blog.publicDate}</td>
                        <td>${blog.userID}</td>
                        <!-- Status Button to Toggle Active/Inactive -->
                        <td>
                            <form action="Status" method="post" style="display:inline;">
                                <input type="hidden" name="blogID" value="${blog.blogID}">
                                <input type="submit" value="${blog.status ? 'Inactivate' : 'Activate'}">
                            </form>
                        </td>

                        <!-- Delete Button -->
                        <td>
                            <form action="DeleteBlog" method="post" style="display:inline;">
                                <input type="hidden" name="blogID" value="${blog.blogID}">
                                <input type="submit" value="Delete">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </body>
</html>
