package Controleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Papier;
import Service.PapierService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

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
    @Override
    public void initialize(URL url,ResourceBundle rb) {
    	ObservableList<String> list =FXCollections.observableArrayList("once a year","every six months","every three months");
    	insurp.setItems(list);
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
			er1.setText("You must fill in the registration number");
		}else { 
		//tax sticker
		if(!taxcost.isEmpty() && !isFloat(taxcost)) {
			er2.setText("le cout doit etre un float ou nombre");
			testTax=false;
		}else
		if(taxcost.isEmpty() &&  taxDate!=null) {
			er2.setText("tu as oublier de remplir le cout");
			testTax=false;
		}else
		if (!taxcost.isEmpty() &&  taxDate==null) {
			er2.setText("tu as oublier de remplir la date");
			testTax=false;
		}else
		if(!taxcost.isEmpty() &&  taxDate!=null && isFloat(taxcost)) {
			float cost=Float.parseFloat(taxcost);
			testTax=papierService.addTaxSticker(immatricule,cost, taxDate);
		}
		//insurance
		if(!isFloat(insurCost) && !insurCost.isEmpty()) {
			er3.setText("le cout doit etre un float ou nombre");
			testInsur=false;
		}else
		if(insurCost.isEmpty() &&  insurDate!=null) {
			er3.setText("tu as oublier de remplir le cout");
			testInsur=false;
		}else
		if (!insurCost.isEmpty() &&  insurDate==null) {
			er3.setText("tu as oublier de remplir la date");
			testInsur=false;
		}
		if (!insurCost.isEmpty() &&  insurDate!=null && TypeP==null) {
			er3.setText("tu as oublier de choisir type de paiement");
			testInsur=false;
		}else
		if(!insurCost.isEmpty() &&  insurDate!=null && isFloat(insurCost) && TypeP!=null) {
			float cost=Float.parseFloat(insurCost);
			testInsur=papierService.addInseurance(immatricule,cost, insurDate, TypeP);
		}
		//oil change
		if(!isFloat(oilCost) && !oilCost.isEmpty()) {
			er5.setText("le cout doit etre un float ou nombre");
			testOil=false;
		}else
		if(oilCost.isEmpty() &&  oilDate!=null) {
			er5.setText("tu as oublier de remplir le cout");
			testOil=false;
		}else
		if (!oilCost.isEmpty() &&  oilDate==null) {
			er5.setText("tu as oublier de remplir la date");
			testOil=false;
		}else
		if(!oilCost.isEmpty() &&  oilDate!=null && isFloat(oilCost)) {
			float cost=Float.parseFloat(oilCost);
			testOil=papierService.addOilChange(immatricule,cost, oilDate);
		}
		//inspection
		if(!isFloat(inspCost) && !inspCost.isEmpty()) {
			er4.setText("le cout doit etre un float ou nombre");
			testinsp=false;
		}else
		if(inspCost.isEmpty() &&  inspDate!=null) {
			er4.setText("tu as oublier de remplir le cout");
			testinsp=false;
		}else
		if (!inspCost.isEmpty() &&  inspDate==null) {
			er4.setText("tu as oublier de remplir la date");
			testinsp=false;
		}else
		if(!inspCost.isEmpty() &&  inspDate!=null && isFloat(inspCost)) {
			float cost=Float.parseFloat(inspCost);
			testinsp=papierService.addInspection(immatricule,cost, inspDate,weight,Ecapacite);
		}
		
		
		}
		if(testTax && testInsur && testOil && testinsp && !immatricule.isEmpty() ) {
		App.setRoot("Choix");}
		
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
