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
            int unitID = Integer.parseInt(request.getParameter("unitID"));
            double importPrice = Double.parseDouble(request.getParameter("importPrice"));
            double salePrice = Double.parseDouble(request.getParameter("salePrice")); // Get sale price
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            int batchNo = Integer.parseInt(request.getParameter("batchNo"));
            // Use SimpleDateFormat for flexible date parsing
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // Match your input format
            Date manufacturerDate = sdf.parse(request.getParameter("manufacturerDate"));
            Date expiredDate = sdf.parse(request.getParameter("expiredDate"));

            // Convert java.util.Date to java.sql.Date if necessary
            java.sql.Date sqlManufacturerDate = new java.sql.Date(manufacturerDate.getTime());
            java.sql.Date sqlExpiredDate = new java.sql.Date(expiredDate.getTime());

            // Call the DAO method to handle product import
            ProductDAO.INSTANCE.importProduct(unitID, importPrice, quantity, batchNo, sqlManufacturerDate, sqlExpiredDate, salePrice);

            // Redirect or forward to success page
            doGet(request, response);

        } catch (ParseException | NumberFormatException e) {
            e.printStackTrace();
            System.out.println(e);
        } catch (SQLException ex) {
            Logger.getLogger(ImportProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Handles product imports";
    }
}
