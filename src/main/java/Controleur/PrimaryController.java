package Controleur;



import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Auto_Ecolee.Auto_Ecolee.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Dao.Moniteur_Dao;
import Service.Moniteur_Service;
import Entities.Moniteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class PrimaryController {
	
	@FXML
	private TextField searchField;

	@FXML
	private TableView table_p;
	
	@FXML
	private TableColumn<Moniteur, Integer> ch_id;

	@FXML
	private TableColumn<Moniteur, String> ch_nom;

	@FXML
	private TableColumn<Moniteur, String> ch_prenom;

	@FXML
	private TableColumn<Moniteur, String> ch_sexe;

	@FXML
	private TableColumn<Moniteur, String> ch_email;

	@FXML
	private TableColumn<Moniteur, String> ch_phone;

	@FXML
	private TableColumn<Moniteur, LocalDate> ch_date;

	    
	    private ObservableList<Moniteur> moniteurs = FXCollections.observableArrayList();
	    ModifierMoniteurController modifierMoniteurController = new ModifierMoniteurController();


   
    
   /* private void onButtonClick(ActionEvent event) {//new modification
        System.out.println("Le bouton a été cliqué !");
    }*/
	    private Moniteur_Service moniteurService = new Moniteur_Service();
	    
	    
	   
	    @FXML
	        private void color(MouseEvent event) {
	        	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
	        @FXML
	        private void color2(MouseEvent event) {
	        	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
	    
	    
	    
	    
	    
	    @FXML
	    public void switchToSecondary() throws IOException {
	        App.setRoot("secondary");
	    }  
	    
	    
	    @FXML
	    private void switchTomodifierMoniteur() throws IOException {
	        App.setRoot("modifierMoniteur");
	    }
	    
	    
	    
    
    @FXML
    public void initialize() {
        // Configuration des colonnes avec les propriétés de Moniteur
        ch_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        ch_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ch_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ch_sexe.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        ch_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        ch_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        ch_date.setCellValueFactory(new PropertyValueFactory<>("dateRecrutement"));  // Assure-toi que la propriété correspond à celle de ton Moniteur
        
        onAfficherClicked(); // Charge les données dans la TableView au démarrage
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterList(newValue));
        searchField.setStyle("-fx-background-color: #F6F9FE;");
        
    }
    
    @FXML
    public void onAfficherClicked() {
        // Charger les données depuis la base de données
        List<Moniteur> moniteurList =  moniteurService.getAllMoniteurs();
        
        // Déboguer la taille de la liste
        System.out.println("Nombre de personnes récupérées : " + moniteurList.size());
        
        // Convertir la liste en ObservableList et afficher dans TableView
        moniteurs.clear();
        moniteurs.addAll(moniteurList);

        // Mettre à jour le TableView
        table_p.setItems(moniteurs);
    }

    
    @FXML
    private void edit(ActionEvent event) {
        try {
            Moniteur selectedMoniteur = (Moniteur) table_p.getSelectionModel().getSelectedItem();
            if (selectedMoniteur == null) {
                showAlert("Please select a monitor to edit!");
                return;
            }

            // Charger la nouvelle fenêtre
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifierMoniteur.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur et passer les données
            ModifierMoniteurController controller = loader.getController();
            controller.setMoniteur(selectedMoniteur);

            // Afficher dans une nouvelle fenêtre
            Stage newStage = new Stage();
            newStage.setTitle("Edit Monitor");
            newStage.setScene(new Scene(root));
            
            controller.setStage(newStage);
            newStage.show();
           

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error opening the edit window!");
        }
    }

    
    
 




    

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    


    private void filterList(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            table_p.setItems(moniteurs );
            return;
        }

        ObservableList<Moniteur> filteredList = FXCollections.observableArrayList();
        for (Moniteur m :moniteurs ) {
            if (m.getPrenom().toLowerCase().contains(keyword.toLowerCase()) || 
                m.getNom().toLowerCase().contains(keyword.toLowerCase())) {
                filteredList.add(m);
            }
        }
        table_p.setItems(filteredList);
    }
  
    
    }

    
    
   
    
    
  


    
    
    



