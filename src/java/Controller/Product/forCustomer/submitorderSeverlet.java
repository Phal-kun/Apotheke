/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.Product.forCustomer;

import DAL.OrderDao;
import DAL.UserDao;
import Model.Order.Order;
import Model.Order.Status;
import Model.User.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class submitorderSeverlet extends HttpServlet {
   
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
            out.println("<title>Servlet submitorderSeverlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet submitorderSeverlet at " + request.getContextPath () + "</h1>");
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
        HttpSession session = request.getSession(false); // false: không tạo session mới nếu chưa tồn tại

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        User account = (session != null) ? (User) session.getAttribute("account") : null;

         if (account == null) {
            System.out.println("hiepday");
            
            Cart cart = (Cart) session.getAttribute("cart");

            int totalQuantity = 0;
            float totalrice = 0.0f;

            if (cart != null && cart.getListItems() != null) {
                for (Item item : cart.getListItems()) {
                    float price = item.getPrice();
                    totalrice += price;
                }
            } else {
                System.out.println("Giỏ hàng trống.");
            }
            
            // Nếu chưa có tài khoản trong session, bạn có thể trả về thông báo hoặc redirect tới trang login
            request.setAttribute("loginMessage", "Vui lòng đăng nhập để tiếp tục."); // Thiết lập thông báo
            request.getRequestDispatcher("View/pagecontrol/cart.jsp").forward(request, response); // Chuyển hướng đến trang JSP
        } else {
             
            System.out.println(account.toString()); 
            String h= "edasjkldhsadbas";
            session.setAttribute("formsubmit", h); 
            request.getRequestDispatcher("View/pagecontrol/cart.jsp").forward(request, response);
      
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
        String ordererName = request.getParameter("ordererName");
        String ordererPhone = request.getParameter("ordererPhone");
        String ordererEmail = request.getParameter("ordererEmail");
        String receiverName = request.getParameter("receiverName");
        String receiverPhone = request.getParameter("receiverPhone");
        String address = request.getParameter("address");
        String currentTime = request.getParameter("currentTime");  // Lấy giá trị thời gian từ trường hidden
        System.out.println("Orderer Name: " + ordererName);
        System.out.println("Orderer Phone: " + ordererPhone);
        System.out.println("Orderer Email: " + ordererEmail);
        System.out.println("Receiver Name: " + receiverName);
        System.out.println("Receiver Phone: " + receiverPhone);
        System.out.println("Address: " + address);
        System.out.println("Current Time: " + currentTime);
        HttpSession session = request.getSession();
        User userAccount = (User) request.getSession().getAttribute("account");
        UserDao userdao = new UserDao();
       
        Cart cart = (Cart) session.getAttribute("cart");
        
        int totalQuantity = 0;
        float totalPrice = 0.0f;

        if (cart != null && cart.getListItems() != null) {
            for (Item item : cart.getListItems()) {
                float price = item.getPrice();
                totalPrice += price;
            }
        } else {
            System.out.println("Giỏ hàng trống.");
        }
        
        if (userAccount.getPhone() == null || userAccount.getPhone().isEmpty()) {
                try {
                    userdao.updateUser2(userAccount.getUsername(), ordererPhone);
                } catch (Exception ex) {
                    Logger.getLogger(submitorderSeverlet.class.getName()).log(Level.SEVERE, null, ex);
                }
        }else if(userAccount.getAddress() == null || userAccount.getAddress().isEmpty()){
                try { 
                    userdao.updateUser3(userAccount.getUsername(), address);
                } catch (Exception ex) {
                    Logger.getLogger(submitorderSeverlet.class.getName()).log(Level.SEVERE, null, ex);
                }
           
        }else if(userAccount.getPhone() == null || userAccount.getPhone().isEmpty()||userAccount.getAddress() == null || userAccount.getAddress().isEmpty()){
                try {
                    userdao.updateUser1(userAccount.getUsername(), userAccount.getFullname(), ordererPhone, address);
                    User user = userdao.getUserByEmail(userAccount.getUsername());
                } catch (Exception ex) {
                    Logger.getLogger(submitorderSeverlet.class.getName()).log(Level.SEVERE, null, ex);
                }    
        }else{
           System.out.println("hideosa");
        }
        
        
        
        try {
            User user = userdao.getUserByEmail(userAccount.getUsername());
            System.out.println(user.toString());
                Order newOrder = new Order();
                newOrder.setUser(user);  // Gán user cho đơn hàng
                newOrder.setOrderDate(new Date(System.currentTimeMillis())); 
                // Nếu đơn hàng chưa hoàn thành, để null
                Status status = new Status();
                status.setStatusID(1); 
                newOrder.setStatus(status);  // Ví dụ, trạng thái đơn hàng là "Chưa xử lý" (1)
                newOrder.setTotalPrice(totalPrice);  // Tổng giá trị đơn hàng là 150
                newOrder.setShipName("");
                newOrder.setShipAddress(user.getAddress());
                newOrder.setShipPhone(receiverPhone);
                newOrder.setShipNote("Leave the package at the door.");
                newOrder.setRejectReason(null);  // Nếu không có lý do hủy, để null
                
                OrderDao orderDAO = new OrderDao();  // Giả sử đây là lớp chứa hàm insertOrder
                int orderid = orderDAO.insertOrder(newOrder);
                System.out.println("so vua nhap" + orderid); 
                
                // inser order detail 
                if (cart != null && cart.getListItems() != null) {
                    for (Item item : cart.getListItems()) {
                        String name = (String) item.listPrice.get(0).get("name");  
                        double price = (double)item.listPrice.get(0).get("price");
                        int unitid = orderDAO.getUnitIdByProductIdAndProductName(item.getProductID(), name);
                        
                        orderDAO.insertOrderDetail(orderid,item.getProductID(),orderDAO.getProductDetailIdByUnitId(unitid),unitid,new BigDecimal(price),item.getQuantity());
                        System.out.println("thanh cong");
                    }
                } 
                session.removeAttribute("cart");
                session.removeAttribute("formsubmit");
                 session.removeAttribute("number");
                request.setAttribute("Notofication", "Đơn hàng của bạn đang được phê duyệt.");
                request.getRequestDispatcher("View/pagecontrol/cart.jsp").forward(request, response);  
            
        } catch (Exception ex) {
            Logger.getLogger(submitorderSeverlet.class.getName()).log(Level.SEVERE, null, ex);
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
