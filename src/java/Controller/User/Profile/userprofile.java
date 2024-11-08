/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.User.Profile;

import DAL.UserDao;
import Model.User.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class userprofile extends HttpServlet {
   
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
            out.println("<title>Servlet userprofile</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet userprofile at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        
         processRequest(request, response);
        
        
    } 

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        HttpSession session = request.getSession();
        User user = (User) request.getSession().getAttribute("account");
        String phonePattern = "^0\\d{9}$";  // Đảm bảo số điện thoại bắt đầu bằng 0 và có 10 chữ số
       
        if (!phone.matches(phonePattern)) {
            // Nếu không hợp lệ, gửi thông báo lỗi về trang JSP
            request.setAttribute("errorre", "invalid phone number");
            request.getRequestDispatcher("View/pagecontrol/userprofile.jsp").forward(request, response);
           
        }else{
            UserDao userDao = new UserDao();
             try {
                 userDao.updateProfile(user.getUsername(), fullname, phone, gender, address);
                
                 session.setAttribute("account",   userDao.getUserByEmail(user.getUsername()));
                
                 request.setAttribute("updateSuccess", "Profile updated successfully!");
                // Chuyển hướng về trang profile để hiển thị thông báo
                request.getRequestDispatcher("View/pagecontrol/userprofile.jsp").forward(request, response);

             } catch (Exception ex) {
                 Logger.getLogger(userprofile.class.getName()).log(Level.SEVERE, null, ex);
                  request.setAttribute("errorre", "Error occurred while updating the profile.");
                     request.getRequestDispatcher("View/pagecontrol/userprofile.jsp").forward(request, response);
   
             }
             
        }
        
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
