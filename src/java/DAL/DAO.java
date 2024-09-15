/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAL;

import Model.*;
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
    
    public ArrayList<Role> getRoleList(){
        ArrayList roleList = new ArrayList<>();
        try {
            String sql = """
            SELECT 
                *
            FROM 
                Role        
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Role role = new Role(rs.getInt("roleid"),rs.getString("rolename"),rs.getInt("permission"));
                roleList.add(role);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return roleList;
    }
    
    public Role getRole(int id){
        Role role = new Role();
        try {
            String sql = """
            SELECT 
                *
            FROM 
                Role
            WHERE
                roleid = ?         
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                role = new Role(rs.getInt("roleid"),rs.getString("rolename"),rs.getInt("permission"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return role;
    }
    
    public void createAccount(Account acc){
        try {
            String sql = """
            INSERT INTO Acc(username,password,roleid)
                         VALUES (?,?,?)
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, acc.getUsername());
            statement.setString(2, acc.getPassword());
            statement.setInt(3, acc.getRole().getRoleid());
            ResultSet rs = statement.executeQuery();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Account checkAccount(String username, String password) {
    try {
        String sql = """
        SELECT Acc.accid, Acc.username, Acc.password, Acc.highscore, Role.roleid, Role.rolename, Role.permission
        FROM Acc
        JOIN Role ON Acc.roleid = Role.roleid
        WHERE username = ?;
        """;
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, username);
        ResultSet rs = statement.executeQuery();
        if (rs.next() && password.equals(rs.getString("password"))) {
                Role role = new Role(rs.getInt("roleid"),rs.getString("rolename"),rs.getInt("permission"));
                Account acc = new Account(rs.getInt("accid"), username, password, role, rs.getInt("highscore"));
                return acc;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return null;
    }
    
    public boolean checkAccount(String username){
        try {
            String sql = """
            select * from Acc
            where username = ? 
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return false;
    }
    
    public ArrayList<Account> listAcc(){
        ArrayList accList = new ArrayList<>();
        try {
            String sql = """
            select * from Acc a join [Role] r on a.roleid = r.roleid
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Role r = new Role(rs.getInt("roleid"), rs.getString("rolename"), rs.getInt("permission"));
                Account acc = new Account(rs.getInt("accid"), rs.getString("username"), rs.getString("password"), r, rs.getInt("highscore"));
                accList.add(acc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return accList;
    }
    
    public void deleteAcc(int id){
        try {
            String sql = """
            DELETE FROM Acc
            WHERE accid = ?;
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateAcc(Account acc){
        try {
            String sql = """
            UPDATE Acc
            SET username = ?, password = ?, roleid = ?, highscore = ?
            WHERE accid = ?
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, acc.getUsername());
            statement.setString(2, acc.getPassword());
            statement.setInt(3, acc.getRole().getRoleid());
            statement.setInt(4, acc.getHighscore());
            statement.setInt(5, acc.getAccid());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public ArrayList<Account> listHighscrore(){
        ArrayList accList = new ArrayList<>();
        try {
            String sql = """
            select * from Acc
            order by highscore desc       
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Account acc = new Account(rs.getInt("accid"), rs.getString("username"), "", null, rs.getInt("highscore"));
                accList.add(acc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return accList;
    }
    
public ArrayList<Characters> listCharacter(int id) {
    ArrayList<Characters> charList = new ArrayList<>();
    
    // SQL to get character and job details
    String characterSql = """
        SELECT c.charid, c.charname, c.accid, c.jobid, c.exp, c.score, 
               j.jobname, j.hp, j.atk, j.matk
        FROM [Character] c
        JOIN Job j ON c.jobid = j.jobid
        WHERE c.accid = ?;
    """;

    // SQL to get level details
    String levelSql = """
        SELECT lvid, lvname, [exp]
        FROM [Level]
        ORDER BY [exp] ASC;
    """;

    try (PreparedStatement charStmt = con.prepareStatement(characterSql)) {
        charStmt.setInt(1, id);

        try (ResultSet charRs = charStmt.executeQuery()) {
            // Fetch levels
            ArrayList<Levels> levels = new ArrayList<>();
            try (PreparedStatement levelStmt = con.prepareStatement(levelSql);
                 ResultSet levelRs = levelStmt.executeQuery()) {

                while (levelRs.next()) {
                    levels.add(new Levels(levelRs.getInt("lvid"), levelRs.getString("lvname"), levelRs.getInt("exp")));
                }
            }

            // Iterate through character results
            while (charRs.next()) {
                // Determine level based on experience
                Levels currentLevel = null;
                int characterExp = charRs.getInt("exp");

                for (Levels level : levels) {
                    if (characterExp >= level.getExp()) {
                        currentLevel = level;
                    } else {
                        break;
                    }
                }

                Job job = new Job(charRs.getInt("jobid"), charRs.getString("jobname"), 
                                  charRs.getDouble("hp"), charRs.getDouble("atk"), charRs.getDouble("matk"));

                Characters character = new Characters(charRs.getInt("charid"), charRs.getString("charname"), 
                                                      charRs.getInt("accid"), currentLevel, job, 
                                                      charRs.getInt("exp"), charRs.getInt("score"));
                charList.add(character);
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return charList;
}

    public void createCharacter(Characters character){
        try {
            String sql = "INSERT INTO Character (charname, accid, jobid, exp, score) " +
                        "VALUES (?, ?, ?, 0, 0)";
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setString(1, character.getCharname());
            statement.setInt(2, character.getAccid());
            statement.setInt(3, character.getJob().getJobid());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Characters searchCharacter(String charname, int accid){
    String characterSql = """
        SELECT c.charid, c.charname, c.accid, c.jobid, c.exp, c.score, 
               j.jobname, j.hp, j.atk, j.matk
        FROM [Character] c
        JOIN Job j ON c.jobid = j.jobid
        WHERE c.accid = ? AND c.charname = ?;
    """;

    // SQL to get level details
    String levelSql = """
        SELECT lvid, lvname, [exp]
        FROM [Level]
        ORDER BY [exp] ASC;
    """;

    try (PreparedStatement charStmt = con.prepareStatement(characterSql)) {
        charStmt.setInt(1, accid);
        charStmt.setString(2, charname);

        try (ResultSet charRs = charStmt.executeQuery()) {
            // Fetch levels
            ArrayList<Levels> levels = new ArrayList<>();
            try (PreparedStatement levelStmt = con.prepareStatement(levelSql);
                 ResultSet levelRs = levelStmt.executeQuery()) {

                while (levelRs.next()) {
                    levels.add(new Levels(levelRs.getInt("lvid"), levelRs.getString("lvname"), levelRs.getInt("exp")));
                }
            }

            // Iterate through character results
            while (charRs.next()) {
                // Determine level based on experience
                Levels currentLevel = null;
                int characterExp = charRs.getInt("exp");

                for (Levels level : levels) {
                    if (characterExp >= level.getExp()) {
                        currentLevel = level;
                    } else {
                        break;
                    }
                }

                Job job = new Job(charRs.getInt("jobid"), charRs.getString("jobname"), 
                                  charRs.getDouble("hp"), charRs.getDouble("atk"), charRs.getDouble("matk"));

                Characters character = new Characters(charRs.getInt("charid"), charRs.getString("charname"), 
                                                      charRs.getInt("accid"), currentLevel, job, 
                                                      charRs.getInt("exp"), charRs.getInt("score"));
                return character;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
    
    public Characters searchCharacter(int charid){
    String characterSql = """
        SELECT c.charid, c.charname, c.accid, c.jobid, c.exp, c.score, 
               j.jobname, j.hp, j.atk, j.matk
        FROM [Character] c
        JOIN Job j ON c.jobid = j.jobid
        WHERE c.charid = ?
    """;

    // SQL to get level details
    String levelSql = """
        SELECT lvid, lvname, [exp]
        FROM [Level]
        ORDER BY [exp] ASC;
    """;

    try (PreparedStatement charStmt = con.prepareStatement(characterSql)) {
        charStmt.setInt(1, charid);

        try (ResultSet charRs = charStmt.executeQuery()) {
            // Fetch levels
            ArrayList<Levels> levels = new ArrayList<>();
            try (PreparedStatement levelStmt = con.prepareStatement(levelSql);
                 ResultSet levelRs = levelStmt.executeQuery()) {

                while (levelRs.next()) {
                    levels.add(new Levels(levelRs.getInt("lvid"), levelRs.getString("lvname"), levelRs.getInt("exp")));
                }
            }

            // Iterate through character results
            while (charRs.next()) {
                // Determine level based on experience
                Levels currentLevel = null;
                int characterExp = charRs.getInt("exp");

                for (Levels level : levels) {
                    if (characterExp >= level.getExp()) {
                        currentLevel = level;
                    } else {
                        break;
                    }
                }

                Job job = new Job(charRs.getInt("jobid"), charRs.getString("jobname"), 
                                  charRs.getDouble("hp"), charRs.getDouble("atk"), charRs.getDouble("matk"));

                Characters character = new Characters(charRs.getInt("charid"), charRs.getString("charname"), 
                                                      charRs.getInt("accid"), currentLevel, job, 
                                                      charRs.getInt("exp"), charRs.getInt("score"));
                return character;
            }
        }
    } catch (SQLException ex) {
        Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
    }
    
    public void deleteCharacter(int id){
        try {
            String sql = """
            DELETE FROM [Character]
            WHERE charid = ?;
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void updateCharacter(int id, int exp, int score){
        try {
            String sql = """
            UPDATE [Character]
            SET exp = ?, score = ?
            WHERE charid = ?;
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, getExp(id) + exp);
            statement.setInt(2, getScore(id) + score);
            statement.setInt(3, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int getScore(int id){
        int currentScore = 0;
        try {
            String sql = """
            SELECT *
            FROM [Character]
            WHERE charid = ?
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                currentScore = rs.getInt("score");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentScore;
    }
    
    public int getExp(int id){
        int currentExp = 0;
        try {
            String sql = """
            SELECT *
            FROM [Character]
            WHERE charid = ?
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                currentExp = rs.getInt("exp");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentExp;
    }
    
    public ArrayList<Job> getJobList(){
        ArrayList jobList = new ArrayList<>();
        try {
            String sql = """
            select * from Job      
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Job job = new Job(rs.getInt("jobid"), rs.getString("jobname"), rs.getDouble("hp"), rs.getDouble("atk"), rs.getDouble("matk"));
                jobList.add(job);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return jobList;
    }
    
    public ArrayList<Stage> getStageList(){
        ArrayList stageList = new ArrayList<>();
        try {
            String sql = """
            select * from Stage s join Level l on s.stagelv = l.lvid     
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Levels lv = new Levels(rs.getInt("lvid"), rs.getString("lvname"), rs.getInt(9));
                Stage stage = new Stage(rs.getInt("stageid"), rs.getString("stagename"), lv, rs.getInt("maxmon"), rs.getInt(5), rs.getInt("score"));
                stageList.add(stage);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return stageList;
    }
    
    public Stage getStage(int id){
        try {
            String sql = """
            select * from Stage s join Level l on s.stagelv = l.lvid
            where stageid = ?
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Levels lv = new Levels(rs.getInt("lvid"), rs.getString("lvname"), rs.getInt(9));
                Stage stage = new Stage(rs.getInt("stageid"), rs.getString("stagename"), lv, rs.getInt("maxmon"), rs.getInt(5), rs.getInt("score"));
                return stage;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return null;
    }
   
    
    public ArrayList<Monster> getMonList(int monnum, int lvid){
        ArrayList monList = new ArrayList<>();
        try {
            String sql = """
            SELECT *
                    FROM (
                        SELECT ROW_NUMBER() OVER (ORDER BY NEWID()) AS RowNum, m.monid, m.monname, l.lvid, l.lvname, l.exp, j.jobid, j.jobname, j.hp, j.atk, j.matk
                        FROM Monster m 
                        JOIN Level l ON m.lvid = l.lvid
                        JOIN Job j ON m.jobid = j.jobid
                        WHERE m.lvid = ?
                    ) AS Subquery
                    WHERE RowNum <= ?
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, lvid);
            statement.setInt(2, monnum);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Levels lv = new Levels(rs.getInt("lvid"), rs.getString("lvname"), rs.getInt("exp"));
                Job job = new Job(rs.getInt("jobid"),rs.getString("jobname"),rs.getDouble("hp"),rs.getDouble("atk"),rs.getDouble("matk"));
                Monster mon = new Monster(rs.getInt("monid"), rs.getString("monname"), lv, job);
                monList.add(mon);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return monList;
    }
    
    public void updateHighscore(int accid, int score){
        try {
            int currentScore = 0;
            String sql = """
            SELECT *
            FROM Acc
            WHERE accid = ?
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, accid);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                currentScore = rs.getInt("highscore");
            }
            if(score>currentScore){
                sql = """
                UPDATE Acc
                SET highscore = ?
                WHERE accid = ?
                """;
                PreparedStatement statement2 = con.prepareStatement(sql);
                statement2.setInt(1, score);
                statement2.setInt(2, accid);
                statement2.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    public int getHighscore(int id){
        int currentScore = 0;
        try {
            String sql = """
            SELECT *
            FROM Acc
            WHERE accid = ?
            """;
            PreparedStatement statement = con.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                currentScore = rs.getInt("highscore");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return currentScore;
    }
    
    public static void main(String[] args){
        
//        ArrayList<Monster> charlist = INSTANCE.getMonList(1, 1);
//        for (Monster characters : charlist) {
//            System.out.println(characters);
//        }
//        Stage stage = INSTANCE.getStage(1);
//        System.out.println(stage);
        ArrayList<Account> accList = INSTANCE.listAcc();
        for (Account account : accList) {
            System.out.println(account);
        }
        System.out.println("----");
    }
    
}
