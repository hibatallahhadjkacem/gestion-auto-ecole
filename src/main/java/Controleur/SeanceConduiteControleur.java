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
import Entities.SeanceCode;
import Entities.SeanceConduite;
import Service.SeanceConduiteService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import netscape.javascript.JSObject;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;


public class SeanceConduiteControleur {
	
	//map 
	    @FXML
	    private WebView mapView;
	    @FXML
	    private TextField adresseField;  
	    @FXML
	    private TextField latField;      
	    @FXML
	    private TextField lngField;      
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
	    private TextField vehicId;
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
	    private Text er7;
	    @FXML
	    private Text addr;
	    @FXML
	    private Button btnUpdate;
	    @FXML
	    private Button btnGet;
	    
	    private SeanceConduiteService seanceConduiteService=new SeanceConduiteService();
	    private DisponibiliteVControleur disponibiliteVControleur=new DisponibiliteVControleur();
	    
	    
	    @FXML
	    public void initialize() {
	        // Charger la carte OpenStreetMap dans le WebView
	        WebEngine webEngine = mapView.getEngine();
	        webEngine.load(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/map.html").toExternalForm());

	        // Injecter le JavaConnector dans le JavaScript du WebView
	        webEngine.getLoadWorker().stateProperty().addListener((obs, old, newState) -> {
	            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
	                JSObject window = (JSObject) webEngine.executeScript("window");
	                window.setMember("javaConnector", new JavaConnector());
	            }
	        });

	        Tooltip tooltip = new Tooltip("View driving session details");
	        btnGet.setTooltip(tooltip);
	        tooltip.setShowDelay(Duration.ZERO);
	        Tooltip tooltip2 = new Tooltip("Update driving session");
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

	    // Classe interne pour la communication entre Java et JavaScript
	    public class JavaConnector {
	        public void setLocation(double lat, double lng, String address) {
	            // Afficher la latitude et la longitude dans les champs texte
	            latField.setText(String.valueOf(lat));
	            lngField.setText(String.valueOf(lng));

	            // Afficher l'adresse dans le champ adresse
	            adresseField.setText(address);
	            addr.setText(address);
	        }
	    }
	    
	    
		@FXML
		private void add() throws IOException {
			String nums = num.getText().trim();
			LocalDate dt = date.getValue();
			String tp=time.getText().trim();
			String monit=monitId.getText().trim();
			String condid=condidId.getText().trim();
			String vehic=vehicId.getText().trim();
			String adress = adresseField.getText().trim();
			String lat = latField.getText().trim();
			String lng = lngField.getText().trim();
			
			if (nums.isEmpty() || !nums.matches("\\d+")) { 
				afficherMessageTemporaire(er1, "You must fill in the Session number with a number.", 2);

			}else
			if(!nums.isEmpty()&& seanceConduiteService.rechNumSc(Integer.parseInt(nums))==1) {
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
			if(!monit.isEmpty()&& seanceConduiteService.rechMoniteur(Integer.parseInt(monit))==0) {
				afficherMessageTemporaire(er4, "This Instructor is not found", 2);

			}else 
			if(!monit.isEmpty() && seanceConduiteService.disponibMoniteur(Integer.parseInt(monit), dt,LocalTime.parse(tp))==0) {
				afficherMessageTemporaire(er4, "This instructor is not available", 2);
			}else
			if (condid.isEmpty() || !condid.matches("\\d+")) { 
				afficherMessageTemporaire(er5, "You must fill in the Candidate ID.", 2);

			}else
			if(!condid.isEmpty()&& seanceConduiteService.rechCondidat(Integer.parseInt(condid))==0) {
					afficherMessageTemporaire(er5, "This Candidate is not found", 2);
			}else
			if (vehic.isEmpty() ) { 
				afficherMessageTemporaire(er6, "You must fill in the Vehicle registration number", 2);

			}else
			if(!vehic.isEmpty()&&disponibiliteVControleur.rechImmat(vehic)==0) {
				afficherMessageTemporaire(er6, "This vehicle registration is not found", 2);
			}else 
			if(!vehic.isEmpty()&&seanceConduiteService.disponibVehicule(vehic, dt, LocalTime.parse(tp))==0) {
				afficherMessageTemporaire(er6, "This vehicle is not available", 2);
			}else
			if (adress.isEmpty() ) { 
				afficherMessageTemporaire(er7, "Define the candidate's location", 2);

			}else {
				SeanceConduite seanceConduite=new SeanceConduite(Integer.parseInt(nums), dt, LocalTime.parse(tp), adress, Double.parseDouble(lat), Double.parseDouble(lng), Integer.parseInt(monit), Integer.parseInt(condid), vehic);
				boolean test=seanceConduiteService.ajouterSeance(seanceConduite);
				if (test) {
					showAlert("Success"," Driving session added successfully!");
				}else {
					showAlert("Error", "Failed to add the driving session!");
				}
				
			}

			 
		}
		
	    // Show alert method for displaying messages
	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
		
	    public int rechNumSc(int nums) {
	    	return seanceConduiteService.rechNumSc(nums);
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
		
		public List<SeanceConduite> getScParNum(int num){
			return seanceConduiteService.getScParNum(num);
		}
		
	    public String getCondidat(int idcondid) {
	    	return seanceConduiteService.getCondidat(idcondid);
	    }
	    
	    public String getMoniteur(int idMonit) {
	    	return seanceConduiteService.getMoniteur(idMonit);
	    }
	    
	    public String getvehic(String immat) {
	    	return seanceConduiteService.getvehic(immat);}
	    
	    
	@FXML
	private void update() throws IOException {
		App.setRoot("updatScConduite"); 
	}
	@FXML
	private void get() throws IOException {
		App.setRoot("affScConduite"); 
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
		App.setRoot("Home"); 
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

	public int rechMoniteur(int int1) {
		// TODO Auto-generated method stub
		return seanceConduiteService.rechMoniteur(int1);
	}

	public int disponibMoniteur(int int1, LocalDate dt, LocalTime localTime) {
		// TODO Auto-generated method stub
		return seanceConduiteService.disponibMoniteur(int1, dt, localTime);
	}

	public int rechCondidat(int int1) {
		// TODO Auto-generated method stub
		return seanceConduiteService.rechCondidat(int1);
	}

	public int disponibVehicule(String vehic, LocalDate dt, LocalTime localTime) {
		// TODO Auto-generated method stub
		return seanceConduiteService.disponibVehicule(vehic, dt, localTime);
	}
	
	 public boolean updateDate(LocalDate date,int num) {
	    	return seanceConduiteService.updateDate(date, num);
	    }
	    
	    public boolean updateTp(LocalTime tp,int num) {
	    	return seanceConduiteService.updateTp(tp, num);
	    }
	    
	    public boolean updateMonit(int idMonit,int num) {
	    	return seanceConduiteService.updateMonit(idMonit, num);
	    }
	    
	    public boolean updateCondidat(int idCondidat, int num) {
	    	return seanceConduiteService.updateCondidat(idCondidat, num);
	    }
	    
	    public boolean updateVehicule(String idVehicul,int num) {
	    	return seanceConduiteService.updateVehicule(idVehicul, num);
	    }
	    
	    public boolean updateMap(String adresse,double lang,double lalt,int num) {
	    	return seanceConduiteService.updateMap(adresse, lang, lalt, num);
	    }
	    
	    public List<SeanceConduite> getAllScConduit() throws SQLException{
	    	return seanceConduiteService.getAllScConduit();
	    }
	    
	    public List<SeanceConduite> getScConduitBytpDate(LocalTime time,LocalDate dt) throws SQLException {
	    	return seanceConduiteService.getScConduitBytpDate(time, dt);
	    }
	    
	    public List<SeanceConduite> getScConduitBytp(LocalTime time) throws SQLException {
	    	return seanceConduiteService.getScConduitBytp(time);
	    }
	    
	    public List<SeanceConduite> getScConduitByDate(LocalDate dt) throws SQLException {
	    	return seanceConduiteService.getScConduitByDate(dt);
	    }

}
