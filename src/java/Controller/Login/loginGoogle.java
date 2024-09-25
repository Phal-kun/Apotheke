/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Login;

import DAL.UserDao;
import Model.User.User;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class loginGoogle extends HttpServlet {
   private static final long serialVersionUID = 1L;
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {       
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
         String code = request.getParameter("code");
        if (code == null || code.isEmpty()) {
           request.getRequestDispatcher("index.html").forward(request,response);
        }else{
            String accessToken = GoogleLG.getToken(code);
            User user = GoogleLG.getUserInfo(accessToken);
            UserDao usdao = new UserDao();
             try {
                if(usdao.getUserByEmail(user.getFullname())==null){
                   usdao.saveUser(user);
                   request.getRequestDispatcher("customerHome.jsp");
                }else{
                    if(user.getFullname()== usdao.getUserByEmail(user.getFullname()).getFullname()){
                        switch (user.getRole().getRoleID()) {
                            case 1:
                                 request.getRequestDispatcher("/Login_Register/customerHome.jsp");
                                break;
                            case 2:
                                request.getRequestDispatcher("/Login_Register/warehouseHome.jsp");
                                break;
                            case 3:
                                request.getRequestDispatcher("/Login_Register/saleHome.jsp");
                                break;   
                            case 4:
                                request.getRequestDispatcher("/Login_Register/marketingHome.jsp");
                                break;     
                            default:
                                request.getRequestDispatcher("/Login_Register/adminHome.jsp");
                                break; 
                        }
                    } 
                }
             } catch (Exception ex) {
                 Logger.getLogger(loginGoogle.class.getName()).log(Level.SEVERE, null, ex);
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
