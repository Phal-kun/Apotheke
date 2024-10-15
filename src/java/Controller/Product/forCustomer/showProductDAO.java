/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller.Product.forCustomer;

import DAL.DBContext;
import Model.Product.Category;
import Model.Product.Form;
import Model.Product.Manufacturer;
import Model.Product.Origin;
import Model.Product.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Product.Component;
import com.oracle.wls.shaded.org.apache.bcel.generic.AALOAD;

/**
 *
 * @author Dell
 */
public class showProductDAO extends DBContext {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
    public List<Product> list() throws Exception,   SQLException{
        List<Product> products = new ArrayList<>();
        String sql = "SELECT p.productID, p.productName, p.categoryID, p.originID, p.manufacturer, p.componentDescription, "
                   + "p.description, p.baseUnitID, p.isActive, c.categoryName, o.originName "
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
                
                // manufactures
                Manufacturer manu = new Manufacturer();
                manu.setManufacturerName(rs.getString("manufacturer"));
                product.setManufacturer(manu);
                
                // Component list Component 
                Component com = new Component();
                com.setComponentMeasureUnit(rs.getString("componentDescription"));
                ArrayList<Component> myList = new ArrayList<>();
                myList.add(com);
                product.setComponent(myList);
                
                product.setDescription(rs.getString("description"));

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
    
    
}