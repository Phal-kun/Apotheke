/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

document.addEventListener('DOMContentLoaded', function() {
    const plusButtons = document.querySelectorAll('.plus');
    const minusButtons = document.querySelectorAll('.minus');
    const selectAllCheckbox = document.getElementById('select-all');
    const selectItemCheckboxes = document.querySelectorAll('.select-item');
    const selectedCountSpan = document.getElementById('selected-count');

    // Tăng/giảm số lượng
    plusButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            let quantityInput = this.previousElementSibling;
            let currentValue = parseInt(quantityInput.value);
            if (!isNaN(currentValue)) {
                quantityInput.value = currentValue + 1;
            }
        });
    });

    minusButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            let quantityInput = this.nextElementSibling;
            let currentValue = parseInt(quantityInput.value);
            if (!isNaN(currentValue) && currentValue > 1) {
                quantityInput.value = currentValue - 1;
            }
        });
    });

    // Chức năng chọn tất cả hoặc bỏ chọn tất cả
    selectAllCheckbox.addEventListener('change', function() {
        const isChecked = selectAllCheckbox.checked;
        selectItemCheckboxes.forEach(function(checkbox) {
            checkbox.checked = isChecked;
        });
        updateSelectCount();
    });

    // Cập nhật số lượng các mục được chọn
    selectItemCheckboxes.forEach(function(checkbox) {
        checkbox.addEventListener('change', function() {
            updateSelectCount();
        });
    });

    // Hàm cập nhật số lượng được chọn trong phần "Chọn tất cả"
    function updateSelectCount() {
        let selectedCount = 0;
        selectItemCheckboxes.forEach(function(checkbox) {
            if (checkbox.checked) {
                selectedCount++;
            }
        });
        selectedCountSpan.textContent = selectedCount;
        // Nếu tất cả được chọn, thì chọn luôn "Chọn tất cả"
        selectAllCheckbox.checked = (selectedCount === selectItemCheckboxes.length);
    }

    // Gọi lần đầu để cập nhật số lượng ban đầu
    updateSelectCount();
});

