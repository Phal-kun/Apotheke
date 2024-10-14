<%-- 
    Document   : forgotPassword
    Created on : Oct 13, 2024, 1:50:17 PM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <!-- Form nhập tài khoản/email ban đầu -->
        <form id="emailForm" method="get" action="${pageContext.request.contextPath}/forgotPass" >
            <c:if test="${showVerificationForm != '1' and showVerificationForm != 2}" >
                <h1> Nhập tài khoản</h1>
                <label for="username">Email:</label>
                <input type="text" id="username" name="username" required value="${email != null ? email : ''}" >
                <input type="submit" value="Tiếp" name="btAction">
                <div class="error-message" style="color: red">
                    ${messerror}
                </div>
            </c:if>
             <c:if test="${showVerificationForm == '1' }">
                 <h1>Xác minh bảo mật</h1>
                <label for="verificationCode">Mã xác nhận:</label>
                <input type="text" id="verificationCode" name="verificationCode" required value="${verycode != null ? verycode : ''}">
                <input type="submit" value="Xác nhận" name ="btAction">
                <div class="error-message" style="color: red">
                    ${errrocode}
                </div>           
            </c:if>   
            <c:if test="${showVerificationForm == '2' }"> 
                <h1>Thiết lập mật khẩu </h1>
                <label for="password">New Password: </label>
                <input type="password" id="password" name="password" required value="${passcode != null ? passcode : ''}" >
                <label for="password">Confirm Password: </label>
                <input type="confirmpassword" id="confirmPassword" name="confirmPassword" required value="${confirmcode != null ? confirmcode : ''}">
                <input type="submit" value="Hoàn thành" name ="btAction">
                <div class="error-message" style="color: red">
                    ${errorPassword}
                </div> 
            </c:if>
             <c:if test="${showVerificationForm == '2' }"> 
                
                <div >
                    ${mail}
                    ${codeSent} 
                </div> 
            </c:if>   
        </form>
        
        
    </body>
</html>

