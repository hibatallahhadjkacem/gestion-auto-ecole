package Service;

import java.sql.SQLException;
import java.util.List;

import Dao.Moniteur_Dao;
import Entities.Moniteur;

public class Moniteur_Service {
	
	  

	    // Ajouter un moniteur avec validation
	    public  int addMoniteur(Moniteur moniteur) throws SQLException {
	        if (moniteur.getNom() == null || moniteur.getNom().trim().isEmpty()) {
	            throw new IllegalArgumentException("❌ Le nom ne peut pas être vide !");
	        }
	        if (moniteur.getPrenom() == null || moniteur.getPrenom().trim().isEmpty()) {
	            throw new IllegalArgumentException("❌ Le prénom ne peut pas être vide !");
	        }

	        return Moniteur_Dao.save(moniteur);
	    }

	    // Récupérer la liste des moniteurs
	    public List<Moniteur> getAllMoniteurs() {
	        return Moniteur_Dao.findAll();
	    }
	    public List<Moniteur> getAllMoniteurs_butsome_S() {
	        return Moniteur_Dao.findAll_butsome_D();
	    }
	    
	    public  boolean deleteMoniteur_C(int id) {
			return Moniteur_Dao.delete( id);
	    	
	    	
	    }
	    
	    public boolean updateMoniteur(Moniteur moniteur) {
	        return Moniteur_Dao.updateMoniteur(moniteur);  // Appeler la méthode updateMoniteur du DAO
	    }
}


