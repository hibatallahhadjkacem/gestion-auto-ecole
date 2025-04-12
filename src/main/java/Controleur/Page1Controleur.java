package Controleur;

import java.io.IOException;

import Auto_Ecolee.Auto_Ecolee.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class Page1Controleur {
	@FXML
	private void pageScCode() throws IOException {
		App.setRoot("SeanceCode"); 
	}
	
	@FXML
	private void PageScCond() throws IOException {
		App.setRoot("SeanceConduite"); 
	}
	
	@FXML
	private void Pagevehicule() throws IOException {
		App.setRoot("Choix"); 
	}
	
	

	

}
