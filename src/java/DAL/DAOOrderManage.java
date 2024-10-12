/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class DAOOrderManage {
    public static DAOOrderManage INSTANCE = new DAOOrderManage();
    private Connection con;
    private String status = "OK";

    private DAOOrderManage() {
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
    
    public static void main(String[] args) {
        INSTANCE.loadDB();
    }
}
