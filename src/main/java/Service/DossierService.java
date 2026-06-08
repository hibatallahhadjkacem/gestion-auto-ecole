package Service;

import Dao.DossierDAO;
import Entities.Dossier;

public class DossierService {
	private final DossierDAO dossierDAO = new DossierDAO();
	
    public boolean addDossier(Dossier dossier) {
    	if(this.dossierDAO.save(dossier)) {
        return true;}
		return false;}
    
    
    
    public Dossier getDossierById(int num) {
    	Dossier dossier=this.dossierDAO.findDossierById(num);
		return dossier;}



    public static boolean deleteDossier(int num) {
    	if(DossierDAO.delete(num)) {
    		return true;
       
    } else {

    	return false;
        } 
    
    }



	public boolean updateDossier(Dossier dossier) {
		  if(dossierDAO.update(dossier)) {
	        	
	        	return true;
	        	
	        }else {
	        	return false;
	        }
	}
}
