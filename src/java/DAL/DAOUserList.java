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
    
    public ArrayList<User> getUser(int page, boolean sort, String sortType, boolean isCustomer) {
        try {
            String sql = """
        SELECT 
            *
        FROM 
            [User] u JOIN [role] r ON u.role = r.roleID
        WHERE
            u.role %s
        ORDER BY
            %s %s
        OFFSET ? ROWS
        FETCH NEXT 10 ROWS ONLY;
        """;

            String roleCondition = isCustomer ? "= 1" : "!= 1";
            String orderByColumn = sortType;  
            String orderDirection = sort ? "ASC" : "DESC";
            sql = String.format(sql, roleCondition, orderByColumn, orderDirection);

            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, (page - 1) * 10);
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
        INSTANCE.loadDB();
        System.out.println(INSTANCE.getUser(1,true,"userID",true));
        System.out.println("----");
    }
    
}
