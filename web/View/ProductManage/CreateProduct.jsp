<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/ProductCSS/CreateProductCSS.css">
        <title>Create Product</title>
    </head>
    <body onload="initializePage()">
        <header>
            <div class="text-box">
                <h1 id="title">Create product</h1><hr>
                <p id="description">Insert product's information in the fields below</p>
            </div>
        </header>

        <div class="container">

            <form id="product-form" action="CreateProduct" method="POST" onsubmit="return validateTables();">

                <div class="labels">
                    <label id="productID-label" for="productID">* Product's ID</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="number" step="1" id="productID" name="productID" placeholder="Enter product ID.">
                </div>

                <div class="labels">
                    <label id="productName-label" for="productName">* Product's Name</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="text" id="productName" name="productName" placeholder="Enter product name" required>
                </div>

                <!-- Origin Dropdown -->
                <div class="labels">
                    <label for="originID">* Product's origin</label>
                </div>
                <div class="input-tab">
                    <select id="originID" name="originID" required>
                        <option disabled selected>Select an origin</option>
                        <c:forEach var="origin" items="${originList}">
                            <option value="${origin.getOriginID()}">${origin.getOriginName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="labels">
                    <label id="manufacturer-label" for="manufacturer">* Manufacturer</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="text" id="manufacturer" name="manufacturer" placeholder="Enter manufacturer name" required>
                </div>

                <!-- Category Dropdown -->
                <div class="labels">
                    <label for="categoryID">* Category</label>
                </div>
                <div class="input-tab">
                    <select id="categoryID" name="categoryID" required>
                        <option disabled selected>Select a category</option>
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category.getCategoryID()}">${category.getCategoryName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Component dynamic table -->
                <div class="labels">
                    <label>* Component table</label>
                </div>
                <table id="component-table">
                    <thead>
                        <tr>
                            <th>Component name</th>
                            <th>Quantity</th>
                            <th>Component's measure unit</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <td>
                        <input type="text" name="componentName" placeholder="Enter component name" required />
                    </td>
                    <td>
                        <input class="input-field" type="number" name="quantity" placeholder="Enter quantity" min="1" required onchange="validateQuantity(this)" />
                    </td>
                    <td>
                        <input type="text" name="componentUnit" placeholder="Enter component's unit" required />
                    </td>
                    </tbody>
                </table>

                <button type="button" onclick="addComponentRow()">Add more Component</button><br>

                <!-- Unit dynamic table -->
                <div class="labels">
                    <label>* Unit table</label>
                </div>
                <table id="unit-table">
                    <thead>
                        <tr>
                            <th>Unit name</th>
                            <th>Conversion rate</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <td>
                        <input class="input-field" type="text" name="baseUnitName" placeholder="Enter base unit name" required>
                    </td>
                    <td>
                        <input class="input-field" type="number" step="0.01" name="baseConvertRate" value="1" placeholder="1" required readonly>
                    </td>
                    </tbody>
                </table>

                <button type="button" onclick="addUnitRow()">Add more Unit</button><br>

                <!-- Description -->
                <div class="labels">
                    <label for="description">Product Description</label>
                </div>
                <div class="input-tab">
                    <textarea class="input-field" id="description" name="description" rows="10" cols="40" placeholder="Short description about the product..."></textarea>
                </div>

                <div class="btn">
                    <button id="submit" type="submit">Submit</button>
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
            function addComponentRow() {
                var tableBody = document.getElementById("component-table").getElementsByTagName('tbody')[0]; // Get tbody element
                var row = tableBody.insertRow(); // Insert row into tbody

                row.innerHTML = `
                    <td>
                        <input type="text" name="componentName" placeholder="Enter component name" required />
                    </td>
                    <td>
                        <input class="input-field" type="number" name="quantity" placeholder="Enter quantity" min="1" required onchange="validateQuantity(this)" />
                    </td>
                    <td>
                        <input type="text" name="componentUnit" placeholder="Enter component's unit" required />
                    </td>
                    <td>
                        <button type="button" onclick="deleteRow(this)">Delete</button>
                    </td>
                `;
            }

            function addUnitRow() {
                var tableBody = document.getElementById("unit-table").getElementsByTagName('tbody')[0]; // Get tbody element
                var row = tableBody.insertRow(); // Insert row into tbody

                row.innerHTML = `
                    <td>
                        <input class="input-field" type="text" name="unitName" placeholder="Enter unit name" required>
                    </td>
                    <td>
                        <input class="input-field" type="number" step="0.01" name="convertRate" placeholder="Enter conversion rate to base unit" required>
                    </td>
                    <td>
                        <button type="button" onclick="deleteRow(this)">Delete</button>
                    </td>`;
            }

            function validateQuantity(input) {
                var quantity = parseInt(input.value, 10);
                if (quantity < 1) {
                    alert("Quantity must be greater than 0.");
                    input.value = ""; // Clear the input if invalid
                }
            }

            function deleteRow(button) {
                var row = button.closest('tr'); // Get the closest row to the button
                row.parentNode.removeChild(row); // Remove the row
            }

            function showAlert(message) {
                if (message && message !== "false") {
                    alert(message);
                }
            }

            function initializePage() {
                showAlert('${createMsg}');
            }

            function validateTables() {
                var componentTable = document.getElementById("component-table").getElementsByTagName('tbody')[0].rows.length;
                var unitTable = document.getElementById("unit-table").getElementsByTagName('tbody')[0].rows.length;

                if (componentTable < 1) {
                    alert("The component table must have at least one row.");
                    return false; // Prevent form submission
                }

                if (unitTable < 1) {
                    alert("The unit table must have at least one row.");
                    return false; // Prevent form submission
                }

                return true; // Allow form submission
            }
        </script>

    </body>
</html>
