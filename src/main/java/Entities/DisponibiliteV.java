package Entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class DisponibiliteV {
	private LocalDateTime dateTime;
	private boolean dispo;
	


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

	public DisponibiliteV(LocalDateTime dateTime, boolean dispo) {
		super();
		this.dateTime = dateTime;
		this.dispo = dispo;
	}

	@Override
	public String toString() {
		return "DisponibiliteV [dateTime=" + dateTime + ", dispo=" + dispo + "]";
	}
	


	
	
	
	
	
	
	
	
	
	
	
}
