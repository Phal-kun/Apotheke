<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Order List</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/OrderCSS/OrderListCSS.css"> 
    </head>
    <body>
        <h1>Order List</h1>
        
        <div class="toolbar">
            <!-- To do: Add filter here (drop down list) -->
            <form class="search" name="search" method="post" action="CRUDUserList" style="display: inline-block;">
                <input type="text" name="keyword" placeholder="Search..." value="${sessionScope.keyword}"/>
                <input type="hidden" name="keywordReset" value="true"/>
                <input type="submit" value="Search"/>
            </form>
        </div>
        

        <table>
            <thead>
                <tr>
                    <th>Order ID</th>
                    <th>Customer Name</th>
                    <th>Order Date</th>
                    <th>Status</th>
                    <th>Total Price</th>
                    <th>Shipping Address</th>
                    <th>View Detail</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>12345</td>
                    <td>John Doe</td>
                    <td>2024-10-10</td>
                    <td>Approved</td>
                    <td>$100.00</td>
                    <td>123 Elm St.</td>
                    <td><button class="view-btn">View</button></td>
                </tr>
            </tbody>
        </table>

        <div class="pagination" style="margin: auto; justify-content: center; margin-top: 30px;">
            <form action="CRUDOrderList" method="post" style="display: inline;">
                <input type="hidden" name="indexCo" value="${indexNowCo - 1}" />
                <button type="submit">&laquo;</button>
            </form>

            <c:forEach begin="1" end="${endPageCo}" var="o">
                <form action="CRUDOrderList" method="post" style="display: inline;">
                    <input type="hidden" name="indexCo" value="${o}" />
                    <button type="submit" class="${indexNowCo == o ? 'active' : ''}" ${indexNowCo == o ? 'disabled' : ''}>${o}</button>
                </form>
            </c:forEach>

            <form action="CRUDOrderList" method="post" style="display: inline;">
                <input type="hidden" name="indexCo" value="${indexNowCo + 1}" />
                <button type="submit">&raquo;</button>
            </form>
        </div>

    </body>
</html>