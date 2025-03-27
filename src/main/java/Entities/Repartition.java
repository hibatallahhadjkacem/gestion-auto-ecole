package Entities;

import java.time.LocalDate;

public class Repartition {
	private LocalDate date;
	private String discription;
	private double cout;
	private String preuve;
	private String immatricule;

	
	public Repartition(LocalDate date, String discription, double cout, String preuve,String immatricule) {
		this.date = date;
		this.discription = discription;
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

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
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
		return "Repartition [date=" + date + ", discription=" + discription + ", cout=" + cout + ", preuve=" + preuve
				+ ", immatricule=" + immatricule + "]";
	}
	
	

}
