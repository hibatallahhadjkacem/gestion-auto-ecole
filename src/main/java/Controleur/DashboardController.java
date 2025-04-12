package Controleur;

import java.net.URL;
import java.util.ResourceBundle;

import Service.Dashboard_Service;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;



public class DashboardController implements Initializable {
	
	
	
	

    @FXML
    private Label lblTotalCandidats;
    @FXML
    private Label lblTotalMoniteurs;
    @FXML
    private Label lblTotalVehicules;
    @FXML
    private Label lblTotalPaiements;
    @FXML
    private ProgressBar paiementProgressBar;
    @FXML
    private ProgressIndicator paiementProgressIndicator;
    @FXML
    private VBox upcomingSeancesBox;

    private Dashboard_Service service = new Dashboard_Service();

    
    
    
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }


    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            lblTotalCandidats.setText(String.valueOf(service.getTotalCandidats()));
            lblTotalMoniteurs.setText(String.valueOf(service.getTotalMoniteurs()));
            lblTotalVehicules.setText(String.valueOf(service.getTotalVehicules()));
            lblTotalPaiements.setText(service.getTotalPaiements() + " DT");

            double progress = service.getPourcentagePaiementEffectue();
            paiementProgressBar.setProgress(progress);
            paiementProgressIndicator.setProgress(progress);

           /* List<String> seances = service.getNextSeances();
            for (String s : seances) {
                Label label = new Label("📅 " + s);
                label.setStyle("-fx-font-size: 14px; -fx-text-fill: #2c3e50;");
                upcomingSeancesBox.getChildren().add(label);
            }
            
           <VBox alignment="CENTER_LEFT" spacing="10">
            <Label style="-fx-font-weight: bold;" text="🗓️ Prochaines séances" />
            <VBox fx:id="upcomingSeancesBox" spacing="5" />
        </VBox>
          
            */

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

