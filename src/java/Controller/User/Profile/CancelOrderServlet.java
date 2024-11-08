/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.User.Profile;

import DAL.OrderDao;
import Model.Order.Order;
import Model.User.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class CancelOrderServlet extends HttpServlet {
   
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
            out.println("<title>Servlet CancelOrderServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CancelOrderServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         String orderIDStr = request.getParameter("orderID");
         HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute("account");
        
         try {
                // Chuyển đổi orderID từ String sang Integer
                int orderID = Integer.parseInt(orderIDStr);

                // Gọi phương thức trong DAO để cập nhật đơn hàng (giả sử bạn có phương thức updateOrderByOrderID)
                OrderDao orderDAO = new OrderDao();
                boolean result = orderDAO.updateOrderByOrderID(orderID, 4);  // Giả sử 2 là statusID cho trạng thái đã huỷ

                if (result) {
                    List<Order> orders = orderDAO.getOrderFromUserId(user.getUserID());
                    session.setAttribute("orders", orders);

                    // Cập nhật thành công, chuyển hướng đến trang khác hoặc thông báo
                    request.getRequestDispatcher("View/pagecontrol/myorder.jsp").forward(request, response);

                } else {
                    // Nếu không cập nhật thành công
                    response.sendRedirect("orderList.jsp?status=failure");
                }
            }  catch (Exception ex) {
            Logger.getLogger(CancelOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

   
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
