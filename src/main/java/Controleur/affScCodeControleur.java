package Controleur;

import java.io.IOException;
import java.util.List;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.SeanceCode;
import Entities.SeanceConduite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.util.Duration;

public class affScCodeControleur {
	@FXML
	private TextField num;
	@FXML
	private Text date;
	@FXML
	private Text time;
	@FXML
	private Text monit;
	@FXML
	private Text monitId;
	@FXML
	private Text condid;
	@FXML
	private Text condidId;
	@FXML
    private Text er1;
	@FXML
	private Button btnAffTous;
	
	private SeanceCodeControleur seanceCodeControleur=new SeanceCodeControleur();
	private SeanceConduiteControleur seanceConduiteControleur=new SeanceConduiteControleur();
	
	
	@FXML
	public void initialize() {
        Tooltip tooltip = new Tooltip("View all the driving session details");
        btnAffTous.setTooltip(tooltip);
        tooltip.setShowDelay(Duration.ZERO);
	}
	@FXML
    private void show() throws IOException {
        String nums = num.getText().trim();
        if (nums.isEmpty() || !nums.matches("\\d+")) { 
            afficherMessageTemporaire(er1, "You must fill in the Session number with a number", 2);
        } else if (!nums.isEmpty() && seanceCodeControleur.rechNumSc(Integer.parseInt(nums)) == 0) {
            afficherMessageTemporaire(er1, "This code session is not found", 2);
        } else {
        	List<SeanceCode> codes=  seanceCodeControleur.getScCodeByNum(Integer.parseInt(nums));
        	SeanceCode seanceCode = codes.get(0);
        	date.setText(seanceCode.getDate().toString());
        	time.setText(seanceCode.getTemp().toString());
        	monit.setText(seanceConduiteControleur.getMoniteur(seanceCode.getIdMoniteur()));
        	condid.setText(seanceConduiteControleur.getCondidat(seanceCode.getIdcondidat()));
        	monitId.setText(String.valueOf(seanceCode.getIdMoniteur()));
        	condidId.setText(String.valueOf(seanceCode.getIdcondidat()));
        	
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
	@FXML
	private void affTous() throws IOException {
		App.setRoot("affTousScCode"); 
	}
	
	@FXML
	private void seanceCond() throws IOException {
		App.setRoot("SeanceConduite"); 
	}
	@FXML
	private void home() throws IOException {
		App.setRoot("Home"); 
	}


	@FXML
	private void back() throws IOException {
		App.setRoot("SeanceCode"); 
	}
	@FXML
	private void vehicule() throws IOException {
		App.setRoot("Choix"); 
	}


    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }

}
