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
public class updateCartServelet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static final long serialVersionUID = 1L;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet updateCartServelet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet updateCartServelet at " + request.getContextPath () + "</h1>");
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
        String k = request.getParameter("k");
        String indexValue = k.replaceAll(".*index:(\\d+).*", "$1"); // Lấy giá trị index
        String priceValue = k.replaceAll(".*price(\\d+).*", "$1");  // Lấy giá trị price
        String quantityValue = k.replaceAll(".*quantity:(\\d+).*", "$1"); // Lấy giá trị quantity

        // Chuyển các giá trị về kiểu số nếu cần
        int index = Integer.parseInt(indexValue);
        double price = Double.parseDouble(priceValue);
        int quantity = Integer.parseInt(quantityValue);
        
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            List<Item> items = cart.getListItems();
            if (items != null && !items.isEmpty()) {
                items.get(index).setPrice((float)price);
                items.get(index).setQuantity(quantity);
                
                System.out.println(items.get(index).toString());
            }    
        
        }
        
        session.setAttribute("cart", cart);

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
        String k = request.getParameter("k");
        System.out.println(k);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart != null) {
            try {
                // Chuyển 'k' thành kiểu int
                int index = Integer.parseInt(k);

                // Kiểm tra xem index có nằm trong phạm vi của listItems không
                if (index >= 0 && index < cart.getListItems().size()) {
                    // Gọi phương thức removeItem để xóa mục khỏi giỏ hàng theo index
                    cart.removeItem(index);
                    System.out.println("Item at index " + index + " removed successfully.");
                    
                    
                    
                } else {
                    System.out.println("Index out of range.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid index format: " + k);
            }
        
        }
        session.setAttribute("cart", cart);
        session.setAttribute("number", cart.coutn());
        // Chuyển hướng người dùng đến cart.jsp
        request.getRequestDispatcher(request.getContextPath() + "/View/pagecontrol/cart.jsp").forward(request, response);
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
