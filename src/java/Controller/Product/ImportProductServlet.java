package Controller.Product;

import DAL.ProductDAO;
import Model.Product.*;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ImportProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> productList = ProductDAO.INSTANCE.loadProductList(); // Fetch product list from DAO
        request.setAttribute("productList", productList);
        request.getRequestDispatcher("View/ProductManage/ImportProduct.jsp").forward(request, response); // Forward to JSP
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Parse form data
            int unitID = Integer.parseInt(request.getParameter("unitID"));
            double importPrice = Double.parseDouble(request.getParameter("importPrice"));
            double salePrice = Double.parseDouble(request.getParameter("salePrice"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int batchNo = Integer.parseInt(request.getParameter("batchNo"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            Date manufacturerDate = sdf.parse(request.getParameter("manufacturerDate"));
            Date expiredDate = sdf.parse(request.getParameter("expiredDate"));

            // Convert dates to java.sql.Date
            java.sql.Date sqlManufacturerDate = new java.sql.Date(manufacturerDate.getTime());
            java.sql.Date sqlExpiredDate = new java.sql.Date(expiredDate.getTime());

            // Call DAO method to import product
            ProductDAO.INSTANCE.importProduct(unitID, importPrice, quantity, batchNo, sqlManufacturerDate, sqlExpiredDate, salePrice);

            // Redirect to the GET request after success
            response.sendRedirect("ImportProduct");  // Redirect to refresh the page and load product list

        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "Invalid input format. Please check your entries and try again.");
            request.getRequestDispatcher("View/ProductManage/ImportProduct.jsp").forward(request, response); // Forward to JSP with error message
        } catch (SQLException ex) {
            Logger.getLogger(ImportProductServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("errorMsg", "Database error occurred. Please try again later.");
            request.getRequestDispatcher("View/ProductManage/ImportProduct.jsp").forward(request, response); // Forward to JSP with error message
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles product imports";
    }
}
