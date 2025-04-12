package Service;



import Dao.Tranche_Dao;
import Entities.Tranche;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import Dao.Tranche_Dao;

public class Tranche_Service {

	 private Tranche_Dao trancheDao;

	    public Tranche_Service() {
	        try {
	            trancheDao = new Tranche_Dao();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

    
	    
	   /* public void ajouterTranches(int paiementId, List<Tranche> tranches) throws SQLException {
	        trancheDao.ajouterTranches(paiementId, tranches);
	    }*/
	    
	    
	    
	    
	    
	    
    public static List<Tranche> getTranchesByPaiement(int paiementId) {
        return Tranche_Dao.getTranchesByPaiement(paiementId);
    }
    
    public void  updateTranche3(Tranche t) {
    	
    	Tranche_Dao.updateTranche3(t);
    }
    
    

    public void supprimerTranchesPourPaiement(int paiementId) {
        trancheDao.deleteTranchesByPaiementId(paiementId);
    }

    
    public boolean verifierTotalTranches(List<Tranche> tranches, BigDecimal montantTotal) {
        BigDecimal total = tranches.stream()
                .map(Tranche::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return total.compareTo(montantTotal) == 0;
    }



    public boolean ajouterTranches(int paiementId, List<Tranche> tranches, BigDecimal montantTotal) {
        // Vérifier dans le DAO si l'ajout des tranches est possible
        boolean ajoutRéussi = false;
        try {
            ajoutRéussi = trancheDao.ajouterTranches(paiementId, tranches, montantTotal);
        } catch (SQLException e) {
            // Gérer l'exception de manière appropriée, par exemple, en loggant l'erreur
            System.err.println("Erreur lors de l'ajout des tranches : " + e.getMessage());
            e.printStackTrace();
            // Tu pourrais également choisir de relancer l'exception ou retourner false directement ici.
            return false;  // Retourner false en cas d'erreur
        }

        // Si l'ajout a échoué (par exemple si la somme des tranches ne correspond pas au montant total), retourner false
        if (!ajoutRéussi) {
            return false;
        }

        return true;  // Retourner true si l'ajout des tranches est réussi
    }

		
    public void addTranche(Tranche tranche) {
        trancheDao.add(tranche);
    }



	public void updateTranche(Tranche t) {
		trancheDao.updateTranche(t);
		
	}
	
	

    

		
			
    
    
}

