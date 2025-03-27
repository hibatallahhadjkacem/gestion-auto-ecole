package Controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Papier;
import Entities.Repartition;
import Entities.Vehicule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class affichageVehiculeControleur implements Initializable {
	@FXML
	private TextField immat;
	@FXML
	private Text model;
	@FXML
	private Text categ;
	@FXML
	private Text dates;
	@FXML
	private Text age;
	@FXML
	private Text kmt;
	@FXML
	private Text kmp;
	@FXML
	private TableView<Repartition> tableReparation;
	@FXML
	private TableColumn<Repartition, LocalDate> colDate;
	@FXML
	private TableColumn<Repartition, String> colDescription;
	@FXML
	private TableColumn<Repartition, Double> colCout;
	@FXML
	private TableColumn<Repartition, String> colPreuve;
	@FXML
	private TableView<Papier> tablePapier;
	@FXML
	private TableColumn<Papier, LocalDate> colDatePap;
	@FXML
	private TableColumn<Papier, LocalDate> colDateProch;
	@FXML
	private TableColumn<Papier, Double> colCoutP;
	@FXML
	private TableColumn<Papier, String> colType;
	
    private VehiculeControleur vehiculeControleur ;
    private RepartitionControleur repartitionControleur;
    private PapierControleur papierControleur;

	private ObservableList<Repartition> reparationList = FXCollections.observableArrayList();
	private ObservableList<Papier> papierList = FXCollections.observableArrayList();


	
	public void chargerReparations(String immatriculation) {
        if (repartitionControleur != null) {
            List<Repartition> reparations = repartitionControleur.getReparationsByImmat(immatriculation);
            reparationList.clear();
            reparationList.addAll(reparations);
            tableReparation.refresh(); // Important pour mettre à jour l'affichage
        } else {
            System.out.println("RepartitionControleur non initialisé !");
        }
	}
	
	public void chargerPapiers(String immatriculation) {
        if (papierControleur != null) {
            List<Papier> papiers = papierControleur.getPapiersByImmat(immatriculation);
            papierList.clear();
            papierList.addAll(papiers);
            tablePapier.refresh(); // Important pour mettre à jour l'affichage
        } else {
            System.out.println("PapierControleur non initialisé !");
        }
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vehiculeControleur = new VehiculeControleur();
        repartitionControleur = new RepartitionControleur();
        papierControleur=new PapierControleur();
        
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colCout.setCellValueFactory(new PropertyValueFactory<>("cout"));
        colPreuve.setCellValueFactory(new PropertyValueFactory<>("preuve"));
        
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colCoutP.setCellValueFactory(new PropertyValueFactory<>("cout"));
        colDateProch.setCellValueFactory(new PropertyValueFactory<>("dateProchain"));
        colDatePap.setCellValueFactory(new PropertyValueFactory<>("date"));

        tableReparation.setItems(reparationList);
        tablePapier.setItems(papierList);
    }


	


	public void setVehiculeController(VehiculeControleur vehiculeControleur) {
        this.vehiculeControleur = vehiculeControleur;
    }
	public void setRepartitionControleur(RepartitionControleur repartitionControleur) {
		this.repartitionControleur=repartitionControleur;
	}
	
	@FXML
	private void get() throws IOException {
		String im = immat.getText().trim();
		if(! im.isEmpty()) {
			try {
				List<Vehicule>vehicules= vehiculeControleur.getVehiculesByImmat(im);
				Vehicule vehicle = vehicules.get(0);
			    if (model != null) model.setText(vehicle.getModel());
			    if (categ != null) categ.setText(vehicle.getCatégorie().toString());
			    if (dates != null) dates.setText(vehicle.getDateMiseEnService().toString());
			    if (age != null) age.setText(String.valueOf(vehicle.getAge()));
			    if (kmt != null) kmt.setText(String.valueOf(vehicle.getKmTotal()));
			    if (kmp != null) kmp.setText(String.valueOf(vehicle.getKmProchEntretient()));
			    chargerReparations(im);
			    chargerPapiers(im);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	

    

    
    
	/*private String immatricule;
	
	public void setImmatricule(String immatricule) {
		this.immatricule=immatricule;
	}*/
    /*
   public void chargerDetailsVehicule(List<Vehicule> vehicules){        
        if (!vehicules.isEmpty()) {
		    
		    Vehicule vehicle = vehicules.get(0); 
		    
		    System.out.println(vehicle);
		    System.out.println(vehicle.getImmatricule());
		    if (immat != null) immat.setText(vehicle.getImmatricule());
		    if (model != null) model.setText(vehicle.getModel());
		    if (categ != null) categ.setText(vehicle.getCatégorie().toString());
		    if (dates != null) dates.setText(vehicle.getDateMiseEnService().toString());
		    if (age != null) age.setText(String.valueOf(vehicle.getAge()));
		    if (kmt != null) kmt.setText(String.valueOf(vehicle.getKmTotal()));
		    if (kmp != null) kmp.setText(String.valueOf(vehicle.getKmProchEntretient()));
		     
		} else {
		    System.out.println("No vehicle found with the specified immatricule.");
		}

    }
    
    


    
    public void chargerDetailsVehicule(String immatricule){        
        try {

            
            
            List<Vehicule> vehicules = vehiculeControleur.getVehiculesByImmat(immatricule);
            
            if (!vehicules.isEmpty()) {
                Vehicule vehicle = vehicules.get(0);  
                System.out.println(vehicle);
                immat.setText(vehicle.getImmatricule());
                model.setText(vehicle.getModel());
                categ.setText(vehicle.getCatégorie().toString());  
                dates.setText(vehicle.getDateMiseEnService().toString()); 
                age.setText(String.valueOf(vehicle.getAge()));
                kmt.setText(String.valueOf(vehicle.getKmTotal())); 
                kmp.setText(String.valueOf(vehicle.getKmProchEntretient()));  
            } else {
                System.out.println("No vehicle found with the specified immatricule.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

*/
	

	
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
