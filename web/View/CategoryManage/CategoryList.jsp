<%-- 
    Document   : CategoryList
    Created on : Oct 7, 2024, 11:13:01 AM
    Author     : ACER
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Category List</title>
        <!-- Link the external CSS file -->
        <link href="${pageContext.request.contextPath}/CSS/CategoryCSS/CategoryList.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        <h1>Category List</h1>
        <form method="GET" action="CategoryManager">
            <!-- Sort Order Dropdown -->
            <label for="sortOrder">Sort by:</label>
            <select name="sortOrder" id="sortOrder" onchange="this.form.submit()">
                <option value="ASC" ${sortOrder == 'ASC' ? 'selected' : ''}>A-Z</option>
                <option value="DESC" ${sortOrder == 'DESC' ? 'selected' : ''}>Z-A</option>
            </select>
        </form>

        <c:if test="${empty categoryList}">
        <tr>
            <td colspan="6">No categories found.</td>
        </tr>
    </c:if>

    <!-- Success/Error Message Section -->
    <c:if test="${not empty param.message}">
        <p class="message">${param.message}</p>
    </c:if>

    <table>
        <thead>
            <tr>
                <th>Category ID</th>
                <th>Category Name</th>
                <th>Description</th>
                <th>Status</th>
                <th>Parent Category</th>
                <th>Option</th>                
            </tr>
        </thead>
        <tbody>
            <c:forEach var="category" items="${categories}">
                <tr>
                    <td>${category.categoryID}</td>
                    <td>${category.categoryName}</td>
                    <td>${category.description}</td>
                    <td>
                        <c:choose>
                            <c:when test="${category.status}">
                                Active
                            </c:when>
                            <c:otherwise>
                                Inactive
                            </c:otherwise>
                        </c:choose>
                    </td> <!-- Status column -->
                    <td>
                        <c:choose>
                            <c:when test="${category.parentCategory != null}">
                                ${category.parentCategory.categoryName}
                            </c:when>
                            <c:otherwise>
                                No Parent
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <!-- Redirect to EditCategory.jsp with categoryID for editing -->
                        <form action="EditCategory" method="GET">
                            <input type="hidden" name="categoryID" value="${category.categoryID}" />
                            <button type="submit" class="select-btn">Edit</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <button class="select-btn" onclick="location.href = 'CreateCategory'">Create</button>
</body>
</html>
