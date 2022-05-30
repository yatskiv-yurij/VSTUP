/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliary;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
/**
 *
 * @author Admin
 */
public class Auxiliary {
    public static byte[] getSHA(String input)
    { 
        MessageDigest md= null;
        try {
            md = MessageDigest.getInstance("SHA-256");  
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Auxiliary.class.getName()).log(Level.SEVERE, null, ex);
        }
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }
    
    public static String toHexString(byte[] hash)
    {
        BigInteger number = new BigInteger(1, hash); 
        StringBuilder hexString = new StringBuilder(number.toString(16));   
        while (hexString.length() < 32) 
        { 
            hexString.insert(0, '0'); 
        }   
        return hexString.toString(); 
    }
    
    public boolean isValidEmail(String login) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = java.util.regex.Pattern.compile(ePattern);
        Matcher m = p.matcher(login);
        return m.matches();
    } 
    
    public boolean isValidPass(String pass){
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher m = pattern.matcher(pass);
        return m.matches();
    }
    
    public void newWindow(String packages, String name_window){
        Parent root=null;
        try {
            root = FXMLLoader.load(getClass().getResource("/"+packages+"/"+name_window+".fxml"));
        } catch (IOException ex) {
            Logger.getLogger(Auxiliary.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        scene.getStylesheets().add("style/style.css");
        
        Stage stage = new Stage();
        stage.setTitle(name_window);
        stage.setScene(scene);
        stage.setTitle("VSTUP");
        stage.setResizable(false);
        stage.initModality(Modality.NONE);
        stage.getIcons().add(new Image("/images/favicon.jpg"));
        stage.show();
    }
    
    public int[] randomThreeNumber(){
        int[] random_num = new int[3];
        random_num[0]=(int) (10+Math.random()*89);
        while(true){
            int num2 = (int) (10+Math.random()*89);
            if(random_num[0] != num2){
                random_num[1]=num2;
                break;
            }
        }
        
        while(true){
            int num3 = (int) (10+Math.random()*89);
            if(random_num[0] != num3 && random_num[1] != num3){
                random_num[2]=num3;
                break;
            }
        }
        return random_num;
    }
    
    public void getSubject(ComboBox subject_zno){
        subject_zno.getItems().addAll("Українська мова", "Українська мова та література", "Історія України", 
                "Математика", "Біологія", "Географія", "Фізика", "Хімія", "Англійська мова", "Німецька мова", 
                "Іспанська мова", "Французька мова");
    }
}
