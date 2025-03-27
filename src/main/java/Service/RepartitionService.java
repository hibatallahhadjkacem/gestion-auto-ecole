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

}
