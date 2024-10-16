/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Product.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Admin
 */
public class ProductDAO {

    public static ProductDAO INSTANCE = new ProductDAO();
    private Connection con;
    private String status = "OK";

    private ProductDAO() {
        if (INSTANCE == null) {
            con = new DBContext().connect;
        } else {
            INSTANCE = this;
        }
    }
    
    //fetch data from origin table
    public ArrayList<Origin> loadOriginList() {
        System.out.println("Load origin list");
        String sql = "SELECT * FROM [Origin]";
        ArrayList<Origin> originList = new ArrayList<Origin>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int originID = rs.getInt("originID");
                String originName = rs.getString("originName");
                originList.add(new Origin(originID, originName));
            }
            rs.close();
        } catch (Exception e) {
            status = "Error at reading Origin: " + e.getMessage();
        }
        return originList;
    }
    
    //fetch data from category table
    public ArrayList<Category> loadCategoryList() {
        System.out.println("Load category list");
        String sql = "select * from [Category]";
        ArrayList<Category> categoryList = new ArrayList<Category>();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Temporary map to hold parent-child relationships
            HashMap<Integer, Category> categoryMap = new HashMap<>();

            // First, load all categories without parent reference
            while (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                int parentCategoryID = rs.getInt("ParentCategoryID"); // Might be NULL
                String categoryName = rs.getString("CategoryName");
                String description = rs.getString("Description");

                Category parentCategory = null; // Default to null if no parent

                // If parentCategoryID is not null, find or delay setting it
                if (parentCategoryID != 0) {
                    parentCategory = categoryMap.get(parentCategoryID); // Retrieve from map or add later
                }

                Category category = new Category(categoryID, parentCategory, categoryName, description);
                categoryList.add(category);

                // Store this category in the map for later reference by its children
                categoryMap.put(categoryID, category);
            }

            rs.close();
        } catch (Exception e) {
            status = "Error at reading Category: " + e.getMessage();
        }
        return categoryList;
    }
    
    public ArrayList<Product> loadProductList(){
         System.out.println("Loading product list...");

        // SQL to get product, category, origin, manufacturer, and form data
        String sql = "SELECT p.productID, p.productName, p.Description, p.isActive, p.manufacturer, "
                + "c.CategoryID, c.CategoryName, c.Description AS categoryDescription, "
                + "o.originID, o.originName, "
                + "u.unitID, u.unitName "
                + "FROM [product] p "
                + "LEFT JOIN [Category] c ON p.CategoryID = c.CategoryID "
                + "LEFT JOIN [Origin] o ON p.originID = o.originID "
                + "LEFT JOIN [productUnit] u ON p.baseUnitID = u.unitID";

        // SQL to get components associated with each product
        String sqlComponentProduct = "SELECT c.componentName, c.quantity, c.componentUnit "
                + "FROM [ComponentProduct] c "
                + "JOIN [product] p ON c.productID = p.productID "
                + "WHERE c.productID = ?";
        
        String sqlUnit = "SELECT u.unitID, u.unitName, u.unitToBaseConvertRate "
                + "FROM [productUnit] u "
                + "JOIN [product] p ON u.productID = p.productID "
                + "WHERE u.productID = ?";

        ArrayList<Product> productList = new ArrayList<>();

        try {
            PreparedStatement psProduct = con.prepareStatement(sql);
            ResultSet rsProduct = psProduct.executeQuery();

            while (rsProduct.next()) {
                // Get product data
                int productID = rsProduct.getInt("productID");
                String productName = rsProduct.getString("productName");
                String description = rsProduct.getString("Description");
                boolean isActive = rsProduct.getBoolean("isActive");

                // Get Category object
                int categoryID = rsProduct.getInt("CategoryID");
                String categoryName = rsProduct.getString("CategoryName");
                String categoryDescription = rsProduct.getString("categoryDescription");
                Category category = new Category(categoryID, null, categoryName, categoryDescription); // Assuming no parentCategory

                // Get Origin object
                int originID = rsProduct.getInt("originID");
                String originName = rsProduct.getString("originName");
                Origin origin = new Origin(originID, originName);

                // Fetch associated components for the product
                ArrayList<Component> componentList = new ArrayList<>();
                PreparedStatement psComponentProduct = con.prepareStatement(sqlComponentProduct);
                psComponentProduct.setInt(1, productID);
                ResultSet rsComponentProduct = psComponentProduct.executeQuery();

                while (rsComponentProduct.next()) {
                    String componentName = rsComponentProduct.getString("componentName");
                    String componentMeasureUnit = rsComponentProduct.getString("componentUnit");
                    int quantity = (int) Math.floor(rsComponentProduct.getDouble("quantity"));
                    componentList.add(new Component(componentName, componentMeasureUnit, quantity));
                }
                rsComponentProduct.close();
                // Fetch associated Unit for the product
                ArrayList<Unit> unitList = new ArrayList<>();
                PreparedStatement psUnit = con.prepareStatement(sqlUnit);
                psUnit.setInt(1, productID);
                ResultSet rsUnit = psUnit.executeQuery();
                
                Unit baseUnit = new Unit();
                while (rsUnit.next()) {
                    int unitID = rsUnit.getInt("unitID");
                    String unitName = rsUnit.getString("unitName");
                    int convertRate = (int) Math.floor(rsUnit.getDouble("unitToBaseConvertRate"));
                    if (convertRate==1){
                        baseUnit = new Unit(unitID, unitName, convertRate);
                    }
                    unitList.add(new Unit(unitID, unitName, convertRate));
                }
                rsUnit.close();
                // Add to productList
                Product product = new Product(productID, productName, category, origin, status, categoryDescription, description, baseUnit, unitList, componentList, isActive);
                productList.add(product);
            }
            rsProduct.close();
        } catch (Exception e) {
            System.out.println("Error loading product list: " + e.getMessage());
        }

        return productList;
    }
    
    //insert new product using procedure
    public void insertProduct(int productID, String productName, int categoryID, int originID, String manufacturer, String description, String componentDescription, String unitString, String componentString) {
        String sql = "{call InsertProduct(?, ?, ?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement stmt = con.prepareCall(sql)) {
            stmt.setInt(1, productID);
            stmt.setString(2, productName);
            stmt.setInt(3, categoryID);
            stmt.setInt(4, originID);
            stmt.setString(5, manufacturer);
            stmt.setString(6, description);
            stmt.setString(7, componentDescription); // Can be NULL or a valid string
            stmt.setString(8, unitString); // Your unit string
            stmt.setString(9, componentString); // Your component string

            stmt.execute();
            System.out.println("Product inserted successfully!");
        } catch (SQLServerException ex) {
            System.err.println("SQL Error: " + ex.getMessage());
        } catch (SQLException ex) {
            // Handle other SQL exceptions
            System.err.println("SQL Error: " + ex.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Error: " + e.getMessage());
        }
    }

        public void importProduct(int productID, double importPrice, int batchNo, int quantity, Date manufacturedDate, Date expiredDate, double price) throws SQLException {
        // Assuming the stored procedure is called 'importProduct'
        String sql = "{CALL importProduct(?, ?, ?, ?, ?, ?, ?)}";

        try (CallableStatement callableStatement = con.prepareCall(sql)) {
            // Set the input parameters for the stored procedure
            callableStatement.setInt(1, productID);
            callableStatement.setDouble(2, importPrice);
            callableStatement.setInt(3, batchNo);
            callableStatement.setInt(4, quantity);
            callableStatement.setDate(5, manufacturedDate);
            callableStatement.setDate(6, expiredDate);
            callableStatement.setDouble(7, price);


            // Execute the stored procedure
            callableStatement.execute();
        }
    }
    
    public static void main(String[] args) {
        insertProductTest();
    }

    static void insertProductTest() {
//        int productID = 1;
//        String productName = "Test Product";
//        int categoryID = 1; // Assuming category exists
//        int originID = 1; // Assuming origin exists
//        String manufacturer = "Test Manufacturer";
//        String description = "Test product description.";
//        String componentDescription = null; // Optional
//        String unitString = "Tablet, 1.00; Capsule, 0.50"; // List of units
//        String componentString = "Salicylic Acid, 500, mg; Buffering Agent, 100, mg"; // List of components
//        ProductDAO.INSTANCE.insertProduct(productID, productName, categoryID, originID, manufacturer, description, componentDescription, unitString, componentString);
//        
        System.out.println(ProductDAO.INSTANCE.loadProductList());
    }
}
