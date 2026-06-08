package Dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import Entities.Dossier;

public class DossierDAO {
	private static Connection conn =connection.getInstance();
	public static List<Dossier> findAll(){
		return null;
		}
	
	
	public static boolean save(Dossier d) {
		int dossierId=0;
		 PreparedStatement stmt = null;
		 ResultSet rs = null;
		 try {
		        String sql = "INSERT INTO dossier (cin,photo,certificat_medical,numeroCondidat) values(?,?,?,?)";
		        stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		        stmt.setBytes(1, d.getCin());
		        stmt.setBytes(2, d.getPhoto());
		        stmt.setBytes(3, d.getCertif());
		        stmt.setInt(4, d.getNumeroCondidat());
		        stmt.executeUpdate();
		        rs = stmt.getGeneratedKeys();
		        if (rs.next()) {
		        	dossierId = rs.getInt(1);
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
	   public static byte[] convertFileToByteArray(String filePath) throws IOException {
	        File file = new File(filePath);
	        try (FileInputStream fis = new FileInputStream(file)) {
	            byte[] byteArray = new byte[(int) file.length()];
	            fis.read(byteArray);
	            return byteArray;
	        } 
	        
	    }
		public static Dossier findDossierById(int num) {
		    String sql = "SELECT * FROM dossier WHERE numeroCondidat = ?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, num); 
		        ResultSet rs = stmt.executeQuery();

		        if (rs.next()) {
		         
					byte[] cin =rs.getBytes(1);
					byte[] photo =rs.getBytes(2);
					byte[] certif =rs.getBytes(3);
					int idCondidat =rs.getInt(4);
		            return new Dossier(cin, photo, certif, idCondidat);
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return null; 
		}
		public static boolean update(Dossier d) {
			 String sql = "UPDATE dossier SET cin = ?, photo = ?, certificat_medical = ? WHERE numeroCondidat = ?";
		    
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		    	        
		         stmt.setBytes(1, d.getCin()); 
		         stmt.setBytes(2, d.getPhoto()); 
		         stmt.setBytes(3, d.getCertif()); 
		         stmt.setInt(4, d.getNumeroCondidat());          
		        int rowsAffected = stmt.executeUpdate();
		        return rowsAffected > 0; 
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}

			public static boolean delete(int num) {
		    String sql = "DELETE FROM dossier WHERE numeroCondidat = ?";
		    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
		        stmt.setInt(1, num);
		        int rowsAffected = stmt.executeUpdate();
		        return rowsAffected > 0; 
		    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		    }
		}
		
	
}
