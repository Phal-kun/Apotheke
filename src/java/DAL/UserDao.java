/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;


import Model.User.Role;
import Model.User.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author hieppd
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
                    Role role = new Role();
                    role.setRoleID(rs.getInt("role"));
                user.setRole(role);
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
            ps.setInt(4, user.getRole().getRoleID());
            ps.setBoolean(5, user.isStatus());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getAddress());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public User login(String name) {
    String query = "SELECT * FROM [User] WHERE username = ?";
    try {
        con = new DBContext().getConnection();
        ps = con.prepareStatement(query);
        ps.setString(1, name);
        
        rs = ps.executeQuery();
        
        if (rs.next()) {
           
            Role role = new Role();
            role.setRoleID(rs.getInt("role"));

           
            User user = new User();
            
            
            user.setUserID(rs.getInt("userID"));
            user.setFullname(rs.getString("fullname"));
            user.setPhone(rs.getString("phone"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setGender(rs.getString("gender"));
            user.setStatus(rs.getBoolean("status"));
            user.setRole(role);
            user.setAddress(rs.getString("address"));
            
            return user;
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        // Ensure resources are closed in the finally block
                try {
                    if (rs != null) rs.close();
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
    }
    
    
    public User getUserByNameUserPass(String name, String pass, String fulname){
        String sql = "select * from [User] where username =? and password =? and fullname =?";
        try{
            con = new DBContext().getConnection();
            ps= con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2,pass);
            ps.setString(3, fulname);
            rs = ps.executeQuery();
            while( rs.next()){
                 Role role = new Role();
                role.setRoleID(rs.getInt("role"));
                
                return new User(rs.getInt("userID"),
                            rs.getString("fullname"),
                            rs.getString("phone"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("gender"),
                            rs.getBoolean("status"),
                            role,
                            rs.getString("address"));
                            }
        }catch(Exception e){           
             e.printStackTrace();
        }finally {
        
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
    
    public void saveUserByUsername(User user) {
            String sql = "INSERT INTO [user] (fullname, username, password, gender, role, status, phone, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try {
                con = getConnection();
                PreparedStatement ps = con.prepareStatement(sql);

                // Set các giá trị cho PreparedStatement
                ps.setString(1, user.getFullname());
                ps.setString(2, user.getUsername());

                // Mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
                  ps.setString(3, user.getPassword());

                ps.setString(4, user.getGender());
                
                ps.setInt(5, user.getRole().getRoleID()); 
                ps.setBoolean(6, user.isStatus());
                ps.setString(7, user.getPhone());
                ps.setString(8, user.getAddress());

                // Thực thi câu lệnh
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                // Đảm bảo đóng kết nối sau khi thực hiện xong
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        public String hashPassword(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt(12));
        }



}