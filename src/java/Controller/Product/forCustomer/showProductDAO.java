/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import DAL.DBContext;
import Model.Product.Category;
import Model.Product.Origin;
import Model.Product.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Product.Component;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;

/**
 *
 * @author Dell
 */
public class showProductDAO extends DBContext {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
    // list product 
    public List<Product> list() throws Exception,   SQLException{
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.productID, p.productName, p.categoryID, p.originID, p.manufacturer, p.componentDescription, "
                   + "p.description, p.baseUnitID, p.isActive, c.categoryName, o.originName, p.product_image "
                   + "FROM product p "
                   + "LEFT JOIN category c ON p.categoryID = c.categoryID "
                   + "LEFT JOIN origin o ON p.originID = o.originID";
        try {
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Tạo đối tượng Product
                Product product = new Product();
                product.setProductID(rs.getInt("productID"));
                product.setProductName(rs.getString("productName"));

                // Lấy thông tin Category
                Category category = new Category();
                category.setCategoryID(rs.getInt("categoryID"));
                category.setCategoryName(rs.getString("categoryName"));
                product.setCategory(category);

                // Lấy thông tin Origin
                Origin origin = new Origin();
                origin.setOriginID(rs.getInt("originID"));
                origin.setOriginName(rs.getString("originName"));
                product.setOrigin(origin);
                

                product.setManufacturer(rs.getString("manufacturer"));
                
                // Component list Component 
                Component com = new Component();
                com.setComponentMeasureUnit(rs.getString("componentDescription"));
                ArrayList<Component> myList = new ArrayList<>();
                myList.add(com);
                product.setComponent(myList);
                
                product.setDescription(rs.getString("description"));
//                product.setBaseUnitID(rs.getInt("baseUnitID"));
//                product.setActive(rs.getBoolean("isActive")); // Giả sử bạn có phương thức này

                product.setProduct_image(rs.getString("product_image"));
                products.add(product);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            // Đóng các tài nguyên
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }

        return products;
    }
    

    // list product unit by productID 
    public List<ProductUnit> myListProductUnit(int productID) throws Exception{
        List<ProductUnit> productUnits = new ArrayList<>();
        // Câu lệnh SQL để lấy các ProductUnit dựa trên ProductID
        String sql = "SELECT * FROM productUnit WHERE productID = ?";

        // Kết nối tới cơ sở dữ liệu và thực hiện truy vấn
        try {
                con = new DBContext().getConnection();
                ps = con.prepareStatement(sql);
                ps.setInt(1, productID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    // Tạo đối tượng ProductUnit
                    ProductUnit productUnit = new ProductUnit();
                    productUnit.setProductUnitID(rs.getInt("unitID"));
                    productUnit.setProductUnitName(rs.getString("unitName"));
                    productUnit.setUnitToBaseConvertRate(rs.getDouble("unitToBaseConvertRate"));
                    productUnits.add(productUnit);
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;  // Quăng lỗi nếu có vấn đề xảy ra
            }

            // Trả về danh sách ProductUnit
            return productUnits;

    }
    
    // productUnit by productId
    public ProductUnit getProductUnitByProductId(int productId) throws Exception {
        ProductUnit productUnit = new ProductUnit();
        String sql =    "SELECT unitID, productID, unitName, unitToBaseConvertRate"+
                        " FROM productUnit"+
                        " WHERE productID = ?";
        try{
                con = new DBContext().getConnection();
                ps = con.prepareStatement(sql);
                ps.setInt(1, productId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    // Tạo đối tượng ProductDetail và gán giá trị từ ResultSet
                   
                    productUnit.setProductUnitID(rs.getInt("unitID"));
                    productUnit.setProductUnitName(rs.getString("unitName"));
                    productUnit.setUnitToBaseConvertRate(rs.getDouble("unitToBaseConvertRate"));
                }
         } catch (SQLException e) {
                e.printStackTrace();
                throw e;  
            }   
        return productUnit;
    
    }
    
    // product detail by unitID
    public ProductDetail getProductDetailByUnitId(int unitId) throws Exception{
    ProductDetail productDetail = null;
    String sql = "SELECT productDetailID, baseSoldPrice, unitID " +
                 "FROM dbo.productDetail " +
                 "WHERE unitID = ?"; 
    try{
                con = new DBContext().getConnection();
                ps = con.prepareStatement(sql);
                ps.setInt(1, unitId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    // Tạo đối tượng ProductDetail và gán giá trị từ ResultSet
                    productDetail = new ProductDetail();
                    productDetail.setProductDetailID(rs.getInt("productDetailID"));
                    productDetail.setSoldPrice(rs.getInt("baseSoldPrice"));
                    Product product = null;
                    productDetail.setProduct(product);
                    ProductUnit productUnit = new ProductUnit();
                    productUnit.setProductUnitID(rs.getInt("unitID"));
                    productUnit.setProductUnitName("");
                    productUnit.setUnitToBaseConvertRate(0);

                    productDetail.setUnit(productUnit);
                    
                    productDetail.setStock(0);
                    productDetail.setBatchNo(0);              
                }
         } catch (SQLException e) {
                e.printStackTrace();
                throw e;  
            }   
        return productDetail;
        
    }
    
    public Category getCategoryByCategoryID(int categoryID) throws Exception {
    Category category = null;
    String sql = "SELECT categoryID, categoryName, description, status, parentCategoryID " +
                 "FROM dbo.category " +
                 "WHERE categoryID = ?"; 
    try {
        // Thiết lập kết nối với cơ sở dữ liệu
        con = new DBContext().getConnection();
        ps = con.prepareStatement(sql);
        
        // Gán giá trị cho tham số trong câu truy vấn SQL
        ps.setInt(1, categoryID);
        
        // Thực thi câu truy vấn
        rs = ps.executeQuery();
        
        // Nếu có dữ liệu trả về, khởi tạo và gán giá trị cho đối tượng Category
        if (rs.next()) {
            category = new Category();
            category.setCategoryID(rs.getInt("categoryID"));
            category.setCategoryName(rs.getString("categoryName"));
            category.setDescription(rs.getString("description"));
            category.setStatus(rs.getBoolean("status"));
            
            // Xử lý parentCategoryID nếu có
            int parentCategoryID = rs.getInt("parentCategoryID");
            if (parentCategoryID != 0) {
                Category parentCategory = getCategoryByCategoryID(parentCategoryID);
                category.setParentCategory(parentCategory);
            } else {
                category.setParentCategory(null);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw new Exception("Error retrieving category by category ID", e);
    } finally {
        // Đảm bảo đóng tài nguyên sau khi hoàn thành
        if (rs != null) rs.close();
        if (ps != null) ps.close();
        if (con != null) con.close();
    }
    return category;
}
    public Origin getOriginByProductID(int productID) throws Exception {
        Origin origin = null;
        String sql = "SELECT o.originID, o.originName " +
                     "FROM dbo.product p " +
                     "INNER JOIN dbo.origin o ON p.originID = o.originID " +
                     "WHERE p.productID = ?";

        try {
            // Thiết lập kết nối với cơ sở dữ liệu
            con = new DBContext().getConnection(); // Đảm bảo DBContext cung cấp kết nối đúng
            ps = con.prepareStatement(sql);

            // Gán giá trị cho tham số trong câu truy vấn SQL
            ps.setInt(1, productID);

            // Thực thi câu truy vấn
            rs = ps.executeQuery();

            // Nếu có dữ liệu trả về, khởi tạo và gán giá trị cho đối tượng Origin
            if (rs.next()) {
                origin = new Origin();
                origin.setOriginID(rs.getInt("originID"));
                origin.setOriginName(rs.getString("originName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new Exception("Error retrieving origin by product ID", e);
        } finally {
            // Đảm bảo đóng tài nguyên sau khi hoàn thành
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return origin;
    }
    
     public Product getProductByID(int productID) throws SQLException, Exception {
        Product product = null;
        String sql = "SELECT p.productID, p.categoryID, c.categoryName, c.description AS categoryDescription, " +
                     "p.originID, o.originName, p.manufacturer, p.componentDescription, p.isActive, p.product_image " +
                     "FROM product p " +
                     "JOIN category c ON p.categoryID = c.categoryID " +
                     "JOIN origin o ON p.originID = o.originID " +
                     "JOIN productUnit u ON p.baseUnitID = u.unitID " +
                     "WHERE p.productID = ?";
        
        try {
            // Thiết lập kết nối với cơ sở dữ liệu
            con = new DBContext().getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, productID);

            // Thực thi câu truy vấn
            rs = ps.executeQuery();

            // Nếu có kết quả
            if (rs.next()) {
                product = new Product();
                product.setProductID(rs.getInt("productID"));
                
                // Lấy thông tin Category
                Category category = new Category();
                category.setCategoryID(rs.getInt("categoryID"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setDescription(rs.getString("categoryDescription"));
                product.setCategory(category);
                
                // Lấy thông tin Origin
                Origin origin = new Origin();
                origin.setOriginID(rs.getInt("originID"));
                origin.setOriginName(rs.getString("originName"));
                product.setOrigin(origin);

                // Các trường khác
                product.setManufacturer(rs.getString("manufacturer"));
                product.setComponentDescription(rs.getString("componentDescription"));
                product.setIsActive(rs.getBoolean("isActive"));
                product.setProduct_image(rs.getString("product_image"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving product by ID", e);
        } finally {
            // Đảm bảo đóng tài nguyên sau khi hoàn thành
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        }
        return product;
    }
}
