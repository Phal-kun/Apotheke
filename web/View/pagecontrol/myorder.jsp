<%-- 
    Document   : myorder
    Created on : Nov 8, 2024, 10:58:11 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="Model.Order.Order" %>
<%@ page import="Model.Order.Status" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/myorder.css">
     
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        
        <div class="myordercontainer">
         <div class="tab-container">
            <div class="tab" data-status="pending" onclick="showOrders('pending')">Pending</div>
            <div class="tab" data-status="processing" onclick="showOrders('processing')">Processing</div>
            <div class="tab" data-status="completed" onclick="showOrders('completed')">Completed</div>
            <div class="tab" data-status="shipped" onclick="showOrders('shipped')">Shipped</div>
            <div class="tab" data-status="canceled" onclick="showOrders('canceled')">Canceled</div>
        
         </div>
          <div id="pending" class="order-list active">
            <c:forEach var="order" items="${orders}">
               <c:if test="${order.status.statusID == 1}"> <!-- Status ID 1 = Pending -->
                   <div class="order-item">
                       Order #${order.orderID} - Waiting for pending - Total Price: ${order.totalPrice}
                   </div>
                    <form action="CancelOrderServlet" method="get">
                        <input type="hidden" name="orderID" value="${order.orderID}" />
                        <button type="submit" class="cancel-btn">Cancel Order</button>
                    </form>
               </c:if>
           </c:forEach>
           </div>     
                
            <div id="processing" class="order-list">
                <c:forEach var="order" items="${orders}">
                   <c:if test="${order.status.statusID == 2}"> <!-- Status ID 5 = Processing -->
                       <div class="order-item">
                           Order #${order.orderID} - Waiting for processing 
                       </div>
                   </c:if>
                </c:forEach>
            </div>

            
            <div id="completed" class="order-list">
                 <c:forEach var="order" items="${orders}">
                    <c:if test="${order.status.statusID == 5}"> <!-- Status ID 2 = Completed -->
                        <div class="order-item">
                            Order #${order.orderID} - Completed - Total Price: ${order.totalPrice}
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        <div id="shipped" class="order-list">
             <c:forEach var="order" items="${orders}">
            <c:if test="${order.status.statusID == 3}"> <!-- Status ID 4 = Shipped -->
                <div class="order-item">
                    Order #${order.orderID} - Delivering -  Total Price: ${order.totalPrice}
                </div>
            </c:if>
             </c:forEach>
        </div>
            
            
            
        <div id="canceled" class="order-list">
               <c:forEach var="order" items="${orders}">
                    <c:if test="${order.status.statusID == 4}"> <!-- Status ID 3 = Canceled -->
                        <div class="order-item">
                            Order #${order.orderID} - Canceled   -  Total Price: ${order.totalPrice}
                        </div>
                    </c:if>
                </c:forEach>
        </div>
            
            
    </div>
    </body>
    <script>
         function showOrders(status) {
        // Xóa 'active' của tất cả các tab và ẩn tất cả order-list
        document.querySelectorAll('.tab').forEach(function(tab) {
            tab.classList.remove('active');
        });
        document.querySelectorAll('.order-list').forEach(function(orderList) {
            orderList.classList.remove('active');
        });

        // Thêm 'active' cho tab được nhấn và hiển thị order-list tương ứng
        document.querySelector('.tab[data-status="' + status + '"]').classList.add('active');
        document.getElementById(status).classList.add('active');
    }

    // Mặc định hiển thị tab 'processing'
    document.addEventListener("DOMContentLoaded", function() {
        showOrders('processing');
    });
    </script>
</html>
