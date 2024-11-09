/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Product.forCustomer;

import DAL.ProductDAO;
import Model.Product.Component;
import Model.Product.Product;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Dell
 */
public class ShowProductHomeServlet extends HttpServlet {
     
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         processRequest(request, response);
    } 

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Item> listItems = CartControl.displayProductList();
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
        session.setAttribute("listItems", listItems);
        session.setAttribute("itemsAtPageIndex", itemsAtPageIndex);
        session.setAttribute("groupCount", groupedItems.size());
        session.setAttribute("currentIndex", 0);
        request.getRequestDispatcher("View/Home.jsp").forward(request, response);
    } 
      
    private List<Item> getItemsByIndex(int index) {
        List<Item> listItems = CartControl.displayProductList();

        // Chia danh sách thành các nhóm 12 item
        List<List<Item>> groupedItems = CartControl.splitItemsIntoGroups(listItems, 12);
        // size
        int groupCount = groupedItems.size();
        List<Item> itemsAtIndex1 = groupedItems.get(index); 
        return itemsAtIndex1;
    }
    
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String pageIndexParam = request.getParameter("pageIndex");
        try{
            int index = Integer.parseInt(pageIndexParam);
            List<Item> itemsAtPageIndex = getItemsByIndex(index);
            HttpSession session = request.getSession();
            session.setAttribute("itemsAtPageIndex", itemsAtPageIndex);
            // Lưu index hiện tại để sử dụng cho các nút điều hướng
            session.setAttribute("currentIndex", index);
            // Chuyển hướng đến trang JSP để hiển thị danh sách item
            request.getRequestDispatcher("View/Home.jsp").forward(request, response);
        }catch(Exception e){
             e.printStackTrace(); 
        }
            
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    
    
    
}
