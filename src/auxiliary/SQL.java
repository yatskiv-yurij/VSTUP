/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliary;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class SQL {
    private String user = "root";
    private String pass = "";
    private String host = "localhost";
    private String port = "3306";
    private String db = "vstup";
    private String cp = "utf8";
    private Connection conn = null;
    User us = new User();
    
    String id="";
    
    public boolean isConnect(){
        MysqlDataSource  ds = new MysqlDataSource();
        ds.setUser(user);
        ds.setPassword(pass);
        ds.setServerName(host);
        ds.setDatabaseName(db);
        ds.setPort(Integer.parseInt(port));
        try{
            ds.setServerTimezone("UTC");
            ds.setCharacterEncoding(cp);
            conn = ds.getConnection();
            return true;
        }catch(Exception ex){
            return false;
        }
    }
 
    public boolean isUser(String login){
        try{
            String sql = "SELECT * FROM `user` WHERE `login`=? ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,login);
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false){
                return true;
            }    
            rs.close();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        } 
        
        return false;
    }
    
    public boolean isUser2(String login){
        try{
            String sql = "SELECT * FROM `user` WHERE `login`=? ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,login);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }    
            rs.close();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        } 
        
        return false;
    }
    
    public boolean registrationUser(String login, String pass, String role){
        try{
            String sql = "INSERT INTO `user` (`login`, `password`, `role`) VALUES(?,?, ?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, pass);
            ps.setString(3, role);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }   
        return true;
    }
    
    public boolean enterUser(String login, String pass){
        try{
            String sql = "SELECT * FROM `user` WHERE `login`=? and `password`=? ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,login);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false){
                return false;
            }else{ 
                us.setid(rs.getString("id"));
                us.setlogin(rs.getString("login"));
                us.setpass(rs.getString("password")); 
                us.setrole(rs.getString("role"));
            } 
            rs.close();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }   
        return true;
    }
    
    public boolean addDataEntrant(String id, String document, String is_data){
         try{
            String sql = "INSERT INTO `data_entrant` (`user_id`, `document`, `is_data`) VALUES(?,?,?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, document);
            ps.setString(3, is_data);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }   
        return true;
    }
    
    public void changePass(String login, String pass){
        try{
            String sql = "UPDATE `user` SET password = ? WHERE login = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, pass);
            ps.setString(2, login);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
        } 
    }
    
    public boolean dataUser(String id){
        try{
            String sql = "SELECT * FROM `data_admin` WHERE `user_id`= ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next() == false){
                return false;
            }else{ 
                us.setname(rs.getString("name"));
                us.setlast_name(rs.getString("last_name"));
                us.setfaculty(rs.getString("faculty"));
                us.setdvnz(rs.getString("dvnz"));
            } 
            rs.close();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }   
        return true;
    } 
    
    public ResultSet infoUsers(){
        try{
            String sql = "SELECT * FROM `user`,`data_admin` WHERE data_admin.user_id= user.id;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
            
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return null;
    }
    
    public String getID(String login){
        String id="";
        try{
            String sql = "SELECT id FROM `user` WHERE `login`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,login);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return id;
    }
    
    public boolean addUserData(String id, String name, String ln, String dvnz, String faculty){
        try{
            String sql = "INSERT INTO `data_admin` (`user_id`, `name`, `last_name`, `dvnz`, `faculty`) VALUES(?,?,?,?,?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, ln);
            ps.setString(4, dvnz);
            ps.setString(5, faculty);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }   
        return true;
    }
    
    
    public ResultSet getUniversity(){
        ResultSet rs=null;
        try{
            String sql = "SELECT * FROM `university`;";
            PreparedStatement ps = conn.prepareStatement(sql);
             rs= ps.executeQuery();
            
        }catch(SQLException ex){
            System.out.print(ex);
        }   
        return rs;
    }
    
    public ResultSet choiceUniversity(String data){
        ResultSet rs=null;
        try{
            String sql = "SELECT * FROM `university` WHERE name LIKE '%"+data+"%';";
            PreparedStatement ps = conn.prepareStatement(sql);
             rs= ps.executeQuery();
            
        }catch(SQLException ex){
            System.out.print(ex);
        }   
        return rs;
    }
    
    public ResultSet facultiesOfUniversity(String id_university){
        ResultSet rs = null;
        try{
            String sql = "Select faculty.id, faculty.name From faculty "
                    + "INNER JOIN system ON faculty.id=system.faculty_id "
                    + "INNER JOIN university ON system.university_id = university.id "
                    + "WHERE system.university_id = ? GROUP BY faculty.name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id_university);
            rs = ps.executeQuery();
        }catch(SQLException ex){
            System.out.print(ex);
        }   
        return rs;
    }
    
    public ResultSet specialtiesOfUniversity(String id_university){
        ResultSet rs=null;
        try{
            String sql = "Select specialties.id, specialties.name From specialties \n" +
                         "INNER JOIN system ON specialties.id=system.specialty_id \n" +
                         "INNER JOIN university ON system.university_id = university.id \n" +
                         "WHERE system.university_id = ? GROUP BY specialties.name";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id_university);
            rs = ps.executeQuery();
            
        }catch(SQLException ex){
            System.out.print(ex);
        }   
        return rs;
    }
    
    
    
    public String dataFaculty(String id_faculty){
        String name="";
        try{
            String sql = "SELECT name FROM `faculty` WHERE `id`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id_faculty);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                name=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return name;
    }
    public boolean isUniversity(String name){
        try{
            String sql = "SELECT * FROM `university` WHERE `name`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return false;
            }
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    public boolean isFaculty(String name){
        try{
            String sql = "SELECT * FROM `faculty` WHERE `name`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,name);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return false;
            }
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean isSpecialty(String code){
        try{
            String sql = "SELECT * FROM `specialties` WHERE `code`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,code);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return false;
            }
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    public String dataUniversity(String id_university){
        String name="";
        try{
            String sql = "SELECT name FROM `university` WHERE `id`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id_university);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                name=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return name;
    }
    
    public String[] dataSpecialty(String id_specialty){
        String[] data =new String[2];
        try{
            String sql = "SELECT code, name FROM `specialties` WHERE `id`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id_specialty);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                data[0]=rs.getString(1);
                data[1]=rs.getString(2);
            }
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return data;
    }
    
    public ResultSet infoUser(String id){
        try{
            String sql = "SELECT user.id, data_admin.name, data_admin.last_name, user.role, data_admin.dvnz, data_admin.faculty FROM `user`,`data_admin` WHERE data_admin.user_id= user.id AND user.id = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            return rs;
            
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return null;
    }
    
    public void updateUser(String id_user, String role, String name, String lname, String dvnz, String faculty){
        try{
            String sql = "UPDATE `user` SET role = ? WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, role);
            ps.setString(2, id_user);
            ps.executeUpdate();
            ps.close();
            
            sql = "UPDATE `data_admin` SET name = ?, last_name=?, dvnz= ?, faculty=? WHERE user_id = ?";
            ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, lname);
            ps.setString(3, dvnz);
            ps.setString(4, faculty);
            ps.setString(5, id_user);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
        } 
    }
    public void deleteUser(String id){
        try {
            String sql = "DELETE FROM `user` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public boolean addUniversity(String name){
        try{
            String sql = "INSERT INTO `university` (`name`) VALUES(?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }   
        return true;
    }
    
    public void deleteUniversity(String id){
        try {
            String sql = "DELETE FROM `university` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet allFaculty(){
        ResultSet rs=null;
        try{
            String sql = "SELECT * FROM `faculty` ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return rs;
    }
    public ResultSet allSpecialty(){
        ResultSet rs=null;
        try{
            String sql = "SELECT * FROM `specialties` ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return rs;
    }
    
    public ResultSet choiceSpecialty(String data){
        ResultSet rs=null;
        try{
            String sql = "SELECT * FROM `specialties` WHERE name LIKE '%"+data+"%' OR code LIKE '%"+data+"%';";
            PreparedStatement ps = conn.prepareStatement(sql);
             rs= ps.executeQuery();
            
        }catch(SQLException ex){
            System.out.print(ex);
        }   
        return rs;
    }
    
    
    
    public void deleteUniversitySpecialty(String id_university, String id_specialty){
        try {
            String sql = "DELETE FROM `system` WHERE university_id = ? AND specialty_id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_university);
            ps.setString(2, id_specialty);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isDependence(String id_university, String id_faculty, String id_specialty){
        ResultSet rs=null;
        try{
            String sql = "SELECT * FROM `system` WHERE university_id = ? AND faculty_id = ? AND specialty_id = ? ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id_university);
            ps.setString(2, id_faculty);
            ps.setString(3, id_specialty);
            rs = ps.executeQuery();
            if(rs.next()==false){
                return true;
            }else{
                return false;
            }
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public String idDependence(String id_university, String id_faculty, String id_specialty){
        ResultSet rs=null;
        String id="";
        try{
            String sql = "SELECT id FROM `system` WHERE university_id = ? AND faculty_id = ? AND specialty_id = ? ;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id_university);
            ps.setString(2, id_faculty);
            ps.setString(3, id_specialty);
            rs = ps.executeQuery();
            while(rs.next()){
                id=rs.getString("id");
            }
            rs.close();
            ps.close();
            return id;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public boolean addDependence(String id_university, String id_faculty, String id_specialty){
        try{
            String sql = "INSERT INTO `system` (`university_id`, `faculty_id`, `specialty_id`) VALUES(?, ?, ?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_university);
            ps.setString(2, id_faculty);
            ps.setString(3, id_specialty);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public void deleteDependence(String id_university, String id_faculty, String id_specialty){
        try {
            String sql = "DELETE FROM `system` WHERE university_id = ? AND faculty_id=? AND specialty_id =?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_university);
            ps.setString(2, id_faculty);
            ps.setString(3, id_specialty);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean updateUniversity(String id_university, String name_university){
        try{
            String sql = "UPDATE `university` SET name = ? WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, name_university);
            ps.setString(2, id_university);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    } 
    
    public boolean addFaculty(String name){
        try{
            String sql = "INSERT INTO `faculty` (`name`) VALUES(?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    public boolean addSpecialty(String name, String code){
        try{
            String sql = "INSERT INTO `specialties` (`code`,`name`) VALUES(?,?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, name);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    
    public void Faculty_delete(String id_faculty){
        try {
            String sql = "DELETE FROM `faculty` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_faculty);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteSpecialty(String id_specialty){
        try {
            String sql = "DELETE FROM `specialties` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_specialty);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean changeFaculty(String id_faculty, String name){
        try{
            String sql = "UPDATE `faculty` SET name = ? WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id_faculty);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    public boolean changeSpecialty(String id_specialty, String name, String code){
        try{
            String sql = "UPDATE `specialties` SET code=?, name = ? WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, code);
            ps.setString(2, name);
            ps.setString(3, id_specialty);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    
    public boolean isDocument(String document){
        try{
            String sql = "SELECT * FROM `document` WHERE `document`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,document);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return false;
            }
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean addDocument(String document){
        try{
            String sql = "INSERT INTO `document` (`document`) VALUES(?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, document);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public ResultSet allDocument(){
        try{
            String sql = "SELECT * FROM `document`;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public void deleteDoc(String id_document){
        try {
            String sql = "DELETE FROM `document` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_document);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String dataDocument(String id){
        String name="";
        try{
            String sql = "SELECT document FROM `document` WHERE `id`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                name=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return name;
    }
    
    public boolean changeDocument(String id, String name){
        try{
            String sql = "UPDATE `document` SET document = ? WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, id);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean isEducation(String education){
        try{
            String sql = "SELECT * FROM `education_level` WHERE `education`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,education);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return false;
            }
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean addEducation(String education){
        try{
            String sql = "INSERT INTO `education_level` (`education`) VALUES(?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, education);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public ResultSet allEducation(){
        try{
            String sql = "SELECT * FROM `education_level`;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    } 
    
   
    
    public void deleteEducation(String id){
        try {
            String sql = "DELETE FROM `education_level` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String dataEducation(String id){
        String name="";
        try{
            String sql = "SELECT education FROM `education_level` WHERE `id`=?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                name=rs.getString(1);
            }
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return name;
    }
    
    public boolean changeEducation(String id, String education){
        try{
            String sql = "UPDATE `education_level` SET education = ? WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, education);
            ps.setString(2, id);
            ps.executeUpdate();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    
    public boolean isDataEntrant(String id_user){
        try{
            String sql = "SELECT is_data FROM `data_entrant` WHERE user_id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                if(rs.getString("is_data").equals("0")){
                    rs.close();
                    return false;
                }
            }
            
            rs.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public String[] entrantDocument(String id_user){
        String doc[] = new String[2];
         try{
            String sql = "SELECT document.* FROM `data_entrant`,`document` WHERE data_entrant.document = document.id AND user_id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                doc[0]= rs.getString("id");
                doc[1] = rs.getString("document");
            }
            rs.close();
            ps.close();
            return doc;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public boolean addDataEntrant(String id_user, String ln, String name, String birthday, String sex, String phone, String ds, String dn, String rating){
        try{
            String sql = "Update `data_entrant` SET `last_name`=?,`name`=?, `birthday`=?, `sex`=?, `phone`=?, `document_series`=?, `document_number`=?,`rating`=?, `is_data`=? WHERE user_id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, ln);
            ps.setString(2, name);
            ps.setString(3, birthday);
            ps.setString(4, sex);
            ps.setString(5, phone);
            ps.setString(6, ds);
            ps.setString(7, dn);
            ps.setString(8, rating);
            ps.setInt(9, 1);
            ps.setString(10, id_user);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public ResultSet getDataEntrant(String id_user){
        try{
            String sql = "SELECT * FROM `data_entrant` WHERE user_id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public void addImage(String id_user, InputStream image){
        try{
            String sql = "UPDATE `data_entrant` SET `image` = ? WHERE user_id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setBlob(1, image);
            ps.setString(2, id_user);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
        }
    }
    
    public void addDocument(String id_user, InputStream document, String document_name){
        try{
            String sql = "UPDATE `data_entrant` SET `document_file` = ?, `document_name`=? WHERE user_id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setBlob(1, document);
            ps.setString(2, document_name);
            ps.setString(3, id_user);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
        }
    }
    
    public void deleteDocument(String id_user){
        try{
            String sql = "UPDATE `data_entrant` SET `document_file` = ?, `document_name`=? WHERE user_id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, null);
            ps.setString(2, null);
            ps.setString(3, id_user);
            ps.execute();
            ps.close();
        }catch(SQLException ex){
            System.out.print(ex);
        }
    }
    
    public ResultSet dataZNO(String id_user){
        try{
            String sql = "SELECT * FROM `zno` WHERE user_id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    } 
    
    public boolean addZNO(String id_user, String year, String certificate, String subject, String pincode, String rating){
        try{
            String sql = "INSERT INTO `zno` (`user_id`, `year`, `certificate`, `subject`, `pincode`, `rating`) VALUES(?,?,?,?,?,?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ps.setString(2, year);
            ps.setString(3, certificate);
            ps.setString(4, subject);
            ps.setString(5, pincode);
            ps.setString(6, rating);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public void deleteZNO(String id_zno){
        try {
            String sql = "DELETE FROM `zno` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_zno);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet workerSpecialty(){
        ResultSet rs=null;
        try{
            String sql = "SELECT specialties.id, specialties.code,specialties.name FROM specialties, `system` WHERE `system`.university_id=? AND `system`.`faculty_id`=? \n" +
                            "AND `system`.`specialty_id` = specialties.id";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, us.getdvnz());
            ps.setString(2, us.getfaculty());
            rs = ps.executeQuery();
            
        }catch(SQLException ex){
            System.out.print(ex);
        }
        return rs;
    }
    
    public String idSpecialty(String code){
        String id="";
        try{
            String sql = "SELECT id FROM `specialties` WHERE code = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getString("id");
            }
            return id;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public ResultSet detailSpecialty(String id){
        try{
            String sql = "SELECT data_specialties.`id`, `education_level`.`id`,`education_level`.`education`,`forma`, `offer`,`document`.`id`, `document`.`document`, `course`, `term`, `amount`, `contract`, `price`, `mandatory_exams`, `choice_exams`,`entrance_exams` "
                    + "FROM `data_specialties`, `document`, `education_level` WHERE data_specialties.document=document.id AND data_specialties.education=education_level.id AND id_system=? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    public ResultSet editDetailSpecialty(String id){
        try{
            String sql = "SELECT `education_level`.`id`,`education_level`.`education`,`forma`, `offer`,`document`.`id`, `document`.`document`, `course`, `term`, `amount`, `contract`, `price` "
                    + "FROM `data_specialties`, `document`, `education_level` WHERE data_specialties.document=document.id AND data_specialties.education=education_level.id AND `data_specialties`.`id`=? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    public boolean updateDetailSpecialty(String id, String education, String forma, String offer, String document, String course, String term, String amount, String contract, String price){
        try{
            String sql = "UPDATE `data_specialties` SET `education` = ?, `forma`=?, `offer`=?, `document`=?, `course`=?, `term`=?, `amount`=?, `contract`=?, `price`=? WHERE id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, education);
            ps.setString(2, forma);
            ps.setString(3, offer);
            ps.setString(4, document);
            ps.setString(5, course);
            ps.setString(6, term);
            ps.setString(7, amount);
            ps.setString(8, contract);
            ps.setString(9, price);
            ps.setString(10, id);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean addDetails(String id_sys, String education, String forma, String offer, String document, String course, String term, String amount, String contract, String price){
        try{
            String sql = "INSERT INTO `data_specialties` (`id_system`, `education`, `forma`, `offer`, `document`, `course`, `term`, `amount`, `contract`, `price`) VALUES(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
                ps.setString(1, id_sys);
                ps.setString(2, education);
                ps.setString(3, forma);
                ps.setString(4, offer);
                ps.setString(5, document);
                ps.setString(6, course);
                ps.setString(7, term);
                ps.setString(8, amount);
                ps.setString(9, contract);
                ps.setString(10, price);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean updateExams(String id, String mandatory_exams, String choice_exams, String entrance_exams){
        try{
            String sql = "UPDATE `data_specialties` SET `mandatory_exams` = ?, `choice_exams`=?, `entrance_exams`=? WHERE id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, mandatory_exams);
            ps.setString(2, choice_exams);
            ps.setString(3, entrance_exams);
            ps.setString(4, id);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public void deleteDetails(String id){
        try {
            String sql = "DELETE FROM `data_specialties` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.execute();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ResultSet getSearch( String data){
        try{
            String sql = "SELECT `university`.`name`, `faculty`.`name`, `specialties`.name, `education_level`.`education`, `data_specialties`.* FROM `data_specialties`, `university`, `specialties`,`faculty`, `system`, `education_level` \n" +
                        "WHERE `data_specialties`.`id_system`=`system`.`id` \n" +
                        "AND `system`.university_id = `university`.`id` \n" +
                        "AND `system`.`specialty_id`=`specialties`.`id` \n" +
                        "AND `system`.`faculty_id` = `faculty`.`id` \n"+
                        "AND `data_specialties`.`education`=`education_level`.`id` " + data;
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public ResultSet dataSpecialtySearch(String id){
        try{
            String sql = "SELECT `university`.`name`, `faculty`.`name`, `specialties`.name, `education_level`.`education`, `data_specialties`.* FROM `data_specialties`, `university`, `specialties`,`faculty`, `system`, `education_level` \n" +
                        "WHERE `data_specialties`.`id_system`=`system`.`id` \n" +
                        "AND `system`.university_id = `university`.`id` \n" +
                        "AND `system`.`specialty_id`=`specialties`.`id` \n" +
                        "AND `system`.`faculty_id` = `faculty`.`id` \n"+
                        "AND `data_specialties`.`education`=`education_level`.`id` AND `data_specialties`.`id` = ?"; 
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public boolean isRequest(String id_user, String id_specialty){
        try{
            String sql = "SELECT * FROM `request` WHERE id_user= ? AND id_specialty = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ps.setString(2, id_specialty);
            ResultSet rs = ps.executeQuery();
            if(rs.next()==false){
                return true;
            }else{
                return false;
            }
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean addRequest(String id_user, String id_specialty, String status){
        try{
            String sql = "INSERT INTO `request` (`id_user`, `id_specialty`, `status`) VALUES(?,?,?)";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ps.setString(2, id_specialty);
            ps.setString(3, status);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public ResultSet userRequest(String id_user){
        try{
            String sql = "SELECT `university`.`name`, `faculty`.`name`, `specialties`.`name`, `data_specialties`.`forma`, `request`.`status` "
                    + "FROM `university`, `faculty`,`specialties`, `data_specialties`, `system`, `request` "
                    + "WHERE `system`.`university_id`=`university`.`id` AND `system`.`faculty_id`=`faculty`.`id` AND `system`.`specialty_id`=`specialties`.`id` "
                    + "AND `data_specialties`.`id_system`=`system`.`id` AND `request`.id_specialty = `data_specialties`.`id` "
                    + "AND `request`.`id_user`= ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_user);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public ResultSet detailSpecialtyRequest(String id_univer, String id_faculty){
        try{
            String sql = "SELECT data_specialties.id, `specialties`.`name`, `education_level`.`education`, data_specialties.forma, data_specialties.offer, `document`.`document` "
                    + "FROM `data_specialties`, `specialties`, `system`, `education_level`, `document` "
                    + "WHERE `data_specialties`.`id_system`=`system`.`id` AND `specialties`.`id` = `system`.`specialty_id` "
                    + "AND `data_specialties`.`education`=`education_level`.`id` AND `data_specialties`.`document`=`document`.`id` "
                    + "AND `system`.`university_id`=? AND `system`.`faculty_id`=?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1,id_univer);
            ps.setString(2,id_faculty);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public ResultSet userRequest(String id_specialty, String status){
        try{
            String sql = "SELECT `request`.`id`, `data_entrant`.`id`,`user`.`id`, `data_entrant`.`last_name`,`data_entrant`.`name`, `data_entrant`.`sex`, `data_entrant`.`phone`, `user`.`login`,`data_entrant`.`birthday` \n" +
                            "FROM `request`,`data_specialties`,`user`, `data_entrant`\n" +
                            "WHERE `request`.`id_specialty`=`data_specialties`.`id` AND `request`.`id_user`=`user`.`id` AND `data_entrant`.`user_id`=`user`.`id`\n" +
                            "AND `data_specialties`.`id`=? AND `request`.`status` = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_specialty);
            ps.setString(2, status);
            ResultSet rs = ps.executeQuery();
            return rs;
        }catch(SQLException ex){
            System.out.print(ex);
            return null;
        }
    }
    
    public boolean updateRequest(String id_request){
        try{
            String sql = "UPDATE `request` SET `status` = ? WHERE id = ? ;";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, "підтверджено");
            ps.setString(2, id_request);
            ps.execute();
            ps.close();
            return true;
        }catch(SQLException ex){
            System.out.print(ex);
            return false;
        }
    }
    
    public boolean deleteRequest(String id_request){
        try {
            String sql = "DELETE FROM `request` WHERE id = ?";
            PreparedStatement ps = (PreparedStatement) conn.prepareStatement(sql);
            ps.setString(1, id_request);
            ps.execute();
            ps.close();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(SQL.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}




