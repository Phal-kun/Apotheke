/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.WarehouseOrderManage;

import DAL.DAOOrderManage;
import DAL.WarehouseOrderDAO;
import Model.Order.Order;
import Model.Order.Status;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ApprovedOrderList", urlPatterns = {"/ApprovedOrderList"})
public class ApprovedOrderList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
            out.println("<title>Servlet ApprovedOrderList</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ApprovedOrderList at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Go to listing");
        
        WarehouseOrderDAO db = WarehouseOrderDAO.INSTANCE;
        HttpSession session = request.getSession();
        int indexNowCo = 1;

        ArrayList<Order> orderList = db.getOrder(indexNowCo, false, "orderDate", "", 0);
        ArrayList<Status> statusList = db.getStatus();

        request.setAttribute("orderList", orderList);
        request.setAttribute("statusList", statusList);

        session.setAttribute("indexNowCo", indexNowCo);
        request.setAttribute("endPageCo", db.getTotalPages("", 0));

        session.setAttribute("statusID", 0);

        request.getRequestDispatcher("/View/WarehouseOrderManage/ViewApprovedOrder.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        WarehouseOrderDAO db = WarehouseOrderDAO.INSTANCE;
        HttpSession session = request.getSession(false);
        int indexNowCo;

        if (request.getParameter("indexCo") != null) {
            indexNowCo = Integer.parseInt(request.getParameter("indexCo"));
        } else {
            indexNowCo = 1;
        }

        String keyword = request.getParameter("keyword");
        if (request.getParameter("keywordReset") != null && keyword != null) {
            session.setAttribute("keyword", keyword);
        } else if (request.getParameter("keywordReset") != null && keyword != null) {
            session.setAttribute("keyword", null);
        }

        String statusIDString = request.getParameter("statusID");
        if (statusIDString != null && !statusIDString.isEmpty()) {
            int statusID = Integer.parseInt(statusIDString);
            session.setAttribute("statusID", statusID);
        }

        int endPageCo = db.getTotalPages((String) session.getAttribute("keyword"), (int) session.getAttribute("statusID"));
        ArrayList<Order> orderList = db.getOrder(indexNowCo, false, "orderDate", (String) session.getAttribute("keyword"), (int) session.getAttribute("statusID"));
        ArrayList<Status> statusList = db.getStatus();

        request.setAttribute("orderList", orderList);
        request.setAttribute("indexNowCo", indexNowCo);
        request.setAttribute("endPageCo", endPageCo);
        request.setAttribute("statusList", statusList);

        request.getRequestDispatcher("/View/WarehouseOrderManage/ViewApprovedOrder.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
