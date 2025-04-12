package Entities;

import java.sql.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.time.LocalDate;
import java.time.LocalTime;

public class Disponibilite {
	
	
	private int id;
	private int moniteurId;
	private LocalDate  sessionDate ;
	private LocalTime  startTime;
	private LocalTime  endTime;
	private Boolean availability;
	private BooleanProperty selected = new SimpleBooleanProperty(false);
	
	private Disponibilite(int id, int moniteurId, LocalDate sessionDate, LocalTime startTime, LocalTime endTime,
			Boolean availability) {
		super();
		this.id = id;
		this.moniteurId = moniteurId;
		this.sessionDate = sessionDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.availability = availability;
		this.selected.set(false);
	}
	
	
	public Disponibilite() {
		this.selected.set(false);
	}
	
	
	public Disponibilite(int moniteurId, LocalDate sessionDate, LocalTime startTime, LocalTime endTime,
			Boolean availability) {
		super();
		this.moniteurId = moniteurId;
		this.sessionDate = sessionDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.availability = availability;
		this.selected.set(false);
	}
	
	


    // Getters et Setters
    public BooleanProperty selectedProperty() {
        return selected;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);}
	
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMoniteurId() {
		return moniteurId;
	}
	public void setMoniteurId(int moniteurId) {
		this.moniteurId = moniteurId;
	}
	public LocalDate getSessionDate() {
		return sessionDate;
	}
	public void setSessionDate(LocalDate sessionDate) {
		this.sessionDate = sessionDate;
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
	public Boolean getAvailability() {
		return availability;
	}
	public void setAvailability(Boolean availability) {
		this.availability = availability;
	}
	@Override
	public String toString() {
		return "Disponibilite (id=" + id + ", moniteurId=" + moniteurId + ", sessionDate=" + sessionDate
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", availability=" + availability + ")";
	}
	
	
	
	

	
	
	
	
	

}
