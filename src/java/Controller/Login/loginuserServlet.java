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
import org.mindrot.jbcrypt.BCrypt;


/**
 *
 * @author Dell
 */
public class loginuserServlet extends HttpServlet {
   
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
            out.println("<title>Servlet loginsde</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet loginsde at " + request.getContextPath () + "</h1>");
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
        User user = userd.login(username);
        System.out.println(user.toString());
        if(user ==null){
            request.setAttribute("mess", "Wrong user or pass");
            request.setAttribute("enteredUsername", username); // Lưu username đã nhập
            request.setAttribute("enteredPassword", password); // Lưu password đã nhập
            request.getRequestDispatcher("View/Home.jsp").forward(request, response);  
            
        }else{
            if(!BCrypt.checkpw(password, user.getPassword())){
                request.setAttribute("mess", "Wrong user or pass");
                request.setAttribute("enteredUsername", username); // Lưu username đã nhập
                request.setAttribute("enteredPassword", password); // Lưu password đã nhập
                request.getRequestDispatcher("View/Home.jsp").forward(request, response);
            }else{
                HttpSession session = request.getSession();
                session.setAttribute("account", user);
                System.out.println(user.toString());
                System.out.println(user.getRole().getRoleID());
            if(user.isStatus() == true) {
                System.out.println("dung roi");
                switch (user.getRole().getRoleID()) {
                    case 1:
                        request.getRequestDispatcher("View/Login_Register/customerHome.jsp").forward(request, response);
                        break;
                    case 2:
                        System.out.println("deo on roi");
                        request.getRequestDispatcher("View/Login_Register/warehouseHome.jsp").forward(request, response);
                        break;
                    case 3:
                        request.getRequestDispatcher("/CRUDOrderList").forward(request, response);
                        break;
                    case 4:
                        request.getRequestDispatcher("/BlogManager").forward(request, response);
                        break;
                    case 5:
                        request.getRequestDispatcher("/CRUDUserList").forward(request, response);
                        break;
                    default:
                        break;
                }
            
            
            }else{
                response.sendRedirect("View/Login_Register/active.jsp");
            }
            
            
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
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // 12 là số vòng lặp
    }
    public boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    //f z e m c s z w n y i c w x a d
}
