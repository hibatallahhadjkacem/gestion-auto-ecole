package Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import Dao.SeanceCodeDao;
import Entities.SeanceCode;
import Entities.SeanceConduite;

public class SeanceCodeSevice {
	private SeanceCodeDao seanceCodeDao=new SeanceCodeDao();
	public int rechNumSc(int num) {
		return seanceCodeDao.rechNumSc(num);
	}
	
	public boolean insert(SeanceCode seance) {
		return seanceCodeDao.insert(seance);
	}
	
	public List<SeanceCode> getScCodeByNum(int num){
		return seanceCodeDao.getScCodeByNum(num);
	}
	public boolean updateDate(LocalDate date,int num) {
    	return seanceCodeDao.updateDate(date, num);
    }
    
    public boolean updateTp(LocalTime tp,int num) {
    	return seanceCodeDao.updateTp(tp, num);
    }
    
    public boolean updateMonit(int idMonit,int num) {
    	return seanceCodeDao.updateMonit(idMonit, num);
    }
    
    public boolean updateCondidat(int idCondidat, int num) {
    	return seanceCodeDao.updateCondidat(idCondidat, num);
    }
    
    
    public List<SeanceCode> getAllScCode() throws SQLException{
    	return seanceCodeDao.getAllScCode();
    }
    
    public List<SeanceCode> getScCodeBytpDate(LocalTime time,LocalDate dt) throws SQLException {
    	return seanceCodeDao.getScCodeBytpDate(time, dt);
    }
    
    public List<SeanceCode> getScCodeBytp(LocalTime time) throws SQLException {
    	return seanceCodeDao.getScCodeBytp(time);
    }
    
    public List<SeanceCode> getScCodeByDate(LocalDate dt) throws SQLException {
    	return seanceCodeDao.getScCodeByDate(dt);
}}
