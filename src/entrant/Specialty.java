/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrant;

/**
 *
 * @author Admin
 */
public class Specialty {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialty(String id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }
    String id, code, name;
    
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
