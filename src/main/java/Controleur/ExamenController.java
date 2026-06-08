package Controleur ;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.ExamenCode;
import Entities.ExamenConduite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import Service.ExamenCodeService;
import Service.ExamenConduiteService;

public class ExamenController implements Initializable {

    @FXML
    private TableView<Map.Entry<Integer, Object[]>> table;

    @FXML
    private TableColumn<Map.Entry<Integer, Object[]>, Integer> condidat;

    @FXML
    private TableColumn<Map.Entry<Integer, Object[]>, LocalDate> code;

    @FXML
    private TableColumn<Map.Entry<Integer, Object[]>, LocalDate> conduite;
    @FXML
    private ImageView afficheButton;
  
    private ExamenCodeService exmenCodeService=new ExamenCodeService();
    private ExamenConduiteService examenConduiteService= new ExamenConduiteService();
    private List<ExamenCode> listeExamenCode= exmenCodeService.getAllExams();
    private List<ExamenConduite> listeExamenConduite= examenConduiteService.getAllExams(); 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    	 Tooltip tooltip1 = new Tooltip("View Exams details");
    	

    	    afficheButton.setOnMouseEntered(e -> {
    	        tooltip1.show(afficheButton, e.getScreenX(), e.getScreenY() + 10);
    	    });

    	    afficheButton.setOnMouseExited(e -> tooltip1.hide());

    	    
    	
    
        condidat.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(
                () -> cellData.getValue().getKey()));

        code.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(
                () -> (LocalDate) cellData.getValue().getValue()[0]));

        conduite.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createObjectBinding(
                () -> (LocalDate) cellData.getValue().getValue()[1]));

           loadExamData();
    }

    private void loadExamData() {
        Map<Integer, Object[]> examMap = new HashMap<>();
        for (ExamenCode ec : listeExamenCode) {
            examMap.put(ec.getCondidat_num(), new Object[]{ec.getDate(), null});
        }

        for (ExamenConduite exConduite : listeExamenConduite) {
            examMap.compute(exConduite.getCondidat(), (key, value) -> {
                if (value == null) {
                    return new Object[]{null, exConduite.getDate()};
                }
                value[1] = exConduite.getDate();
                return value;
            });
        }

        ObservableList<Map.Entry<Integer, Object[]>> examensList = FXCollections.observableArrayList(examMap.entrySet());

        table.setItems(examensList);
    }
    @FXML
    private void addCodeExam() throws IOException {
    	App.setRoot("examenCode");
    }
    @FXML
    private void addConduiteExam(ActionEvent event) throws IOException {
    	App.setRoot("examenConduite");
    }
    @FXML
    private void DeleteAll(ActionEvent event) {
    		ObservableList<Map.Entry<Integer, Object[]>> examItems = table.getItems();
    	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	    alert.setTitle("Delete All Exam Data");
    	    alert.setHeaderText("Are you sure you want to delete all exam codes and exam conduites?");
    	    alert.setContentText("This action cannot be undone.");
    	    
    	    Optional<ButtonType> result = alert.showAndWait();
    	    if (result.isPresent() && result.get() == ButtonType.OK) {
    	        boolean examCodeDeleted = ExamenCodeService.deleteAllExamCode(); // Suppression des exam codes
    	        boolean examConduiteDeleted = ExamenConduiteService.deleteAllExamConduite(); // Suppression des exam conduites
    	        
    	        if (examCodeDeleted && examConduiteDeleted) {
    	        	examItems.clear();
    	           
    	            showAlert("Success", "Code Exam and driving exam deleted successfully!", Alert.AlertType.INFORMATION);
    	        } else {
    	            showAlert("Error", "Failed to delete exam code Exam and driving exam .", Alert.AlertType.ERROR);
    	        }
    	    }
    }
 
    @FXML
    private void affiche(MouseEvent event) throws IOException {
    	App.setRoot("afficheExam");
    }
    @FXML
    private void Exam() throws IOException {
    	App.setRoot("examen");
    }

    @FXML
    private void home() throws IOException {
    	App.setRoot("Home");
    }
    @FXML
    private void Condidat() throws IOException {
    	App.setRoot("page1");
    }
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
   

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
}
