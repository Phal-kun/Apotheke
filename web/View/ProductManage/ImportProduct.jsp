<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/ProductCSS/CreateProductCSS.css">
        <title>Import Product</title>
    </head>
    <body onload="initializePage()">
        <header>
            <div class="text-box">
                <h1 id="title">Import Product</h1><hr>
                <p id="description">Fill in the details to import a new batch of the product.</p>
            </div>
        </header>

        <div class="container">
            <form id="import-product-form" action="ImportProduct" method="POST" onsubmit="return validateTables();">

                <!-- Select Product from Dropdown -->
                <div class="labels">
                    <label for="productID">* Select Product</label>
                </div>
                <div class="input-tab">
                    <select id="productID" name="unitID" required>
                        <option disabled selected>Select a product</option>
                        <c:forEach var="product" items="${productList}">
                            <option value="${product.getBaseUnit().getUnitID()}">${product.getProductName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Import Price -->
                <div class="labels">
                    <label id="importPrice-label" for="importPrice">* Import Price</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="number" step="0.01" id="importPrice" name="importPrice" placeholder="Enter import price" required>
                </div>

                <!-- Sale Price -->
                <div class="labels">
                    <label id="salePrice-label" for="salePrice">* Sale Price</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="number" step="0.01" id="salePrice" name="salePrice" placeholder="Enter sale price" required>
                </div>

                <!-- Quantity -->
                <div class="labels">
                    <label id="quantity-label" for="quantity">* Quantity</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="number" id="quantity" name="quantity" placeholder="Enter quantity" required>
                </div>

                <!-- Batch Number -->
                <div class="labels">
                    <label id="batchNo-label" for="batchNo">* Batch Number</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="number" id="batchNo" name="batchNo" placeholder="Enter batch number" required>
                </div>

                <!-- Manufacturer Date -->
                <div class="labels">
                    <label id="manufacturerDate-label" for="manufacturerDate">* Manufacturer Date</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="date" id="manufacturerDate" name="manufacturerDate" required>
                </div>

                <!-- Expired Date -->
                <div class="labels">
                    <label id="expiredDate-label" for="expiredDate">* Expired Date</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="date" id="expiredDate" name="expiredDate" required>
                </div>

                <div class="btn">
                    <button id="submit" type="submit">Import Product</button>
                </div>    

                <c:if test="${not empty errorMsg}">
                    <p style="color:red;">${errorMsg}</p>
                </c:if>

                <div class="back-btn">
                    <a href="ListProduct">
                        <button type="button">Back to List</button>
                    </a>
                </div>

            </form>
        </div>

        <script>
            function showAlert(message) {
                if (message && message !== "false") {
                    alert(message);
                }
            }

            function initializePage() {
                showAlert('${createMsg}');
            }
        </script>

    </body>
</html>
