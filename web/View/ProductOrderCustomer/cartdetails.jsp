<%-- 
    Document   : orderDetails
    Created on : Oct 18, 2024, 4:15:43 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/login/showOrderDetails.css"> 
       
    </head>
    <body>
        <c:if test="${not empty productsData}"> 
            <div class="cart-header">
                <input type="checkbox" id="select-all" checked> Chọn tất cả (<span id="selected-count">${cartCount}</span>)
            </div>
                <c:set var="product" value="${entry.product}" />
                <c:set var="productUnitList" value="${entry.productUnitList}" />
                <c:set var="productDetail" value="${entry.productDetail}" />
                <c:set var="cartCount" value="${entry.cartCounts}" />
                  
                <c:forEach var="i" begin="1" end="${cartCount}">  
                  
                <div class="cart-container">
                    <div class="cart-item">
                        <input type="checkbox" class="select-item" checked>
                        <img src="https://via.placeholder.com/60" alt="Panactol">
                        <div class="item-info">
                            <p class="item-name">${product.productName}</p>
                            <p class="item-price">Giá: ${product.price} đồng</p>
                        </div>
                        
                        <div class="item-quantity">
                            <button class="minus">-</button>
                            <input type="text" value="1" class="quantity-input">
                            <button class="plus">+</button>
                        </div>
                        
                        <div class="item-unit">
                            <c:forEach var="unit" items="${productUnitList}">
                                <option value="${unit.unitID}">${unit.productUnitName}</option>
                            </c:forEach>
                        </div>
                        <div class="item-delete">
                            <button>🗑️</button>
                          </div>
                    </div>
          
        
            </div>
            </c:forEach>
        </c:if>
        
        <c:if test="${empty productsData}">
            <p>Không có sản phẩm trong giỏ hàng.</p>
        </c:if>  

          
    </body>      
        <script src="${pageContext.request.contextPath}/View/ProductOrderCustomer/showOrderdetails.js"></script>
   
</html>
