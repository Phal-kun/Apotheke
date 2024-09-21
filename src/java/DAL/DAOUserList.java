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

/**
 *
 * @author ASUS
 */
public class DAOUserList {
    public static DAOUserList INSTANCE = new DAOUserList();
    private Connection con;
    private String status="OK";
    
    private DAOUserList(){
        if (INSTANCE==null){
            con = new DBContext().connect;
        }else
            INSTANCE = this;
    }
    
    public void loadDB(){
        System.out.println("Loading data...");
        String sql = "select * from [Role]";
        try{
            PreparedStatement ps = con.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    int roleid = rs.getInt("roleid");
                    String rolename = rs.getString("rolename");
                    int permision = rs.getInt("permission");
                    System.out.println(roleid + " - " + rolename + " - " + permision);
                }
            }
        }catch (SQLException e){
            status = "Error at read Account "+e.getMessage();
            System.out.println(status);
        }
    }
    
    public ArrayList<Role> getRole(){       
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
    
    public ArrayList<User> getUser(int page, boolean sort, String sortCol, boolean isCustomer, String keyword, String filter) {
        try {
            String sql = """
        SELECT 
            *
        FROM 
            [User] u JOIN [role] r ON u.role = r.roleID
        WHERE
            %s
        ORDER BY
            %s
        OFFSET ? ROWS
        FETCH NEXT 10 ROWS ONLY;
        """;

            // Set up the base WHERE clause
            StringBuilder condition = new StringBuilder(isCustomer ? "u.role = 1" : "u.role != 1");

            // Add search condition
            if (keyword != null && !keyword.isEmpty()) {
                condition.append(" AND (u.fullname LIKE ? OR u.username LIKE ? OR u.address LIKE ? OR u.phone LIKE ?)");
            }

            // Add filter conditions
            if (filter != null && !filter.isEmpty()) {
                String[] filterArr = filter.split(",");
                for (String string : filterArr) {
                    switch (string) {
                        case "male" ->
                            condition.append(" AND u.gender = 'male'");
                        case "female" ->
                            condition.append(" AND u.gender = 'female'");
                        case "warehouse" ->
                            condition.append(" AND u.role = 2");
                        case "sale" ->
                            condition.append(" AND u.role = 3");
                        case "marketing" ->
                            condition.append(" AND u.role = 4");
                        case "active" ->
                            condition.append(" AND u.status = 1");
                        case "deactive" ->
                            condition.append(" AND u.status = 0");
                    }
                }
            }

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

    
    public ArrayList<Role> toArrRole(ResultSet rs){
        ArrayList roleList = new ArrayList<>();
        try{
            while(rs.next()){
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                roleList.add(role);
            }
            return roleList;
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return roleList;
        }
    }
    
    public ArrayList<User> toArrUser(ResultSet rs){
        ArrayList userList = new ArrayList<>();
        try{
            while(rs.next()){
                Role role = new Role(rs.getInt("roleID"), rs.getString("roleName"));
                User user = new User(rs.getInt("userID"),rs.getString("fullname"), rs.getString("phone"),rs.getString("username"),rs.getString("password"),rs.getString("gender"),Boolean.parseBoolean(rs.getString("status")), role, rs.getString("address"));
                userList.add(user);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return userList;
    }
    
    
    
    public static void main(String[] args){
        ArrayList userList = INSTANCE.getUser(1,true,"userID",false,"John","sale,male");
        for (Object object : userList) {
            System.out.println(object);
        }
        System.out.println("----");
    }
    
}
