<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order Detail</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/OrderCSS/OrderDetailCSS.css"> 
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/style.css">
    </head>
    <body>
        <div class="text-box">
            <h1>Order Detail</h1>
            <div class="logout">
                <a href="${pageContext.request.contextPath}/logout">
                    <button>Logout</button>
                </a>
            </div>
        </div>

        <div class="container">
            <div class="order-detail">
                <div class="section-title">Order Information</div>
                <div class="detail-row"><span>Order ID:</span> ${order.orderID}</div>
                <div class="detail-row"><span>Order Date:</span> ${order.orderDate}</div>
                <div class="detail-row"><span>Order Completed:</span> ${order.orderCompleted}</div>
                <div class="detail-row"><span>Total Price:</span> $${order.totalPrice}</div>
                <div class="detail-row"><span>Order Status:</span> ${order.status}</div>
                <div class="detail-row"><span>Reject Reason:</span> ${order.rejectReason != null ? order.rejectReason : 'N/A'}</div>
                <br/><br/>

                <div class="section-title">Shipping Information</div>
                <div class="detail-row"><span>Ship Name:</span> ${order.shipName}</div>
                <div class="detail-row"><span>Ship Address:</span> ${order.shipAddress}</div>
                <div class="detail-row"><span>Ship Phone:</span> ${order.shipPhone}</div>
                <div class="detail-row"><span>Ship Note:</span> ${order.shipNote}</div>
                <br/><br/>

                <div class="section-title">Customer Account Information</div>
                <div class="detail-row"><span>Username:</span> ${order.user.username}</div>
                <div class="detail-row"><span>Full Name:</span> ${order.user.fullname}</div>
                <div class="detail-row"><span>Phone:</span> ${order.user.phone}</div>
                <div class="detail-row"><span>Address:</span> ${order.user.address}</div>
                <br/><br/>

                <div class="section-title">Order Details</div>
                <table>
                    <thead>
                        <tr>
                            <th>Product Name</th>                 
                            <th>Quantity</th>
                            <th>Unit</th>
                            <th>Sold Price</th>
                            <th>Total Product Price</th>
                            <th>Manufacturer</th>
                            <th>Batch No.</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="orderDetail" items="${order.orderDetail}">
                            <tr>
                                <td>${orderDetail.product.productName}</td>
                                <td>${orderDetail.quantity}</td>
                                <td>${orderDetail.unit.productUnitName}</td>
                                <td>${orderDetail.soldPrice}</td>
                                <td>${orderDetail.totalProductPrice}</td>
                                <td>${orderDetail.product.manufacturer.manufacturerName}</td>
                                <td>${orderDetail.productDetail.batchNo}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="container">
            <div class="section-title">Actions</div>
            <form action="${pageContext.request.contextPath}/CRUDOrderApprove" method="get" class="input-tab">
                <input type="hidden" name="orderID" value="${order.orderID}">
                <button type="submit" class="approve-btn">Approve Order</button>
            </form>
            <form action="${pageContext.request.contextPath}/CRUDOrderReject" method="get" class="input-tab">
                <input type="hidden" name="orderID" value="${order.orderID}">
                <button type="submit" class="reject-btn">Reject Order</button>
            </form>
            <form action="${pageContext.request.contextPath}/CRUDOrderList" method="get" class="input-tab">
                <button type="submit" class="back-btn">Back to List</button>
            </form>
        </div>

        <footer>
            <p>&copy; 2023 Order Detail. All rights reserved.</p>
        </footer>
    </body>
</html>