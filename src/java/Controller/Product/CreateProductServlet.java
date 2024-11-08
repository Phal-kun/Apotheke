/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Product;

import DAL.ProductDAO;
import Model.Product.*;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class CreateProductServlet extends HttpServlet {

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
            out.println("<title>Servlet createProductServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet createProductServlet at " + request.getContextPath() + "</h1>");
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
        ArrayList<Category> categoryList = ProductDAO.INSTANCE.loadCategoryList();
        request.setAttribute("categoryList", categoryList);

        ArrayList<Origin> originList = ProductDAO.INSTANCE.loadOriginList();
        request.setAttribute("originList", originList);

        RequestDispatcher rd = request.getRequestDispatcher("View/ProductManage/CreateProduct.jsp");
        rd.forward(request, response);
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
        
        String productIDStr = request.getParameter("productID");
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String componentDescription = request.getParameter("componentDescription");
        String categoryIDStr = request.getParameter("categoryID");
        String originIDStr = request.getParameter("originID");
        String manufacturer = request.getParameter("manufacturer");
        
//        String baseUnitName = request.getParameter("baseUnitName");
        
        String[] unitNames = request.getParameterValues("unitName");
        String[] convertRates = request.getParameterValues("convertRate");
        String[] componentNames = request.getParameterValues("componentName");
        String[] quantities = request.getParameterValues("quantity");
        String[] componentUnits = request.getParameterValues("componentUnit");

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder("Creare Product Failed: ");

        if (productName == null || productName.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Product Name missing");
        }

        int productID=0, categoryID = 0, originID = 0;

        try {
            if (productIDStr != null) {
                productID = Integer.parseInt(productIDStr);
            }
            if (categoryIDStr != null) {
                categoryID = Integer.parseInt(categoryIDStr);
            }
            if (originIDStr != null) {
                originID = Integer.parseInt(originIDStr);
            }
        } catch (NumberFormatException e) {
            hasError = true;
            errorMsg.append("Invalid ID format ");
        }

        if (componentNames == null || componentNames.length == 0) {
            hasError = true;
            errorMsg.append("Components missing");
        }
        
        if (productID!=0 && ProductDAO.INSTANCE.isExist(productID)) {
            hasError = true;
            errorMsg.append("Product ID is already exists!");
        }

        if (hasError) {
            request.setAttribute("errorMsg", errorMsg.toString());
            request.setAttribute("productName", productName);
            request.setAttribute("description", description);
            request.setAttribute("categoryID", categoryID);
            request.setAttribute("originID", originID);
            request.setAttribute("componentNames", componentNames);
            request.setAttribute("quantities", quantities);
            request.setAttribute("createMsg", false);
        } else {
            StringBuilder unitString = new StringBuilder();
//            unitString.append(baseUnitName).append(",")
//                        .append("1.00")
//                        .append(";");
            for (int i = 0; i < unitNames.length; i++) {
                unitString.append(unitNames[i])
                        .append(",")
                        .append(convertRates[i]+".00")
                        .append(";");
            }

            StringBuilder componentString = new StringBuilder();
            for (int i = 0; i < componentNames.length; i++) {
                componentString.append(componentNames[i])
                        .append(",")
                        .append(quantities[i])
                        .append(",")
                        .append(componentUnits[i])
                        .append(";");
            }

            ProductDAO.INSTANCE.insertProduct(productID, productName, categoryID, originID, manufacturer, description, componentDescription, unitString.toString(), componentString.toString());

        }

        doGet(request, response);
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
