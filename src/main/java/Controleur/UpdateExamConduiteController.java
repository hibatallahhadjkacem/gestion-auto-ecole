package Controleur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import Auto_Ecolee.Auto_Ecolee.App;
import Dao.ExamenCodeDAO;
import Entities.ExamenConduite;
import Entities.Res;
import Service.CondidatService;
import Service.ExamenConduiteService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class UpdateExamConduiteController {
	@FXML
	private TextField num;
	@FXML private TextField condidat;
    @FXML private TextField moniteur;
    @FXML private TextField vehicule;
    @FXML private TextField fraisField;
    @FXML private DatePicker dateExam;
    @FXML private RadioButton pass;
    @FXML private RadioButton fail;
	@FXML
	private TextField stime;
	@FXML
	private TextField etime;
    @FXML
     private TextField adressField;
    
    @FXML
    private TextField latField;      
    @FXML
    private TextField lngField;	
    @FXML
    private ImageView mapView;
	private CondidatService condidatService = new CondidatService();
	private ExamenConduiteService examenCodeService = new ExamenConduiteService();
	 @FXML
 	public void initialize() {
     	
     	    adressField.setDisable(true);
     	    latField.setDisable(true);
     	    lngField.setDisable(true);
     	
 	    dateExam.valueProperty().addListener((observable, oldValue, newValue) -> {
 	        if (newValue != null && newValue.isAfter(LocalDate.now())) {
 	            pass.setDisable(true);
 	            fail.setDisable(true);
 	        } else {
 	            pass.setDisable(false);
 	            fail.setDisable(false);
 	        }
 	    });
 	    Tooltip tooltip1 = new Tooltip("Select adress");
     	

 	    mapView.setOnMouseEntered(e -> {
 	        tooltip1.show(mapView, e.getScreenX(), e.getScreenY() + 10);
 	    });

 	    mapView.setOnMouseExited(e -> tooltip1.hide());

 	}






		@FXML
		public void openMapWindow(MouseEvent event) throws IOException {
		    
		    Stage mapStage = new Stage();

		    FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/nermine/mapWindow.fxml"));
		    Parent root = loader.load();
		    
		    
		    MapWindowController mapController = loader.getController();
		    
		   
		    mapController.setParentFields(latField,lngField,adressField);
		    
		  
		    Scene mapScene = new Scene(root);
		    mapStage.setTitle("Map Window");
		    mapStage.setScene(mapScene);
		    mapStage.show();
		}
		    
		@FXML
		private void update() {
				String numero = num.getText();
		        LocalDate date = dateExam.getValue();
		        String startTimeStr = stime.getText();
			    String endTimeStr = etime.getText();
			    String moniteur_id= moniteur.getText();
			    String condidat_num= condidat.getText();
			    String vehicule_immat= vehicule.getText();
			    Res resultat = null;
			    String frais= fraisField.getText();
			    String latitude = latField.getText();
		        String longitude = lngField.getText();
		        String adress = adressField.getText();
			    
		        if (numero.isEmpty() || !numero.matches("\\d+") || Integer.parseInt(numero) < 1) {
		            showAlert("Error", "Exam number ivalid!", AlertType.ERROR);
		            return;
		        }
			    if(!isValidDate(date)) {
		        	showAlert("Error", " Invalid Date !", AlertType.ERROR);
		        	return;
		        }// Validate the start and end times
			    LocalTime startTime = null;
			    LocalTime endTime = null;

			    try {
			        startTime = LocalTime.parse(startTimeStr);
			        endTime = LocalTime.parse(endTimeStr);
			    } catch (DateTimeParseException e) {
			        showAlert("Error", "Invalid time format! Please use HH:mm format.", AlertType.ERROR);
			        return;
			    }

			    // Ensure start time is before end time
			    if (startTime.isAfter(endTime)) {
			        showAlert("Error", "Start time cannot be after end time.", AlertType.ERROR);
			        return;
			    }

			   

			    if (moniteur_id.isEmpty() || !moniteur_id.matches("\\d+") || Integer.parseInt(moniteur_id) < 1) {
			        showAlert("Error", "Instructor id invalid!", AlertType.ERROR);
			        return;
			    }if((ExamenCodeDAO.findMoniteurById(Integer.parseInt(moniteur_id))==null)) {
			    	showAlert("Error", "Instructor does not exist!", AlertType.ERROR);
			    }
			    if (condidat_num.isEmpty() || !condidat_num.matches("\\d+") || Integer.parseInt(condidat_num) < 1) {
			        showAlert("Error", "Candidate Number invalid!", AlertType.ERROR);
			        return;
			    }
			    if (CondidatService.getCondidatById(Integer.parseInt(condidat_num)) == null) {
			        showAlert("Error", "Candidate number does not exist!", AlertType.ERROR);
			        return;
			    }if (adressField.getText().isEmpty()) {
			        showAlert("Error", "Please select an address!", AlertType.ERROR);
			        return;
			    }
			    if (date.isAfter(LocalDate.now())) {
			        resultat = Res.Pending;
			    } else {
			        if (pass.isSelected()) {
			            resultat = Res.Pass;
			        } else if (fail.isSelected()) {
			            resultat = Res.Fail;
			        } else {
			            showAlert("Error", "Please select a result!", AlertType.ERROR);
			            return;
			        }
			    }
			    
			    if(frais.isEmpty()) {
			    	showAlert("Error", "Costs invalid !", AlertType.ERROR);
			    	return;
			    	}
			    ExamenConduite examenConduite = new ExamenConduite(0, date, startTime, endTime, Integer.parseInt(moniteur_id), Integer.parseInt(condidat_num), vehicule_immat, resultat,Double.parseDouble(latitude),Double.parseDouble(longitude), adress,Double.parseDouble(frais));
			    if(ExamenConduiteService.updateExam(examenConduite)) {
			        showAlert("Success", "Exam updated succesfully.", AlertType.INFORMATION);
			     		 }else {
			     			 showAlert("Error", "Error while updating Exam", AlertType.ERROR);
			     		 }
		    
	}  public static boolean isValidDate(LocalDate date) {
        try {
            return date != null;
        } catch (DateTimeParseException e) {
            return false;
        }
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

    @FXML
    private void back() throws IOException {

        App.setRoot("afficheExam");
    }
    @FXML
    private void Exam() throws IOException {
    	App.setRoot("examen");
    }
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
}
