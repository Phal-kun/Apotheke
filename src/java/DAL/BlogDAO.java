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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

/**
 *
 * @author ACER
 */
public class BlogDAO {

    // Singleton instance
    public static BlogDAO instance = new BlogDAO();
    private Connection connection;

    private BlogDAO() {
        if (instance == null) {
            connection = new DBContext().connect;
        } else {
            instance = this;
        }
    }

    public void createBlog(Blog blog) {
        String query = "INSERT INTO Blog (title, content, userID, publicDate, status) VALUES (?, ?, ?, GETDATE(), 1)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getUserID());

            stmt.executeUpdate();

            // Get the generated blog ID
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    blog.setBlogID(rs.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get all blogs
    public List<Blog> getAllBlogs() {
        List<Blog> blogs = new ArrayList<>();
        String query = "SELECT b.blogID, b.title, b.content, b.publicDate, b.userID, b.status FROM Blog b";

        try (PreparedStatement stmt = connection.prepareStatement(query); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int blogID = rs.getInt("blogID");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Date publicDate = rs.getDate("publicDate");
                int userID = rs.getInt("userID");
                boolean status = rs.getBoolean("status");

                Blog blog = new Blog(blogID, title, content, publicDate, userID, status);

                // Fetch tags for this blog
                List<Tag> tags = TagDAO.instance.getTagsForBlog(blogID);
                blog.setTags(tags);

                blogs.add(blog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blogs;
    }

    // Update a blog
    public void updateBlog(Blog blog) {
        // Update blog details
        String updateBlogQuery = "UPDATE Blog SET title = ?, content = ? WHERE blogID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(updateBlogQuery)) {
            stmt.setString(1, blog.getTitle());
            stmt.setString(2, blog.getContent());
            stmt.setInt(3, blog.getBlogID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Delete existing blog-tag relationships
        String deleteTagsQuery = "DELETE FROM blogTag WHERE blogID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(deleteTagsQuery)) {
            stmt.setInt(1, blog.getBlogID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Insert the new blog-tag relationships
        String insertTagQuery = "INSERT INTO blogTag (blogID, tagID) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(insertTagQuery)) {
            for (Tag tag : blog.getTags()) {
                stmt.setInt(1, blog.getBlogID());
                stmt.setInt(2, tag.getTagID());
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a blog by its ID
    public void deleteBlog(int blogID) {
        String deleteBlogTagQuery = "DELETE FROM blogTag WHERE blogID = ?";
        String deleteBlogQuery = "DELETE FROM blog WHERE blogID = ?";

        try (PreparedStatement psBlogTag = connection.prepareStatement(deleteBlogTagQuery); PreparedStatement psBlog = connection.prepareStatement(deleteBlogQuery)) {

            // First, delete the related entries in the blogTag table
            psBlogTag.setInt(1, blogID);
            psBlogTag.executeUpdate();

            // Then, delete the blog from the blog table
            psBlog.setInt(1, blogID);
            psBlog.executeUpdate();

            System.out.println("Blog and related tags deleted successfully.");

        } catch (SQLException e) {
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

    public Blog getBlogById(int blogID) {
        String sql = "SELECT * FROM Blog WHERE blogID = ?";
        Blog blog = null;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, blogID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    blog = new Blog();
                    blog.setBlogID(rs.getInt("blogID"));
                    blog.setTitle(rs.getString("title"));
                    blog.setContent(rs.getString("content"));
                    blog.setPublicDate(rs.getDate("publicDate"));
                    blog.setUserID(rs.getInt("userID"));
                    blog.setStatus(rs.getBoolean("status"));
                }
                List<Tag> tags = TagDAO.instance.getTagsForBlog(blogID);
                blog.setTags(tags);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return blog;
    }

    public static void main(String[] args) {
//        System.out.println(BlogDAO.instance.getAllBlogs());

        List<Blog> blogs = BlogDAO.instance.getAllBlogs();

        // Iterate through each blog and print its details along with tags
        for (Blog blog : blogs) {
            System.out.println("Blog ID: " + blog.getBlogID());
            System.out.println("Title: " + blog.getTitle());
            System.out.println("Content: " + blog.getContent());
            System.out.println("Public Date: " + blog.getPublicDate());
            System.out.println("User ID: " + blog.getUserID());
            System.out.println("Status: " + blog.isStatus());

            // Print associated tags
            List<Tag> tags = blog.getTags();
            if (tags != null && !tags.isEmpty()) {
                System.out.println("Tags:");
                for (Tag tag : tags) {
                    System.out.println("- " + tag.getTagName());
                }
            } else {
                System.out.println("No tags for this blog.");
            }
            System.out.println("-----------------------------");
        }
    }
}
