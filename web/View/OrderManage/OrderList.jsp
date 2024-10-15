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
        <div>
            <a href="${pageContext.request.contextPath}/logout" class ="logout">
                <button>Logout</button>
            </a>
        </div>
                
        <div>Status ID: ${sessionScope.statusID != null ? sessionScope.statusID : 'Not set'}</div>

        <div class="toolbar">
            <form name="statusForm" method="post" action="CRUDOrderList" style="display: inline-block;">
                <label for="statusID">Order Status:</label>
                <select name="statusID" id="statusID" onchange="this.form.submit()">
                    <option value="0" ${sessionScope.statusID == 0 ? 'selected' : ''}>All Order</option>
                    <c:forEach var="status" items="${statusList}">
                        <option name="statusID" value="${status.statusID}" ${sessionScope.statusID == status.statusID ? 'selected' : ''}>${status.statusName}</option>
                    </c:forEach>
                </select>
            </form>

            <form class="search" name="search" method="post" action="CRUDOrderList" style="display: inline-block;">
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
                <c:forEach var="order" items="${orderList}">
                    <tr>
                        <td>${order.orderID}</td>
                        <td>${order.user.fullname}</td>
                        <td>${order.orderDate}</td>
                        <td>${order.status.statusName}</td>
                        <td>${order.totalPrice}</td>
                        <td>${order.shipAddress}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/CRUDOrderDetail?orderID=${order.orderID}">
                                <button class="view-btn">View</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- Pagination -->
        <div class="pagination" style="margin: auto; justify-content: center; margin-top: 30px;">
            <form action="CRUDOrderList" method="post" style="display: inline;">
                <input type="hidden" name="indexCo" value="${indexNowCo - 1}" />
                <button type="submit" ${indexNowCo == 1 ? 'disabled' : ''}>&laquo;</button>
            </form>

            <c:forEach begin="1" end="${endPageCo}" var="o">
                <form action="CRUDOrderList" method="post" style="display: inline;">
                    <input type="hidden" name="indexCo" value="${o}" />
                    <button type="submit" class="${indexNowCo == o ? 'active' : ''}" ${indexNowCo == o ? 'disabled' : ''}>${o}</button>
                </form>
            </c:forEach>

            <form action="CRUDOrderList" method="post" style="display: inline;">
                <input type="hidden" name="indexCo" value="${indexNowCo + 1}" />
                <button type="submit" ${indexNowCo == endPageCo ? 'disabled' : ''}>&raquo;</button>
            </form>
        </div>

    </body>
</html>
