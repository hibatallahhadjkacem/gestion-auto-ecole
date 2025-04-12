package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Entities.SeanceCode;
import Entities.SeanceConduite;

public class SeanceCodeDao {
	private Connection conn = connection.getInstance();
	
	public List<SeanceCode> getAllScCode() throws SQLException {
		List<SeanceCode> codes = new ArrayList<>();
	    String sql = "SELECT * FROM seancecode"; 

	    try (PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            int nums = rs.getInt("num");
	            Date date = rs.getDate("date");
	            Time tp = rs.getTime("time");
	            int monit = rs.getInt("idMoniteur");
	            int condidat = rs.getInt("idCondidat");

                SeanceCode code = new SeanceCode(nums, date.toLocalDate(), tp.toLocalTime(), monit, condidat);

                codes.add(code);
            }
        }
    

    return codes;
	}

	public List<SeanceCode> getScCodeBytp(LocalTime time) throws SQLException {
		List<SeanceCode> codes = new ArrayList<>();
	    String sql = "SELECT * FROM seancecode WHERE time = ?"; 

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setTime(1, java.sql.Time.valueOf(time));
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                int nums = rs.getInt("num");
	                Date date = rs.getDate("date");
	                Time tp = rs.getTime("time");
	                int monit = rs.getInt("idMoniteur");
	                int condidat = rs.getInt("idCondidat");

	                SeanceCode code = new SeanceCode(nums, date.toLocalDate(), tp.toLocalTime(), monit, condidat);

	                codes.add(code);
	            }
	        }
	    }

	    return codes;
	}

	public List<SeanceCode> getScCodeBytpDate(LocalTime time, LocalDate dt) throws SQLException {
		List<SeanceCode> codes = new ArrayList<>();
		String sql = "SELECT * FROM seancecode WHERE time = ? AND date = ?"; 

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setTime(1, java.sql.Time.valueOf(time));
	        stmt.setDate(2, java.sql.Date.valueOf(dt));
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                int nums = rs.getInt("num");
	                Date date = rs.getDate("date");
	                Time tp = rs.getTime("time");
	                int monit = rs.getInt("idMoniteur");
	                int condidat = rs.getInt("idCondidat");


	                SeanceCode code = new SeanceCode(nums, date.toLocalDate(), tp.toLocalTime(), monit, condidat);

	                codes.add(code);
	            }
	        }
	    }

	    return codes;
	}

	public List<SeanceCode> getScCodeByDate(LocalDate dt) throws SQLException {
	    List<SeanceCode> codes = new ArrayList<>();
	    String sql = "SELECT * FROM seancecode WHERE date = ?"; 

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setDate(1, java.sql.Date.valueOf(dt));
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                int nums = rs.getInt("num");
	                Date date = rs.getDate("date");
	                Time tp = rs.getTime("time");
	                int monit = rs.getInt("idMoniteur");
	                int condidat = rs.getInt("idCondidat");

	                SeanceCode code = new SeanceCode(nums, date.toLocalDate(), tp.toLocalTime(), monit, condidat);

	                codes.add(code);
	            }
	        }
	    }

	    return codes;
	}
	
	public boolean updateDate(LocalDate date,int num) {
		String sql = "UPDATE seancecode SET date= ? WHERE num= ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	stmt.setDate(1, java.sql.Date.valueOf(date));
        	stmt.setInt(2, num);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateTp(LocalTime tp,int num) {
		String sql = "UPDATE seancecode SET time= ? WHERE num= ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setTime(1, java.sql.Time.valueOf(tp));
            stmt.setInt(2, num);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateMonit(int idMonit,int num) {
		String sql = "UPDATE seancecode SET idMoniteur= ? WHERE num= ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMonit);
            stmt.setInt(2, num);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateCondidat(int idCondidat,int num) {
		String sql = "UPDATE seancecode SET idCondidat= ? WHERE num= ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCondidat);
            stmt.setInt(2, num);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}

	public int rechNumSc(int num) {
	    String sql = "SELECT 1 FROM seancecode WHERE num = ? LIMIT 1"; 
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, num);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next() ? 1 : 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; 
	    }
	}
	
	public boolean insert(SeanceCode seance) {
    	String sql = "INSERT INTO seancecode VALUES (?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, seance.getNum());
            stmt.setDate(2, java.sql.Date.valueOf(seance.getDate()));
            stmt.setTime(3,java.sql.Time.valueOf( seance.getTemp()));
            stmt.setInt(4, seance.getIdMoniteur());
            stmt.setInt(5, seance.getIdcondidat());
            stmt.executeUpdate();       
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
	
	public List<SeanceCode> getScCodeByNum(int num){
		List<SeanceCode> codes = new ArrayList<>();
        String sql = "SELECT * FROM seancecode WHERE num = ?"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, num); 
            ResultSet rs =stmt.executeQuery();
            while (rs.next()) {
                int nums = rs.getInt("num");
                Date date = rs.getDate("date");
                Time tp = rs.getTime("time");
                int monit = rs.getInt("idMoniteur");
                int condidat = rs.getInt("idCondidat");

                SeanceCode seanceCode =new SeanceCode(nums,date.toLocalDate(), tp.toLocalTime(), monit, condidat);
                codes.add(seanceCode);
                
            }
        } catch (SQLException e) {
			e.printStackTrace();
		}

        return codes;
	}
	

}
