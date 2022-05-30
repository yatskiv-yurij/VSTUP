/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entrant;
import admin.AdminController;
import admin.Education;
import admin.University;
import auxiliary.Auxiliary;
import auxiliary.Email;
import auxiliary.SQL;
import auxiliary.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;


/**
 *
 * @author Admin
 */
public class EntrantController implements Initializable {
    
    @FXML
    private Button profile_tab, search_tab, request_tab,logout, menu_open, document_file, search;
    @FXML
    private FontAwesomeIconView menu_open_icon;
    @FXML
    private AnchorPane entrant_panel, panel_menu_items,panel_tabs, entrant_add_data_panel, add_zno_panel,document_panel, zno_panel;
    @FXML
    private AnchorPane tab1, tab2, tab4, tab5;
    @FXML
    private ScrollPane result_scroll, tab3;
    @FXML
    private VBox result_search_panel, all_request_panel;
    @FXML
    private ComboBox entrant_sex, subject_zno, search_education, search_forma, search_course, search_specialty_cb, search_univer_cb;
    @FXML
    private DatePicker entrant_data_birthday;
    @FXML
    private TextField entrant_last_name,entrant_name, entrant_phone, entrant_series_document, entrant_number_document,entrant_bal, year_zno, certificate_zno, pincode_zno, rating_zno, search_specialty, search_univer;
    @FXML
    private Label entrant_result, search_result, forma, offer,course,term, amount, contract, price, apply_result;
    @FXML
    private TableView zno_data;
    @FXML
    private TableColumn zno_col1, zno_col2,zno_col3, zno_col4, zno_col5;
    @FXML
    private ImageView entrant_img;
    @FXML
    private Label last_name_data, name_data, sex_data, birthday_data, phone_data, series_data, number_data, zno_result, rating_data, rating;
    @FXML
    private GridPane add_data_entrant, search_grid, data_specialty_panel;
    @FXML
    private TextArea exams;
    
    
    SQL sql = new SQL();
    User us = new User();
    Auxiliary ax = new Auxiliary();
    Email email = new Email();
    boolean menu=true;
    boolean edit = false;
    byte[] document = null;
    boolean isUniver=false;
    String id_university=null, id_specialty=null,id_data_specialty = null;
    ObservableList<University> university = FXCollections.observableArrayList();
    ObservableList<Specialty> data_specialty = FXCollections.observableArrayList();
    
    String[] doc = new String[2];
    String mandatory_exams=null, choice_exams=null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sql.isConnect();
        add_zno_panel.toBack();
        if(!sql.isDataEntrant(us.getid())){
            entrant_add_data_panel.toFront();
            entrant_sex.getItems().add("Чоловік");
            entrant_sex.getItems().add("Жінка");
            entrant_data_birthday.setDayCellFactory(param -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.compareTo(LocalDate.now()) > 0 );
                }
            });
            
        }else{
            entrant_panel.toFront();
            tab1.toFront();
            dataEntrant(us.getid());
            userZno();
            entrant_add_data_panel.toBack();
        }
        ax.getSubject(subject_zno);
        
        doc = sql.entrantDocument(us.getid());
        check_document(doc[1]);
        
        
        
        
        
        
        setEducation();
        setForma();
        setCourse();
        setSpecialty("");
        setUniversity("");
        search_grid.getChildren().remove(search_specialty_cb);
        search_grid.getChildren().remove(search_univer_cb);
        search_univer.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean change=true;
            for(University elem : university) {
                if(elem.Equal(newValue.toUpperCase())){
                    change=false;
                }
            }
            if(change && newValue.length()>0){
                if(!search_grid.getChildren().contains(search_univer_cb)){
                    search_grid.getChildren().add(search_univer_cb);
                }
                setUniversity(newValue);
                
            }else{
                try{
                    search_grid.getChildren().remove(search_univer_cb);
                }catch(Exception ex){
                
        }}});
        
        search_specialty.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean change=true;
            for(Specialty elem : data_specialty) {
                if(elem.Equal(newValue.toUpperCase())){
                    change=false;
                }
            }
            if(change && newValue.length()>0){
                if(!search_grid.getChildren().contains(search_specialty_cb)){
                    search_grid.getChildren().add(search_specialty_cb);
                }
                setSpecialty(newValue);
            }else{
                try{
                    search_grid.getChildren().remove(search_specialty_cb);
                }catch(Exception ex){
                
        }}});
        
        
        
        result_scroll.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                result_search_panel.setPrefWidth((double) newValue-15);
            }
        });
        result_scroll.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                result_search_panel.setPrefHeight((double) newValue);
            }
        });
        
        tab3.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                all_request_panel.setPrefWidth((double) newValue-15);
            }
        });
        tab3.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                all_request_panel.setPrefHeight((double) newValue);
            }
        });
    }
    
    
    
    @FXML
    private void back_click(ActionEvent event){
        if(edit){
            entrant_panel.toFront();
            dataEntrant(us.getid());
            entrant_add_data_panel.toBack();
            edit=false;
        }else{
            String path = System.getProperty("user.dir");
            File file = new File(path+"/user-data.txt");
            file.delete();
            ax.newWindow("vstup","FXMLDocument");
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.close();
        }
    }
    
    @FXML
    private void continue_click(ActionEvent event){
       if(entrant_last_name.getText().length()>=3 && entrant_name.getText().length()>=3){
            if(entrant_data_birthday.getValue() != null){
                if(entrant_sex.getValue() != null){
                  if(entrant_series_document.getText().length()==2 && entrant_number_document.getText().length()>=5){
                        if(!doc[1].equals("Повна загальна середня освіта")){
                            sql.addDataEntrant(us.getid(),entrant_last_name.getText(), entrant_name.getText(), entrant_data_birthday.getValue().toString(), entrant_sex.getValue().toString(), entrant_phone.getText(), entrant_series_document.getText(), entrant_number_document.getText(),"");
                        }else{
                            sql.addDataEntrant(us.getid(),entrant_last_name.getText(), entrant_name.getText(), entrant_data_birthday.getValue().toString(), entrant_sex.getValue().toString(), entrant_phone.getText(), entrant_series_document.getText(), entrant_number_document.getText(),entrant_bal.getText());
                        }
                        entrant_panel.toFront();
                        tab1.toFront();
                        dataEntrant(us.getid());
                        entrant_add_data_panel.toBack();
                  }  
                }
            }
        }
    }
    
    @FXML
    private void add_img_user_click(ActionEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("JPEG", "*.jpeg")
                );
            File file = fileChooser.showOpenDialog(null);
            
            if(file !=null){
                InputStream in = new FileInputStream(file);
                sql.addImage(us.getid(), in); 
                dataEntrant(us.getid());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    @FXML
    private void menu_open_click(ActionEvent event){
        if(menu==true){
            profile_tab.setText("");
            profile_tab.setPrefWidth(55);
            search_tab.setText("");
            search_tab.setPrefWidth(55);
            request_tab.setText("");
            request_tab.setPrefWidth(55);
            logout.setText("");
            logout.setPrefWidth(55);
            panel_menu_items.setPrefWidth(55);
            AnchorPane.setLeftAnchor(panel_tabs, 55.0);
            menu_open_icon.setGlyphName("BARS");
            menu=false;
        }else{
            profile_tab.setText("Профіль");
            profile_tab.setPrefWidth(200);
            search_tab.setText("Пошук");
            search_tab.setPrefWidth(200);
            request_tab.setText("Заяки");
            request_tab.setPrefWidth(200);
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
    private void menu_tab_click(ActionEvent event){
        profile_tab.getStyleClass().remove("active");
        search_tab.getStyleClass().remove("active");
        request_tab.getStyleClass().remove("active");
        if(event.getSource() == profile_tab){
            tab1.toFront();
            profile_tab.getStyleClass().addAll("active");
            dataEntrant(us.getid());
            userZno();
            apply_result.setText("");
        }else if(event.getSource() == search_tab){
            tab2.toFront();
            search_tab.getStyleClass().addAll("active");
            apply_result.setText("");
        }else if(event.getSource() == request_tab){
            tab3.toFront();
            request_tab.getStyleClass().addAll("active");
            ResultSet rs = sql.userRequest(us.getid());
            getRequest(rs);
            apply_result.setText("");
        } 
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
    
    @FXML
    private void edit_user_data_click(ActionEvent event){
        entrant_add_data_panel.toFront();
        edit=true;
        editEntrant(us.getid());
    }
    
    
    @FXML
    private void add_file_click(ActionEvent event){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF", "*.pdf")
                );
            File file = fileChooser.showOpenDialog(null);
            
            if(file !=null){
                InputStream in = new FileInputStream(file);
                sql.addDocument(us.getid(), in, file.getName()); 
                dataEntrant(us.getid());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    @FXML
    private void document_file_click(ActionEvent event){
        if(document !=null){
            try {
                String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\document.pdf";
                File f = new File(path);
                if (f.exists()){
                    Files.write(Paths.get(path), document, StandardOpenOption.WRITE);

                }else{
                    Files.write(Paths.get(path), document, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
                }
                Desktop desktop = Desktop.getDesktop();
                File file = new File(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\document.pdf");
                desktop.open(file);
                TimerTask task = new TimerTask() {
                    public void run() {
                        file.delete();
                    }
                };
                Timer timer = new Timer("Timer");
                long delay = 10000L;
                timer.schedule(task, delay);

            } catch (IOException ex) {
                Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void delete_file_click(ActionEvent event){
        sql.deleteDocument(us.getid());
        dataEntrant(us.getid());
    }
    
    
    @FXML
    private void add_zno_click(ActionEvent event){
        add_zno_panel.toFront();
    }
    
    @FXML
    private void back2_click(ActionEvent event){
        add_zno_panel.toBack();
    }
    
    @FXML
    private void save_zno_click(ActionEvent event){
        zno_result.setText("");
        if(year_zno.getText().length()==4 && isNumber(year_zno.getText())){
            if(certificate_zno.getText().length()>0 && isNumber(certificate_zno.getText())){
                if(pincode_zno.getText().length()==4 && isNumber(pincode_zno.getText())){
                    if(subject_zno.getValue() != null){
                        if(rating_zno.getText().length()>0 &&rating_zno.getText().length() <=3){
                            boolean iszno = sql.addZNO(us.getid(), year_zno.getText(), certificate_zno.getText(), subject_zno.getValue().toString(), pincode_zno.getText(), rating_zno.getText());
                            if(iszno){
                                userZno();
                                add_zno_panel.toBack();
                            }else{
                                zno_result.setText("Дані зно додані не були");
                            }
                        }
                        
                    }else{
                        zno_result.setText("Предмет ЗНО не вибраний");
                    }
                }else{
                    zno_result.setText("Некоректний пінкод");
                }
            }else{
                zno_result.setText("Некоректний номер сертифікату");
            }
        }else{
            zno_result.setText("Рік сертифікату некоректний");
        }
    }
    
    @FXML
    private void delete_zno_click(ActionEvent event){
        ZNO zno = (ZNO) zno_data.getSelectionModel().getSelectedItem();
        sql.deleteZNO(zno.getId());
        userZno();
    }
    

    @FXML
    private void search_univer_click(ActionEvent event){
        try{
            University un = (University) search_univer_cb.getSelectionModel().getSelectedItem();
            String data = un.toString();
            search_univer.setText(data);
            id_university = un.getId();
        }catch(Exception ex){
        
        }
    }
    
    @FXML
    private void search_specialty_click(ActionEvent event){
        try{
            Specialty sp = (Specialty) search_specialty_cb.getSelectionModel().getSelectedItem();
            String data = sp.toString();
            search_specialty.setText(data);
            id_specialty=sp.getId();
        }catch(Exception ex){
        
        }
    }
    
    @FXML
    private void search_click(ActionEvent event){
        search_result.setText("");
        if(search_education.getValue()!=null && id_specialty != null){
            Education education = (Education) search_education.getSelectionModel().getSelectedItem();
            
            String sql_report = " AND `document` = "+doc[0]+" AND `education_level`.`id` = "+education.getId()+" AND `specialties`.id = "+id_specialty;
            if(id_university != null){
                sql_report+=" AND `university`.`id` = "+id_university;
            }
            if(search_forma.getValue() != null){
                sql_report += " AND forma = '"+search_forma.getValue().toString()+"'";
            }
            if(search_course.getValue() != null){
                sql_report += " AND course = '"+search_course.getValue().toString()+"'";
            }
            
            ResultSet rs = sql.getSearch(sql_report);
            result_scroll.toFront();
            getSpecialty(rs);
            
        }else{
            search_result.setText("Освітній рівень або спеціалізація не вибрані");
        }
    }
    
    EventHandler<ActionEvent> look_click = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            Button bt = (Button) e.getSource();
            id_data_specialty = bt.getId();
            data_specialty_panel.toFront();
            ResultSet rs= sql.dataSpecialtySearch(bt.getId());
            try {
                while(rs.next()){
                    forma.setText(rs.getString("forma"));
                    course.setText(rs.getString("course"));
                    offer.setText(rs.getString("offer"));
                    term.setText(rs.getString("term"));
                    amount.setText(rs.getString("amount"));
                    contract.setText(rs.getString("contract"));
                    price.setText(rs.getString("price"));
                    String exam="";
                    if(rs.getString("mandatory_exams") != null && !rs.getString("mandatory_exams").equals("")){
                        exam+="Обов’язкові:\n"+rs.getString("mandatory_exams")+"\n\n";
                        mandatory_exams=rs.getString("mandatory_exams");
                    }
                    if(rs.getString("choice_exams") != null && !rs.getString("choice_exams").equals("")){
                        exam+="На вибір:\n"+rs.getString("choice_exams")+"\n\n";
                        choice_exams = rs.getString("choice_exams");
                    }
                    if(rs.getString("entrance_exams") != null && !rs.getString("entrance_exams").equals("")){
                        exam+="Вступні:\n"+rs.getString("entrance_exams");
                    }
                    exams.setText(exam);
                   
                }
            } catch (SQLException ex) {
                Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    @FXML
    private void back3_click(ActionEvent event){
        result_scroll.toFront();
        apply_result.setText("");
    }
    
    @FXML
    private void apply_click(ActionEvent event){
        apply_result.setText("");
        if(sql.isRequest(us.getid(), id_data_specialty)){
            boolean is_mantadory=true, is_choice=true;
            ResultSet rs = sql.dataZNO(us.getid());
            List<String> exam = new ArrayList<String>();
            try {
                while(rs.next()){
                    exam.add(rs.getString("subject"));
                    System.out.println(rs.getString("subject"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(mandatory_exams != null){
                String [] data = mandatory_exams.split("\\s*\n\\s*");
                List<String> items = Arrays.asList(data);
                for (String subject : items) {
                    System.out.println("^"+subject);
                    if(exam.contains(subject)){
                        is_mantadory=true;
                        continue;
                    }else{
                        is_mantadory=false;
                        break;
                    }
                }
            }

            if(choice_exams != null){
                String [] data = choice_exams.split("\\s*\n\\s*");
                List<String> items = Arrays.asList(data);
                for (String subject : items) {
                    System.out.println("*"+subject);
                    if(exam.contains(subject)){
                        is_choice=true;
                        break;
                    }else{
                        is_choice=false;
                        continue;
                    }
                }
            }

            if(is_mantadory && is_choice){
                ResultSet user_document = sql.getDataEntrant(us.getid());
                boolean is_photo=false, is_file=false;
                try {
                    while(user_document.next()){
                        if(user_document.getBlob("image") != null){
                            is_photo = true;
                        }
                        if(user_document.getBlob("document_file") != null){
                            is_file=true;
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
                }

                if(is_photo && is_file){
                    boolean is_request = sql.addRequest(us.getid(), id_data_specialty,"очікується");
                    if(is_request){
                        apply_result.setText("Заявка подана");
                    }else{
                        apply_result.setText("Заявка не подана");
                    }
                }else{
                    apply_result.setText("Ваш профіль не заповнений");
                }

            }else{
                apply_result.setText("Ваші конкурсні предмети не відповідають даній спеціальності");
            }
        }else{
            apply_result.setText("Заявка уже подана");
        }
    }
    
   
    
    
    private void dataEntrant(String id_user){
        ResultSet rs = sql.getDataEntrant(id_user);
        try {
            while(rs.next()){
                last_name_data.setText(rs.getString("last_name"));
                name_data.setText(rs.getString("name"));
                sex_data.setText(rs.getString("sex"));
                Date date = rs.getDate("birthday");
                String pattern = "dd.MM.yyyy";
                SimpleDateFormat DateFormat = new SimpleDateFormat(pattern);
                birthday_data.setText(DateFormat.format(date));
                phone_data.setText(rs.getString("phone"));
                series_data.setText(rs.getString("document_series"));
                number_data.setText(rs.getString("document_number"));
                rating_data.setText(rs.getString("rating"));
                if(rs.getBinaryStream("image") !=null){
                    Image img = new Image(rs.getBinaryStream("image"));
                    entrant_img.setImage(img);
                }
                if(rs.getBinaryStream("document_file") !=null){
                    document_file.setText(rs.getString("document_name"));
                    document = rs.getBytes("document_file");
                }else{
                    document_file.setText("Файл не завантажений");
                    document=null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void editEntrant(String id_user){
        ResultSet rs = sql.getDataEntrant(id_user);
        try {
            while(rs.next()){
                entrant_last_name.setText(rs.getString("last_name"));
                entrant_name.setText(rs.getString("name"));
                entrant_sex.getItems().add("Чоловік");
                entrant_sex.getItems().add("Жінка");
                entrant_sex.setValue(rs.getString("sex"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(rs.getString("birthday"), formatter);
                entrant_data_birthday.setValue(localDate);
                entrant_phone.setText(rs.getString("phone"));
                entrant_series_document.setText(rs.getString("document_series"));
                entrant_number_document.setText(rs.getString("document_number"));
                if(!doc[1].equals("Повна загальна середня освіта")){
                    add_data_entrant.getChildren().remove(entrant_bal);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void userZno(){
        ResultSet rs =sql.dataZNO(us.getid());
        ObservableList<ZNO> zno = FXCollections.observableArrayList();
        if(rs != null){
            try {
                while(rs.next()){
                    zno.add(new ZNO(rs.getString("id"), rs.getString("year"), rs.getString("certificate"), rs.getString("subject"), rs.getString("pincode"), rs.getString("rating")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            zno_col1.setCellValueFactory(new PropertyValueFactory<>("year"));
            zno_col2.setCellValueFactory(new PropertyValueFactory<>("certificate"));
            zno_col3.setCellValueFactory(new PropertyValueFactory<>("subject"));
            zno_col4.setCellValueFactory(new PropertyValueFactory<>("pincode"));
            zno_col5.setCellValueFactory(new PropertyValueFactory<>("rating"));
            this.zno_data.setItems(zno);
        }
    }
    
    private boolean isNumber(String data){
         try { 
            int Value = Integer.parseInt(data); 
            return true; 
        } catch (NumberFormatException e) { 
            System.out.println(e); 
       }
         return false;
    }
    
    private void setEducation(){
        ObservableList<Education> data_education = FXCollections.observableArrayList();
        ResultSet rs = sql.allEducation();
        String document="";
        if(rs !=null){
           try {
                while (rs.next()){
                    data_education.add(new Education(rs.getString(1), rs.getString(2), null, null));
                }        
            } catch (SQLException ex) {
                Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
            this.search_education.setItems(data_education);
        }
    }
    
    private void setUniversity(String data){
        university.clear();
        ResultSet rs=null;
        if(data.length()>0){
            rs = sql.choiceUniversity(data);
        }else{
            rs = sql.getUniversity();
        }
        try {
            while(rs.next()){
                university.add(new University(rs.getString("id"), rs.getString("name")));
            }
            
            this.search_univer_cb.setItems(university);
        } catch (SQLException ex) {
            Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    
    private void setSpecialty(String data){
        data_specialty.clear();
        ResultSet rs = null;
        if(data.length()>0){
            rs = sql.choiceSpecialty(data);
        }else{
            rs = sql.allSpecialty();
        }
        if(rs != null){
            try {
                while(rs.next()){
                    data_specialty.add(new Specialty(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
            } catch (SQLException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.search_specialty_cb.setItems(data_specialty);
        }
    }
    
    private void setCourse(){
        search_course.getItems().addAll("1 курс", "2 курс", "3 курс", "4 курс");
    }
    
    private void setForma(){
        search_forma.getItems().addAll("Денна", "Заочна");
    }
    
    private void getSpecialty(ResultSet rs){
        result_search_panel.getChildren().clear();
        try {
            while(rs.next()){
                AnchorPane ap = new AnchorPane();
                ap.getStyleClass().add("item_result");
                ap.prefWidthProperty().bind(result_search_panel.widthProperty());
                ap.setPrefHeight(100);
                ap.setMinHeight(100);
                ap.setMaxHeight(100);
                result_search_panel.getChildren().add(ap);
                result_search_panel.setMargin(ap, new Insets(20, 50, 20, 50));
                
                Label name_univer = new Label();
                name_univer.setText(rs.getString("university.name"));
                name_univer.getStyleClass().add("result_data");
                ap.getChildren().add(name_univer);
                ap.setTopAnchor(name_univer, 8.0);
                ap.setLeftAnchor(name_univer, 15.0);
                
                Label name_faculty = new Label();
                name_faculty.setText(rs.getString("faculty.name"));
                name_faculty.getStyleClass().add("result_data");
                ap.getChildren().add(name_faculty);
                ap.setTopAnchor(name_faculty, 38.0);
                ap.setLeftAnchor(name_faculty, 30.0);
                
                Label forma = new Label();
                forma.setText(rs.getString("data_specialties.forma"));
                forma.getStyleClass().add("result_data");
                ap.getChildren().add(forma);
                ap.setTopAnchor(forma, 65.0);
                ap.setLeftAnchor(forma, 45.0);
                
                Button bt = new Button();
                bt.setText("Переглянути");
                bt.getStyleClass().add("look");
                bt.setId(rs.getString("data_specialties.id"));
                bt.setOnAction(look_click);
                ap.getChildren().add(bt);
                ap.setBottomAnchor(bt, 10.0);
                ap.setRightAnchor(bt, 15.0);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    private void getRequest(ResultSet rs){
        all_request_panel.getChildren().clear();
        try {
            while(rs.next()){
                AnchorPane ap = new AnchorPane();
                ap.getStyleClass().add("item_result");
                ap.prefWidthProperty().bind(all_request_panel.widthProperty());
                ap.setPrefHeight(120);
                ap.setMinHeight(120);
                ap.setMaxHeight(120);
                all_request_panel.getChildren().add(ap);
                all_request_panel.setMargin(ap, new Insets(20, 50, 20, 50));
                
                Label name_univer = new Label();
                name_univer.setText(rs.getString("university.name"));
                name_univer.getStyleClass().add("result_data");
                ap.getChildren().add(name_univer);
                ap.setTopAnchor(name_univer, 8.0);
                ap.setLeftAnchor(name_univer, 15.0);
                
                Label name_faculty = new Label();
                name_faculty.setText(rs.getString("faculty.name"));
                name_faculty.getStyleClass().add("result_data");
                ap.getChildren().add(name_faculty);
                ap.setTopAnchor(name_faculty, 35.0);
                ap.setLeftAnchor(name_faculty, 30.0);
                
                Label name_specialty = new Label();
                name_specialty.setText(rs.getString("specialties.name"));
                name_specialty.getStyleClass().add("result_data");
                ap.getChildren().add(name_specialty);
                ap.setTopAnchor(name_specialty, 62.0);
                ap.setLeftAnchor(name_specialty, 45.0);
                
                Label forma = new Label();
                forma.setText(rs.getString("data_specialties.forma"));
                forma.getStyleClass().add("result_data");
                ap.getChildren().add(forma);
                ap.setTopAnchor(forma, 89.0);
                ap.setLeftAnchor(forma, 60.0);
                
                Label status_lb = new Label();
                status_lb.setText("Статус: ");
                status_lb.getStyleClass().add("result_data");
                ap.getChildren().add(status_lb);
                ap.setTopAnchor(status_lb, 89.0);
                ap.setRightAnchor(status_lb, 150.0);
                
                Label status = new Label();
                status.setText(rs.getString("request.status"));
                status.getStyleClass().add("result_data");
                ap.getChildren().add(status);
                ap.setTopAnchor(status, 89.0);
                ap.setRightAnchor(status, 30.0);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(EntrantController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }  
    
    private void check_document(String name_doc){
        
        if(!name_doc.toLowerCase().equals("повна загальна середня освіта") && !name_doc.toLowerCase().equals("диплом молодшого спеціаліста") && !name_doc.toLowerCase().equals("диплом фахового молодшого бакалавра")){
            tab1.getChildren().remove(zno_panel);
        }
        if(!name_doc.equals("Повна загальна середня освіта")){
            add_data_entrant.getChildren().remove(entrant_bal);
            document_panel.getChildren().remove(rating);
            document_panel.getChildren().remove(rating_data);
        }
    }
}

