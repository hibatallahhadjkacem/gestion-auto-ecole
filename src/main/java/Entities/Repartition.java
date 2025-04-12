package Entities;

import java.time.LocalDate;

public class Repartition {
	private LocalDate date;
	private String description;
	private double cout;
	private String preuve;
	private String immatricule;

	
	public Repartition(LocalDate date, String description, double cout, String preuve,String immatricule) {
		this.date = date;
		this.description = description;
		this.cout = cout;
		this.preuve = preuve;
		this.immatricule=immatricule;
	}
	
	

	public String getImmatricule() {
		return immatricule;
	}



	public void setImmatricule(String immatricule) {
		this.immatricule = immatricule;
	}



	public Repartition() {
	}



	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public double getCout() {
		return cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
	}

	public String getPreuve() {
		return preuve;
	}

	public void setPreuve(String preuve) {
		this.preuve = preuve;
	}

	@Override
	public String toString() {
		return "Repartition [date=" + date + ", description=" + description + ", cout=" + cout + ", preuve=" + preuve
				+ ", immatricule=" + immatricule + "]";
	}
	
	

}
