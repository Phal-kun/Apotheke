/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Login;

import DAL.UserDao;
import Model.User.Role;
import Model.User.User;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Dell
 */
public class register extends HttpServlet {
   
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
            out.println("<title>Servlet register</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet register at " + request.getContextPath () + "</h1>");
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
        String fullname = request.getParameter("fullname");
        String username = request.getParameter("username");
        String password= request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");
        System.out.print(password);
        if (fullname == null || username == null || password == null || confirm == null ||
            fullname.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            request.setAttribute("mess2", "Please fill in all the blanks.");
            request.getRequestDispatcher("View/Home.jsp").forward(request, response);
        }else if(isValidPassword(password)!= true || isValidPassword(confirm)!= true){
             request.setAttribute("mess2", "password must have UpperCase,LowerCase,SpecialChar ");
                request.getRequestDispatcher("View/Home.jsp").forward(request, response);
        }else if(password.equals(confirm)!=true ){
            request.setAttribute("mess2", "password must have to match ");
              request.getRequestDispatcher("View/Home.jsp").forward(request, response);
        }else{    
            UserDao us = new UserDao();
            User user = us.getUserByNameUserPass(username, password, fullname);
            if(isUserExists(username,request, response)==false&& (isUserExis(username,password,request,response)==false) ){
                Role role = new Role();
                role.setRoleID(1);
                User newuser = new User(fullname, "", username, hashPassword(password), "", true, role, "");
                us.saveUserByUsername(newuser);
                response.sendRedirect("View/Home.jsp");
            }else{
                request.setAttribute("mess2", "The account already exists in the system.");
                request.getRequestDispatcher("View/Home.jsp").forward(request, response);
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
        public static boolean isValidPassword(String password) {
            // Check if the password is longer than 8 characters
            if (password.length() <= 8) {
                return false;
            }

            // Flags to check the presence of required characters
            boolean hasUpperCase = false;
            boolean hasLowerCase = false;
            boolean hasSpecialChar = false;

            // Define special characters
            String specialCharacters = "!@#$%^&*()_+[]{}|;':\",.<>?";

            // Loop through each character in the password
            for (char ch : password.toCharArray()) {
                if (Character.isUpperCase(ch)) {
                    hasUpperCase = true;
                } else if (Character.isLowerCase(ch)) {
                    hasLowerCase = true;
                } else if (specialCharacters.indexOf(ch) != -1) {
                    hasSpecialChar = true;
                }
            }

            // Return true if all conditions are met
            return hasUpperCase && hasLowerCase && hasSpecialChar;
        }
     public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // 12 là số vòng lặp
    }
    private boolean isUserExists(String username, HttpServletRequest request, HttpServletResponse response) {
       UserDao us = new UserDao();
       try {
           User s = us.getUserByEmail(username);
           if (s != null) {

               return true; // Tài khoản đã tồn tại
           }
       } catch (Exception ex) {
           System.out.print(ex);
       }
       return false; // Tài khoản không tồn tại
   }
    private boolean isUserExis(String username,String password, HttpServletRequest request, HttpServletResponse response) {
       UserDao us = new UserDao();
       try {
           User s = us.getUserByUsernamePassword(username,password);
           if (s != null) {

               return true; // Tài khoản đã tồn tại
           }
       } catch (Exception ex) {
           System.out.print(ex);
       }
       return false; // Tài khoản không tồn tại
   }
}
