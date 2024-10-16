package Controller.Category;

import DAL.CategoryDAO;
import Model.Product.Category;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class CreateCategoryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Fetch list of categories for the dropdown (to select parent category)
        List<Category> categories = CategoryDAO.getInstance().getAllCategories();
        
        request.setAttribute("categories", categories);  // Pass the list to the JSP
        request.getRequestDispatcher("View/CategoryManage/CreateCategory.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            // Get data from the form
            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");
            String parentCategoryID = request.getParameter("parentCategoryID");
            String statusParam = request.getParameter("status");  // Get status from radio buttons

            // Convert status to boolean (active = true, inactive = false)
            boolean status = "active".equalsIgnoreCase(statusParam);

            // Determine if a parent category was selected
            Category parentCategory = null;
            if (parentCategoryID != null && !parentCategoryID.isEmpty()) {
                parentCategory = CategoryDAO.getInstance().getCategoryByID(Integer.parseInt(parentCategoryID));
            }

            // Create a new Category object with boolean status
            Category newCategory = new Category(0, parentCategory, categoryName, description, status);

            // Save the new category
            CategoryDAO.getInstance().addCategory(newCategory);

            // Redirect back to the category list
            response.sendRedirect("CategoryManager?message=Category created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while creating the category.");
            request.getRequestDispatcher("View/CategoryManage/CreateCategory.jsp").forward(request, response);
        }
    }
}
