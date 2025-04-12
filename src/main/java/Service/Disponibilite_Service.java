package Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import Dao.Disponibilite_Dao;
import Entities.Disponibilite;

public class Disponibilite_Service {
	 private Disponibilite_Dao disponibiliteDao;

	    public Disponibilite_Service() {
	        try {
				disponibiliteDao = new Disponibilite_Dao();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }

	   
	    public List<Disponibilite> getDisponibilitesByMoniteurId(int moniteurId) {
	        return Disponibilite_Dao.getDisponibilitesByMoniteurId(moniteurId);
	    }

	    
	    public List<Disponibilite> getDisponibilitesByMoniteurIdWeek(int moniteurId) {
	        return Disponibilite_Dao.getDisponibilitesByMoniteurId_week(moniteurId);
	    }

	    
	    public List<Disponibilite> getDisponibilitesToday(int moniteurId) {
	        return Disponibilite_Dao.getDisponibilitesToday(moniteurId);
	    }

	    // Ajouter une nouvelle disponibilité
	    public boolean addDisponibilite(Disponibilite dispo) {
	    	 return Disponibilite_Dao.addDisponibilite(dispo);
	    }

	    
	    public void updateDisponibilite(Disponibilite dispo) {
	        Disponibilite_Dao.updateDisponibilite(dispo);
	    }

	  
	    public boolean deleteDisponibilite(int dispoId) {
	        return Disponibilite_Dao.deleteDisponibilite(dispoId);
	    }

	   
	    public boolean isNotTimeFree(int moniteurId, LocalDate sessionDate, LocalTime startTime, LocalTime endTime) {
	        return Disponibilite_Dao.isNotTimeFree(moniteurId, sessionDate, startTime, endTime);
	    }

	    public LocalDate getDateRecrutement(int moniteurId){
	    
	       return Disponibilite_Dao.getDateRecrutement(moniteurId);
		
	    }

}
