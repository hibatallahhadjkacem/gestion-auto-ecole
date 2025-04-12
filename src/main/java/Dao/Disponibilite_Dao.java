package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Entities.Disponibilite;

import Entities.Moniteur;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;




public class Disponibilite_Dao {
	

		
	    private static Connection connexion;

	    private final String DB_URL = "jdbc:mysql://localhost:3306/autoecole";
	    private final String USER = "root";
	    private final String PASS = "";

	    public Disponibilite_Dao() throws SQLException{

	        try{
	               Class.forName("com.mysql.cj.jdbc.Driver");
	        } catch (ClassNotFoundException e) {

	            e.printStackTrace();
	        }
	        connexion= DriverManager.getConnection(DB_URL, USER, PASS);
	    }

	    public static Connection getInstance(){
	        if (connexion == null)
	            try {
	                new Disponibilite_Dao();
	            }catch(Exception e){
	                System.out.println("--"+e.getMessage());
	            }
	        return connexion;
	    }
	    
	    
	    
	    
	    
	    private static Connection conn = getInstance();
		
		
	    
	   
	    
	    public static  List<Disponibilite> getDisponibilitesByMoniteurId(int moniteurId) {
	        List<Disponibilite> disponibilites = new ArrayList<>();
	        String sql = "SELECT * FROM disponibilite WHERE moniteur_id = ? ORDER BY session_date, start_time";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, moniteurId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Disponibilite dispo = new Disponibilite();
	                    dispo.setId(rs.getInt("id"));
	                    dispo.setMoniteurId(rs.getInt("moniteur_id"));
	                    dispo.setSessionDate(rs.getDate("session_date").toLocalDate());
	                    dispo.setStartTime(rs.getTime("start_time").toLocalTime());
	                    dispo.setEndTime(rs.getTime("end_time").toLocalTime());
	       
	                    dispo.setAvailability(rs.getBoolean("availability"));
	                    disponibilites.add(dispo);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return disponibilites;
	    }
	    
	    
	    
	    
	    public static  List<Disponibilite> getDisponibilitesByMoniteurId_week(int moniteurId) {
	        List<Disponibilite> disponibilites = new ArrayList<>();
	        String sql = "SELECT * FROM disponibilite WHERE moniteur_id = ? AND  session_date BETWEEN CURDATE() - INTERVAL 7 DAY AND CURDATE() ORDER BY session_date, start_time";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, moniteurId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    Disponibilite dispo = new Disponibilite();
	                    dispo.setId(rs.getInt("id"));
	                    dispo.setMoniteurId(rs.getInt("moniteur_id"));
	                    dispo.setSessionDate(rs.getDate("session_date").toLocalDate());
	                    dispo.setStartTime(rs.getTime("start_time").toLocalTime());
	                    dispo.setEndTime(rs.getTime("end_time").toLocalTime());
	       
	                    dispo.setAvailability(rs.getBoolean("availability"));
	                    disponibilites.add(dispo);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return disponibilites;
	    }
	    
	    
	    public static List<Disponibilite> getDisponibilitesToday(int moniteurId) {
	        List<Disponibilite> disponibilitesToday = new ArrayList<>();
	        String sql = "SELECT * FROM disponibilite WHERE moniteur_id = ? AND session_date = CURDATE() ORDER BY  start_time";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, moniteurId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                Disponibilite dispo = new Disponibilite();
	                dispo.setId(rs.getInt("id"));
	                dispo.setMoniteurId(rs.getInt("moniteur_id"));
	                dispo.setSessionDate(rs.getDate("session_date").toLocalDate());
	                dispo.setStartTime(rs.getTime("start_time").toLocalTime());
	                dispo.setEndTime(rs.getTime("end_time").toLocalTime());
	                dispo.setAvailability(rs.getBoolean("availability"));

	                disponibilitesToday.add(dispo);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return disponibilitesToday;
	    }

	    
	    
	    

	    
	    


	

	    
	    public static boolean deleteDisponibilite(int dispoId) {
	        String sql = "DELETE FROM disponibilite WHERE id = ?";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, dispoId);

	            int rowsAffected = stmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }

	    
	    public static LocalDate getDateRecrutement(int moniteurId) {
	        String sql = "SELECT date_recrutement FROM moniteur WHERE id = ?";
	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, moniteurId);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getDate("date_recrutement").toLocalDate();
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }

	    
	  
	    
	    
	    
	    
	    public static boolean isNotTimeFree(int moniteurId, LocalDate sessionDate, LocalTime startTime, LocalTime endTime) {

	        String sql = "SELECT * FROM disponibilite WHERE moniteur_id = ? AND session_date = ? " +
	                     "AND ((start_time < ? AND end_time > ?) OR (start_time < ? AND end_time > ?))";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, moniteurId);
	            stmt.setDate(2, java.sql.Date.valueOf(sessionDate));
	            stmt.setTime(3, java.sql.Time.valueOf(endTime));  // Fin de la nouvelle séance
	            stmt.setTime(4, java.sql.Time.valueOf(startTime)); // Début de la nouvelle séance
	            stmt.setTime(5, java.sql.Time.valueOf(endTime));  // Fin de la nouvelle séance
	            stmt.setTime(6, java.sql.Time.valueOf(startTime)); // Début de la nouvelle séance

	            try (ResultSet rs = stmt.executeQuery()) {
	                return rs.next();  // Si rs.next() retourne true, cela signifie qu'il y a au moins une ligne, donc le créneau est occupé
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return false;
	    }

	    
	    
	    
	    
	    
	    
	    
	    public static boolean addDisponibilite(Disponibilite dispo) {
	     

	        String sql = "INSERT INTO disponibilite  (moniteur_id, session_date, start_time, end_time, availability) VALUES (?, ?, ?, ?, ?)";

	        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	            stmt.setInt(1, dispo.getMoniteurId());
	            stmt.setDate(2, java.sql.Date.valueOf(dispo.getSessionDate()));
	            stmt.setTime(3, java.sql.Time.valueOf(dispo.getStartTime()));
	            stmt.setTime(4, java.sql.Time.valueOf(dispo.getEndTime()));
	           // stmt.setBoolean(5, dispo.getAvailability());
               
	            if (dispo.getAvailability() != null) {
	                stmt.setBoolean(5, dispo.getAvailability());
	            } else {
	                stmt.setNull(5, java.sql.Types.BOOLEAN);
	            }
	            
	            int rowsAffected = stmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }


	    

	    
	    public static void updateDisponibilite(Disponibilite disponibilite) {
	        String sql = "UPDATE disponibilite SET availability = ? WHERE id = ?";
	        try (PreparedStatement ps = connexion.prepareStatement(sql)) {
	            ps.setBoolean(1, disponibilite.getAvailability());
	            ps.setInt(2, disponibilite.getId());
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
 
	   



	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
}
