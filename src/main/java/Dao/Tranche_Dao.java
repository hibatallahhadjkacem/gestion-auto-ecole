package Dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Entities.Tranche;

public class Tranche_Dao {
	
    private static Connection connexion;

    private final String DB_URL = "jdbc:mysql://localhost:3306/autoecole";
    private final String USER = "root";
    private final String PASS = "";

    public Tranche_Dao() throws SQLException{

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
                new Tranche_Dao();
            }catch(Exception e){
                System.out.println("--"+e.getMessage());
            }
        return connexion;
    }
    
    
    
    
    
    private static Connection conn = getInstance();
	
	
    
    
    
    /*public static void ajouterTranches(int paiementId, List<Tranche> tranches) throws SQLException {
        String insertTranche = "INSERT INTO tranche (id_paiement, numero_tranche, date_limite, montant, statut, date_paiement_effectif) VALUES (?, ?, ?, ?, ?, ?)";
        for (Tranche t : tranches) {
            PreparedStatement psTranche = conn.prepareStatement(insertTranche);
            psTranche.setInt(1, paiementId);
            psTranche.setInt(2, t.getNumeroTranche());
            psTranche.setDate(3, java.sql.Date.valueOf(t.getDateLimite()));
            psTranche.setBigDecimal(4, t.getMontant());
            psTranche.setString(5, t.getStatut());

            if (t.getDatePaiementEffectif() != null) {
                psTranche.setDate(6, java.sql.Date.valueOf(t.getDatePaiementEffectif()));
            } else {
                psTranche.setNull(6, Types.DATE);
            }

            psTranche.executeUpdate();
        }
    }*/
    
    
    public static boolean ajouterTranches(int paiementId, List<Tranche> tranches, BigDecimal montantTotal) throws SQLException {
        // Calculer la somme des montants des tranches
        BigDecimal totalTranches = BigDecimal.ZERO;
        for (Tranche t : tranches) {
            totalTranches = totalTranches.add(t.getMontant());
        }

        // Vérifier que la somme des montants des tranches est égale au montant total
        if (totalTranches.compareTo(montantTotal) != 0) {
            // Retourner false si la somme ne correspond pas au montant total
            return false;
        }

        // Si la validation est réussie, on procède à l'ajout des tranches dans la base de données
        String insertTranche = "INSERT INTO tranche (id_paiement, numero_tranche, date_limite, montant, statut, date_paiement_effectif) VALUES (?, ?, ?, ?, ?, ?)";
        for (Tranche t : tranches) {
            PreparedStatement psTranche = conn.prepareStatement(insertTranche);
            psTranche.setInt(1, paiementId);
            psTranche.setInt(2, t.getNumeroTranche());
            psTranche.setDate(3, java.sql.Date.valueOf(t.getDateLimite()));
            psTranche.setBigDecimal(4, t.getMontant());
            psTranche.setString(5, t.getStatut());

            if (t.getDatePaiementEffectif() != null) {
                psTranche.setDate(6, java.sql.Date.valueOf(t.getDatePaiementEffectif()));
            } else {
                psTranche.setNull(6, Types.DATE);
            }

            psTranche.executeUpdate();
        }

        // Retourner true si l'ajout des tranches a été effectué avec succès
        return true;
    }


    
    
    
    
    
    
    

	
	public  static List<Tranche> getTranchesByPaiement(int idPaiement) {
        List<Tranche> tranches = new ArrayList<>();
        String sql = "SELECT * FROM tranche WHERE id_paiement = ? ORDER BY numero_tranche";
        System.out.println("seya sql");
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPaiement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
            	LocalDate datePaiementEffectif = null;
            	if (rs.getDate("date_paiement_effectif") != null) {
            	    datePaiementEffectif = rs.getDate("date_paiement_effectif").toLocalDate();
            	}
                Tranche t = new Tranche(
                    rs.getInt("id"),
                    rs.getInt("id_paiement"),
                    rs.getInt("numero_tranche"),
                    rs.getDate("date_limite").toLocalDate(),
                    rs.getBigDecimal("montant"),
                    rs.getString("statut"),
                    //rs.getDate("date_paiement_effectif").toLocalDate()
                    datePaiementEffectif

                     );
                tranches.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(tranches);
        return tranches;
    }
	
	
	public static void updateTranche(Tranche t) {
	    String sql = "UPDATE tranche SET  date_limite=?, montant=?, statut=?, date_paiement_effectif=? WHERE id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	       // ps.setInt(1, t.getNumeroTranche());
	        ps.setDate(1, Date.valueOf(t.getDateLimite()));
	        ps.setBigDecimal(2, t.getMontant());
	        ps.setString(3, t.getStatut());
	        ps.setDate(4, t.getDatePaiementEffectif() != null ? Date.valueOf(t.getDatePaiementEffectif()) : null);
	        ps.setInt(5, t.getId());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	
	
	
	
	public static void updateTranche2(Tranche t) {
	    String sql = "UPDATE tranche SET  date_limite=?, montant=?, statut=?, date_paiement_effectif=? WHERE id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	       // ps.setInt(1, t.getNumeroTranche());
	        ps.setDate(1, Date.valueOf(t.getDateLimite()));
	        ps.setBigDecimal(2, t.getMontant());
	        ps.setString(3, t.getStatut());
	        ps.setDate(4, t.getDatePaiementEffectif() != null ? Date.valueOf(t.getDatePaiementEffectif()) : null);
	        ps.setInt(5, t.getId());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
	
	
	
	
	
	public static void updateTranche3(Tranche t) {
	    String sql = "UPDATE tranche SET  date_limite=?, statut=?, date_paiement_effectif=? WHERE id=?";
	    try (PreparedStatement ps = conn.prepareStatement(sql)) {
	       // ps.setInt(1, t.getNumeroTranche());
	        ps.setDate(1, Date.valueOf(t.getDateLimite()));
	       // ps.setBigDecimal(3, t.getMontant());
	        ps.setString(2, t.getStatut());
	        ps.setDate(3, t.getDatePaiementEffectif() != null ? Date.valueOf(t.getDatePaiementEffectif()) : null);
	        ps.setInt(4, t.getId());
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void deleteTranchesByPaiementId(int paiementId) {
	    String query = "DELETE FROM tranche WHERE id_paiement = ?";
	    try (PreparedStatement ps = conn.prepareStatement(query)) {
	        ps.setInt(1, paiementId);
	        ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	
	public void add(Tranche tranche) {
        String sql = "INSERT INTO tranche (id_paiement, numero_tranche, date_limite, montant, statut, date_paiement_effectif) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, tranche.getIdPaiement());
            ps.setInt(2, tranche.getNumeroTranche());
            ps.setDate(3, java.sql.Date.valueOf(tranche.getDateLimite()));
            ps.setBigDecimal(4, tranche.getMontant());
            ps.setString(5, tranche.getStatut());
            if (tranche.getDatePaiementEffectif() != null) {
                ps.setDate(6, java.sql.Date.valueOf(tranche.getDatePaiementEffectif()));
            } else {
                ps.setNull(6, java.sql.Types.DATE);
            }

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // ou logger
        }
    }
	
	
	
	

}
