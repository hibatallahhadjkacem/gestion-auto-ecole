package Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class ExamenCode {
	private static int numero;
	private LocalDate date;
	private int moniteur_id;
	private int condidat_num;
	private LocalTime startTime;
	private LocalTime endTime;
	private Res resultat;
	private double frais;
	public ExamenCode(int numero, LocalDate date, int moniteur_id, int condidat_num, LocalTime startTime,
			LocalTime endTime, Res resultat, double frais) {
		super();
		this.numero = numero;
		this.date = date;
		this.moniteur_id = moniteur_id;
		this.condidat_num = condidat_num;
		this.startTime = startTime;
		this.endTime = endTime;
		this.resultat = resultat;
		this.frais = frais;
	}
	public static int getNumero() {
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
	public int getMoniteur_id() {
		return moniteur_id;
	}
	public void setMoniteur_id(int moniteur_id) {
		this.moniteur_id = moniteur_id;
	}
	public int getCondidat_num() {
		return condidat_num;
	}
	public void setCondidat_num(int condidat_num) {
		this.condidat_num = condidat_num;
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
	public Res getResultat() {
		return resultat;
	}
	public void setResultat(Res resultat) {
		this.resultat = resultat;
	}
	public double getFrais() {
		return frais;
	}
	public void setFrais(double frais) {
		this.frais = frais;
	}
	@Override
	public String toString() {
		return "ExamenCode [numero=" + numero + ", date=" + date + ", moniteur_id=" + moniteur_id + ", condidat_num="
				+ condidat_num + ", startTime=" + startTime + ", endTime=" + endTime + ", resultat=" + resultat
				+ ", frais=" + frais + "]";
	}
	
	

	
}