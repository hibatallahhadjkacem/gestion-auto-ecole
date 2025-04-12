package Controleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Repartition;
import Service.RepartitionService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class RepartitionControleur implements Initializable {
	@FXML
	private TextField immat;
	@FXML
	private DatePicker date;
	@FXML
	private TextField descrip;
	@FXML
	private TextField cost;
	@FXML
	private TextField proof;
    @FXML
    private Text er1;
    @FXML
    private Text er2;
    @FXML
    private Text er3;
    @FXML
    private Text er4;
    @FXML
    private Text er5;
	
	private RepartitionService repartitionService=new RepartitionService();
	private DisponibiliteVControleur disponibiliteVControleur=new DisponibiliteVControleur();
	
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	
        // Désactiver les dates passées 
    	date.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
        }
	

	
	@FXML
	private void next() throws IOException {
		String im = immat.getText().trim();
		LocalDate dt = date.getValue();
		String desc = descrip.getText().trim();
		String costt=cost.getText().trim();
		String prooff=proof.getText().trim();
		if(!im.isEmpty() && disponibiliteVControleur.rechImmat(im)==0) {
			afficherMessageTemporaire(er5, "This vehicle registration is not found", 2);
		}else if(!im.isEmpty() && disponibiliteVControleur.rechImmat(im)==-1) {
			afficherMessageTemporaire(er5, "An error in the database", 2);
		}
		else if(!im.isEmpty() && !desc.isEmpty() && dt!=null  && !costt.isEmpty() && !prooff.isEmpty() && isFloat(costt)) {
			float cost=Float.parseFloat(costt);
			if(repartitionService.addRepartition(dt,desc,cost,prooff,im)) {
				App.setRoot("Choix");
}
		}else {
			if (im.isEmpty()) { 
				afficherMessageTemporaire(er5,"You must fill in the registration number", 2);
			}else
			if(dt==null) {
				afficherMessageTemporaire(er1,"You must select a date", 2);
			}else
			if (desc.isEmpty()) { 
				afficherMessageTemporaire(er2,"You must fill in the description", 2);
			}else
			if(costt.isEmpty() || !isFloat(costt)) {
				afficherMessageTemporaire(er3,"You must fill in the cost with a number or a float", 2);
			}else
			if (prooff.isEmpty()) { 
				afficherMessageTemporaire(er4,"You must fill in the URL that leads to the proof", 2);
			}

			
		
		}}
	
	
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
	private void scCode() throws IOException {
		App.setRoot("SeanceCode"); 
	}
	@FXML
	private void back() throws IOException {
		App.setRoot("Choix");
	}
	@FXML
	private void home() throws IOException {
		App.setRoot("Home"); 
	}
	
	@FXML
	private void scConduit() throws IOException {
		App.setRoot("SeanceConduite"); 
	}
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
    
    public boolean isFloat(String str) {
        String regex = "^[-+]?\\d*\\.?\\d+$"; 
        return str.matches(regex);
    }
    
	public List<Repartition> getReparationsByImmat(String immatricule){
		return repartitionService.getReparationsByImmat(immatricule);
	}
	
	//update Repartition
	
	public boolean updatePreuveRepartition(String immat,String preuve,LocalDate date) {
		return repartitionService.updatePreuveRepartition(immat, preuve, date);
	}
	
	public boolean updateDescriptionRepartition(String immat,String descrip,LocalDate date) {
		return repartitionService.updateDescriptionRepartition(immat, descrip, date);
	}
	
	public boolean updateCoutRepartition(String immat,float cout,LocalDate date) {
		return repartitionService.updateCoutRepartition(immat, cout, date);
	}

}
