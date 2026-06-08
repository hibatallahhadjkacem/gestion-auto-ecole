package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import Entities.Categorie;
import Entities.Condidat;
import Entities.ExamenCode;
import Entities.Moniteur;
import Entities.Res;
import Entities.Type;
import Entities.Vehicule;

public class ExamenCodeDAO {
	private static Connection conn =connection.getInstance();
	public static boolean save(ExamenCode exam) {
		  int examCodeNumero = 0;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    
		    try {
		    	String sql = "INSERT INTO examenCode (numero, date, moniteur_id, condidat_numero, resultat, frais, starttime, endtime) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		    	stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		    	// Setting the parameters for the prepared statement
		    	stmt.setInt(1, exam.getNumero());  // numero
		    	stmt.setDate(2, java.sql.Date.valueOf(exam.getDate()));  // date
		    	stmt.setInt(3, exam.getMoniteur_id());  // moniteur_id
		    	stmt.setInt(4, exam.getCondidat_num());  // condidat_numero
		    	stmt.setString(5, exam.getResultat().name());  // resultat (converted to String)
		    	stmt.setDouble(6, exam.getFrais());  // frais
		    	stmt.setTime(7, java.sql.Time.valueOf(exam.getStartTime()));  // start_time
		    	stmt.setTime(8, java.sql.Time.valueOf(exam.getEndTime()));  // end_time
		        stmt.executeUpdate();
		        rs = stmt.getGeneratedKeys();
		        if (rs.next()) {
		        	examCodeNumero = rs.getInt(1);
		        }
		      } catch (SQLException e) {
			        System.out.println("Error while saving Code Exam: " + e.getMessage());
			        e.printStackTrace();
			        return false;
			    } finally {
			        try {
			            if (rs != null) rs.close();
			            if (stmt != null) stmt.close();
			        } catch (SQLException e) {
			            System.out.println("Error closing resources: " + e.getMessage());
			        }
			    }
		 
		return true;}
	
	public static List<ExamenCode> findAll(){
	    Statement stmt = null;
	    ResultSet rs = null;
	    List<ExamenCode> exam = new ArrayList<>();
	    String SQL = "SELECT * FROM examenCode";

	    try {
	        stmt = conn.createStatement();
	        rs = stmt.executeQuery(SQL);

	        while (rs.next()) {
	            int id = rs.getInt(1);
	            LocalDate date = rs.getDate(2).toLocalDate();
	            int moniteur = rs.getInt(3);
	            int condidat = rs.getInt(4);
	            String resultat = rs.getString(5);
	            double frais = rs.getDouble(6);
	            LocalTime startTime = rs.getTime(7).toLocalTime();
	            LocalTime endTime = rs.getTime(8).toLocalTime();

	            Res res = null;

	            if (resultat != null) {
	                try {
	                    res = Res.valueOf(resultat.trim()); 
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid result value in database: " + resultat);
	                }
	            }

	            ExamenCode examenCode = new ExamenCode(id, date, moniteur, condidat, startTime, endTime, res, frais);
	            exam.add(examenCode);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return exam;
	}
	public static boolean delete(int numero) {
	    String sql = "DELETE FROM examenCode WHERE numero = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, numero);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error while deleting Code Exam: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
	public static boolean update(ExamenCode exam) {
	    String sql = "UPDATE ExamenCode SET date = ?, moniteur_id = ?, condidat_numero = ?, resultat = ?, frais = ?, startTime = ?, endTime = ? WHERE numero = ?";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setDate(1, java.sql.Date.valueOf(exam.getDate()));  // date
	        stmt.setInt(2, exam.getMoniteur_id());  // moniteur_id
	        stmt.setInt(3, exam.getCondidat_num());  // condidat_numero
	        stmt.setString(4, exam.getResultat().name());  // resultat (converted to String)
	        stmt.setDouble(5, exam.getFrais());  // frais
	        stmt.setTime(6, java.sql.Time.valueOf(exam.getStartTime()));  // start_time
	        stmt.setTime(7, java.sql.Time.valueOf(exam.getEndTime()));  // end_time
	        stmt.setInt(8, exam.getNumero());  // numero (for WHERE clause)
	        
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error while updating Code Exam: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

	
	public static boolean isMoniteurAvailable(int moniteurId, LocalDate sessionDate, LocalTime startTime, LocalTime endTime) throws SQLException {
        String existsQuery = "SELECT * FROM disponibilite WHERE moniteur_id = ? AND session_date = ? AND availability = 1";

        try (PreparedStatement existsStmt = conn.prepareStatement(existsQuery)) {
            existsStmt.setInt(1, moniteurId);
            existsStmt.setDate(2, java.sql.Date.valueOf(sessionDate));
            ResultSet existsRs = existsStmt.executeQuery();

            if (!existsRs.next()) {
                System.out.println("No availability found for the moniteur on this date.");
                return false;
            }
        }

        String checkQuery = "SELECT * FROM disponibilite " +
                "WHERE moniteur_id = ? AND session_date = ? AND availability = 1 " +
                "AND NOT (" +
                "(start_time <= ? AND end_time >= ?) OR " +
                "(start_time >= ? AND end_time <= ?))";

        try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
            checkStmt.setInt(1, moniteurId);
            checkStmt.setDate(2, java.sql.Date.valueOf(sessionDate));
            checkStmt.setTime(3, java.sql.Time.valueOf(startTime));
            checkStmt.setTime(4, java.sql.Time.valueOf(endTime));
            checkStmt.setTime(5, java.sql.Time.valueOf(startTime));
            checkStmt.setTime(6, java.sql.Time.valueOf(endTime));

            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("❌ Overlap detected for the new session.");
                return false;
            }

          

            System.out.println("✅ Moniteur is available for the requested session.");
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

	public static Moniteur findMoniteurById(int id) {
		   


	    String SQL = "SELECT * FROM moniteur where id =?";

	    try (PreparedStatement stmt = conn.prepareStatement(SQL)) {
	        stmt.setInt(1, id); 
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            int idm = rs.getInt("id"); // Récupération par nom de colonne
	            String prenom = rs.getString("first_name");
	            String nom = rs.getString("last_name");
	            String sexe = rs.getString("sexe");
	            String email = rs.getString("email");
	            String phone = rs.getString("phone");
	            LocalDate dateRecrutement = rs.getDate("date_recrutement").toLocalDate(); // Convertir SQL Date en LocalDate
	           


	            // Création d'un objet Moniteur
	            return  new Moniteur(idm, nom, prenom, sexe, email, phone, dateRecrutement);
	            
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while fetching instructor: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;
	}
	public static boolean isVehiculeAvailable(String immatV, LocalDate sessionDate, LocalTime startTime, LocalTime endTime) throws SQLException {
	    LocalDateTime startDateTime = LocalDateTime.of(sessionDate, startTime);
	    System.out.println("🔍 Requête pour : " + immatV + " à " + startDateTime);

	    // Requête pour vérifier la disponibilité du véhicule
	    String query = "SELECT * FROM disponibilitev " +
	                   "WHERE immatV = ? AND dispo = 1 AND dateTime = ?";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(query)) {
	        stmt.setString(1, immatV);
	        stmt.setTimestamp(2, Timestamp.valueOf(startDateTime));

	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            System.out.println("✅ Véhicule DISPONIBLE à " + startDateTime);
	            return true;
	        } else {
	            System.out.println("❌ Véhicule INDISPONIBLE à " + startDateTime);
	            return false;
	        }
	    }
	}
	public static void updateMoniteurAvailability(int moniteurId, LocalDate sessionDate, LocalTime newStartTime) throws SQLException {
	    String updateStartTimeQuery = "UPDATE disponibilite SET start_time = ? WHERE moniteur_id = ? AND session_date = ?";
	    try (PreparedStatement updateStartStmt = conn.prepareStatement(updateStartTimeQuery)) {
	        updateStartStmt.setTime(1, java.sql.Time.valueOf(newStartTime));
	        updateStartStmt.setInt(2, moniteurId);
	        updateStartStmt.setDate(3, java.sql.Date.valueOf(sessionDate));
	        int updatedStart = updateStartStmt.executeUpdate();

	        if (updatedStart > 0) {
	            System.out.println("🔄 Updated start_time to endTime.");
	        }
	    }

	    String checkEqualQuery = "SELECT start_time, end_time FROM disponibilite WHERE moniteur_id = ? AND session_date = ?";
	    try (PreparedStatement checkEqualStmt = conn.prepareStatement(checkEqualQuery)) {
	        checkEqualStmt.setInt(1, moniteurId);
	        checkEqualStmt.setDate(2, java.sql.Date.valueOf(sessionDate));
	        ResultSet rsCheck = checkEqualStmt.executeQuery();

	        if (rsCheck.next()) {
	            LocalTime updatedStart = rsCheck.getTime("start_time").toLocalTime();
	            LocalTime updatedEnd = rsCheck.getTime("end_time").toLocalTime();

	            if (updatedStart.equals(updatedEnd)) {
	                String updateAvailabilityQuery = "UPDATE disponibilite SET availability = 0 WHERE moniteur_id = ? AND session_date = ?";
	                try (PreparedStatement updateAvailStmt = conn.prepareStatement(updateAvailabilityQuery)) {
	                    updateAvailStmt.setInt(1, moniteurId);
	                    updateAvailStmt.setDate(2, java.sql.Date.valueOf(sessionDate));
	                    int updatedAvailability = updateAvailStmt.executeUpdate();

	                    if (updatedAvailability > 0) {
	                        System.out.println("🚫 Updated availability to 0 (fully booked).");
	                    }
	                }
	            }
	        }
	    }
	}
	public boolean deleteAll() {
	    String sql = "DELETE FROM examenCode";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	     
	        e.printStackTrace();
	        return false;
	    }
	}



}
