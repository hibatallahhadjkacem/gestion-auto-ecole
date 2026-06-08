package Service;
import java.util.List;

import Dao.ExamenCodeDAO;
import Dao.ExamenConduiteDAO;
import Entities.ExamenCode;
import Entities.ExamenConduite;

public class ExamenConduiteService {
	private final static ExamenConduiteDAO examenConduiteDAO = new ExamenConduiteDAO();
	public static boolean addExam(ExamenConduite examenConduite) {
		if(examenConduiteDAO.save(examenConduite)) {;
        return true;}
		return false;}
	 public List<ExamenConduite> getAllExams() {
		 
	        return examenConduiteDAO.findAll();
	    }
	public boolean deleteExam(int num) {
		if(this.examenConduiteDAO.delete(num)) {
    		return true;
      
    } else {
      
    	return false;
        } 
	}
	 public static boolean updateExam(ExamenConduite exam) {
	        if(examenConduiteDAO.update(exam)) {
	        
	        	return true;
	        	
	        }else {
	        
	        	return false;
	        }
	    }
	 public static boolean deleteAllExamConduite() {
		 examenConduiteDAO.deleteAll();
	    	return true;
		}

}
