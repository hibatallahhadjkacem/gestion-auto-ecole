package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.Papier;
import Entities.Type2;

public class PapierDao {
	private Connection conn = connection.getInstance();
	
	
	public boolean ajouterPapier(Papier papier) {
	    String sql = "INSERT INTO papier (type,cout,date,dateProchaine,immatV) VALUES (?,?,?,?,?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, papier.getType().toString());
	        stmt.setDouble(2, papier.getCout());
	        stmt.setDate(3, java.sql.Date.valueOf(papier.getDate()));  
	        stmt.setDate(4, java.sql.Date.valueOf(papier.getDateProchain()));
	        stmt.setString(5, papier.getImmatricule());
	        stmt.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	public int getAge(String immat) {
        String sql = "SELECT age FROM vehicule WHERE immatricule = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, immat);  

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("age"); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; 
    }
	
	public String getCateg(String immat) {
        String sql = "SELECT categorie FROM vehicule WHERE immatricule = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, immat);  

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("categorie"); 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
	
	//update papier
	
	public boolean updateCoutPapier(String immat,float cout,LocalDate date,Type2 type) {
        String sql = "UPDATE papier SET cout= ? WHERE immatV = ? AND date=? AND type=?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setFloat(1, cout); 
            stmt.setString(2, immat);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.setString(4, type.toString());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	public boolean updateDateProchPapier(String immat,LocalDate dateProch,LocalDate date,Type2 type) {
        String sql = "UPDATE papier SET dateProchaine= ? WHERE immatV = ? AND date=? AND type=?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1,  java.sql.Date.valueOf(dateProch)); 
            stmt.setString(2, immat);
            stmt.setDate(3, java.sql.Date.valueOf(date));
            stmt.setString(4, type.toString());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
	}
		
	
	
	public String getDerniereImmatriculation() {
	    String sql = "SELECT immatricule FROM vehicule ORDER BY immatricule DESC LIMIT 1";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql);
	         ResultSet rs = stmt.executeQuery()) {
	        
	        if (rs.next()) {
	            return rs.getString("immatricule");
	        } else {
	            return null; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	public List<Papier> getPapiersByImmat(String immatriculation) {
	    List<Papier> liste = new ArrayList<>();
	    String sql = "SELECT * FROM papier WHERE immatV = ?";
	    				
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, immatriculation);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            liste.add(new Papier(
	            	Type2.valueOf(rs.getString("type")),
	                rs.getDouble("cout"),
	                rs.getDate("dateProchaine").toLocalDate(),
	                rs.getDate("date").toLocalDate(),
	                rs.getString("immatV") 
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}

}
