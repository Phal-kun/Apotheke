/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import Model.User.*;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ASUS
 */
public class DAOUserList {

    public static DAOUserList INSTANCE = new DAOUserList();
    private Connection con;
    private String status = "OK";

    private DAOUserList() {
        if (INSTANCE == null) {
            con = new DBContext().connect;
        } else {
            INSTANCE = this;
        }
    }

    public void loadDB() {
        System.out.println("Loading data...");
        String sql = "select * from [Role]";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int roleid = rs.getInt("roleid");
                    String rolename = rs.getString("rolename");
                    int permision = rs.getInt("permission");
                    System.out.println(roleid + " - " + rolename + " - " + permision);
                }
            }
        } catch (SQLException e) {
            status = "Error at read Account " + e.getMessage();
            System.out.println(status);
        }
    }

    public ArrayList<Role> getRole() {
        try {
            String sql = """
            SELECT 
                *
            FROM 
                [role]        
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            return toArrRole(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<User> getUser(int page, boolean sort, String sortCol, boolean isCustomer, String keyword, String filterArr[]) {
        try {
            String sql = """
        SELECT 
            *
        FROM 
            [User] u JOIN [role] r ON u.roleID = r.roleID
        WHERE
            %s
        ORDER BY
            %s
        OFFSET ? ROWS
        FETCH NEXT 10 ROWS ONLY;
        """;

            // Set up the base WHERE clause
            StringBuilder condition = new StringBuilder(isCustomer ? "u.roleID = 1" : "u.roleID != 1");

            // Add search condition
            if (keyword != null && !keyword.isEmpty()) {
                condition.append(" AND (u.fullname LIKE ? OR u.username LIKE ? OR u.address LIKE ? OR u.phone LIKE ?)");
            }

            // Add filter conditions    
            StringBuilder filterCondition = new StringBuilder();
            if (filterArr != null && filterArr.length > 0) {
                filterCondition.append(" AND (");
                for (String filter : filterArr) {
                    switch (filter) {
                        case "male" ->
                            filterCondition.append(" OR u.gender = 'male'");
                        case "female" ->
                            filterCondition.append(" OR u.gender = 'female'");
                        case "warehouse" ->
                            filterCondition.append(" OR u.role = 2");
                        case "sale" ->
                            filterCondition.append(" OR u.role = 3");
                        case "marketing" ->
                            filterCondition.append(" OR u.role = 4");
                        case "active" ->
                            filterCondition.append(" OR u.status = 1");
                        case "inactive" ->
                            filterCondition.append(" OR u.status = 0");
                    }
                }
                filterCondition = new StringBuilder(filterCondition.toString().replaceFirst(" OR", "")).append(")");
            }
            condition.append(filterCondition);
            // Set up sort order
            String order = sortCol + (sort ? " ASC" : " DESC");

            // Format the SQL string
            sql = String.format(sql, condition.toString(), order);

            PreparedStatement statement = con.prepareStatement(sql);

            // Set the search parameters if keyword exists
            int paramIndex = 1;
            if (keyword != null && !keyword.isEmpty()) {
                String keywordPattern = "%" + keyword + "%";
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
                statement.setString(paramIndex++, keywordPattern);
            }

            // Set pagination offset
            statement.setInt(paramIndex, (page - 1) * 10);

            ResultSet rs = statement.executeQuery();
            return toArrUser(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public int getTotalPages(boolean isCustomer, String keyword, String[] filterArr) {
        try {
            String countSql = """
            SELECT COUNT(*)
            FROM [User] u JOIN [role] r ON u.roleID = r.roleID
            WHERE %s
        """;

            StringBuilder condition = new StringBuilder(isCustomer ? "u.roleID = 1" : "u.roleID != 1");

            if (keyword != null && !keyword.isEmpty()) {
                condition.append(" AND (u.fullname LIKE ? OR u.username LIKE ? OR u.address LIKE ? OR u.phone LIKE ?)");
            }

            StringBuilder filterCondition = new StringBuilder();
            if (filterArr != null && filterArr.length > 0) {
                filterCondition.append(" AND (");
                for (String filter : filterArr) {
                    switch (filter) {
                        case "male" ->
                            filterCondition.append(" OR u.gender = 'male'");
                        case "female" ->
                            filterCondition.append(" OR u.gender = 'female'");
                        case "warehouse" ->
                            filterCondition.append(" OR u.role = 2");
                        case "sale" ->
                            filterCondition.append(" OR u.role = 3");
                        case "marketing" ->
                            filterCondition.append(" OR u.role = 4");
                        case "active" ->
                            filterCondition.append(" OR u.status = 1");
                        case "inactive" ->
                            filterCondition.append(" OR u.status = 0");
                    }
                }
                filterCondition = new StringBuilder(filterCondition.toString().replaceFirst(" OR", "")).append(")");
            }
            condition.append(filterCondition);

            countSql = String.format(countSql, condition);

            PreparedStatement statement = con.prepareStatement(countSql);

            // Set parameters for the search part
            if (keyword != null && !keyword.isEmpty()) {
                statement.setString(1, "%" + keyword + "%");
                statement.setString(2, "%" + keyword + "%");
                statement.setString(3, "%" + keyword + "%");
                statement.setString(4, "%" + keyword + "%");
            }

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                int totalRecords = rs.getInt(1);
                int recordsPerPage = 10;  
                return (int) Math.ceil(totalRecords / (double) recordsPerPage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public User getUserById(int id) {
        try {
            // SQL query to fetch user by id
            String sql = """
            SELECT 
                * 
            FROM 
                [User] u 
            JOIN 
                [role] r ON u.role = r.roleID 
            WHERE 
                u.userID = ?;
        """;

            PreparedStatement statement = con.prepareStatement(sql);
   
            statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            return toUser(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }


    public void updateStatus(int userId, boolean status) {
        try {
            String sql = """
        UPDATE [user]
        SET status = ?
        WHERE userID = ?
        """;
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setBoolean(1, status);  
            statement.setInt(2, userId);      

            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateStaff(User user) {
        try {
            String sql = """
        UPDATE [user]
        SET fullname = ?, 
            phone = ?, 
            username = ?, 
            gender = ?, 
            status = ?, 
            role = ?, 
            address = ?
        WHERE userID = ?;
        """;
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, user.getFullname());  
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getGender());
            statement.setBoolean(5, user.isStatus());
            statement.setInt(6, user.getRole().getRoleID());
            statement.setString(7, user.getAddress());
            statement.setInt(8, user.getUserID());
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resetPassword(int userID){
        try {
            String sql = """
        UPDATE [user]
        SET password = ?
        WHERE userID = ?;
        """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, hashPassword("12345678"));
            statement.setInt(2, userID);  
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(12)); // 12 là số vòng lặp
    }
    
    public void addStaff(User user){
        try {
            String sql = """
        INSERT INTO [user](fullname,phone,username,password,gender,status,role,address)
        VALUES (?,?,?,?,?,?,?,?)
        """;
            PreparedStatement statement = con.prepareStatement(sql);

            statement.setString(1, user.getFullname());  
            statement.setString(2, user.getPhone());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getGender());
            statement.setBoolean(6, user.isStatus());
            statement.setInt(7, user.getRole().getRoleID());
            statement.setString(8, user.getAddress());
            
            statement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public ArrayList<Role> toArrRole(ResultSet rs) {
        ArrayList roleList = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                roleList.add(role);
            }
            return roleList;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return roleList;
        }
    }

    public ArrayList<User> toArrUser(ResultSet rs) {
        ArrayList userList = new ArrayList<>();
        try {
            while (rs.next()) {
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                User user = new User(rs.getInt("userID"), rs.getString("fullname"), rs.getString("phone"), rs.getString("username"), rs.getString("password"), rs.getString("gender"), rs.getBoolean("status"), role, rs.getString("address"));
                userList.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
    
    public User toUser(ResultSet rs) {
        User user = new User();
        try {
            if (rs.next()) {
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                user = new User(rs.getInt("userID"), rs.getString("fullname"), rs.getString("phone"), rs.getString("username"), rs.getString("password"), rs.getString("gender"), rs.getBoolean("status"), role, rs.getString("address"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public static void main(String[] args) {
//        ArrayList userList = INSTANCE.getUser(1, true, "userID", true, "", null);
//        for (Object object : userList) {
//            System.out.println(object);
//        }
        
        
        System.out.println(INSTANCE.hashPassword("admin"));
        System.out.println(BCrypt.checkpw("admin", "$2a$12$0QN5zTK2THT.2j4lNpmyjelJwvhsuN1Y942/Q7P1r8Ttu2/jSwmnC"));

    }

}
