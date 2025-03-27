package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entities.Categorie;
import Entities.Papier;
import Entities.Repartition;
import Entities.Type;

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
	            	Type.valueOf(rs.getString("type")),
	                rs.getDouble("cout"),
	                rs.getDate("date").toLocalDate(),
	                rs.getDate("dateProchaine").toLocalDate(),
	                rs.getString("immatV") 
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}

}
