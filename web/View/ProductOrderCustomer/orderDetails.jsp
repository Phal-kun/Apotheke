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
          
      <div class="cart-container">
          <div class="cart-header">
              <input type="checkbox" id="select-all" checked> Chọn tất cả (<span id="selected-count">2</span>)
          </div>
          
          <div class="cart-item">
              <input type="checkbox" class="select-item" checked>
              <img src="https://via.placeholder.com/60" alt="Panactol">
              <div class="item-info">
                  <p class="item-name">Viên nén Panactol Extra Khapharco hạ sốt, giảm đau (10 vỉ x 10 viên)</p>
                  <p class="item-price">600đ</p>
              </div>
              <div class="item-quantity">
                  <button class="minus">-</button>
                  <input type="text" value="1" class="quantity-input">
                  <button class="plus">+</button>
              </div>
              <div class="item-unit">
                  <select>
                      <option>Viên</option>
                  </select>
              </div>
              <div class="item-delete">
                  <button>🗑️</button>
              </div>
          </div>
          
          
          
          
          <div class="cart-item">
              <input type="checkbox" class="select-item" checked>
              <img src="https://via.placeholder.com/60" alt="Reihaku Hatoumuji">
              <div class="item-info">
                  <p class="item-name">Sữa rửa mặt Reihaku Hatomugi Acne Care and Facial Washing (130g)</p>
                  <p class="item-price"><span class="old-price">109.000đ</span> 76.300đ</p>
              </div>
              <div class="item-quantity">
                  <button class="minus">-</button>
                  <input type="text" value="1" class="quantity-input">
                  <button class="plus">+</button>
              </div>
              <div class="item-unit">
                  <select>
                      <option>Túyp</option>
                  </select>
              </div>
              <div class="item-delete">
                  <button>🗑️</button>
              </div>
          </div>
          
      </div>  
        <script src="${pageContext.request.contextPath}/View/ProductOrderCustomer/showOrderdetails.js"></script>
   
</html>
