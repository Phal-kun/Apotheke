<%-- 
    Document   : header
    Created on : Nov 1, 2024, 11:07:23 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">    
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/login/loginform.css">
       <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=swap">
    
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/header.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined&display=swap">
        
    </head>
    <script src="${pageContext.request.contextPath}/View/Login_Register/oauthConfig.js"></script>
    
    <body class="overlay" id="overlay">

        <div class="container">
          <header class="header">
              <a href="${pageContext.request.contextPath}/View/Home.jsp">
            <div class="title">APOTHEKE Shop</div>
            </a>
             <div class="header-actions">
            <div class="search-bar">
              <input type="text" placeholder="Search medicines..." class="search-input" />
              <span class="material-symbols-outlined search-icon">search</span>
            </div>
      <!--  pop up -->
       
        <div class="header-actions">
                <c:if test="${empty sessionScope.account}">
                       <!-- login  -->
                        <div>
                            <button class="login-btn" id ="de" onclick="openForm()">Login</button>
                        </div>     
                </c:if> 
                <c:if test="${not empty sessionScope.account}">      
                        <c:if test="${sessionScope.account.role.roleID == 1}">
                                    <!--  user profile -->
                                    <div class="user-popup">
                                        <button class="user-btn">User</button>
                                        <div class="popup-content">
                                            <a href="${pageContext.request.contextPath}/View/pagecontrol/userprofile.jsp" class="popup-item">
                                            <span class="material-symbols-outlined">account_circle</span>
                                                Thông tin cá nhân
                                            </a>
                                            <a href="${pageContext.request.contextPath}/myorder" class="popup-item">
                                            <span class="material-symbols-outlined">inventory_2</span>
                                            Đơn hàng của tôi
                                            </a>
                                            <a href="#" class="popup-item">
                                            <span class="material-symbols-outlined">location_on</span>
                                            Sổ địa chỉ nhận hàng
                                            </a>
                                            <a href="#" class="popup-item">
                                            <span class="material-symbols-outlined">logout</span>
                                            Đăng xuất
                                            </a>
                                        </div>
                                    </div>
                            
                        </c:if>              
                 </c:if>
                
         </div>
        <!-- cart -->
        <div class="cart">
            <span class="material-symbols-outlined cart-icon">shopping_cart</span>
         
        <a href="${pageContext.request.contextPath}/View/pagecontrol/cart.jsp" style="text-decoration: none; color: inherit;">
            <span class="cart-text">Cart ${number}</span>
        </a>
            
        </div>
        <!-- logout  -->
         <c:if test="${not empty sessionScope.account}">      
                        <c:if test="${sessionScope.account.role.roleID == 1}">
                        <div>
                            <form action ="${pageContext.request.contextPath}/logout" method ="get">
                                <button class="logout-btn" type="submit">Logout</button> 
                                
                            </form>
                        </div>
                </c:if>              
        </c:if>
    </div>
          </header>
                        <!-- menu  -->
          <div>
            <nav class="navbar1">
              <ul class="nav-items1">
                  <li><a href="#">Pharmaceutical Cosmetics</a></li>
                  <li><a href="#">Functional food</a></li>
                  <li><a href="#">Medicine</a></li>
                  <li><a href="#">Pharmaceutical support</a></li>

              </ul>
          </nav>

         </div>

        </div>
        
               <!--  login -->
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
        
        
            
        
        
        

    </body>
    
    <script>
        
        function openForm(){
            document.getElementById("my-form").style.display = "block";
            document.getElementById("overlay").style.display ="";
            document.getElementById("my-form").classList.add("show");
            document.getElementById("register-de").style.display = "none";
        };
        function closeForm(){
            document.getElementById("my-form").style.display ="none";
            document.getElementById("register-de").style.display = "none";
        };
        
        function openForm3(){
            document.getElementById("my-form").style.display = "none";
            document.getElementById("overlay").style.display ="";
            document.getElementById("register-de").classList.add("show");
            document.getElementById("register-de").style.display = "block";
        };
        function closeForm3(){
            document.getElementById("register-de").style.display ="none";
            document.getElementById("my-form").style.display ="none";
        };
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
        };
       

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
            };
    
    </script>
</html>