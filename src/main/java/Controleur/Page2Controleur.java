package Controleur;

import java.io.IOException;

import Auto_Ecolee.Auto_Ecolee.App;
import Service.loginService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Page2Controleur {
	@FXML
	private TextField userName;
	@FXML
	private PasswordField mdp;
	@FXML
	private Text er1;
	@FXML
	private Text er2;
	@FXML
	private Text er3;
	
	private loginService loginService=new loginService();
	
	@FXML
	private void login() throws IOException {
		String user = userName.getText().trim();
		String pwd = mdp.getText().trim();
		
		if(user.isEmpty()) {
			afficherMessageTemporaire(er1, "Please enter your username", 2);
		}
		else if(pwd.isEmpty()) {
			afficherMessageTemporaire(er2, "Please enter your password", 2);
		} else if(!user.isEmpty() && loginService.foundUser(user)==0) {
			afficherMessageTemporaire(er1, "User not found", 2);
		}/* else if(!pwd.isEmpty() && loginService.foundMdp(pwd)==0) {
			afficherMessageTemporaire(er2, "Incorrect password", 2);
		}*/else {
			if(loginService.found2(user,pwd)==1) {
				App.setRoot("Home");
			}else {
				
				afficherMessageTemporaire(er3, "Your username or password is incorrect. Please try again", 2);
			}
		}
		
	}
	
	// Créer un Timeline pour effacer le texte après tp
		private void afficherMessageTemporaire(Text textElement, String message, int duree) {
		    textElement.setText(message);
		    Timeline timeline = new Timeline(
		        new KeyFrame(Duration.seconds(duree), e -> textElement.setText(""))
		    );
		    timeline.setCycleCount(1); // Exécuter une seule fois
		    timeline.play(); // Lancer le timer
		}
	
	


}
