package Service;

import java.time.LocalDate;
import java.util.List;

import Dao.PapierDao;
import Entities.Papier;
import Entities.Repartition;
import Entities.Type2;

public class PapierService {
	
	private PapierDao papierDao=new PapierDao();
	
	public List<Papier> getPapiersByImmat(String immatricule){
		return papierDao.getPapiersByImmat(immatricule);
	}
	
	public boolean updateCout(String immat,float cout,String type,LocalDate date) {
		Type2 type2=Type2.valueOf(type);
		return papierDao.updateCoutPapier(immat, cout, date, type2);
	}
	
	public boolean updateTaxSticker(String immat,LocalDate date) {
		Type2 type=Type2.VehicleTaxSticker;
		LocalDate dateProch=date.plusYears(1);
		return papierDao.updateDateProchPapier(immat, dateProch, date, type);

	}
	
	public boolean addTaxSticker(String immat,float cost,LocalDate date) {
		Type2 type=Type2.VehicleTaxSticker;
		LocalDate dateProch=date.plusYears(1);
		Papier papier=new Papier(type, cost, dateProch, date,immat);
		return papierDao.ajouterPapier(papier);
	}
	
	public boolean updateInseurance(String immat,LocalDate date,String typeP) {
		Type2 type=Type2.Insurance;
		LocalDate dateProch=null;
		if(typeP=="once a year") {
			dateProch=date.plusYears(1);
		}else if(typeP=="every six months") {
			dateProch=date.plusMonths(6);
		}else {
			dateProch=date.plusMonths(3);
		}
		return papierDao.updateDateProchPapier(immat, dateProch, date, type);
	}
	
	public boolean addInseurance(String immat,float cost,LocalDate date,String typeP) {
		Type2 type=Type2.Insurance;
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
	
	public boolean updateOilChange(String immat,LocalDate date) {
		Type2 type=Type2.OilChange;
		LocalDate dateProch=null;
		if(papierDao.getCateg(immat)=="Motorcycle") {
			dateProch=date.plusMonths(6);
		}else {
			dateProch=date.plusYears(1);
		}
		return papierDao.updateDateProchPapier(immat, dateProch, date, type);
	}
	
	public boolean addOilChange(String immat,float cost,LocalDate date) {
		Type2 type=Type2.OilChange;
		LocalDate dateProch=null;
		if(papierDao.getCateg(immat)=="Motorcycle") {
			dateProch=date.plusMonths(6);
		}else {
			dateProch=date.plusYears(1);
		}
		Papier papier=new Papier(type, cost, dateProch, date,immat);
		return papierDao.ajouterPapier(papier);
	}
	
	public boolean updateInspection(String immat,LocalDate date, String weight,String Ecapacite) {
		Type2 type=Type2.VehicleInspection;
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
		return papierDao.updateDateProchPapier(immat, dateProch, dateProch, type);
	}
	
	public boolean addInspection(String immat,float cost,LocalDate date, String weight,String Ecapacite) {
		Type2 type=Type2.VehicleInspection;
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
	
	public boolean updatePapierType(String immat,LocalDate date, String weight,String Ecapacite,String typeP,String type) {
		Type2 type2=Type2.valueOf(type);
		if(type2==Type2.Insurance) {
			return updateInseurance(immat, date, typeP);
		}else if (type2==Type2.OilChange) {
			return updateOilChange(immat, date);
		}else if(type2==Type2.VehicleInspection) {
			return updateInspection(immat, date, weight, Ecapacite);
		}else {
			return updateTaxSticker(immat, date);
		}
	}
	

}
