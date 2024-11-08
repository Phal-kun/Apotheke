<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Warehouse Product Detail</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/OrderCSS/OrderDetailCSS.css"> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">

        <script>
            function validateStock(button) {
                // Retrieve values from data attributes
                const quantity = parseFloat(button.dataset.quantity);
                const unitToBaseConvertRate = parseFloat(button.dataset.unitToBaseConvertRate);
                const stock = parseFloat(button.dataset.stock);

                // Calculate the total required stock
                const requiredStock = quantity * unitToBaseConvertRate;

                // Validate if the stock is sufficient
                if (requiredStock >= stock) {
                    alert("Insufficient stock! Please choose a stock that meets the requirements.");
                    return false; // Prevent form submission
                }
                return true; // Allow form submission
            }
        </script>
    </head>
    <body>
        <div class="text-box">
            <h1>Product Detail</h1>
            <div class="logout">
                <a href="${pageContext.request.contextPath}/logout">
                    <button>Logout</button>
                </a>
            </div>
        </div>

        <c:if test="${not empty orderDetail}">
            <div class="container">
                <div class="order-detail">
                    <div class="section-title" style="font-weight: bold">Product Information</div><br/>
                    <div class="detail-row"><span>Product ID:</span> ${orderDetail.product.productID}</div>
                    <div class="detail-row"><span>Product Name:</span> ${orderDetail.product.productName}</div>
                    <div class="detail-row"><span>Category:</span> ${orderDetail.product.category.categoryName}</div>
                    <div class="detail-row"><span>Origin:</span> ${orderDetail.product.origin.originName}</div>
                    <div class="detail-row"><span>Manufacturer:</span> ${orderDetail.product.manufacturer}</div>
                    <div class="detail-row"><span>Component Description:</span> ${orderDetail.product.componentDescription}</div>
                    <div class="detail-row"><span>Product Description:</span> ${orderDetail.product.description}</div>
                    <div class="detail-row"><span>Product Unit:</span> ${orderDetail.unit.productUnitName}</div>
                    <div class="detail-row"><span>Quantity:</span> ${orderDetail.quantity}</div>
                    <div class="detail-row"><span>Sold Price per Unit:</span> ${orderDetail.soldPrice}</div>
                    <div class="detail-row"><span>Total Product Price:</span> ${orderDetail.totalProductPrice}</div>
                    <br/><br/>

                    <div class="container">
                        <div class="order-detail">
                            <!-- Other details and titles -->

                            <table>
                                <thead>
                                    <tr>
                                        <th>Batch No.</th>                 
                                        <th>Stock</th>
                                        <th>Average Import Price</th>
                                        <th>Manufacture Date</th>
                                        <th>Expired Date</th>
                                        <th></th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="productDetail" items="${productDetailList}">
                                        <tr>
                                            <td>${productDetail.batchNo}</td>
                                            <td>${productDetail.stock}</td>
                                            <td>${productDetail.importPrice}</td>
                                            <td><fmt:formatDate value="${productDetail.manufactureDate}" pattern="yyyy-MM-dd"/></td>
                                            <td><fmt:formatDate value="${productDetail.expiredDate}" pattern="yyyy-MM-dd"/></td>
                                            <td>
                                                <c:if test="${sessionScope.orderStatusID == 2}">
                                                    <form action="${pageContext.request.contextPath}/ProductDetailList" method="post" class="input-tab">
                                                        <input type="hidden" name="orderDetailID" value="${orderDetail.orderDetailID}">
                                                        <input type="hidden" name="productDetailID" value="${productDetail.productDetailID}">                                   
                                                        <button type="submit" 
                                                                class="approve-btn"
                                                                data-quantity="${orderDetail.quantity}" 
                                                                data-unitToBaseConvertRate="${orderDetail.unit.unitToBaseConvertRate}" 
                                                                data-stock="${productDetail.stock}"
                                                                onclick="return validateStock(this)">Choose Stock</button>
                                                    </form>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>

                <div class="container">
                    <form action="${pageContext.request.contextPath}/ApprovedOrderList" method="get" class="input-tab">
                        <button type="submit" class="back-btn">Back to List</button>
                    </form>
                </div>
                </body>
                </html>
