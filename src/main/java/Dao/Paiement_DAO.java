package Dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Entities.Tranche;
import Entities.Paiement;



public class Paiement_DAO {
    private static Connection connexion;

    private final String DB_URL = "jdbc:mysql://localhost:3306/autoecole";
    private final String USER = "root";
    private final String PASS = "";

    public Paiement_DAO() throws SQLException{

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
                new Paiement_DAO();
            }catch(Exception e){
                System.out.println("--"+e.getMessage());
            }
        return connexion;
    }
    
    
    
    
    
    private static Connection conn = getInstance();
    
    
    public static List<Paiement> findAll() {
    	Statement stmt=null;

		ResultSet rs=null;

        List<Paiement> paiements = new ArrayList<>();
        
        String SQL = "SELECT p.id, p.id_candidat, p.date_paiement_initial, p.montant_total, p.mode_paiement, c.nom, c.prenom " +
                "FROM paiement p " +
                "JOIN condidat c ON p.id_candidat = c.numero";
        
        try {

			stmt=conn.createStatement();//Créer un objet Statement pour exécuter la requête SQL

			rs=stmt.executeQuery(SQL);// Étape 4 : Exécuter une requête SQL et récupérer le résultat

        
            while (rs.next()) {
                Paiement paiement = new Paiement(
                		 rs.getInt("id"),
                         rs.getInt("id_candidat"),
                         rs.getDate("date_paiement_initial").toLocalDate(),
                         rs.getBigDecimal("montant_total"),
                         rs.getString("mode_paiement")
                       //  rs.getString("statut_global")
                       
                    
                );
                String firstName = rs.getString("prenom");
                String lastName = rs.getString("nom");
                
                paiement.setCandidatNom(firstName); 
                paiement.setCandidatPrenom(lastName); 

                
                paiements.add(paiement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paiements;
    }
    
    
    /*public  static void ajouterPaiement(Paiement p, List<Tranche> tranches) throws SQLException {
        String insertPaiement = "INSERT INTO paiement (id_candidat, date_paiement_initial, montant_total, mode_paiement) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertPaiement, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, p.getIdCandidat());
        ps.setDate(2, java.sql.Date.valueOf(p.getDatePaiement()));
        ps.setBigDecimal(3, p.getMontantTotal());
        ps.setString(4, p.getModePaiement());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            int paiementId = rs.getInt(1);

            // Vérifie si le paiement est en mode 2 ou 3 tranches
            if ("facilite:2tranches".equalsIgnoreCase(p.getModePaiement()) || "facilite:3tranche".equalsIgnoreCase(p.getModePaiement())) {

                for (Tranche t : tranches) {
                    String insertTranche = "INSERT INTO tranche (id_paiement, numero_tranche, date_limite, montant, statut, date_paiement_effectif) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement psTranche = conn.prepareStatement(insertTranche);
                    psTranche.setInt(1, paiementId);
                    psTranche.setInt(2, t.getNumeroTranche());
                    psTranche.setDate(3, java.sql.Date.valueOf(p.getDatePaiement()));
                    psTranche.setBigDecimal(4, t.getMontant());
                    psTranche.setString(5, t.getStatut());

                    if (t.getDatePaiementEffectif() != null) {
                        psTranche.setDate(6, java.sql.Date.valueOf(p.getDatePaiement()));
                    } else {
                        psTranche.setNull(6, Types.DATE);
                    }

                    psTranche.executeUpdate();
                }
            }
        }
    }*/
    
    
    
    
    public static int ajouterPaiement(Paiement p) throws SQLException {
        String insertPaiement = "INSERT INTO paiement (id_candidat, date_paiement_initial, montant_total, mode_paiement) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(insertPaiement, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, p.getIdCandidat());
        ps.setDate(2, java.sql.Date.valueOf(p.getDatePaiement()));
        ps.setBigDecimal(3, p.getMontantTotal());
        ps.setString(4, p.getModePaiement());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1); // retourne l'ID du paiement inséré
        }
        throw new SQLException("Échec lors de la récupération de l'ID du paiement.");
    }

    
    
    
    
    
    
    
    
    
    

    
   
    public void deletePaiementById(int id) {
        String query = "DELETE FROM paiement WHERE id = ?";
        try (PreparedStatement ps = connexion.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    public boolean candidatExists(int idCandidat) {
        String query = "SELECT COUNT(*) FROM condidat WHERE numero = ?";
        try (PreparedStatement stmt = connexion.prepareStatement(query)) {
            stmt.setInt(1, idCandidat);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;  // Si le compte est supérieur à 0, l'ID existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    
  
    public Paiement getPaiementById(int id) {
        Paiement paiement = null;
        String sql = "SELECT p.id, p.idCandidat, p.datePaiement, p.montantTotal, p.modePaiement, "
                   + "c.nom, c.prenom "
                   + "FROM paiement p "
                   + "JOIN condidat c ON p.idCondidat = c.numero "
                   + "WHERE p.id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                paiement = new Paiement(
                    rs.getInt("id"),
                    rs.getInt("idCandidat"),
                    rs.getDate("datePaiement").toLocalDate(),
                    rs.getBigDecimal("montantTotal"),
                    rs.getString("modePaiement"),
                    rs.getString("candidatNom"),
                    rs.getString("candidatPrenom")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return paiement;
    }

    

   
    
   /* public void updatePaiement(Paiement paiement) {
        String sql = "UPDATE paiement SET datePaiement = ?, montantTotal = ?, modePaiement = ? WHERE id = ?";

        try ( PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(paiement.getDatePaiement()));
            stmt.setBigDecimal(2, paiement.getMontantTotal());
            stmt.setString(3, paiement.getModePaiement());
            //stmt.setInt(4, paiement.getIdCandidat());
            stmt.setInt(4, paiement.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
    
    public boolean updatePaiement(Paiement paiement) {
        String sql = "UPDATE paiement SET date_paiement_initial = ?, montant_total = ?, mode_paiement = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(paiement.getDatePaiement()));
            stmt.setBigDecimal(2, paiement.getMontantTotal());
            stmt.setString(3, paiement.getModePaiement());
            stmt.setInt(4, paiement.getId());

            int rowsAffected = stmt.executeUpdate();

            // Si des lignes ont été affectées, la mise à jour a réussi
            if (rowsAffected > 0) {
                return true;  // La mise à jour a réussi
            } else {
                return false;  // Aucune ligne affectée, la mise à jour n'a pas réussi
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // En cas d'exception, retourner false
        }
    }


    
    
    
    
    
    
    
    
}
