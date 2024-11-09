/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Order.Order;
import Model.Order.OrderDetail;
import Model.Order.Status;
import Model.User.User;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

import java.util.List;
import java.sql.Types; 


/**
 *
 * @author Dell
 */
public class OrderDao extends DBContext {
     java.sql.Connection con= null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        public int  insertOrder(Order order) throws SQLException , Exception {
    String sql = "INSERT INTO [dbo].[order] (userID, orderDate, orderCompleted, statusID, totalPrice, " +
                 "shipName, shipAddress, shipPhone, shipNote, rejectReason) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


    try {
        // Kết nối cơ sở dữ liệu
        con = getConnection();
        
        // Chuẩn bị câu lệnh SQL
        //ps = con.prepareStatement(sql);
        ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        // Set các giá trị vào PreparedStatement
        ps.setInt(1, order.getUser().getUserID());  // userID
        ps.setDate(2, order.getOrderDate());  // orderDate
        ps.setDate(3, null);  // orderCompleted
        ps.setInt(4, order.getStatus().getStatusID());  // statusID
        BigDecimal totalPrice = BigDecimal.valueOf(order.getTotalPrice());
        ps.setBigDecimal(5, totalPrice);  // totalPrice
        ps.setString(6, order.getShipName());  // shipName
        ps.setString(7, order.getShipAddress());  // shipAddress
        ps.setString(8, order.getShipPhone());  // shipPhone
        ps.setString(9, order.getShipNote());  // shipNote
        ps.setString(10, order.getRejectReason());  // rejectReason

        // Thực thi câu lệnh
        int rowsAffected = ps.executeUpdate();

        if (rowsAffected > 0) {
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int orderID = generatedKeys.getInt(1);  // Lấy orderID, nó là khóa chính của bảng order
                System.out.println("Order inserted successfully with OrderID: " + orderID);
                return orderID;  
            }
        } else {
            System.out.println("No rows affected. Order insert failed.");
        }
        
    } catch (SQLException e) {
        // Đảm bảo các lỗi được ném ra ngoài
        throw e;
    } finally {
        // Đảm bảo đóng kết nối và PreparedStatement
        closePreparedStatement(ps);
        closeConnection(con);
    }
     return -1;
}

    public void insertOrderDetail(int orderID, int productID, int productDetailID, int unitID, BigDecimal soldPrice, int quantity) throws Exception {
        String sql = "INSERT INTO [dbo].[orderDetail] " +
                     "([orderID], [productID], [productDetailID], [unitID], [soldPrice], [quantity]) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            con = getConnection();
        
            // Chuẩn bị câu lệnh SQL
            ps = con.prepareStatement(sql);
            ps.setInt(1, orderID);
            ps.setInt(2, productID);
            ps.setNull(3, Types.INTEGER); 
            ps.setInt(4, unitID);
            ps.setBigDecimal(5, soldPrice);
            ps.setInt(6, quantity);
           
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Insert into orderDetail successful.");
            } else {
                System.out.println("Insert into orderDetail failed.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error while inserting into orderDetail.");
        }
    }
    public int getUnitIdByProductIdAndProductName(int productId, String productName) throws SQLException, Exception {
        String sql = "SELECT unitID FROM [dbo].[productUnit] WHERE productID = ? AND unitName = ?";

        try {
            // Kết nối cơ sở dữ liệu
            con = getConnection(); // Đảm bảo rằng bạn có phương thức để kết nối với cơ sở dữ liệu

            // Chuẩn bị câu lệnh SQL
            ps = con.prepareStatement(sql);

            // Set các tham số vào PreparedStatement
            ps.setInt(1, productId);   // productID
            ps.setString(2, productName); // unitName

            // Thực thi câu lệnh
            rs = ps.executeQuery();

            // Kiểm tra kết quả và trả về unitID nếu tìm thấy
            if (rs.next()) {
                return rs.getInt("unitID"); // Trả về unitID từ kết quả
            } else {
                // Nếu không tìm thấy kết quả
                System.out.println("No unit found for the given productID and productName.");
                return -1; // Trả về -1 nếu không có kết quả
            }
        } catch (SQLException e) {
            // Log và ném lỗi nếu có sự cố với cơ sở dữ liệu
            e.printStackTrace();
            throw e;
        } finally {
            // Đảm bảo đóng kết nối và PreparedStatement
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
    }    
    
    
    public int getProductDetailIdByUnitId(int unitId) throws SQLException, Exception {
    String sql = "SELECT productDetailID FROM [dbo].[productDetail] WHERE unitID = ?";

    try {
        // Kết nối cơ sở dữ liệu
        con = getConnection(); // Đảm bảo rằng bạn có phương thức để kết nối với cơ sở dữ liệu

        // Chuẩn bị câu lệnh SQL
        ps = con.prepareStatement(sql);

        // Set tham số vào PreparedStatement
        ps.setInt(1, unitId);   // unitID

        // Thực thi câu lệnh
        rs = ps.executeQuery();

        // Kiểm tra kết quả và trả về productDetailID nếu tìm thấy
        if (rs.next()) {
            return rs.getInt("productDetailID"); // Trả về productDetailID từ kết quả
        } else {
            // Nếu không tìm thấy kết quả
            System.out.println("No product detail found for the given unitID.");
            return -1; // Trả về -1 nếu không có kết quả
        }
    } catch (SQLException e) {
        // Log và ném lỗi nếu có sự cố với cơ sở dữ liệu
        e.printStackTrace();
        throw e;
    } finally {
        // Đảm bảo đóng kết nối và PreparedStatement
        closeResultSet(rs);
        closePreparedStatement(ps);
        closeConnection(con);
    }
}

    
    public List<Order> getOrderFromUserId(int userID) throws Exception {
    String sql = "SELECT * FROM [dbo].[order] WHERE [userID] = ?";
    List<Order> orders = new ArrayList<>();

    try {
        con = getConnection();

        // Chuẩn bị câu lệnh SQL
        ps = con.prepareStatement(sql);
        ps.setInt(1, userID);

        rs = ps.executeQuery();

        while (rs.next()) {
            // Lấy thông tin từ ResultSet
            int orderID = rs.getInt("orderID");
            Date orderDate = rs.getDate("orderDate");
            Date orderCompleted = rs.getDate("orderCompleted");
            double totalPrice = rs.getDouble("totalPrice");
            String shipName = rs.getString("shipName");
            String shipAddress = rs.getString("shipAddress");
            String shipPhone = rs.getString("shipPhone");
            String shipNote = rs.getString("shipNote");
            String rejectReason = rs.getString("rejectReason");
            // Lấy đối tượng User dựa vào userID
            User user = new User(); 
            user.setUserID(userID);
            // Giả sử bạn có hàm getUserById để lấy thông tin người dùng
            // Lấy trạng thái đơn hàng từ statusID
            int statusID = rs.getInt("statusID");
            Status status = new Status(statusID,"","");  // Giả sử có hàm getStatusById để lấy đối tượng Status
            
            // Lấy danh sách chi tiết đơn hàng cho orderID
               ArrayList<OrderDetail> orderDetail = null;
            // Tạo đối tượng Order
           Order order = new Order(orderID, user, orderDate, orderCompleted, orderDetail, status, totalPrice, 
                                    shipName, shipAddress, shipPhone, shipNote, rejectReason);
           orders.add(order);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error while retrieving orders by userID.");
    } finally {
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    return orders;
}
        
        public boolean updateOrderByOrderID(int orderID, int statusID) throws SQLException, Exception {
    String sql = "UPDATE [dbo].[order] SET [statusID] = ? WHERE [orderID] = ?";
    boolean isUpdated = false;

    try {
        con = getConnection();  // Tạo kết nối với cơ sở dữ liệu

        // Chuẩn bị câu lệnh SQL
        ps = con.prepareStatement(sql);
        ps.setInt(1, statusID);  // Cập nhật trạng thái (statusID) của đơn hàng
        ps.setInt(2, orderID);   // Cập nhật đơn hàng với orderID tương ứng

        // Thực thi câu lệnh SQL
        int rowsAffected = ps.executeUpdate();

        // Nếu có ít nhất một dòng bị ảnh hưởng, trả về true
        if (rowsAffected > 0) {
            isUpdated = true;
        }

    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("Error while updating order status.");
    } finally {
        // Đảm bảo đóng tài nguyên sau khi sử dụng
        if (ps != null) ps.close();
        if (con != null) con.close();
    }

    return isUpdated;
}

}
