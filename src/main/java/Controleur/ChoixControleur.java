package Controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TableCell;


import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Categorie;
import Entities.Vehicule;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ChoixControleur {
	@FXML
	private TableView<Vehicule> tab;
    @FXML
    private TableColumn<Vehicule, String> modelC;
    @FXML
    private TableColumn<Vehicule, String> immatC;
    
	@FXML
	private TableColumn<Vehicule, Void> deleteC;
	
	@FXML
	private TableColumn<Vehicule, LocalDate> date;
	@FXML
	private TableColumn<Vehicule, Boolean> Availability;
	
	@FXML
	private Button btnGet;
	@FXML
	private Button btnUapdate;
	@FXML
	private Button btnDespo;
	//notif
	@FXML
	private Pane notifPane;

	@FXML
	private Button btnnotif;

	
	
	@FXML
	private ListView<Node> notifList;
	private boolean isNotifVisible = false;
	
	
	
	//////////

	
	private VehiculeControleur vehiculeControleur;
	private affichageVehiculeControleur affichageVehiculeControleur=new affichageVehiculeControleur();
	
	public void setAffichageVehiculeControleur(affichageVehiculeControleur affichageVehiculeControleur) {
		this.affichageVehiculeControleur=affichageVehiculeControleur;
	}
	
	

	public void setVehiculeController(VehiculeControleur vehiculeControleur) {
	        this.vehiculeControleur = vehiculeControleur;
	        afficherVehicules();
	    }

	@FXML
	public void initialize() {
	    modelC.setCellValueFactory(new PropertyValueFactory<>("model"));
	    immatC.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
	    date.setCellValueFactory(new PropertyValueFactory<>("dateMiseEnService"));
	    //Availability.setCellValueFactory(new PropertyValueFactory<>("availability"));

	    if (vehiculeControleur == null) {
	        vehiculeControleur = new VehiculeControleur();
	    }
	    
	    setVehiculeController(vehiculeControleur); 
	    initActionColumns();
        Tooltip tooltip = new Tooltip("View vehicle details");
        btnGet.setTooltip(tooltip);
        tooltip.setShowDelay(Duration.ZERO);
        Tooltip tooltip2 = new Tooltip("Update vehicle");
        btnUapdate.setTooltip(tooltip2);
        tooltip2.setShowDelay(Duration.ZERO);
        //btnDespo
        Tooltip tooltip3 = new Tooltip("Check the availability of vehicles");
        btnDespo.setTooltip(tooltip3);
        tooltip3.setShowDelay(Duration.ZERO);
        
        //notif
        
        notifPane.setVisible(false);
        Tooltip tooltip4 = new Tooltip("Click to see the notifications");
        btnnotif.setTooltip(tooltip4);
        tooltip4.setShowDelay(Duration.ZERO);
	}
	
	//notif 
	@FXML
	private void notif() throws SQLException {
	    isNotifVisible = !isNotifVisible;
	    notifPane.setVisible(isNotifVisible);

	    if (isNotifVisible) {
	        // Suppose que tu récupères ta liste de véhicules depuis un service ou une DAO
	        List<Vehicule> vehicules = vehiculeControleur.getAllVehicules(); // À adapter
	        List<Node> alerts = vehiculeControleur.genererNotifications(vehicules);  // Liste de Nodes

	        // Définir le CellFactory pour la ListView afin de gérer des objets de type Node
	        notifList.setCellFactory(listView -> new ListCell<Node>() {
	            @Override
	            protected void updateItem(Node item, boolean empty) {
	                super.updateItem(item, empty);

	                if (empty || item == null) {
	                    setText(null);
	                    setGraphic(null);
	                } else {
	                    setGraphic(item);  // Mettre l'élément graphique (TextFlow) dans la cellule
	                }
	            }
	        });

	        // Vider la liste avant d'ajouter les nouveaux éléments
	        notifList.getItems().clear();

	        if (alerts.isEmpty()) {
	            // Créer un Label avec du texte stylisé pour "Aucune alerte"
	            Label noAlertsLabel = new Label("Aucune alerte pour l'instant 🚗");
	            noAlertsLabel.setStyle(
	                "-fx-font-weight: bold; " +  
	                "-fx-font-size: 12px; " +   
	                "-fx-text-fill: black;" +     
	                "-fx-background-color: transparent; " +  // Fond transparent pour éviter le cadre
	                "-fx-padding: 20px; " 
	            );

	            StackPane container = new StackPane();
	            container.setStyle(" -fx-background-color: transparent; -fx-padding: 20px; -fx-alignment: center;");  // Pas de fond et pas de bordure
	            container.getChildren().add(noAlertsLabel);

	            // Ajouter le conteneur à la ListView
	            notifList.getItems().add(container);  // Ajouter le StackPane à la ListView
	        } else {
	            // Ajouter les notifications générées dans la liste
	            for (Node alert : alerts) {
	                StackPane alertContainer = new StackPane();
	                alertContainer.setStyle("-fx-padding: 5px; -fx-alignment: center-left; -fx-background-color: transparent; -fx-border-width: 0;"); // Fond transparent, pas de bordure
	                alertContainer.getChildren().add(alert);  // Ajouter le TextFlow ou autre Node

	                notifList.getItems().add(alertContainer);  // Ajouter le StackPane à la ListView
	            }
	        }
	    }
	}




	
	
/////////////



	private void afficherVehicules() {
	    try {
	        if (vehiculeControleur != null) {
	            tab.setItems(vehiculeControleur.getVehiculeList());
	        } 
	    } catch (SQLException e) {
	        e.printStackTrace(); 
	    } catch (Exception e) {
	        e.printStackTrace(); 
	    }
	}
	    ////////////////
	 private void initActionColumns() {
	        // Delete column setup
	        deleteC.setCellFactory(col -> new TableCell<Vehicule, Void>() {
	            private final Button deleteButton = new Button();

	            {
	                deleteButton.setStyle("-fx-background-color:#f2f2f2; -fx-text-fill: #5673a9;");
	                deleteButton.setPrefWidth(60);
	                deleteButton.setPrefHeight(26);
	                deleteButton.setAlignment(javafx.geometry.Pos.CENTER);
	                deleteButton.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);

	                ImageView imageView = new ImageView(new Image("file:///C:/Users/Hiba/OneDrive/Bureau/cpi2/java project/auto ecole/delete3.png"));
	                imageView.setFitHeight(24);
	                imageView.setFitWidth(23);
	                deleteButton.setGraphic(imageView);

	                Tooltip tooltip = new Tooltip("Delete this vehicle");
	                deleteButton.setTooltip(tooltip);
	                tooltip.setShowDelay(Duration.ZERO);

	                deleteButton.setOnAction(event -> {
	                    Vehicule item = getTableView().getItems().get(getIndex());
	                    String immatricule = item.getImmatricule();  // Use immatricule to delete
	                    // Show confirmation alert before deleting
	                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
	                    confirmationAlert.setTitle("Delete Vehicle");
	                    confirmationAlert.setHeaderText("Are you sure you want to delete this vehicle?");
	                    confirmationAlert.setContentText("This action cannot be undone.");

	                    // Show the dialog and capture the response
	                    ButtonType response = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

	                    if (response == ButtonType.OK) {
	                    // Call delete method in VehiculeControleur
	                    try {
	                        boolean success = vehiculeControleur.deleteVehiculeByImmatricule(immatricule);
	                        if (success) {
	                            // Remove from TableView after deletion
	                            tab.getItems().remove(item);
	                            showAlert("Success", "Vehicule deleted successfully!");
	                        } else {
	                            showAlert("Error", "Failed to delete vehicule.");
	                        
	                    } }catch (SQLException e) {
	                        e.printStackTrace();
	                        showAlert("Error", "Database error while deleting vehicule.");
	                    }}
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                setGraphic(empty ? null : deleteButton);
	            }
	        });


	        tab.getColumns().add(deleteC);
	    }

	    // Show alert method for displaying messages
	    private void showAlert(String title, String message) {
	        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	    
	    private String immatricule;
	    public void setImmatricule(String immatricule) {
	    	this.immatricule=immatricule;
	    }
	    public String getImmat() {
	    	return immatricule;
	    }


	

////
	    
		@FXML
		private void scCode() throws IOException {
			App.setRoot("SeanceCode"); 
		}
	
	@FXML
	private void affVehicule() throws IOException {
		App.setRoot("affichageVehicule"); 
	}
	
	@FXML
	private void updVehicule() throws IOException {
		App.setRoot("updateVehicule"); 
	}
	
	    
	@FXML
	private void addVehicle() throws IOException {
		App.setRoot("Vehicule"); 
	}
	
	@FXML
	private void addPapier() throws IOException {
		App.setRoot("Papier"); 
	}
	
	@FXML
	private void addRepartiton() throws IOException {
		App.setRoot("Repartition"); 
	}
	
	@FXML
	private void affdispo() throws IOException {
		App.setRoot("DisponibiliteV"); 
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
    
    



}
