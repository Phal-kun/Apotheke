/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.WarehouseOrderManage;

import DAL.ProductDAO;
import DAL.WarehouseOrderDAO;
import Model.Order.OrderDetail;
import Model.Product.Product;
import Model.Product.ProductDetail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
@WebServlet(name="ProductDetailList", urlPatterns={"/ProductDetailList"})
public class ProductDetailList extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ProductDetailList</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ProductDetailList at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try{
            WarehouseOrderDAO db = WarehouseOrderDAO.INSTANCE;
            
            int orderDetailID = Integer.parseInt(request.getParameter("orderDetailID"));
            int orderStatusID = Integer.parseInt(request.getParameter("orderStatusID"));
            
            OrderDetail orderDetail = db.findOrderDetailBaseOnId(orderDetailID);
            ArrayList<ProductDetail> productDetailList = db.findProductDetailBaseOnUnit(orderDetail.getUnit().getProductUnitID());
            
            request.setAttribute("orderDetail", orderDetail);
            request.setAttribute("productDetailList", productDetailList);
            request.getSession().setAttribute("orderStatusID", orderStatusID);
                
            request.getRequestDispatcher("/View/WarehouseOrderManage/ViewProductDetail.jsp").forward(request, response);
            

        }catch (Exception e){
            System.out.println(e);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try{
             int orderDetailID = Integer.parseInt(request.getParameter("orderDetailID"));
             int productDetailID = Integer.parseInt(request.getParameter("productDetailID"));
             
             
             WarehouseOrderDAO db = WarehouseOrderDAO.INSTANCE;
             
             db.chooseStock(orderDetailID, productDetailID);
             OrderDetail orderDetail = db.findOrderDetailBaseOnId(orderDetailID);
             ArrayList<ProductDetail> productDetailList = db.findProductDetailBaseOnUnit(orderDetail.getUnit().getProductUnitID());
             request.setAttribute("orderDetail", orderDetail);
            request.setAttribute("productDetailList", productDetailList);
                
            request.getRequestDispatcher("/View/WarehouseOrderManage/ViewProductDetail.jsp").forward(request, response);
             
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
