/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;

/**
 *
 * @author Admin
 */
public class University {
    String id, name;
    
    public University(String id, String name) {
        this.id = id;
        this.name = name;
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

   @Override
    public String toString() {
        return this.getName();
    }
    
    public boolean Equal(String name){
        if(this.name.toUpperCase().equals(name)){
            return true;
        }else{
            return false;
        }
    }
    
}
