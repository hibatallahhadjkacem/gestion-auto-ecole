package Dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.*;

public class CondidatDAO {
	private static Connection conn =connection.getInstance();
	public static List<Condidat> findAll(){
		Statement stmt=null;
		ResultSet rs=null;
		List<Condidat> condidats = new ArrayList<Condidat>();
		String SQL="SELECT * FROM condidat";
		
		try {

			stmt=conn.createStatement();

			rs=stmt.executeQuery(SQL);

			while (rs.next()) {

			int id =rs.getInt(1);

			String nom=rs.getString(2);

			String prenom=rs.getString(3);
			
			 LocalDate dateNaissance = rs.getDate(4).toLocalDate();
	         String adresse = rs.getString(5);
	         int tel = rs.getInt(6);
	         String email = rs.getString(7);
	         String typePermiStr = rs.getString(8); 
	         Type typePermi = null;

	            if (typePermiStr != null) {
	                try {
	                    typePermi = Type.valueOf(typePermiStr.trim().toUpperCase()); 
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid typePermi value in database: " + typePermiStr);
	                }
	            }
			Condidat condidat = new Condidat(id, nom, prenom, dateNaissance, adresse, tel, email, typePermi);

			condidats.add(condidat);

			}


			}catch (SQLException e) {

			e.printStackTrace();

			}
		
		
		return condidats;}
	public static boolean save(Condidat c) {
		  int condidatNumero = 0;
		    PreparedStatement stmt = null;
		    ResultSet rs = null;
		    
		    try {
		        String sql = "INSERT INTO condidat (numero, nom, prenom,dateNaissance,adresse,tel,email,typePermi) VALUES (?, ?, ?,?,?,?,?,?)";
		        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		        stmt.setInt(1, c.getNumero());
		        stmt.setString(2, c.getNom());
		        stmt.setString(3, c.getPrenom());
		        stmt.setDate(4, java.sql.Date.valueOf(c.getDateNais()));
		        stmt.setString(5, c.getAdresse());
		        stmt.setInt(6, c.getTel());
		        stmt.setString(7, c.getEmail());
		        stmt.setString(8, c.getTypePermi().name());
		        stmt.executeUpdate();
		        rs = stmt.getGeneratedKeys();
		        if (rs.next()) {
		        	condidatNumero = rs.getInt(1);
		        }
		      } catch (SQLException e) {
			        System.out.println("Error while saving Dossier: " + e.getMessage());
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
	public static boolean delete(int numero) {
	    String sql = "DELETE FROM condidat WHERE numero = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, numero);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error while deleting Condidat: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
	public static boolean update(Condidat c) {
	    String sql = "UPDATE condidat SET nom = ?, prenom = ?, dateNaissance = ?, adresse = ?, tel = ?, email = ?, typePermi = ? WHERE numero = ?";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setString(1, c.getNom());
	        stmt.setString(2, c.getPrenom());
	        stmt.setDate(3, java.sql.Date.valueOf(c.getDateNais()));
	        stmt.setString(4, c.getAdresse());
	        stmt.setInt(5, c.getTel());
	        stmt.setString(6, c.getEmail());
	        stmt.setString(7, c.getTypePermi().name());
	        stmt.setInt(8, c.getNumero());  

	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error while updating Condidat: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}
	public static Condidat findCondidatById(int numero) {
	    String sql = "SELECT * FROM condidat WHERE numero = ?";
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, numero); 
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	           
	            int id = rs.getInt("numero");
	            String firstName = rs.getString("nom");
	            String lastName = rs.getString("prenom");
	            LocalDate dateNaissance = rs.getDate("dateNaissance").toLocalDate();
	            String adresse = rs.getString("adresse");
	            int tel = rs.getInt("tel");
	            String email = rs.getString("email");
	            String typePermiStr = rs.getString("typePermi"); 
	            Type typePermi = null;

	            if (typePermiStr != null) {
	                try {
	                    typePermi = Type.valueOf(typePermiStr.trim().toUpperCase()); 
	                } catch (IllegalArgumentException e) {
	                    System.out.println("Invalid typePermi value in database: " + typePermiStr);
	                }
	            }

	         
	            return new Condidat(id, firstName, lastName, dateNaissance, adresse, tel, email, typePermi);
	        }
	    } catch (SQLException e) {
	        System.out.println("Error while fetching Condidat: " + e.getMessage());
	        e.printStackTrace();
	    }
	    return null; 
	}




	public boolean deleteAll() {
	    String sql = "DELETE FROM condidat";
	    
	    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0; 
	    } catch (SQLException e) {
	        System.out.println("Error while deleting all Condidats: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}


}
