package Service;

import java.time.LocalDate;
import java.util.List;

import Dao.RepartitionDao;
import Entities.Repartition;



public class RepartitionService {
	private RepartitionDao repartitionDao=new RepartitionDao();

	public boolean addRepartition(LocalDate date,String descrip,float cost,String proof,String immat) {
		Repartition repartition=new Repartition(date, descrip, cost, proof,immat);
		return repartitionDao.ajouterRepartition(repartition);
	}
	
	public List<Repartition> getReparationsByImmat(String immatricule){
		return repartitionDao.getReparationsByImmat(immatricule);
	}
	
	//update Repartition
	
	public boolean updatePreuveRepartition(String immat,String preuve,LocalDate date) {
		return repartitionDao.updatePreuveRepartition(immat, preuve, date);
	}
	
	public boolean updateDescriptionRepartition(String immat,String descrip,LocalDate date) {
		return repartitionDao.updateDescriptionRepartition(immat, descrip, date);
	}
	
	public boolean updateCoutRepartition(String immat,float cout,LocalDate date) {
		return repartitionDao.updateCoutRepartition(immat, cout, date);
	}

}
