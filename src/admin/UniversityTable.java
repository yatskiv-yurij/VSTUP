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
public class UniversityTable {
    String id, name, faculty, specialty;
    Button edit;
    
    public UniversityTable(String id, String name, String faculty, String specialty, Button edit) {
        this.id = id;
        this.name = name;
        this.faculty = faculty;
        this.specialty = specialty;
        this.edit = edit;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button bt) {
        this.edit = edit;
    }

    
}
