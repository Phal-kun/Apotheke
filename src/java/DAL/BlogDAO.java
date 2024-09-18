/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.Blog.Blog;
import Model.Blog.Tag;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
/**
 *
 * @author ACER
 */
public class BlogDAO extends DBContext {
    // Singleton instance
    public static BlogDAO instance = new BlogDAO();
    private Connection connection;

    // Constructor
    public BlogDAO() {
        // Initialize connection
        this.connection = connect;
    }

    // Get connection method
    public Connection getConnection() {
        return this.connection;
    }

    // Add a new blog
    public void addBlog(Blog blog) {
        String sql = "INSERT INTO Blog (title, content, publicDate, userID, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, blog.getTitle());
            st.setString(2, blog.getContent());
            st.setDate(3, new java.sql.Date(blog.getPublicDate().getTime()));
            st.setInt(4, blog.getUserID());
            st.setBoolean(5, blog.isStatus());
            
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all blogs
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String sql = "SELECT * FROM Blog";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("blogID"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setPublicDate(rs.getDate("publicDate"));
                blog.setUserID(rs.getInt("userID"));
                blog.setStatus(rs.getBoolean("status"));
                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // Update a blog
    public void updateBlog(Blog blog) {
        String sql = "UPDATE Blog SET title = ?, content = ?, userID = ?, status = ? WHERE blogID = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, blog.getTitle());
            st.setString(2, blog.getContent());
            st.setDate(3, new java.sql.Date(blog.getPublicDate().getTime()));
            st.setInt(4, blog.getUserID());
            st.setBoolean(5, blog.isStatus());
            st.setInt(6, blog.getBlogID());
            
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Add a new tag
    public void addTag(Tag tag) {
        String sql = "INSERT INTO Tag (tagName) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, tag.getTagName());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all tags
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        String sql = "SELECT * FROM Tag";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            
            while (rs.next()) {
                Tag tag = new Tag();
                tag.setTagID(rs.getInt("tagID"));
                tag.setTagName(rs.getString("tagName"));
                tags.add(tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    // Update a tag
    public void updateTag(Tag tag) {
        String sql = "UPDATE Tag SET tagName = ? WHERE tagID = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setString(1, tag.getTagName());
            st.setInt(2, tag.getTagID());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a tag
    public void deleteTag(int tagID) {
        String sql = "DELETE FROM Tag WHERE tagID = ?";
        try (Connection conn = getConnection();
             PreparedStatement st = conn.prepareStatement(sql)) {
            
            st.setInt(1, tagID);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Method to delete a blog by its ID
    public void deleteBlog(int blogID) {
        String query = "DELETE FROM blog WHERE blogID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, blogID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to toggle the status of a blog
    public void toggleBlogStatus(int blogID) {
        String query = "UPDATE blog SET status = CASE WHEN status = 1 THEN 0 ELSE 1 END WHERE blogID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, blogID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        System.out.println(BlogDAO.instance.getAllBlogs());
    }
}
