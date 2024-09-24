/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.User;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import DAL.DAOUserList;
import Model.User.*;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
public class CRUDUserList extends HttpServlet {
   
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
            out.println("<title>Servlet CRUDUserListServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CRUDUserListServlet at " + request.getContextPath () + "</h1>");
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
        DAOUserList db = DAOUserList.INSTANCE;
        HttpSession session = request.getSession();      
        int indexNowCo = 1;
        
        
        
        ArrayList<User> userList = db.getUser(indexNowCo, true, "fullname", true, "", null);
        request.setAttribute("userList", userList);
        session.setAttribute("indexNowCo", indexNowCo);
        session.setAttribute("showcustomer", true);
        request.setAttribute("endPageCo", db.getTotalPages(true, "", null));
        
        request.getRequestDispatcher("/View/UserManage/UserList.jsp").forward(request, response);
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
        DAOUserList db = DAOUserList.INSTANCE;
        HttpSession session = request.getSession(false);  
        int indexNowCo;
        
        if(request.getParameter("indexCo") != null){
            indexNowCo = Integer.parseInt(request.getParameter("indexCo"));
        } else {
            indexNowCo = 1;
        }
        
        if(request.getParameter("show")!=null){
            session.setAttribute("showcustomer", request.getParameter("show").equals("true"));
            session.setAttribute("filter", null); 
        }
    
        String keyword = request.getParameter("keyword");
        if (request.getParameter("keywordReset") != null && request.getParameter("keywordReset").equals("true")) {
            session.setAttribute("keyword", keyword);
        }


        String[] filterArr = request.getParameterValues("filter");
        if (request.getParameter("filterReset") != null && request.getParameter("filterReset").equals("true")) {
            session.setAttribute("filter", filterArr); 
        } 

        int endPageCo = db.getTotalPages((boolean)session.getAttribute("showcustomer"), (String)session.getAttribute("keyword"), (String[])session.getAttribute("filter"));
        ArrayList<User> userList = db.getUser(indexNowCo, true, "fullname", (boolean) session.getAttribute("showcustomer"), (String) session.getAttribute("keyword"), (String[]) session.getAttribute("filter"));
        request.setAttribute("userList", userList);
        request.setAttribute("indexNowCo", indexNowCo);
        request.setAttribute("endPageCo", endPageCo);

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
