<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Add Staff</title>
        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <link rel="stylesheet" href="/Apotheke/View/UserManage/addstaff.css">
    </head>
    <body>

        <form id="addStaffForm" action="${pageContext.request.contextPath}/CRUDUserEditStaff" method="post" onsubmit="return validateForm()">
            <h2>Edit Staff</h2>
            
            <input type="hidden" name="userID" value="${user.userID}"/>
            
            <div>
                <label for="fullname">Full Name</label>
                <input type="text" id="fullname" name="fullname" required pattern="[A-Za-z\s]+" 
                       value="${user.fullname}" title="Please enter a valid name (letters and spaces only)">
            </div>

            <div>
                <label for="phone">Phone</label>
                <input type="text" id="phone" name="phone" required pattern="^\d{10}$" 
                       value="${user.phone}" title="Please enter a 10-digit phone number">
            </div>

            <div>
                <label for="username">Username</label>
                <input type="text" id="username" name="username" required 
                       value="${user.username}">
            </div>

            <input type="hidden" name="password" value="${user.password}"/>

            <div>
                <label for="gender">Gender</label>
                <select id="gender" name="gender" required>
                    <option value="male" ${user.gender == 'male' ? 'selected' : ''}>Male</option>
                    <option value="female" ${user.gender == 'female' ? 'selected' : ''}>Female</option>
                </select>
            </div>

            <div>
                <label for="status">Status</label>
                <select id="status" name="status" required>
                    <option value="true" ${user.status ? 'selected' : ''}>Active</option>
                    <option value="false" ${!user.status ? 'selected' : ''}>Inactive</option>
                </select>
            </div>

            <div>
                <label for="role">Role</label>
                <select id="role" name="role" required>
                    <option value="2" ${user.role.roleID == 2 ? 'selected' : ''}>Warehouse</option>
                    <option value="3" ${user.role.roleID == 3 ? 'selected' : ''}>Sale</option>
                    <option value="4" ${user.role.roleID == 4 ? 'selected' : ''}>Marketing</option>
                </select>
            </div>

            <div>
                <label for="address">Address</label>
                <input type="text" id="address" name="address" required 
                       value="${user.address}">
            </div>

            <div>
                <button type="submit">Update Staff</button>
                <button type="button" onclick="window.location.href='CRUDUserList'">Back to List</button>
            </div>

            <div id="errorMessage" class="error"></div>
        </form>

        <form action="${pageContext.request.contextPath}/CRUDUserResetPassword" method="get">
            <input type="hidden" name="userID" value="${user.userID}"/>
            <input type="submit" value="Reset Password"/>
        </form>
            
        
        <script>
            function validateForm() {
                const form = document.getElementById('addStaffForm');
                const fullname = form.fullname.value.trim();
                const phone = form.phone.value.trim();
                const username = form.username.value.trim();
                const gender = form.gender.value;
                const status = form.status.value;
                const role = form.role.value;
                const address = form.address.value.trim();

                // Fullname validation
                const namePattern = /^[A-Za-z\s]+$/;
                if (!namePattern.test(fullname)) {
                    showError("Full Name must contain only letters and spaces.");
                    return false;
                }

                // Phone number validation (10 digits)
                const phonePattern = /^\d{10}$/;
                if (!phonePattern.test(phone)) {
                    showError("Please enter a valid 10-digit phone number.");
                    return false;
                }

                // Username validation (basic check)
                if (username.length < 3) {
                    showError("Username must be at least 3 characters long.");
                    return false;
                }

                // Gender, Status, Role validation (already handled by 'required' attribute)

                // Address validation (basic non-empty check)
                if (address.length === 0) {
                    showError("Address is required.");
                    return false;
                }

                return true;
            }

            function showError(message) {
                const errorMessage = document.getElementById('errorMessage');
                errorMessage.textContent = message;
            }
        </script>

    </body>
</html>
