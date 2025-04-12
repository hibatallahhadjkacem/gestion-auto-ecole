package Controleur;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Entities.Tranche;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AjouterTranchesController {
	
	
	private BigDecimal montantTotal;  
    /*private Stage stage;
    
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
*/
	
	public void setMontantTotal(BigDecimal montantTotal) {
	    this.montantTotal = montantTotal;}

    @FXML
    private VBox trancheContainer;

    private List<TrancheForm> trancheForms = new ArrayList<>();

    public void initTranches(String mode) {
        int nbTranches = 0;

        if ("facilite:2tranches".equalsIgnoreCase(mode)) nbTranches = 2;
        else if ("facilite:3tranches".equalsIgnoreCase(mode)) nbTranches = 3;
        
        
        
        
        
        for (int i = 1; i <= nbTranches; i++) {
            TrancheForm form = new TrancheForm(i);
            trancheForms.add(form);
            trancheContainer.getChildren().add(form.getContainer());
        }
        
       
       
        
    }

    public List<Tranche> getTranches() {
        return trancheForms.stream().map(TrancheForm::toTranche).collect(Collectors.toList());
    }

    
   /* private void handleSave() {
    	
    	for (int i = 0; i < trancheForms.size(); i++) {
            TrancheForm form = trancheForms.get(i);

            if (form.getDateEcheance() == null) {
                showAlert("Tranche " + (i + 1) + ": Veuillez sélectionner une date d'échéance.");
                return;
            }

           
            try {
                double montant = Double.parseDouble(form.getMontantText());
                if (montant <= 0) {
                    showAlert("Tranche " + (i + 1) + " : Veuillez saisir un montant supérieur à 0.");
                    return;
                }
            } catch (NumberFormatException e) {
                showAlert("Tranche " + (i + 1) + " : Le montant n'est pas un nombre valide.");
                return;
            }

            
            

            if (form.getStatut() == null || form.getStatut().isEmpty()) {
                showAlert("Tranche " + (i + 1) + ": Veuillez sélectionner un statut.");
                return;
            }

            if (form.getStatut().equals("payé") && form.getDatePaiementEffectif() == null) {
                showAlert("Tranche " + (i + 1) + ": Veuillez saisir la date de paiement pour une tranche payée.");
                return;
            }
        }
    	
    	

        // Vérifier si la somme des montants des tranches est égale au montant total
    	if (totalTranches != montantTotal.doubleValue()) {
            showAlert("Error", "The sum of installments must be equal to the total amount.");
            return;
        }
    	
    	
    	
        // Fermer la fenêtre
        Stage stage = (Stage) trancheContainer.getScene().getWindow();
        stage.close();
    }*/
    
    
    @FXML
    private void handleSave() {
        double totalTranches = 0;

        for (int i = 0; i < trancheForms.size(); i++) {
            TrancheForm form = trancheForms.get(i);

            if (form.getDateEcheance() == null) {
                showAlert("Installment " + (i + 1) + ": Please select a due date.");
                return;
            }

            try {
                double montant = Double.parseDouble(form.getMontantText());
                if (montant <= 0) {
                    showAlert("Installment " + (i + 1) + ": Please enter an amount greater than 0.");
                    return;
                }
                totalTranches += montant; // Add installment amount to the total
            } catch (NumberFormatException e) {
                showAlert("Installment " + (i + 1) + ": The amount is not a valid number.");
                return;
            }

            if (form.getStatut() == null || form.getStatut().isEmpty()) {
                showAlert("Installment " + (i + 1) + ": Please select a status.");
                return;
            }

            if (form.getStatut().equals("payée") && form.getDatePaiementEffectif() == null) {
                showAlert("Installment " + (i + 1) + ": Please provide the payment date for a paid installment.");
                return;
            }
        }

        // Validate if the sum of installments is equal to the total amount
        if (totalTranches != montantTotal.doubleValue()) {
            showAlert("Error:The total amount for the permit is "+montantTotal  
	               + " while the sum "+ "of the installments you added is "+totalTranches);
            return;
        }
        
        Stage stage = (Stage) trancheContainer.getScene().getWindow();
        stage.close();
    
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Missing fields");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
}

