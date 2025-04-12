package Controleur;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import Entities.Tranche;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AfficherTranchesController {

	

	    @FXML
	    private TableView<Tranche> tableTranches;
	    @FXML
	    private TableColumn<Tranche, Integer> colNumero;
	    @FXML
	    private TableColumn<Tranche, LocalDate> colDateEcheance;
	    @FXML 
	    private TableColumn<Tranche, BigDecimal> colMontant;
	    @FXML
	    private TableColumn<Tranche, String> colStatut;
	    @FXML
	    private TableColumn<Tranche, LocalDate> colDatePaiement;
	    
	    
	    
	   
	    public void initialize() {
	        colNumero.setCellValueFactory(new PropertyValueFactory<>("numeroTranche"));
	        colDateEcheance.setCellValueFactory(new PropertyValueFactory<>("dateLimite"));
	        colMontant.setCellValueFactory(new PropertyValueFactory<>("montant"));
	        colStatut.setCellValueFactory(new PropertyValueFactory<>("statut"));
	        colDatePaiement.setCellValueFactory(new PropertyValueFactory<>("datePaiementEffectif"));
	    }

	    public void setTranches(List<Tranche> tranches) {
	        tableTranches.getItems().setAll(tranches);
	    
	}

	
	    @FXML
	    private void handleEditTranche(ActionEvent event) {
	        Tranche selected = tableTranches.getSelectionModel().getSelectedItem();

	        if (selected != null) {
	            try {
	                FXMLLoader loader = new FXMLLoader(getClass().getResource("edit_tranche.fxml"));
	                Parent root = loader.load();

	                // Passer la tranche au contrôleur d'édition
	                EditTrancheController controller = loader.getController();
	                controller.setTranche(selected); // méthode à créer

	                Stage stage = new Stage();
	                stage.setTitle(" Edit Installment");
	                stage.setScene(new Scene(root));
	                stage.initModality(Modality.APPLICATION_MODAL);
	                stage.showAndWait();

	                // Après fermeture de la fenêtre, tu peux rafraîchir ta liste :
	                tableTranches.refresh(); // ou recharger depuis la BDD
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } else {
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("No selection");
	            alert.setHeaderText(null);
	            alert.setContentText("Please select an installment to edit");
	            alert.showAndWait();
	        }
	
	
	
	
	    }
}
