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
        <link href="${pageContext.request.contextPath}/CSS/BlogCSS/BlogStyle.css" rel="stylesheet" type="text/css"/>
        <h1>List of Blogs</h1>

        <!--         Search Form with Keyword and Tag Filters -->
        <form action="SearchBlog" method="get">
            <div>
                <label for="keyword">Search by Title:</label>
                <input type="text" id="keyword" name="keyword" value="${param.keyword}" placeholder="Enter keyword"><br><br>
                <input type="submit" value="Search">
            </div>

            <label>Filter by Tags:</label><br>
            <!-- Checkbox list for filtering by tags -->
            <div class="tag-filters">
                <c:forEach var="tag" items="${tagList}">
                    <label>
                        <input type="checkbox" name="tag" value="${tag.tagID}">
                        ${tag.tagName}
                    </label>
                </c:forEach>
            </div>
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
                                <button type="submit" value="Edit">Edit</button>
                            </form>
                        </td>

                        <!-- Delete Button -->
                        <td>
                            <form action="DeleteBlog" method="post" style="display:inline;">
                                <input type="hidden" name="blogID" value="${blog.blogID}">
                                <button type="submit" value="Delete">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <button onclick="location.href = 'CreateBlog'">Create</button>
        <a href="${pageContext.request.contextPath}/logout">
            <button>Logout</button>
        </a>
    </body>
</html>
