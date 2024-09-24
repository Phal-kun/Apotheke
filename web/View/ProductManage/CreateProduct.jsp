<%-- 
    Document   : createProduct
    Created on : Sep 21, 2024, 5:45:52 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/ProductCSS/CreateProductCSS.css">
        <title>JSP Page</title>
    </head>
    <body onload="initializePage()">
        <header>
            <div class="text-box">
                <h1 id="title">Create product</h1><hr>
                <p id="description">Insert product's informations down these field below</p>
            </div>
        </header>

        <div class="container">

            <form id="product-form" action="CreateProduct" method="POST" onsubmit="return validateTable();">



                <!--                Might want to input ID manually
                                    <div class="labels">
                                    <label id="productID-label" for="productID">* Product's ID</label></div>
                                <div class="input-tab">
                                    <input class="input-field" type="number" step="1" id="productID" name="productID" placeholder="Enter product ID. If blank, system will generate one automatically"></div>-->

                <div class="labels">
                    <label id="productName-label" for="productName">* Product's Name</label></div>
                <div class="input-tab">
                    <input class="input-field" type="text" id="productName" name="productName" placeholder="Enter product name" required autofocus></div>

                <!-- Origin Dropdown -->
                <div class="labels">
                    <label for="dropdown">* Product's origin</label>
                </div>
                <div class="input-tab">
                    <select id="dropdown" name="originID" required>
                        <option disabled value selected>Select an origin</option>
                        <c:forEach var="origin" items="${originList}">
                            <option value="${origin.getOriginID()}">${origin.getOriginName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Manufacturer Dropdown -->
                <div class="labels">
                    <label for="dropdown">* Manufacturer</label>
                </div>
                <div class="input-tab">
                    <select id="dropdown" name="manufacturerID" required>
                        <option disabled value selected>Select a manufacturer</option>
                        <c:forEach var="manufacturer" items="${manufacturerList}">
                            <option value="${manufacturer.getManufacturerID()}">${manufacturer.getManufacturerName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Form Dropdown -->
                <div class="labels">
                    <label for="dropdown">* Form</label>
                </div>
                <div class="input-tab">
                    <select id="dropdown" name="formID" required>
                        <option disabled value selected>Select a form</option>
                        <c:forEach var="form" items="${formList}">
                            <option value="${form.getFormID()}">${form.getFormName()}</option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Category Dropdown -->
                <div class="labels">
                    <label for="dropdown">* Category</label>
                </div>
                <div class="input-tab">
                    <select id="dropdown" name="categoryID" required>
                        <option disabled value selected>Select a category</option>
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

                    </tbody>
                </table>

                <button onclick="addRow()">Create row</button><br>

                <div class="labels">
                    <label for="description">ll us more about your favorite character(s) or episode(s)</label></div>
                <div class="input-tab">
                    <textarea class="input-field" id="description" name="description" rows="10" cols="40" placeholder="Short description about product..."></textarea></div>
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
    </body>


    <script>
        function addRow() {
            var table = document.getElementById("component-table");
            var row = table.insertRow(1);

            // Use innerHTML to create the entire row structure
            row.innerHTML = `
            <td>
                <select id="dropdown" name="componentID" required onchange="updateMeasureUnit(this)">
                    <option disabled value selected>Select a component</option>
        <c:forEach var="component" items="${componentList}">
                                        <option value="${component.getComponentID()}" data-unit="${component.getComponentMeasureUnit()}">
            ${component.getComponentName()}
                                        </option>
        </c:forEach>
                </select>
            </td>
            <td>
                <input class="input-field" type="number" name="quantity" placeholder="Enter quantity" min="1" required onchange="validateQuantity(this)" />
            </td>
            <td>
                <span class="measure-unit">Unit</span>
            </td>
            <td>
                <button type="button" onclick="deleteRow(this)">Delete</button>
            </td>
        `;
        }

        function updateMeasureUnit(select) {
            var unit = select.options[select.selectedIndex].dataset.unit; // Get the data-unit attribute
            var measureUnitSpan = select.closest('tr').querySelector('.measure-unit'); // Find the measure unit span
            measureUnitSpan.textContent = unit; // Update the measure unit
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

        function validateTable() {
            var table = document.getElementById("component-table");
            var rowCount = table.rows.length;

            if (rowCount < 2) { // < 2 because the first row is usually the header
                alert("The table must have at least one row.");
                return false; // Prevent form submission
            }
            return true; // Allow form submission
        }
    </script>

</html>