package Service;

import java.time.LocalDate;
import java.util.List;

import Dao.PapierDao;
import Entities.Papier;
import Entities.Repartition;
import Entities.Type;

public class PapierService {
	
	private PapierDao papierDao=new PapierDao();
	
	public List<Papier> getPapiersByImmat(String immatricule){
		return papierDao.getPapiersByImmat(immatricule);
	}
	public boolean addTaxSticker(String immat,float cost,LocalDate date) {
		Type type=Type.VehicleTaxSticker;
		LocalDate dateProch=date.plusYears(1);
		Papier papier=new Papier(type, cost, dateProch, date,immat);
		return papierDao.ajouterPapier(papier);
	}
	public boolean addInseurance(String immat,float cost,LocalDate date,String typeP) {
		Type type=Type.Insurance;
		LocalDate dateProch=null;
		if(typeP=="once a year") {
			dateProch=date.plusYears(1);
		}else if(typeP=="every six months") {
			dateProch=date.plusMonths(6);
		}else {
			dateProch=date.plusMonths(3);
		}
		Papier papier=new Papier(type, cost, dateProch, date,immat);
		return papierDao.ajouterPapier(papier);
	}
	
	public boolean addOilChange(String immat,float cost,LocalDate date) {
		Type type=Type.OilChange;
		LocalDate dateProch=null;
		if(papierDao.getCateg(immat)=="Motorcycle") {
			dateProch=date.plusMonths(6);
		}else {
			dateProch=date.plusYears(1);
		}
		Papier papier=new Papier(type, cost, dateProch, date,immat);
		return papierDao.ajouterPapier(papier);
	}
	
	public boolean addInspection(String immat,float cost,LocalDate date, String weight,String Ecapacite) {
		Type type=Type.VehicleInspection;
		LocalDate dateProch=null;
		if(papierDao.getCateg(immat).equals("Car")) {
			if(papierDao.getAge(immat)<=10 && 4<papierDao.getAge(immat)) {
				dateProch=date.plusYears(2);
			}
			else if(papierDao.getAge(immat)>10 ) {
				dateProch=date.plusYears(1);
			}else if(papierDao.getAge(immat)<= 4) {
				dateProch=date.plusYears(4);
			}
		}
		else if(papierDao.getCateg(immat).equals("Truck")) {
			if(! weight.isEmpty()) {
			if(Float.parseFloat(weight)<=3.5) {
				dateProch=date.plusYears(1);
			}else {
				dateProch=date.plusMonths(6);
			}}
		}else {
			if(! Ecapacite.isEmpty()) {
			if(Float.parseFloat(Ecapacite)>49) {
				dateProch=date.plusYears(2);
			}else {
				dateProch=date.plusYears(1);
			}}
			
		}
		Papier papier=new Papier(type, cost, dateProch, date,immat);
		return papierDao.ajouterPapier(papier);
	}
	

}
