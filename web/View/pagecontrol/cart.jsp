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
<%@ page import="Model.User.User" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
         
          <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/cart.css">
     
        <script >
               
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
            window.location.reload();
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
                window.location.reload();
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

        
        function showPopup(message) {
              alert(message); // Sử dụng alert để hiển thị thông báo
        };
            
        function setCurrentTime() {
        // Lấy thời gian hiện tại
        var currentTime = new Date().toISOString();  // ISO format: YYYY-MM-DDTHH:MM:SS.sssZ
        
        // Gán giá trị vào trường hidden
        document.getElementById('currentTime').value = currentTime;
        };
        
        </script>
        
    </head>
    
             

       
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <%
            // Lấy cart từ session
            Cart cart = (Cart) session.getAttribute("cart");
        %>
         <!-- Cart Item 1 -->
        <c:if test="${not empty cart}">
            <% 
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
           
        </div>

       
            <c:forEach var="item" items="${cart.getListItems()}" varStatus="status">
                <div class="cart-item" data-price="${item.price}" data-index="${status.index}">
                    <input type="checkbox" class="item-checkbox" onclick="updateSelectionCount()" checked>
                    <span>${item.productName} - ${item.description}</span>
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
            <div class="total-price" id="total-price">Tổng giá:   <%= formattedTotalPrice %></div>

            <form action ="${pageContext.request.contextPath}/submitorder" method="get">

                <input type="submit" value="Submit Order" />
            </form> 
         </div>
        </c:if>
        
        <c:if test="${empty cart}">
            <div class="emptycart-container">
                <img src="../../IMAGINE/banner/11010851.png" alt="Empty Cart">
                <div class="emptycart-message">Chưa có sản phẩm nào trong giỏ</div>
                <div class="emptycart-subtext">Cùng khám phá hàng ngàn sản phẩm tại APOTHEKE shop nhé!</div>
                <a href="${pageContext.request.contextPath}/View/Home.jsp" class="emptycart-button">Khám phá ngay</a>
            </div>
        </c:if>
        
      
        <c:if test="${not empty loginMessage}">
            <script>
                var message = '${loginMessage}'; // Truyền giá trị của loginMessage vào biến JavaScript
                console.log("Login Message: " + message); // Ghi lại giá trị của message
                showPopup(message);
            </script>
        </c:if>
    
      
        <c:if test="${not empty formsubmit}" >
         
             <% 
            double totalrice = 0.0; 
            for (Item cartItem : cart.getListItems()) {
                totalrice += cartItem.getPrice() ; // Cộng giá trị vào totalPrice
            }
            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedTotalPridce = currencyFormat.format(totalrice);
            %>
            <c:if test="${not empty sessionScope.account}">
                    <form id="formShip-form" action="${pageContext.request.contextPath}/submitorder" method="post">
                <div id="formShip-order-section" class="section">
                    <h2>Thông tin người đặt</h2>

                    <div class="form-group">
                        <label for="formShip-orderer-name">Họ và tên người đặt</label>
                       <input type="text" id="formShip-orderer-name" name="ordererName" 
                      value="${sessionScope.account.fullname != null ? sessionScope.account.fullname : ''}" required readonly style="background-color: #745a5a70; color: black;">
                    </div>

                    <div class="form-group">
                        <label for="formShip-orderer-phone">Số điện thoại</label>
                        <input type="text" id="formShip-orderer-phone" name="ordererPhone" 
                          value="${sessionScope.account.phone != null ? sessionScope.account.phone : ''}"  required    >
                    </div>

                    <div class="form-group">
                        <label for="formShip-orderer-email">Email</label>
                        <input type="email" id="formShip-orderer-email" name="ordererEmail" 

                          value="${sessionScope.account.username != null ? sessionScope.account.username : ''}" required readonly style="background-color: #745a5a70; color: black;">      
                    </div>
                </div>
               
                
                    
                    
                    
                    
                <div id="formShip-address-section" class="section">
                    <h2>Địa chỉ nhận hàng</h2>

                    <div class="form-group">
                        <label for="formShip-receiver-name">Họ và tên người nhận</label>
                        <input type="text" id="formShip-receiver-name" name="receiverName" 
                            placeholder="Họ và tên người nhận" required>
                    </div>

                    <div class="form-group">
                        <label for="formShip-receiver-phone">Số điện thoại</label>
                        <input type="text" id="formShip-receiver-phone" name="receiverPhone" 
                       value="${sessionScope.account.phone != null ? sessionScope.account.phone : ''}"          placeholder="Số điện thoại" required>
                    </div>



                    <div class="form-group">
                        <label for="formShip-address">Nhập địa chỉ cụ thể</label>
                        <input type="text" id="formShip-address" name="address" 
                       value="${sessionScope.account.address != null ? sessionScope.account.address : ''}"        placeholder="Nhập địa chỉ cụ thể" required>
                    </div>
                </div>
                 <input type="hidden" id="currentTime" name="currentTime">
                 
                 <!-- form tien  -->
                 
                 
                 
                 
                 <div id="formSubmitOrder">
                    
                        <div class="order-summary">Order Summary</div>

                        <div class="price-line total">
                            <span>Total:</span>
                            <span class="total-price"> <%= formattedTotalPridce %></span>
                        </div>

                        <div class="price-line voucher">
                            <span>Voucher Discount:</span>
                            <span class="voucher-amount">0₫</span>
                        </div>

                        <div class="price-line shipping">
                            <span>Shipping Fee:</span>
                            <span class="shipping-fee">Free</span>
                        </div>

                        <div class="final-price">
                           <%= formattedTotalPridce %>
                        </div>
                         <button type="submit"  id="completeOrder"onclick="setCurrentTime()">Submit Order</button>
                        

                        <p>By proceeding, you agree to the <a href="#">Terms of Service</a> and <a href="#">Privacy Policy</a> of APOTHEKE Pharmacy.</p>
                       
                </div>
            </form>
            </c:if>
       
        </c:if>
    
        <c:if test="${not empty Notofication}" >
            <script>
                 var Notofication = "${Notofication}"; 
                console.log(Notofication);
            </script>
                <div class="order-notification" id="orderNotification">
                    <h2>Đơn hàng của bạn</h2>
                    <p>Đơn hàng của bạn đang được phê duyệt.</p>
                    <button class="close-button" onclick="closeOrderNotification()">Đóng</button>
                </div>
        </c:if>
        
            
    </body>
    <script>
         function closeOrderNotification() {
        // Lấy phần tử div bằng ID và ẩn nó
        var notification = document.getElementById("orderNotification");
        if (notification) {
            notification.style.display = "none";
        }
    }   
     function removeItem(button) {

                    const cartItem = button.closest('.cart-item');
                    const index = cartItem.getAttribute('data-index');
                    RemoveCart(index);
                    window.location.reload();
                    };
                    
            function RemoveCart(index) {
                const xhr = new XMLHttpRequest();
                const k = index;
                // Tạo URL với các tham số
                xhr.open('POST', `${pageContext.request.contextPath}/updateCartServelet?k=`+ k, true);
                // Xử lý phản hồi từ server
                xhr.onreadystatechange = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        console.log("Đã gửi thành công");
                        console.log(`${pageContext.request.contextPath}/updateCartServlet?index=${index}`);
                        console.log(xhr.responseText);
                    }
                };
                // Gửi yêu cầu mà không thêm tham số vào `send` vì chúng đã có trong URL
                xhr.send();
            };
    </script>
</html>
