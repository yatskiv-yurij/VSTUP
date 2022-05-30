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
public class Education {

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

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public Button getEdit() {
        return edit;
    }

    public void setEdit(Button edit) {
        this.edit = edit;
    }

    public Education(String id, String name, String document, Button edit) {
        this.id = id;
        this.name = name;
        this.document = document;
        this.edit = edit;
    }
    String id, name, document;
    Button edit;
    
    @Override
    public String toString() {
        return this.getName();
    }
}
