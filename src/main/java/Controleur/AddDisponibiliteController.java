package Controleur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.time.LocalTime;
//import org.fsb.proj_serious_version.weeklyPlannerController;
import Dao.Disponibilite_Dao;
import Service.Disponibilite_Service;
import Entities.Disponibilite;
public class AddDisponibiliteController {
	
	
	
	 private int moniteurId;

	    @FXML
	    private DatePicker datePicker;

	    @FXML
	    private TextField startTimeField;

	    @FXML
	    private TextField endTimeField;

	    @FXML
	    private ComboBox<String> statusComboBox;

	    @FXML
	    private Button saveButton;
        private Disponibilite_Service disponibiliteService;
	    
	    
	    public AddDisponibiliteController() {
	        disponibiliteService = new Disponibilite_Service();
	    }
	    
	    
	    
	    

	    public void setMoniteurId(int id) {
	        this.moniteurId = id;
	    }

	    @FXML
	    public void initialize() {
	    	// disponible ->1
	    	// not disponible ->0
	    	statusComboBox.getItems().addAll("Occupied", " Not Occupied");
	        statusComboBox.setValue("Occupied");
	        
	    }
	    
	    
	    

	    @FXML
	    private void handleSave(ActionEvent event) {
	   
	        try {
	            LocalDate date = datePicker.getValue();
	            LocalTime startTime = LocalTime.parse(startTimeField.getText());
	            LocalTime endTime = LocalTime.parse(endTimeField.getText());
	            Boolean availability;
	            if(statusComboBox.getValue()=="Occupied") {
	            	 availability =true;
	            		
	            }
	            else { availability =false;}
	            
	            
	           // Boolean availability = statusComboBox.getValue();

	            // Vérifier la validité des horaires
	            if (startTime.isAfter(endTime)) {
	                showAlert("Erreur", "L'heure de début doit être avant l'heure de fin.");
	                return;
	            }

	            //Vérifier si le moniteur est disponible pendant cette période
	            System.out.println(disponibiliteService.isNotTimeFree(moniteurId, date, startTime, endTime));
	           
	            if (disponibiliteService.isNotTimeFree(moniteurId, date, startTime, endTime)==true) {
	            	 System.out.println(disponibiliteService.isNotTimeFree(moniteurId, date, startTime, endTime));
	                showAlert("Erreur", "  cette période est deja reserveé.");
	                return;
	            }

	            
	            LocalDate dateRecrutement = disponibiliteService.getDateRecrutement(moniteurId);
		        if (dateRecrutement == null) {
		        	showAlert("Erreur "," Impossible de récupérer la date de recrutement.");
		            return;
		        }
		            
		            if (!date.isAfter(dateRecrutement)) {
		            	showAlert("Erreur" ," La date de la séance doit être après la date de recrutement.");
			            return;
			        }
		            
   

	            // Ajouter la disponibilité dans la base de données
	            Disponibilite newDispo = new Disponibilite(moniteurId, date, startTime, endTime, availability);
	            if (disponibiliteService.addDisponibilite(newDispo)){
	                showAlert("Succès", "Disponibilité ajoutée avec succès.");
	                  
	            closeWindow();
	              
                    
	                
	            } else {
	                showAlert("Erreur", "Une erreur s'est produite lors de l'ajout de la disponibilité.");
	            }
	        } catch (Exception e) {
	            showAlert("Erreur", "Veuillez vérifier les informations saisies.");
	        }
	    }

	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }

	    private void closeWindow() {
	    	
	        Stage stage = (Stage) saveButton.getScene().getWindow();
	        stage.close();
	    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
