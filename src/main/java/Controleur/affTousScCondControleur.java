package Controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Repartition;
import Entities.SeanceConduite;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class affTousScCondControleur implements Initializable{
	@FXML
	private TableView<SeanceConduite> tab;
    @FXML
    private TableColumn<SeanceConduite, Integer> num;
    @FXML
    private TableColumn<SeanceConduite, LocalDate> date;
    @FXML
    private TableColumn<SeanceConduite, LocalTime> time;
    @FXML
    private TableColumn<SeanceConduite, Integer> idMonit;
    @FXML
    private TableColumn<SeanceConduite, Integer> idCondid;
    @FXML
    private TableColumn<SeanceConduite, String> idVehic;
    @FXML
    private TableColumn<SeanceConduite, String> adresse;
    @FXML
    private DatePicker dateCh;
    @FXML
    private TextField tp;
	@FXML
    private Text er1;
    
	private ObservableList<SeanceConduite> seanceConduites = FXCollections.observableArrayList();
	private SeanceConduiteControleur seanceConduiteControleur=new SeanceConduiteControleur();

	
	public void chargerSc() throws SQLException {
        if (seanceConduiteControleur != null) {
            List<SeanceConduite> conduites = seanceConduiteControleur.getAllScConduit();
            seanceConduites.clear();
            seanceConduites.addAll(conduites);
            tab.refresh(); //  mettre à jour l'affichage
        } 
	}
	
	public void chargerScDate(LocalDate date) throws SQLException {
        if (seanceConduiteControleur != null) {
            List<SeanceConduite> conduites = seanceConduiteControleur. getScConduitByDate(date);
            seanceConduites.clear();
            seanceConduites.addAll(conduites);
            tab.refresh(); //  mettre à jour l'affichage
        } 
	}
	
	public void chargerScDateTp(LocalDate date,LocalTime tp) throws SQLException {
        if (seanceConduiteControleur != null) {
            List<SeanceConduite> conduites = seanceConduiteControleur. getScConduitBytpDate(tp,date);
            seanceConduites.clear();
            seanceConduites.addAll(conduites);
            tab.refresh(); //  mettre à jour l'affichage
        } 
	}
	
	public void chargerScTp(LocalTime tp) throws SQLException {
        if (seanceConduiteControleur != null) {
            List<SeanceConduite> conduites = seanceConduiteControleur. getScConduitBytp(tp);
            seanceConduites.clear();
            seanceConduites.addAll(conduites);
            tab.refresh(); //  mettre à jour l'affichage
        } 
	}
	
	
	
	 @Override
	    public void initialize(URL location, ResourceBundle resources) {
		 num.setCellValueFactory(new PropertyValueFactory<>("num"));   
		 date.setCellValueFactory(new PropertyValueFactory<>("date"));
		 time.setCellValueFactory(new PropertyValueFactory<>("temp"));
		 idMonit.setCellValueFactory(new PropertyValueFactory<>("idMoniteur"));
		 idCondid.setCellValueFactory(new PropertyValueFactory<>("idcondidat"));
		 idVehic.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
		 adresse.setCellValueFactory(new PropertyValueFactory<>("lieuRDV"));
	     tab.setItems(seanceConduites);
	        try {
				chargerSc();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	 
	 
	 @FXML
		private void show()throws IOException{
			String temp=tp.getText().trim();
			LocalDate dt=dateCh.getValue();
			
			if(!temp.isEmpty()&& convertToLocalTime(temp)==null) {
				afficherMessageTemporaire(er1, "You must fill in the time correctly hh:mm", 2);
				
			}else {
				if(!temp.isEmpty()&&dt!=null) {
					//tpDte
					 num.setCellValueFactory(new PropertyValueFactory<>("num"));   
					 date.setCellValueFactory(new PropertyValueFactory<>("date"));
					 time.setCellValueFactory(new PropertyValueFactory<>("temp"));
					 idMonit.setCellValueFactory(new PropertyValueFactory<>("idMoniteur"));
					 idCondid.setCellValueFactory(new PropertyValueFactory<>("idcondidat"));
					 idVehic.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
					 adresse.setCellValueFactory(new PropertyValueFactory<>("lieuRDV"));
				     tab.setItems(seanceConduites);
				        try {
				        	chargerScDateTp(dt, LocalTime.parse(temp));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}else if(!temp.isEmpty()&&dt==null) {
					//tp
					 num.setCellValueFactory(new PropertyValueFactory<>("num"));   
					 date.setCellValueFactory(new PropertyValueFactory<>("date"));
					 time.setCellValueFactory(new PropertyValueFactory<>("temp"));
					 idMonit.setCellValueFactory(new PropertyValueFactory<>("idMoniteur"));
					 idCondid.setCellValueFactory(new PropertyValueFactory<>("idcondidat"));
					 idVehic.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
					 adresse.setCellValueFactory(new PropertyValueFactory<>("lieuRDV"));
				     tab.setItems(seanceConduites);
				        try {
				        	chargerScTp(LocalTime.parse(temp));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}else if(temp.isEmpty()&&dt!=null) {
					//Date
					 num.setCellValueFactory(new PropertyValueFactory<>("num"));   
					 date.setCellValueFactory(new PropertyValueFactory<>("date"));
					 time.setCellValueFactory(new PropertyValueFactory<>("temp"));
					 idMonit.setCellValueFactory(new PropertyValueFactory<>("idMoniteur"));
					 idCondid.setCellValueFactory(new PropertyValueFactory<>("idcondidat"));
					 idVehic.setCellValueFactory(new PropertyValueFactory<>("idVehicule"));
					 adresse.setCellValueFactory(new PropertyValueFactory<>("lieuRDV"));
				     tab.setItems(seanceConduites);
				        try {
				        	chargerScDate(dt);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}
			
	 }
	
	 
	 // Créer un Timeline pour effacer le texte après tp
				public void afficherMessageTemporaire(Text textElement, String message, int duree) {
				    textElement.setText(message);
				    Timeline timeline = new Timeline(
				        new KeyFrame(Duration.seconds(duree), e -> textElement.setText(""))
				    );
				    timeline.setCycleCount(1); // Exécuter une seule fois
				    timeline.play(); // Lancer le timer
				}

				 // Method to validate and convert the time
				public LocalTime convertToLocalTime(String timeString) {
				    // Remove spaces
				    timeString = timeString.trim();

				    //  HH:mm
				    Pattern pattern = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$");
				    Matcher matcher = pattern.matcher(timeString);

				    if (matcher.matches()) {
				        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
				    } else {
				        return null;
				    }
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
	App.setRoot("affScConduite"); 
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
