package Controleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Papier;
import Service.PapierService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class PapierControleur implements Initializable {
	@FXML
	private TextField immat;
	@FXML
	private TextField taxc;
	@FXML
	private DatePicker taxd;
	@FXML
	private TextField insurc;
	@FXML
	private DatePicker insurd;
	@FXML
	private ComboBox<String> insurp;
	@FXML
	private TextField inspc;
	@FXML
	private DatePicker inspd;
	@FXML
	private TextField inspw;
	@FXML
	private TextField inspcap;
	@FXML
	private TextField oilc;
	@FXML
	private DatePicker oild;
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
	
    private PapierService papierService=new PapierService();
    private DisponibiliteVControleur disponibiliteVControleur =new DisponibiliteVControleur();
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	ObservableList<String> list =FXCollections.observableArrayList("once a year","every six months","every three months");
    	insurp.setItems(list);
    	
        // Désactiver les dates passées 
        taxd.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        insurd.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        oild.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });

        inspd.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
    }
    

    
	@FXML
	private void next() throws IOException {
		String immatricule = immat.getText().trim();
		//tax sticker
		boolean testTax=true;
		String taxcost = taxc.getText().trim();
		LocalDate taxDate = taxd.getValue();
		//insurance
		boolean testInsur=true;
		String insurCost = insurc.getText().trim();
		LocalDate insurDate = insurd.getValue();
		String TypeP=insurp.getValue();
		//oil change
		boolean testOil=true;
		String oilCost = oilc.getText().trim();
		LocalDate oilDate = oild.getValue();
		//inspection
		boolean testinsp=true;
		String inspCost = inspc.getText().trim();
		LocalDate inspDate = inspd.getValue();
		String weight=inspw.getText().trim();
		String Ecapacite=inspcap.getText().trim();
		if(immatricule.isEmpty()) {
			afficherMessageTemporaire(er1, "You must fill in the registration number", 2);
		}
		else { 
		 if(!immatricule.isEmpty() && disponibiliteVControleur.rechImmat(immatricule)==0) {
				afficherMessageTemporaire(er1, "This vehicle registration is not found", 2);
			}else if(!immatricule.isEmpty() && disponibiliteVControleur.rechImmat(immatricule)==-1) {
				afficherMessageTemporaire(er1, "An error in the database", 2);
			}
		//tax sticker
		if(!taxcost.isEmpty() && !isFloat(taxcost)) {
			afficherMessageTemporaire(er2, "le cout doit etre un float ou nombre", 2);
			testTax=false;
		}else
		if(taxcost.isEmpty() &&  taxDate!=null) {
			afficherMessageTemporaire(er2, "tu as oublier de remplir le cout", 2);
			testTax=false;
		}else
		if (!taxcost.isEmpty() &&  taxDate==null) {
			afficherMessageTemporaire(er2, "tu as oublier de remplir la date", 2);
			testTax=false;
		}else
		if(!taxcost.isEmpty() &&  taxDate!=null && isFloat(taxcost)) {
			float cost=Float.parseFloat(taxcost);
			testTax=papierService.addTaxSticker(immatricule,cost, taxDate);
		}
		//insurance
		if(!isFloat(insurCost) && !insurCost.isEmpty()) {
			afficherMessageTemporaire(er3, "le cout doit etre un float ou nombre", 2);

			testInsur=false;
		}else
		if(insurCost.isEmpty() &&  insurDate!=null) {
			afficherMessageTemporaire(er3, "tu as oublier de remplir le cout", 2);
			testInsur=false;
		}else
		if (!insurCost.isEmpty() &&  insurDate==null) {
			afficherMessageTemporaire(er3, "tu as oublier de remplir la date", 2);
			testInsur=false;
		}
		if (!insurCost.isEmpty() &&  insurDate!=null && TypeP==null) {
			afficherMessageTemporaire(er3, "tu as oublier de choisir type de paiement", 2);
			testInsur=false;
		}else
		if(!insurCost.isEmpty() &&  insurDate!=null && isFloat(insurCost) && TypeP!=null) {
			float cost=Float.parseFloat(insurCost);
			testInsur=papierService.addInseurance(immatricule,cost, insurDate, TypeP);
		}
		//oil change
		if(!isFloat(oilCost) && !oilCost.isEmpty()) {
			afficherMessageTemporaire(er5, "le cout doit etre un float ou nombre", 2);
			testOil=false;
		}else
		if(oilCost.isEmpty() &&  oilDate!=null) {
			afficherMessageTemporaire(er5, "tu as oublier de remplir le cout", 2);
			testOil=false;
		}else
		if (!oilCost.isEmpty() &&  oilDate==null) {
			afficherMessageTemporaire(er5, "tu as oublier de remplir la date", 2);
			testOil=false;
		}else
		if(!oilCost.isEmpty() &&  oilDate!=null && isFloat(oilCost)) {
			float cost=Float.parseFloat(oilCost);
			testOil=papierService.addOilChange(immatricule,cost, oilDate);
		}
		//inspection
		if(!isFloat(inspCost) && !inspCost.isEmpty()) {
			afficherMessageTemporaire(er4, "le cout doit etre un float ou nombre", 2);
			testinsp=false;
		}else
		if(inspCost.isEmpty() &&  inspDate!=null) {
			afficherMessageTemporaire(er4, "tu as oublier de remplir le cout", 2);
			testinsp=false;
		}else
		if (!inspCost.isEmpty() &&  inspDate==null) {
			afficherMessageTemporaire(er4, "tu as oublier de remplir la date", 2);
			testinsp=false;
		}else
		if(!inspCost.isEmpty() &&  inspDate!=null && isFloat(inspCost)) {
			float cost=Float.parseFloat(inspCost);
			testinsp=papierService.addInspection(immatricule,cost, inspDate,weight,Ecapacite);
		}
		
		
		}
		if(testTax && testInsur && testOil && testinsp && !immatricule.isEmpty() && disponibiliteVControleur.rechImmat(immatricule)==1 ) {
		App.setRoot("Choix");}
		
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
	
	
	//update papier
	
	public boolean updateCout(String immat,float cout,String type,LocalDate date) {
		return papierService.updateCout(immat, cout, type, date);
	}
	
	public boolean updatePapierType(String immat,LocalDate date, String weight,String Ecapacite,String typeP,String type) {
		return papierService.updatePapierType(immat, date, weight, Ecapacite, typeP, type);
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
	private void scConduit() throws IOException {
		App.setRoot("SeanceConduite"); 
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
    
    public boolean isFloat(String str) {
        if (str == null || str.trim().isEmpty()) {
            return false;
        }
        String regex = "^[-+]?\\d*\\.?\\d+$"; 
        return str.matches(regex);
    }
	public List<Papier> getPapiersByImmat(String immatricule){
		return papierService.getPapiersByImmat(immatricule);
	}


}
