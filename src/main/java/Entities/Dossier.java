package Entities;

import java.util.Arrays;

public class Dossier {
	
	private byte[] cin;
	private byte[] photo;
	private byte[] certif;
	private int numeroCondidat;
	public Dossier(byte[] cin, byte[] photo, byte[] certif, int numeroCondidat) {
		super();
		this.cin = cin;
		this.photo = photo;
		this.certif = certif;
		this.numeroCondidat = numeroCondidat;
	}
	public byte[] getCin() {
		return cin;
	}
	public void setCin(byte[] cin) {
		this.cin = cin;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public byte[] getCertif() {
		return certif;
	}
	public void setCertif(byte[] certif) {
		this.certif = certif;
	}
	public int getNumeroCondidat() {
		return numeroCondidat;
	}
	public void setNumeroCondidat(int numeroCondidat) {
		this.numeroCondidat = numeroCondidat;
	}
	@Override
	public String toString() {
		return "Dossier [cin=" + Arrays.toString(cin) + ", photo=" + Arrays.toString(photo) + ", certif="
				+ Arrays.toString(certif) + ", numeroCondidat=" + numeroCondidat + "]";
	}
	
}
