<%-- 
    Document   : cart
    Created on : Nov 4, 2024, 8:01:32 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.List" %>
<%@ page import="Controller.Product.forCustomer.Item" %>
<%@ page import="Controller.Product.forCustomer.Cart" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
          <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/cart.css">
        <script>
          // Định nghĩa hàm showPopup
          function showPopup(message) {
              alert(message); // Sử dụng alert để hiển thị thông báo
          }
          function increment(button) {
            // Lấy input quantity hiện tại và tăng thêm 1
            const cartItem = button.closest('.cart-item');
            const index = cartItem.getAttribute('data-index');
            // console.log("index", index); 
            const k1 = 'quantity_'+index;
            const quantityInput = document.getElementById(k1);
          //  console.log("quantityInput", quantityInput);  // In ra quantity mới sau khi tăng
            const quantityString = quantityInput.textContent.trim(); // Lấy chuỗi giá và loại bỏ khoảng trắng
            const quantityds = quantityString.replace(/\D/g, ''); // Loại bỏ tất cả ký tự không phải số
            const quantityValue1 = parseFloat(quantityds); 
        //    console.log("quantityValue:", quantityValue1);                   
            const k = 'price_'+index;
            const priceElement = document.getElementById(k);
            const priceString = priceElement.textContent.trim();
            const numericString = priceString.replace(/\D/g, ''); // Loại bỏ tất cả ký tự không phải số
            const numericValue = parseFloat(numericString);
           // console.log("numericValue", numericValue);
            
            let lastPrice = numericValue / quantityValue1 * (quantityValue1+1);
            let q2 = quantityValue1 +1;
            
            priceElement.textContent = lastPrice.toLocaleString('vi-VN')+ ''; 
            priceElement.innerHTML += '<sup>đ</sup>';
            quantityInput.textContent = q2;
            updateSelectionCount();
            console.log("index:", index);
             console.log("lastPrice", lastPrice);
              console.log("quantity", q2);
            updateCart(index,q2,lastPrice);
        };

        function decrement(button) {
            const cartItem = button.closest('.cart-item');
            const index = cartItem.getAttribute('data-index');
            // console.log("index", index); 
            const k1 = 'quantity_'+index;
            const quantityInput = document.getElementById(k1);
            console.log("quantityInput", quantityInput);  // In ra quantity mới sau khi tăng
            const quantityString = quantityInput.textContent.trim(); // Lấy chuỗi giá và loại bỏ khoảng trắng
            const quantityds = quantityString.replace(/\D/g, ''); // Loại bỏ tất cả ký tự không phải số
            const quantityValue1 = parseFloat(quantityds); 
            console.log("quantityValue:", quantityValue1);    
             if (quantityValue1 > 1) {
                const k = 'price_'+index;
                const priceElement = document.getElementById(k);
                 const priceString = priceElement.textContent.trim();
                 const numericString = priceString.replace(/\D/g, ''); // Loại bỏ tất cả ký tự không phải số
                const numericValue = parseFloat(numericString);
                console.log("numericValue", numericValue);

                let lastPrice = numericValue / quantityValue1 * (quantityValue1-1);
                let q2 = quantityValue1 -1;

                priceElement.textContent = lastPrice.toLocaleString('vi-VN')+'';
                priceElement.innerHTML += '<sup>đ</sup>';
                quantityInput.textContent = q2;
                updateSelectionCount();
                updateCart(index,q2,lastPrice);
            }else {
                console.log("Số lượng không thể giảm vì nó đã ở mức tối thiểu (1)");
            }
        };

       

        function selectAllItems() {
            const selectAllCheckbox = document.getElementById('select-all');
            const itemCheckboxes = document.querySelectorAll('.item-checkbox');

            // Đặt trạng thái cho tất cả các checkbox sản phẩm
            itemCheckboxes.forEach(checkbox => {
                checkbox.checked = selectAllCheckbox.checked;
            });

            // Cập nhật số lượng sản phẩm được chọn
            updateSelectionCount();
        };
       
        
            function updateSelectionCount() {
                const checkboxes = document.querySelectorAll('.item-checkbox');
                let totalAmount = 0; // Biến lưu trữ tổng số tiền

                checkboxes.forEach(checkbox => {
                    if (checkbox.checked) {
                        const cartItem = checkbox.closest('.cart-item');
                        const priceElement = cartItem.querySelector(`span[id^="price_"]`);
                        const priceString = priceElement.textContent.trim();
                        const numericString = priceString.replace(/\D/g, ''); // Loại bỏ ký tự không phải số
                        const priceValue = parseFloat(numericString); // Chuyển đổi thành số

                        totalAmount += priceValue; // Cộng dồn vào tổng số tiền
                    }
                });

                // Cập nhật vào ô tổng số tiền
                const totalPriceDisplay = document.getElementById('total-price');
                totalPriceDisplay.textContent = 'Tổng giá: ' + totalAmount.toLocaleString('vi-VN') + 'đ'; // Cập nhật tổng số tiền
            };
            
            function updateCart(index, quantity, price) {
//             console.log("index:", index);
//             console.log("price", price);
//              console.log("quantity", quantity);
//              const xhr = new XMLHttpRequest();
//               const url = `/Apotheke/updateCartServlet?index=${index}&quantity=${quantity}&price=${price}`;
//    
            const xhr = new XMLHttpRequest();
            const k = "index:"+index+"price"+price+"quantity:"+quantity;  // Chuỗi bạn muốn gửi
            
            // Tạo URL với các tham số
            xhr.open('GET', `${pageContext.request.contextPath}/updateCartServelet?k=`+ k, true);

            // Xử lý phản hồi từ server
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    console.log("Đã gửi thành công");
                    console.log(`${pageContext.request.contextPath}/updateCartServlet?index=${index}&quantity=${quantity}&price=${price}`);

                    console.log(xhr.responseText);
                }
            };

            // Gửi yêu cầu mà không thêm tham số vào `send` vì chúng đã có trong URL
            xhr.send();
        };

        
      </script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <%
            // Lấy cart từ session
            Cart cart = (Cart) session.getAttribute("cart");
            double totalPrice = 0.0; 
            for (Item cartItem : cart.getListItems()) {
                totalPrice += cartItem.getPrice() ; // Cộng giá trị vào totalPrice
            }
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTotalPrice = currencyFormat.format(totalPrice);
        %>
        
        <div class="cart-container">
        <!-- Cart Header -->
        <div class="cart-header">
            <input type="checkbox" id="select-all" onclick="selectAllItems()" checked>
            <span>Tên sản phẩm</span>
            <span>Giá thành</span>
            <span>Số lượng</span>
            <span>Đơn vị</span>
            <span>Xóa</span>
        </div>

        <!-- Cart Item 1 -->
         <c:if test="${not empty cart}">
            <c:forEach var="item" items="${cart.getListItems()}" varStatus="status">
                <div class="cart-item" data-price="${item.price}" data-index="${status.index}">
                    <input type="checkbox" class="item-checkbox" onclick="updateSelectionCount()" checked>
                    <span>${item.productName} - ${item.description}</span>
<!--                <span class="price">$item.listPrice[0].price.toLocaleString('vi-VN')}đ</span>-->
                    <span class="price_${status.index}"id="price_${status.index}">${item.getFormattedTotalPrice()} </span>
                    <div class="quantity-control">
                        <button onclick="decrement(this)">-</button>
                        <span id="quantity_${status.index}"> ${item.quantity} </span>
                        <button onclick="increment(this)">+</button>
                    </div>
                    <span>${item.listPrice[0].name}</span>
                    <span class="delete-btn" onclick="removeItem(this)">🗑️</span>
                </div>
            </c:forEach>
        </c:if>
        <c:if test="${empty cart}">
            <div class="empty-cart-message">
                <p>Giỏ hàng của bạn hiện đang trống.</p>
            </div>
        </c:if>
    </div>
    <div class="total-price" id="total-price">Tổng giá:   <%= formattedTotalPrice %></div>
     
    <form action ="${pageContext.request.contextPath}/submitorder" method="get">
            
        <input type="submit" value="Submit Order" />
    </form> 
    <c:if test="${not empty loginMessage}">
        <script>
            var message = '${loginMessage}'; // Truyền giá trị của loginMessage vào biến JavaScript
            console.log("Login Message: " + message); // Ghi lại giá trị của message
            showPopup(message);
            </script>
    </c:if>
    </body>
    <script>

         // Hàm tăng/giảm số lượng và cập nhật giá
        
    </script>
</html>
