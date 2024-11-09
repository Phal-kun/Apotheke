/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.User.Profile;

import Controller.Product.forCustomer.Item;
import Controller.Product.forCustomer.showProductDAO;
import Model.Product.Product;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class productdetail extends HttpServlet {
   
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
            out.println("<title>Servlet productdetail</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet productdetail at " + request.getContextPath () + "</h1>");
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
        String productID = request.getParameter("productID");
        HttpSession session = request.getSession();
        Item selectedItem = null;
            // Lấy listItems từ session
        List<Item> listItems = (List<Item>) session.getAttribute("listItems");
        for (Item item : listItems) {
                if (String.valueOf(item.getProductID()).equals(productID)) {
                selectedItem = item;
                break; // Dừng vòng lặp nếu đã tìm thấy item
                }
        }
        
        showProductDAO usdao = new showProductDAO();
        
        try {
            Product newproduct = usdao.getProductByID(selectedItem.getProductID());
            System.out.println(newproduct.toString());
            System.out.println(selectedItem.toString());
            request.setAttribute("product", newproduct);
            request.setAttribute("items", selectedItem);
            request.getRequestDispatcher("View/pagecontrol/productdetail.jsp").forward(request, response);
    
        } catch (Exception ex) {
            Logger.getLogger(productdetail.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request, response);
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
