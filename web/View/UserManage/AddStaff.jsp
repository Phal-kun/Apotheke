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
    <div class="container">
        <h1>Add Staff</h1>
        
        <div class="text-box">
            <form id="addStaffForm" action="CRUDUserAddStaff" method="post" onsubmit="return validateForm()">
                <div class="labels">
                    <label for="fullname">Full Name</label>
                </div>
                <div class="input-tab">
                    <input type="text" id="fullname" name="fullname" class="input-field" required pattern="[A-Za-z\s]+" title="Please enter a valid name (letters and spaces only)">
                </div>
                
                <div class="labels">
                    <label for="phone">Phone</label>
                </div>
                <div class="input-tab">
                    <input type="text" id="phone" name="phone" class="input-field" required pattern="^\d{10}$" title="Please enter a 10-digit phone number">
                </div>
                
                <div class="labels">
                    <label for="username">Username</label>
                </div>
                <div class="input-tab">
                    <input type="text" id="username" name="username" class="input-field" required>
                </div>
                
                <div>
                    <p>Default Password is: 12345678</p>
                </div>
                
                <div class="labels">
                    <label for="gender">Gender</label>
                </div>
                <div class="input-tab">
                    <select id="gender" name="gender" class="input-field" required>
                        <option value="">Select Gender</option>
                        <option value="male">Male</option>
                        <option value="female">Female</option>
                    </select>
                </div>

                <div class="labels">
                    <label for="status">Status</label>
                </div>
                <div class="input-tab">
                    <select id="status" name="status" class="input-field" required>
                        <option value="true">Active</option>
                        <option value="false">Inactive</option>
                    </select>
                </div>

                <div class="labels">
                    <label for="role">Role</label>
                </div>
                <div class="input-tab">
                    <select id="role" name="role" class="input-field" required>
                        <option value="2">Warehouse</option>
                        <option value="3">Sale</option>
                        <option value="4">Marketing</option>
                    </select>
                </div>

                <div class="labels">
                    <label for="address">Address</label>
                </div>
                <div class="input-tab">
                    <input type="text" id="address" name="address" class="input-field" required>
                </div>

                <div class="btn">
                    <button type="submit">Add Staff</button>
                    <button type="button" onclick="window.location.href='CRUDUserList'">Back to List</button>
                </div>

                <div id="errorMessage" class="error"></div>
            </form>
        </div>
    </div>

    <footer>
        <p>&copy; 2024 Apotheke. All rights reserved.</p>
    </footer>

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

            const namePattern = /^[A-Za-z\s]+$/;
            if (!namePattern.test(fullname)) {
                showError("Full Name must contain only letters and spaces.");
                return false;
            }

            const phonePattern = /^\d{10}$/;
            if (!phonePattern.test(phone)) {
                showError("Please enter a valid 10-digit phone number.");
                return false;
            }

            if (username.length < 3) {
                showError("Username must be at least 3 characters long.");
                return false;
            }

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
