package Dao;

import java.sql.Connection;
import java.sql.Date;
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
import java.util.List;

import Entities.Categorie;
import Entities.ExamenCode;
import Entities.ExamenConduite;
import Entities.Moniteur;
import Entities.Res;
import Entities.Vehicule;

public class ExamenConduiteDAO {
	private static Connection conn =connection.getInstance();
	public static boolean save(ExamenConduite exam) {
		  int examConduiteNumero = 0;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    
		    try {
		    	  String sql = "INSERT INTO examenConduite (numero, date, moniteur_id, condidat_numero, vehicule_immat, resultat, frais , latitude, longitude,address, starttime, endtime) " +
	                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		    	stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		    	// Setting the parameters for the prepared statement
		    	stmt.setInt(1, exam.getNumero());  // numero
		    	stmt.setDate(2, java.sql.Date.valueOf(exam.getDate())); // date
		    	stmt.setInt(3, exam.getMoniteur());  // moniteur_id
		    	stmt.setInt(4, exam.getCondidat());  // condidat_numero
		    	stmt.setString(5, exam.getVehicule());// vehicule_numero
		    	stmt.setString(6, exam.getResultat().name()) ; // resultat (converted to String)
		    	stmt.setDouble(7, exam.getFrais());  // frais
		    	stmt.setDouble(8, exam.getLatitude());  
		        stmt.setDouble(9, exam.getLongitude());
		        stmt.setString(10, exam.getAdress());
		    	stmt.setTime(11, java.sql.Time.valueOf(exam.getStartTime()));  // start_time
		    	stmt.setTime(12, java.sql.Time.valueOf(exam.getEndTime()));  // end_time
		    	
		        
		        stmt.executeUpdate();
		        rs = stmt.getGeneratedKeys();
		        if (rs.next()) {
		        	examConduiteNumero = rs.getInt(1);
		        }
		      } catch (SQLException e) {
			        System.out.println("Error while saving driving Exam: " + e.getMessage());
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
	public static List<ExamenConduite> findAll(){
		Statement stmt=null;
		ResultSet rs=null;
		List<ExamenConduite> exam = new ArrayList<ExamenConduite>();
		String SQL="SELECT * FROM examenConduite";
		
		try {

			stmt=conn.createStatement();

			rs=stmt.executeQuery(SQL);

			while (rs.next()) {

			int id =rs.getInt(1);
			
			 LocalDate date = rs.getDate(2).toLocalDate();
	         int moniteur = rs.getInt(3);
	         int condidat = rs.getInt(4);
	         String resultat = rs.getString(6);
	         String vehicule = rs.getString(5);
	         double frais =rs.getDouble(7);
	         double lat =rs.getDouble(8);
	         double lng =rs.getDouble(9);
	         String adress = rs.getString(10);
	         LocalTime startTime=rs.getTime(11).toLocalTime();
	         LocalTime endTime=rs.getTime(12).toLocalTime();
	         Res res = null;

	            if (resultat != null) {
	                try {
	                    res = Res.valueOf(resultat.trim()); 
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid result value in database: " + resultat);
	                }
	            }
	           ExamenConduite examenConduite = new ExamenConduite(id, date,startTime,endTime,moniteur, condidat, vehicule, res,lat,lng,adress,frais);

			exam.add(examenConduite);

			}


			}catch (SQLException e) {

			e.printStackTrace();

			}
		
		
		return exam;}

	public static boolean delete(int numero) {
	    String sql = "DELETE FROM examenConduite WHERE numero = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, numero);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error while deleting Driving Exam: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
	public static boolean update(ExamenConduite exam) {
	    String sql = "UPDATE ExamenConduite SET date = ?, startTime = ?, endTime = ?, resultat = ?, frais = ?, vehicule = ?, adress = ? WHERE numero = ?";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setDate(2, java.sql.Date.valueOf(exam.getDate())); // date
	    	stmt.setInt(3, exam.getMoniteur());  // moniteur_id
	    	stmt.setInt(4, exam.getCondidat());  // condidat_numero
	    	stmt.setString(5, exam.getVehicule());// vehicule_numero
	    	stmt.setString(6, exam.getResultat().name()) ; // resultat (converted to String)
	    	stmt.setDouble(7, exam.getFrais());  // frais
	    	stmt.setDouble(8, exam.getLatitude());  
	        stmt.setDouble(9, exam.getLongitude());
	        stmt.setString(10, exam.getAdress());
	    	stmt.setTime(11, java.sql.Time.valueOf(exam.getStartTime()));  // start_time
	    	stmt.setTime(12, java.sql.Time.valueOf(exam.getEndTime()));  // end_time
	    	
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error while updating Code Exam: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}

	public Vehicule getVehiculesByImmat(String immat) throws SQLException {
       
        String sql = "SELECT * FROM vehicule WHERE immat = ?"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, immat); 
            ResultSet rs =stmt.executeQuery();
            while (rs.next()) {
                String modele = rs.getString(1);
                LocalDate date = rs.getDate(2).toLocalDate();
                int kmtot = rs.getInt(2);
                int kmproche = rs.getInt(3);
                String catg = rs.getString(4);
                int age = rs.getInt(6);
                Categorie categorie=Categorie.valueOf(catg);

                Vehicule vehicule = new Vehicule(immat, modele, date, kmtot, kmproche, categorie, age);
               
                
            }
        }catch (SQLException e) {
	        System.out.println("Error while fetching Vehicule: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null;

       
    }public static boolean isMoniteurAvailable(int moniteurId, LocalDate sessionDate, LocalTime startTime, LocalTime endTime) throws SQLException {
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


	public static void updateVehiculeAvailability(String immatV, LocalDateTime startDateTime) throws SQLException {
	    // Mise à jour de la disponibilité du véhicule
	    String updateQuery = "UPDATE disponibilitev SET dispo = 0 WHERE immatV = ? AND dateTime = ?";

	    try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
	        updateStmt.setString(1, immatV);
	        updateStmt.setTimestamp(2, Timestamp.valueOf(startDateTime));
	        
	        // Exécution de la mise à jour
	        int rowsUpdated = updateStmt.executeUpdate();
	        
	        // Si la mise à jour a réussi
	        if (rowsUpdated > 0) {
	            System.out.println("🚗 Véhicule réservé avec succès ! Disponibilité mise à jour.");
	        }
	    }
	}
	public boolean deleteAll() {
	    String sql = "DELETE FROM examenConduite";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	     
	        e.printStackTrace();
	        return false;
	    }
	}

}
