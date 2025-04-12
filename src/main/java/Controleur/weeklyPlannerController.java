package Controleur;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Auto_Ecolee.Auto_Ecolee.App;
import Dao.Disponibilite_Dao;
import Service.Disponibilite_Service;
import Entities.Disponibilite;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TableRow;


public class weeklyPlannerController {
	

    

	    private int moniteurId;
	    @FXML
	    private Text didi;

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
	    
	    /**************************************************************************/
	    /**************************************************************************/

	    @FXML
	    private TableView<Disponibilite> table_dispo_jour;
	    
	    @FXML
	    private TableColumn<Disponibilite, String> col_todaySessionDate;
	    
	    @FXML
	    private TableColumn<Disponibilite, String> col_todayStartTime;
	    
	    @FXML
	    private TableColumn<Disponibilite, String> col_todayEndTime;
	    
	    @FXML
	    private TableColumn<Disponibilite, String> col_todayDisponibilite;

	    @FXML
	    private TableColumn<Disponibilite, Boolean> col_todaySelect;
	    @FXML
	    private TableColumn<Disponibilite, Void> col_todayChangeAvailability;
	    
	    
	    @FXML
	    private Button more_button;

	    private Set<LocalDate> seenDates= new HashSet<>();
	    
        private Disponibilite_Service disponibiliteService;
	    
	    
	    public weeklyPlannerController() {
	        disponibiliteService = new Disponibilite_Service();
	    }
	    
	    
	    @FXML
	    public void initialize() {

	        // Initialisation des colonnes de la première table
	       // col_date.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
	        col_startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
	        col_endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
	        
	        availabilityColumn.setCellFactory(cellData -> new TableCell<Disponibilite, String>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
	                    setText(null);
	                } else {
	                    Disponibilite dispo = getTableRow().getItem();
	                    setText(dispo.getAvailability() ? "Occupied" : " Not Occupied");
	                }
	            }
	        });

	        
	        
	        
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

	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        // Initialisation des colonnes de sélection (checkbox) pour table_dispo
	        col_select.setCellValueFactory(new PropertyValueFactory<>("selected"));
	        col_select.setCellFactory(CheckBoxTableCell.forTableColumn(col_select));

	        // Action sur la case à cocher pour la table_dispo
	        col_select.setCellFactory(param -> new TableCell<Disponibilite, Boolean>() {
	            private final CheckBox checkBox = new CheckBox();

	            @Override
	            protected void updateItem(Boolean item, boolean empty) {
	                super.updateItem(item, empty);
	                if (!empty) {
	                    checkBox.setSelected(item);
	                    checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	                        Disponibilite disponibilite = getTableView().getItems().get(getIndex());
	                        disponibilite.setSelected(newValue);
	                    });
	                    setGraphic(checkBox);
	                } else {
	                    setGraphic(null);
	                }
	            }
	        });

	        // Initialisation de la colonne de changement de disponibilité
	        col_changeAvailability.setCellFactory(param -> new TableCell<Disponibilite, Void>() {
	            private final Button btnChange = new Button("Change Availability");

	            {
	                btnChange.setOnAction(event -> {
	                    Disponibilite disponibilite = getTableView().getItems().get(getIndex());
	                    toggleAvailability(disponibilite);
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                if (!empty) {
	                    setGraphic(btnChange);
	                } else {
	                    setGraphic(null);
	                }
	            }
	        });
/****************************************************************************************/
	        /*******************************************************************************************/
	        
	        
	        // Initialisation des colonnes de la deuxième table (table_dispo_jour)
	        col_todaySessionDate.setCellValueFactory(new PropertyValueFactory<>("sessionDate"));
	        col_todayStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
	        col_todayEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));

	        col_todayDisponibilite.setCellFactory(cellData -> new TableCell<Disponibilite, String>() {
	            @Override
	            protected void updateItem(String item, boolean empty) {
	                super.updateItem(item, empty);
	                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
	                    setText(null);
	                } else {
	                    Disponibilite dispo = getTableRow().getItem();
	                    setText(dispo.getAvailability() ? "Occupied" : " Not Occupied");
	                }
	            }
	        });

	        // Initialisation des cases à cocher pour la table_dispo_jour
	        col_todaySelect.setCellValueFactory(new PropertyValueFactory<>("selected"));
	        col_todaySelect.setCellFactory(CheckBoxTableCell.forTableColumn(col_todaySelect));

	        col_todaySelect.setCellFactory(param -> new TableCell<Disponibilite, Boolean>() {
	            private final CheckBox checkBox = new CheckBox();

	            @Override
	            protected void updateItem(Boolean item, boolean empty) {
	                super.updateItem(item, empty);
	                if (!empty) {
	                    checkBox.setSelected(item);
	                    checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
	                        Disponibilite disponibilite = getTableView().getItems().get(getIndex());
	                        disponibilite.setSelected(newValue);
	                    });
	                    setGraphic(checkBox);
	                } else {
	                    setGraphic(null);
	                }
	            }
	        });

	        // Colonne de changement de disponibilité pour la table_dispo_jour
	        col_todayChangeAvailability.setCellFactory(param -> new TableCell<Disponibilite, Void>() {
	            private final Button btnChange = new Button("Change Availability");

	            {
	                btnChange.setOnAction(event -> {
	                    Disponibilite disponibilite = getTableView().getItems().get(getIndex());
	                    toggleAvailability(disponibilite);
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                if (!empty) {
	                    setGraphic(btnChange);
	                } else {
	                    setGraphic(null);
	                }
	            }
	       
	        
	        
	     });
	        
	        loadDisponibilites();
	        loadDisponibilitesToday();
	        
	        
	    }
          
	    
	    @FXML
	    public void switchToDisponibilites() throws IOException {
	        App.setRoot("Disponibilites");
	    }
	    
	    
	    
	    
	    
	    
	    
	    public void setMoniteurId(int id) {
	        this.moniteurId = id;
	       
	        loadDisponibilites();
	        loadDisponibilitesToday();
	       
	    }
	    
	    @FXML
	    public void loadDisponibilites() {
	    	
	    	System.out.println(moniteurId);
	    	
	        List<Disponibilite> disponibilites = disponibiliteService.getDisponibilitesByMoniteurIdWeek(moniteurId);
	        
			System.out.println(disponibilites);
			 table_dispo.getItems().setAll(disponibilites);
			
					
			 	 
		
	    }
	    @FXML
	    private  void loadDisponibilitesToday(){
	    	System.out.println(moniteurId);
	        List<Disponibilite> disponibilitesToday = disponibiliteService.getDisponibilitesToday( moniteurId);
	        System.out.println(disponibilitesToday);
	        table_dispo_jour.getItems().setAll(disponibilitesToday);
	    }
	    
	    
	    
	    private void toggleAvailability(Disponibilite disponibilite) {
	        // Inverser la disponibilité (si 1 devient 0, sinon 1)
	        disponibilite.setAvailability(!disponibilite.getAvailability());

	        // Mettre à jour la disponibilité dans la base de données (si nécessaire)
	        disponibiliteService.updateDisponibilite(disponibilite);

	        // Rafraîchir la table pour refléter le changement
	        table_dispo.refresh();
	        table_dispo_jour.refresh();
	    }

	    
	    

	



	    
	    
	    
	    
	    @FXML
	    private void handleDelete(ActionEvent event) {
	        // Récupérer les disponibilités sélectionnées (cases cochées)
	        ObservableList<Disponibilite> selectedDispos = table_dispo.getItems().filtered(Disponibilite::isSelected);
	        ObservableList<Disponibilite> selectedDispos_today = table_dispo_jour.getItems().filtered(Disponibilite::isSelected);

	        if ((selectedDispos != null && !selectedDispos.isEmpty())||(selectedDispos_today != null && !selectedDispos_today.isEmpty())) {
	            // Demander confirmation
	            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
	            confirm.setTitle("Confirmation de suppression");
	            confirm.setHeaderText(null);
	            confirm.setContentText("Voulez-vous vraiment supprimer les disponibilités sélectionnées ?");
	            
	            if (confirm.showAndWait().get() == ButtonType.OK) {
	            	          	
	                try {
	                    boolean allDeleted = true;
	                    
	                    if (selectedDispos != null && !selectedDispos.isEmpty()){
	                    for (Disponibilite dispo : selectedDispos) {
	                        // Appeler la méthode de suppression du DAO
	                        boolean deleted = Disponibilite_Dao.deleteDisponibilite(dispo.getId());
	                        if (!deleted) {
	                            allDeleted = false;
	                            break;
	                        }
	                    }}
	                    
	                    else {
	                    	 for (Disponibilite dispo : selectedDispos_today) {
	 	                        // Appeler la méthode de suppression du DAO
	 	                        boolean deleted = Disponibilite_Dao.deleteDisponibilite(dispo.getId());
	 	                        if (!deleted) {
	 	                            allDeleted = false;
	 	                            break;
	 	                        }
	 	                    }}
	 
	                    
	                    if (allDeleted) {
	                        // Afficher un message de succès
	                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	                        alert.setTitle("Succès");
	                        alert.setHeaderText(null);
	                        alert.setContentText("Les disponibilités ont été supprimées avec succès.");
	                        alert.showAndWait();
	                        
	                        // Recharger la table après suppression
	                        loadDisponibilites();
	                        loadDisponibilitesToday();
	                        
	                    } else {
	                        // Afficher un message d'erreur si la suppression a échoué
	                        Alert alert = new Alert(Alert.AlertType.ERROR);
	                        alert.setTitle("Erreur");
	                        alert.setHeaderText(null);
	                        alert.setContentText("Échec de la suppression de certaines disponibilités.");
	                        alert.showAndWait();
	                    }
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    Alert alert = new Alert(Alert.AlertType.ERROR);
	                    alert.setTitle("Erreur");
	                    alert.setHeaderText(null);
	                    alert.setContentText("Une erreur est survenue lors de la suppression.");
	                    alert.showAndWait();
	                }
	            }
	        } else {
	            // Aucun élément sélectionné
	            Alert alert = new Alert(Alert.AlertType.WARNING);
	            alert.setTitle("Avertissement");
	            alert.setHeaderText(null);
	            alert.setContentText("Veuillez sélectionner une ou plusieurs disponibilités à supprimer.");
	            alert.showAndWait();
	        }
	    }

	    
	    

	    
	    
	    @FXML
	    private  void handleAdd(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fsb/proj_serious_version/add_dispo.fxml"));
	            Parent root = loader.load();

	            // Récupérer le contrôleur de la vue d'ajout
	            AddDisponibiliteController addController = loader.getController();
	            addController.setMoniteurId(moniteurId);
	            

	            Stage stage = new Stage();
	            stage.setTitle("Ajouter une disponibilité");
	            stage.setScene(new Scene(root));
	            stage.showAndWait();
  
	            // Recharger les disponibilités après ajout
	            initialize();
	            //loadDisponibilites();
	            //loadDisponibilitesToday();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	   
	    
	    
	    @FXML
	    public void switchToDisponibilites(ActionEvent event) {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/fsb/proj_serious_version/disponibilites.fxml"));
	            Parent disponibilitesView = loader.load();

	            // Récupérer la scène actuelle et changer la racine
	            Scene currentScene = ((Node) event.getSource()).getScene();
	            currentScene.setRoot(disponibilitesView);
	            
	            DisponibilitesController dController = loader.getController();
	            dController.setMoniteurId(moniteurId);

	            System.out.println("Switch vers disponibilites.fxml réussi !");
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
	

}
