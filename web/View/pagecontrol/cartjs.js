/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
        // Định nghĩa hàm showPopup
          
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
        
       