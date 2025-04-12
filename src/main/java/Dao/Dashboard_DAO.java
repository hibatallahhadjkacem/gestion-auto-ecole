package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dashboard_DAO {

	private static Connection connexion;

    private final String DB_URL = "jdbc:mysql://localhost:3306/autoecole";
    private final String USER = "root";
    private final String PASS = "";

    public Dashboard_DAO() throws SQLException{

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
                new Dashboard_DAO();
            }catch(Exception e){
                System.out.println("--"+e.getMessage());
            }
        return connexion;
    }
    
    
    
    
    
    private static Connection conn = getInstance();
    
    
	
	
	
    public int getTotalCandidats() throws SQLException {
        String query = "SELECT COUNT(*) FROM condidat";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
        	System.out.println("cond");
            return rs.next() ? rs.getInt(1) : 0;
           
        }
    }

    public int getTotalMoniteurs() throws SQLException {
        String query = "SELECT COUNT(*) FROM moniteur";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public int getTotalVehicules() throws SQLException {
        String query = "SELECT COUNT(*) FROM vehicule";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public double getTotalPaiements() throws SQLException {
        String query = "SELECT SUM(montant_total) FROM paiement";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            return rs.next() ? rs.getDouble(1) : 0.0;
        }
    }

    public double getPaiementsEffectues() throws SQLException {
        String query = "SELECT SUM(montant) FROM tranche WHERE statut='Payée'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
        	System.out.println("hiii");
        	if (rs.next()) {
        	    double montant = rs.getDouble(1);
        	    System.out.println("Montant total payée : " + montant);
        	    return montant;
        	} else {
        	    System.out.println("Aucun paiement trouvé.");
        	    return 0.0;
        	}

        }
    }

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}	
	
	
	

