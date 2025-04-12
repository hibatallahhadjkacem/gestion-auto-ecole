package Entities;

import java.time.LocalDateTime;


public class DisponibiliteV {
	private LocalDateTime dateTime;
	private boolean dispo;
	private String immatricule;

	


	public DisponibiliteV(LocalDateTime dateTime, boolean dispo, String immatricule) {
		super();
		this.dateTime = dateTime;
		this.dispo = dispo;
		this.immatricule = immatricule;
	}

	public String getImmatricule() {
		return immatricule;
	}

	public void setImmatricule(String immatricule) {
		this.immatricule = immatricule;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public boolean isDispo() {
		return dispo;
	}

	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}



	@Override
	public String toString() {
		return "DisponibiliteV [dateTime=" + dateTime + ", dispo=" + dispo + ", immatricule=" + immatricule + "]";
	}
	


	
	
	
	
	
	
	
	
	
	
	
}
