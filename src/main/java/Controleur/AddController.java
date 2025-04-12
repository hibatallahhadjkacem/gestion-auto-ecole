package Controleur;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import Auto_Ecolee.Auto_Ecolee.App;
import Service.Moniteur_Service;
import Entities.Moniteur;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AddController {
	
    @FXML
    private TextField tf_nom;

    @FXML
    private TextField tf_prenom;
    @FXML
    private DatePicker date;
    @FXML
    private TextField tf_email;
    @FXML
    private TextField tf_phone;
    @FXML
    private RadioButton male;  // Lier le RadioButton pour "Male"
    
    @FXML
    private RadioButton female;  // Lier le RadioButton pour "Female"
    
    @FXML
    private ToggleGroup sexeToggleGroup;  // Le groupe de boutons radio
    
    
	
	private Moniteur_Service moniteurService = new Moniteur_Service();
	
	
    @FXML
    public void initialize_radiobutton() {
        // Assigner les RadioButtons à un ToggleGroup pour qu'un seul soit sélectionné à la fois
    	sexeToggleGroup = new ToggleGroup();
        male.setToggleGroup(sexeToggleGroup);
        female.setToggleGroup(sexeToggleGroup);
    }
  
	
	
    @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
   
   

    @FXML
    private void ajouterMoniteur_C() {
        String nom = tf_nom.getText();
        String prenom = tf_prenom.getText();
        String sexe = male.isSelected() ? "Male" : "Female";
        String email = tf_email.getText();
        String phone = tf_phone.getText();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || phone.isEmpty()) {
        	afficherAlerte("Error", "Please fill in all the fields!");
            return;
        }

        // Phone number validation: must be exactly 8 digits
        if (!phone.matches("\\d{8}")) {
        	afficherAlerte("Error", "Phone number must contain exactly 8 digits.");
            return;
        }

        LocalDate dateRecrutement = date.getValue();
        if (dateRecrutement == null) {
        	afficherAlerte("Error", "Please select a recruitment date.");
            return;
        }

        Moniteur moniteur = new Moniteur(nom, prenom, sexe, email, phone, dateRecrutement);

        try {
            int generatedId = moniteurService.addMoniteur(moniteur);
            if (generatedId > 0) {
            	afficherAlerte("Success", "Instructor has been added successfully!");

                // Reset fields
                tf_nom.clear();
                tf_prenom.clear();
                tf_email.clear();
                tf_phone.clear();
                date.setValue(null);
                sexeToggleGroup.selectToggle(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Error", "An error occurred while adding the instructor: " + e.getMessage());
        }
    }

    
    
    

    
                
            
       
    

    private void afficherAlerte(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
