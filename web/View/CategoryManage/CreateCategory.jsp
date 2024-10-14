<%-- 
    Document   : CreateCategory
    Created on : Oct 13, 2024, 11:04:26 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Create Category</title>
        <link href="${pageContext.request.contextPath}/CSS/CategoryCSS/EditCategory.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Create Category</h1>

        <!-- Error message -->
        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>

        <!-- Form to create a new category -->
        <form action="CreateCategory" method="POST">
            <label for="categoryName">Category Name:</label>
            <input type="text" id="categoryName" name="categoryName" required /><br>

            <label for="description">Description:</label>
            <textarea id="description" name="description" required></textarea><br>

            <label for="parentCategoryID">Parent Category (optional):</label>
            <select id="parentCategoryID" name="parentCategoryID">
                <option value="">None</option> <!-- No parent category option -->
                <c:forEach var="c" items="${categories}">
                    <option value="${c.categoryID}">
                        ${c.categoryName}
                    </option>
                </c:forEach>
            </select><br>

            <button type="submit">Create Category</button>
        </form>

        <button onclick="location.href = 'CategoryManager'">Back to Category List</button>
    </body>
</html>
