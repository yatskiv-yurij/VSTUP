/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;
import javafx.scene.control.Button;

/**
 *
 * @author Admin
 */
public class UsersTable {

    AdminController ac = new AdminController();
    String id, name, last_name, email, role, dvnz, faculty, dvnz_id, faculty_id;
    Button edit;
    public UsersTable(String id, String name, String last_name, String email, String role, String dvnz, String faculty, String dvnz_id, String faculty_id, Button edit) {
        this.id = id;
        this.name = name;
        this.last_name = last_name;
        this.email = email;
        this.role = role;
        this.dvnz = dvnz;
        this.faculty = faculty;
        this.dvnz_id = dvnz_id;
        this.faculty_id = faculty_id;
        this.edit=edit;
    }
    
    
    
    public Button getButton(){
        return edit;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDvnz() {
        return dvnz;
    }

    public void setDvnz(String dvnz) {
        this.dvnz = dvnz;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
    public String getDvnz_id() {
        return dvnz_id;
    }

    public void setDvnz_id(String dvnz_id) {
        this.dvnz_id = dvnz_id;
    }

    public String getFaculty_id() {
        return faculty_id;
    }

    public void setFaculty_id(String faculty_id) {
        this.faculty_id = faculty_id;
    }
    
}
