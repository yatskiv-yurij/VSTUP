/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package worker;


import admin.Document;
import admin.Education;
import admin.Specialty;
import auxiliary.Auxiliary;
import auxiliary.Email;
import auxiliary.SQL;
import auxiliary.User;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entrant.ZNO;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javax.swing.filechooser.FileSystemView;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;

/**
 *
 * @author Admin
 */
public class WorkerController  implements Initializable { 
    
    @FXML
    private Label change_specialty_result, detail_result, last_name, name, exams_result;
    @FXML
    private Button specialty_tab, request_tab,confirm_tab, logout,document_file,look_user,reject,confirm,export;
    @FXML
    private AnchorPane tab1, tab2, tab3,tab4,tab5, panel_tabs,panel_menu_items, change_specialty_panel, worker_panel,add_detail_panel,entrant_info;
    @FXML
    private FontAwesomeIconView menu_open_icon;
    @FXML
    private TableView<Specialty> data_specialty;
    @FXML
    private TableView<DetailSpecialty> specialty_detail, specialty_request;
    @FXML
    private TableView<Request> user_request;
    @FXML
    private TableView<ZNO> zno_data;
    @FXML
    private TableColumn specialty_col1,specialty_col2,specialty_col3, detail_col1, detail_col2, detail_col3, detail_col4, detail_col5, specialty_request_col1,
            specialty_request_col2, specialty_request_col3, specialty_request_col4,specialty_request_col5, user_col1,user_col2,user_col3,user_col4,user_col5,
            zno_col1,zno_col2,zno_col3,zno_col4,zno_col5;
    @FXML
    private TextField change_specialty, change_specialty_code, detail_amount, detail_contract, detail_price;
    @FXML
    private ComboBox detail_forma, detail_offer, detail_document, detail_course, detail_education, mandatory_combo, choice_combo, entrance_combo;
    @FXML
    private DatePicker detail_start, detail_end;
    @FXML
    private TextArea mandatory_exams, choice_exams, entrance_exams;
    @FXML
    private GridPane add_exams_panel,request_grid;
    @FXML 
    private Label last_name_data, name_data, sex_data, birthday_data, phone_data, series_data, number_data, rating_data, rating;
    @FXML
    private ImageView entrant_img;
    
    Auxiliary ax = new Auxiliary();
    SQL sql = new SQL();
    User user=new User();
    Email email = new Email();
    boolean menu=true;
    boolean edit=false;
    boolean acept = false;
    String id_specialty="",id_dependence="",id_detail="";
    byte[] document = null;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        sql.isConnect();
        sql.dataUser(user.getid());
        setSpecialty();
        worker_panel.toFront();
        tab1.toFront();
        dataComboBox();
        last_name.setText(user.getlast_name());
        name.setText(user.getname());
    }
    
    @FXML
    private void menu_tab_click(ActionEvent event){
        specialty_tab.getStyleClass().remove("active");
        request_tab.getStyleClass().remove("active");
        confirm_tab.getStyleClass().remove("active");
        if(event.getSource() == specialty_tab){
            tab1.toFront();
            specialty_tab.getStyleClass().addAll("active");
            setSpecialty();
        }else if(event.getSource() == request_tab){
            tab2.toFront();
            request_tab.getStyleClass().addAll("active");
            acept=false;
            tableRequest();
        }else if(event.getSource() == confirm_tab){
            tab2.toFront();
            confirm_tab.getStyleClass().addAll("active");
            acept = true;
            tableRequest();
        }
    }
    
    @FXML
    private void menu_open_click(ActionEvent event){
        if(menu==true){
            specialty_tab.setText("");
            specialty_tab.setPrefWidth(55);
            request_tab.setText("");
            request_tab.setPrefWidth(55);
            confirm_tab.setText("");
            confirm_tab.setPrefWidth(55);
            logout.setText("");
            logout.setPrefWidth(55);
            panel_menu_items.setPrefWidth(55);
            AnchorPane.setLeftAnchor(panel_tabs, 55.0);
            menu_open_icon.setGlyphName("BARS");
            last_name.setText("");
            name.setText("");
            menu=false;
        }else{
            specialty_tab.setText("Спеціальності");
            specialty_tab.setPrefWidth(200);
            request_tab.setText("Заявки");
            request_tab.setPrefWidth(200);
            confirm_tab.setText("Прийняти");
            confirm_tab.setPrefWidth(200);
            logout.setText("Вихід");
            logout.setPrefWidth(200);
            panel_menu_items.setPrefWidth(200);
            panel_menu_items.setMaxWidth(200);
            AnchorPane.setLeftAnchor(panel_tabs, 200.0);
            menu_open_icon.setGlyphName("CLOSE");
            last_name.setText(user.getlast_name());
            name.setText(user.getname());
            menu=true;
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
    private void add_specialty_click(ActionEvent event){
        change_specialty_panel.toFront();
    }
    
    @FXML
    private void back_click(ActionEvent event){
        worker_panel.toFront();
        change_specialty.setText("");
        change_specialty_code.setText("");
    }
    
    @FXML
    private void specialty_save_click(ActionEvent event){
        
        boolean is_specialty = sql.isSpecialty(change_specialty_code.getText());
        if(is_specialty){
            is_specialty = sql.addSpecialty(change_specialty.getText(), change_specialty_code.getText());
            String id = sql.idSpecialty(change_specialty_code.getText());
            if(sql.isDependence(user.getdvnz(), user.getfaculty(), id)){
                sql.addDependence(user.getdvnz(), user.getfaculty(), id);
            }else{
                change_specialty_result.setText("Спеціальність вже існує");
            }
        }else{
            String id = sql.idSpecialty(change_specialty_code.getText());
            if(sql.isDependence(user.getdvnz(), user.getfaculty(), id)){
                sql.addDependence(user.getdvnz(), user.getfaculty(), id);
            }else{
                change_specialty_result.setText("Спеціальність вже існує");
            }
        }
        worker_panel.toFront();
        change_specialty.setText("");
        change_specialty_code.setText("");
        setSpecialty();
    }
    
    @FXML
    private void specialty_delete_click(ActionEvent event){
        Specialty sp = data_specialty.getSelectionModel().getSelectedItem();
        sql.deleteDependence(user.getdvnz(), user.getfaculty(), sp.getId());
        data_specialty.getItems().clear();
        setSpecialty();
    }
    
    @FXML
    private void back2_click(ActionEvent event){
        tab1.toFront();
    }
    
    @FXML
    private void add_detail_click(ActionEvent event){
        add_detail_panel.toFront();
        edit=false;
    }
    @FXML
    private void back3_click(){
        worker_panel.toFront();
        detail_education.setValue(null);
        detail_forma.setValue(null);
        detail_offer.setValue(null);
        detail_document.setValue(null);
        detail_course.setValue(null);
        detail_amount.setText("");
        detail_contract.setText("");
        detail_price.setText("");
        detail_start.setValue(null);
        detail_end.setValue(null);
        
    }
    
    @FXML
    private void add_exams_click(ActionEvent event){
        add_exams_panel.toFront();
        ax.getSubject(mandatory_combo);
        mandatory_combo.getItems().add("Іноземна мова");
        ax.getSubject(choice_combo);
        choice_combo.getItems().add("Іноземна мова");
        entrance_combo.getItems().add("Іноземна мова");
        entrance_combo.getItems().add("Фахове випробування");
        DetailSpecialty ds = specialty_detail.getSelectionModel().getSelectedItem();
        id_detail=ds.getId();
        mandatory_exams.setText(ds.mandatory_exams);
        choice_exams.setText(ds.choice_exams);
        entrance_exams.setText(ds.entrance_exams);
      
    }
    
    @FXML
    private void add_exams1_click(ActionEvent event){
       add_exams(mandatory_combo, mandatory_exams);
    }
    @FXML
    private void add_exams2_click(ActionEvent event){
       add_exams(choice_combo, choice_exams);
    }
    @FXML
    private void add_exams3_click(ActionEvent event){
       add_exams(entrance_combo, entrance_exams);
    }
    
    @FXML
    private void delete_exams1_click(ActionEvent event){
       delete_exams(mandatory_combo, mandatory_exams);
    }
    @FXML
    private void delete_exams2_click(ActionEvent event){
       delete_exams(choice_combo, choice_exams);
    }
    @FXML
    private void delete_exams3_click(ActionEvent event){
       delete_exams(entrance_combo, entrance_exams);
    }
    
    public void add_exams(ComboBox combo, TextArea area){
        if(combo.getValue() != null){
            if(area.getText().length()>0){
                List<String> items = new ArrayList<String>(Arrays.asList(area.getText().split("\n")));
                if(!items.contains(combo.getValue())){
                    area.setText(String.join("\n", items)+"\n"+combo.getValue());
                }
            }else{
                area.setText((String) combo.getValue());
            }
        }
    }
    
    public void delete_exams(ComboBox combo, TextArea area){
        if(combo.getValue() !=null){
            if(area.getText().length()>0){
               List<String> items = new ArrayList<String>(Arrays.asList(area.getText().split("\n")));
               if(items.contains(combo.getValue())){
                   items.remove(combo.getValue());
                   area.setText(String.join("\n", items));
               }
            }
        }
    }
    
    @FXML
    private void detail_save_click(ActionEvent event){
        detail_result.setText("");
        if(detail_education.getValue() !=null && detail_offer.getValue() !=null && detail_document.getValue() !=null && detail_forma.getValue() !=null && detail_course.getValue() !=null){
            if(detail_amount.getText().matches("[-+]?\\d+") && detail_contract.getText().matches("[-+]?\\d+") && detail_price.getText().matches("[-+]?\\d+")){
                if(detail_start.getValue() !=null && detail_end.getValue() !=null){
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    LocalDate date1 = detail_start.getValue();
                    String date = formatter.format(date1);
                    LocalDate date2 = detail_end.getValue();
                    date =date +" - "+ formatter.format(date2);
                    Education ed = (Education) detail_education.getSelectionModel().getSelectedItem();
                    Document doc = (Document) detail_document.getSelectionModel().getSelectedItem();
                    if(edit){
                        boolean is_detail = sql.updateDetailSpecialty(id_detail, ed.getId(),detail_forma.getValue().toString(), detail_offer.getValue().toString(), doc.getId(), detail_course.getValue().toString(), date, detail_amount.getText(), detail_contract.getText(), detail_price.getText());
                        if(is_detail){
                            back3_click();
                            setDetail(id_dependence);
                        }else{
                            detail_result.setText("Дані не змінені");
                        }
                    }else{
                        boolean is_detail = sql.addDetails(id_dependence, ed.getId(),detail_forma.getValue().toString(), detail_offer.getValue().toString(), doc.getId(), detail_course.getValue().toString(), date, detail_amount.getText(), detail_contract.getText(), detail_price.getText());
                        if(is_detail){
                            back3_click();
                            setDetail(id_dependence);
                        }else{
                            detail_result.setText("Дані не внесені");
                        }
                    }
                }else{
                    detail_result.setText("Виберіть коректну дату");
                }
            }else{
                detail_result.setText("Введіть коректні дані");
            }
        }else{
            detail_result.setText("Введіть коректні дані");
        }
    }
    
    @FXML
    private void delete_detail_click(ActionEvent event){
        DetailSpecialty ds = specialty_detail.getSelectionModel().getSelectedItem();
        sql.deleteDetails(ds.getId());
        setDetail(id_dependence);
    }
    
    @FXML
    private void back4_click(ActionEvent event){
        back4();
    }
    
    @FXML
    private void save_exams_click(ActionEvent event){
        boolean is_exams = sql.updateExams(id_detail, mandatory_exams.getText(), choice_exams.getText(), entrance_exams.getText());
        if(is_exams){
            back4();
            setDetail(id_dependence);
        }else{
            exams_result.setText("Дані не змінено");
        }
    }
    
    public void back4(){
        worker_panel.toFront();
        mandatory_combo.setValue(null);
        mandatory_combo.getItems().clear();
        choice_combo.setValue(null);
        choice_combo.getItems().clear();
        entrance_combo.setValue(null);
        entrance_combo.getItems().clear();
    }
    
    @FXML
    private void request_click(ActionEvent event){
        DetailSpecialty ds = specialty_request.getSelectionModel().getSelectedItem();
        id_detail=ds.getId();
        request_grid.getChildren().remove(export);
        if(acept == false){
            tableUser(ds.getId(),"очікується"); 
            if(!request_grid.getChildren().contains(reject)){
                request_grid.getChildren().add(reject);
                request_grid.getChildren().add(confirm);
            }
            request_grid.getChildren().remove(look_user);
            request_grid.add(look_user,1,0);
        }else{
            tableUser(ds.getId(),"підтверджено");
            request_grid.getChildren().remove(reject);
            request_grid.getChildren().remove(confirm);
            request_grid.getChildren().remove(look_user);
            request_grid.getChildren().add(export);
            request_grid.add(look_user,3,0);
        }
        tab4.toFront();
    }
    
    @FXML
    private void back5_click(ActionEvent event){
        tab2.toFront();
    }
    
    @FXML
    private void look_user_click(ActionEvent event){
        Request rq = user_request.getSelectionModel().getSelectedItem();
        dataEntrant(rq.getUser_id());
        userZno(rq.getUser_id());
        entrant_info.toFront();
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

            }catch (IOException ex) {
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void back6_click(ActionEvent event){
        tab4.toFront();
    }
    
    @FXML
    private void confirm_click(ActionEvent event){
        Request rq = user_request.getSelectionModel().getSelectedItem();
        if(sql.updateRequest(rq.getId_request())){
            String message = email.confirmRequest();
            email.sendMail(rq.getEmail(), message, "Підтвердження");
            tableUser(id_detail,"очікується");
        }else{
            System.out.println("Помилка");
        }
    }
    
    @FXML
    private void reject_click(ActionEvent event){
        Request rq = user_request.getSelectionModel().getSelectedItem();
        Dialog dialog = new TextInputDialog();
        dialog.setTitle("Відхилення заявки");
        dialog.setHeaderText("Введіть причину відхилення");

        Optional<String> result = dialog.showAndWait();
        String reason = null;

        if (result.isPresent()) {
            reason = result.get();
            if(sql.deleteRequest(rq.getId_request())){
                String message = email.rejectRequest(reason);
                email.sendMail(rq.getEmail(), message, "Відхилено");
                tableUser(id_detail,"очікується");
            }
        }
       
    }
    
    @FXML
    private void export_click(ActionEvent event){
        
        try{
            
            ResultSet rs = sql.userRequest(id_detail, "підтверджено");
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("Export");
            HSSFRow rowhead = sheet.createRow(0);
            
            HSSFCellStyle fontStyle = workbook.createCellStyle();
            HSSFFont font = workbook.createFont();
            font.setBold(true);
            fontStyle.setFont(font);
            rowhead.createCell(0).setCellStyle(fontStyle);
            rowhead.createCell(1).setCellStyle(fontStyle);
            rowhead.createCell(2).setCellStyle(fontStyle);
            rowhead.createCell(3).setCellStyle(fontStyle);
            rowhead.createCell(4).setCellStyle(fontStyle);
            rowhead.getCell(0).setCellValue("Прізвише");
            rowhead.getCell(1).setCellValue("Ім’я");
            rowhead.getCell(2).setCellValue("Стать");
            rowhead.getCell(3).setCellValue("Дата народження");
            rowhead.getCell(4).setCellValue("Телефон");
            
            int i = 1;
            while (rs.next()){
                HSSFRow row = sheet.createRow((short) i);
                row.createCell(0).setCellValue(rs.getString("data_entrant.last_name"));
                row.createCell(1).setCellValue(rs.getString("data_entrant.name"));
                row.createCell(2).setCellValue(rs.getString("data_entrant.sex"));
                row.createCell(3).setCellValue(rs.getString("data_entrant.birthday"));
                row.createCell(4).setCellValue(rs.getString("data_entrant.phone"));
                i++;
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath()+"\\export.xls";
            FileOutputStream fileOut = new FileOutputStream(path);
            workbook.write(fileOut);
            fileOut.close();
        }catch (Exception e1) {
            e1.printStackTrace();
        }
    }
    
    
    
    
    
    private void setSpecialty(){
        ObservableList<Specialty> data_specialty = FXCollections.observableArrayList();
        ResultSet rs = sql.workerSpecialty();
        if(rs != null){
            try {
                while(rs.next()){
                    data_specialty.add(new Specialty("", rs.getString(1), rs.getString(2), rs.getString(3), getButton(rs.getString(1), review_specialty_click, "review")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            specialty_col1.setCellValueFactory(new PropertyValueFactory<>("code"));
            specialty_col2.setCellValueFactory(new PropertyValueFactory<>("name"));
            specialty_col3.setCellValueFactory(new PropertyValueFactory<>("edit"));
            this.data_specialty.setItems(data_specialty);
        }
    }
    
    private Button getButton(String id, EventHandler<ActionEvent> click, String style){
        Button edit = new Button();
        edit.setId(id);
        edit.setPrefWidth(40);
        edit.getStyleClass().add(style);
        edit.setOnAction(click);
        return edit;
    }
    EventHandler<ActionEvent> review_specialty_click = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            Button bt = (Button) e.getSource();
            id_dependence=sql.idDependence(user.getdvnz(), user.getfaculty(), bt.getId());
            setDetail(id_dependence);
            tab3.toFront();
        }
    };
    
    private void setDetail(String id){
        ObservableList<DetailSpecialty> detail_specialty = FXCollections.observableArrayList();
        ResultSet rs = sql.detailSpecialty(id);
        if(rs != null){
            try {
                while(rs.next()){
                    String exams1 = "", exams2="", exams3="";
                    if(rs.getString(13) != null){
                        exams1=rs.getString(13);
                    }
                    if(rs.getString(14) != null){
                        exams2=rs.getString(14);
                    }
                    if(rs.getString(15) != null){
                        exams3=rs.getString(15);
                    }
                    detail_specialty.add(new DetailSpecialty(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10),rs.getString(11),rs.getString(12),exams1,exams2,exams3,getButton(rs.getString(1),edit_detail_click, "edit"),""));
                }
            } catch (SQLException ex) {
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            detail_col1.setCellValueFactory(new PropertyValueFactory<>("education_name"));
            detail_col2.setCellValueFactory(new PropertyValueFactory<>("forma"));
            detail_col3.setCellValueFactory(new PropertyValueFactory<>("offer"));
            detail_col4.setCellValueFactory(new PropertyValueFactory<>("document_name"));
            detail_col5.setCellValueFactory(new PropertyValueFactory<>("edit"));
            this.specialty_detail.setItems(detail_specialty);
        }
    }
    EventHandler<ActionEvent> edit_detail_click = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e)
        {
            add_detail_panel.toFront();
            edit=true;
            Button bt = (Button) e.getSource();
            id_detail = bt.getId();
            ResultSet rs = sql.editDetailSpecialty(bt.getId());
            try {
                while(rs.next()){
                    detail_forma.setValue(rs.getString("forma"));
                    detail_offer.setValue(rs.getString("offer"));
                    detail_course.setValue(rs.getString("course"));
                    Document doc = new Document(rs.getString("document.id"), rs.getString("document.document"), null);
                    detail_document.setValue(doc);
                    Education ed = new Education(rs.getString("education_level.id"), rs.getString("education_level.education"), null, null);
                    detail_education.setValue(ed);
                    detail_amount.setText(rs.getString("amount"));
                    detail_contract.setText(rs.getString("contract"));
                    detail_price.setText(rs.getString("price"));
                    String[] date = rs.getString("term").split(" - ");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    
                    LocalDate localDate = LocalDate.parse(date[0], formatter);
                    detail_start.setValue(localDate);
                    
                    localDate = LocalDate.parse(date[1], formatter);
                    detail_end.setValue(localDate);
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    
    private void dataComboBox(){
        detail_forma.getItems().addAll("Денна", "Заочна");
        detail_offer.getItems().addAll("Фіксована", "Відкрита, з пріоритетом","Фіксована, з пріоритетом","Небюджетна");
        detail_course.getItems().addAll("1 курс","2 курс", "3 курс", "4 курс");
        setDocument();
        setEducation();
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
                    Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
                }

                detail_document.setItems(data_document);
            }
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
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            
            this.detail_education.setItems(data_education);
        }
    }
    
    private void tableRequest(){
        ObservableList<DetailSpecialty> detail_specialty = FXCollections.observableArrayList();
        ResultSet rs = sql.detailSpecialtyRequest(user.getdvnz(), user.getfaculty());
        if(rs != null){
            try {
                while(rs.next()){
                    detail_specialty.add(new DetailSpecialty(rs.getString(1), "", rs.getString(3), rs.getString(4), rs.getString(5), "", rs.getString(6),"", "", "","","","","","",null, rs.getString(2)));
                }
            } catch (SQLException ex) {
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            specialty_request_col1.setCellValueFactory(new PropertyValueFactory<>("specialty"));
            specialty_request_col2.setCellValueFactory(new PropertyValueFactory<>("education_name"));
            specialty_request_col3.setCellValueFactory(new PropertyValueFactory<>("forma"));
            specialty_request_col4.setCellValueFactory(new PropertyValueFactory<>("offer"));
            specialty_request_col5.setCellValueFactory(new PropertyValueFactory<>("document_name"));
            
            this.specialty_request.setItems(detail_specialty);
        }
    }
    
    private void tableUser(String id_specialty, String status){
        ObservableList<Request> user_request = FXCollections.observableArrayList();
        ResultSet rs = sql.userRequest(id_specialty, status);
        if(rs != null){
            try {
                while(rs.next()){
                    user_request.add(new Request(rs.getString(1), rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8)));
                }
            } catch (SQLException ex) {
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            user_col1.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            user_col2.setCellValueFactory(new PropertyValueFactory<>("name"));
            user_col3.setCellValueFactory(new PropertyValueFactory<>("sex"));
            user_col4.setCellValueFactory(new PropertyValueFactory<>("phone"));
            this.user_request.setItems(user_request);
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
                if(rs.getString("rating").length()>0){
                    rating_data.setText(rs.getString("rating"));
                    rating.setText("Середній бал: ");
                }else{
                    rating.setText("");
                }
                
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
            Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void userZno(String id_user){
        ResultSet rs =sql.dataZNO(id_user);
        ObservableList<ZNO> zno = FXCollections.observableArrayList();
        if(rs != null){
            try {
                while(rs.next()){
                    zno.add(new ZNO(rs.getString("id"), rs.getString("year"), rs.getString("certificate"), rs.getString("subject"), rs.getString("pincode"), rs.getString("rating")));
                }
            } catch (SQLException ex) {
                Logger.getLogger(WorkerController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            zno_col1.setCellValueFactory(new PropertyValueFactory<>("year"));
            zno_col2.setCellValueFactory(new PropertyValueFactory<>("certificate"));
            zno_col3.setCellValueFactory(new PropertyValueFactory<>("subject"));
            zno_col4.setCellValueFactory(new PropertyValueFactory<>("pincode"));
            zno_col5.setCellValueFactory(new PropertyValueFactory<>("rating"));
            this.zno_data.setItems(zno);
        }
    }
    
}
