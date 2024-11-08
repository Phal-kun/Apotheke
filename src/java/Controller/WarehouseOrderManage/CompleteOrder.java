/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.WarehouseOrderManage;

import DAL.WarehouseOrderDAO;
import Model.Order.Order;
import Model.Order.OrderDetail;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
@WebServlet(name="CompleteOrder", urlPatterns={"/CompleteOrder"})
public class CompleteOrder extends HttpServlet {
   
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
            out.println("<title>Servlet CompleteOrder</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CompleteOrder at " + request.getContextPath () + "</h1>");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            WarehouseOrderDAO db = WarehouseOrderDAO.INSTANCE;
            int orderID;
            try {
                orderID = Integer.parseInt(request.getParameter("orderID"));
            } catch (NumberFormatException e) {
                request.setAttribute("err", "Invalid Order ID.");
                request.getRequestDispatcher("/View/ErrorPage.jsp").forward(request, response);
                return;
            }

            Order order = db.getOrder(orderID);
            boolean hasError = false;
            StringBuilder errMsg = new StringBuilder();

            // If errors were detected, set error message and redirect
            if (hasError) {
                request.setAttribute("err", errMsg.toString());
                request.getRequestDispatcher("/View/WarehouseOrderManage/ApprovedOrderDetail.jsp").forward(request, response);
                return;
            }

            db.completeOrder(orderID);
            out.println("<script type=\"text/javascript\">");
            out.println("alert('Order Completed!!!');");
            out.println("window.location.href = 'ApprovedOrderList';");
            out.println("</script>");

        } catch (Exception e) {
            // Log exception using proper logging
            e.printStackTrace();
            request.setAttribute("err", "An unexpected error occurred.");
            request.getRequestDispatcher("/View/ErrorPage.jsp").forward(request, response);
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
