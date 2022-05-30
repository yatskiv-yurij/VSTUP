/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vstup;


import admin.AdminController;
import admin.Document;
import auxiliary.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 *
 * @author Admin
 */
public class FXMLDocumentController implements Initializable {
    
    
    @FXML
    private Label choice, exception, label_pass_exception, label_login_exception;
    @FXML
    private Button enter, registration, forgot_pass, number_one, number_two, number_three;
    @FXML
    private TextField user_login, forgot_login, user_pass_open, user_pass_repeat_open, user_new_pass_open, user_new_pass2_open;
    @FXML
    private PasswordField user_pass, user_pass_repeat, user_new_pass, user_new_pass2;
    @FXML
    private GridPane login_grid, pane_number, pane_login, pane_new_pass;
    @FXML
    private CheckBox remember_me;
    @FXML
    private ComboBox user_document;
    @FXML
    private AnchorPane Pane, panel_pass, panel_pass_repeat, panel_new_pass, panel_new_pass_repeat;
    
    SQL sql = new SQL();
    Auxiliary ax=new Auxiliary();
    User user = new User();
    Email email = new Email();
    boolean registration_open=false;
    boolean open_count_signin = false;
    boolean open_count_signup = false;
    boolean open_pass1=false;
    boolean open_pass2=false;
    Connection connect=null;
    String number_email="";
    
    
    @FXML
    private void registration_click(ActionEvent event) {
        exception.setText("");
        user_login.setText("");
        user_pass.setText("");
        user_pass_repeat.setText("");
        if(registration_open==false){
            choice.setText("Реєстрація");
            enter.setText("Зареєструватись");
            enter.setMaxWidth(230);
            registration.setText("Увійти");
            forgot_pass.setVisible(false);
            login_grid.getChildren().add(panel_pass_repeat);
            login_grid.getChildren().add(user_document);
            login_grid.getRowConstraints().get(6).setMinHeight(15);
            login_grid.getRowConstraints().get(6).setMaxHeight(15);
            login_grid.getRowConstraints().get(7).setMaxHeight(30);
            login_grid.getRowConstraints().get(7).setMinHeight(30);
            login_grid.getRowConstraints().get(8).setMaxHeight(15);
            login_grid.getRowConstraints().get(8).setMinHeight(15);
            login_grid.getRowConstraints().get(9).setMaxHeight(40);
            login_grid.getRowConstraints().get(9).setMinHeight(40);
            login_grid.getRowConstraints().get(10).setMaxHeight(15);
            login_grid.getRowConstraints().get(10).setMinHeight(015);
            registration_open=true;
        }else{
            choice.setText("Вхід");
            enter.setText("Ввійти");
            enter.setMaxWidth(120);
            registration.setText("Зареєструватись");
            forgot_pass.setVisible(true);
            login_grid.getChildren().remove(panel_pass_repeat);
            login_grid.getChildren().remove(user_document);
            login_grid.getRowConstraints().get(6).setMinHeight(0);
            login_grid.getRowConstraints().get(6).setMaxHeight(0);
            login_grid.getRowConstraints().get(7).setMaxHeight(0);
            login_grid.getRowConstraints().get(7).setMinHeight(0);
            login_grid.getRowConstraints().get(8).setMaxHeight(0);
            login_grid.getRowConstraints().get(8).setMinHeight(0);
            login_grid.getRowConstraints().get(9).setMaxHeight(0);
            login_grid.getRowConstraints().get(9).setMinHeight(0);
            login_grid.getRowConstraints().get(10).setMaxHeight(0);
            login_grid.getRowConstraints().get(10).setMinHeight(0);
            registration_open=false;
        }  
    }
    
    @FXML
    private void enter_click(ActionEvent event){
        exception.setText("");
        String ecrypt_pass="";
        if(registration_open == true){
            if(ax.isValidEmail(user_login.getText())){
                String pass1 = "";
                String pass2 = "";
                if(open_count_signin){
                    pass1 = user_pass_open.getText();
                }else{
                    pass1 = user_pass.getText();
                }
                if(open_count_signup){
                    pass2 = user_pass_repeat_open.getText();
                }else{
                    pass2 = user_pass_repeat.getText();
                }
                if(ax.isValidPass(pass1)){
                    if(sql.isUser(user_login.getText())){
                        
                        if(pass1.equals(pass2)){
                            ecrypt_pass = ax.toHexString(ax.getSHA(pass1));
                            boolean is_registration = sql.registrationUser(user_login.getText(), ecrypt_pass, "entrant");
                            if(is_registration==false){
                                exception.setText("Ви не були зареєстровані");    
                            }else{
                                sql.enterUser(user_login.getText(), ecrypt_pass);
                                if(remember_me.isSelected()){
                                    user.remember();
                                }
                                Document doc = (Document) user_document.getSelectionModel().getSelectedItem();
                                sql.addDataEntrant(user.getid(), doc.getId(),"0");
                                ax.newWindow("entrant","Entrant");
                                Stage stage = (Stage) enter.getScene().getWindow();
                                stage.close();
                            }
                        }else{
                            exception.setText("Паролі не збігаються");
                        }
                    }else{
                        exception.setText("Такий користувач вже існує");
                    }    
                }else{
                    exception.setText("Некоректний пароль");
                }
            }else{
                exception.setText("Некоректний логін");
            }
        }else{
            if(open_count_signin){
                ecrypt_pass = ax.toHexString(ax.getSHA(user_pass_open.getText()));
            }else{
                ecrypt_pass = ax.toHexString(ax.getSHA(user_pass.getText()));
            }
            if(sql.enterUser(user_login.getText(), ecrypt_pass)){
                if(remember_me.isSelected()){
                    user.remember();
                }
                if(user.getrole().equals("entrant")){
                    ax.newWindow("entrant","Entrant");
                }else if(user.getrole().equals("worker")){
                    ax.newWindow("worker","Worker");
                }else{
                    ax.newWindow("admin","Admin");
                }

                Stage stage = (Stage) enter.getScene().getWindow();
                stage.close();
            }else{
                exception.setText("Невірний логін або пароль");
            }
        }   
    }
    
    
    @FXML
    private void forgot_click(ActionEvent event){
        Pane.getChildren().add(pane_login);
        Pane.getChildren().remove(login_grid);
    }
    
    @FXML
    private void next_number_click(ActionEvent event){
        label_login_exception.setText("");
        if(sql.isUser2(forgot_login.getText())){
            Pane.getChildren().add(pane_number);
            Pane.getChildren().remove(pane_login);
            int[] random = ax.randomThreeNumber();
            number_one.setText(String.valueOf(random[0]));
            number_two.setText(String.valueOf(random[1]));
            number_three.setText(String.valueOf(random[2]));
            int choice = (int) (Math.random()*3);
            String message = email.resetPass(String.valueOf(random[choice]));
            number_email=String.valueOf(random[choice]);
            try {
                boolean isEmail = email.sendMail(forgot_login.getText(), message, "Зміна паролю");
                if(isEmail){
                    user.setlogin(forgot_login.getText());
                }else{
                    label_login_exception.setText("Помилка");
                } 
            } catch (Exception ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }   
            forgot_login.setText("");
        }else{
            label_login_exception.setText("Користувача не існує");
        }
        
    }
    
    @FXML
    private void back_click(ActionEvent event){
        Pane.getChildren().add(login_grid);
        Pane.getChildren().remove(pane_login);
        Pane.getChildren().remove(pane_number);
        Pane.getChildren().remove(pane_new_pass);
    }
    
    @FXML
    private void number_click(ActionEvent event){
        String click_num = ((Button)event.getSource()).getText();
        if(number_email.equals(click_num)){
            Pane.getChildren().add(pane_new_pass);
            Pane.getChildren().remove(pane_number);
        }else{
            Pane.getChildren().add(login_grid);
            Pane.getChildren().remove(pane_new_pass);
            Pane.getChildren().remove(pane_number);
        }
    }
    
    @FXML
    private void change_pass_click(ActionEvent event){
        label_pass_exception.setText("");
        String pass1="";
        String pass2="";
        if(open_pass1){
            pass1=user_new_pass_open.getText();
        }else{
            pass1=user_new_pass.getText();
        }
        if(open_pass2){
            pass2=user_new_pass2_open.getText();
        }else{
            pass2=user_new_pass2.getText();
        }
        if(pass1.equals(pass2)){
            if(ax.isValidPass(pass1)){
                String ecrypt_pass="";
                ecrypt_pass = ax.toHexString(ax.getSHA(pass1));
                sql.changePass(user.getlogin(), ecrypt_pass);
                Pane.getChildren().add(login_grid);
                Pane.getChildren().remove(pane_new_pass);
            }else{
                exception.setText("Некоректний пароль");
            }
        }else{
            label_pass_exception.setText("Паролі не співпадають");
        }  
    }
    
    @FXML
    private void open_pass1_click(ActionEvent event){
        if(open_count_signin){
            user_pass.setText(user_pass_open.getText());
            panel_pass.getChildren().add(user_pass);
            panel_pass.getChildren().remove(user_pass_open);
            open_count_signin=false;
        }else{
            user_pass_open.setText(user_pass.getText());
            panel_pass.getChildren().add(user_pass_open);
            panel_pass.getChildren().remove(user_pass);
            open_count_signin=true;
        }
    }
    
    @FXML
    private void open_pass2_click(ActionEvent event){
        if(open_count_signup){
            user_pass_repeat.setText(user_pass_repeat_open.getText());
            panel_pass_repeat.getChildren().add(user_pass_repeat);
            panel_pass_repeat.getChildren().remove(user_pass_repeat_open);
            open_count_signup=false;
        }else{
            user_pass_repeat_open.setText(user_pass_repeat.getText());
            panel_pass_repeat.getChildren().add(user_pass_repeat_open);
            panel_pass_repeat.getChildren().remove(user_pass_repeat);
            open_count_signup=true;
        }
    }
    
    @FXML
    private void open_pass3_click(ActionEvent event){
        if(open_pass1){
            user_new_pass.setText(user_new_pass_open.getText());
            panel_new_pass.getChildren().add(user_new_pass);
            panel_new_pass.getChildren().remove(user_new_pass_open);
            open_pass1=false;
        }else{
            user_new_pass_open.setText(user_new_pass.getText());
            panel_new_pass.getChildren().add(user_new_pass_open);
            panel_new_pass.getChildren().remove(user_new_pass);
            open_pass1=true;
        }
    }
    
    @FXML
    private void open_pass4_click(ActionEvent event){
        if(open_pass2){
            user_new_pass2.setText(user_new_pass2_open.getText());
            panel_new_pass_repeat.getChildren().add(user_new_pass2);
            panel_new_pass_repeat.getChildren().remove(user_new_pass2_open);
            open_pass2=false;
        }else{
            user_new_pass2_open.setText(user_new_pass2.getText());
            panel_new_pass_repeat.getChildren().add(user_new_pass2_open);
            panel_new_pass_repeat.getChildren().remove(user_new_pass2);
            open_pass2=true;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        panel_new_pass.getChildren().remove(user_new_pass_open);
        panel_new_pass_repeat.getChildren().remove(user_new_pass2_open);
        Pane.getChildren().remove(pane_number);
        Pane.getChildren().remove(pane_login);
        Pane.getChildren().remove(pane_new_pass);
        login_grid.getChildren().remove(panel_pass_repeat);
        login_grid.getChildren().remove(panel_pass_repeat);
        login_grid.getChildren().remove(user_document);
        panel_pass.getChildren().remove(user_pass_open);
        panel_pass_repeat.getChildren().remove(user_pass_repeat_open);
        login_grid.getRowConstraints().get(6).setMinHeight(0);
        login_grid.getRowConstraints().get(6).setMaxHeight(0);
        login_grid.getRowConstraints().get(7).setMaxHeight(0);
        login_grid.getRowConstraints().get(7).setMinHeight(0);
        login_grid.getRowConstraints().get(8).setMaxHeight(0);
        login_grid.getRowConstraints().get(8).setMinHeight(0);
        login_grid.getRowConstraints().get(9).setMaxHeight(0);
        login_grid.getRowConstraints().get(9).setMinHeight(0);
        login_grid.getRowConstraints().get(10).setMaxHeight(0);
        login_grid.getRowConstraints().get(10).setMinHeight(0);
        boolean is_connect = sql.isConnect();
        exception.setText("");
        if(is_connect){
            setDocument();
        }else{
            exception.setText("Немає підключення до базиданих");
        }
        
    }
    
    private void setDocument(){
        ObservableList<Document> data_document = FXCollections.observableArrayList();
            ResultSet rs = sql.allDocument();
            if(rs != null){
                try {
                    while(rs.next()){
                        data_document.add(new Document(rs.getString(1), rs.getString(2), null));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
                }

                user_document.setItems(data_document);
            }
    }
}

