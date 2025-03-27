package Service;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import Dao.VehiculeDao;
import Entities.Categorie;
import Entities.Vehicule;

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
    

    
    public boolean deleteVehicule(String immat) {
        return vehiculeDao.deleteVehicule(immat);
    }

}
