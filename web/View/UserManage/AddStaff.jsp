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

    <form id="addStaffForm" action="CRUDUserAddStaff" method="post" onsubmit="return validateForm()">
        <h2>Add Staff</h2>
        
        <div>
            <label for="fullname">Full Name</label>
            <input type="text" id="fullname" name="fullname" required pattern="[A-Za-z\s]+" title="Please enter a valid name (letters and spaces only)">
        </div>
        
        <div>
            <label for="phone">Phone</label>
            <input type="text" id="phone" name="phone" required pattern="^\d{10}$" title="Please enter a 10-digit phone number">
        </div>
        
        <div>
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>Default Password is: 12345678</div>
        <div>
            <label for="gender">Gender</label>
            <select id="gender" name="gender" required>
                <option value="">Select Gender</option>
                <option value="male">Male</option>
                <option value="female">Female</option>
            </select>
        </div>

        <div>
            <label for="status">Status</label>
            <select id="status" name="status" required>
                <option value="true">Active</option>
                <option value="false">Inactive</option>
            </select>
        </div>
        
        <div>
            <label for="role">Role</label>
            <select id="role" name="role" required>
                <option value="2">Warehouse</option>
                <option value="3">Sale</option>
                <option value="4">Marketing</option>
            </select>
        </div>

        <div>
            <label for="address">Address</label>
            <input type="text" id="address" name="address" required>
        </div>

        <div>
            <button type="submit">Add Staff</button>
        </div>

        <div id="errorMessage" class="error"></div>
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
