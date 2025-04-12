package Controleur;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import Dao.Disponibilite_Dao;
import Service.Disponibilite_Service;
import Entities.Disponibilite;
import Entities.Moniteur;
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
//import service.Disponibilite_Service;
import javafx.stage.Stage;








public class DisponibilitesController {
	
	
	
	private Set<LocalDate> seenDates= new HashSet<>();
	 private int moniteurId;

	    @FXML
		private TextField searchField;
	 
	    @FXML
	    private TableView<Disponibilite> table_dispo;

	    @FXML
	    private TableColumn<Disponibilite, String> col_date;

	    @FXML
	    private TableColumn<Disponibilite, LocalTime> col_startTime;

	    @FXML
	    private TableColumn<Disponibilite, String> col_endTime;

	    @FXML
	    private TableColumn<Disponibilite, String> availabilityColumn;
	    @FXML
	    private TableColumn<Disponibilite, Boolean> col_select;
	    @FXML
	    private TableColumn<Disponibilite, Void> col_changeAvailability;
	   
	    private ObservableList<Disponibilite> disponibilitesList;
	    private Disponibilite_Service disponibiliteService;
	    
	    
	    public DisponibilitesController() {
	        disponibiliteService = new Disponibilite_Service();
	    }
	    
	    
	    
	    @FXML
	    public void initialize() {
	    	
	    	
	    	 col_date.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));

		        // Personnaliser l'affichage des lignes
		        table_dispo.setRowFactory(tv -> new TableRow<Disponibilite>() {
		            @Override
		            protected void updateItem(Disponibilite item, boolean empty) {
		                super.updateItem(item, empty);

		                if (item == null || empty) {
		                    setStyle("");  // Réinitialiser le style pour les lignes vides
		                } else {
		                    LocalDate date = item.getSessionDate();

		                    // Vérifier si c'est la première apparition de la date
		                    if (!seenDates.contains(date)) {
		                        seenDates.add(date);
		                        setStyle("-fx-background-color: lightblue;");  // Mettre en bleu toute la ligne
		                    } else {
		                        setStyle("");  // Style par défaut si la date est déjà rencontrée
		                    }
		                }
		            }
		        });

	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	col_startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
	    	col_endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
	    	//col_status.setCellValueFactory(new PropertyValueFactory<>("availability"));
	    	availabilityColumn.setCellFactory(cellData -> new TableCell<Disponibilite, String>() {
	    	    @Override
	    	    protected void updateItem(String item, boolean empty) {
	    	        super.updateItem(item, empty);

	    	        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
	    	            setText(null);
	    	        } else {
	    	            Disponibilite dispo = getTableRow().getItem();
	    	            setText(dispo.getAvailability() ? "Occupied" : " Not occupied");
	    	        }
	    	    }
	    	
	    	    
	    	
	    	});
	    	
	    	 col_select.setCellValueFactory(new PropertyValueFactory<>("selected"));
	    	 

	    	 col_select.setCellFactory(CheckBoxTableCell.forTableColumn(col_select));
	    	 col_select.setCellFactory(param -> new TableCell<Disponibilite, Boolean>() {
	    	     private final CheckBox checkBox = new CheckBox();

	    	     @Override
	    	     protected void updateItem(Boolean item, boolean empty) {
	    	         super.updateItem(item, empty);
	    	         if (!empty) {
	    	             checkBox.setSelected(item);
	    	             checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	    	                 Disponibilite disponibilite = getTableView().getItems().get(getIndex());
	    	                 disponibilite.setSelected(newValue);  // Met à jour la propriété 'selected' de l'objet
	    	             });
	    	             setGraphic(checkBox);
	    	         } else {
	    	             setGraphic(null);
	    	         }
	    	     }
	    	 });
	    	 
	    	 
	    	 col_changeAvailability.setCellFactory(param -> new TableCell<Disponibilite, Void>() {
	    	        private final Button btnChange = new Button("Change Availability");

	    	        {
	    	            // Lors du clic sur le bouton, on inverse l'état de la disponibilité
	    	            btnChange.setOnAction(event -> {
	    	                Disponibilite disponibilite = getTableView().getItems().get(getIndex());
	    	                toggleAvailability(disponibilite);
	    	            });
	    	        }

	    	        @Override
	    	        protected void updateItem(Void item, boolean empty) {
	    	            super.updateItem(item, empty);
	    	            if (!empty) {
	    	                // Ajouter le bouton à la cellule
	    	                setGraphic(btnChange);
	    	            } else {
	    	                setGraphic(null);
	    	            }
	    	        }
	    	    });
 
	    	 
	    	 
	    	 
	    	 
	    	 
	    	 
	        loadDisponibilites();	
	        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterList(newValue));
	        searchField.setStyle("-fx-background-color: #F6F9FE;");
	        
	        
	        
	        

	    }
	    
	    
	    
	    
	    
	    public void setMoniteurId(int id) {
	        this.moniteurId = id;
	       
	        loadDisponibilites();
	       
	    }

	    private void loadDisponibilites() {
	    	
	    	System.out.println(moniteurId);
	    	
	        List<Disponibilite> disponibilites = disponibiliteService.getDisponibilitesByMoniteurId(moniteurId);
	        
			System.out.println(disponibilites);
			 table_dispo.getItems().setAll(disponibilites);
			
			  disponibilitesList = FXCollections.observableArrayList(disponibilites);
			    table_dispo.setItems(disponibilitesList);		
			 
		
	    }
	    
	    

	    
	    
	    private void toggleAvailability(Disponibilite disponibilite) {
	        // Inverser la disponibilité (si 1 devient 0, sinon 1)
	        disponibilite.setAvailability(!disponibilite.getAvailability());

	        // Mettre à jour la disponibilité dans la base de données (si nécessaire)
	        disponibiliteService.updateDisponibilite(disponibilite);

	        // Rafraîchir la table pour refléter le changement
	        table_dispo.refresh();
	    }

	    
	    

	

	 


	    
	    
	    
	    
	    @FXML
	    private void handleDelete(ActionEvent event) {
	        // Récupérer les disponibilités sélectionnées (cases cochées)
	        ObservableList<Disponibilite> selectedDispos = table_dispo.getItems().filtered(Disponibilite::isSelected);

	        if (selectedDispos != null && !selectedDispos.isEmpty()) {
	            // Demander confirmation
	            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	            confirm.setTitle("Deletion Confirmation");
	            confirm.setHeaderText(null);
	            confirm.setContentText("Do you really want to delete the selected availability?");
	            
	            if (confirm.showAndWait().get() == ButtonType.OK) {
	                try {
	                    boolean allDeleted = true;
	                    for (Disponibilite dispo : selectedDispos) {
	                        // Appeler la méthode de suppression du DAO
	                        boolean deleted = disponibiliteService.deleteDisponibilite(dispo.getId());
	                        if (!deleted) {
	                            allDeleted = false;
	                            break;
	                        }
	                    }
	                    
	                    if (allDeleted) {
	                        // Afficher un message de succès
	                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                        alert.setTitle("Success");
	                        alert.setHeaderText(null);
	                        alert.setContentText("The availability has been successfully deleted.");
	                        alert.showAndWait();
	                        
	                        // Recharger la table après suppression
	                        loadDisponibilites();
	                    } else {
	                        // Afficher un message d'erreur si la suppression a échoué
	                        Alert alert = new Alert(Alert.AlertType.ERROR);
	                        alert.setTitle("Error");
	                        alert.setHeaderText(null);
	                        alert.setContentText("Failed to delete some availability.");
	                        alert.showAndWait();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Error");
	                    alert.setHeaderText(null);
	                    alert.setContentText("An error occurred during deletion.");
	                    alert.showAndWait();
	                }
	            }
	        } else {
	            // Aucun élément sélectionné
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Warning");
	            alert.setHeaderText(null);
	            alert.setContentText("Please select one or more availability slots to delete.");
	            alert.showAndWait();
	        }
	    }

	    
	    

	    
	    
	    @FXML
	    private void handleAdd(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/add_dispo.fxml"));
	            Parent root = loader.load();

	            // Récupérer le contrôleur de la vue d'ajout
	            AddDisponibiliteController addController = loader.getController();
	            addController.setMoniteurId(moniteurId);

	            Stage stage = new Stage();
	            stage.setTitle("Add availability");
	            stage.setScene(new Scene(root));
	            stage.showAndWait();

	            // Recharger les disponibilités après ajout
	            loadDisponibilites();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    @FXML
	    public void switchToWeeklyPlanner(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Auto_Ecolee/Auto_Ecolee/weeklyPlanner.fxml"));
	            Parent weeklyPlannerView = loader.load();

	            // Récupérer la scène actuelle et changer la racine
	            Scene currentScene = ((Node) event.getSource()).getScene();
	            currentScene.setRoot(weeklyPlannerView);
	            
	            weeklyPlannerController wController = loader.getController();
	            wController.setMoniteurId(moniteurId);

	            System.out.println("Switch vers weeklyplanner.fxml réussi !");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	    
	    
	    
	    
	    
	    
	    
	    
	    
	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    
	    
	    
	    
	    private void filterList(String keyword) {
	        if (keyword == null || keyword.trim().isEmpty()) {
	            table_dispo.setItems(disponibilitesList);
	            return;
	        }

	        ObservableList<Disponibilite> filteredList = disponibilitesList.stream()
	            .filter(dispo -> dispo.getSessionDate().toString().toLowerCase().contains(keyword.toLowerCase()) ||
	                            dispo.getStartTime().toString().toLowerCase().contains(keyword.toLowerCase()) ||
	                            dispo.getEndTime().toString().toLowerCase().contains(keyword.toLowerCase()) ||
	                            (dispo.getAvailability() ? "on duty" : "off work").toLowerCase().contains(keyword.toLowerCase()))
	            .collect(Collectors.toCollection(FXCollections::observableArrayList));

	        table_dispo.setItems(filteredList);
	    }
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	   
	    }

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	  



	    
	    
	    
	    
	    
	    
	    
	





	


