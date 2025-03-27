package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Entities.Repartition;

public class RepartitionDao {
	private Connection conn = connection.getInstance();
	public boolean ajouterRepartition(Repartition repartition) {
	    String sql = "INSERT INTO repartition (date,description,cout,preuve,immatV) VALUES (?,?,?,?,?)";

	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setDate(1, java.sql.Date.valueOf(repartition.getDate()));
	        stmt.setString(2, repartition.getDiscription());
	        stmt.setDouble(3, repartition.getCout());
	        stmt.setString(4, repartition.getPreuve());
	        stmt.setString(5, repartition.getImmatricule());
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
	
	public List<Repartition> getReparationsByImmat(String immatriculation) {
	    List<Repartition> liste = new ArrayList<>();
	    String sql = "SELECT * FROM repartition WHERE immatV = ?";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, immatriculation);
	        ResultSet rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	            liste.add(new Repartition(
	                rs.getDate("date").toLocalDate(),
	                rs.getString("description"),
	                rs.getDouble("cout"),
	                rs.getString("preuve"),
	                rs.getString("immatV") 
	            ));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return liste;
	}

}
