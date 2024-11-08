
<!-- Updated Update Product JSP -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/ProductCSS/UpdateProductCSS.css">
        <title>Update Product</title>
    </head>
    <body onload="initializePage()">
        <header>
            <div class="text-box">
                <h1 id="title">Update Product</h1><hr>
                <p id="description">Update product's information below</p>
            </div>
        </header>

        <div class="container">
            <form id="product-form" action="UpdateProduct" method="POST" onsubmit="return validateTables();">
                
                <div class="labels">
                    <label id="productName-label" for="productID">* Product's ID</label></div>
                <div class="input-tab">
                    <input class="input-field" type="text" id="productID" name="productID" value="${updateProduct.getProductID()}" required readonly autofocus>
                </div>

                <div class="labels">
                    <label id="productName-label" for="productName">* Product's Name</label></div>
                <div class="input-tab">
                    <input class="input-field" type="text" id="productName" name="productName" value="${updateProduct.getProductName()}" required autofocus>
                </div>

                <!-- Origin Dropdown -->
                <div class="labels">
                    <label for="originID">* Product's Origin</label>
                </div>
                <div class="input-tab">
                    <select id="originID" name="originID" required>
                        <option disabled value>Select an origin</option>
                        <c:forEach var="origin" items="${originList}">
                            <option value="${origin.getOriginID()}" <c:if test="${origin.getOriginID() == updateProduct.getOrigin().getOriginID()}">selected</c:if>>${origin.getOriginName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Manufacturer -->
                <div class="labels">
                    <label id="manufacturer-label" for="manufacturer">* Manufacturer</label>
                </div>
                <div class="input-tab">
                    <input class="input-field" type="text" id="manufacturer" name="manufacturer" value="${updateProduct.getManufacturer()}" required>
                </div>

                <!-- Category Dropdown -->
                <div class="labels">
                    <label for="categoryID">* Category</label>
                </div>
                <div class="input-tab">
                    <select id="categoryID" name="categoryID" required>
                        <option disabled value>Select a category</option>
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category.getCategoryID()}" <c:if test="${category.getCategoryID() == updateProduct.getCategory().getCategoryID()}">selected</c:if>>${category.getCategoryName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Component Table -->
                <div class="labels">
                    <label>* Component Table</label>
                </div>
                <table id="component-table">
                    <thead>
                        <tr>
                            <th>Component Name</th>
                            <th>Quantity</th>
                            <th>Measure Unit</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="component" items="${updateProduct.getComponent()}">
                            <tr>
                                <td>
                                    <!-- Display component name as plain text input field, pre-filled with the component name -->
                                    <input type="text" class="input-field" name="componentName" value="${component.getComponentName()}" required/>
                                </td>
                                <td>
                                    <input type="number" class="input-field" name="quantity" value="${component.getQuantity()}" min="1" required onchange="validateQuantity(this)" />
                                </td>
                                <td>
                                    <input type="text" class="input-field" name="measureUnit" value="${component.getComponentMeasureUnit()}" required />
                                </td>
                                <td>
                                    <button type="button" onclick="deleteRow(this)">Delete</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="button" onclick="addComponentRow()">Add Component</button>

                <!-- Unit Table -->
                <div class="labels">
                    <label>* Unit Table</label>
                </div>
                <table id="unit-table">
                    <thead>
                        <tr>
                            <th>Unit Name</th>
                            <th>Conversion Rate</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="unit" items="${updateProduct.getUnit()}">
                            <tr>
                                <td>
                                    <input class="input-field" type="text" name="unitName" value="${unit.getProductUnitName()}" required>
                                </td>
                                <c:choose>
                                    <c:when test="${unit.getUnitToBaseConvertRate() != 1}">
                                        <td>
                                            <input class="input-field" type="number" name="convertRate" step="0.01" value="${unit.getUnitToBaseConvertRate()}" required>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td>
                                            <input class="input-field" type="number" name="convertRate" step="0.01" value="${unit.getUnitToBaseConvertRate()}" required readonly>
                                        </td>
                                    </c:otherwise>
                                </c:choose>  
                                <td>
                                    <button type="button" onclick="deleteRow(this)">Delete</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <button type="button" onclick="addUnitRow()">Add Unit</button>

                <!-- Description -->
                <div class="labels">
                    <label for="description">Product Description</label>
                </div>
                <div class="input-tab">
                    <textarea name="description" rows="5">${updateProduct.getDescription()}</textarea>
                </div>

                <!-- Buttons -->
                <div class="btn">
                    <button type="submit">Update</button>
                </div>
                <div class="back-btn">
                    <a href="ListProduct">
                        <button type="button">Back to List</button>
                    </a>
                </div>
            </form>
        </div>

        <c:if test="${not empty showAlert and showAlert == true}">
            <script>
        alert("${alertMessage}");
            </script>
        </c:if>


        <script>
            function addComponentRow() {
                var tableBody = document.getElementById("component-table").getElementsByTagName('tbody')[0]; // Get tbody element
                var row = tableBody.insertRow(); // Insert row into tbody

                row.innerHTML = `
                    <td>
                        <input type="text" class="input-field" name="componentName" placeholder="Enter component name" required />
                    </td>
                    <td>
                        <input class="input-field" type="number" name="quantity" placeholder="Enter quantity" min="1" required onchange="validateQuantity(this)" />
                    </td>
                    <td>
                        <input type="text" class="input-field" name="componentUnit" placeholder="Enter component's unit" required />
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
