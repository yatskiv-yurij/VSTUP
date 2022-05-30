/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin;
import auxiliary.Auxiliary;
import auxiliary.Email;
import auxiliary.SQL;
import auxiliary.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.File;
import java.net.URL;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
/**
 *
 * @author Admin
 */


public class AdminController implements Initializable {
    @FXML
    private Label last_name, name, add_user_result, university_change_result, change_faculty_result;
    @FXML
    private TextField last_name_add, name_add, login_add, university_change, change_specialty_code, change_faculty;
    @FXML
    private TextArea specialty_all;
    @FXML
    private AnchorPane admin_panel, panel_menu_items, panel_tabs, add_user_panel, change_university_panel, change_faculty_panel;
    @FXML
    private AnchorPane tab1, tab2, tab3, tab4, tab5, tab6;
    @FXML
    private GridPane grid_new_user, faculty_grid;
    @FXML
    private Button user_tab, dvnz_tab, faculty_tab, special_tab, analytic_tab, logout, menu_open, new_user;
    @FXML
    private TableView<UsersTable> data_user;
    @FXML
    private TableView<UniversityTable> data_university;
    @FXML
    private TableView<Faculty> data_faculty;
    @FXML
    private TableView<Specialty> data_specialty;
    @FXML
    private TableColumn<UsersTable, String> col1, col2, col3, col4, col5, col6, col, univer_col1, univer_col2, univer_col3,univer_col4, faculty_col1,faculty_col2,faculty_col3,
            specialty_col1,specialty_col2,specialty_col3,specialty_col4;
    @FXML
    private TableColumn<UsersTable, Button> col7;
    @FXML
    private FontAwesomeIconView menu_open_icon;
    @FXML
    private ComboBox role_add, dvnz_add, faculty_add, university_faculty_add, university_specialty_add;
    
    
    boolean menu=true;
    boolean add_edit = true;
    SQL sql = new SQL();
    User us = new User();
    Auxiliary ax = new Auxiliary();
    Email email = new Email();
    ObservableList<UsersTable> users = FXCollections.observableArrayList();

    
    String edit_user_id = "";
    String id_university ="";
    String id_faculty = "";
    String id_specialty="";
    String id_document="";
    String id_education="";
    String DC ="";
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        sql.isConnect();
        admin_panel.toFront();
        tab1.toFront();
        sql.dataUser(us.getid());
        last_name.setText(us.getlast_name());
        name.setText(us.getname());
        Users();
        role_add.getItems().add("admin");
        role_add.getItems().add("worker");
        ObservableList<University> university = FXCollections.observableArrayList();
        ResultSet rs = sql.getUniversity();
        try {
            while(rs.next()){
                university.add(new University(rs.getString("id"), rs.getString("name")));
            }
            dvnz_add.setItems(university);
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    @FXML
    private void menu_tab_click(ActionEvent event){
        user_tab.getStyleClass().remove("active");
        dvnz_tab.getStyleClass().remove("active");
        faculty_tab.getStyleClass().remove("active");
        special_tab.getStyleClass().remove("active");
        if(event.getSource() == user_tab){
            tab1.toFront();
            user_tab.getStyleClass().addAll("active");
            data_user.getItems().clear();
            Users();
        }else if(event.getSource() == dvnz_tab){
            tab2.toFront();
            dvnz_tab.getStyleClass().addAll("active");
            data_university.getItems().clear();
            setUniversity();
        }else if(event.getSource() == faculty_tab){
            tab3.toFront();
            faculty_tab.getStyleClass().addAll("active");
            data_faculty.getItems().clear();
            setFaculty();
        }else if(event.getSource() == special_tab){
            tab4.toFront();
            special_tab.getStyleClass().addAll("active");
            setSpecialty();
        } 
    }
    
    @FXML
    private void menu_open_click(ActionEvent event){
        if(menu==true){
            user_tab.setText("");
            user_tab.setPrefWidth(55);
            dvnz_tab.setText("");
            dvnz_tab.setPrefWidth(55);
            faculty_tab.setText("");
            faculty_tab.setPrefWidth(55);
            special_tab.setText("");
            special_tab.setPrefWidth(55);
            logout.setText("");
            logout.setPrefWidth(55);
            panel_menu_items.setPrefWidth(55);
            AnchorPane.setLeftAnchor(panel_tabs, 55.0);
            menu_open_icon.setGlyphName("BARS");
            menu=false;
        }else{
            user_tab.setText("Користувачі");
            user_tab.setPrefWidth(200);
            dvnz_tab.setText("ДВНЗ");
            dvnz_tab.setPrefWidth(200);
            faculty_tab.setText("Факультети");
            faculty_tab.setPrefWidth(200);
            special_tab.setText("Спеціальності");
            special_tab.setPrefWidth(200);
            logout.setText("Вихід");
            logout.setPrefWidth(200);
            panel_menu_items.setPrefWidth(200);
            panel_menu_items.setMaxWidth(200);
            AnchorPane.setLeftAnchor(panel_tabs, 200.0);
            menu_open_icon.setGlyphName("CLOSE");
            menu=true;
        }  
    }
    
    @FXML
    private void delete_click(ActionEvent event){
    
        UsersTable ut = data_user.getSelectionModel().getSelectedItem();
        sql.deleteUser(ut.getId());
        data_user.getItems().clear();
        Users();
    }
    
    @FXML
    private void add_click(ActionEvent event){
        add_user_panel.toFront();
        new_user.setText("Додати");
        new_user.setPrefWidth(85);
        if(add_edit==false){
            grid_new_user.getRowConstraints().get(5).setMaxHeight(30);
            grid_new_user.getRowConstraints().get(6).setMaxHeight(25);
            grid_new_user.getRowConstraints().get(5).setMinHeight(30);
            grid_new_user.getRowConstraints().get(6).setMinHeight(25);
            grid_new_user.getRowConstraints().get(5).setPrefHeight(50);
            grid_new_user.getRowConstraints().get(6).setPrefHeight(50);
            grid_new_user.getChildren().add(login_add);
        }
        add_edit=true;
        
    }
    
    @FXML
    private void back_click(){
        admin_panel.toFront();
        last_name_add.setText("");
        name_add.setText("");
        login_add.setText("");
        role_add.valueProperty().set(null);
        dvnz_add.valueProperty().set(null);
        faculty_add.valueProperty().set(null);
    }
    
    
    @FXML
    private void new_user_click(ActionEvent event){
        add_user_result.setText("");
        if(add_edit==true){
            if(last_name_add.getText().length()>=3 && name_add.getText().length()>=3){
                if(ax.isValidEmail(login_add.getText())){
                    if(sql.isUser(login_add.getText())){
                        if(role_add.getValue() != null && role_add.getValue().equals("admin")){
                            String pass = generatePass();
                            String encrypt_pass = ax.toHexString(ax.getSHA(pass));
                            boolean isnewuser = sql.registrationUser(login_add.getText(), encrypt_pass, "admin");
                            if(isnewuser){
                                String id =sql.getID(login_add.getText());
                                boolean isuserdata = sql.addUserData(id, name_add.getText(), last_name_add.getText(), null, null);
                                if(isuserdata){
                                    String message = email.registrationUser(login_add.getText(), pass);
                                    email.sendMail(login_add.getText(), message, "Реєстрація");
                                    data_user.getItems().clear();
                                    Users();
                                    back_click();
                                }
                            } 
                        }
                        else if(role_add.getValue() != null && role_add.getValue().equals("worker")){
                            if(dvnz_add.getValue() != null && faculty_add.getValue() != null){
                                String pass = generatePass();
                                String encrypt_pass = ax.toHexString(ax.getSHA(pass));
                                boolean isnewuser = sql.registrationUser(login_add.getText(), encrypt_pass, "worker");
                                if(isnewuser){
                                    String id =sql.getID(login_add.getText());
                                    University un = (University) dvnz_add.getSelectionModel().getSelectedItem();
                                    Faculty fl = (Faculty) faculty_add.getSelectionModel().getSelectedItem();
                                    boolean isuserdata = sql.addUserData(id, name_add.getText(), last_name_add.getText(), un.getId(), fl.getId());
                                    if(isuserdata){
                                        String message = email.registrationUser(login_add.getText(), pass);
                                        email.sendMail(login_add.getText(), message, "Реєстрація");
                                        data_user.getItems().clear();
                                        Users();
                                        back_click();
                                    }
                                } 
                            }else{
                                add_user_result.setText("Заповніть усі відповідні пункти");
                        }   
                        }else{
                            add_user_result.setText("Заповніть усі відповідні пункти");
                        }
                    }else{
                        add_user_result.setText("Користувач уже існує");
                    }
                }else{
                    add_user_result.setText("Некоректний логін");
                }
            }else{
                add_user_result.setText("Прізвище або ім’я дуже короткі");
            }
            
            
        }else{
            if(last_name_add.getText().length()>=3 && name_add.getText().length()>=3){
                if(role_add.getValue() != null && role_add.getValue().equals("admin")){
                    sql.updateUser(edit_user_id, "admin", name_add.getText(), last_name_add.getText(), null, null);
                }else if(role_add.getValue() != null && role_add.getValue().equals("worker")){
                    University un = (University) dvnz_add.getSelectionModel().getSelectedItem();
                    Faculty fl = (Faculty) faculty_add.getSelectionModel().getSelectedItem();
                    sql.updateUser(edit_user_id, "worker", name_add.getText(), last_name_add.getText(), un.getId(), fl.getId());
                }
            }
            data_user.getItems().clear();
            Users();
            back_click();
        }
    }
    
    EventHandler<ActionEvent> edit_click = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            Button bt = (Button) e.getSource();
            edit_user_id= bt.getId();
            add_user_panel.toFront();
            new_user.setText("Змінити");
            new_user.setPrefWidth(120);
            if(add_edit ==true){
                grid_new_user.getRowConstraints().get(5).setMaxHeight(0);
                grid_new_user.getRowConstraints().get(6).setMaxHeight(0);
                grid_new_user.getRowConstraints().get(5).setMinHeight(0);
                grid_new_user.getRowConstraints().get(6).setMinHeight(0);
                grid_new_user.getRowConstraints().get(5).setPrefHeight(0);
                grid_new_user.getRowConstraints().get(6).setPrefHeight(0);
                grid_new_user.getChildren().remove(login_add);
            }
            add_edit = false;
            ResultSet rs = sql.infoUser(bt.getId());
            try {
                while(rs.next()){
                    last_name_add.setText(rs.getString("last_name"));
                    name_add.setText(rs.getString("name"));
                    role_add.setValue(rs.getString("role"));
                    if(rs.getString("role").equals("worker")){
                        String dvnz = sql.dataUniversity(rs.getString("dvnz"));
                        String faculty = sql.dataFaculty(rs.getString("faculty"));
                        University ut = new University(rs.getString("dvnz"), dvnz);
                        dvnz_add.setValue(ut);
                        Faculty fl = new Faculty(rs.getString("faculty"),rs.getString("id"), faculty,null);
                        faculty_add.setValue(fl);
                    }
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    EventHandler<ActionEvent> edit_univer_click = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            ObservableList<Faculty> faculty = FXCollections.observableArrayList();
            ObservableList<Specialty> specialty = FXCollections.observableArrayList();
            Button bt = (Button) e.getSource();
            change_university_panel.toFront();
            id_university = bt.getId();
            university_change.setText(sql.dataUniversity(id_university));
            ResultSet rs = sql.allFaculty();
            try {
                while(rs.next()){
                    faculty.add(new Faculty(rs.getString("id"), rs.getString("id"), rs.getString("name"),null));
                }
                rs.close();
                rs=sql.allSpecialty();
                while(rs.next()){
                    specialty.add(new Specialty(rs.getString("id"),rs.getString("id"),  rs.getString("code"),rs.getString("name"), null));
                }
                rs.close();
               
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            university_faculty_add.setItems(faculty);
            university_specialty_add.setItems(specialty);
            getSpecialty();
        }
    };
    
    EventHandler<ActionEvent> edit_faculty_click = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            Button bt = (Button) e.getSource();
            change_faculty_panel.toFront();
            faculty_grid.getChildren().remove(change_specialty_code);
            change_faculty.setText(sql.dataFaculty(bt.getId()));
            id_faculty = bt.getId();
            DC = "ЗФ";
          
        }
    };
    
    EventHandler<ActionEvent> edit_specialty_click = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            Button bt = (Button) e.getSource();
            change_faculty_panel.toFront();
            String[] data = sql.dataSpecialty(bt.getId());
            change_faculty.setText(data[1]);
            change_specialty_code.setText(data[0]);
            id_specialty = bt.getId();
            DC = "ЗС";
            if(!faculty_grid.getChildren().contains(change_specialty_code)){
                faculty_grid.getChildren().add(change_specialty_code);
            }
          
        }
    };
    
    @FXML
    private void dvnz_action(ActionEvent event){
         University un = (University) dvnz_add.getSelectionModel().getSelectedItem();
         if(un!=null){
            ObservableList<Faculty>faculty = FXCollections.observableArrayList();
            ResultSet rs = sql.facultiesOfUniversity(un.getId());
            try {
                while(rs.next()){
                    faculty.add(new Faculty(rs.getString("id"),rs.getString("id"),rs.getString("name"),null));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            faculty_add.setItems(faculty);
         }
    }
    
    
    
    @FXML
    private void university_add_click(ActionEvent event){
        change_faculty_panel.toFront();
        change_faculty.setPromptText("Назва закладу");
        faculty_grid.getChildren().remove(change_specialty_code);
        DC = "ДУ";
    }
    
    @FXML
    private void university_delete_click(ActionEvent event){
        UniversityTable ut = data_university.getSelectionModel().getSelectedItem();
        sql.deleteUniversity(ut.getId());
        data_university.getItems().clear();
        setUniversity();
    }
    
    @FXML
    private void back2_click(ActionEvent event){
       backUniversityChange();
    }
    
    @FXML
    private void university_change_delete_click(ActionEvent event){
        Specialty sp = (Specialty) university_specialty_add.getSelectionModel().getSelectedItem();
        sql.deleteUniversitySpecialty(id_university, sp.getId());
        getSpecialty();
    }
    
    @FXML
    private void university_change_add_click(ActionEvent event){
        university_change_result.setText("");
        Faculty fl = (Faculty) university_faculty_add.getSelectionModel().getSelectedItem();
        Specialty sp = (Specialty) university_specialty_add.getSelectionModel().getSelectedItem();
        boolean is_dep = sql.isDependence(id_university, fl.getId(), sp.getId());
        if(is_dep){
            boolean is_add = sql.addDependence(id_university, fl.getId(), sp.getId());
            if(is_add){
                getSpecialty();
            }else{
                university_change_result.setText("Не вдалось додати спеціальність");
            }
        }else{
            university_change_result.setText("Ця спеціальність вже існує");
        }
        
    }
    
     @FXML
    private void university_change_save_click(ActionEvent event){
        university_change_result.setText("");
        boolean is_save = sql.updateUniversity(id_university, university_change.getText());
        if(is_save){
            backUniversityChange();
        }else{
            university_change_result.setText("Не вдалось змініти назву закладу");
        }
    }
    
    @FXML
    private void faculty_add_btn_click(ActionEvent event){
        change_faculty_panel.toFront();
        change_faculty.setPromptText("Назва факультету");
        faculty_grid.getChildren().remove(change_specialty_code);
        DC = "ДФ";
    }
    
    @FXML
    private void back3_click(ActionEvent event){
        admin_panel.toFront();
        if(DC.equals("ДФ") || DC.equals("ЗФ")){
            faculty_grid.getChildren().add(change_specialty_code);
        }
        change_faculty.setText("");
        change_specialty_code.setText("");
        change_faculty_result.setText("");
    }
    
    @FXML
    private void faculty_save_click(ActionEvent event){
        if(DC.equals("ДФ")){
            boolean is_faculty = sql.isFaculty(change_faculty.getText());
            if(is_faculty){
                is_faculty = sql.addFaculty(change_faculty.getText());
                if(is_faculty){
                    backFacultyChange();
                }else{
                    change_faculty_result.setText("Факультет не був доданий");
                }
            }else{
               change_faculty_result.setText("Факультет існує"); 
            }    
        }else if(DC.equals("ЗФ")){
            boolean is_faculty = sql.changeFaculty(id_faculty,change_faculty.getText());
            if(is_faculty){
                backFacultyChange();
            }else{
                change_faculty_result.setText("Факультет не був змінений");
            }
        }else if(DC.equals("ДС")){
            boolean is_specialty = sql.isSpecialty(change_specialty_code.getText());
            if(is_specialty){
                is_specialty = sql.addSpecialty(change_faculty.getText(), change_specialty_code.getText());
                if(is_specialty){
                    backFacultyChange();
                }else{
                    change_faculty_result.setText("Спеціальність не була додана");
                }
            }else{
                change_faculty_result.setText("Спеціальність існує");
            }
        }else if(DC.equals("ЗС")){
            boolean is_specialty = sql.changeSpecialty(id_specialty,change_faculty.getText(),change_specialty_code.getText());
            if(is_specialty){
                backFacultyChange();
            }else{
                change_faculty_result.setText("Спеціальність не була змінена");
            }
        }else if(DC.equals("ДУ")){
            boolean is_university  = sql.isUniversity(change_faculty.getText());
            if(is_university){
                is_university = sql.addUniversity(change_faculty.getText());
                if(is_university){
                    backFacultyChange();
                }else{
                    change_faculty_result.setText("Заклад не додано");
                }
            }else{
                change_faculty_result.setText("Заклад існує");
            }
        }
        
        
    }
    
    @FXML
    private void faculty_delete_click(ActionEvent event){
        Faculty fl = data_faculty.getSelectionModel().getSelectedItem();
        sql.Faculty_delete(fl.getId());
        data_faculty.getItems().clear();
        setFaculty();
    }
    
    @FXML
    private void specialty_add_btn_click(ActionEvent event){
        change_faculty.setPromptText("Назва спеціальності");
        change_faculty_panel.toFront();
        if(!faculty_grid.getChildren().contains(change_specialty_code)){
            faculty_grid.getChildren().add(change_specialty_code);
        }
        DC = "ДС";
    }
    
    @FXML
    private void specialty_delete_click(ActionEvent event){
        Specialty sp = data_specialty.getSelectionModel().getSelectedItem();
        sql.deleteSpecialty(sp.getId());
        data_specialty.getItems().clear();
        setSpecialty();
    }
    
    
    @FXML
    private void logout_click(ActionEvent event){
        String path = System.getProperty("user.dir");
        File file = new File(path+"/user-data.txt");
        file.delete();
        ax.newWindow("vstup","FXMLDocument");
        Stage stage = (Stage) logout.getScene().getWindow();
        stage.close();
    }
    
    public void backUniversityChange(){
        admin_panel.toFront();
        university_change.setText("");
        specialty_all.setText("");
        university_specialty_add.valueProperty().set(null);
        university_faculty_add.valueProperty().set(null);
        data_university.getItems().clear();
        setUniversity();
    }
    
    public void backFacultyChange(){
        admin_panel.toFront();
        if(DC.equals("ДФ") || DC.equals("ЗФ")|| DC.equals("ДУ")){
            faculty_grid.getChildren().add(change_specialty_code);
        }
        change_faculty.setText("");
        change_specialty_code.setText("");
        change_faculty_result.setText("");
        data_faculty.getItems().clear();
        data_specialty.getItems().clear();
        data_university.getItems().clear();
        setUniversity();
        setFaculty();
        setSpecialty();
    }
    
    private void getSpecialty(){
        String allspecialty="";
        ResultSet rs=sql.specialtiesOfUniversity(id_university);
        try {
            while(rs.next()){
                allspecialty+=rs.getString("name")+"\n";
            }
            specialty_all.setText(allspecialty);
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void Users(){
        ResultSet rs = sql.infoUsers();
        if(rs !=null){
            try {
                while (rs.next()){
                    if(rs.getString("role").equals("admin")){
                        users.add(new UsersTable(rs.getString(1),rs.getString(7),rs.getString(8),rs.getString(2),rs.getString(4), "", "", "0", "0", getButton(rs.getString(1), edit_click)));
                    }else{
                        users.add(new UsersTable(rs.getString(1),rs.getString(7),rs.getString(8),rs.getString(2),rs.getString(4), sql.dataUniversity(rs.getString(9)), sql.dataFaculty(rs.getString(10)), rs.getString(9), rs.getString(10),getButton(rs.getString(1), edit_click)));
                    }
                }        
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            col1.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            col2.setCellValueFactory(new PropertyValueFactory<>("name"));
            col3.setCellValueFactory(new PropertyValueFactory<>("email"));
            col4.setCellValueFactory(new PropertyValueFactory<>("role"));
            col5.setCellValueFactory(new PropertyValueFactory<>("dvnz"));
            col6.setCellValueFactory(new PropertyValueFactory<>("faculty"));
            col7.setCellValueFactory(new PropertyValueFactory<>("button"));
            data_user.setItems(users);   
        }
    }
    
    private void setUniversity(){
        ObservableList<UniversityTable> univer = FXCollections.observableArrayList();
        ResultSet rs = sql.getUniversity();
        String faculty="", specialty="";
        if(rs !=null){
           try {
                while (rs.next()){
                    ResultSet rs1 = sql.facultiesOfUniversity(rs.getString(1));
                    while(rs1.next()){
                        faculty+=rs1.getString(2)+"\n";
                    }

                    ResultSet rs2 = sql.specialtiesOfUniversity(rs.getString(1));
                    while(rs2.next()){
                        specialty+=rs2.getString(2)+"\n";
                    }
                    univer.add(new UniversityTable(rs.getString(1),rs.getString(2),faculty,specialty,getButton(rs.getString(1), edit_univer_click)));
                    faculty=""; specialty= "";
                }        
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            univer_col1.setCellValueFactory(new PropertyValueFactory<>("name"));
            univer_col2.setCellValueFactory(new PropertyValueFactory<>("faculty"));
            univer_col3.setCellValueFactory(new PropertyValueFactory<>("specialty"));
            univer_col4.setCellValueFactory(new PropertyValueFactory<>("edit"));
            data_university.setItems(univer);
        }
    }
    
    
    
    private void setFaculty(){
        ObservableList<Faculty> data_faculty = FXCollections.observableArrayList();
        ResultSet rs = sql.allFaculty();
        if(rs != null){
            try {
                int count=1;
                while(rs.next()){
                    data_faculty.add(new Faculty(String.valueOf(count), rs.getString(1), rs.getString(2), getButton(rs.getString(1),edit_faculty_click)));
                    count++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            faculty_col1.setCellValueFactory(new PropertyValueFactory<>("nomer"));
            faculty_col2.setCellValueFactory(new PropertyValueFactory<>("name"));
            faculty_col3.setCellValueFactory(new PropertyValueFactory<>("edit"));
            this.data_faculty.setItems(data_faculty);
        }
    }
    private void setSpecialty(){
        ObservableList<Specialty> data_specialty = FXCollections.observableArrayList();
        ResultSet rs = sql.allSpecialty();
        if(rs != null){
            try {
                int count=1;
                while(rs.next()){
                    data_specialty.add(new Specialty(String.valueOf(count), rs.getString(1), rs.getString(2), rs.getString(3), getButton(rs.getString(1), edit_specialty_click)));
                    count++;
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            specialty_col1.setCellValueFactory(new PropertyValueFactory<>("nomer"));
            specialty_col2.setCellValueFactory(new PropertyValueFactory<>("code"));
            specialty_col3.setCellValueFactory(new PropertyValueFactory<>("name"));
            specialty_col4.setCellValueFactory(new PropertyValueFactory<>("edit"));
            this.data_specialty.setItems(data_specialty);
        }
    }
    
    private Button getButton(String id, EventHandler<ActionEvent> click){
        Button edit = new Button();
        edit.setId(id);
        edit.setPrefWidth(40);
        edit.getStyleClass().add("edit");
        edit.setOnAction(click);
        return edit;
    }
    
    private String generatePass(){
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
 
        for (int i = 0; i < 10; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
    }
        
}
