package Controleur;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import Dao.Paiement_DAO;
import Dao.Tranche_Dao;
import Service.Paiement_Service;
import Service.Tranche_Service;
import Entities.Moniteur;
import Entities.Paiement;
import Entities.Tranche;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class PaiementController {
	
	private Paiement_DAO paiementDAO;
	
	
@FXML
private Button editButton;
@FXML
private TextField searchField;
@FXML
private TableColumn<Paiement, Integer> col_id;
@FXML
private TableColumn<Paiement, Integer> col_cond_id;
@FXML
private TableColumn<Paiement, String> col_date;
@FXML
private TableColumn<Paiement, Double> col_amount;
@FXML
private TableColumn<Paiement, String> col_pay_method;
@FXML
private TableColumn<Paiement, String>  col_f_name;
@FXML
private TableColumn<Paiement, String>  col_l_name;
/*@FXML
private TableColumn<Paiement, String>  col_statut;*/
@FXML
private TableView<Paiement> paiementTable;

private Tranche_Service TService;





private ObservableList<Paiement> paiements = FXCollections.observableArrayList();
private Paiement_Service paiementService = new Paiement_Service();



@FXML
public void  initialize() {
	
	TService = new Tranche_Service();
	  editButton.setOnAction(e -> handleEditPayment());
	
	
	
    col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
    col_cond_id.setCellValueFactory(new PropertyValueFactory<>("idCandidat"));
    col_date.setCellValueFactory(new PropertyValueFactory<>("datePaiement"));
    col_amount.setCellValueFactory(new PropertyValueFactory<>("montantTotal"));
    col_pay_method.setCellValueFactory(new PropertyValueFactory<>("modePaiement"));
    //col_statut.setCellValueFactory(new PropertyValueFactory<>("statutGlobal"));
    col_f_name.setCellValueFactory(new PropertyValueFactory<>("candidatPrenom"));
    col_l_name.setCellValueFactory(new PropertyValueFactory<>("candidatNom"));
    
    
    TableColumn<Paiement, Void> colAction = new TableColumn<>("");
    
    colAction.setCellFactory(param -> new TableCell<>() {
        private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete.jpg")));
        private final Button deleteButton = new Button();

        {
            deleteIcon.setFitWidth(16);
            deleteIcon.setFitHeight(16);
            deleteButton.setGraphic(deleteIcon);
            deleteButton.setStyle("-fx-background-color: transparent;");

            deleteButton.setOnAction(event -> {
                Paiement paiement = getTableView().getItems().get(getIndex());

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Deletion Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Do you really want to delete this payment?");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        //Paiement_Service paiementService = new Paiement_Service();
                        paiementService.supprimerPaiement(paiement.getId(), paiement.getModePaiement());

                        getTableView().getItems().remove(paiement);

                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Payment successfully deleted!");
                        success.showAndWait();

                    } catch (Exception e) {
                        e.printStackTrace();
                        Alert error = new Alert(Alert.AlertType.ERROR);
                        error.setTitle("Error");
                        error.setHeaderText("Deletion error");
                        error.setContentText("An error occurred while deleting the payment.");
                        error.showAndWait();
                    }
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(deleteButton);
            }
        }
    });

    


    paiementTable.getColumns().add(colAction);
    
    
    

    TableColumn<Paiement, Void> actionCol = new TableColumn<>("Tranches");

    actionCol.setCellFactory(col -> new TableCell<Paiement, Void>() {
        private final Button btn = new Button("View installments");

        {
            btn.setOnAction(e -> {
                Paiement paiement = getTableView().getItems().get(getIndex());
                if (paiement.getModePaiement().startsWith("facilite")) {
                    ouvrirTranchesPourPaiement(paiement);
                }
            });
        }

        @Override
        protected void updateItem(Void item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || !getTableView().getItems().get(getIndex()).getModePaiement().startsWith("facilite")) {
                setGraphic(null);
            } else {
                setGraphic(btn);
            }
        }
    });

    paiementTable.getColumns().add(actionCol);

    	
    	

    
    
    
     searchField.textProperty().addListener((observable, oldValue, newValue) -> filterList(newValue));
     searchField.setStyle("-fx-background-color: #F6F9FE;");
     
     onAfficherClicked(); 
     
     
    
    	
  
} 
    	 
    	 
    	 


    	 
    	 
    	 
    	 


@FXML
private void color(MouseEvent event) {
	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
@FXML
private void color2(MouseEvent event) {
	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }



@FXML
public  void onAfficherClicked() {
    // Charger les données depuis la base de données
    List<Paiement> paiementList =  paiementService.afficher_paiement();
    
    // Déboguer la taille de la liste
    System.out.println("Nombre  récupérées : " + paiementList.size());
    
    // Convertir la liste en ObservableList et afficher dans TableView
    paiements.clear();
    paiements.addAll(paiementList);

    // Mettre à jour le TableView
    paiementTable.setItems(paiements);
}



@FXML
private void handleAjouterPaiement(ActionEvent event)  {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/ajouter_paiement.fxml"));
        Parent root = loader.load();

        
        AjouterPaiementController ajouterCtrl = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Add Payment");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        Paiement paiement = ajouterCtrl.getPaiement();
        List<Tranche> tranches = new ArrayList<>();

        if (paiement != null) {
            String mode = paiement.getModePaiement();
             BigDecimal montantTotal=paiement.getMontantTotal();          
             int paiementId = 0;
			try {
				  paiementId = paiementService.ajouterPaiement(paiement);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            if ("facilite:2tranches".equalsIgnoreCase(mode) || "facilite:3tranches".equalsIgnoreCase(mode)) {
                FXMLLoader trancheLoader = new FXMLLoader(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/ajouter_tranches.fxml"));
                Parent trancheRoot = trancheLoader.load();

                AjouterTranchesController trancheController = trancheLoader.getController();
                trancheController.initTranches(mode);
                trancheController.setMontantTotal(montantTotal);  //passer le mt au trancheController


                Stage trancheStage = new Stage();
                trancheStage.setTitle("Add Installments");
                trancheStage.setScene(new Scene(trancheRoot));
                trancheStage.sizeToScene();
                trancheStage.showAndWait();
                

                tranches = trancheController.getTranches();
                System.out.println(tranches);
                
               if( TService.ajouterTranches(paiementId, tranches,montantTotal)==true)
               { showAlert(Alert.AlertType.INFORMATION, "Success", "The payment has been successfully added.");}
            	   
               
            }
            System.out.println("lehne");

            //Paiement_Service service = new Paiement_Service();
            //paiementService.ajouterPaiement(paiement, tranches);
            
            
            
           
            onAfficherClicked();
        }

    } catch (IOException e) {
        e.printStackTrace();
    }
}















private void filterList(String keyword) {
    if (keyword == null || keyword.trim().isEmpty()) {
    	paiementTable.setItems(paiements );
        return;
    }

    ObservableList<Paiement> filteredList = FXCollections.observableArrayList();
    for (Paiement p :paiements ) {
        if (p.getCandidatNom().toLowerCase().contains(keyword.toLowerCase()) || 
            p.getCandidatPrenom().toLowerCase().contains(keyword.toLowerCase())) {
            filteredList.add(p);
        }
    }
    paiementTable.setItems(filteredList);
}







private void ouvrirTranchesPourPaiement(Paiement paiement) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/AfficherTranches.fxml"));
        Parent root = loader.load();

        AfficherTranchesController controller = loader.getController();
        controller.setTranches(TService.getTranchesByPaiement(paiement.getId()));

        Stage stage = new Stage();
        stage.setTitle("Tranches du paiement ID: " + paiement.getId());
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }
}



/*private void editSelectedPaiement() {
    Paiement selected = tablePaiements.getSelectionModel().getSelectedItem();
    if (selected != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit_paiement.fxml"));
            Parent root = loader.load();

            EditPaiementController controller = loader.getController();
            controller.setPaiement(selected);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Après save dans la fenêtre edit, si facilité → ouvrir fenêtre tranche
            if (controller.isSaved()) {
                if (selected.getModePaiement().toLowerCase().contains("facilite")) {
                    openEditTranches(selected);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}*/


/*private void editSelectedPaiement() {
    Paiement selected =paiementTable.getSelectionModel().getSelectedItem();
    if (selected != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fsb/proj_serious_version/edit_paiement.fxml"));
            Parent root = loader.load();

            EditPaiementController controller = loader.getController();
            controller.setPaiement(selected);
            

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            System.out.println("lalalal   "+controller.isSaved());

            // Après save dans la fenêtre edit, si facilité → ouvrir fenêtre tranche
            if (controller.isSaved()) {
                if (selected.getModePaiement().toLowerCase().contains("facilite")) {
                    openEditTranches(selected);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

private void openEditTranches(Paiement paiement) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fsb/proj_serious_version/edit_tranches.fxml"));
        Parent root = loader.load();

        EditTranchesController controller = loader.getController();
        controller.setPaiement(paiement);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    } catch (IOException e) {
        e.printStackTrace();
    }

}*/
private Paiement getSelectedPaiement() {
    Paiement selectedPaiement = paiementTable.getSelectionModel().getSelectedItem();
    return selectedPaiement; // Renvoie l'élément sélectionné
}

@FXML
private void handleEditPayment() {
    Paiement paiement = getSelectedPaiement(); // Récupérer le paiement sélectionné depuis la table ou autre source
    if (paiement == null) {
        showError("Aucun paiement sélectionné.");
        return;
    }

    // Récupérer les tranches du paiement sélectionné
    //List<Tranche> tranches = paiementService.getTranchesForPaiement(paiement.getId());
    List<Tranche> tranches = paiementService.getTranchesByPaiementId(paiement.getId());

    // Charger la fenêtre d'édition avec les données existantes
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/edit_payment.fxml"));
        Parent root = loader.load();
        EditPaymentController editController = loader.getController();
        editController.initData(paiement, tranches); // Initialiser les données de la fenêtre d'édition

        // Afficher la fenêtre dans une nouvelle fenêtre Stage
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Éditer Paiement");
        stage.show();
        onAfficherClicked();

    } catch (IOException e) {
        showError("Erreur lors de l'ouverture de la fenêtre d'édition.");
    }
}

private void showError(String message) {
    // Méthode utilitaire pour afficher des messages d'erreur
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}







private void showAlert(Alert.AlertType type, String titre, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(titre);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}

}