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

/**
 *
 * @author Admin
 */
public class UpdateProductServlet extends HttpServlet {
   
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
            out.println("<title>Servlet updateProductServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateProductServlet at " + request.getContextPath () + "</h1>");
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
        try {
            int updateProductID = Integer.parseInt(request.getParameter("productID"));
            Product updateProduct = ProductDAO.INSTANCE.findProductByID(updateProductID);
            request.setAttribute("updateProduct", updateProduct);
        } catch (Exception e){
            response.sendRedirect("ListProduct");
        }
        ArrayList<Category> categoryList = ProductDAO.INSTANCE.loadCategoryList();
        request.setAttribute("categoryList", categoryList);

        ArrayList<Origin> originList = ProductDAO.INSTANCE.loadOriginList();
        request.setAttribute("originList", originList);
       
        request.getRequestDispatcher("View/ProductManage/UpdateProduct.jsp").forward(request, response);
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
        String productIDStr = request.getParameter("productID");
        String productName = request.getParameter("productName");
        String description = request.getParameter("description");
        String categoryIDStr = request.getParameter("categoryID");
        String originIDStr = request.getParameter("originID");
        String manufacturerIDStr = request.getParameter("manufacturerID");
        String formIDStr = request.getParameter("formID");
        String[] componentIDs = request.getParameterValues("componentID");
        String[] quantities = request.getParameterValues("quantity");

        boolean hasError = false;
        StringBuilder errorMsg = new StringBuilder("Please fill in the following fields: ");

        if (productName == null || productName.trim().isEmpty()) {
            hasError = true;
            errorMsg.append("Product Name ");
        }
        int productID = 0 ,categoryID = 0, originID = 0, manufacturerID = 0, formID = 0;

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
            if (manufacturerIDStr != null) {
                manufacturerID = Integer.parseInt(manufacturerIDStr);
            }
        } catch (NumberFormatException e) {
            hasError = true;
            errorMsg.append("Invalid ID format ");
        }

        if (componentIDs == null || componentIDs.length == 0) {
            hasError = true;
            errorMsg.append("Components ");
        }

        if (hasError) {
            request.setAttribute("errorMsg", errorMsg.toString());
            request.setAttribute("productName", productName);
            request.setAttribute("description", description);
            request.setAttribute("categoryID", categoryID);
            request.setAttribute("originID", originID);
            request.setAttribute("manufacturerID", manufacturerID);

            request.setAttribute("componentIDs", componentIDs);
            request.setAttribute("quantities", quantities);
            request.setAttribute("createMsg", false);

            request.setAttribute("showAlert", true);
            request.setAttribute("alertMessage", "Update Fail!");
            doGet(request, response);

        } else {

            ArrayList<Component> components = new ArrayList<>();
            for (int i = 0; i < componentIDs.length; i++) {
                int componentID = Integer.parseInt(componentIDs[i]);
                int quantity = Integer.parseInt(quantities[i]);
                components.add(new Component(null, null, quantity));
            }

//            ProductDAO.INSTANCE.updateProduct(productID, productName, categoryID, originID, manufacturerID, formID, description, components);

            response.setContentType("text/html");
            response.getWriter().println("<script>alert('Update successfully!'); window.location.href = 'ListProduct';</script>");

        }
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
