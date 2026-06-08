package Controleur;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Condidat;
import Entities.Dossier;
import Service.CondidatService;
import Service.DossierService;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class AfficheController {
	@FXML
	private TextField num;
    @FXML
    private TableView<Condidat> table;
    @FXML
    private TableColumn<Condidat, String> numeroColumn;
    @FXML
    private TableColumn<Condidat, String> lastname;
    @FXML
    private TableColumn<Condidat, String> firstname;
    @FXML
    private TableColumn<Condidat, String> bdate;
    @FXML
    private TableColumn<Condidat, String> addressColumn;
    @FXML
    private TableColumn<Condidat, String> telColumn;
    @FXML
    private TableColumn<Condidat, String> emailColumn;
    @FXML
    private TableColumn<Condidat, String> permi;
    @FXML
    private TableColumn<Condidat, Void> actionsColumn;
    @FXML
    private ImageView cin;
    @FXML
    private ImageView photo;
    @FXML
    private ImageView certif;
    @FXML	
    private ImageView updateButton;
    @FXML	
    private ImageView deleteButton;
    private DossierService dossierService =new DossierService();
    private CondidatService condidatService = new CondidatService();
	@FXML 
	
	private void getCondidat() {
		String numCondidat = num.getText();
		if (numCondidat.isEmpty() || !numCondidat.matches("\\d+") || Integer.parseInt(numCondidat) < 0) {
			showAlert("Error", "Candidate number ivalid!", AlertType.ERROR);
	        return;
	    	}
		loadCondidat(Integer.parseInt(numCondidat));
		loadDossier(Integer.parseInt(numCondidat));
	}
	
	public void initialize() {
		 Tooltip tooltip1 = new Tooltip("Delete File ");
    	 Tooltip tooltip2 = new Tooltip("Update File");

    	    deleteButton.setOnMouseEntered(e -> {
    	        tooltip1.show(deleteButton, e.getScreenX(), e.getScreenY() + 10);
    	    });

    	    deleteButton.setOnMouseExited(e -> tooltip1.hide());

    	    updateButton.setOnMouseEntered(e -> tooltip2.show(updateButton, e.getScreenX(), e.getScreenY() + 10));
    	    updateButton.setOnMouseExited(e -> {
    	        PauseTransition delay = new PauseTransition(Duration.seconds(0.1)); // Small delay to ensure smooth hiding
    	        delay.setOnFinished(event -> tooltip2.hide());
    	        delay.play();
    	    });
        lastname.setCellValueFactory(new PropertyValueFactory<>("nom"));
        firstname.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        bdate.setCellValueFactory(new PropertyValueFactory<>("dateNais"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        permi.setCellValueFactory(new PropertyValueFactory<>("typePermi"));
    
        setupActionsColumn();}
	private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<>() {

            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/pics/poubelle.png")));
            private final Button deleteButton = new Button();
            private final Tooltip tooltip = new Tooltip("Delete condidate");

            {
                deleteButton.setTooltip(tooltip);
                tooltip.setShowDelay(Duration.ZERO);
                deleteIcon.setFitWidth(20);
                deleteIcon.setFitHeight(20);

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


    private void loadCondidat(int condidatId) {
        Condidat condidat = condidatService.getCondidatById(condidatId); 
        if (condidat != null) {
            ObservableList<Condidat> condidatData = FXCollections.observableArrayList(condidat);
            table.setItems(condidatData);
            
        } else {
            showAlert("Error", "No Candidate found with ID: "+condidatId, AlertType.ERROR);
        }
    }
    @FXML
    private void loadDossier(int condidatId) {
        
         
            Dossier dossier = dossierService.getDossierById(condidatId);  
            
            if (dossier != null) {
                try {
      
                    ByteArrayInputStream cinInputStream = new ByteArrayInputStream(dossier.getCin());
                    Image cinImage = new Image(cinInputStream);
                    cin.setImage(cinImage);

                    
                    ByteArrayInputStream photoInputStream = new ByteArrayInputStream(dossier.getPhoto());
                    Image photoImage = new Image(photoInputStream);
                    photo.setImage(photoImage);

                    
                    ByteArrayInputStream certifInputStream = new ByteArrayInputStream(dossier.getCertif());
                    Image certifImage = new Image(certifInputStream);
                    certif.setImage(certifImage);
                } catch (Exception e) {
                    e.printStackTrace();
                }}else {
                	showAlert("Error", "No File for the Candidate !", AlertType.ERROR);
                }
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
                showAlert("Success", "Candidate deleted successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to delete Candidate.", Alert.AlertType.ERROR);
            }
        	
              
        }
    }
    @FXML
    private void deleteDossier() {
    	String numCondidat = num.getText();
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete  ?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (DossierService.deleteDossier(Integer.parseInt(numCondidat))){
                   
                    showAlert("Success", "File deleted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete File.", Alert.AlertType.ERROR);
                }
            }
        });
    }
    @FXML
    private void updateDossier() throws IOException {
    	String numCondidat = num.getText();
		if (numCondidat.isEmpty() || !numCondidat.matches("\\d+") || Integer.parseInt(numCondidat) < 0) {
			showAlert("Error", "Enter candidate number !", AlertType.ERROR);
	        return;
	    	}
    	 UpdateDossierController.setNumCondidat(Integer.parseInt(numCondidat));
    	  
    	App.setRoot("updateDossier");
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
	@FXML
    private void back() throws IOException {

        App.setRoot("page1");
    }
    @FXML
    private void Exam() throws IOException {
    	App.setRoot("examen");
    }
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
    
}
