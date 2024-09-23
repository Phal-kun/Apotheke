/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.User;

import DAL.DAOUserList;
import Model.User.Role;
import Model.User.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class CRUDUserAddStaff extends HttpServlet {
   
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
            out.println("<title>Servlet CRUDUserAddStaffServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CRUDUserAddStaffServlet at " + request.getContextPath () + "</h1>");
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
        request.getRequestDispatcher("/View/UserManage/AddStaff.jsp").forward(request, response);
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
        String fullname = request.getParameter("fullname");
        String phone = request.getParameter("phone");
        String username = request.getParameter("username");
        String gender = request.getParameter("gender");
        boolean status = Boolean.parseBoolean(request.getParameter("status"));
        int roleID = Integer.parseInt(request.getParameter("role"));
        String address = request.getParameter("address");
        
        Role role = new Role(roleID,"");
        User user = new User(0, fullname, phone, username, "12345678", gender, status, role, address);
        
        DAOUserList db = DAOUserList.INSTANCE;
        db.addStaff(user);
        
        HttpSession session = request.getSession();              
        ArrayList<User> userList = db.getUser(1, true, "fullname", true, "", null);
        request.setAttribute("userList", userList);
        session.setAttribute("showcustomer", true);
        request.getRequestDispatcher("/View/UserManage/UserList.jsp").forward(request, response);
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
