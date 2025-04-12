package Controleur;

import java.io.IOException;
import java.sql.SQLException;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Vehicule;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class test {
	/*
	 *  // Update column setup
	        updateC.setCellFactory(col -> new TableCell<Vehicule, Void>() {
	            private final Button updateButton = new Button();

	            {
	                updateButton.setStyle("-fx-background-color: #f2f2f2; -fx-text-fill: #5673a9;");
	                updateButton.setPrefWidth(60);
	                updateButton.setPrefHeight(26);
	                updateButton.setAlignment(javafx.geometry.Pos.CENTER);
	                updateButton.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);

	                ImageView imageView = new ImageView(new Image("file:///C:/Users/Hiba/OneDrive/Bureau/cpi2/java project/auto ecole/update2.png"));
	                imageView.setFitHeight(24);
	                imageView.setFitWidth(23);
	                updateButton.setGraphic(imageView);

	                Tooltip tooltip = new Tooltip("Update this vehicle");
	                updateButton.setTooltip(tooltip);
	                tooltip.setShowDelay(Duration.ZERO);

	                updateButton.setOnAction(event -> {
	                    Vehicule item = getTableView().getItems().get(getIndex());
	                    try {
	                        App.setRoot("Vehicule");
	                    } catch (IOException e) {
	                        e.printStackTrace();
	                    }
	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                setGraphic(empty ? null : updateButton);
	            }
	        });

	        // Get column setup
	        getC.setCellFactory(col -> new TableCell<Vehicule, Void>() {
	            private final Button getButton = new Button();

	            {
	                getButton.setStyle("-fx-background-color: #f2f2f2; -fx-text-fill: #5673a9;");
	                getButton.setPrefWidth(60);
	                getButton.setPrefHeight(26);
	                getButton.setAlignment(javafx.geometry.Pos.CENTER);
	                getButton.setContentDisplay(javafx.scene.control.ContentDisplay.TOP);

	                ImageView imageView = new ImageView(new Image("file:///C:/Users/Hiba/OneDrive/Bureau/cpi2/java project/auto ecole/affich2.png"));
	                imageView.setFitHeight(24);
	                imageView.setFitWidth(23);
	                getButton.setGraphic(imageView);

	                Tooltip tooltip = new Tooltip("View vehicle details");
	                getButton.setTooltip(tooltip);
	                tooltip.setShowDelay(Duration.ZERO);

	                getButton.setOnAction(event -> {
	                    Vehicule item = getTableView().getItems().get(getIndex());
	                    String immatricule = item.getImmatricule();

	                    try {
							vehiculeControleur.getVehiculesByImmat(immatricule);
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							
						}
	                    
	                    try {
							App.setRoot("affichageVehicule");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
	                    


	                });
	            }

	            @Override
	            protected void updateItem(Void item, boolean empty) {
	                super.updateItem(item, empty);
	                setGraphic(empty ? null : getButton);
	            }
	            	       	        });

	        // Add action columns to table
	        
	        tab.getColumns().add(updateC);
	        tab.getColumns().add(getC);
	        
	        
	        ///////////
	         * 	@FXML
	private RadioButton yes;
	@FXML
	private RadioButton no;
	@FXML
	private ToggleGroup Availabilit;
	
	    private boolean radioButRes;
    private boolean RadioB;
    
    //radio button
    public void getAvailability() {
        if (Availabilit.getSelectedToggle() == yes) {
            radioButRes = true;
            RadioB=true;
        } else if (Availabilit.getSelectedToggle() == no){
            radioButRes = false;
            RadioB=true;
        }else if(Availabilit.getSelectedToggle() ==null) {
        	RadioB=false;
        }
        System.out.println("Radio Button Selected: " + radioButRes);
    }
    
    Availabilit = new ToggleGroup();
        yes.setToggleGroup(Availabilit);
        no.setToggleGroup(Availabilit);
        
        // Add a listener to the toggle group
        Availabilit.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            getAvailability(); // Call method when selection changes
        });
*/

}
