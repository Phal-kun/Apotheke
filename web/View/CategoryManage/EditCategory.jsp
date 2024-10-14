<%-- 
    Document   : EditCategory
    Created on : Oct 12, 2024, 11:33:18 PM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Edit Category</title>
        <link href="${pageContext.request.contextPath}/CSS/CategoryCSS/EditCategory.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Edit Category</h1>

        <!-- Error message -->
        <c:if test="${not empty error}">
            <p style="color: red;">${error}</p>
        </c:if>

        <!-- Check if the category object is available -->
        <c:if test="${category != null}">
            <!-- Form to edit category -->
            <form action="EditCategory" method="POST">
                <input type="hidden" name="categoryID" value="${category.categoryID}" />

                <label for="categoryName">Category Name:</label>
                <input type="text" id="categoryName" name="categoryName" value="${category.categoryName}" required /><br>

                <label for="description">Description:</label>
                <textarea id="description" name="description" required>${category.description}</textarea><br>

                <label for="parentCategoryID">Parent Category (optional):</label>
                <select id="parentCategoryID" name="parentCategoryID">
                    <option value="">None</option> <!-- No parent category option -->
                    <c:forEach var="c" items="${categories}">
                        <option value="${c.categoryID}" ${category.parentCategory != null && c.categoryID == category.parentCategory.categoryID ? 'selected' : ''}>
                            ${c.categoryName}
                        </option>
                    </c:forEach>
                </select><br>

                <button type="submit">Save Changes</button>
            </form>
        </c:if>

        <button onclick="location.href = 'CategoryManager'">Back to Category List</button>
    </body>
</html>
