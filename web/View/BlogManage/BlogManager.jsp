<%-- 
    Document   : BlogManager
    Created on : Sep 18, 2024, 8:12:07 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

        <h1>List of Blogs</h1>

<!--         Search Form with Keyword and Tag Filters -->
        <form action="SearchBlog" method="get">
            <label for="keyword">Search by Title:</label>
            <input type="text" id="keyword" name="keyword" value="${param.keyword}"><br><br>

            <label>Filter by Tags:</label><br>
            <c:forEach var="tag" items="${tagList}">
                <input type="checkbox" name="tagFilter" value="${tag.tagID}" 
                       <c:if test="${fn:contains(param.tagFilter, tag.tagID)}">checked</c:if>
                       >${tag.tagName}<br>
            </c:forEach><br>

            <input type="submit" value="Search">
        </form>

        <table border="1">
            <thead>
                <tr>
                    <th>Blog ID</th>
                    <th>Title</th>
                    <th>Tags</th>
                    <th>Date</th>
                    <th>User ID</th>
                    <th>Status</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="blog" items="${blogList}">
                    <tr>
                        <td>${blog.blogID}</td>
                        <td>${blog.title}</td>
                        <!-- Displaying tags associated with the blog -->
                        <td>
                            <c:forEach var="tag" items="${blog.tags}">
                                ${tag.tagName}<br>
                            </c:forEach>
                        </td>
                        <td>${blog.publicDate}</td>
                        <td>${blog.userID}</td>
                        <!-- Status Button to Toggle Active/Inactive -->
                        <td>
                            <form action="StatusBlog" method="post" style="display:inline;">
                                <input type="hidden" name="blogID" value="${blog.blogID}">
                                <input type="hidden" name="status" value="${blog.status ? 'Inactivate' : 'Activate'}">
                                <input type="submit" value="${blog.status ? 'Inactivate' : 'Activate'}">
                            </form>
                        </td>

                        <!--Edit post-->
                        <td>
                            <form action="EditBlog" method="get" style="display:inline;">
                                <input type="hidden" name="blogID" value="${blog.blogID}">
                                <input type="submit" value="Edit">
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
        <button onclick="location.href = 'CreateBlog'">Create</button>

    </body>
</html>
