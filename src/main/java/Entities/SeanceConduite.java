package Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class SeanceConduite {
	private int num;
	private LocalDate date;
	private LocalTime temp;
	private String lieuRDV;
	private double latitude;
	private double longitude;
	
	private int idMoniteur;
	private int idcondidat;
	private String idVehicule;
	
	public SeanceConduite(int num, LocalDate date, LocalTime temp, String lieuRDV, double latitude, double longitude,
			int idMoniteur, int idcondidat, String idVehicule) {
		super();
		this.num = num;
		this.date = date;
		this.temp = temp;
		this.lieuRDV = lieuRDV;
		this.latitude = latitude;
		this.longitude = longitude;
		this.idMoniteur = idMoniteur;
		this.idcondidat = idcondidat;
		this.idVehicule = idVehicule;
	}

	public SeanceConduite(int num, LocalDate date, LocalTime temp, String lieuRDV, double latitude, double longitude) {
		super();
		this.num = num;
		this.date = date;
		this.temp = temp;
		this.lieuRDV = lieuRDV;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public SeanceConduite() {
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTemp() {
		return temp;
	}

	public void setTemp(LocalTime temp) {
		this.temp = temp;
	}

	public String getLieuRDV() {
		return lieuRDV;
	}

	public void setLieuRDV(String lieuRDV) {
		this.lieuRDV = lieuRDV;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public int getIdMoniteur() {
		return idMoniteur;
	}

	public void setIdMoniteur(int idMoniteur) {
		this.idMoniteur = idMoniteur;
	}

	public int getIdcondidat() {
		return idcondidat;
	}

	public void setIdcondidat(int idcondidat) {
		this.idcondidat = idcondidat;
	}

	public String getIdVehicule() {
		return idVehicule;
	}

	public void setIdVehicule(String idVehicule) {
		this.idVehicule = idVehicule;
	}

	@Override
	public String toString() {
		return "SeanceConduite [num=" + num + ", date=" + date + ", temp=" + temp + ", lieuRDV=" + lieuRDV
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", idMoniteur=" + idMoniteur + ", idcondidat="
				+ idcondidat + ", idVehicule=" + idVehicule + "]";
	}

	
	

}
