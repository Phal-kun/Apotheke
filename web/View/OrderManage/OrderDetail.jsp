<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Detail</title>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/OrderCSS/OrderDetailCSS.css"> 
</head>
<body>

    <h1>Order Detail</h1>

    <div class="order-detail">
        <div class="section-title">Order Information</div>
        <div class="detail-row"><span>Order ID:</span> 12345</div>
        <div class="detail-row"><span>Order Date:</span> 2024-10-12</div>
        <div class="detail-row"><span>Order Completed:</span> 2024-10-13</div>
        <div class="detail-row"><span>Total Price:</span> $500.00</div>
        <div class="detail-row"><span>Order Status:</span> Approved</div>
        <div class="detail-row"><span>Reject Reason:</span> N/A</div>
        <br/><br/>
        
        <div class="section-title">Shipping Information</div>
        <div class="detail-row"><span>Ship Name:</span> John Doe</div>
        <div class="detail-row"><span>Ship Address:</span> 123 Main Street, City, Country</div>
        <div class="detail-row"><span>Ship Phone:</span> 123-456-7890</div>
        <div class="detail-row"><span>Ship Note:</span> Leave the package at the front door.</div>
        <br/><br/>

        <div class="section-title">Customer Account Information</div>
        <div class="detail-row"><span>Username:</span> john_doe</div>
        <div class="detail-row"><span>Full Name:</span> John Doe</div>
        <div class="detail-row"><span>Phone:</span> 123-456-7890</div>
        <div class="detail-row"><span>Address:</span> 123 Main Street, City, Country</div>
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
                    <th>Manufacture Date</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Paracetamol</td>
                    <td>2</td>
                    <td>Bottle</td>
                    <td>$5.00</td>
                    <td>$10.00</td>
                    <td>Pharma Inc.</td>
                    <td>12345</td>
                    <td>2025-01-01</td>
                </tr>
            </tbody>
        </table>
    </div>

</body>
</html>
