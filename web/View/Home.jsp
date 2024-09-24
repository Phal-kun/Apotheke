<%-- 
    Document   : DefaultJSP
    Created on : Sep 10, 2024, 10:42:16 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/csss/loginform.css">
        
    </head>
    
    <body class="overlay" id="overlay">
               
        <button class ="open-button" id ="de" onclick="openForm()"> login  </button>

        <div class="form-login" id="my-form">
            <span id="close">
                <img  src="${pageContext.request.contextPath}/View/imagine/formLogin/x.png" onclick="closeForm()"> 
            </span>
            <form action = "${pageContext.request.contextPath}/loginuser" method="get">
                <h1 class="titds"> Login</h1>
                <div class="ds">Vui lòng đăng nhập để hưởng những đặc<br> quyền dành cho thành viên.</div>
        
                <input class="user" type="text" id="username" name ="username"placeholder="Enter phone number or email " ><br>
                <span class="user0-icon ">
                    <img src="${pageContext.request.contextPath}/View/imagine/formLogin/user.png">
                </span>
                    <div class="password-wrapper">
                    <input class="pass" type="password" id="password"name="password" placeholder="Enter your password">                   
                </div>
                <span class="pass-icon">
                    <img src="${pageContext.request.contextPath}/View/imagine/formLogin/pass1.png">
                </span>
                <span class="eye-icon" onclick="togglePassword()">
                    <img src="${pageContext.request.contextPath}/View/imagine/formLogin/hide.png" id="toggleEye" alt="Show/Hide Password" />
                </span> 

                <span class="forgot-password">
                    
                    <a href="register.jsp" class="link2">Register</a>
                    <a href="forgot-password.jsp" class="link1">Forgot Password?</a>
                </span>
                <button type="submit" class ="submitds" >Login Now </button><br>
               
                <c:if test="${not empty mess}">
                    <div class="error-message">
                        ${mess}
                    </div>
                </c:if>

                <h3 class="login-separator">
                    <span>or login with google</span>
                </h3>
                
            
            </form>
             <button class="gsi-material-button" onclick="window.location.href='https://accounts.google.com/o/oauth2/auth?scope=https://www.googleapis.com/auth/userinfo.email https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/user.birthday.read https://www.googleapis.com/auth/user.addresses.read https://www.googleapis.com/auth/user.phonenumbers.read https://www.googleapis.com/auth/user.gender.read&access_type=online&redirect_uri=http://localhost:8080/Apotheke/loginGoogle&response_type=code&client_id=774397983057-vbou6cr3kmos51u2c552btnlt4l0fh5t.apps.googleusercontent.com'">
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
               
                
                
                
                
    </body>
    
         <script>
        function openForm(){
            document.getElementById("my-form").style.display = "block";

        }
        function closeForm(){
            document.getElementById("my-form").style.display ="none";

        }
        function togglePassword() {
            var passwordInput = document.getElementById("password");
            var eyeIcon = document.getElementById("toggleEye");

            if (passwordInput.type === "password") {
                passwordInput.type = "text";
                eyeIcon.src = "${pageContext.request.contextPath}/View/imagine/formLogin/hide.png"; // Thay đổi biểu tượng thành "mắt đóng"
            } else {
                passwordInput.type = "password";
                eyeIcon.src = "${pageContext.request.contextPath}/View/imagine/formLogin/view.png"; // Thay đổi biểu tượng thành "mắt mở"
            }
        }
        
        window.onload = function() {
            var mess = '${mess}';
            if (mess && mess !== "") {
                openForm();  // Mở form đăng nhập nếu có thông báo lỗi
            }
        }
        </script>
</html>
