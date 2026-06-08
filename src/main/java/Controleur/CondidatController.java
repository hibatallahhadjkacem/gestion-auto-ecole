package Controleur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.*;
import Service.CondidatService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class CondidatController {
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private DatePicker date;
    @FXML
    private TextField adresseField;
    @FXML
    private TextField telField;
    @FXML
    private TextField emailField;
    @FXML
    private RadioButton typeA,typeB,typeC;
   
    @FXML
    private Button addCondidat;
 

    private CondidatService condidatService = new CondidatService();

    @FXML
    private void addCondidat() {
      
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        LocalDate dateNais = date.getValue();
        String adresse = adresseField.getText();
        String tel = telField.getText();
        String email = emailField.getText();
        Type typePermi = null;
        

    
        
        if (nom.isEmpty() || !nom.matches("[a-zA-Z- ]+")) { 
            showAlert("Error", "Invalid lastname!", AlertType.ERROR);
            return;
        } 

        if (prenom.isEmpty() || !prenom.matches("[a-zA-Z ]+")) {  // Allow spaces
            showAlert("Error", "Invalid name!", AlertType.ERROR);
            return;
        }
        

        if(!isValidDate(dateNais)||!isValidBirthDate(dateNais)) {
        	showAlert("Error", " Invalid Birth Date !", AlertType.ERROR);
        	return;
        }
        if(adresse.isEmpty() || !adresse.matches("[a-zA-Z0-9\\s,.-]+")) {
        	showAlert("Error", " Invalid adresse !", AlertType.ERROR);
        	return;
        }if(!tel.matches("^[259]\\d{7}$")) {
        	showAlert("Error", " Invalid Phone number !", AlertType.ERROR);
        	return;
        }if(!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
        	showAlert("Error", " Invalid  Email !", AlertType.ERROR);
        	return;
        }
        if(typeA.isSelected()) {
        	 typePermi=Type.A;
        }else
        if(typeB.isSelected()) {
        	 typePermi=Type.B;
        }else
        if(typeC.isSelected()) {
        	 typePermi=Type.C;
        }else {
        	showAlert("Error", "Please select a permit type!", AlertType.ERROR);
        	
        }
        Condidat condidat = new Condidat(
                0,
                nom,
                prenom,
                dateNais, adresse,
                Integer.parseInt(tel),
                email, typePermi      );

      
        if(condidatService.addCondidat(condidat)) {
        showAlert("Success", "Candidate added succesfully.", AlertType.INFORMATION);
     		 }else {
     			 showAlert("Error", "Error while saving candidate", AlertType.ERROR);
     		 }
     		  }
        
  
    
        
    
    public static boolean isValidDate(LocalDate date) {
        try {
            return date != null;
        } catch (DateTimeParseException e) {
            return false;
        }
    } 
    public static boolean isValidBirthDate(LocalDate dateNais) {
        if (dateNais.isAfter(LocalDate.now())) {
            return false;
        }
        int age = calculateAge(dateNais);
        
       
        return age >= 16;
    }
    public static int calculateAge(LocalDate birthdate) {
    
        LocalDate today = LocalDate.now();
        
        
   
        int age = today.getYear() - birthdate.getYear();

  
        return age;
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

        App.setRoot("page1");
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


