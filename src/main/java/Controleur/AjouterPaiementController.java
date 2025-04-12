package Controleur;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import Service.Paiement_Service;
import Entities.Paiement;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AjouterPaiementController {

    @FXML
    private TextField idCandidatField;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField montantTotalField;
    @FXML
    private ComboBox<String> modePaiementCombo;

    private Paiement paiement;
    private Paiement_Service paiementService = new Paiement_Service();

    @FXML
    public void initialize() {
        modePaiementCombo.getItems().addAll("Comptant", "facilite:2tranches", "facilite:3tranches");
    }

   
    @FXML
    private void handleSave() {
        // Vérification des champs vides
        if (idCandidatField.getText().isEmpty() ||
            datePicker.getValue() == null ||
            montantTotalField.getText().isEmpty() ||
            modePaiementCombo.getValue() == null) {
            
            showAlert(Alert.AlertType.ERROR, "Missing fields", "Please fill in all the fields.");
            return;
        }

        int idCandidat;
        try {
            idCandidat = Integer.parseInt(idCandidatField.getText());
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid format", "The candidate ID must be an integer.");
            return;
        }

        if (paiementService.isCandidatExists(idCandidat)==false) {
            showAlert(Alert.AlertType.ERROR, "Candidate not found", "The candidate ID does not exist.");
            return;
        }
        
        
        
        BigDecimal montant;
        try {
            montant = new BigDecimal(montantTotalField.getText());
         // Vérifier que le montant est positif
            if (montant.compareTo(BigDecimal.ZERO) <= 0) {
                showAlert(Alert.AlertType.ERROR, "Invalid amount", "The amount must be a positive number.");
                return;
            }
            
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid format", "The amount must be a valid number.");
            return;
        }

        LocalDate datePaiement = datePicker.getValue();
        String mode = modePaiementCombo.getValue();
        
     // Vérifier si la date de paiement est valide
        if (datePaiement == null) {
            showAlert(Alert.AlertType.ERROR, "Invalid date", "Please select a valid payment date.");
            return;
        }

        // Vérifier si le mode de paiement est sélectionné
        if (mode == null || mode.trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Invalid payment mode", "Please select a payment mode.");
            return;
        }
        
        

        paiement = new Paiement(idCandidat, datePaiement, montant, mode);
        

        // Fermer la fenêtre
        ((Stage) idCandidatField.getScene().getWindow()).close();
    }

    

    private void showAlert(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Paiement getPaiement() {
        return paiement;
    }
}
