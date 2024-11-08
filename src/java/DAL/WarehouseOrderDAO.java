/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Order.Order;
import Model.Order.OrderDetail;
import Model.Order.Status;
import Model.Product.Category;
import Model.Product.Origin;
import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import Model.User.User;
import java.sql.CallableStatement;
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
public class WarehouseOrderDAO {

    public static WarehouseOrderDAO INSTANCE = new WarehouseOrderDAO();
    private Connection con;
    private String status = "OK";

    private final int pendingStatus = 1;
    private final int processingStatus = 2;
    private final int deliveringStatus = 3;
    private final int rejectedStatus = 4;
    private final int completedStatus = 5;

    private WarehouseOrderDAO() {
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
            StringBuilder condition = new StringBuilder("(o.statusID = 2 OR o.statusID = 3 OR o.statusID = 5)");  // Base condition that always evaluates to true (to simplify appending)

            // Add search condition for keyword
            if (keyword != null && !keyword.isEmpty()) {
                condition.append(" AND (CAST(o.orderID AS NVARCHAR) LIKE ? OR u.fullname LIKE ? OR u.address LIKE ? OR CAST(o.totalPrice AS NVARCHAR) LIKE ?)");
            }

            if (statusID == processingStatus || statusID == deliveringStatus || statusID == completedStatus) {
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
            if (statusID == processingStatus || statusID == deliveringStatus || statusID == completedStatus) {
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
            StringBuilder condition = new StringBuilder("(o.statusID = 2 OR o.statusID = 3 OR o.statusID = 5)");

            // Add search condition if a keyword is provided
            if (keyword != null && !keyword.isEmpty()) {
                condition.append(" AND (CAST(o.orderID AS NVARCHAR) LIKE ? OR u.fullname LIKE ? OR u.address LIKE ? OR CAST(o.totalPrice AS NVARCHAR) LIKE ?)");
            }

            // Add filter for statusID (if it's greater than 0)
            if (statusID == processingStatus || statusID == deliveringStatus || statusID == completedStatus) {
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
            if (statusID == processingStatus || statusID == deliveringStatus || statusID == completedStatus) {
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

    public Order getOrder(int orderID) {
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

    private ArrayList orderToList(ResultSet rs) {
        ArrayList<Order> orderList = new ArrayList<Order>();
        try {
            while (rs.next()) {
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
        } catch (SQLException e) {
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
             JOIN productDetail pd ON od.productDetailID = pd.productDetailID
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

    private Order insertDetail(ResultSet rs, Order order) {
        try {
            ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
            while (rs.next()) {

                Product product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setProductName(rs.getString("productName"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setComponentDescription(rs.getString("componentDescription"));
                product.setDescription(rs.getString("description"));
                product.setIsActive(rs.getBoolean("isActive"));

                ProductDetail productDetail = new ProductDetail();
                productDetail.setProductDetailID(rs.getInt("productDetailID"));
                productDetail.setBatchNo(rs.getInt("batchNo"));

                ProductUnit productUnit = new ProductUnit();
                productUnit.setProductUnitID(rs.getInt("unitID"));
                productUnit.setProductUnitName(rs.getString("unitName"));
                

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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return order;
    }

//    public void approveOrder(int orderID){
//        try {
//            // SQL query to select the order details for the given order
//            String sql = """
//             UPDATE [order]
//             SET statusID = 2
//             WHERE orderID = ?
//             """;
//
//            PreparedStatement statement = con.prepareStatement(sql);
//            statement.setInt(1, orderID); // Set the order ID in the query
//            statement.executeUpdate();
//
//            // Populating the order with its details
//
//        } catch (SQLException e) {
//            System.out.println("Error inserting order detail: " + e.getMessage());
//        }
//    }
    public void rejectOrder(Order order) {
        try {
            changeToRejectStatus(order.getOrderID(), order.getRejectReason());

            if (order.getStatus().getStatusID() == deliveringStatus) {
                CallableStatement statement = null;

                try {
                    // Start a transaction
                    con.setAutoCommit(false);

                    // Loop through order details and reduce stock for each
                    String sql = "{CALL IncreaseStockBasedOnOrder(?)}";

                    for (OrderDetail orderDetail : order.getOrderDetail()) {
                        statement = con.prepareCall(sql);
                        statement.setInt(1, orderDetail.getOrderDetailID());
                        statement.execute();
                        System.out.println("Stock increase successfully for orderDetailID: " + orderDetail.getOrderDetailID());
                        statement.close(); // Close statement after each iteration
                    }

                    // Commit transaction if everything went well
                    con.commit();
                } catch (SQLException e) {
                    // Rollback transaction in case of error
                    try {
                        if (con != null) {
                            con.rollback();
                        }
                        System.out.println("Transaction rolled back due to: " + e.getMessage());
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }
                } finally {
                    // Clean up resources
                    try {
                        if (statement != null) {
                            statement.close();
                        }
                        con.setAutoCommit(true);
                    } catch (SQLException closeEx) {
                        closeEx.printStackTrace();
                    }
                }
            } 
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void changeToRejectStatus(int orderID, String rejectReason) {
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

    public void deliverOrder(Order order) {
        CallableStatement statement = null;

        try {
            // Start a transaction
            con.setAutoCommit(false);

            // Change order status to 'Delivered'
            changeToDeliverStatus(order.getOrderID());

            // Loop through order details and reduce stock for each
            String sql = "{CALL ReduceStockBasedOnOrder(?)}";

            for (OrderDetail orderDetail : order.getOrderDetail()) {
                statement = con.prepareCall(sql);
                statement.setInt(1, orderDetail.getOrderDetailID());
                statement.execute();
                System.out.println("Stock reduced successfully for orderDetailID: " + orderDetail.getOrderDetailID());
                statement.close(); // Close statement after each iteration
            }

            // Commit transaction if everything went well
            con.commit();
        } catch (SQLException e) {
            // Rollback transaction in case of error
            try {
                if (con != null) {
                    con.rollback();
                }
                System.out.println("Transaction rolled back due to: " + e.getMessage());
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
        } finally {
            // Clean up resources
            try {
                if (statement != null) {
                    statement.close();

                }
                con.setAutoCommit(true);
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }

        }
    }

    private void changeToDeliverStatus(int orderID) {
        try {
            // SQL query to select the order details for the given order
            String sql = """
             UPDATE [order]
             SET statusID = ?
             WHERE orderID = ?
             """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, deliveringStatus);
            statement.setInt(2, orderID); // Set the order ID in the query
            statement.executeUpdate();

            // Populating the order with its details
        } catch (SQLException e) {
            System.out.println("Error inserting order detail: " + e.getMessage());
        }
    }

    public ArrayList<ProductDetail> getProductDetailList(int unitID) {
        ArrayList<ProductDetail> productDetailList = new ArrayList<>();

        try {
            String sql = """
                     SELECT pd.productDetailID, pd.stock, pd.avgImportPrice AS importPrice, pd.baseSoldPrice AS soldPrice,
                            pd.manufactureDate, pd.expiredDate, pd.isActive, pd.batchNo,
                            p.productID, p.productName, p.categoryID, p.originID, p.manufacturer, 
                            p.componentDescription, p.description AS productDescription, p.isActive AS productActive,
                            pu.unitID, pu.unitName, pu.unitToBaseConvertRate
                     FROM productDetail pd
                     JOIN productUnit pu ON pd.unitID = pu.unitID
                     JOIN product p ON pu.productID = p.productID
                     WHERE pd.unitID = ?
                     """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, unitID); // Set the orderID parameter
            ResultSet rs = statement.executeQuery();

            productDetailList = toProductDetailList(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productDetailList;
    }

    private ArrayList<ProductDetail> toProductDetailList(ResultSet rs) {
        ArrayList<ProductDetail> productDetailList = new ArrayList<>();

        try {
            ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
            while (rs.next()) {

                ProductDetail productDetail = new ProductDetail();

                // Populate ProductDetail fields using setters
                productDetail.setProductDetailID(rs.getInt("productDetailID"));
                productDetail.setStock(rs.getInt("stock"));
                productDetail.setImportPrice(rs.getDouble("importPrice"));
                productDetail.setSoldPrice(rs.getDouble("soldPrice"));
                productDetail.setManufactureDate(rs.getDate("manufactureDate"));
                productDetail.setExpiredDate(rs.getDate("expiredDate"));
                productDetail.setIsActive(rs.getBoolean("isActive"));
                productDetail.setBatchNo(rs.getInt("batchNo"));

                // Populate the associated Product using setters
                Product product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setProductName(rs.getString("productName"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setComponentDescription(rs.getString("componentDescription"));
                product.setDescription(rs.getString("description"));
                product.setIsActive(rs.getBoolean("isActive"));

                // Set Product to ProductDetail
                productDetail.setProduct(product);

                // Populate the associated ProductUnit using setters
                ProductUnit unit = new ProductUnit();
                unit.setProductUnitID(rs.getInt("unitID"));
                unit.setProductUnitName(rs.getString("unitName"));
                unit.setUnitToBaseConvertRate(rs.getDouble("unitToBaseConvertRate"));

                // Set Unit to ProductDetail
                productDetail.setUnit(unit);

                productDetailList.add(productDetail);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productDetailList;
    }
    
    public OrderDetail findOrderDetailBaseOnId(int orderDetailID){
        OrderDetail orderDetail = new OrderDetail();
        
        try {
            String sql = """
                    SELECT 
                         od.*,                  
                         p.*,                 
                         pu.unitName,            
                         pu.unitToBaseConvertRate,
                         o.originName,            
                         c.categoryName,         
                         c.description AS categoryDescription 
                     FROM 
                         orderDetail od
                     JOIN 
                         product p ON od.productID = p.productID
                     JOIN 
                         productUnit pu ON od.unitID = pu.unitID
                     JOIN 
                         origin o ON p.originID = o.originID
                     JOIN 
                         category c ON p.categoryID = c.categoryID
                     WHERE 
                         od.orderDetailID = ?;
                     """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, orderDetailID); // Set the orderID parameter
            ResultSet rs = statement.executeQuery();

            orderDetail = toOrderDetail(rs);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return orderDetail;
    }
    
    private OrderDetail toOrderDetail(ResultSet rs){
        OrderDetail orderDetail = new OrderDetail();
        
         try {
            if (rs.next()) {

                Product product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setProductName(rs.getString("productName"));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setComponentDescription(rs.getString("componentDescription"));
                product.setDescription(rs.getString("description"));
                product.setIsActive(rs.getBoolean("isActive"));
                
                Category category = new Category();
                category.setCategoryID(rs.getInt("categoryID"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("categoryDescription"));
                product.setCategory(category);
                
                Origin origin = new Origin();
                origin.setOriginID(rs.getInt("originID"));
                origin.setOriginName(rs.getString("originName"));
                product.setOrigin(origin);
                
                orderDetail.setProduct(product);
                
                ProductUnit unit = new ProductUnit();
                unit.setProductUnitID(rs.getInt("unitID"));
                unit.setProductUnitName(rs.getString("unitName"));
                unit.setUnitToBaseConvertRate(rs.getDouble("unitToBaseConvertRate"));
                
                orderDetail.setUnit(unit);
                
                orderDetail.setOrderDetailID(rs.getInt("orderDetailID"));
                orderDetail.setSoldPrice(rs.getDouble("soldPrice"));
                orderDetail.setQuantity(rs.getInt("quantity"));
                orderDetail.setTotalProductPrice(rs.getDouble("totalProductPrice"));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        return orderDetail;
    }
    
    public ArrayList<ProductDetail> findProductDetailBaseOnUnit(int unitID){
        ArrayList<ProductDetail> productDetailList = new ArrayList<ProductDetail>();
        
        try {
            String sql = """
                    SELECT 
                        pu.unitID,
                        pu.productID,
                        pu.unitName,
                        pu.unitToBaseConvertRate,
                        pd.productDetailID,
                        pd.avgImportPrice,
                        pd.stock,
                        pd.baseSoldPrice,
                        pd.batchNo,
                        pd.manufactureDate,
                        pd.expiredDate,
                        pd.isActive
                    FROM 
                        productUnit pu
                    JOIN 
                        productDetail pd ON pu.unitID = pd.unitID
                     WHERE 
                         pu.unitID = ? AND pd.isActive = 1;
                     """;

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, unitID); // Set the orderID parameter
            ResultSet rs = statement.executeQuery();
            
            while (rs.next()) {

                ProductDetail productDetail = new ProductDetail();

                // Populate ProductDetail fields using setters
                productDetail.setProductDetailID(rs.getInt("productDetailID"));
                productDetail.setStock(rs.getInt("stock"));
                productDetail.setImportPrice(rs.getDouble("importPrice"));
                productDetail.setSoldPrice(rs.getDouble("soldPrice"));
                productDetail.setManufactureDate(rs.getDate("manufactureDate"));
                productDetail.setExpiredDate(rs.getDate("expiredDate"));
                productDetail.setIsActive(rs.getBoolean("isActive"));
                productDetail.setBatchNo(rs.getInt("batchNo"));


                // Populate the associated ProductUnit using setters
                ProductUnit unit = new ProductUnit();
                unit.setProductUnitID(rs.getInt("unitID"));
                unit.setProductUnitName(rs.getString("unitName"));
                unit.setUnitToBaseConvertRate(rs.getDouble("unitToBaseConvertRate"));

                // Set Unit to ProductDetail
                productDetail.setUnit(unit);

                productDetailList.add(productDetail);

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return productDetailList;
    }
    
    public void chooseStock(int orderDetailID, int productDetailID){
        try{
            String sql = """
                         UPDATE orderDetail
                         SET productDetailID = ?
                         WHERE orderDetailID = ?
                         """;
            PreparedStatement st = con.prepareStatement(sql);
            st.setInt(1, productDetailID);
            st.setInt(2, productDetailID);
            st.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
    }
        
    public static void main(String[] args) {
        INSTANCE.loadDB();
//        ArrayList list = INSTANCE.getOrder(1, false, "orderDate", "", 0);
//        for (Object object : list) {
//            System.out.println(object);
//        }


        OrderDetail ob = INSTANCE.findOrderDetailBaseOnId(1);
        System.out.println(ob);
    }

}
