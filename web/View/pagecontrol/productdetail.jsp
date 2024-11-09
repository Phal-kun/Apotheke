<%-- 
    Document   : productdetail
    Created on : Nov 8, 2024, 9:34:41 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="Controller.Product.forCustomer.Item" %>
<%@ page import="Model.Product.Product" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/productdetail.css">
        <script>
    // Hàm để cập nhật giá hiển thị và thay đổi nút đang chọn
    function updatePrice(price, button) {
        // Cập nhật giá trong thẻ <span>
        document.querySelector('.custom-current-price').innerText = price.toLocaleString('vi-VN') + 'đ';

        // Loại bỏ lớp 'active' từ tất cả các nút
        document.querySelectorAll('.custom-option-btn').forEach(btn => {
            btn.classList.remove('active');
        });

        // Thêm lớp 'active' vào nút vừa được nhấn
        button.classList.add('active');
    }
</script>
    </head>
    <body>
        
        <jsp:include page="header.jsp"></jsp:include>
       <c:set var="item" value="${items}" />
       <c:set var="productInfo" value="${product}" />
      <div class="custom-product-container">
        <div class="custom-product-images">
            <img src="IMAGINE/productima/${item.imagine}" alt="Main Product" class="custom-main-image">
            <div class="custom-thumbnail-images">
               
             
           
            </div>
        </div>
        <div class="custom-product-details">
            <h2>${item.productName} - ${item.description}</h2>
            <p class="custom-product-code">00046430 • 5 ★ </p>
            <p class="custom-price">
                <span class="custom-current-price" id="productdetailprice_${item.productID}" >  ${item.listPrice[0].price}đ</span> 
            </p>
             <div class="custom-product-options">
                 <c:forEach var="option" items="${item.listPrice}">
                    <button 
                        class="custom-option-btn${status.first ? 'active' : ''}" id="productdetailname_${item.productID}"
                        onclick="updatePrice(${option.price}, this)">
                        ${option.name}
                    </button>
                </c:forEach>
            </div>
           
            <ul class="custom-product-info">
                <li><strong>Category: </strong> ${productInfo.category.categoryName}</li>
                
                <li><strong>Unit:</strong> Hộp</li>
                <li><strong>Brand Origin:</strong> ${productInfo.origin.originName}</li>
                <li><strong>Manufacturer:</strong> ${productInfo.manufacturer}</li>
                <li><strong>Origin: </strong>${productInfo.origin.originName}</li>
                <li><strong>Component:</strong> ${productInfo.componentDescription}</li>
            </ul>
            <p class="custom-short-description">
                Short description: ${item.description} 
            </p>
             
            <div class="custom-purchase-options">
                <label for="custom-quantity">Select quantity</label>
                <form action="${pageContext.request.contextPath}/addtocart" method="post">
                    <input type="number" id="custom-quantity" value="1" min="1" max="999">
                    <input type="hidden" name="productID" value="${item.productID}">
                    <input type="hidden" id="selectedPrice" name="selectedPrice" value="">
                    <input type="hidden" id="selectedOption" name="selectedOption" value="">
                    <input type="hidden" id="quantity" name="quantity" value="">

                    <button class="custom-buy-button" >Buy</button>
                </form>
            </div>
        </div>
    </div>
    </body>
    <script>
    // Hàm cập nhật giá trị của giá và option vào các trường input ẩn
    function updateSelectedValues(productID) {
        // Lấy giá trị từ thẻ có id "productdetailprice_${productID}" (giá)
        const priceElement = document.getElementById("productdetailprice_" + productID);
        const priceValue = priceElement ? priceElement.textContent.trim() : "";

        // Lấy giá trị từ thẻ có class "custom-option-btn active" (option)
        const optionElement = document.querySelector(".custom-option-btn.active");
        const optionValue = optionElement ? optionElement.textContent.trim() : "";
        
        // Gán giá trị vào các trường input ẩn
        document.getElementById("selectedPrice").value = priceValue;
        document.getElementById("selectedOption").value = optionValue;

        // In ra console để kiểm tra
        console.log("Selected Price: " + priceValue);
        console.log("Selected Option: " + optionValue);
    }

    // Gọi hàm này khi trang được tải hoặc khi bạn muốn cập nhật giá trị ban đầu
   

    // Nếu bạn muốn gọi hàm này khi nhấn vào nút "Buy"
    const buyButton = document.querySelector('.custom-buy-button');
    if (buyButton) {
        buyButton.addEventListener('click', function(event) {
            event.preventDefault();  // Ngăn chặn hành động mặc định của form
            const productID = ${item.productID}; // Giả sử item.productID được sử dụng trong JSP
            updateSelectedValues(productID);
            // Có thể thêm các xử lý khác khi click vào nút Buy
            const quantityValue = document.getElementById("custom-quantity").value;
            console.log("Selected Quantity: " + quantityValue);

            const form = buyButton.closest('form'); // Lấy form chứa nút Buy
            form.submit(); // Gửi form đến servlet
        });
    }
</script>

</html>
