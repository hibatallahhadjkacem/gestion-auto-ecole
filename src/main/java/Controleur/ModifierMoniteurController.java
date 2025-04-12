package Controleur;


import java.io.IOException;

import Auto_Ecolee.Auto_Ecolee.App;
import Service.Moniteur_Service;
import javafx.event.ActionEvent; 
import Entities.Moniteur;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.*;

public class ModifierMoniteurController {


	    private static Moniteur moniteur; // Variable statique pour stocker l'objet Moniteur
	    private Moniteur_Service moniteurService = new Moniteur_Service();
		private Stage stage;
	    private PrimaryController primaryController; 
	    public void setStage(Stage stage) {
	        this.stage = stage;
	    }


	    @FXML
	    private TextField tf_nom;
	    @FXML
	    private TextField tf_prenom;
	    @FXML
	    private TextField tf_email;
	    @FXML
	    private TextField tf_phone;
	    @FXML
	    private RadioButton male;
	    @FXML
	    private RadioButton female;
	    @FXML
	    private DatePicker date;
	    @FXML
	    private Button btn_insts;
	    @FXML
	    private Button btn_save;
	    
	    
	    
	    @FXML
	    private void switchToPrimary() throws IOException {
	    	System.out.println("Bouton 'Instructors' cliqué !");
	        App.setRoot("primary");  
	    }

	    // Méthode pour initialiser les champs de saisie avec les informations du moniteur
	
	    public void setMoniteur(Moniteur moniteur) {
	        this.moniteur = moniteur;

	        if (tf_nom != null) {
	            tf_nom.setText(moniteur.getNom());
	        } else {
	            System.out.println("Erreur : tf_nom est null !");
	        }

	        if (tf_prenom != null) {
	            tf_prenom.setText(moniteur.getPrenom());
	        } else {
	            System.out.println("Erreur : tf_prenom est null !");
	        }

	        if (tf_email != null) {
	            tf_email.setText(moniteur.getEmail());
	        }

	        if (tf_phone != null) {
	            tf_phone.setText(moniteur.getPhone());
	        }

	        if (date != null) {
	            date.setValue(moniteur.getDateRecrutement());
	        }

	        if (moniteur.getSexe().equals("Male")) {
	            if (male != null) male.setSelected(true);
	        } else {
	            if (female != null) female.setSelected(true);
	        }
	    }


	    
	    
	    
	  
	    
	    
	 
	    
	    
	    
	    // Méthode pour sauvegarder les modifications
	    @FXML
	    public void Save(ActionEvent event) {
	        // Mettre à jour les informations du moniteur et sauvegarder dans la base de données
	        moniteur.setNom(tf_nom.getText());
	        moniteur.setPrenom(tf_prenom.getText());
	        moniteur.setEmail(tf_email.getText());
	        moniteur.setPhone(tf_phone.getText());
	        moniteur.setSexe(male.isSelected() ? "Male" : "Female");
	        moniteur.setDateRecrutement(date.getValue());
	        
	        // Appel à la méthode de sauvegarde dans la base de données
	        boolean success = moniteurService.updateMoniteur(moniteur);
           
	        
	        if (success) {
	            showAlert("Modifications enregistrées avec succès !");       
	           
	        } else {
	            showAlert("Erreur lors de la mise à jour.");
	        }
	        
	       

	        // Fermer la fenêtre après l'alerte
	        if (stage != null) {
	            stage.close();
	        }
	       
	           
	      
	        
	    }
	    
	    private void showAlert(String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("Information");
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	}

	
