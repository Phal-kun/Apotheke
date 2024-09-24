/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Blog;

import DAL.BlogDAO;
import DAL.TagDAO;
import Model.Blog.Blog;
import Model.Blog.Tag;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ACER
 */
public class EditBlogServlet extends HttpServlet {

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
            out.println("<title>Servlet EditServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet EditServlet at " + request.getContextPath() + "</h1>");
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
        // Get blogID from request
        int blogID = Integer.parseInt(request.getParameter("blogID"));

        // Fetch blog by ID
        Blog blog = BlogDAO.instance.getBlogById(blogID);
        List<Tag> tagList = TagDAO.instance.getAllTags();

        // Set blog as request attribute and forward to edit page
        request.setAttribute("blog", blog);
        request.setAttribute("tagList", tagList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("View/BlogManage/UpdatePost.jsp");
        dispatcher.forward(request, response);
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
        // Get parameters from the form
        int blogID = Integer.parseInt(request.getParameter("blogID"));
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    int userID = Integer.parseInt(request.getParameter("userID"));

    // Fetch the selected tags from the form (checkboxes)
    String[] selectedTags = request.getParameterValues("tagUpdate");

    // Parse and store the selected tags in a list
    List<Tag> tagList = new ArrayList<>();
    if (selectedTags != null) {
        for (String tagID : selectedTags) {
            int tagIdInt = Integer.parseInt(tagID);
            tagList.add(new Tag(tagIdInt, null));  // assuming Tag has a constructor that takes ID
        }
    }

    // Fetch the blog from DB (if needed) and update its attributes
    Blog blog = new Blog(blogID, title, content, userID, true); // Assuming the status remains unchanged
    blog.setTags(tagList); // Assuming the Blog class has a setTags() method

    // Update the blog in the database
    BlogDAO.instance.updateBlog(blog);

    // Redirect after update
    response.sendRedirect("BlogManager");
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
