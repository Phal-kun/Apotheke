/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Product.*;
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

    //fetch data from form table
    public ArrayList<Form> loadFormList() {
        System.out.println("Load form list");
        String sql = "select * from [Form]";
        ArrayList<Form> formList = new ArrayList<Form>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int formID = rs.getInt("formID");
                String formName = rs.getString("formName");
                formList.add(new Form(formID, formName));
            }
            rs.close();
        } catch (Exception e) {
            status = "Error at read Form " + e.getMessage();
        }
        return formList;
    }

    //fetch data from component table
    public ArrayList<Component> loadComponentList() {
        System.out.println("Load component list");
        String sql = "SELECT * FROM [Component]";
        ArrayList<Component> componentList = new ArrayList<Component>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int componentID = rs.getInt("componentID");
                String componentName = rs.getString("componentName");
                String componentMeasureUnit = rs.getString("componentMeasureUnit");
                componentList.add(new Component(componentID, componentName, componentMeasureUnit));
            }
            rs.close();
        } catch (Exception e) {
            status = "Error at reading Component: " + e.getMessage();
        }
        return componentList;
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

    //fetch data from manufacturer table
    public ArrayList<Manufacturer> loadManufacturerList() {
        System.out.println("Load manufacturer list");
        String sql = "SELECT * FROM [Manufacturer]";
        ArrayList<Manufacturer> manufacturerList = new ArrayList<Manufacturer>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int manufacturerID = rs.getInt("manufacturerID");
                String manufacturerName = rs.getString("manufacturerName");
                manufacturerList.add(new Manufacturer(manufacturerID, manufacturerName));
            }
            rs.close();
        } catch (Exception e) {
            status = "Error at reading Manufacturer: " + e.getMessage();
        }
        return manufacturerList;
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

    //fetch data from product table
    public ArrayList<Product> loadProductList() {
        System.out.println("Loading product list...");

        // SQL to get product, category, origin, manufacturer, and form data
        String sql = "SELECT p.productID, p.productName, p.Description, p.isActive, "
                + "c.CategoryID, c.CategoryName, c.Description AS categoryDescription, "
                + "o.originID, o.originName, "
                + "m.manufacturerID, m.manufacturerName, "
                + "f.formID, f.formName "
                + "FROM [product] p "
                + "LEFT JOIN [Category] c ON p.CategoryID = c.CategoryID "
                + "LEFT JOIN [Origin] o ON p.originID = o.originID "
                + "LEFT JOIN [Manufacturer] m ON p.ManufacturerID = m.manufacturerID "
                + "LEFT JOIN [Form] f ON p.FormID = f.formID";

        // SQL to get components associated with each product
        String sqlComponentProduct = "SELECT cp.componentID, c.componentName, cp.quantity, c.componentMeasureUnit "
                + "FROM [ComponentProduct] cp "
                + "JOIN [Component] c ON cp.componentID = c.componentID "
                + "WHERE cp.productID = ?";

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

                // Get Manufacturer object
                int manufacturerID = rsProduct.getInt("manufacturerID");
                String manufacturerName = rsProduct.getString("manufacturerName");
                Manufacturer manufacturer = new Manufacturer(manufacturerID, manufacturerName);

                // Get Form object
                int formID = rsProduct.getInt("formID");
                String formName = rsProduct.getString("formName");
                Form form = new Form(formID, formName);

                // Fetch associated components for the product
                ArrayList<Component> componentList = new ArrayList<>();
                PreparedStatement psComponentProduct = con.prepareStatement(sqlComponentProduct);
                psComponentProduct.setInt(1, productID);
                ResultSet rsComponentProduct = psComponentProduct.executeQuery();

                while (rsComponentProduct.next()) {
                    int componentID = rsComponentProduct.getInt("componentID");
                    String componentName = rsComponentProduct.getString("componentName");
                    String componentMeasureUnit = rsComponentProduct.getString("componentMeasureUnit");
                    int quantity = rsComponentProduct.getInt("quantity");
                    componentList.add(new Component(componentID, componentName, componentMeasureUnit, quantity));
                }
                rsComponentProduct.close();
                // Fetch associated Unit for the product
                ArrayList unitList = new ArrayList<>();
                PreparedStatement psUnit = con.prepareStatement(sqlUnit);
                psUnit.setInt(1, productID);
                ResultSet rsUnit = psUnit.executeQuery();
                
                ProductUnit baseUnit = new ProductUnit();
                while (rsUnit.next()) {
                    int unitID = rsUnit.getInt("unitID");
                    String unitName = rsUnit.getString("unitName");
                    int convertRate = (int) Math.floor(rsUnit.getDouble("unitToBaseConvertRate"));
                    if (convertRate==1){
                        baseUnit = new ProductUnit(unitID, unitName, convertRate);
                    }
                    unitList.add(new ProductUnit(unitID, unitName, convertRate));
                }
                rsUnit.close();
                // Add to productList
                Product product = new Product(productID, productName, category, origin, manufacturer, form, description, componentList, isActive);
                productList.add(product);
            }
            rsProduct.close();
        } catch (Exception e) {
            System.out.println("Error loading product list: " + e.getMessage());
        }

        return productList;
    }

    //find category by id
    public Category findCategoryByID(int categoryID) {
        try {
            ArrayList<Category> categoryList = loadCategoryList();
            for (Category current : categoryList) {
                if (current.getCategoryID() == categoryID) {
                    return current;
                }
            }
        } catch (Exception e) {
            status = "Error at find Category" + e.getMessage();
        }
        return null;
    }

    //find origin by id
    public Origin findOriginByID(int originID) {
        try {
            ArrayList<Origin> originList = loadOriginList();
            for (Origin current : originList) {
                if (current.getOriginID() == originID) {
                    return current;
                }
            }
        } catch (Exception e) {
            status = "Error at find Origin: " + e.getMessage();
        }
        return null;
    }

    //find manufacturer by id
    public Manufacturer findManufacturerByID(int manufacturerID) {
        try {
            ArrayList<Manufacturer> manufacturerList = loadManufacturerList();
            for (Manufacturer current : manufacturerList) {
                if (current.getManufacturerID() == manufacturerID) {
                    return current;
                }
            }
        } catch (Exception e) {
            status = "Error at find Manufacturer: " + e.getMessage();
        }
        return null;
    }

    //find form by id
    public Form findFormByID(int formID) {
        try {
            ArrayList<Form> formList = loadFormList();
            for (Form current : formList) {
                if (current.getFormID() == formID) {
                    return current;
                }
            }
        } catch (Exception e) {
            status = "Error at find Form: " + e.getMessage();
        }
        return null;
    }

    //find product by id
    public Product findProductByID(int productID) {
        System.out.println("Loading product list...");

        // SQL to get product, category, origin, manufacturer, and form data
        String sql = "SELECT p.productID, p.productName, p.Description, p.isActive, "
                + "c.CategoryID, c.CategoryName, c.Description AS categoryDescription, "
                + "o.originID, o.originName, "
                + "m.manufacturerID, m.manufacturerName, "
                + "f.formID, f.formName "
                + "FROM [product] p "
                + "LEFT JOIN [Category] c ON p.CategoryID = c.CategoryID "
                + "LEFT JOIN [Origin] o ON p.originID = o.originID "
                + "LEFT JOIN [Manufacturer] m ON p.ManufacturerID = m.manufacturerID "
                + "LEFT JOIN [Form] f ON p.FormID = f.formID "
                + "WHERE p.productID = ?";

        // SQL to get components associated with each product
        String sqlComponentProduct = "SELECT cp.componentID, c.componentName, cp.quantity, c.componentMeasureUnit "
                + "FROM [ComponentProduct] cp "
                + "JOIN [Component] c ON cp.componentID = c.componentID "
                + "WHERE cp.productID = ?";

        ArrayList<Product> productList = new ArrayList<>();

        try {
            PreparedStatement psProduct = con.prepareStatement(sql);
            psProduct.setInt(1, productID);
            ResultSet rsProduct = psProduct.executeQuery();

            while (rsProduct.next()) {
                // Get product data
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

                // Get Manufacturer object
                int manufacturerID = rsProduct.getInt("manufacturerID");
                String manufacturerName = rsProduct.getString("manufacturerName");
                Manufacturer manufacturer = new Manufacturer(manufacturerID, manufacturerName);

                // Get Form object
                int formID = rsProduct.getInt("formID");
                String formName = rsProduct.getString("formName");
                Form form = new Form(formID, formName);

                // Fetch associated components for the product
                ArrayList<Component> componentList = new ArrayList<>();
                PreparedStatement psComponentProduct = con.prepareStatement(sqlComponentProduct);
                psComponentProduct.setInt(1, productID);
                ResultSet rsComponentProduct = psComponentProduct.executeQuery();

                while (rsComponentProduct.next()) {
                    int componentID = rsComponentProduct.getInt("componentID");
                    String componentName = rsComponentProduct.getString("componentName");
                    String componentMeasureUnit = rsComponentProduct.getString("componentMeasureUnit");
                    int quantity = rsComponentProduct.getInt("quantity");
                    componentList.add(new Component(componentID, componentName, componentMeasureUnit, quantity));
                }
                rsComponentProduct.close();

                // Add to productList
                return new Product(productID, productName, category, origin, manufacturer, form, description, componentList, isActive);
            }
            rsProduct.close();
        } catch (Exception e) {
            System.out.println("Error loading product list: " + e.getMessage());
        }

        return null;
    }

    public void addProduct(String productName, int categoryID, int originID, int manufacturerID, int formID, String description, ArrayList<Component> component) {
        String productSql = "INSERT INTO [product] ([productName], [CategoryID], [originID], [ManufacturerID], [FormID], [Description], [isActive])\n"
                + "VALUES(?, ?, ?, ?, ?, ?, 1)";
        String getProductID = "SELECT ProductID FROM [Product] where ProductName = ?";
        String productComponentSql = "INSERT INTO [ComponentProduct] ([componentID], [productID], [quantity])\n"
                + "VALUES (?,?,?)";
        int productID = 0;
        try {
            //insert product
            PreparedStatement productPs = con.prepareStatement(productSql);
            productPs.setString(1, productName);
            productPs.setInt(2, categoryID);
            productPs.setInt(3, originID);
            productPs.setInt(4, manufacturerID);
            productPs.setInt(5, formID);
            productPs.setString(6, description);
            productPs.execute();
            //get productID
            PreparedStatement productIDPs = con.prepareStatement(getProductID);
            productIDPs.setString(1, productName);
            ResultSet rs = productIDPs.executeQuery();
            while (rs.next()) {
                productID = rs.getInt("productID");
            }
            rs.close();
            if (productID <= 0) {
                throw new IllegalArgumentException();
            }

            // Insert component product
            PreparedStatement productComponentPs = con.prepareStatement(productComponentSql);
            for (Component current : component) {
                productComponentPs.setInt(1, current.getComponentID());
                productComponentPs.setInt(2, productID);
                productComponentPs.setInt(3, current.getQuantity());
                productComponentPs.addBatch();
            }
            productComponentPs.executeBatch();

        } catch (Exception e) {
            status = "Error at adding Product: " + e.getMessage();
        }
    }

    public void updateProduct(int productID, String productName, int categoryID, int originID, int manufacturerID, int formID, String description, ArrayList<Component> newComponents) {
        System.out.println("Updating product...");

        // SQL to update product details
        String updateProductSql = "UPDATE [product] SET productName = ?, CategoryID = ?, originID = ?, ManufacturerID = ?, FormID = ?, Description = ? WHERE productID = ?";

        // SQL to delete all components associated with the product
        String deleteComponentSql = "DELETE FROM [ComponentProduct] WHERE productID = ?";

        // SQL to insert new components for the product
        String insertComponentSql = "INSERT INTO [ComponentProduct] (componentID, productID, quantity) VALUES (?, ?, ?)";

        try {
            // Update product information
            PreparedStatement psUpdateProduct = con.prepareStatement(updateProductSql);
            psUpdateProduct.setString(1, productName);
            psUpdateProduct.setInt(2, categoryID);
            psUpdateProduct.setInt(3, originID);
            psUpdateProduct.setInt(4, manufacturerID);
            psUpdateProduct.setInt(5, formID);
            psUpdateProduct.setString(6, description);
            psUpdateProduct.setInt(7, productID);
            psUpdateProduct.executeUpdate();

            // Delete the old component list for the product
            PreparedStatement psDeleteComponents = con.prepareStatement(deleteComponentSql);
            psDeleteComponents.setInt(1, productID);
            psDeleteComponents.executeUpdate();

            // Insert the new component list
            PreparedStatement psInsertComponent = con.prepareStatement(insertComponentSql);
            for (Component newComponent : newComponents) {
                psInsertComponent.setInt(1, newComponent.getComponentID());
                psInsertComponent.setInt(2, productID);
                psInsertComponent.setInt(3, newComponent.getQuantity());
                psInsertComponent.addBatch();
            }
            psInsertComponent.executeBatch();

            System.out.println("Product update successful!");

        } catch (Exception e) {
            System.out.println("Error updating product: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println(ProductDAO.INSTANCE.findProductByID(1));
//        System.out.println(ProductDAO.INSTANCE.loadFormList());
//        System.out.println(ProductDAO.INSTANCE.loadCategoryList());
//        System.out.println(ProductDAO.INSTANCE.loadComponentList());
//        System.out.println(ProductDAO.INSTANCE.loadManufacturerList());
//        System.out.println(ProductDAO.INSTANCE.loadOriginList());
//        System.out.println(ProductDAO.INSTANCE.loadProductList());
//        System.out.println(ProductDAO.INSTANCE.findCategoryByID(1));
//        ArrayList<Component> components = new ArrayList<>();
//        components.add(new Component(1, null, null, 5));
//        components.add(new Component(4, null, null, 10));
//
//        // Define mock product data
//        String productName = "Test Product2";
//        int productID = 10;
//        int categoryID = 3;
//        int originID = 1;
//        int manufacturerID = 1;
//        int formID = 2;
//        String description = "This is a test update product";
//
//        // Call the updateProduct method
//        ProductDAO.INSTANCE.updateProduct(productID, productName, categoryID, originID, manufacturerID, formID, description, components);
//
//        // Print status or results after adding the product
//        System.out.println("Add product status: " + daoProduct.status);
    }
}
