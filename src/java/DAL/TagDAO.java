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

/**
 *
 * @author ACER
 */
public class TagDAO {

    // Singleton instance
    public static TagDAO instance = new TagDAO();
    private Connection connection;

    private TagDAO() {
        if (instance == null) {
            connection = new DBContext().connect;
        } else {
            instance = this;
        }
    }

    // Add a new tag
    public void addTag(Tag tag) {
        String sql = "INSERT INTO Tag (tagName) VALUES (?)";
        try (
                PreparedStatement st = connection.prepareStatement(sql)) {

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
        try (
                PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {

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
        try (
                PreparedStatement st = connection.prepareStatement(sql)) {

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
        try (
                PreparedStatement st = connection.prepareStatement(sql)) {

            st.setInt(1, tagID);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Tag> getTagsForBlog(int blogID) {
        List<Tag> tags = new ArrayList<>();
        String query = "SELECT t.tagID, t.tagName FROM Tag t JOIN blogTag bt ON t.tagID = bt.tagID WHERE bt.blogID = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, blogID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int tagID = rs.getInt("tagID");
                    String tagName = rs.getString("tagName");
                    tags.add(new Tag(tagID, tagName));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tags;
    }

    public void updateBlogTags(int blogID, List<Tag> tags) {
        // First, delete the existing tags for the blog
        String deleteSQL = "DELETE FROM blogTag WHERE blogID = ?";
        try (PreparedStatement deleteStmt = connection.prepareStatement(deleteSQL)) {
            deleteStmt.setInt(1, blogID);
            deleteStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Then, insert the new tags
        String insertSQL = "INSERT INTO blogTag (blogID, tagID) VALUES (?, ?)";
        try (PreparedStatement insertStmt = connection.prepareStatement(insertSQL)) {
            for (Tag tag : tags) {
                insertStmt.setInt(1, blogID);
                insertStmt.setInt(2, tag.getTagID());
                insertStmt.addBatch();
            }
            insertStmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addTagToBlog(int blogID, int tagID) {
        String query = "INSERT INTO blogTag (blogID, tagID) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, blogID);
            stmt.setInt(2, tagID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Blog> searchBlogsByKeywordAndTags(String keyword, String[] tagFilter) {
        List<Blog> blogs = new ArrayList<>();
        
        StringBuilder sql = new StringBuilder("SELECT * FROM Blog WHERE 1=1 ");
        
        // Add keyword filter
        if (keyword != null && !keyword.isEmpty()) {
            sql.append("AND title LIKE ? ");
        }
        
        // Add tag filter if tags are selected
        if (tagFilter != null && tagFilter.length > 0) {
            sql.append("AND blogID IN (SELECT blogID FROM BlogTag WHERE tagID IN (");
            for (int i = 0; i < tagFilter.length; i++) {
                sql.append("?");
                if (i < tagFilter.length - 1) {
                    sql.append(", ");
                }
            }
            sql.append("))");
        }

        try {
            PreparedStatement ps = connection.prepareStatement(sql.toString());
            
            int paramIndex = 1;
            
            // Set the keyword parameter
            if (keyword != null && !keyword.isEmpty()) {
                ps.setString(paramIndex++, "%" + keyword + "%");
            }
            
            // Set tag filter parameters
            if (tagFilter != null && tagFilter.length > 0) {
                for (String tagID : tagFilter) {
                    ps.setInt(paramIndex++, Integer.parseInt(tagID));
                }
            }

            ResultSet rs = ps.executeQuery();
            
            // Process result set to create Blog objects
            while (rs.next()) {
                Blog blog = new Blog();
                blog.setBlogID(rs.getInt("blogID"));
                blog.setTitle(rs.getString("title"));
                blog.setContent(rs.getString("content"));
                blog.setPublicDate(rs.getDate("publicDate"));
                blog.setUserID(rs.getInt("userID"));
                blog.setStatus(rs.getBoolean("status"));
                blog.setTags(getTagsForBlog(blog.getBlogID())); // Retrieve associated tags
                blogs.add(blog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return blogs;
    }

    public static void main(String[] args) {
        System.out.println(TagDAO.instance.getAllTags());
        System.out.println(BlogDAO.instance.getBlogById(7));
        String[] testTag = {"1","4"};
        System.out.println(TagDAO.instance.searchBlogsByKeywordAndTags("so", testTag));
    }
}
