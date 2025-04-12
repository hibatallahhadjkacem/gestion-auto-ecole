package Entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Paiement {
    private int id;
    private int idCandidat;
    private LocalDate  datePaiement;
    private BigDecimal montantTotal;
    private String modePaiement;
    private String candidatNom;
    private String candidatPrenom;
    
	public Paiement(int id, int idCandidat, LocalDate datePaiement, BigDecimal montantTotal, String modePaiement) {
		
		this.id = id;
		this.idCandidat = idCandidat;
		this.datePaiement = datePaiement;
		this.montantTotal = montantTotal;
		this.modePaiement = modePaiement;
		
	}
	public Paiement() {}
	public Paiement(int idCandidat, LocalDate datePaiement, BigDecimal montantTotal, String modePaiement) {
		super();
		this.idCandidat = idCandidat;
		this.datePaiement = datePaiement;
		this.montantTotal = montantTotal;
		this.modePaiement = modePaiement;
	
	}
	
	
	public Paiement(int id, int idCandidat, LocalDate datePaiement, BigDecimal montantTotal, String modePaiement,
			String candidatNom, String candidatPrenom) {
		super();
		this.id = id;
		this.idCandidat = idCandidat;
		this.datePaiement = datePaiement;
		this.montantTotal = montantTotal;
		this.modePaiement = modePaiement;
		this.candidatNom = candidatNom;
		this.candidatPrenom = candidatPrenom;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdCandidat() {
		return idCandidat;
	}
	public void setIdCandidat(int idCandidat) {
		this.idCandidat = idCandidat;
	}
	public LocalDate getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(LocalDate datePaiement) {
		this.datePaiement = datePaiement;
	}
	public BigDecimal getMontantTotal() {
		return montantTotal;
	}
	public void setMontantTotal( BigDecimal montantTotal) {
		this.montantTotal = montantTotal;
	}
	public String getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}
	public String getCandidatNom() {
		return candidatNom;
	}
	public void setCandidatNom(String candidatNom) {
		this.candidatNom = candidatNom;
	}
	public String getCandidatPrenom() {
		return candidatPrenom;
	}
	public void setCandidatPrenom(String candidatPrenom) {
		this.candidatPrenom = candidatPrenom;
	}
	
	
	
	
	
	
	
	
		


	
		
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	}

	
	
	
	
	
	
	


