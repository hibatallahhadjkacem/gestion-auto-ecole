package Controleur;

import java.io.IOException;
import java.util.Optional;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Condidat;
import Service.CondidatService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class Page1Controller {
    @FXML
    private TableView<Condidat> table;
    @FXML
    private TableColumn<Condidat, String> numeroColumn;
    @FXML
    private TableColumn<Condidat, String> lastname;
    @FXML
    private TableColumn<Condidat, String> firstname;
    @FXML
    private TableColumn<Condidat, String> permi;
    @FXML
    private TableColumn<Condidat, Void> actionsColumn;
    @FXML
    private ImageView afficheButton;
    @FXML
    private ImageView updateButton;
   

    private CondidatService condidatService = new CondidatService();

    public void initialize() {
    	 Tooltip tooltip1 = new Tooltip("View condidate details");
    	 Tooltip tooltip2 = new Tooltip("Update condidate");

    	    afficheButton.setOnMouseEntered(e -> {
    	        tooltip1.show(afficheButton, e.getScreenX(), e.getScreenY() + 10);
    	    });

    	    afficheButton.setOnMouseExited(e -> tooltip1.hide());

    	    updateButton.setOnMouseEntered(e -> {
    	        tooltip2.show(updateButton, e.getScreenX(), e.getScreenY() + 10);
    	    });

    	    updateButton.setOnMouseExited(e -> tooltip2.hide());
    	    
    	    
    	    
    	numeroColumn.setCellValueFactory(new PropertyValueFactory<>("numero"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("nom"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        permi.setCellValueFactory(new PropertyValueFactory<>("typePermi"));
        
        loadCondidats();
        setupActionsColumn();
    }
    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {

            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/pics/poubelle.png")));
            private final Button deleteButton = new Button();
            private final Tooltip tooltip = new Tooltip("Delete condidate");

            {
                // Tooltip configuration
                deleteButton.setTooltip(tooltip);
                tooltip.setShowDelay(Duration.ZERO);

                // Configure delete icon
                deleteIcon.setFitWidth(20);
                deleteIcon.setFitHeight(20);

                // Set button properties
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 5;");

                // Set action event for delete button
                deleteButton.setOnAction(event -> {
                    Condidat condidat = getTableView().getItems().get(getIndex());
                    handleDelete(condidat);
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
    }



    private void loadCondidats() {
        ObservableList<Condidat> condidats = FXCollections.observableArrayList(condidatService.getAllCondidats());
        System.out.println("Loaded Candidates: " + condidats.size());
        table.setItems(condidats);
    }
    private void handleDelete(Condidat condidat) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + condidat.getNom() + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (condidatService.deleteCondidat(condidat.getNumero())) {
                    table.getItems().remove(condidat);
                    showAlert("Success", "Candidate deleted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete Candidate.", Alert.AlertType.ERROR);
                }
            }
        });
    }
    @FXML

    private void DeleteAll() {
        
        ObservableList<Condidat> items = table.getItems();
        
   
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete All");
        alert.setHeaderText("Are you sure you want to delete all candidates?");
        alert.setContentText("This action cannot be undone.");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
        	if (condidatService.deleteAllCondidat()) {
        		items.clear();
                showAlert("Success", "Candidates deleted successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to delete candidates.", Alert.AlertType.ERROR);
            }
        	
              
        }
    }
  
    @FXML
    private void affiche() throws IOException {
    	App.setRoot("affiche");}
    
    @FXML
    private void update() throws IOException {
    	App.setRoot("update");}
        
   
    @FXML
    private void addCondidat() throws IOException {
        App.setRoot("condidat");
    }

    @FXML
    private void addDossier() throws IOException {
        
        App.setRoot("dossier");
    }
    @FXML
    private void Exam() throws IOException {
    	App.setRoot("examen");
    }
    @FXML
    private void home() throws IOException {
    	App.setRoot("Home");
    }
    @FXML
    private void Condidat() throws IOException {
    	App.setRoot("page1");
    }
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
   

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
}
