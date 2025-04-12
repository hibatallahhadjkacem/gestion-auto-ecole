package Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import Dao.VehiculeDao;
import Entities.Categorie;
import Entities.Papier;
import Entities.Repartition;
import Entities.Vehicule;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class VehiculeService {
	
	private VehiculeDao vehiculeDao=new VehiculeDao();
	
    public List<Vehicule> getVehiculesByImmat(String immat) throws SQLException {
        return vehiculeDao.getVehiculesByImmat(immat);
    }
	
	public boolean addVehicule(String immat,String modele,LocalDate date,int kmtot,int kmproche,String catg,int age) {
		Categorie categorie=Categorie.valueOf(catg);
		Vehicule vehicule=new Vehicule(immat, modele, date, kmtot, kmproche, categorie, age);
		return vehiculeDao.ajouterVehicule(vehicule);
	}
	//affichage de vehicule
    public List<Vehicule> getAllVehicules() throws SQLException {
        return vehiculeDao.getAllVehicules();
    }
    
    //update vehicule 
    public boolean updateDateVehicule(String immat,LocalDate date) {
    	return vehiculeDao.updateDateVehicule(immat, date);
    }
    
    public boolean updateAgeVehicule(String immat, int age) {
    	return vehiculeDao.updateAgeVehicule(immat, age);
    }
    
    public boolean updateKmtotVehicule(String immat,int kmtot) {
    	return vehiculeDao.updatekmTotalVehicule(immat, kmtot);
    }
    
    public boolean updatekmProchEntrVehicule(String immat,int kmProchEntr) {
    	return vehiculeDao.updatekmProchEntrVehicule(immat, kmProchEntr);
    }
    
    public boolean updateCategorieVehicule(String immat, String categorie) {
    	Categorie categorie2=Categorie.valueOf(categorie);
    	return vehiculeDao.updateCategorieVehicule(immat, categorie2);
    }
    
    public boolean updateMadelVehicule(String immat,String modele) {
    	return vehiculeDao.updateMadelVehicule(immat, modele);
    }
    
    

    
    public boolean deleteVehicule(String immat) {
        return vehiculeDao.deleteVehicule(immat);
    }
    
    //notif 
    public List<Node> genererNotifications(List<Vehicule> vehicules) {
        List<Node> notifications = new ArrayList<>();
        LocalDate aujourdHui = LocalDate.now();

        for (Vehicule v : vehicules) {
            System.out.println("Véhicule: " + v.getImmatricule());

            // Récupérer les papiers et répartitions du véhicule
            List<Papier> papiers = vehiculeDao.getPapiersByImmat(v.getImmatricule());  
            List<Repartition> repartitions = vehiculeDao.getReparationsByImmat(v.getImmatricule());  

            for (Papier p : papiers) {
                if (p.getDateProchain() != null) {
                    long jours = ChronoUnit.DAYS.between(aujourdHui, p.getDateProchain());

                    // Créer un TextFlow pour chaque notification
                    TextFlow textFlow = new TextFlow();
                    textFlow.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Aucun fond, bordure ou padding

                    if (jours < 0) {
                        // Déjà expiré
                        Text emoji = new Text("⚠️ ");
                        emoji.setStyle("-fx-fill: red; -fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Emoji rouge

                        Text message = new Text(String.format("%s: %s a expiré le %s !", 
                            v.getImmatricule(), 
                            p.getClass().getSimpleName(), 
                            p.getDateProchain()));

                        message.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Texte sans fond ni bordure

                        textFlow.getChildren().addAll(emoji, message);  // Ajouter les deux éléments dans le TextFlow
                        notifications.add(textFlow);
                    } else if (jours <= 7) {
                        // Bientôt expiré
                        Text emoji = new Text("⏰ ");
                        emoji.setStyle("-fx-fill: orange; -fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Emoji orange

                        Text message = new Text(String.format("%s: %s expire dans %d jours (le %s)", 
                            v.getImmatricule(), 
                            p.getClass().getSimpleName(), 
                            jours, 
                            p.getDateProchain()));

                        message.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Texte sans fond ni bordure

                        textFlow.getChildren().addAll(emoji, message);  // Ajouter les deux éléments dans le TextFlow
                        notifications.add(textFlow);
                    }
                }
            }

            // Vérification annuelle de l’état du véhicule (réparation)
            for (Repartition r : repartitions) {
                long mois = ChronoUnit.MONTHS.between(r.getDate(), aujourdHui);

                if (mois >= 12) {
                    // Créer un TextFlow pour chaque notification
                    TextFlow textFlow = new TextFlow();
                    textFlow.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Aucun fond, bordure ou padding

                    Text emoji = new Text("🛠️ ");
                    emoji.setStyle("-fx-fill: blue; -fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Emoji bleu

                    Text message = new Text(String.format("%s: Vérification annuelle requise. Dernière vérification le %s (%d mois)", 
                        v.getImmatricule(), 
                        r.getDate(), 
                        mois));

                    message.setStyle("-fx-background-color: transparent; -fx-border-width: 0; -fx-padding: 0;");  // Texte sans fond ni bordure

                    textFlow.getChildren().addAll(emoji, message);  // Ajouter les deux éléments dans le TextFlow
                    notifications.add(textFlow);
                }
            }
        }

        return notifications;
    }

}
