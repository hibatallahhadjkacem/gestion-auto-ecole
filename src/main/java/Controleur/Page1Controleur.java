package Controleur;

import java.io.IOException;

import Auto_Ecolee.Auto_Ecolee.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.util.List;

import Dao.AutoInfosDAO;
import Entities.AutoEcoleInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Page1Controleur {
	@FXML
    private Label nameText;
    @FXML
    private Label emailText;
    @FXML
    private Label phoneText;
    @FXML
    private Label addressText;

    private AutoInfosDAO autoEcoleService = new AutoInfosDAO();

    @FXML
    public void initialize() {
        // Fetch all auto école information using the service
        List<AutoEcoleInfo> autoEcoleInfoList = autoEcoleService.findAll();

        // Assuming there's only one record in your table
        if (!autoEcoleInfoList.isEmpty()) {
            AutoEcoleInfo autoEcoleInfo = autoEcoleInfoList.get(0);

            // Set the fetched data to the Text components
            nameText.setText(autoEcoleInfo.getNom());
            emailText.setText("Email: " + autoEcoleInfo.getEmail());
            phoneText.setText("Phone: " + autoEcoleInfo.getTel());
            addressText.setText("Address: " + autoEcoleInfo.getAdress());
        } else {
            // Handle case when no data is found
            nameText.setText("No data available");
            emailText.setText("");
            phoneText.setText("");
            addressText.setText("");
        }
    }
	
	 @FXML
	    private void Exam() throws IOException {
	    	App.setRoot("examen");
	    }
	@FXML
	private void Pagevehicule() throws IOException {
		App.setRoot("Vehicule");
	}
	 @FXML
	    private void moniteur() throws IOException {
	    	App.setRoot("secondary");
	    }
	 @FXML
	    private void sessionCode() throws IOException {
	    	App.setRoot("SeanceCode");
	    }
	 @FXML
	    private void sessionDrive() throws IOException {
	    	App.setRoot("SeanceConduite");
	    }
	 @FXML
	    private void payment() throws IOException {
	    	App.setRoot("paiementController");
	    }
	@FXML
	private void PageCondidat() throws IOException {
		App.setRoot("page1");
}
}
