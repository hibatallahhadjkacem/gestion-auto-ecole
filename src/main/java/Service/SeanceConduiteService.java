package Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import Dao.SeanceConduiteDAO;
import Entities.SeanceConduite;

public class SeanceConduiteService {
	
	    private SeanceConduiteDAO seanceConduiteDAO = new SeanceConduiteDAO();

	    
	    public int rechMoniteur(int idMonit) {
	    	return seanceConduiteDAO.rechMoniteur(idMonit);
	    }
	    
	    public int rechCondidat(int idCondid) {
	    	return seanceConduiteDAO.rechCondidat(idCondid);
	    } 
	    
	    public int disponibMoniteur(int idMonit,LocalDate date,LocalTime time) {
	    	return seanceConduiteDAO.disponibMoniteur(idMonit, date, time);
	    }
	    
	    public int disponibVehicule(String immatV, LocalDate date, LocalTime time) {
	    	return seanceConduiteDAO.disponibVehicule(immatV, date, time);
	    }
	    
	    public boolean ajouterSeance(SeanceConduite seance) {
	    	return seanceConduiteDAO.insert(seance);
	    }
	    
	    public int rechNumSc(int num) {
	    	return seanceConduiteDAO.rechNumSc(num);
	    }
	    
	    public List<SeanceConduite> getScParNum(int num){
	    	return seanceConduiteDAO.getScParNum(num);
	    }
	    
	    public String getCondidat(int idcondid) {
	    	return seanceConduiteDAO.getCondidat(idcondid);
	    }
	    
	    public String getMoniteur(int idMonit) {
	    	return seanceConduiteDAO.getMoniteur(idMonit);
	    }
	    public String getvehic(String immat) {
	    	return seanceConduiteDAO.getvehic(immat);}
	    
	    public boolean updateDate(LocalDate date,int num) {
	    	return seanceConduiteDAO.updateDate(date, num);
	    }
	    
	    public boolean updateTp(LocalTime tp,int num) {
	    	return seanceConduiteDAO.updateTp(tp, num);
	    }
	    
	    public boolean updateMonit(int idMonit,int num) {
	    	return seanceConduiteDAO.updateMonit(idMonit, num);
	    }
	    
	    public boolean updateCondidat(int idCondidat, int num) {
	    	return seanceConduiteDAO.updateCondidat(idCondidat, num);
	    }
	    
	    public boolean updateVehicule(String idVehicul,int num) {
	    	return seanceConduiteDAO.updateVehicule(idVehicul, num);
	    }
	    
	    public boolean updateMap(String adresse,double lang,double lalt,int num) {
	    	return seanceConduiteDAO.updateMap(adresse, lang, lalt, num);
	    }
	    
	    public List<SeanceConduite> getAllScConduit() throws SQLException{
	    	return seanceConduiteDAO.getAllScConduit();
	    }
	    
	    public List<SeanceConduite> getScConduitBytpDate(LocalTime time,LocalDate dt) throws SQLException {
	    	return seanceConduiteDAO.getScConduitBytpDate(time, dt);
	    }
	    
	    public List<SeanceConduite> getScConduitBytp(LocalTime time) throws SQLException {
	    	return seanceConduiteDAO.getScConduitBytp(time);
	    }
	    
	    public List<SeanceConduite> getScConduitByDate(LocalDate dt) throws SQLException {
	    	return seanceConduiteDAO.getScConduitByDate(dt);
	    }
	    
}
