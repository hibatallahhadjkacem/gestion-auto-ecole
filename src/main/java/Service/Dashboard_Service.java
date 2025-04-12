package Service;

import java.sql.SQLException;

import Dao.Dashboard_DAO;
import Dao.Paiement_DAO;

public class Dashboard_Service {


    private Dashboard_DAO dao;

    public Dashboard_Service() {
        try {
            dao = new Dashboard_DAO();
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionnel : tu peux aussi relancer une RuntimeException pour signaler un échec grave
            throw new RuntimeException("Erreur de connexion à la base de données dans Dashboard_Service", e);
        }
    }
	
	

    public int getTotalCandidats() throws SQLException {
        return dao.getTotalCandidats();
    }

    public int getTotalMoniteurs() throws SQLException {
        return dao.getTotalMoniteurs();
    }

    public int getTotalVehicules() throws SQLException {
        return dao.getTotalVehicules();
    }

    public double getTotalPaiements() throws SQLException {
        return dao.getTotalPaiements();
    }

    public double getPaiementsEffectues() throws SQLException {
        return dao.getPaiementsEffectues();
    }


    public double getPourcentagePaiementEffectue() throws SQLException {
        double total = dao.getTotalPaiements();
        double effectue = dao.getPaiementsEffectues();
        return total == 0 ? 0 : (effectue / total);
    }
}

