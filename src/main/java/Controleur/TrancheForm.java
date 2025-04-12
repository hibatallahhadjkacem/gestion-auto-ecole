package Controleur;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Insets;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

import Entities.Tranche;

public class TrancheForm {

    private int numero;
    private DatePicker dateEcheancePicker = new DatePicker();
    private TextField montantField = new TextField();
    private ComboBox<String> statutCombo = new ComboBox<>();
    private DatePicker datePaiementEffectifPicker = new DatePicker();

    private VBox container;

    public TrancheForm(int numero) {
        this.numero = numero;
        this.container = new VBox(5);
        this.container.setPadding(new Insets(10));
        this.container.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5px; -fx-border-width: 1px;");

        Label title = new Label("Tranche " + numero);
        title.setStyle("-fx-font-weight: bold;");

        montantField.setPromptText("Montant");
        statutCombo.getItems().addAll("en attente", "payée");
        statutCombo.setValue("en attente");

        dateEcheancePicker.setPromptText("Date d’échéance");
        datePaiementEffectifPicker.setPromptText("Date de paiement (si payée)");

        container.getChildren().addAll(
                title,
                new Label("Date d’échéance :"), dateEcheancePicker,
                new Label("Montant :"), montantField,
                new Label("Statut :"), statutCombo,
                new Label("Date de paiement effectif :"), datePaiementEffectifPicker
        );
    }
    
    public String getStatut() {
        return statutCombo.getValue();
    }

    public LocalDate getDateEcheance() {
        return dateEcheancePicker.getValue();
    }

    public LocalDate getDatePaiementEffectif() {
        return datePaiementEffectifPicker.getValue();
    }

    public String getMontantText() {
        return montantField.getText();
    }


    public VBox getContainer() {
        return container;
    }

    public Tranche toTranche() {
        Tranche t = new Tranche();
        t.setNumeroTranche(numero);
        t.setDateLimite(Date.valueOf(dateEcheancePicker.getValue()).toLocalDate());
        t.setMontant(new BigDecimal(montantField.getText()));
        t.setStatut(statutCombo.getValue());

        if (datePaiementEffectifPicker.getValue() != null) {
            t.setDatePaiementEffectif(Date.valueOf(datePaiementEffectifPicker.getValue()).toLocalDate());
        } else {
            t.setDatePaiementEffectif(null);
        }

        return t;
    }
}

