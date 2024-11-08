<%-- 
    Document   : ListProduct
    Created on : Sep 21, 2024, 6:30:00 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/ProductCSS/CreateProductCSS.css">
        <title>Product List</title>
    </head>
    <body>
        <header>
            <div class="text-box">
                <h1 id="title">Product List</h1>
                <a href="${pageContext.request.contextPath}/logout">
                    <button>Logout</button>
                </a>
                <a href="${pageContext.request.contextPath}/View/Login_Register/warehouseHome.jsp">
                    <button>Management Menu</button>
                </a>
                <hr>
                <p id="description">Below is the list of products available</p>
            </div>
        </header>
        <div class="container">
            <!-- Product Table -->
            <table id="product-table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Name</th>
                        <th>Category</th>
                        <th>Origin</th>
                        <th>Manufacturer</th>
                        <th>Base Unit</th>
                        <th>Active Status</th>
                        <th>        
                            <a href="ImportProduct">
                                <button type="button">Import product</button>
                            </a>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="product" items="${productList}">
                        <tr>
                            <td>${product.getProductID()}</td>
                            <td>${product.getProductName()}</td>
                            <td>${product.getCategory().getCategoryName()}</td>
                            <td>${product.getOrigin().getOriginName()}</td>
                            <td>${product.getManufacturer()}</td>
                            <td>${product.getBaseUnit().getProductUnitName()}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${product.isActive()}">Active</c:when>
                                    <c:otherwise>Inactive</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="UpdateProduct" method="get" style="display:inline-block;">
                                    <input type="hidden" name="productID" value="${product.getProductID()}" />
                                    <button type="submit" class="select-btn">Update</button>
                                </form>
                                <form action="ProductDetailServlet" method="get" style="display:inline-block;">
                                    <input type="hidden" name="productID" value="${product.getProductID()}" />
                                    <button type="submit" class="select-btn">View Detail</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="btn">
                <a href="CreateProduct">
                    <button id="create-product-btn" type="button">Create New Product</button>
                </a>
            </div>
        </div>


    </body>
</html>
