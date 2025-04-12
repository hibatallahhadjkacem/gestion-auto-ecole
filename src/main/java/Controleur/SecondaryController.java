package Controleur;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

import java.time.LocalDate;
import java.util.List;

import Auto_Ecolee.Auto_Ecolee.App;
import Service.Moniteur_Service;
import Entities.Moniteur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.Callback;

public class SecondaryController {
    @FXML
    private Button btn_add;
	@FXML
    private Button btn_insts;
	@FXML
	private TableView<Moniteur>table_p;
	
	@FXML
	private TableColumn<Moniteur, Integer> ch_id;

	@FXML
	private TableColumn<Moniteur, String> ch_nom;

	@FXML
	private TableColumn<Moniteur, String> ch_prenom;
	@FXML
	private TableColumn<Moniteur, Void> ch_action;
	
	 @FXML
	  private TableColumn<Moniteur, Void> col_schedule;
	



	 private ObservableList<Moniteur> moniteurs = FXCollections.observableArrayList();
	
	 private Moniteur_Service moniteurService = new Moniteur_Service();

    
	 
	 @FXML
	     private void color(MouseEvent event) {
	     	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
	     @FXML
	     private void color2(MouseEvent event) {
	     	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
	 
	 
	 
	 
	 @FXML
    public void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    public void switchToAdd() throws IOException {
        App.setRoot("add");
    }
    

    
    @FXML
    public void initialize() {
        // Configuration des colonnes
 
        ch_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        ch_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        ch_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        
        
        onAfficherClicked();
        
        

        // Ajouter la colonne "Action" avec l'icône de suppression
        TableColumn<Moniteur, Void> colAction = new TableColumn<>("");

        colAction.setCellFactory(param -> new TableCell<>() {
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/delete.jpg")));

            {
                deleteIcon.setFitWidth(18);  // Redimensionner l'icône si nécessaire
                deleteIcon.setFitHeight(18);
                deleteIcon.setOnMouseClicked(event -> {
                    // Appeler la méthode de suppression quand l'icône est cliquée
                    onDeleteClicked();
                });
                
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : new HBox(10, deleteIcon));
            }
        });

        table_p.getColumns().add(colAction);
        
        
        
  
        
        	TableColumn<Moniteur, Void> col_schedule = new TableColumn<>("");
        	
        	if (col_schedule == null) {
                System.out.println("Erreur: col_schedule est null");
                return;
            }
        	
        	
        	
            col_schedule.setCellFactory(param -> new TableCell<>() {
            	private final ImageView schIcon = new ImageView(new Image(getClass().getResourceAsStream("/icons/sch111.png")));

                {   
                	schIcon.setFitWidth(18);  
                	schIcon.setFitHeight(18);
                	
                	schIcon.setOnMouseClicked(event -> {
                        Moniteur moniteur = getTableView().getItems().get(getIndex());
                        openDisponibiliteView(moniteur.getId());
                        
                    });
                	
                }
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	
            	/*private final Button scheduleBtn = new Button("🗓️");
                
                    scheduleBtn.setOnAction((ActionEvent event) -> {
                        Moniteur moniteur = getTableView().getItems().get(getIndex());
                        openDisponibiliteView(moniteur.getId());
                    });
                }*/

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox box = new HBox(schIcon);
                        setGraphic(box);
                    }
                }
            });
        
            table_p.getColumns().add(col_schedule);
    }



private void openDisponibiliteView(int moniteurId) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/weeklyPlanner.fxml"));
        Parent root = loader.load();
        weeklyPlannerController controller = loader.getController();
        controller.setMoniteurId(moniteurId);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Disponibilités du Moniteur");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }

}





        
        
    
    
    
    

              
        
        
    
        
    
    @FXML
    private void onAfficherClicked() {
        // Charger les données depuis la base de données
        List<Moniteur> moniteurList =  moniteurService.getAllMoniteurs_butsome_S();
        
        
      // Convertir la liste en ObservableList et afficher dans TableView
        moniteurs.clear();
        moniteurs.addAll(moniteurList);

        // Mettre à jour le TableView
        table_p.setItems(moniteurs);
    }
    
    
    
    
    @FXML
    private void onDeleteClicked() {
        // Récupérer le moniteur sélectionné dans la TableView
        Moniteur selectedMoniteur = table_p.getSelectionModel().getSelectedItem();

        if (selectedMoniteur != null) {
            // Récupérer l'ID du moniteur sélectionné
            int selectedId = selectedMoniteur.getId();

            // Appeler la méthode delete du service ou du DAO pour supprimer le moniteur de la base de données
            boolean success = moniteurService.deleteMoniteur_C(selectedId);

            if (success) {
                // Si la suppression est réussie, actualiser la TableView
                moniteurs.remove(selectedMoniteur); // Supprimer l'élément de la liste ObservableList
                table_p.setItems(moniteurs); // Mettre à jour la TableView

                // Afficher une alerte de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Moniteur supprimé avec succès.");
                alert.showAndWait();
            } else {
                // Si la suppression échoue, afficher une alerte d'erreur
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText("Échec de la suppression");
                alert.setContentText("Une erreur est survenue lors de la suppression du moniteur.");
                alert.showAndWait();
            }
        } else {
            // Si aucun moniteur n'est sélectionné, afficher une alerte d'avertissement
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Avertissement");
            alert.setHeaderText("Aucun moniteur sélectionné");
            alert.setContentText("Veuillez sélectionner un moniteur à supprimer.");
            alert.showAndWait();
        }
    }
    
 
    
    
    
    
    
    
}



 
    
    
    
 
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

    



    
    
    

