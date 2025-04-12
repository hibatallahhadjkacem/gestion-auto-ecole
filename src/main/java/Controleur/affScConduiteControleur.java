package Controleur;

import java.io.IOException;
import java.util.List;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.SeanceConduite;
import Entities.Vehicule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class affScConduiteControleur {
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
	private Text vehic;
	@FXML
	private Text vehicId;
    @FXML
    private WebView mapView;
	@FXML
    private Text er1;
	@FXML
	private Button btnAffTous;
	
	private SeanceConduiteControleur seanceConduiteControleur=new SeanceConduiteControleur();
	private WebEngine webEngine;

	@FXML
	public void initialize() {
	    webEngine = mapView.getEngine();

	    // Charger le fichier HTML (adapte bien le chemin)
	    webEngine.load(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/map2.html").toExternalForm());

	    // Utilisation classique sans flèche
	    /*webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
	        if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
	            setMapLocation(36.8065, 10.1815, "Tunis, Tunisie");
	        }
	    });*/
	    
        Tooltip tooltip = new Tooltip("View all the driving session details");
        btnAffTous.setTooltip(tooltip);
        tooltip.setShowDelay(Duration.ZERO);
	}

	    // Appelle la fonction JS définie dans le HTML
	    public void setMapLocation(double latitude, double longitude, String address) {
	        JSObject window = (JSObject) webEngine.executeScript("window");
	        window.call("setLocation", latitude, longitude, address);
	    }
	

	   
	    @FXML
	    private void show() throws IOException {
	        String nums = num.getText().trim();
	        if (nums.isEmpty() || !nums.matches("\\d+")) { 
	            afficherMessageTemporaire(er1, "You must fill in the Session number with a number", 2);
	        } else if (!nums.isEmpty() && seanceConduiteControleur.rechNumSc(Integer.parseInt(nums)) == 0) {
	            afficherMessageTemporaire(er1, "This driving session is not found", 2);
	        } else {
	        	List<SeanceConduite> conduites=  seanceConduiteControleur.getScParNum(Integer.parseInt(nums));
	        	SeanceConduite seanceConduite = conduites.get(0);
	        	date.setText(seanceConduite.getDate().toString());
	        	time.setText(seanceConduite.getTemp().toString());
	        	monit.setText(seanceConduiteControleur.getMoniteur(seanceConduite.getIdMoniteur()));
	        	condid.setText(seanceConduiteControleur.getCondidat(seanceConduite.getIdcondidat()));
	        	monitId.setText(String.valueOf(seanceConduite.getIdMoniteur()));
	        	condidId.setText(String.valueOf(seanceConduite.getIdcondidat()));
	        	vehicId.setText(seanceConduite.getIdVehicule());
	        	vehic.setText(seanceConduiteControleur.getvehic(seanceConduite.getIdVehicule()));
	             setMapLocation(seanceConduite.getLatitude(), seanceConduite.getLongitude(), seanceConduite.getLieuRDV());
	            
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
			App.setRoot("affTousScCond"); 
		}
		
		@FXML
		private void scCode() throws IOException {
			App.setRoot("SeanceCode"); 
		}
	
	@FXML
	private void home() throws IOException {
		App.setRoot("Home"); 
	}
	@FXML
	private void back() throws IOException {
		App.setRoot("SeanceConduite"); 
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
