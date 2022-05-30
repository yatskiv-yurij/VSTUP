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
public class Faculty {

    String id, name, nomer;
    Button edit;
    public Faculty(String nomer, String id, String name, Button edit) {
        this.id = id;
        this.name = name;
        this.edit = edit;
        this.nomer = nomer;
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
    
    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }
    
    public String getNomer() {
        return nomer;
    }

    public void setNomer(String nomer) {
        this.nomer = nomer;
    }

   @Override
    public String toString() {
        return this.getName();
    }
}
