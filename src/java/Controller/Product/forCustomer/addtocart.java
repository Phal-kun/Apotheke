/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Product.forCustomer;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;

/**
 *
 * @author Dell
 */
public class addtocart extends HttpServlet {
   
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
            out.println("<title>Servlet addtocart</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet addtocart at " + request.getContextPath () + "</h1>");
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
        String productID = request.getParameter("productID");
        String productName = request.getParameter("productName");
        String productDescription = request.getParameter("productDescription");
        String productPrice = request.getParameter("productPrice");
        String quantity = request.getParameter("quantity");
        // tien + don vi 
        String[] result = processString(productPrice);
        String price = result[0];
        String name = result[1];
       Item item = new Item();
        item.setProductID(Integer.parseInt(productID));
        item.setProductName(productName);
        item.setDescription(productDescription);
        item.setQuantity(Integer.parseInt(quantity));
        item.setPrice(Float.parseFloat(price));
        item.addPrice(name, Float.parseFloat(price));
        
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        
        if (cart == null) {
            cart = new Cart(); // Khởi tạo Cart mới nếu chưa có trong session
        }
        // Thêm item vào Cart
        cart.addItem(item);
        // debug
        cart.consolidateCart();
        cart.printItems();
        

        // Cập nhật lại Cart trong session
        cart.coutn();
        session.setAttribute("number", cart.coutn());
        session.setAttribute("cart", cart);
       
        request.getRequestDispatcher("View/Home.jsp").forward(request, response);
                      
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
        String productID = request.getParameter("productID");
        String selectedPrice = request.getParameter("selectedPrice");
        String selectedOption = request.getParameter("selectedOption");
        String quantity = request.getParameter("quantity");

        // Chuyển đổi các giá trị thành kiểu dữ liệu cần thiết (nếu cần)
//        int prodID = Integer.parseInt(productID);
//        double price = Double.parseDouble(selectedPrice);
//        int qty = Integer.parseInt(quantity);
        System.out.println("Product ID: " + productID);
        System.out.println("Selected Price: " + selectedPrice);
        System.out.println("Selected Option: " + selectedOption);
        System.out.println("Quantity: " + quantity);

    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
     public static String[] processString(String input) {
    // Tìm vị trí của dấu "/"
    int slashIndex = input.indexOf("/");

    // Nếu không tìm thấy dấu "/", trả về chuỗi ban đầu cho phần đầu tiên
    if (slashIndex == -1) {
        return new String[]{input.trim(), ""};
    }

    // Lấy phần trước dấu "/"
    String firstPart = input.substring(0, slashIndex).trim();
    String secondPart = input.substring(slashIndex + 1).trim();

    // Tìm vị trí dấu "." trong firstPart
    int dotIndex = firstPart.indexOf(".");

    // Nếu có dấu ".", lấy phần trước dấu "."
    if (dotIndex != -1) {
        firstPart = firstPart.substring(0, dotIndex).trim();
    }

    // Trả về mảng chứa hai chuỗi
    return new String[]{firstPart, secondPart};
}
     public int calculateTotalQuantity(List<Item> cart) {
    int totalQuantity = 0;
    for (Item item : cart) {
        totalQuantity += item.getQuantity();
    }
    return totalQuantity;
}
}
