<%-- 
    Document   : userprofile
    Created on : Nov 8, 2024, 1:16:29 AM
    Author     : Dell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/View/pagecontrol/userprofile.css">
        
    </head>
    <body>
    <jsp:include page="header.jsp"></jsp:include>
    <c:if test="${not empty sessionScope.account}">
        <div class="user-form">
            <h2>Change User Profile</h2>
            <form action="${pageContext.request.contextPath}/userprofile" method="post">
                <div class="form-group">
                    <label>Full name</label>
                    <input type="text" name="fullname" value="${sessionScope.account.fullname}" required >
                </div>
                <div class="form-group">
                    <label>Phone number</label>
                    <input type="tel" name="phone" value="${sessionScope.account.phone}" required>
                </div>
                <div class="form-group">
                    <label>Username</label>
                    <input type="email" name="username" value="${sessionScope.account.username}" disabled>
                </div>
                <div class="form-group">
                    <label>Gender</label>
                    <select name="gender" required>
                        <option value="" disabled ${empty sessionScope.account.gender ? 'selected' : ''}>Select gender</option>
                        <option value="male" ${sessionScope.account.gender == 'male' ? 'selected' : ''}>Male</option>
                        <option value="female" ${sessionScope.account.gender == 'female' ? 'selected' : ''}>Female</option>
                        <option value="other" ${sessionScope.account.gender == 'other' ? 'selected' : ''}>Other</option>
                    </select>
                </div>
                <div class="form-group">
                    <label>Address</label>
                    <input type="text" name="address" value="${sessionScope.account.address}">
                </div>
                <div>
                    <span style="color: red">${errorre}</span>
                </div>
                <div class="form-actions">
                    <button type="submit">Save</button>
                   <button type="button" onclick="openChangePasswordModal()">Change Password</button>
              </div>
            </form>
        </div>
                
         <div class="modal" id="changePasswordModal">
        <div class="modal-content">
            <span class="close" onclick="closeChangePasswordModal()">&times;</span>
            <h2>Change Password</h2>
            <form action="${pageContext.request.contextPath}/changePassword" method="post">
            <c:choose>
                <c:when test="${empty sessionScope.account.password}">
                    <!-- Only show New Password and Confirm Password fields if password is null -->
                    <div class="form-group">
                        <label>New Password</label>
                        <input type="password" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label>Confirm Password</label>
                        <input type="password" name="confirmPassword" required>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <label>Old Password</label>
                        <input type="password" name="oldPassword" required>
                    </div>
                    <div class="form-group">
                        <label>New Password</label>
                        <input type="password" name="newPassword" required>
                    </div>
                    <div class="form-group">
                        <label>Confirm Password</label>
                        <input type="password" name="confirmPassword" required>
                    </div>
                </c:otherwise>
            </c:choose>
            <div>
                <span style="color: red">${error}</span>
            </div>
            <div class="form-actions">
                <button type="submit">Submit</button>
            </div>
</form>

        </div>
    </div>        
                
    </c:if>
    
    <c:if test="${sessionScope.passwordChangeSuccess}">
        <script>
           
            alert("Password changed successfully!");
            document.getElementById("changePasswordModal").style.display = "none";
        </script>
        
        <c:remove var="passwordChangeSuccess" scope="session"/>
    </c:if>

   
    </body>
    <script>
    function openChangePasswordModal() {
        document.getElementById("changePasswordModal").style.display = "block";
    }

    function closeChangePasswordModal() {
        document.getElementById("changePasswordModal").style.display = "none";
    }

    // Close modal when clicking outside of it
    window.onclick = function(event) {
        var modal = document.getElementById("changePasswordModal");
        if (event.target == modal) {
            modal.style.display = "none";
        }
    };
      // Check if there is an error, and keep the modal open if true
    window.onload = function() {
        <% 
            String error = (String) request.getAttribute("error");
            if (error != null && !error.isEmpty()) { 
        %>
            openChangePasswordModal();
        <% 
            } 
        %>
                 <% if (request.getAttribute("updateSuccess") != null) { %>
            alert("<%= request.getAttribute("updateSuccess") %>");
        <% } %>

        // Check if there is an error message to show it in an alert
        <% if (request.getAttribute("errorre") != null) { %>
            alert("<%= request.getAttribute("errorre") %>");
        <% } %>
    };
    function validateForm() {
        const phone = document.getElementById('phone').value;
        const phonePattern = /^0\d{9}$/;  // Đảm bảo số điện thoại bắt đầu bằng 0 và có 10 chữ số
        if (!phonePattern.test(phone)) {
            alert('invalid phone number');
            return false;  // Ngừng gửi form
        }
        return true;  // Cho phép gửi form
    }
     // Hiển thị thông báo alert nếu có thông báo thành công
     
</script>
</html>
