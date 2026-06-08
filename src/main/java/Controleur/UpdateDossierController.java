package Controleur;

import java.io.File;
import java.io.IOException;

import Auto_Ecolee.Auto_Ecolee.App;
import Dao.DossierDAO;
import Entities.Dossier;
import Service.DossierService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class UpdateDossierController {
	@FXML
	private TextField cin;
	@FXML
	private TextField img; 
	@FXML
	private TextField certifMed; 
	private DossierService dossierService = new DossierService();
	private static int numCondidat;
	@FXML
	private void updateDossier() throws IOException {
		 String cinPath = cin.getText();
		 String photoPath = img.getText();
		 String certifPath = certifMed.getText();
		
		 if(cinPath.isEmpty()) {
			 showAlert("Error", "Please enter the CIN", AlertType.ERROR);
			 return;}
		 if(photoPath.isEmpty()) {
			 showAlert("Error", "Please enter the Photo", AlertType.ERROR);
			 return;}
		 if(certifPath.isEmpty()) {
			 showAlert("Error", "Please enter the Medical_certificate", AlertType.ERROR);
			 return;
		 } 
		 byte[] cinImg = DossierDAO.convertFileToByteArray(cinPath);
		 byte[] photo = DossierDAO.convertFileToByteArray(photoPath);
		 byte[] certif = DossierDAO.convertFileToByteArray(certifPath);
		 System.out.println(numCondidat );
		 Dossier dossier =new Dossier( cinImg, photo, certif, numCondidat);
		  if(dossierService.updateDossier(dossier)) {
			  System.out.println("File updated succesfully.");
	        showAlert("Success", "File updated succesfully.", AlertType.INFORMATION);
		 }else {
			 System.out.println("Error while updating File.");
			 showAlert("Error", "Error while updating File.", AlertType.ERROR);
		 }
		  }
	@FXML
	private void chooseFile() {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Sélectionner un fichier");

	    fileChooser.getExtensionFilters().addAll(
	        new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
	        new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
	    );

	    File selectedFile = fileChooser.showOpenDialog(null);
	    if (selectedFile != null) {
	        cin.setText(selectedFile.getAbsolutePath()); 
	    }
	}
	@FXML
	private void chooseFile1() {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Sélectionner un fichier");

	    fileChooser.getExtensionFilters().addAll(
	        new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
	        new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
	    );

	    File selectedFile = fileChooser.showOpenDialog(null);
	    if (selectedFile != null) {
	        img.setText(selectedFile.getAbsolutePath()); 
	    }
	}
	@FXML
	private void chooseFile2() {
	    FileChooser fileChooser = new FileChooser();
	    fileChooser.setTitle("Sélectionner un fichier");

	    fileChooser.getExtensionFilters().addAll(
	        new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif"),
	        new FileChooser.ExtensionFilter("Tous les fichiers", "*.*")
	    );

	    File selectedFile = fileChooser.showOpenDialog(null);
	    if (selectedFile != null) {
	    	certifMed.setText(selectedFile.getAbsolutePath()); 
	    }
	}
	

	public static void setNumCondidat(int num) {
	    numCondidat = num;
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

	        App.setRoot("affiche");
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
	   }
}
