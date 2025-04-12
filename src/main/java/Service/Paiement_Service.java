package Service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import Dao.Disponibilite_Dao;
import Dao.Paiement_DAO;
import Dao.Tranche_Dao;
import Entities.Paiement;
import Entities.Tranche;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;




public class Paiement_Service {
	private Paiement_DAO paiementDao;
    private Tranche_Service trancheService;
    private Tranche_Dao trancheDAO;

    public Paiement_Service() {
        try {
            paiementDao = new Paiement_DAO();
            trancheService = new Tranche_Service();
            trancheDAO =new Tranche_Dao();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
   
    
    
    
    
	 public  List<Paiement> afficher_paiement() {
	        return Paiement_DAO.findAll();
	    }


	 public static ObservableList<Paiement> getAllPaiements() {
	        // Récupérer tous les paiements depuis la base de données
	        List<Paiement> paiementList = Paiement_DAO.findAll();  // Vous pouvez avoir un DAO qui récupère les paiements
	        return FXCollections.observableArrayList(paiementList);
     
	 
	 }
	 
	 
	 
	 
	 
	 
	 
	 public int ajouterPaiement(Paiement p) throws SQLException {
	        return paiementDao.ajouterPaiement(p); // Retourne l'ID du paiement
	    }
	 
	 
	 
	 
     
     public void supprimerPaiement(int idPaiement, String modePaiement) {
         if (modePaiement.toLowerCase().contains("facilite")) {
             trancheService.supprimerTranchesPourPaiement(idPaiement);
         }
         paiementDao.deletePaiementById(idPaiement);
     }

	
     public boolean isCandidatExists(int idCandidat) {
    return paiementDao.candidatExists(idCandidat);
    }
				
       
     public Paiement getPaiementById(int id) {
         return paiementDao.getPaiementById(id);
     }

     public void updatePaiement(Paiement paiement) {
         paiementDao.updatePaiement(paiement);
     }

		        
		        
		        
     
     public List<Tranche> getTranchesByPaiementId(int idPaiement) {
         return trancheDAO.getTranchesByPaiement(idPaiement);
     }

     public void updateTranches(List<Tranche> tranches) {
         for (Tranche t : tranches) {
             trancheDAO.updateTranche2(t);
         }
     }

     public boolean verifierTotalTranches(List<Tranche> tranches, BigDecimal montantTotal) {
         BigDecimal total = tranches.stream()
                 .map(Tranche::getMontant)
                 .reduce(BigDecimal.ZERO, BigDecimal::add);
         return total.compareTo(montantTotal) == 0;
     }	  
     
     
     //pay
     public void updatePaiement1(Paiement paiement, List<Tranche> updatedTranches) {
    	   
    	    // 1. Mettre à jour le paiement dans la base
    	    paiementDao.updatePaiement(paiement);

    	    // 2. Mettre à jour les tranches une par une
    	    for (Tranche t : updatedTranches) {
    	        if (t.getId() == 0) {
    	            // nouvelle tranche
    	            t.setIdPaiement(paiement.getId()); // associer l'id du paiement
    	            trancheService.addTranche(t);;
    	        } else {
    	            // mise à jour d'une tranche existante
    	            trancheService.updateTranche(t);
    	        }
    	    }
    	}

		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
		        
     
     
     
		

	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}


