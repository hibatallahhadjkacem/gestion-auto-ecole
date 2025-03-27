package Controleur;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Auto_Ecolee.Auto_Ecolee.App;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    
    
	@FXML
	private void update() throws IOException {
		String im = immat.getText().trim();
		if(!im.isEmpty()) {
			
			App.setRoot("Choix");
		}else {
			if (im.isEmpty()) { 
				er1.setText("You must fill in the registration number");
			}}
		
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
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }

}
