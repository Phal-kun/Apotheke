/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Blog;

import DAL.BlogDAO;
import DAL.TagDAO;
import Model.Blog.Blog;
import Model.Blog.Tag;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author ACER
 */
public class SearchBlogServlet extends HttpServlet {

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
            out.println("<title>Servlet SearchBlogServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SearchBlogServlet at " + request.getContextPath() + "</h1>");
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
        // Get the list of blogs filtered by keyword and tags
        String keyword = request.getParameter("keyword");
        List<Blog> blogList;
        TagDAO tagDAO = TagDAO.instance;  
        List<Tag> tagList = tagDAO.getAllTags();
        // Get selected tag IDs from the form
        String[] selectedTagIDs = request.getParameterValues("tag");

        if ((selectedTagIDs != null && selectedTagIDs.length > 0) || (keyword != null && !keyword.isBlank())) {
            // Pass the selected tag IDs to your DAO for filtering blogs
            blogList = TagDAO.instance.searchBlogsByKeywordAndTags(keyword,selectedTagIDs);
        } else {
            // If no tags are selected, just return all blogs
            blogList = BlogDAO.instance.getAllBlogs();
        }

        // Set the filtered blog list into the request scope
        request.setAttribute("blogList", blogList);
        request.setAttribute("tagList", tagList);
        // Forward the request back to BlogManager.jsp to display the results
        request.getRequestDispatcher("View/BlogManage/BlogManager.jsp").forward(request, response);
        
//
//        // Get the search keyword from the request
//        String keyword = request.getParameter("keyword");
//
//        // Get the selected tags from the request (tagFilter is an array of tag IDs)
//        String[] tagFilter = request.getParameterValues("tagFilter");
//
//        // DAO objects to retrieve blogs and tags
//        BlogDAO blogDAO = BlogDAO.instance;
//        TagDAO tagDAO = TagDAO.instance;
//
//        // Get the list of blogs filtered by keyword and tags
//        List<Blog> blogList;
//
//        if ((keyword == null || keyword.isEmpty()) && (tagFilter == null || tagFilter.length == 0)) {
//            // No filters applied, get all blogs
//            blogList = blogDAO.getAllBlogs();
//        } else {
//            // Search and filter blogs based on the keyword and tags
//            blogList = tagDAO.searchBlogsByKeywordAndTags(keyword, tagFilter);
//        }
//
//        // Get the list of all tags for the checkbox filter
//        List<Tag> tagList = tagDAO.getAllTags();
//
//        // Set the results and tag list in the request scope
//        request.setAttribute("blogList", blogList);
//        request.setAttribute("tagList", tagList);
//
//        // Preserve search criteria in the request scope for JSP
//        request.setAttribute("keyword", keyword);
//        request.setAttribute("tagFilter", tagFilter);
//
//        // Forward the request back to BlogManager.jsp to display the results
//        request.getRequestDispatcher("View/BlogManage/BlogManager.jsp").forward(request, response);
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
