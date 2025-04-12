package Controleur;



import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;


import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Categorie;
import Entities.Vehicule;
import Service.VehiculeService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    private DisponibiliteVControleur disponibiliteVControleur=new DisponibiliteVControleur();
    
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
    	
        // Désactiver les dates passées 
    	datev.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    	
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



		if(!im.isEmpty() && disponibiliteVControleur.rechImmat(im)==0 && dt!=null && choix!=null && !mod.isEmpty() && !kmtt.isEmpty() && !kmpp.isEmpty() && !agg.isEmpty()&& kmtt.matches("\\d+")&& kmpp.matches("\\d+")&& agg.matches("\\d+") ) {
			int ag=Integer.parseInt(age.getText().trim());
			int kmp=Integer.parseInt(kmproche.getText().trim());
			int kmt=Integer.parseInt(kmtot.getText().trim());
			if(vehiculeService.addVehicule(im, mod, dt, kmt, kmp, choix, ag)) {
			App.setRoot("Choix");}
		}else {
			if (im.isEmpty()) { 
				afficherMessageTemporaire(er1,"You must fill in the registration number", 2);
			}else
			if(!im.isEmpty() && disponibiliteVControleur.rechImmat(im)==1) {
				afficherMessageTemporaire(er1,"This vehicle already exists", 2);
			}else
			if (mod.isEmpty()) { 
				afficherMessageTemporaire(er2,"You must fill in the model", 2);
			}else
			if(dt==null) {
				afficherMessageTemporaire(er3,"You must select a date", 2);
			}else
			if (kmtt.isEmpty() || !kmtt.matches("\\d+")) { 
				afficherMessageTemporaire(er4,"You must fill in the total kilometers with a number.", 2);
			}else
			if (kmpp.isEmpty() || !kmpp.matches("\\d+")) { 
				afficherMessageTemporaire(er5, "You must fill in the Remaining Km to Service with a number.", 2);
			}else
			if(choix==null) {
				afficherMessageTemporaire(er6,"You must choose a category", 2);
			}else
			if (agg.isEmpty() || !agg.matches("\\d+")) { 
				afficherMessageTemporaire(er7, "You must fill in the age with a number.", 2);

			}

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
	
	//update vehicule 
    public boolean updateDateVehicule(String immat,LocalDate date) {
    	return vehiculeService.updateDateVehicule(immat, date);
    }
    
    public boolean updateAgeVehicule(String immat, int age) {
    	return vehiculeService.updateAgeVehicule(immat, age);
    }
    
    public boolean updateKmtotVehicule(String immat,int kmtot) {
    	return vehiculeService.updateKmtotVehicule(immat, kmtot);
    }
    
    public boolean updatekmProchEntrVehicule(String immat,int kmProchEntr) {
    	return vehiculeService.updatekmProchEntrVehicule(immat, kmProchEntr);
    }
    
    public boolean updateCategorieVehicule(String immat, String categorie) {
    	
    	return vehiculeService.updateCategorieVehicule(immat, categorie);
    }
    
    public boolean updateMadelVehicule(String immat,String modele) {
    	return vehiculeService.updateMadelVehicule(immat, modele);
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
    
	//affichage de vehicule
    public List<Vehicule> getAllVehicules() throws SQLException {
        return vehiculeService.getAllVehicules();
    }
    
    //notif 
    public List<Node> genererNotifications(List<Vehicule> vehicules){
    	return vehiculeService.genererNotifications(vehicules);
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

}
