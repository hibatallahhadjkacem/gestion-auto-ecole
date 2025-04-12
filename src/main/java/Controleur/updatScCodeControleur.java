package Controleur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.SeanceCode;
import Entities.SeanceConduite;
import Service.SeanceCodeSevice;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class updatScCodeControleur {
	@FXML
    private TextField num;
    @FXML
    private DatePicker date;
    @FXML
    private TextField time;
    @FXML
    private TextField monitId;
    @FXML
    private TextField condidId;
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
    
    private SeanceCodeControleur seanceCodeControleur=new SeanceCodeControleur();
    private SeanceConduiteControleur seanceConduiteControleur=new SeanceConduiteControleur();
    
	@FXML
	private void update() throws IOException {
		String nums = num.getText().trim();
		LocalDate dt = date.getValue();
		String tp=time.getText().trim();
		String monit=monitId.getText().trim();
		String condid=condidId.getText().trim();
		
		if (nums.isEmpty()|| !nums.matches("\\d+")) { 
			afficherMessageTemporaire(er1, "You must fill in the Session number with a number", 2);

		}else
		if(!nums.isEmpty()&& seanceCodeControleur.rechNumSc(Integer.parseInt(nums))==0) {
			afficherMessageTemporaire(er1, "This driving session is not found", 2);
		}else {

			List<SeanceCode>codes=seanceCodeControleur.getScCodeByNum(Integer.parseInt(nums));
			SeanceCode seanceCode=codes.get(0);
			
			if(dt!=null) {
				if(seanceCodeControleur.updateDate(dt, Integer.parseInt(nums))) {
				er2.setStyle("-fx-fill: green;");
				afficherMessageTemporaire(er2," Updated successfully!", 2);
				}
			}
			
			if(!tp.isEmpty() && convertToLocalTime(tp)==null) {
				afficherMessageTemporaire(er3, "You must fill in the time correctly hh:mm", 2);
			}else if (!tp.isEmpty() && convertToLocalTime(tp)!=null) {
				if(seanceCodeControleur.updateTp(LocalTime.parse(tp), Integer.parseInt(nums))) {
					er3.setStyle("-fx-fill: green;");
					afficherMessageTemporaire(er3," Updated successfully!", 2);
				}
			}
			
			
			if(!monit.isEmpty()&& !monit.matches("\\d+")) {
				afficherMessageTemporaire(er4, "You must fill in the Instructor ID with a nulber", 2);
			}
			else if(!monit.isEmpty()&& seanceConduiteControleur.rechMoniteur(Integer.parseInt(monit))==0) {
				afficherMessageTemporaire(er4, "This Instructor is not found", 2);

			}else if(!monit.isEmpty() && seanceConduiteControleur.disponibMoniteur(Integer.parseInt(monit), seanceCode.getDate(), seanceCode.getTemp())==0) {
				afficherMessageTemporaire(er4, "This instructor is not available", 2);
			}else if(!monit.isEmpty()&& seanceConduiteControleur.rechMoniteur(Integer.parseInt(monit))==1) {
				if(seanceCodeControleur.updateMonit(Integer.parseInt(monit), Integer.parseInt(nums))) {
					er4.setStyle("-fx-fill: green;");
					afficherMessageTemporaire(er4," Updated successfully!", 2);
				} 
			}
				
			
			if(!condid.isEmpty()&& !condid.matches("\\d+")) {
				afficherMessageTemporaire(er5, "You must fill in the Candidate ID with a nulber", 2);
			}else
			if(!condid.isEmpty()&& seanceConduiteControleur.rechCondidat(Integer.parseInt(condid))==0) {
					afficherMessageTemporaire(er5, "This Candidate is not found", 2);
			}else if(!condid.isEmpty()&& seanceConduiteControleur.rechCondidat(Integer.parseInt(condid))==1) {
				if(seanceConduiteControleur.updateCondidat(Integer.parseInt(condid), Integer.parseInt(nums))) {
					er5.setStyle("-fx-fill: green;");
					afficherMessageTemporaire(er5," Updated successfully!", 2);
				}
			}

			
			}}
	
	
		 
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
