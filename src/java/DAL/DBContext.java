/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContext {
    protected Connection connect;
    
    public DBContext(){
        try{
            String url = "jdbc:sqlserver://"+serverName+":"+portNumber +
                    ";databaseName="+dbName;
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connect = DriverManager.getConnection(url, userID, password);
        }catch(Exception ex){
            ex.printStackTrace();
        }       
    }
    public Connection getConnection()throws Exception {        
        String url = "jdbc:sqlserver://"+serverName+":"+portNumber +
                ";databaseName="+dbName;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");         
        return DriverManager.getConnection(url, userID, password);

    }
    private final String serverName = "Hiep";
    private final String dbName = "SWP";
    private final String portNumber = "1433";
    private final String userID = "sa";
    private final String password = "123456";
    public void closeConnection(Connection con) throws SQLException {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        }

    public void closeResultSet(ResultSet rs) throws SQLException {
            if (rs != null && !rs.isClosed()) {
                rs.close();
            }
        }

    public void closePreparedStatement(PreparedStatement ps) throws SQLException {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

class Using{
    public static void main(String[] args) {
        try {
            new DBContext().getConnection();
            System.out.println("Ket noi thanh cong");
        } catch (Exception e) {
            System.out.println("Ket noi that bai" + e.getMessage());
        }
    }
}


