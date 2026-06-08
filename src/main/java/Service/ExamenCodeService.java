package Service;


import java.util.List;

import Dao.ExamenCodeDAO;
import Entities.Condidat;
import Entities.ExamenCode;

public class ExamenCodeService {
	private final static ExamenCodeDAO examenCodeDAO = new ExamenCodeDAO();
	public boolean addExam(ExamenCode exam) {
    	if(this.examenCodeDAO.save(exam)) {;
        return true;}
		return false;}
	 public List<ExamenCode> getAllExams() {
		 
	        return examenCodeDAO.findAll();
	    }
	public boolean deleteExam(int num) {
	    	if(this.examenCodeDAO.delete(num)) {
	    		return true;
	      
	    } else {
	      
	    	return false;
	        } 
	    
	    }
	 public boolean updateExam(ExamenCode exam) {
	        if(examenCodeDAO.update(exam)) {
	        
	        	return true;
	        	
	        }else {
	        
	        	return false;
	        }
	    }
	public static boolean deleteAllExamCode() {
		examenCodeDAO.deleteAll();
    	return true;
	}
	}

