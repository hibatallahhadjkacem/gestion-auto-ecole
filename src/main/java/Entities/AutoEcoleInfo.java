package Entities;

public class AutoEcoleInfo {
		private String nom;
		private String email;
		private int tel;
		private String adress;
		public AutoEcoleInfo(String nom, String email, int tel, String adress) {
			super();
			this.nom = nom;
			this.email = email;
			this.tel = tel;
			this.adress = adress;
		}
		public AutoEcoleInfo() {

		}
		public String getNom() {
			return nom;
		}
		public void setNom(String nom) {
			this.nom = nom;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public int getTel() {
			return tel;
		}
		public void setTel(int tel) {
			this.tel = tel;
		}
		public String getAdress() {
			return adress;
		}
		public void setAdress(String adress) {
			this.adress = adress;
		}
		@Override
		public String toString() {
			return "AutoEcoleInfo [nom=" + nom + ", email=" + email + ", tel=" + tel + ", adress=" + adress + "]";
		}
		}

