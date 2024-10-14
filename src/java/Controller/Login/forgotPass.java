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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Dell
 */
public class forgotPass extends HttpServlet {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@gmail\\.com$";
    private static String codeSent = "";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet forgotPass</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet forgotPass at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String btAction = request.getParameter("btAction");
        if(btAction.equals("Tiếp")){
            String email = request.getParameter("username"); 
            // nulll or empty
            if (email == null || email.isEmpty()) {
                System.out.println("hiep1");
                request.setAttribute("messerror", "Please input your Gmail");
                request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
            } else if (!isValidEmail(email)) {
            // valid mail 
                System.out.println("hiep2");   
                request.setAttribute("email", email);  
                request.setAttribute("messerror", "Please input a valid Gmail");
                request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
            }else{
                UserDao us = new UserDao();
                User newus= us.login(email);
                if(newus==null){
                    System.out.println("khong ton tai ");   
                    request.setAttribute("email", email);  
                    request.setAttribute("messerror", "Your account is not exist in system");
                    request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response); 
                }else{
      // if success: send mail and set form =1 to show form 2
                    System.out.println("hiep3");
                    System.out.println(email);
                    codeSent ="" +generateVerificationCode();
                    Emailsw sendCode = new Emailsw();
                    // send mail 
                    sendCode.sendMail("hieppdhe171309@fpt.edu.vn", "fzemcszwnyicwxad", codeSent, email);
                    // set form 1
                    request.setAttribute("showVerificationForm", "1"); 
                    // send code and mail 
                    request.setAttribute("codeSent", codeSent);
                    request.getSession().setAttribute("codeSent", codeSent);
                    request.getSession().setAttribute("email1", email);
                    
                    request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
                }
            } 

        }else if(btAction.equals("Xác nhận")){
            // code nguoi dung nhap 
            String codeReceive = request.getParameter("verificationCode");
             // code system send  
            String checkCode =  (String) request.getSession().getAttribute("codeSent");
            String email = (String) request.getSession().getAttribute("email1");
            // check valid code 
            if(codeReceive == null || codeReceive.isEmpty()){
                /// form 1
                request.setAttribute("showVerificationForm", "1"); 
                request.setAttribute("errrocode", "please enter your code ");     
                request.setAttribute("verycode",codeReceive);
                request.setAttribute("codeSent",checkCode);
                request.setAttribute("email", email);
                request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
            }else{
                if(codeReceive.equals(checkCode)==true){
                    // set form 2
                    request.setAttribute("showVerificationForm", "2");
                    request.setAttribute("mail", email);
                    request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
                }else{
                    System.out.println("hiep4"); 
                    System.out.println(codeReceive + checkCode + email);
                    request.setAttribute("showVerificationForm", "1");
                    request.setAttribute("verycode",codeReceive);
                    request.setAttribute("codeSent",checkCode);
                    request.setAttribute("mail", email);  
                    request.setAttribute("errrocode", "Please enter your code");
                    request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);        
                }
            
            }
            
            
        
        }else if(btAction.equals("Hoàn thành")){
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            String email =  (String) request.getSession().getAttribute("mess2");
            if(password == null || password.isEmpty()||confirmPassword ==null||confirmPassword.isEmpty()){
                request.setAttribute("showVerificationForm", "2");
                request.setAttribute("email", email);  
                request.setAttribute("passcode", password); 
                request.setAttribute("confirmcode", confirmPassword); 
                request.setAttribute("errorPassword", "Please enter your password");
                request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
            
            }else if(isValidPassword(password)!= true || isValidPassword(confirmPassword)!= true){
                request.setAttribute("showVerificationForm", "2");
                request.setAttribute("email", email);  
                request.setAttribute("passcode", password); 
                request.setAttribute("confirmcode", confirmPassword); 
                request.setAttribute("errorPassword", "Please enter your valid password");
                request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
  
            }else if(password.equals(confirmPassword)!=true){
                request.setAttribute("showVerificationForm", "2");
                request.setAttribute("email", email);  
                request.setAttribute("passcode", password); 
                request.setAttribute("confirmcode", confirmPassword); 
                request.setAttribute("errorPassword", "password not match");
                request.getRequestDispatcher("/View/Login_Register/forgotPassword.jsp").forward(request, response);
            }else{
                UserDao us = new UserDao();
                try {
                    us.updatePassword(email, password);
                    request.setAttribute("mess", "");
                    request.getRequestDispatcher("View/Home.jsp").forward(request, response);
                } catch (Exception ex) {
                    Logger.getLogger(forgotPass.class.getName()).log(Level.SEVERE, null, ex);
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
    private boolean isValidEmail(String email) {
        // Sử dụng Pattern để kiểm tra theo regex
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        return pattern.matcher(email).matches();
    }
     public static String generateVerificationCode() {
        Random random = new Random();
        // Sinh số từ 100000 đến 999999
        int code = random.nextInt(899999) + 100000;
        return String.valueOf(code);
    } 
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
}
