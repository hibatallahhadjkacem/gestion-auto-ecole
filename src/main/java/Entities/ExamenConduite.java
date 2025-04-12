package Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamenConduite {
	private int numero;
	private LocalDate date;
	private LocalTime startTime;
	private LocalTime endTime;
	private int moniteur;
	private int condidat;
	private int vehicule;
	private Res resultat;
	private double longitude;
	private double latitude;
	private String adress;
	private double frais;
	public ExamenConduite(int numero, LocalDate date, LocalTime startTime, LocalTime endTime, int moniteur,
			int condidat, int vehicule, Res resultat, double longitude, double latitude, String adress, double frais) {
		super();
		this.numero = numero;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.moniteur = moniteur;
		this.condidat = condidat;
		this.vehicule = vehicule;
		this.resultat = resultat;
		this.longitude = longitude;
		this.latitude = latitude;
		this.adress = adress;
		this.frais = frais;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public int getMoniteur() {
		return moniteur;
	}
	public void setMoniteur(int moniteur) {
		this.moniteur = moniteur;
	}
	public int getCondidat() {
		return condidat;
	}
	public void setCondidat(int condidat) {
		this.condidat = condidat;
	}
	public int getVehicule() {
		return vehicule;
	}
	public void setVehicule(int vehicule) {
		this.vehicule = vehicule;
	}
	public Res getResultat() {
		return resultat;
	}
	public void setResultat(Res resultat) {
		this.resultat = resultat;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public double getFrais() {
		return frais;
	}
	public void setFrais(double frais) {
		this.frais = frais;
	}
	@Override
	public String toString() {
		return "ExamenConduite [numero=" + numero + ", date=" + date + ", startTime=" + startTime + ", endTime="
				+ endTime + ", moniteur=" + moniteur + ", condidat=" + condidat + ", vehicule=" + vehicule
				+ ", resultat=" + resultat + ", longitude=" + longitude + ", latitude=" + latitude + ", adress="
				+ adress + ", frais=" + frais + "]";
	}
	
}
