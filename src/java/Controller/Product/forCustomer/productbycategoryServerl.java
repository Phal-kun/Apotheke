/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Product.forCustomer;

import Model.Product.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dell
 */
public class productbycategoryServerl extends HttpServlet {
   
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
            out.println("<title>Servlet productbycategoryServerl</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet productbycategoryServerl at " + request.getContextPath () + "</h1>");
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
        String categoryID = request.getParameter("categoryID");
        System.out.println("Category"+categoryID);
        try{
            int cate = Integer.parseInt(categoryID);
            
             List<Item> listItems = CartControl.displayProductList2(cate);
            // Chia danh sách thành các nhóm 12 item
            List<List<Item>> groupedItems = CartControl.splitItemsIntoGroups(listItems, 12);
            // size
            int groupCount = groupedItems.size();
            List<Item> itemsAtPageIndex = getItemsByIndex(0);
             for (Item item : itemsAtPageIndex) {
                    System.out.println(item.toString()); // In thông tin của từng item
             }
            System.out.print("so size cua ban la "+ groupedItems.size());
            HttpSession session = request.getSession();
            
            session.removeAttribute("listItems");
            session.removeAttribute("itemsAtPageIndex");
            session.removeAttribute("groupCount");
            session.removeAttribute("currentIndex");

            // Thêm các thuộc tính mới vào session
            session.setAttribute("listItems", listItems);
            session.setAttribute("itemsAtPageIndex", itemsAtPageIndex);
            session.setAttribute("groupCount", groupCount);
            session.setAttribute("currentIndex", 0);
    
           
            request.getRequestDispatcher("View/Home.jsp").forward(request, response);
  
        }catch(Exception e){
            
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
    private List<Item> getItemsByIndex(int index) {
        List<Item> listItems = CartControl.displayProductList();

        // Chia danh sách thành các nhóm 12 item
        List<List<Item>> groupedItems = CartControl.splitItemsIntoGroups(listItems, 12);
        // size
        int groupCount = groupedItems.size();
        List<Item> itemsAtIndex1 = groupedItems.get(index); 
        return itemsAtIndex1;
    }
    
}
