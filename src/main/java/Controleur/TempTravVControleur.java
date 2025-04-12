package Controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.DisponibiliteV;
import Entities.Vehicule;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.text.Text;
import javafx.util.Duration;



public class TempTravVControleur {
	
	@FXML
	private TableView<DisponibiliteV> tab;
	@FXML
	private TableColumn<DisponibiliteV, LocalDate> date;
	@FXML
	private TableColumn<DisponibiliteV, LocalTime> time;
	@FXML
	private TableColumn<DisponibiliteV, String> dispo;
	@FXML
	private TableColumn<DisponibiliteV, Void> update;
	
	@FXML
	private TextField immat;
	@FXML
	private TextField day;
	@FXML
	private TextField month;
	@FXML
	private TextField year;
	@FXML
	private Text er1;
	@FXML
	private Text er2;
	
	private DisponibiliteVControleur disponibiliteVControleur=new DisponibiliteVControleur();
	private ObservableList<DisponibiliteV> disponibiliteVList = FXCollections.observableArrayList();
	
	public void chargerDisponibiliteVDMY(String d,String m,String y,String immat) {
        if (disponibiliteVControleur != null) {
            List<DisponibiliteV> disponibiliteVs = disponibiliteVControleur.ViewdispVehic(d,m,y,immat);
            disponibiliteVList.clear();
            disponibiliteVList.addAll(disponibiliteVs);
            
            tab.setItems(disponibiliteVList); // Setting the updated data

            tab.refresh(); //  mettre à jour l'affichage
        } 
	}

	
	@FXML
	private void show() throws IOException {
		String im=immat.getText().trim();
		String d=day.getText().trim();
		String m=month.getText().trim();
		String y=year.getText().trim();
		
		if(im.isEmpty()) {
			afficherMessageTemporaire(er1, "You must fill in the registration number", 2);
		}else if(disponibiliteVControleur.rechImmat(im)==0) {
			afficherMessageTemporaire(er1, "This vehicle registration is not found", 2);

		}else if(disponibiliteVControleur.rechImmat(im)==-1) {
			afficherMessageTemporaire(er1, "An error in the database", 2);

		}else {
			if( !isValidDateFormat(d) ) {
				afficherMessageTemporaire(er2, "You must fill in the days correctly", 2);
			}else if ( !isValidMonthFormat(m)) {
				afficherMessageTemporaire(er2, "You must fill in the months correctly", 2);
			}else if ( !isValidYearFormat(y)) {
				afficherMessageTemporaire(er2, "You must fill in the years correctly", 2);
			}else {
				
				date.setCellValueFactory(cellData -> 
			    new javafx.beans.property.SimpleObjectProperty<>(
			        cellData.getValue().getDateTime().toLocalDate()
			    ));

				time.setCellValueFactory(cellData -> 
		        new javafx.beans.property.SimpleObjectProperty<>(
		            cellData.getValue().getDateTime().toLocalTime()
		        ));        
			    dispo.setCellValueFactory(new PropertyValueFactory<>("dispo"));
			    initActionColumns(im);
			    chargerDisponibiliteVDMY(d.isEmpty() ? null : d, m.isEmpty() ? null : m, y.isEmpty() ? null : y, im);
			}
			
		}
		
	}
	
		private void afficherMessageTemporaire(Text textElement, String message, int duree) {
		    textElement.setText(message);
		    Timeline timeline = new Timeline(
		        new KeyFrame(Duration.seconds(duree), e -> textElement.setText(""))
		    );
		    timeline.setCycleCount(1); // Exécuter une seule fois
		    timeline.play(); // Lancer le timer
		}
		
		public static boolean isValidMonthFormat(String str) {
			if(str.isEmpty()) {
				return true;
			}
		    
		    if (str.matches("\\d+")) {
		        int num = Integer.parseInt(str);
		        return num >= 1 && num <= 12;  
		    }
		    
		    
		    if (str.matches("\\d+-\\d+")) {
		        String[] parts = str.split("-");
		        try {
		            int start = Integer.parseInt(parts[0]);
		            int end = Integer.parseInt(parts[1]);
		            return start >= 1 && start <= 12 && end >= 1 && end <= 12 && start <= end;
		        } catch (NumberFormatException e) {
		            return false;  
		        }
		    }
		    
		    return false;  
		}
		public static boolean isValidYearFormat(String str) {
			if(str.isEmpty()) {
				return true;
			}
		    
		    if (str.matches("\\d+")) {
		        int num = Integer.parseInt(str);
		        return num >= 2025;  
		    }
		    
		    
		    if (str.matches("\\d+-\\d+")) {
		        String[] parts = str.split("-");
		        try {
		            int start = Integer.parseInt(parts[0]);
		            int end = Integer.parseInt(parts[1]);
		            return start >= 2025  && end >= 2025 && start <= end;
		        } catch (NumberFormatException e) {
		            return false;  
		        }
		    }
		    
		    return false;  
		}

		public static boolean isValidDateFormat(String str) {
			if(str.isEmpty()) {
				return true;
			}
		    // ex "12"
		    if (str.matches("\\d+")) {
		        int num = Integer.parseInt(str);
		        return num >= 1 && num <= 31;  // Vérifie que le nombre est entre 1 et 31
		    }
		    
		    // ex "16-20"
		    if (str.matches("\\d+-\\d+")) {
		        String[] parts = str.split("-");
		        try {
		            int start = Integer.parseInt(parts[0]);
		            int end = Integer.parseInt(parts[1]);
		            return start >= 1 && start <= 31 && end >= 1 && end <= 31 && start <= end;
		        } catch (NumberFormatException e) {
		            return false;  //la chaîne n'est pas valide
		        }
		    }
		    
		    return false;  // Si ce n'est pas un nombre 
		}
		
		   ////////////////
		private void initActionColumns(String im) {
		   
		        // Create the update button column
		        update.setCellFactory(col -> new TableCell<DisponibiliteV, Void>() {
		            private final Button updateButton = new Button();

		            {
		                updateButton.setStyle("-fx-background-color:#f2f2f2; -fx-text-fill: #5673a9;");
		                updateButton.setPrefWidth(103);
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
		                    DisponibiliteV item = getTableView().getItems().get(getIndex());

		                    // Get date and time from the current row (DisponibiliteV object)
		                    LocalDate date = item.getDateTime().toLocalDate();
		                    LocalTime time = item.getDateTime().toLocalTime();

		                    Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
		                    confirmationAlert.setTitle("Update Vehicle");
		                    confirmationAlert.setHeaderText("Are you sure you want to update this vehicle?");
		                    ButtonType response = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

		                    if (response == ButtonType.OK) {
		                        boolean success = disponibiliteVControleur.updateDispoByImmatricule(im, date, time); // Pass date and time
		                        if (success) {
		                        	 // Mettre à jour l'objet 
		                            item.setDateTime(LocalDateTime.of(date, time));  

		                            // Mettre à jour la ligne dans la TableView 
		                            getTableView().getItems().set(getIndex(), item); 
		                            tab.refresh();
		                            showAlert("Success", "Vehicle updated successfully!");
		                        } else {
		                            showAlert("Error", "Failed to update vehicle.");
		                        }
		                    }
		                });
		            }

		            @Override
		            protected void updateItem(Void item, boolean empty) {
		                super.updateItem(item, empty);
		                setGraphic(empty ? null : updateButton);  // Only show the button when there is data
		            }
		        });

		        // Ne pas ajouter la colonne si elle est déjà présente
		        if (!tab.getColumns().contains(update)) {
		            tab.getColumns().add(update);
		        }
		    }
		



		    // Show alert method for displaying messages
		    private void showAlert(String title, String message) {
		        Alert alert = new Alert(AlertType.INFORMATION);
		        alert.setTitle(title);
		        alert.setHeaderText(null);
		        alert.setContentText(message);
		        alert.showAndWait();
		    }
		
			@FXML
			private void scCode() throws IOException {
				App.setRoot("SeanceCode"); 
			}
	
	//Morning operating hours for the vehicles
	@FXML
	private void back() throws IOException {
		App.setRoot("DisponibiliteV");
	}
	@FXML
	private void home() throws IOException {
		App.setRoot("Home"); 
	}
	
	@FXML
	private void scConduit() throws IOException {
		App.setRoot("SeanceConduite"); 
	}
	
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
    
    
    

}
