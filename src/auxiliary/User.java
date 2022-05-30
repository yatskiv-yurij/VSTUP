/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliary;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import vstup.FXMLDocumentController;

/**
 *
 * @author Admin
 */
public class User {
    private static String id="";
    private static String pass="";
    private static String login="";
    private static String role="";
    private static String name="";
    private static String last_name=""; 
    private static String faculty="";
    private static String dvnz="";
    
    public void setid(String id){
        this.id = id;
    }
    public String getid(){
        return id;
    }
    public void setlogin(String login){
        this.login = login;
    }
    
    public String getlogin(){
        return login;
    }
    public void setpass(String pass){
        this.pass = pass;
    }
    public String getpass(){
        return pass;
    }
    
    public void setrole(String role){
        this.role = role;
    }
    
    public String getrole(){
        return role;
    }
    
    public void setname(String name){
        this.name = name;
    }
    
    public String getname(){
        return name;
    }
    
    public void setlast_name(String last){
        this.last_name = last;
    }
    
    public String getlast_name(){
        return last_name;
    }
    
    public void setfaculty(String faculty){
        this.faculty = faculty;
    }
    public String getfaculty(){
        return faculty;
    }
    
    public void setdvnz(String dvnz){
        this.dvnz = dvnz;
    }
    public String getdvnz(){
        return dvnz;
    }
    
    public void remember(){
        String data="id:"+id+"\n"
                + "login:"+login+"\n"
                + "pass:"+pass+"\n"
                + "role:"+role;
        String path = System.getProperty("user.dir");
        try {
            File file = new File("user-data.txt");
            file.createNewFile();
            FileWriter writer = new FileWriter(path+"\\"+file);
            writer.write(data);
            writer.flush();
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
}
