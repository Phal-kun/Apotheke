<%-- 
    Document   : DefaultJSP
    Created on : Sep 10, 2024, 10:42:16 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/login/loginform.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/login/showproduct.css"> 
    </head>
    <script src="Login_Register/oauthConfig.js"></script>
    
    <body class="overlay" id="overlay">
               
        <button class ="open-button" id ="de" onclick="openForm()"> login  </button>

        <div class="form-login" id="my-form">
            <span id="close">
                <img  src="${pageContext.request.contextPath}/IMAGINE/login/x.png" onclick="closeForm()"> 
            </span>
            <form action = "${pageContext.request.contextPath}/loginuser" method="get">
                <h1 class="titds"> Login</h1>
                <div class="ds">Vui lòng đăng nhập để hưởng những đặc<br> quyền dành cho thành viên.</div>
        
                <input class="user" type="text" id="username" name ="username"placeholder="Enter phone number or email " value="${enteredUsername != null ? enteredUsername : ''}"><br>
                <span class="user0-icon ">
                    <img src="${pageContext.request.contextPath}/IMAGINE/login/user.png">
                </span>
                <div class="password-wrapper">
                    <input class="pass" type="password" id="password"name="password" placeholder="Enter your password" value="${enteredPassword != null ? enteredPassword : ''}">                   
                </div>
                <span class="pass-icon">
                    <img src="${pageContext.request.contextPath}/IMAGINE/login/pass1.png">
                </span>
                <span class="eye-icon" onclick="togglePassword()">
                    <img src="${pageContext.request.contextPath}/IMAGINE/login/hide.png" id="toggleEye" alt="Show/Hide Password" />
                </span> 

                <span class="forgot-password">
                   <!-- register -->
                    <a onclick="openForm3()" class="link2">Register</a>                
                    <a href="${pageContext.request.contextPath}/View/Login_Register/forgotPassword.jsp" class="link1">Forgot Password?</a>
                    
                </span>
                <button type="submit" class ="submitds" >Login Now </button><br>
               
                
                <div class="error-message2" style="color: red">
                        ${mess}
                </div>
               

                <h3 class="login-separator">
                    <span>or login with google</span>
                </h3>
                
            
            </form>
             <button class="gsi-material-button" onclick="window.location.href=googleOAuthURL">
                <div class="gsi-material-button-state"></div>
                <div class="gsi-material-button-content-wrapper">
                    <div class="gsi-material-button-icon">
                        <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" xmlns:xlink="http://www.w3.org/1999/xlink" style="display: block;">
                            <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"></path>
                            <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"></path>
                            <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"></path>
                            <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"></path>
                            <path fill="none" d="M0 0h48v48H0z"></path>
                        </svg>
                    </div>
                    <span class="gsi-material-button-contents">Sign in with Google</span>
                    <span style="display: none;">Sign in with Google</span>
                </div>
            </button>
            
        </div>
               
        <!--   register-->
        
        <div class="register-form" id="register-de">
        <form action="${pageContext.request.contextPath}/register" method="get" >
        <span id="close">
            <img src="${pageContext.request.contextPath}/IMAGINE/login/x.png" onclick="closeForm3()"> 
        </span>
        <h1 class="ti">Register</h1>
        <div class="ti2">Đăng kí để có trải nghiệm tuyệt vời</div>
        <div>
            <input class="fullname" type="text" id="fullname" name ="fullname" placeholder="Enter your full name"
              value="${enteredFullname != null ? enteredFullname : ''}"   ><br>

            <span class="user0-icon">
                <img src="${pageContext.request.contextPath}/IMAGINE/login/user.png">
            </span>    
        </div>
        <div>
            <input class="user2" type="email" id="username" name ="username" placeholder="Enter email"
                   value="${enteredEmail != null ? enteredEmail : ''}"
                   ><br>
            <span class="user0-icon">
                <img src="${pageContext.request.contextPath}/IMAGINE/login/user.png">
            </span>    
        </div>
        <div >
           <input class="user2" type="text" id="codevery" name="codevery" placeholder="Enter your code">   
        </div>
        <div >
            <input type="submit" value="|Send Code" id="sendCodebut" class="sendCodebut" name="btAction"/>
        </div>
        <div>
            <input class="pass2" type="password" id="password" name="password" placeholder="Enter password"
                    value="${enteredPassword != null ? enteredPassword : ''}"
                   ><br>
            <span class="pass-icon2">
                <img src="${pageContext.request.contextPath}/IMAGINE/login/pass1.png">
            </span> 
            
        </div>

        <div>
            <input class="pass21" type="password" id="confirmPassword" name="confirmPassword" placeholder="Confirm password"
                    value="${enteredConfirmPassword != null ? enteredConfirmPassword : ''}"
                   ><br>
            <span class="pass-icon2">
                <img src="${pageContext.request.contextPath}/IMAGINE/login/pass1.png">
            </span> 
            
        </div>
        <div class="error-message" style="color: red">
            ${mess2}
        </div>
        <div class ="dsweq">   
                <span class="acc" id="acc2"> 
                    <a onclick="openForm()">Do you have an account?</a>    
                </span>
        </div>  
        
        <button type="submit" class="submitds2" name ="btAction" value="register">Register Now</button><br>
        </form>
        <span id="dwe">or register with google</span>
        
                <button class="gsi-material-button" onclick="window.location.href=googleOAuthURL">
                    <div class="gsi-material-button-state"></div>
                    <div class="gsi-material-button-content-wrapper">
                        <div class="gsi-material-button-icon">
                            <svg version="1.1" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 48 48" xmlns:xlink="http://www.w3.org/1999/xlink" style="display: block;">
                                <path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"></path>
                                <path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"></path>
                                <path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"></path>
                                <path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"></path>
                                <path fill="none" d="M0 0h48v48H0z"></path>
                            </svg>
                        </div>
                        <span class="gsi-material-button-contents">Regiser with google</span>
                        <span style="display: none;">Register with google </span>
                    </div>
                </button>


        </div>
        <!--        <div> $verificationCode </div>  -->
    

        <!-- show list product  -->
        
       
          <h1>Danh sách sản phẩm</h1>
            <div class="product-grid">
                <c:forEach var="product" items="${products}" varStatus="status">
                    <div class="product-card" style="display: ${status.index < 12 ? 'block' : 'none'};">
                        <img src="${product.imageURL}" alt="${product.productName}">
                        <h3>${product.productName}</h3>
                        <p>Giá: ${product.baseSoldPrice} VNĐ</p>
                        <a href="addToCart?id=${product.productID}" class="btn">Thêm vào giỏ hàng</a>
                    </div>
                </c:forEach>
            </div>

            <div style="text-align: center; margin-top: 20px;">
                <button id="loadMore" onclick="showMoreProducts()">Xem thêm</button>
            </div>

        
        

        
        
        
        
        
        
        
        
        
        

    </body>
    
        <script>

        function openForm(){
            document.getElementById("my-form").style.display = "block";
            document.getElementById("overlay").style.display ="";
            document.getElementById("my-form").classList.add("show");
            document.getElementById("register-de").style.display = "none";
        }
        function closeForm(){
            document.getElementById("my-form").style.display ="none";
            document.getElementById("register-de").style.display = "none";
        }
        
        function openForm3(){
            document.getElementById("my-form").style.display = "none";
            document.getElementById("overlay").style.display ="";
            document.getElementById("register-de").classList.add("show");
            document.getElementById("register-de").style.display = "block";
        }
        function closeForm3(){
            document.getElementById("register-de").style.display ="none";
            document.getElementById("my-form").style.display ="none";
        }
        function togglePassword() {
            var passwordInput = document.getElementById("password");
            var eyeIcon = document.getElementById("toggleEye");

            if (passwordInput.type === "password") {
                passwordInput.type = "text";
                eyeIcon.src = "${pageContext.request.contextPath}/IMAGINE/login/hide.png"; // Thay đổi biểu tượng thành "mắt đóng"
            } else {
                passwordInput.type = "password";
                eyeIcon.src = "${pageContext.request.contextPath}/IMAGINE/login/view.png"; // Thay đổi biểu tượng thành "mắt mở"
            }
        }
       

        window.onload = function() {
                var mess = '${mess}';
                var mess2 = '${mess2}';

                if (mess && mess !== "") {
                    openForm();  
                }

                // Check if mess2 is not null or empty
                if (mess2 && mess2 !== "") {
                    openForm3();  // Open the registration form if mess2 has a value
                }
            }

        
        // phan trang 
        let currentProducts = 12; // Số lượng sản phẩm hiện tại hiển thị
        const productsPerPage = 12; // Tổng sản phẩm trên 1 trang

        function showMoreProducts() {
            currentProducts += 12; // Thêm 12 sản phẩm khi nhấn "Xem thêm"
            const allProducts = document.querySelectorAll('.product-card');
            allProducts.forEach((product, index) => {
                if (index < currentProducts) {
                    product.style.display = 'block'; // Hiện sản phẩm
                }
            });

            // Ẩn nút "Xem thêm" nếu không còn sản phẩm nào để hiển thị
            if (currentProducts >= allProducts.length) {
                document.getElementById('loadMore').style.display = 'none';
            }
        }
            
        
          
    
        </script>
</html>
