/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vstup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import auxiliary.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Admin
 */
public class VSTUP extends Application {
    User user = new User();
    Auxiliary ax = new Auxiliary();
    SQL sql = new SQL();
    @Override
    public void start(Stage stage) throws Exception {
        boolean is_connect = sql.isConnect();
        if(is_connect){
            if(ReadFile()){
                if(sql.enterUser(user.getlogin(), user.getpass())){
                    if(user.getrole().equals("entrant")){
                    ax.newWindow("entrant","Entrant");
                    }else if(user.getrole().equals("worker")){
                        ax.newWindow("worker","Worker");
                    }else{
                        ax.newWindow("admin","Admin");
                    }
                }else{
                    stage.getIcons().add(new Image("/images/favicon.jpg"));
                    CreateStage(stage);
                }
            }else{
                stage.getIcons().add(new Image("/images/favicon.jpg"));
                CreateStage(stage);
            } 
        }else{
            stage.getIcons().add(new Image("/images/favicon.jpg"));
            CreateStage(stage);
        }
      
        
     
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        launch(args);  
    }
    
    private void CreateStage( Stage stage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add("style/style.css");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("VSTUP");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(VSTUP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean ReadFile() throws IOException{
        try{ 
            File file = new File("user-data.txt");
            if(file.isFile()){
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                String line;
                String[] arr = new String[4];
                int i=0;
                while((line = br.readLine()) != null){
                   arr[i]=line.substring(line.indexOf(":")+1);
                   i++;
                }
                br.close();
                fr.close();
                user.setid(arr[0]);
                user.setlogin(arr[1]);
                user.setpass(arr[2]);
                user.setrole(arr[3]);
            }else{
                return false;
            }
        }catch(Exception e){
            System.out.print("Немає даних");
            return false;
        }
        return true;
    }
   
}
