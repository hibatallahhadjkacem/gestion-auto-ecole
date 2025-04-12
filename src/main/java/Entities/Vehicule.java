package Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Vehicule {
	private String immatricule;
	private String model;
	private LocalDate dateMiseEnService;
	private int KmTotal;
	private int KmProchEntretient;
	private Categorie catégorie;
	private int Age;
	private List<DisponibiliteV> availability;
	private List<Repartition> repartition;
	private List<Papier> papier;
	
	public Vehicule(String immatricule,String model, LocalDate dateMiseEnService, int kmTotal, int kmProchEntretient,
			Categorie catégorie, int age, List<Repartition> repartition, List<Papier> papier,List<DisponibiliteV> availability) {
		this.immatricule = immatricule;
		this.dateMiseEnService = dateMiseEnService;
		KmTotal = kmTotal;
		KmProchEntretient = kmProchEntretient;
		this.catégorie = catégorie;
		Age = age;
		this.repartition = repartition;
		this.papier = papier;
		this.model=model;
		this.availability=availability;
	}

	public Vehicule(String immatricule, String model,LocalDate dateMiseEnService, int kmTotal, int kmProchEntretient,
			Categorie catégorie, int age,List<DisponibiliteV> availability) {
		this.immatricule = immatricule;
		this.dateMiseEnService = dateMiseEnService;
		KmTotal = kmTotal;
		KmProchEntretient = kmProchEntretient;
		this.catégorie = catégorie;
		Age = age;
		this.model=model;
		this.availability=availability;
	}

	public List<DisponibiliteV> isAvailability() {
		return availability;
	}

	public void setAvailability(List<DisponibiliteV> availability) {
		this.availability = availability;
	}

	public Vehicule(String immatricule, String model, LocalDate dateMiseEnService, int kmTotal, int kmProchEntretient,
			Categorie catégorie, int age) {
		this.immatricule = immatricule;
		this.model = model;
		this.dateMiseEnService = dateMiseEnService;
		KmTotal = kmTotal;
		KmProchEntretient = kmProchEntretient;
		this.catégorie = catégorie;
		Age = age;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Vehicule() {
	}

	public String getImmatricule() {
		return immatricule;
	}

	public void setImmatricule(String immatricule) {
		this.immatricule = immatricule;
	}

	public LocalDate getDateMiseEnService() {
		return dateMiseEnService;
	}

	public void setDateMiseEnService(LocalDate dateMiseEnService) {
		this.dateMiseEnService = dateMiseEnService;
	}

	public int getKmTotal() {
		return KmTotal;
	}

	public void setKmTotal(int kmTotal) {
		KmTotal = kmTotal;
	}

	public int getKmProchEntretient() {
		return KmProchEntretient;
	}

	public void setKmProchEntretient(int kmProchEntretient) {
		KmProchEntretient = kmProchEntretient;
	}

	public Categorie getCatégorie() {
		return catégorie;
	}

	public void setCatégorie(Categorie catégorie) {
		this.catégorie = catégorie;
	}

	public int getAge() {
		return Age;
	}

	public void setAge(int age) {
		Age = age;
	}

	public List<Repartition> getRepartition() {
		return repartition;
	}

	public void setRepartition(List<Repartition> repartition) {
		this.repartition = repartition;
	}

	public List<Papier> getPapier() {
		return papier;
	}

	public void setPapier(List<Papier> papier) {
		this.papier = papier;
	}

	@Override
	public String toString() {
		return "Vehicule [immatricule=" + immatricule + ", model=" + model + ", dateMiseEnService=" + dateMiseEnService
				+ ", KmTotal=" + KmTotal + ", KmProchEntretient=" + KmProchEntretient + ", catégorie=" + catégorie
				+ ", Age=" + Age + ", availability=" + availability + ", repartition=" + repartition + ", papier="
				+ papier + "]";
	}
	
	
	
	
	
	

}
