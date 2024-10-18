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
                                <td>${orderDetail.product.manufacturer}</td>
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
            <!-- Main Page Form -->
            <form id="rejectForm" action="${pageContext.request.contextPath}/CRUDOrderReject" method="post" class="input-tab">
                <input type="hidden" name="orderID" value="${order.orderID}">
                <input type="hidden" name="rejectReason" id="rejectReasonInput"> <!-- Hidden field to hold the reject reason -->
                <button type="button" class="reject-btn" onclick="openRejectModal()">Reject Order</button> <!-- Trigger modal -->
            </form>

            <form action="${pageContext.request.contextPath}/CRUDOrderList" method="get" class="input-tab">
                <button type="submit" class="back-btn">Back to List</button>
            </form>
        </div>


        <div id="rejectModal" class="modal" style="display:none;">
            <div class="modal-content">
                <span class="close" onclick="closeRejectModal()">&times;</span>
                <h3>Enter Reject Reason</h3>
                <textarea id="rejectReasonText" rows="4" cols="50" placeholder="Enter your reason here..."></textarea>
                <button type="button" class="reject-submit-btn" onclick="submitRejectForm()">Submit</button>
            </div>
        </div>

        <footer>
            <p>&copy; 2023 Order Detail. All rights reserved.</p>
        </footer>


        <!-- JavaScript for Modal and Form Submission -->
        <script>
            // Open the modal
            function openRejectModal() {
                document.getElementById("rejectModal").style.display = "block";
            }

            // Close the modal
            function closeRejectModal() {
                document.getElementById("rejectModal").style.display = "none";
            }

            // Submit the form with the reject reason
            function submitRejectForm() {
                var rejectReason = document.getElementById("rejectReasonText").value;
                if (rejectReason.trim() === "") {
                    alert("Please enter a reject reason.");
                    return; // Prevent submission if the reason is empty
                }

                // Set the reject reason in the hidden input field
                document.getElementById("rejectReasonInput").value = rejectReason;

                // Submit the form
                document.getElementById("rejectForm").submit();
            }

            // Close modal if the user clicks outside of it
            window.onclick = function (event) {
                var modal = document.getElementById("rejectModal");
                if (event.target === modal) {
                    modal.style.display = "none";
                }
            };
        </script>
    </body>
</html>