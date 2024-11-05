<%-- 
    Document   : showProduct
    Created on : Nov 2, 2024, 4:49:57 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Product.forCustomer.Item" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="productlist.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=swap">
    </head>
    <body>
        <%
            List<List<Item>> groupedItems = (List<List<Item>>) session.getAttribute("groupedItems");
            Integer groupCount = (Integer) session.getAttribute("groupCount");
        %>
    <div class="product-list">
        <c:if test="${not empty itemsAtPageIndex}">
            <c:forEach var="item" items="${itemsAtPageIndex}">
                <div class="product-card">
                    <!-- Hình ảnh sản phẩm   <img src="$item.image}" alt="$item.name}">-->
                    <img src="tovalgan-ef.jpg" alt="Tovalgan Ef">

                    <!-- Tên và mô tả sản phẩm -->
                    <h2>${item.productName} - ${item.description}</h2>

                    <!-- Giá sản phẩm  <p class="price">$item.price} đ / Hộp</p>-->
                    
                    <p class="price">40.000đ / Hộp</p>    
                    <!-- Các chi tiết bổ sung nếu có -->
                    <p>${item.nameShow}</p>

                    <!-- Các tùy chọn -->
                    <div class="options">
                        <c:forEach var="option" items="${item.listPrice}">
                            <!-- Hiển thị tên của từng option trong danh sách listPrice -->
                            <button id="options-bt">${option.name}</button>
                        </c:forEach>
                    </div>

                    <!-- Nút mua hàng -->
                    <button class="buy-button">Chọn mua</button>
                </div>
            </c:forEach>
        </c:if>

  
   
</div>
  <!-- Product 13 -->
  
  <!-- Pagination -->

  
  </div>
  <div class="pagination1">
     <form action="${pageContext.request.contextPath}/ShowProductHomeServlet" method="post" style="display: inline;">
        <!-- Nút "Trang trước" -->
        <input type="hidden" name="pageIndex" value="${currentIndex > 0 ? currentIndex - 1 : 0}" />
        <button type="submit">&laquo; Trang trước</button>
    </form>
     <div class="pagination1">
        <c:forEach var="i" begin="1" end="${groupCount}">
            <!-- Form submit cho mỗi nút -->
            <form action="${pageContext.request.contextPath}/ShowProductHomeServlet" method="post" style="display: inline;">
                <!-- Truyền index của trang cần hiển thị về servlet -->
                <input type="hidden" name="pageIndex" value="${i - 1}" />
                <button type="submit">${i}</button>
            </form>
        </c:forEach>
    </div>
     <form action="${pageContext.request.contextPath}/ShowProductHomeServlet" method="post" style="display: inline;">
        <!-- Nút "Trang sau" -->
        <input type="hidden" name="pageIndex" value="${currentIndex < groupCount - 1 ? currentIndex + 1 : groupCount - 1}" />
        <button type="submit">Trang sau &raquo;</button>
    </form>
  </div>

    </body>
    
    
    <script>
       
    </script>
</html>
