package Entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class SeanceCode {
	private int num;
	private LocalDate date;
	private LocalTime temp;
	
	private int idMoniteur;
	private int idcondidat;
	
	
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
	
	public SeanceCode(int num, LocalDate date, LocalTime temp, int idMoniteur, int idcondidat) {
		super();
		this.num = num;
		this.date = date;
		this.temp = temp;
		this.idMoniteur = idMoniteur;
		this.idcondidat = idcondidat;
		
	}
	public SeanceCode(int num, LocalDate date, LocalTime temp) {
		super();
		this.num = num;
		this.date = date;
		this.temp = temp;
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
	@Override
	public String toString() {
		return "SeanceCode [num=" + num + ", date=" + date + ", temp=" + temp + ", idMoniteur=" + idMoniteur
				+ ", idcondidat=" + idcondidat + "]";
	}
	
	

}
