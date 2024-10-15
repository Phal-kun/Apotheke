package Controller.Category;

import DAL.CategoryDAO;
import Model.Product.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

public class EditCategoryServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet EditCategoryServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditCategoryServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int categoryID = Integer.parseInt(request.getParameter("categoryID"));

        // Fetch category details
        Category category = CategoryDAO.getInstance().getCategoryByID(categoryID);

        // Fetch list of all categories for the dropdown
        List<Category> categories = CategoryDAO.getInstance().getAllCategories();

        // Remove the category itself from the list if you don't want it to be a parent of itself
        categories.removeIf(c -> c.getCategoryID() == categoryID);

        if (category != null) {
            request.setAttribute("category", category);
            request.setAttribute("categories", categories);  // Set the list of categories for the dropdown
            request.getRequestDispatcher("View/CategoryManage/EditCategory.jsp").forward(request, response);
        } else {
            response.sendRedirect("CategoryManager?message=Category not found");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Retrieve form data
            int categoryID = Integer.parseInt(request.getParameter("categoryID"));
            String categoryName = request.getParameter("categoryName");
            String description = request.getParameter("description");
            String parentCategoryIDParam = request.getParameter("parentCategoryID");

            // Handle parent category (it might be null if the user selected "None")
            Category parentCategory = null;
            if (parentCategoryIDParam != null && !parentCategoryIDParam.isEmpty()) {
                int parentCategoryID = Integer.parseInt(parentCategoryIDParam);
                parentCategory = CategoryDAO.getInstance().getCategoryByID(parentCategoryID);
            }

            // Fetch the category from the database
            Category category = CategoryDAO.getInstance().getCategoryByID(categoryID);
            if (category != null) {
                // Update category with new values
                category.setCategoryName(categoryName);
                category.setDescription(description);
                category.setParentCategory(parentCategory);

                // Retrieve status from radio button value
                boolean status = Boolean.parseBoolean(request.getParameter("status"));
                category.setStatus(status);

                // Call DAO to update the category in the database
                boolean updateSuccess = CategoryDAO.getInstance().updateCategory(category);

                if (updateSuccess) {
                    // Redirect to CategoryManager if update was successful
                    response.sendRedirect("CategoryManager?message=Category updated successfully");
                } else {
                    // Handle failure to update
                    request.setAttribute("error", "Failed to update the category.");
                    request.getRequestDispatcher("View/CategoryManage/EditCategory.jsp").forward(request, response);
                }
            } else {
                response.sendRedirect("CategoryManager?message=Category not found");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log the error in server logs
            request.setAttribute("error", "An error occurred while updating the category.");
            request.getRequestDispatcher("View/CategoryManage/EditCategory.jsp").forward(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
