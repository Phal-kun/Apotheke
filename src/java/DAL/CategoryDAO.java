package DAL;

import DAL.DBContext;
import Model.Product.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for Category
 */
public class CategoryDAO {

    // Singleton instance
    private static final CategoryDAO instance = new CategoryDAO();
    private Connection connection;

    // Private constructor to enforce Singleton pattern
    private CategoryDAO() {
        connection = new DBContext().connect;
    }

    public static CategoryDAO getInstance() {
        return instance;
    }

    // Method to insert a new category into the database
    public boolean insertCategory(Category category) {
        String sql = "INSERT INTO Category (CategoryName, Description, ParentCategoryID, Status) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());

            if (category.getParentCategory() != null) {
                ps.setInt(3, category.getParentCategory().getCategoryID());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }

            ps.setBoolean(4, category.isStatus()); // Assuming Category has a status field as boolean

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                new DBContext().closePreparedStatement(ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static final Logger LOG = Logger.getLogger(CategoryDAO.class.getName());

    // Get a single Category by its ID
    public Category getCategoryByID(int categoryID) {
        String query = "SELECT * FROM Category WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, categoryID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Category parentCategory = null;
                int parentID = rs.getInt("ParentCategoryID");
                if (!rs.wasNull()) {
                    parentCategory = getCategoryByID(parentID);
                }
                return new Category(
                        rs.getInt("CategoryID"),
                        parentCategory,
                        rs.getString("CategoryName"),
                        rs.getString("Description"),
                        rs.getBoolean("Status") // Assuming status is a boolean
                );
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting category by ID", e);
        }
        return null;
    }

    // Get a list of all categories
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Category";
        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category parentCategory = getCategoryByID(rs.getInt("ParentCategoryID"));
                Category category = new Category(
                        rs.getInt("CategoryID"),
                        parentCategory,
                        rs.getString("CategoryName"),
                        rs.getString("Description"),
                        rs.getBoolean("Status") // Assuming status is a boolean
                );
                categories.add(category);
            }
            rs.close();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting all categories", e);
        }
        return categories;
    }

    public List<Category> getAllCategoriesSorted(String sortOrder) {
        List<Category> categories = new ArrayList<>();
        String query = "SELECT * FROM Category ORDER BY CategoryName " + (sortOrder.equals("DESC") ? "DESC" : "ASC");

        try (PreparedStatement ps = connection.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category parentCategory = getCategoryByID(rs.getInt("ParentCategoryID"));
                Category category = new Category(
                        rs.getInt("CategoryID"),
                        parentCategory,
                        rs.getString("CategoryName"),
                        rs.getString("Description"),
                        rs.getBoolean("Status")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error getting categories with sorting", e);
        }
        return categories;
    }

    // Add a new category (alternative method to insertCategory)
    public boolean addCategory(Category category) {
        String query = "INSERT INTO Category (CategoryName, Description, ParentCategoryID, Status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            if (category.getParentCategory() != null) {
                ps.setInt(3, category.getParentCategory().getCategoryID());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setBoolean(4, category.isStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error adding category", e);
        }
        return false;
    }

    // Update an existing category
    public boolean updateCategory(Category category) {
        String query = "UPDATE Category SET CategoryName = ?, Description = ?, ParentCategoryID = ?, Status = ? WHERE CategoryID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, category.getCategoryName());
            ps.setString(2, category.getDescription());
            if (category.getParentCategory() != null) {
                ps.setInt(3, category.getParentCategory().getCategoryID());
            } else {
                ps.setNull(3, java.sql.Types.INTEGER);
            }
            ps.setBoolean(4, category.isStatus());
            ps.setInt(5, category.getCategoryID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, "Error updating category", e);
        }
        return false;
    }

    // Delete a category
    public boolean deleteCategory(int categoryID) {
        String sql = "DELETE FROM Category WHERE CategoryID = ?";
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, categoryID);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                new DBContext().closePreparedStatement(ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(CategoryDAO.getInstance().getAllCategories());
    }
}
