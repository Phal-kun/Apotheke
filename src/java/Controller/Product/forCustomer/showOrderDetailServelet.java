/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Product.forCustomer;

import Model.Product.Product;
import Model.Product.ProductDetail;
import Model.Product.ProductUnit;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class showOrderDetailServelet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("View/ProductOrderCustomer/ỏderDetails.jsp").forward(request, response);    
        
       
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        HttpSession session = request.getSession();
         
        Integer cartCount = (Integer) session.getAttribute("cartCount");
       
        List<Integer> productIdList = (List<Integer>) session.getAttribute("productIdList");
        if (productIdList != null && !productIdList.isEmpty()) {
            
            // Duyệt qua danh sách ID sản phẩm
            for (Integer productId : productIdList) {
                Product product = new Product();
                List<ProductUnit> productUnitList = new ArrayList<>();
       
                ProductDetail productdetail = new ProductDetail();
                ProductUnit productUnit = new ProductUnit();
                // Thực hiện hành động với từng productId
                System.out.println("Processing product ID: " + productId);
                showProductDAO showus = new showProductDAO();
                try {
                    product = showus.getProductsByProductId(productId);
                    productUnitList = showus.myListProductUnit(productId);
                    for (ProductUnit unit : productUnitList) {
                         System.out.println(unit.toString());
                    }
                    // Bạn có thể thêm logic khác ở đây
                    System.out.println(product.getProductName() + product.getDescription());
                } catch (Exception ex) {
                    Logger.getLogger(showOrderDetailServelet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            System.out.println("Product ID list is empty or not found.");
        }
        request.getRequestDispatcher("View/ProductOrderCustomer/orderDetails.jsp").forward(request, response);     
    } 
    
    
    
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
