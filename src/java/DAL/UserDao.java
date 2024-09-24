/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Dell
 */
public class UserDao extends DBContext{
    java.sql.Connection con= null;
    PreparedStatement ps = null;
    ResultSet rs = null;
     public User getUserByEmail(String email) throws SQLException, Exception {
        String sql = "SELECT * FROM [user] WHERE username = ?";
            try {
                con = getConnection();
                ps = con.prepareStatement(sql);
                ps.setString(1,email);
                rs =ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserID(rs.getInt("userID"));
                user.setFullname(rs.getString("fullname"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setGender(rs.getString("gender"));
                user.setRole(rs.getInt("role"));
                user.setStatus(rs.getBoolean("status"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                return user;
            }else{
                return null;
            }
        } catch (Exception e) {
           throw e;
        }finally{
            closeResultSet(rs);
            closePreparedStatement(ps);
            closeConnection(con);
        }
       }

    public void saveUser(User user) {
         String sql = "INSERT INTO [user] (fullname, username, gender, role, status, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            con = getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getFullname());
            ps.setString(2, user.getUsername());
            ps.setString(3, user.getGender());
            ps.setInt(4, user.getRole());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getAddress());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public User login(String name,String pass){
        String query = "select * from [User] where username = ? and password= ?";
        try{
            con = new DBContext().getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1,name);
            ps.setString(2,pass);
            rs = ps.executeQuery();
            while( rs.next()){
                return new User(rs.getInt("userID"),
                            rs.getString("fullname"),
                            rs.getString("phone"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("gender"),
                            rs.getBoolean("status"),
                            rs.getInt("role"),
                            rs.getString("address"));
                            }
        }catch(Exception e){           
             e.printStackTrace();
        }finally {
        // Close resources in a finally block to ensure they are closed regardless of success or failure
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (Exception e) {
            e.printStackTrace(); // Print the stack trace for debugging
        }
    }
        return null;
    }
}
