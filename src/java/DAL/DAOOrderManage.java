/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Order.Order;
import Model.Order.OrderDetail;
import Model.Order.Status;
import Model.Product.Product;
import Model.Product.ProductUnit;
import Model.User.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public ArrayList<Order> getOrder(int page, boolean sort, String sortCol, String keyword, int statusID) {
        try {
            String sql = """
        SELECT 
            *
        FROM [order] o
            JOIN [user] u ON o.userID = u.userID
            JOIN orderStatus s ON o.statusID = s.statusID
        WHERE
            %s
        ORDER BY
            %s
        OFFSET ? ROWS
        FETCH NEXT 10 ROWS ONLY;
        """;

            // Set up the base WHERE clause
            StringBuilder condition = new StringBuilder("1=1");  // Base condition that always evaluates to true (to simplify appending)

            // Add search condition for keyword
            if (keyword != null && !keyword.isEmpty()) {
                condition.append(" AND (CAST(o.orderID AS NVARCHAR) LIKE ? OR u.fullname LIKE ? OR u.address LIKE ? OR CAST(o.totalPrice AS NVARCHAR) LIKE ?)");
            }

            // Add filter for statusID (if it's greater than 0)
            if (statusID > 0) {
                condition.append(" AND o.statusID = ?");
            }

            // Set up sort order
            String order = sortCol + (sort ? " ASC" : " DESC");

            // Format the SQL string
            sql = String.format(sql, condition.toString(), order);

            PreparedStatement statement = con.prepareStatement(sql);

            // Set the search parameters if keyword exists
            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String keywordPattern = "%" + keyword + "%";
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
            }

            // Set the statusID parameter if it's greater than 0
            if (statusID > 0) {
                statement.setInt(paramIndex++, statusID);
            }

            // Set pagination offset
            statement.setInt(paramIndex, (page - 1) * 10);

            // Execute the query and return the result list
            ResultSet rs = statement.executeQuery();
            return orderToList(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalPages(String keyword, int statusID) {
        try {
            String countSql = """
        SELECT COUNT(*)
        FROM [order] o
            JOIN [user] u ON o.userID = u.userID
            JOIN orderStatus s ON o.statusID = s.statusID
        WHERE %s
        """;

            // Start with a base condition that always evaluates to true (for easy appending)
            StringBuilder condition = new StringBuilder("1=1");

            // Add search condition if a keyword is provided
            if (keyword != null && !keyword.isEmpty()) {
                condition.append(" AND (CAST(o.orderID AS NVARCHAR) LIKE ? OR u.fullname LIKE ? OR u.address LIKE ? OR CAST(o.totalPrice AS NVARCHAR) LIKE ?)");
            }

            // Add filter for statusID (if it's greater than 0)
            if (statusID > 0) {
                condition.append(" AND o.statusID = ?");
            }

            // Format the SQL query with the dynamic conditions
            countSql = String.format(countSql, condition.toString());

            PreparedStatement statement = con.prepareStatement(countSql);

            // Set the search parameters for the keyword if it exists
            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String keywordPattern = "%" + keyword + "%";
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
            }

            // Set the statusID parameter if it's greater than 0
            if (statusID > 0) {
                statement.setInt(paramIndex++, statusID);
            }

            // Execute the query to get the total record count
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int totalRecords = rs.getInt(1); // Get the total count of orders
                int recordsPerPage = 10; // Assuming 10 records per page
                return (int) Math.ceil(totalRecords / (double) recordsPerPage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0; // Default return if an exception occurs or no results are found
    }

    public ArrayList<Status> getStatus() {
        ArrayList<Status> statusList = new ArrayList<>();
        try {
            String countSql = """
        SELECT * FROM orderStatus
        """;

            PreparedStatement statement = con.prepareStatement(countSql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                int statusID = rs.getInt("statusID");
                String statusName = rs.getString("statusName");
                String description = rs.getString("description");

                Status status = new Status(statusID, statusName, description);
                statusList.add(status);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statusList;
    }

    public Order getOrderDetail(int orderID) {
        Order order = null;
        try {
            String sql = """
                     SELECT o.orderID, o.orderDate, o.orderCompleted, o.totalPrice, 
                            o.shipName, o.shipAddress, o.shipPhone, o.shipNote, o.rejectReason, 
                            u.userID, u.fullname, u.username, u.phone, u.gender, u.address,
                            s.statusID, s.statusName, s.description
                     FROM [order] o
                     JOIN [user] u ON o.userID = u.userID
                     JOIN orderStatus s ON o.statusID = s.statusID
                     WHERE o.orderID = ?
                     """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderID); // Set the orderID parameter
            ResultSet rs = statement.executeQuery();

            // Convert the result set to an Order object
            order = orderToSingleOrder(rs);

            // Fetch the order details and add them to the order
            if (order != null) {
                insertOrderDetail(order);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order;
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
   
    private Order orderToSingleOrder(ResultSet rs) {
        Order order = null;
        try {
            if (rs.next()) { 
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

                order = new Order(); // Create an Order instance
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
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order; // Return the single order
    }
 
    private void insertOrderDetail(Order order) {
        try {
            // SQL query to select the order details for the given order
            String sql = """
             SELECT *
             FROM orderDetail od
             JOIN product p ON od.productID = p.productID
             JOIN productUnit pu ON od.unitID = pu.unitID
             WHERE od.orderID = ?
             """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, order.getOrderID()); // Set the order ID in the query
            ResultSet rs = statement.executeQuery();

            // Populating the order with its details
            insertDetail(rs, order);

        } catch (SQLException e) {
            System.out.println("Error inserting order detail: " + e.getMessage());
        }
    }

    private Order insertDetail(ResultSet rs, Order order){
        try{
            ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
            while(rs.next()){
                
                Product product = new Product();
                product.setProductName(rs.getString("productName"));
                product.setManufacturer(rs.getString("manufacturer"));
                
                
                ProductUnit productUnit = new ProductUnit();
                productUnit.setProductUnitName(rs.getString("unitName"));
                
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderDetailID(rs.getInt("orderDetailID"));
                orderDetail.setProduct(product);
                orderDetail.setUnit(productUnit);
                orderDetail.setSoldPrice(rs.getDouble("soldPrice"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setTotalProductPrice(rs.getDouble("totalProductPrice"));
                
                orderDetailList.add(orderDetail);
                
            }
            
            order.setOrderDetail(orderDetailList);
            
        } catch(SQLException e){
            e.printStackTrace();
        }             
        return order;
    }
    
    public void approveOrder(int orderID){
        try {
            // SQL query to select the order details for the given order
            String sql = """
             UPDATE [order]
             SET statusID = 2
             WHERE orderID = ?
             """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderID); // Set the order ID in the query
            statement.executeUpdate();

            // Populating the order with its details

        } catch (SQLException e) {
            System.out.println("Error inserting order detail: " + e.getMessage());
        }
    }
    
    public void rejectOrder(int orderID, String rejectReason){
        try {
            // SQL query to select the order details for the given order
            String sql = """
             UPDATE [order]
             SET statusID = 4, rejectReason = ?
             WHERE orderID = ?
             """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, rejectReason);
            statement.setInt(2, orderID);            
            statement.executeUpdate();


        } catch (SQLException e) {
            System.out.println("Error inserting order detail: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        INSTANCE.loadDB();
//        ArrayList list = INSTANCE.getOrder(1, false, "orderDate", "", 0);
//        for (Object object : list) {
//            System.out.println(object);
//        }
        INSTANCE.rejectOrder(1, "Unable to ship");
        
        Order order = new Order();
        order.setOrderID(48);
        INSTANCE.insertOrderDetail(order);
        System.out.println(order.getOrderDetail());
    }
    
    
}
