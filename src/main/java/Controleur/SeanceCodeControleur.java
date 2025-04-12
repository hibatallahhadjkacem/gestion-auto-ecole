package Controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Auto_Ecolee.Auto_Ecolee.App;
import Controleur.SeanceConduiteControleur.JavaConnector;
import Entities.SeanceCode;
import Entities.SeanceConduite;
import Service.SeanceCodeSevice;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.util.Duration;
import netscape.javascript.JSObject;

public class SeanceCodeControleur {
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
	    @FXML
	    private Text er6;
	    @FXML
	    private Button btnUpdate;
	    @FXML
	    private Button btnGet;
	    
	    private SeanceCodeSevice seanceCodeSevice=new SeanceCodeSevice();
	    private SeanceConduiteControleur seanceConduiteControleur=new SeanceConduiteControleur();
	    
	    
	    @FXML
	    public void initialize() {
	        Tooltip tooltip = new Tooltip("View code session details");
	        btnGet.setTooltip(tooltip);
	        tooltip.setShowDelay(Duration.ZERO);
	        Tooltip tooltip2 = new Tooltip("Update code session");
	        btnUpdate.setTooltip(tooltip2);
	        tooltip2.setShowDelay(Duration.ZERO);
	        
	        // Désactiver les dates passées 
	        date.setDayCellFactory(picker -> new DateCell() {
	            public void updateItem(LocalDate date, boolean empty) {
	                super.updateItem(date, empty);
	                setDisable(empty || date.isBefore(LocalDate.now()));
	            }
	        });
	    }
	    
	    
	    @FXML
		private void add() throws IOException {
	    	String nums = num.getText().trim();
			LocalDate dt = date.getValue();
			String tp=time.getText().trim();
			String monit=monitId.getText().trim();
			String condid=condidId.getText().trim();
	
			if (nums.isEmpty() || !nums.matches("\\d+")) { 
				afficherMessageTemporaire(er1, "You must fill in the Session number with a number.", 2);

			}else
			if(!nums.isEmpty()&& seanceCodeSevice.rechNumSc(Integer.parseInt(nums))==1) {
				afficherMessageTemporaire(er1, "This driving session already exists.", 2);
			}
			else
			if(dt==null) {
				afficherMessageTemporaire(er2,"You must select a date", 2);
			}else
			if(tp.isEmpty()|| convertToLocalTime(tp)==null) {
				afficherMessageTemporaire(er3, "You must fill in the time correctly hh:mm", 2);
			}else
			
			if (monit.isEmpty() || !monit.matches("\\d+")) { 
				afficherMessageTemporaire(er4, "You must fill in the Instructor ID.", 2);

			}else
			if(!monit.isEmpty()&& seanceConduiteControleur.rechMoniteur(Integer.parseInt(monit))==0) {
				afficherMessageTemporaire(er4, "This Instructor is not found", 2);

			}else 
			if(!monit.isEmpty() && seanceConduiteControleur.disponibMoniteur(Integer.parseInt(monit), dt,LocalTime.parse(tp))==0) {
				afficherMessageTemporaire(er4, "This instructor is not available", 2);
			}else
			if (condid.isEmpty() || !condid.matches("\\d+")) { 
				afficherMessageTemporaire(er5, "You must fill in the Candidate ID.", 2);

			}else
			if(!condid.isEmpty()&& seanceConduiteControleur.rechCondidat(Integer.parseInt(condid))==0) {
					afficherMessageTemporaire(er5, "This Candidate is not found", 2);
			}else {
				SeanceCode seanceCode=new SeanceCode (Integer.parseInt(nums), dt, LocalTime.parse(tp), Integer.parseInt(monit), Integer.parseInt(condid));
				boolean test=seanceCodeSevice.insert(seanceCode);
				if (test) {
					showAlert("Success"," Code session added successfully!");
				}else {
					showAlert("Error", "Failed to add the code session!");
				}
			}}
			
			 // Show alert method for displaying messages
		    private void showAlert(String title, String message) {
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle(title);
		        alert.setHeaderText(null);
		        alert.setContentText(message);
		        alert.showAndWait();
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
		private void update() throws IOException {
			App.setRoot("updatScCode"); 
		}
		@FXML
		private void get() throws IOException {
			App.setRoot("affScCode"); 
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
		App.setRoot("Home"); 
	}
	@FXML
	private void vehicule() throws IOException {
		App.setRoot("Choix"); 
	}
	
	public boolean updateDate(LocalDate date,int num) {
    	return seanceCodeSevice.updateDate(date, num);
    }
    
    public boolean updateTp(LocalTime tp,int num) {
    	return seanceCodeSevice.updateTp(tp, num);
    }
    
    public boolean updateMonit(int idMonit,int num) {
    	return seanceCodeSevice.updateMonit(idMonit, num);
    }
    
    public boolean updateCondidat(int idCondidat, int num) {
    	return seanceCodeSevice.updateCondidat(idCondidat, num);
    }

    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
    
	public int rechNumSc(int num) {
		return seanceCodeSevice.rechNumSc(num);
	}
	
	public List<SeanceCode> getScCodeByNum(int num){
		return seanceCodeSevice.getScCodeByNum(num);
	}
	
    public List<SeanceCode> getAllScCode() throws SQLException{
    	return seanceCodeSevice.getAllScCode();
    }
    
    public List<SeanceCode> getScCodeBytpDate(LocalTime time,LocalDate dt) throws SQLException {
    	return seanceCodeSevice.getScCodeBytpDate(time, dt);
    }
    
    public List<SeanceCode> getScCodeBytp(LocalTime time) throws SQLException {
    	return seanceCodeSevice.getScCodeBytp(time);
    }
    
    public List<SeanceCode> getScCodeByDate(LocalDate dt) throws SQLException {
    	return seanceCodeSevice.getScCodeByDate(dt);

}}
