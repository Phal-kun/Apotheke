<%-- 
    Document   : DefaultJSP
    Created on : Sep 10, 2024, 10:42:16 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<jsp:forward page="ShowProductHomeServlet" />--%>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Product.forCustomer.Item" %>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">    
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/login/loginform.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/header.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/banner.css">
         <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/productlist.css">
      <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=swap">
    
    
    </head>
    <script src="${pageContext.request.contextPath}/View/Login_Register/oauthConfig.js"></script>
    
    <body class="overlay" id="overlay">
        <jsp:include page="pagecontrol/header.jsp"></jsp:include>
        
        <jsp:include page="pagecontrol/banner.jsp"></jsp:include>
        
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
                    <c:set var="firstOption" value="${item.listPrice[0]}" />
                    <p class="price" id="priceDisplay_${item.productID}">${firstOption.price} đ / ${firstOption.name}</p>
                    <!-- Các chi tiết bổ sung nếu có -->
                   
                    <p>${item.nameShow}</p>

                    <!-- Các tùy chọn -->
                    <div class="options">
                         <c:forEach var="option" items="${item.listPrice}">
                            <!-- Hiển thị tên của từng option trong danh sách listPrice -->
                              <!--  <button id="options-bt">$option.name}</button>-->
                            <button id="options-bt" onclick="updatePrice('${option.price}', '${option.name}','${item.productID}')">${option.name}</button>
                        </c:forEach>
                    </div>
                     <form action="${pageContext.request.contextPath}/addtocart" method="get">
                        <input type="hidden" name="productID" value="${item.productID}">
                        <input type="hidden" name="productName" value="${item.productName}">
                        <input type="hidden" name="productDescription" value="${item.description}">    
                        <input type="hidden" name="productPrice" id="hiddenPriceDisplay_${item.productID}" value="">
      
                        <input type="hidden" name="quantity" value="1"> <!-- Số lượng mặc định là 1 -->

                    <!-- Nút mua hàng -->
                    
                   <button type="submit" class="buy-button" onclick="syncPriceToHiddenField('${item.productID}')">Chọn mua</button>
   
                    </form>
                </div>
            </c:forEach>
            
         
            
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
        </c:if>

  
           
  </div>
    
  
  
  
        
        
  

    </body>
    
        <script>

            function updatePrice(price, name, productid) {
                 // Tìm phần tử với id chứa productid và cập nhật nội dung của nó
               const k = "priceDisplay_" + productid; // Dùng dấu "+" để nối chuỗi trong JavaScript
                const priceDisplay = document.getElementById(k); // Sử dụng biến k mà không có dấu ``

                console.log("Cố gắng tìm phần tử với id:", k); // In ra id đúng
                if (priceDisplay) {
                    const k1 =  price + " đ / "+ name;
                    priceDisplay.innerHTML = k1;
                      console.log("Đã cập nhật nội dung phần tử:", priceDisplay.innerHTML);
                        console.log("Giá:", price, "Tên:", name, "Product ID:", productid);
                } else {
                    console.log("Không tìm thấy phần tử priceDisplay cho Product ID:", productid);
                }
            };
            
            
             function syncPriceToHiddenField(productid) { 
                const k1 = "hiddenPriceDisplay_" + productid; 
                 const k2 = "priceDisplay_" + productid;
                // Lấy toàn bộ nội dung từ thẻ <p> và gán vào input ẩn
                const priceDisplay = document.getElementById(k2);
                const hiddenPrice = document.getElementById(k1);

                if (priceDisplay && hiddenPrice) {
                    hiddenPrice.value = priceDisplay.textContent; // Lấy toàn bộ chuỗi từ thẻ <p>
                    console.log("Đã đồng bộ giá trị vào input ẩn:", hiddenPrice.value);
                } else {
                    console.log("Không tìm thấy phần tử priceDisplay hoặc hiddenPrice cho Product ID:", productid);
                }
            }
            
        </script>
</html>
