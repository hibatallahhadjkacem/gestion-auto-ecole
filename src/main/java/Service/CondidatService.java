package Service;

import java.util.List;

import Dao.CondidatDAO;
import Entities.Condidat;

public class CondidatService {
	private final static CondidatDAO condidatDAO = new CondidatDAO();
	
    public boolean addCondidat(Condidat condidat) {
    	if(this.condidatDAO.save(condidat)) {;
        return true;}
		return false;}
    
    
    public List<Condidat> getAllCondidats() {
        return condidatDAO.findAll();
    }
    public static Condidat getCondidatById(int numero) {
    	Condidat condidat = condidatDAO.findCondidatById(numero);
    	if(condidat!=null) {
    	   
    		return condidat;
        } else {
            
        	return null;
        }
    	}
    
    public boolean deleteCondidat(int id) {
    	if(this.condidatDAO.delete(id)) {
    		return true;
      
    } else {
      
    	return false;
        } 
    
    }
    public Boolean deleteAllCondidat() {
    	this.condidatDAO.deleteAll();
    	return true;
    
    }
    
    
    public boolean updateCondidat(Condidat condidat) {
        if(condidatDAO.update(condidat)) {
        
        	return true;
        	
        }else {
        
        	return false;
        }
    }
    
 
}

