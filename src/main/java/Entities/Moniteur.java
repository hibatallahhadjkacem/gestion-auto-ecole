package Entities;
import java.time.LocalDate;

public class Moniteur {
	
    private int id;
    private String nom;
    private String prenom;
    private String sexe;  
    private String email;
    private String phone;
    private LocalDate dateRecrutement;

    
    public Moniteur(String nom, String prenom, String sexe, String email, String phone, LocalDate dateRecrutement) {
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.email = email;
        this.phone = phone;
        this.dateRecrutement = dateRecrutement;
    }
    
    

  




	public Moniteur(int id, String nom, String prenom, String sexe, String email, String phone,
			LocalDate dateRecrutement) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
		this.sexe = sexe;
		this.email = email;
		this.phone = phone;
		this.dateRecrutement = dateRecrutement;
	}








	public Moniteur(int id, String nom, String prenom) {
		super();
		this.id = id;
		this.nom = nom;
		this.prenom = prenom;
	}








	public Moniteur() {
		
	}



	public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDateRecrutement() {
        return dateRecrutement;
    }

    public void setDateRecrutement(LocalDate dateRecrutement) {
        this.dateRecrutement = dateRecrutement;
    }
    

   
    public int getId() {
		return id;
	}








	public void setId(int id) {
		this.id = id;
	}








	@Override
    public String toString() {
        return "Moniteur{" +
               "nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               ", sexe='" + sexe + '\'' +
               ", email='" + email + '\'' +
               ", phone='" + phone + '\'' +
               ", dateRecrutement=" + dateRecrutement +
               '}';
    }

}
	
	
	
   
		

	



