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
import jakarta.servlet.http.HttpSession;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class loginGoogleServlet extends HttpServlet {
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
           request.getRequestDispatcher("/View/Login_Register/activeHome.jsp").forward(request,response);
        }else{
            String accessToken = GoogleLG.getToken(code);
            User user = GoogleLG.getUserInfo(accessToken);
            UserDao usdao = new UserDao();
            System.out.println(user.toString());
            try {
                // check if ton tai chuyen huong luon 
                String email = user.getUsername();
                // khong ton tai thi luu cai moi 
                if(usdao.getUserByEmail(email)==null){
                    System.out.print("ko co user ");
                    usdao.saveUser(user);
                   // Mật khẩu đúng, thiết lập session
                    HttpSession session = request.getSession();
                    session.setAttribute("account", user);
                    request.getRequestDispatcher("/View/Login_Register/customerHome.jsp").forward(request,response);
                }else{  
                    HttpSession session = request.getSession();
                    User checkuser = usdao.getUserByEmail(email);
                    System.out.println("dhsa"+email);
                    System.out.println("sai roi " + checkuser.toString());
                    session.setAttribute("account", checkuser);
                    System.out.print(checkuser.toString());
                        switch (checkuser.getRole().getRoleID()) {
                            case 1:
                                  request.getRequestDispatcher("View/Home.jsp").forward(request, response);
                      
                               break; 
                            case 2:
                               request.getRequestDispatcher("View/Login_Register/warehouseHome.jsp").forward(request, response);
                        break;
                            case 3:
                                response.sendRedirect("View/Login_Register/saleHome.jsp");
                                break;
                            case 4:
                                response.sendRedirect(request.getContextPath() + "/BlogManager");
                                break;
                            case 5:
                                response.sendRedirect(request.getContextPath() + "/CRUDUserList");
                                break;
                            default:
                                break;    
                                }
                            } 
                
             } catch (Exception ex) {
                 Logger.getLogger(loginGoogleServlet.class.getName()).log(Level.SEVERE, null, ex);
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
