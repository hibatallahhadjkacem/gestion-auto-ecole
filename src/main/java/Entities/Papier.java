package Entities;

import java.time.LocalDate;

public class Papier {
	private Type type;
	private double cout;
	private LocalDate date;
	private LocalDate dateProchain;//calculable
	private String immatricule;
	
	
	

	
	

	public LocalDate getDateProchain() {
		return dateProchain;
	}



	public void setDateProchain(LocalDate dateProchain) {
		this.dateProchain = dateProchain;
	}



	public Papier(Type type, double cout, LocalDate date, LocalDate dateProchain, String immatricule) {
		super();
		this.type = type;
		this.cout = cout;
		this.date = date;
		this.dateProchain = dateProchain;
		this.immatricule = immatricule;
	}



	public String getImmatricule() {
		return immatricule;
	}



	public void setImmatricule(String immatricule) {
		this.immatricule = immatricule;
	}



	public Papier() {
	}



	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public double getCout() {
		return cout;
	}

	public void setCout(double cout) {
		this.cout = cout;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Papier [type=" + type + ", cout=" + cout + ", date=" + date + ", dateProchain=" + dateProchain
				+ ", immatricule=" + immatricule + "]";
	}
	
	
	

}
