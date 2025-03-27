package Controleur;



import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;


import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Vehicule;
import Service.VehiculeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;

public class VehiculeControleur implements Initializable {
	@FXML
	private TextField immat;
	@FXML
	private TextField modele;
	@FXML
	private DatePicker datev;
	@FXML
	private TextField kmtot;
	@FXML
	private TextField kmproche;

	@FXML
	private TextField age;

    @FXML 
    private ComboBox<String> catg;
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
    private Text er8;


	
    @FXML
    private Button myButton;
    private String imatricule;
    private affichageVehiculeControleur affVControleur =new affichageVehiculeControleur();
    
    public void setAffichageVehiculeControleur(affichageVehiculeControleur affichageVehiculeControleur) {
        this.affVControleur = affichageVehiculeControleur;
    }


    

    
    public List<Vehicule> getVehiculesByImmat(String immat) throws SQLException {
    	
    	//affVControleur.chargerDetailsVehicule(vehiculeService.getVehiculesByImmat(immat));
        return vehiculeService.getVehiculesByImmat(immat);
    }

    
    ///

    
    private VehiculeService vehiculeService=new VehiculeService() ;
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	ObservableList<String> list =FXCollections.observableArrayList("Motorcycle","Car","Truck");
    	catg.setItems(list);
    	
    }
    

    
	@FXML
	
	private void next() throws IOException {
		
		String im = immat.getText().trim();
		String mod=modele.getText().trim();
		String kmtt=kmtot.getText().trim();
		String kmpp=kmproche.getText().trim();
		LocalDate dt = datev.getValue();
		String choix=catg.getValue();
		String agg=age.getText().trim();



		if(!im.isEmpty() && dt!=null && choix!=null && !mod.isEmpty() && !kmtt.isEmpty() && !kmpp.isEmpty() && !agg.isEmpty()&& kmtt.matches("\\d+")&& kmpp.matches("\\d+")&& agg.matches("\\d+") ) {
			int ag=Integer.parseInt(age.getText().trim());
			int kmp=Integer.parseInt(kmproche.getText().trim());
			int kmt=Integer.parseInt(kmtot.getText().trim());
			if(vehiculeService.addVehicule(im, mod, dt, kmt, kmp, choix, ag)) {
			App.setRoot("Choix");}
		}else {
			if (im.isEmpty()) { 
				er1.setText("You must fill in the registration number");
			}
			if(dt==null) {
				er3.setText("You must select a date");
			}
			if(choix==null) {
				er6.setText("You must choose a category");
			}
			if (mod.isEmpty()) { 
				er2.setText("You must fill in the model");
			}
			if (kmtt.isEmpty() || !kmtt.matches("\\d+")) { 
				er4.setText("You must fill in the total kilometers with a number.");
			}
			if (kmpp.isEmpty() || !kmpp.matches("\\d+")) { 
				er5.setText("You must fill in the Remaining Km to Service with a number.");
			}
			if (agg.isEmpty() || !agg.matches("\\d+")) { 
				er7.setText("You must fill in the age with a number.");
			}

		}
		
		 
		
	}
	
	//affichage de vehicule 
	public ObservableList<Vehicule> getVehiculeList() throws SQLException {
	    List<Vehicule> vehicules = vehiculeService.getAllVehicules();
	    return FXCollections.observableArrayList(vehicules);
	}
	// supp de vehicule 
	
    public boolean deleteVehiculeByImmatricule(String immatricule) throws SQLException {
        return vehiculeService.deleteVehicule(immatricule); // Delegate the delete action to VehiculeService
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
