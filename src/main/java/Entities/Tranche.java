package Entities;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Tranche {
	 private int id;
	    private int idPaiement;
	    private int numeroTranche;
	    private LocalDate dateLimite;
	    private BigDecimal montant;
	    private String statut;
	    private LocalDate datePaiementEffectif;
		public Tranche(int id, int idPaiement, int numeroTranche, LocalDate dateLimite, BigDecimal montant, String statut,
				LocalDate datePaiementEffectif) {
			super();
			this.id = id;
			this.idPaiement = idPaiement;
			this.numeroTranche = numeroTranche;
			this.dateLimite = dateLimite;
			this.montant = montant;
			this.statut = statut;
			this.datePaiementEffectif = datePaiementEffectif;
		}
		public Tranche(int idPaiement, int numeroTranche, LocalDate dateLimite, BigDecimal montant, String statut,
				LocalDate datePaiementEffectif) {
			super();
			this.idPaiement = idPaiement;
			this.numeroTranche = numeroTranche;
			this.dateLimite = dateLimite;
			this.montant = montant;
			this.statut = statut;
			this.datePaiementEffectif = datePaiementEffectif;
		}
		
		public Tranche(int id) {
			super();
			this.id = id;
		}
		public Tranche() {}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public int getIdPaiement() {
			return idPaiement;
		}
		public void setIdPaiement(int idPaiement) {
			this.idPaiement = idPaiement;
		}
		public int getNumeroTranche() {
			return numeroTranche;
		}
		public void setNumeroTranche(int numeroTranche) {
			this.numeroTranche = numeroTranche;
		}
		public LocalDate getDateLimite() {
			return dateLimite;
		}
		public void setDateLimite(LocalDate dateLimite) {
			this.dateLimite = dateLimite;
		}
		public BigDecimal getMontant() {
			return montant;
		}
		public void setMontant(BigDecimal montant) {
			this.montant = montant;
		}
		public String getStatut() {
			return statut;
		}
		public void setStatut(String statut) {
			this.statut = statut;
		}
		public LocalDate getDatePaiementEffectif() {
			return datePaiementEffectif;
		}
		public void setDatePaiementEffectif(LocalDate datePaiementEffectif) {
			this.datePaiementEffectif = datePaiementEffectif;
		}
		@Override
		public String toString() {
			return "Tranche (id=" + id + ", idPaiement=" + idPaiement + ", numeroTranche=" + numeroTranche
					+ ", dateLimite=" + dateLimite + ", montant=" + montant + ", statut=" + statut
					+ ", datePaiementEffectif=" + datePaiementEffectif + ")";
		}
		
		
		
		
			


	    
	    
	    
	    
	    
}
