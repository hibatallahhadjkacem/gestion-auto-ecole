package Controleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Auto_Ecolee.Auto_Ecolee.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.control.CheckBox;


public class updateVehiculeControleur implements Initializable {

	@FXML
	private TextField immat;
	//vehicuel
	@FXML
	private TextField modele;
	@FXML
	private DatePicker datev;
	@FXML
	private TextField kmtot;
	@FXML
	private TextField kmproche;
    @FXML 
    private ComboBox<String> catg;
	@FXML
	private TextField age;
	@FXML
	private Label modeleT;
	@FXML
	private Label datevT;
	@FXML
	private Label kmtotT;
	@FXML
	private Label kmprocheT;
    @FXML 
    private Label catgT;
	@FXML
	private Label ageT;
	//repartition
	@FXML
	private DatePicker dateS;
	@FXML
	private TextField descripS;
	@FXML
	private TextField costS;
	@FXML
	private TextField proofS;
	@FXML
	private Label dateST;
	@FXML
	private Label descripST;
	@FXML
	private Label costST;
	@FXML
	private Label proofST;
	//papier
	@FXML
	private ComboBox<String> type;
	@FXML
	private TextField costP;
	@FXML
	private DatePicker dateP;
	@FXML
	private ComboBox<String> fP;
	@FXML
	private TextField wP;
	@FXML
	private TextField eP;
	@FXML
	private Label typeT;
	@FXML
	private Label costPT;
	@FXML
	private Label datePT;
	@FXML
	private Label fPT;
	@FXML
	private Label wPT;
	@FXML
	private Label ePT;
	//checkBox
	@FXML
	private CheckBox upV;
	@FXML
	private CheckBox upVP;
	@FXML
	private CheckBox upVS;
	//err
    @FXML
    private Text er1;
    @FXML
    private Text er2;
    @FXML
    private Text er3;
    @FXML
    private Text er4;
    
   private DisponibiliteVControleur disponibiliteVControleur=new DisponibiliteVControleur();
    
    
	@FXML
	private void update() throws IOException {
		String im = immat.getText().trim();
		//vehicule
		String mod=modele.getText().trim();
		String kmtt=kmtot.getText().trim();
		String kmpp=kmproche.getText().trim();
		LocalDate dt = datev.getValue();
		String choix=catg.getValue();
		String agg=age.getText().trim();
		//Repartition
		LocalDate dtS = dateS.getValue();
		String dis=descripS.getText().trim();
		String costt=costS.getText().trim();
		String prf=proofS.getText().trim();
		//papier
		String costp = costP.getText().trim();
		LocalDate dtP = dateP.getValue();
		String weight=wP.getText().trim();
		String Ecapacite=eP.getText().trim();
		String Type=type.getValue();
		String TypeP=fP.getValue();
		if(im.isEmpty()) {
			afficherMessageTemporaire(er1, "You must fill in the registration number", 2);
		}else if(disponibiliteVControleur.rechImmat(im)==0) {
			afficherMessageTemporaire(er1, "This vehicle registration is not found", 2);

		}else if(disponibiliteVControleur.rechImmat(im)==-1) {
			afficherMessageTemporaire(er1, "An error in the database", 2);

		}else if(!im.isEmpty()) {
			if(upV.isSelected()) {
				VehiculeControleur vehiculeControleur=new VehiculeControleur();
				if(!mod.isEmpty()) {
					if(vehiculeControleur.updateMadelVehicule(im, mod)) {
						er1.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er1," Updated vehicle successfully!", 2);
					}
						
				}
				if(!kmtt.isEmpty()&&!kmtt.matches("\\d+")) { 
				afficherMessageTemporaire(er1,"You must fill in the total kilometers with a number.", 2);
			    }else if(!kmtt.isEmpty()&&kmtt.matches("\\d+")){
					if(vehiculeControleur.updateKmtotVehicule(im, Integer.parseInt(kmtt))){
						er1.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er1," Updated vehicle successfully!", 2);
					}
				}
				
				if(!kmpp.isEmpty()&&!kmpp.matches("\\d+")) { 
				afficherMessageTemporaire(er1,"You must fill in the Remaining Km to Service with a number.", 2);
			    }else if(!kmpp.isEmpty() && kmpp.matches("\\d+")) {
					if(vehiculeControleur.updatekmProchEntrVehicule(im, Integer.parseInt(kmpp))){
						er1.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er1," Updated vehicle successfully!", 2);
					}
				}
				
				if(!agg.isEmpty()&&!agg.matches("\\d+")) { 
				afficherMessageTemporaire(er1,"You must fill in the age with a number.", 2);
			    }else if(!agg.isEmpty() && agg.matches("\\d+")) {
					if(vehiculeControleur.updateAgeVehicule(im, Integer.parseInt(agg))){
						er1.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er1," Updated vehicle successfully!", 2);
					}
				}
				if(choix!=null) {
					if(vehiculeControleur.updateCategorieVehicule(im, choix)){
						er1.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er1," Updated vehicle successfully!", 2);
					}
				}
				if(dt!=null) {
					if(vehiculeControleur.updateDateVehicule(im, dt)){
						er1.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er1," Updated vehicle successfully!", 2);
					}
				}
			}
			if (upVS.isSelected()) {
				RepartitionControleur repartitionControleur=new RepartitionControleur();
				if(dt==null) {
					afficherMessageTemporaire(er4,"Please select the date of the vehicle schedule you want to update", 2);
				}
				else if(dtS!=null) {
				if(!dis.isEmpty()) {
					repartitionControleur.updateDescriptionRepartition(im, dis, dtS);
				}
				if(!costt.isEmpty() && !isFloat(costt)) {
					afficherMessageTemporaire(er4,"You must fill in the cost with a number or a float", 2);
				}else if(!costt.isEmpty() && isFloat(costt)) {
					if(repartitionControleur.updateCoutRepartition(im, Float.parseFloat(costt), dtS)){
						er4.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er4," Updated vehicle's paperwork successfully!", 2);
					}
				}
				if(!prf.isEmpty()) {
					if(repartitionControleur.updatePreuveRepartition(im, prf, dtS)){
						er4.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er4," Updated vehicle's paperwork successfully!", 2);
					}
				}}
				
			}
			if(upVP.isSelected()) {
				PapierControleur papierControleur=new PapierControleur();
				if(dtP ==null || Type==null){
					afficherMessageTemporaire(er2,"Please select the date and the type of the vehicle paper you want to update", 2);
				}
				else if(dtP!=null && Type!=null) {
					if(!costp.isEmpty() && !isFloat(costp)) {
						afficherMessageTemporaire(er2,"You must fill in the cost with a number or a float", 2);
					}else if(!costp.isEmpty() && isFloat(costp)) {
					if(papierControleur.updateCout(im, Float.parseFloat(costp), Type, dtP)){
						er2.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er2," Updated Model successfully!", 2);
					}
				}
					if(!Ecapacite.isEmpty()&& !isFloat(Ecapacite)|| !weight.isEmpty()&& !isFloat(weight)) {
						afficherMessageTemporaire(er2,"You must fill in the weight and  with a number or a float", 2);
					}else if(!Ecapacite.isEmpty() && isFloat(Ecapacite)|| !weight.isEmpty() &&isFloat(weight) || TypeP!=null) {
					if(papierControleur.updatePapierType(im, dtP, weight, Ecapacite, TypeP, Type)){
						er2.setStyle("-fx-fill: green;");
						afficherMessageTemporaire(er2," Updated vehicle's scheduling successfully!", 2);
					}
				}
					}
			}
			/*if(kmtt.matches("\\d+") && kmpp.matches("\\d+") && agg.matches("\\d+") || 
					kmtt.isEmpty()|| kmpp.isEmpty() || agg.isEmpty()|| isFloat(costt)|| costt.isEmpty()
					|| isFloat(costp) || costp.isEmpty()|| upVP.isSelected() && dtP!=null && Type!=null
					|| upVS.isSelected() &&dt==null) {
				 App.setRoot("Choix");
				showAlert("Success", "Updated successfully!");
			}*/
			
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
			private void afficherMessageTemporaire(Text textElement, String message, int duree) {
			    textElement.setText(message);
			    Timeline timeline = new Timeline(
			        new KeyFrame(Duration.seconds(duree), e -> textElement.setText(""))
			    );
			    timeline.setCycleCount(1); // Exécuter une seule fois
			    timeline.play(); // Lancer le timer
			}
			
			public boolean isFloat(String str) {
		        String regex = "^[-+]?\\d*\\.?\\d+$"; 
		        return str.matches(regex);
		    }
	
	 @FXML
	 private void handleCheckBoxAction3() {
		 if (upVS.isSelected()) { 
			 dateS.setOpacity(1);
			 descripS.setOpacity(1);
			 costS.setOpacity(1);
			 proofS.setOpacity(1);
			 dateST.setOpacity(1);
			 descripST.setOpacity(1);
			 costST.setOpacity(1);
			 proofST.setOpacity(1);
			 
			 } else {
				 dateS.setOpacity(0.3);
				 descripS.setOpacity(0.3);
				 costS.setOpacity(0.3);
				 proofS.setOpacity(0.3);
				 dateST.setOpacity(0.5);
				 descripST.setOpacity(0.5);
				 costST.setOpacity(0.5);
				 proofST.setOpacity(0.5);
				 }
	        }
	
	 @FXML
	 private void handleCheckBoxAction2() {
		 if (upVP.isSelected()) { 
			 type.setOpacity(1);
			 costP.setOpacity(1);
			 dateP.setOpacity(1);
			 fP.setOpacity(1);
			 wP.setOpacity(1);
			 eP.setOpacity(1);
			 typeT.setOpacity(1);
			 costPT.setOpacity(1);
			 datePT.setOpacity(1);
			 fPT.setOpacity(1);
			 wPT.setOpacity(1);
			 ePT.setOpacity(1);
			 } else {
				 type.setOpacity(0.3);
				 costP.setOpacity(0.3);
				 dateP.setOpacity(0.3);
				 fP.setOpacity(0.3);
				 wP.setOpacity(0.3);
				 eP.setOpacity(0.3);
				 typeT.setOpacity(0.5);
				 costPT.setOpacity(0.5);
				 datePT.setOpacity(0.5);
				 fPT.setOpacity(0.5);
				 wPT.setOpacity(0.5);
				 ePT.setOpacity(0.5);
				 }
	        }
	
	 @FXML
	 private void handleCheckBoxAction() {
		 if (upV.isSelected()) { 
			 modele.setOpacity(1);
			 datev.setOpacity(1);
			 kmtot.setOpacity(1);
			 kmproche.setOpacity(1);
			 catg.setOpacity(1);
			 age.setOpacity(1);
			 modeleT.setOpacity(1);
			 datevT.setOpacity(1);
			 kmtotT.setOpacity(1);
			 kmprocheT.setOpacity(1);
			 catgT.setOpacity(1);
			 ageT.setOpacity(1);
			 } else {
				 modele.setOpacity(0.3);
				 datev.setOpacity(0.3);
				 kmtot.setOpacity(0.3);
				 kmproche.setOpacity(0.3);
				 catg.setOpacity(0.3);
				 age.setOpacity(0.3);
				 modeleT.setOpacity(0.5);
				 datevT.setOpacity(0.5);
				 kmtotT.setOpacity(0.5);
				 kmprocheT.setOpacity(0.5);
				 catgT.setOpacity(0.5);
				 ageT.setOpacity(0.5);
				 }
	        }
	
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	ObservableList<String> list =FXCollections.observableArrayList("Motorcycle","Car","Truck");
    	catg.setItems(list);
    	ObservableList<String> list2 =FXCollections.observableArrayList("VehicleTaxSticker","VehicleInspection","Insurance","OilChange");
    	type.setItems(list2);
    	ObservableList<String> list3 =FXCollections.observableArrayList("once a year","every six months","every three months");
    	fP.setItems(list3);
        Tooltip tooltip = new Tooltip("Check if you want to update the vehicle");
        upV.setTooltip(tooltip);
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip tooltip2 = new Tooltip("Check if you want to update the vehicle's paperwork");
        upVP.setTooltip(tooltip2);
        tooltip2.setShowDelay(Duration.ZERO);
        Tooltip tooltip3 = new Tooltip("Check if you want to update the vehicle's scheduling");
        upVS.setTooltip(tooltip3);
        tooltip3.setShowDelay(Duration.ZERO);
    	
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
	private void scCode() throws IOException {
		App.setRoot("SeanceCode"); 
	}

    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }

}
