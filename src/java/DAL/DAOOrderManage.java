/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Order.Order;
import Model.Order.OrderDetail;
import Model.Order.Status;
import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import Model.User.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class DAOOrderManage {
    public static DAOOrderManage INSTANCE = new DAOOrderManage();
    private Connection con;
    private String status = "OK";

    private DAOOrderManage() {
        if (INSTANCE == null) {
            con = new DBContext().connect;
        } else {
            INSTANCE = this;
        }
    }

    public void loadDB() {
        System.out.println("Loading data...");
        String sql = "select * from [Role]";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int roleid = rs.getInt("roleid");
                    String rolename = rs.getString("rolename");
                    int permision = rs.getInt("permission");
                    System.out.println(roleid + " - " + rolename + " - " + permision);
                }
            }
        } catch (SQLException e) {
            status = "Error at read Account " + e.getMessage();
            System.out.println(status);
        }
    }
    
    private ArrayList orderToList(ResultSet rs){
        ArrayList<Order> orderList = new ArrayList<Order>();
        try{
            while(rs.next()){
                Status status = new Status();
                status.setStatusID(rs.getInt("statusID"));
                status.setStatusName(rs.getString("statusName"));
                status.setDescription(rs.getString("description"));

                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setFullname(rs.getString("fullname"));
                user.setUsername(rs.getString("username")); 
                user.setPhone(rs.getString("phone"));
                user.setGender(rs.getString("gender"));
                user.setAddress(rs.getString("address"));

                Order order = new Order();
                order.setOrderID(rs.getInt("orderID"));
                order.setUser(user);
                order.setOrderDate(rs.getDate("orderDate"));
                order.setOrderCompleted(rs.getDate("orderCompleted"));
                order.setOrderDetail(new ArrayList<OrderDetail>());
                order.setStatus(status);
                order.setTotalPrice(rs.getDouble("totalPrice"));
                order.setShipName(rs.getString("shipName"));
                order.setShipAddress(rs.getString("shipAddress"));
                order.setShipPhone(rs.getString("shipPhone"));
                order.setShipNote(rs.getString("shipNote"));
                order.setRejectReason(rs.getString("rejectReason"));
                
                orderList.add(order);
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return orderList;
    }
    
    private Order insertDetail(ResultSet rs, Order order){
        try{
            ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
            while(rs.next()){
                
                Product product = new Product();
                
                
                ProductDetail productDetail = new ProductDetail();
                
                ProductUnit productUnit = new ProductUnit();
                
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailID(rs.getInt("orderDetailID"));
                orderDetail.setProduct(product);
                orderDetail.setProductDetail(productDetail);
                orderDetail.setUnit(productUnit);
                orderDetail.setSoldPrice(rs.getDouble("soldPrice"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setTotalProductPrice(rs.getDouble("totalProductPrice"));
                
                orderDetailList.add(orderDetail);
                
            }
            
            order.setOrderDetail(orderDetailList);
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }             
        return order;
    }
    
    public static void main(String[] args) {
        INSTANCE.loadDB();
    }
}
