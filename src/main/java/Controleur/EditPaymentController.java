package Controleur;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import Service.Paiement_Service;
import Entities.Paiement;
import Entities.Tranche;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EditPaymentController {

    @FXML
    private TextField montantTotalField;
    @FXML
    private DatePicker datePaiementPicker;
    @FXML
    private ComboBox<String> modePaiementComboBox;
    @FXML
    private VBox trancheContainer;
    @FXML
    private Label errorLabel;

    private Paiement paiementSelected;
    private List<Tranche> tranches = new ArrayList<>();
    private Paiement_Service paiementService=new Paiement_Service();

    public void initData(Paiement paiement, List<Tranche> tranchesList) {
        this.paiementSelected = paiement;
        this.tranches = tranchesList;

        datePaiementPicker.setValue(paiement.getDatePaiement());
        montantTotalField.setText(paiement.getMontantTotal().toString());
        modePaiementComboBox.getItems().addAll("Comptant", "facilite:2tranches", "facilite:3tranches");
        modePaiementComboBox.setValue(paiement.getModePaiement());

        modePaiementComboBox.setOnAction(e -> updateTrancheFields());

        if (paiement.getModePaiement() != null && paiement.getModePaiement().startsWith("facilite")) {
            trancheContainer.setVisible(true);
            updateTrancheFields();
        } else {
            trancheContainer.setVisible(false);
        }
    }

   /* private void updateTrancheFields() {
        trancheContainer.getChildren().clear();
        String mode = modePaiementComboBox.getValue();
        if (mode == null || !mode.startsWith("facilite")) return;

        int nbTranches = mode.contains("3") ? 3 : 2;
        for (int i = 1; i <= nbTranches; i++) {
            Tranche tranche = tranches.stream()
            		int finalI = i;
                   .filter(t -> finalI == t.getNumeroTranche())

                //.filter(t -> t.getNumeroTranche() == i)
            	 .findFirst()
                .orElse(new Tranche(i)); // constructeur avec numéro tranche si tu l’as
        
            HBox hbox = new HBox(10);
            DatePicker dateLimite = new DatePicker(tranche.getDateLimite());
            TextField montant = new TextField(tranche.getMontant() != null ? tranche.getMontant().toString() : "");
            TextField statut = new TextField(tranche.getStatut() != null ? tranche.getStatut() : "");
            hbox.getChildren().addAll(new Label("Tranche " + i), dateLimite, montant, statut);
            hbox.setUserData(tranche);

            trancheContainer.getChildren().add(hbox);
        }
    }*/
    
    
    
    
    private void updateTrancheFields() {
        trancheContainer.getChildren().clear();
        String mode = modePaiementComboBox.getValue();
        if (mode == null || !mode.startsWith("facilite")) return;

        int nbTranches = mode.contains("3") ? 3 : 2;
        for (int i = 1; i <= nbTranches; i++) {
            int finalI = i;
            Tranche tranche = tranches.stream()
                .filter(t -> Integer.valueOf(finalI).equals(t.getNumeroTranche()))
                .findFirst()
                .orElseGet(() -> {
                    Tranche t = new Tranche();
                    t.setNumeroTranche(finalI);
                    return t;
                });

            HBox hbox = new HBox(10);
            		
            TextField montant = new TextField(tranche.getMontant() != null ? tranche.getMontant().toString() : "");
            TextField statut = new TextField(tranche.getStatut());
            DatePicker dateLimite = new DatePicker(tranche.getDateLimite());
            DatePicker datePayment= new DatePicker(tranche.getDatePaiementEffectif());
            

            hbox.getChildren().addAll(new Label("Tranche " + i),  montant, statut,dateLimite,datePayment);
            hbox.setUserData(tranche);
            trancheContainer.getChildren().add(hbox);
        }
    }

    
    
    
    
    
    
    
    

    @FXML
    private void handleSave() {
       // errorLabel.setText(""); // Clear previous errors

        try {
            BigDecimal total = new BigDecimal(montantTotalField.getText());

            // Vérifie la somme des tranches si mode facilité
            if (modePaiementComboBox.getValue() != null && modePaiementComboBox.getValue().startsWith("facilite")) {
                BigDecimal somme = BigDecimal.ZERO;
                for (Node node : trancheContainer.getChildren()) {
                    HBox hbox = (HBox) node;
                    TextField montantField = (TextField) hbox.getChildren().get(2);
                    somme = somme.add(new BigDecimal(montantField.getText()));
                }

                if (somme.compareTo(total) != 0) {
                    errorLabel.setText("La somme des tranches doit être égale au montant total.");
                    return;
                }
            }

            paiementSelected.setDatePaiement(datePaiementPicker.getValue());
            paiementSelected.setMontantTotal(total);
            paiementSelected.setModePaiement(modePaiementComboBox.getValue());

            // Mise à jour des tranches
            List<Tranche> updatedTranches = new ArrayList<>();
            for (Node node : trancheContainer.getChildren()) {
                HBox hbox = (HBox) node;
                Tranche t = (Tranche) hbox.getUserData();
                DatePicker dateLimite = (DatePicker) hbox.getChildren().get(1);
                TextField montantField = (TextField) hbox.getChildren().get(2);
                TextField statutField = (TextField) hbox.getChildren().get(3);

                t.setDateLimite(dateLimite.getValue());
                t.setMontant(new BigDecimal(montantField.getText()));
                t.setStatut(statutField.getText());
                updatedTranches.add(t);
            }

            paiementService.updatePaiement1(paiementSelected, updatedTranches);
           

            // Fermer la fenêtre
            Stage stage = (Stage) montantTotalField.getScene().getWindow();
            stage.close();
            

        } catch (NumberFormatException ex) {
            errorLabel.setText("Montant invalide : " + ex.getMessage());
        } catch (Exception e) {
            errorLabel.setText("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }




}








