package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import Entities.DisponibiliteV;
import Entities.Repartition;
import Entities.Type2;

public class DisponibiliteVDao {
	private Connection conn = connection.getInstance();
	
	
	public List<DisponibiliteV> getCarAuj() {
	    List<DisponibiliteV> liste = new ArrayList<>();
	    String sql = "SELECT dateTime, dispo, immatV FROM disponibilitev D, vehicule V WHERE DATE(dateTime) = CURRENT_DATE AND D.immatV = V.immatricule AND V.categorie = 'Car' AND dispo=true";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            DisponibiliteV dispo = new DisponibiliteV(
	                    rs.getTimestamp("dateTime").toLocalDateTime(), 
	                    rs.getBoolean("dispo"),
	                    rs.getString("immatV") 
	                );
	                liste.add(dispo);
	    	        

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}
	
	public List<DisponibiliteV> getTruckAuj() {
	    List<DisponibiliteV> liste = new ArrayList<>();
	    String sql = "SELECT dateTime, dispo, immatV FROM disponibilitev D, vehicule V WHERE DATE(dateTime) = CURRENT_DATE AND D.immatV = V.immatricule AND V.categorie = 'Truck' AND dispo=true";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            DisponibiliteV dispo = new DisponibiliteV(
	                    rs.getTimestamp("dateTime").toLocalDateTime(), 
	                    rs.getBoolean("dispo"),
	                    rs.getString("immatV") 
	                );
	                liste.add(dispo);
	    	        

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}
	
	
	public List<DisponibiliteV> ViewdispVehic(Object debD, Object finD, Object debM, Object finM, Object debY, Object finY, Object immat) {
	    List<DisponibiliteV> liste = new ArrayList<>();
	    
	    StringBuilder sql = new StringBuilder("SELECT dateTime, dispo, immatV FROM disponibilitev D WHERE immatV = ?");
	    List<Object> params = new ArrayList<>();
	    params.add(immat); // immatricule obligatoire

	    if (debD != null && finD != null) {
	        sql.append(" AND DAY(dateTime) BETWEEN ? AND ?");
	        params.add(debD);
	        params.add(finD);
	    }
	    if (debM != null && finM != null) {
	        sql.append(" AND MONTH(dateTime) BETWEEN ? AND ?");
	        params.add(debM);
	        params.add(finM);
	    }
	    if (debY != null && finY != null) {
	        sql.append(" AND YEAR(dateTime) BETWEEN ? AND ?");
	        params.add(debY);
	        params.add(finY);
	    }

	    try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
	        // Ajout des paramètres dynamiquement
	        for (int i = 0; i < params.size(); i++) {
	            Object param = params.get(i);
	            if (param instanceof Integer) {
	                stmt.setInt(i + 1, (Integer) param);
	            } else if (param instanceof String) {
	                stmt.setString(i + 1, (String) param);
	            }
	        }

	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            DisponibiliteV dispo = new DisponibiliteV(
	                rs.getTimestamp("dateTime").toLocalDateTime(),
	                rs.getBoolean("dispo"),
	                rs.getString("immatV")
	            );
	            liste.add(dispo);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return liste;
	}

	
	public List<DisponibiliteV> getDisByDate(LocalDate dt, String categ) {
		List<DisponibiliteV> liste = new ArrayList<>();
	    String sql = "SELECT dateTime, dispo, immatV FROM disponibilitev D, vehicule V WHERE DATE(dateTime) = ?AND D.immatV = V.immatricule AND V.categorie = ? AND dispo=true";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setDate(1,java.sql.Date.valueOf( dt));
	    	stmt.setString(2, categ);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            DisponibiliteV dispo = new DisponibiliteV(
	                    rs.getTimestamp("dateTime").toLocalDateTime(), 
	                    rs.getBoolean("dispo"),
	                    rs.getString("immatV") 
	                );
	                liste.add(dispo);
	    	        

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}
	
	
	public List<DisponibiliteV> getDisByDateTp(LocalDate dt, LocalTime temp, String categ) {
		LocalDateTime dttp=LocalDateTime.of(dt,temp);
		List<DisponibiliteV> liste = new ArrayList<>();
	    String sql = "SELECT dateTime, dispo, immatV FROM disponibilitev D, vehicule V WHERE dateTime = ?AND D.immatV = V.immatricule AND V.categorie = ? AND dispo=true";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setTimestamp(1,java.sql.Timestamp.valueOf(dttp));
	    	stmt.setString(2, categ);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            DisponibiliteV dispo = new DisponibiliteV(
	                    rs.getTimestamp("dateTime").toLocalDateTime(), 
	                    rs.getBoolean("dispo"),
	                    rs.getString("immatV") 
	                );
	                liste.add(dispo);
	    	        

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}

	
	///
	
	public String getCateg(String immat) {
	    String catg = null;
	    String sql = "SELECT categorie FROM vehicule WHERE immatricule = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, immat);
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                catg = rs.getString("categorie");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return catg;
	}

	
	public List<DisponibiliteV> getMotoAuj() {
	    List<DisponibiliteV> liste = new ArrayList<>();
	    String sql = "SELECT dateTime, dispo, immatV FROM disponibilitev D, vehicule V WHERE DATE(dateTime) = CURRENT_DATE AND D.immatV = V.immatricule AND V.categorie = 'Motorcycle' AND dispo=true ";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            DisponibiliteV dispo = new DisponibiliteV(
	                    rs.getTimestamp("dateTime").toLocalDateTime(), 
	                    rs.getBoolean("dispo"),
	                    rs.getString("immatV") 
	                );
	                liste.add(dispo);
	    	        

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}
	
	
	
	public int rechImmat(String immat) {
	    String sql = "SELECT 1 FROM vehicule WHERE immatricule = ? LIMIT 1"; // Optimisation SQL
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, immat);
	        ResultSet rs = stmt.executeQuery();
	        return rs.next() ? 1 : 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return -1; 
	    }
	}
	
	public boolean addDisponib(String immat,LocalDateTime dateTp) {
		String sql="INSERT INTO disponibilitev (dateTime, dispo, immatV) VALUE (?,?,?)" ;
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setTimestamp(1, Timestamp.valueOf(dateTp)); 
			stmt.setBoolean(2, true);
            stmt.setString(3,immat);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateDispoByImmatricule(String im,LocalDateTime datTp) {
		String sql = "UPDATE disponibilitev SET dispo = CASE WHEN dispo = TRUE THEN FALSE ELSE TRUE END WHERE immatV = ? AND datetime = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, im);
        	stmt.setTimestamp(2, Timestamp.valueOf(datTp));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}



	



	



}
