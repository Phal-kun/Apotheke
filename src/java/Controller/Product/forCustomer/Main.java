/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import DAL.OrderDao;
import DAL.UserDao;
import Model.Order.Order;
import Model.Order.Status;
import Model.Product.Component;
import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import Model.User.User;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.List;
import java.sql.Date;

/**
 *
 * @author Dell
 */

public class Main {
    public static void main(String[] args) throws Exception {
            int userID =11; // Thay 1 bằng một user ID có sẵn trong cơ sở dữ liệu của bạn

            // Khởi tạo đối tượng chứa phương thức getOrderFromUserId
            OrderDao orderDAO = new OrderDao();
            
            // Gọi phương thức để lấy danh sách đơn hàng
            List<Order> orders = orderDAO.getOrderFromUserId(userID);

            // Kiểm tra và in kết quả
            if (orders != null && !orders.isEmpty()) {
                for (Order order : orders) {
                   System.out.println(order.toString()); 
                }
            }
    }  
        
}
