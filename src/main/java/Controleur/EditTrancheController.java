package Controleur;

import java.math.BigDecimal;

import Service.Tranche_Service;
import Entities.Tranche;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditTrancheController {
	
	@FXML
    private Button btn;


    @FXML
    private ComboBox<String> tfStatut;
    @FXML
    private DatePicker dpDateLimite;
    @FXML
    private DatePicker dpDatePaiementEffectif;

    private Tranche tranche;

    public void initialize() {
   	 tfStatut.getItems().addAll("en attente", "payée");
   }
    
    public void setTranche(Tranche tranche) {
        this.tranche = tranche;
        //tfNumero.setText(String.valueOf(tranche.getNumeroTranche()));
        //tfMontant.setText(tranche.getMontant().toString());
        tfStatut.setValue(tranche.getStatut());
        dpDateLimite.setValue(tranche.getDateLimite());
        dpDatePaiementEffectif.setValue(tranche.getDatePaiementEffectif());
    }

    @FXML
    private void handleSave() {
        // Met à jour l'objet Tranche
       // tranche.setNumeroTranche(Integer.parseInt(tfNumero.getText()));
       // tranche.setMontant(new BigDecimal(tfMontant.getText()));
        tranche.setStatut(tfStatut.getValue());
        tranche.setDateLimite(dpDateLimite.getValue());
        tranche.setDatePaiementEffectif(dpDatePaiementEffectif.getValue());

        // Appel à DAO ou Service pour mettre à jour en base
        Tranche_Service service = new Tranche_Service();
        service.updateTranche3(tranche);

        // Fermer la fenêtre
        
       
    }
}

