package Entities;

import java.time.LocalDate;
import java.util.List;

public class Condidat {
 private int numero ;
 private String nom;
 private String prenom;
 private LocalDate dateNais;
 private String adresse;
 private int tel;
 private String email;

 private Type typePermi;
 
 
public Condidat(int numero, String nom, String prenom, LocalDate dateNaissance, String adresse, int tel, String email,
		 Type typePermi) {
	super();
	this.numero = numero;
	this.nom = nom;
	this.prenom = prenom;
	this.dateNais = dateNaissance;
	this.adresse = adresse;
	this.tel = tel;
	this.email = email;
	this.typePermi = typePermi;
}


public int getNumero() {
	return numero;
}


public void setNumero(int numero) {
	this.numero = numero;
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


public LocalDate getDateNais() {
	return dateNais;
}


public void setDateNais(LocalDate dateNaissance) {
	this.dateNais = dateNaissance;
}


public String getAdresse() {
	return adresse;
}


public void setAdresse(String adresse) {
	this.adresse = adresse;
}


public int getTel() {
	return tel;
}


public void setTel(int tel) {
	this.tel = tel;
}


public String getEmail() {
	return email;
}


public void setEmail(String email) {
	this.email = email;
}


public Type getTypePermi() {
	return typePermi;
}


public void setTypePermi(Type typePermi) {
	this.typePermi = typePermi;
}


@Override
public String toString() {
	return "Condidat [numero=" + numero + ", nom=" + nom + ", prenom=" + prenom + ", dateNais=" + dateNais
			+ ", adresse=" + adresse + ", tel=" + tel + ", email=" + email + ", typePermi=" + typePermi + "]";
}





 
}

