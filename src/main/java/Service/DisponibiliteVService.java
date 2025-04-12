package Service;

import javafx.util.Pair;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import Dao.DisponibiliteVDao;
import Entities.DisponibiliteV;

public class DisponibiliteVService {
	private DisponibiliteVDao disponibiliteVDao=new DisponibiliteVDao();
	
	public int rechImmat(String immat) {
		return disponibiliteVDao.rechImmat(immat);
	}
	
	public Pair<Integer, Integer> StartFin(String d){
		if (d.indexOf("-")==-1){
			return new Pair<>(Integer.parseInt(d), Integer.parseInt(d));
		}else {
		        String[] parts = d.split("-");
		       return new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		}
	}
	
	public boolean addTemptrav(String im, String d, String m, String y, int sh, int sm, 
            LocalTime mornStart, LocalTime mornFin, LocalTime evnStart, LocalTime evnFin) {
	boolean test = true;
	
	for (int i = StartFin(y).getKey(); i <= StartFin(y).getValue(); i++) {
	for (int j = StartFin(m).getKey(); j <= StartFin(m).getValue(); j++) {
	for (int k = StartFin(d).getKey(); k <= StartFin(d).getValue(); k++) {
	
	// Vérification de la validité de la date avant de la créer
	if (isValidDate(k, j, i)) {
	    LocalTime currentTime = mornStart;
	    while (currentTime.isBefore(mornFin) || currentTime.equals(mornFin))
	    	{test = disponibiliteVDao.addDisponib(im, LocalDateTime.of(LocalDate.of(i, j, k), currentTime));
	        if (!test) {
	            return false;
	        }
	        currentTime = currentTime.plusMinutes(sh * 60 + sm);
	    }
	
	    LocalTime currentTime2 = evnStart;
	    while (currentTime2.isBefore(evnFin) || currentTime2.equals(evnFin))
	    	{test = disponibiliteVDao.addDisponib(im, LocalDateTime.of(LocalDate.of(i, j, k), currentTime2));
	        if (!test) {
	            return false;
	        }
	        currentTime2 = currentTime2.plusMinutes(sh * 60 + sm);
	    }
		} else {
	    return false; // Retourne false si la date est invalide
	    }}}}
	
		return test;
		}
	
	//Méthode pour vérifier si une date est valide
	private boolean isValidDate(int day, int month, int year) {
	try {
	LocalDate date = LocalDate.of(year, month, day);
	return true; // La date est valide
	} catch (DateTimeException e) {
	return false; // La date est invalide
	}
	}

	public List<DisponibiliteV> getCarAuj() {
		return disponibiliteVDao.getCarAuj();
	}

	public List<DisponibiliteV> getTruckAuj() {
		return disponibiliteVDao.getTruckAuj();
	}

	public List<DisponibiliteV> getMotoAuj() {
		return disponibiliteVDao.getMotoAuj();
	}
	
	public String getCateg(String immat) {
		return disponibiliteVDao.getCateg(immat);
	}

	public List<DisponibiliteV> getDisByDateTp(LocalDate dt, LocalTime temp, String categ) {
		return disponibiliteVDao.getDisByDateTp( dt,  temp,  categ);
	}

	public List<DisponibiliteV> getDisByDate(LocalDate dt, String categ) {
		return disponibiliteVDao.getDisByDate( dt,  categ);
	}
	
	public Pair<Object, Object> StartFin2(String d){
		if(d==null) {
			return new Pair<>(null,null);
		}
		if (d.indexOf("-")==-1){
			return new Pair<>(Integer.parseInt(d), Integer.parseInt(d));
		}else {
		        String[] parts = d.split("-");
		       return new Pair<>(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
		}
	}

	public List<DisponibiliteV> ViewdispVehic(String d, String m, String y,String immat) {
		
		return disponibiliteVDao.ViewdispVehic(StartFin2(d).getKey(), StartFin2(d).getValue(),
				StartFin2(m).getKey(), StartFin2(m).getValue(), StartFin2(y).getKey(), StartFin2(y).getValue(),immat);
	}

	public boolean updateDispoByImmatricule(String im,LocalDate d,LocalTime t) {
		LocalDateTime dateTp=LocalDateTime.of(d, t);
		return disponibiliteVDao.updateDispoByImmatricule(im,dateTp);
	}

}

	
	