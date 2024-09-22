/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author ASUS
 */
public class DAO {
    public static DAO INSTANCE = new DAO();
    private Connection con;
    private String status="OK";
    
    private DAO(){
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
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int roleid = rs.getInt("roleid");
                String rolename = rs.getString("rolename");
                int permision = rs.getInt("permission");
                System.out.println(roleid + " - " + rolename + " - " + permision);
            }
            rs.close();
        }catch (Exception e){
            status = "Error at read Account "+e.getMessage();
        }
    }
    
    
    
    public static void main(String[] args){
        
        INSTANCE.loadDB();
        System.out.println("----");
    }
    
}
