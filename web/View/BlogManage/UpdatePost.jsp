<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Blog</title>
    </head>
    <body>

        <h1>Edit Blog</h1>

        <form action="EditBlog" method="post">
            <input type="hidden" name="blogID" value="${blog.blogID}">

            <label for="title">Title:</label>
            <input type="text" id="title" name="title" value="${blog.title}" required><br><br>

            <!-- Tags (Checkboxes) -->
            Tags:
                <c:forEach var="tag" items="${tagList}">
                    <c:set var="checked" value="" />

                    <c:forEach var="selectedTag" items="${blog.tags}">
                        <c:if test="${tag.tagID eq selectedTag.tagID}">
                            <c:set var="checked" value="checked" />
                        </c:if>
                    </c:forEach>

                    <input type="checkbox" name="tagUpdate" value="${tag.tagID}" ${checked}> ${tag.tagName}<br>
                </c:forEach>

            <label for="content">Content:</label><br>
            <textarea id="content" name="content" rows="5" cols="30">${blog.content}</textarea><br><br>

            <label for="publicDate">Public Date:</label>
            <input type="text" id="publicDate" name="publicDate" value="${blog.getPublicDate()}" readonly><br><br>

            <label for="userID">User ID:</label>
            <input type="text" id="userID" name="userID" value="${blog.userID}" readonly><br><br>

            <input type="submit" value="Update Blog">
        </form>

        <br>
        <a href="BlogManager">Back to Blog List</a>

    </body>
</html>
