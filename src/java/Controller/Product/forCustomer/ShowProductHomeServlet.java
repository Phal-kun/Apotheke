/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Product.forCustomer;

import DAL.ProductDAO;
import Model.Product.Component;
import Model.Product.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class ShowProductHomeServlet extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        showProductDAO showPdDAo = new showProductDAO();
        try {
            // Lấy danh sách sản phẩm từ ProductDAO
            List<Product> productList = showPdDAo.list();
            ArrayList<Component> allComponents = new ArrayList<>();
            // Truyền danh sách sản phẩm vào request
            request.setAttribute("productList", productList);
            // Forward request và response đến trang JSP
            for (Product product : productList){
                ArrayList<Component> components = product.getComponent();
                 // Kiểm tra xem danh sách component của product có dữ liệu không
                if (components != null && !components.isEmpty()) {
                    
                    // Thêm tất cả các components của sản phẩm vào danh sách allComponents
                    allComponents.addAll(components);
                }
            }
            HttpSession session = request.getSession();
            Integer cartCount = (Integer) session.getAttribute("cartCount");
            if (cartCount == null) {
                cartCount = 0;
                session.setAttribute("cartCount", cartCount);
            }
            request.getRequestDispatcher("View/Home.jsp").forward(request, response);  
            
        } catch (Exception e) {
            e.printStackTrace();
            // Xử lý lỗi và chuyển hướng đến trang lỗi nếu cần
            request.setAttribute("errorMessage", "Có lỗi xảy ra khi lấy danh sách sản phẩm");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }   
    
        
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
