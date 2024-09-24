/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Login;

import DAL.UserDao;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Dell
 */
public class loginuser extends HttpServlet {
   
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
            out.println("<title>Servlet loginuser</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginuser at " + request.getContextPath () + "</h1>");
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        UserDao userd= new UserDao();
        User user = userd.login(username, password);
        if(user ==null){
            request.setAttribute("mess", "Wrong user or pass");
            request.getRequestDispatcher("View/Home.jsp").forward(request, response);
      

                     
        }else{
            HttpSession session = request.getSession();
            session.setAttribute("account", user);
            if(user.isStatus()== true){
                switch(user.getRole()){
                    case 1:
                        response.sendRedirect("View/Login_Register/customerHome.jsp");
                        break; 
                    case 2:
                        response.sendRedirect("View/Login_Register/warehouseHome.jsp");
                        break;
                    case 3:
                        response.sendRedirect("View/Login_Register/saleHome.jsp");
                        break;
                    case 4:
                        response.sendRedirect("View/Login_Register/marketingHome.jsp");
                        break;
                    default:
                        response.sendRedirect("View/Login_Register/adminHome.jsp");
                        break;    
                }
            
            
            }else{
                response.sendRedirect("View/Login_Register/active.jsp");
            }
            
            
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
