package Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.Categorie;
import Entities.Vehicule;

public class VehiculeDao {
    private Connection conn = connection.getInstance();
    
    public List<Vehicule> getVehiculesByImmat(String immat) throws SQLException {
        List<Vehicule> vehicules = new ArrayList<>();
        String sql = "SELECT * FROM vehicule WHERE immatricule = ?"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, immat); 
            ResultSet rs =stmt.executeQuery();
            while (rs.next()) {
                String modele = rs.getString("model");
                Date date = rs.getDate("dateMiseEnService");
                int kmtot = rs.getInt("kmTotal");
                int kmproche = rs.getInt("kmProchEntr");
                String catg = rs.getString("categorie");
                int age = rs.getInt("age");
                Categorie categorie=Categorie.valueOf(catg);

                Vehicule vehicule = new Vehicule(immat, modele, date.toLocalDate(), kmtot, kmproche, categorie, age);
                vehicules.add(vehicule);
                
            }
        }

        return vehicules;
    }

    public boolean ajouterVehicule(Vehicule vehicule) {
        String sql = "INSERT INTO vehicule VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, vehicule.getImmatricule());
            stmt.setString(2, vehicule.getModel());
            stmt.setDate(3, java.sql.Date.valueOf(vehicule.getDateMiseEnService()));
            stmt.setInt(4, vehicule.getKmTotal());         
            stmt.setInt(5, vehicule.getKmProchEntretient()); 
            stmt.setString(6, vehicule.getCatégorie().toString());
            stmt.setInt(7, vehicule.getAge());
                                   

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteVehicule(String immat) {
        String sql = "DELETE FROM vehicule WHERE immatricule = ?"; 
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, immat); 
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateAgeVehicule(String immat, int age) {
        String sql = "UPDATE vehicule SET age= ? WHERE immatricule = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, age); 
            stmt.setString(2, immat);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updatekmProchEntrVehicule(String immat, Categorie categorie) {
        String sql = "UPDATE vehicule SET categorie= ? WHERE immatricule = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categorie.toString()); 
            stmt.setString(2, immat);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updatekmProchEntrVehicule(String immat, int kmProchEntr) {
        String sql = "UPDATE vehicule SET kmProchEntr= ? WHERE immatricule = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kmProchEntr); 
            stmt.setString(2, immat);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updatekmTotalVehicule(String immat, int kmTotal) {
        String sql = "UPDATE vehicule SET kmTotal= ? WHERE immatricule = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kmTotal); 
            stmt.setString(2, immat);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateDateVehicule(String immat, LocalDate dateMiseEnService) {
        String sql = "UPDATE vehicule SET dateMiseEnService= ? WHERE immatricule = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, java.sql.Date.valueOf(dateMiseEnService)); 
            stmt.setString(2, immat);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateMadelVehicule(String immat, String model) {
        String sql = "UPDATE vehicule SET model= ? WHERE immatricule = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, model); 
            stmt.setString(2, immat);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    
    public List<Vehicule> getAllVehicules() throws SQLException {
        List<Vehicule> vehicules = new ArrayList<>();
        String sql = "SELECT * FROM vehicule"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql);
   	         ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String immat = rs.getString("immatricule");
                String modele = rs.getString("model");
                Date date = rs.getDate("dateMiseEnService");
                int kmtot = rs.getInt("kmTotal");
                int kmproche = rs.getInt("kmProchEntr");
                String catg = rs.getString("categorie");
                int age = rs.getInt("age");
                Categorie categorie=Categorie.valueOf(catg);

                Vehicule vehicule = new Vehicule(immat, modele, date.toLocalDate(), kmtot, kmproche, categorie, age);
                vehicules.add(vehicule);
            }
        }

        return vehicules;
    }
    

    

}
