package Controleur;

import java.io.IOException;
import java.time.LocalTime;

import Auto_Ecolee.Auto_Ecolee.App;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class AddTempTravVControleur {
	@FXML
	private TextField immat;
	@FXML
	private TextField day;
	@FXML
	private TextField month;
	@FXML
	private TextField year;
	@FXML
	private TextField MFH;
	@FXML
	private TextField MFM;
	@FXML
	private TextField MTH;
	@FXML
	private TextField MTM;
	@FXML
	private TextField EFH;
	@FXML
	private TextField EFM;
	@FXML
	private TextField ETH;
	@FXML
	private TextField ETM;
	@FXML
	private TextField SH;
	@FXML
	private TextField SM;
	@FXML
    private Text er1;
    @FXML
    private Text er2;
    @FXML
    private Text er3;

	
	private DisponibiliteVControleur disponibiliteVControleur=new DisponibiliteVControleur();
	
	@FXML
	private void add() throws IOException {
		String im=immat.getText().trim();
		String d=day.getText().trim();
		String m=month.getText().trim();
		String y=year.getText().trim();
		String mfh=MFH.getText().trim();
		String mfm=MFM.getText().trim();
		String mth=MTH.getText().trim();
		String mtm=MTM.getText().trim();
		String efh=EFH.getText().trim();
		String efm=EFM.getText().trim();
		String eth=ETH.getText().trim();
		String etm=ETM.getText().trim();
		String sh=SH.getText().trim();
		String sm=SH.getText().trim();


		if(im.isEmpty()) {
			afficherMessageTemporaire(er1, "You must fill in the registration number", 2);
		}else if(disponibiliteVControleur.rechImmat(im)==0) {
			afficherMessageTemporaire(er1, "This vehicle registration is not found", 2);

		}else if(disponibiliteVControleur.rechImmat(im)==-1) {
			afficherMessageTemporaire(er1, "An error in the database", 2);

		}else {
			if(d.isEmpty()|| !isValidDateFormat(d) ) {
				afficherMessageTemporaire(er2, "You must fill in the days correctly", 2);
			}else if (m.isEmpty()|| !isValidMonthFormat(m)) {
				afficherMessageTemporaire(er2, "You must fill in the months correctly", 2);
			}else if (y.isEmpty()|| !isValidYearFormat(y)) {
				afficherMessageTemporaire(er2, "You must fill in the years correctly", 2);
			}else if (mfh.isEmpty()|| mfm.isEmpty()||mth.isEmpty()||mtm.isEmpty()|| !isValidHFormat(mfh)||!isValidHFormat(mth) 
					|| Integer.parseInt(mfh)>Integer.parseInt(mth)||(Integer.parseInt(mfh)==Integer.parseInt(mth)&&Integer.parseInt(mfm)>Integer.parseInt(mtm))
					|| !isValidMFormat(mfh)||!isValidMFormat(mfm) ) {
				afficherMessageTemporaire(er3, "You must fill in the Morning vehicle availability correctly", 2);
			}else if (efh.isEmpty()|| efm.isEmpty()||eth.isEmpty()||etm.isEmpty()|| !isValidH2Format(efh)||!isValidH2Format(eth) 
					|| Integer.parseInt(efh)>Integer.parseInt(eth)||(Integer.parseInt(efh)==Integer.parseInt(eth)&&Integer.parseInt(efm)>Integer.parseInt(etm))
					|| !isValidMFormat(efm)||!isValidMFormat(etm) ) {
				afficherMessageTemporaire(er3, "You must fill in the Evening vehicle availability correctly", 2);
			}else if (sh.isEmpty()|| sm.isEmpty()|| !isValidHFormat(sh)|| !isValidMFormat(sm) ) {
				afficherMessageTemporaire(er3, "You must fill in the The scheduled duration of a driving session correctly", 2);
			}
			else {
				LocalTime mornStart=LocalTime.of(Integer.parseInt(mfh), Integer.parseInt(mfm));
				LocalTime mornFin=LocalTime.of(Integer.parseInt(mth), Integer.parseInt(mtm));
				LocalTime evnStart=LocalTime.of(Integer.parseInt(efh), Integer.parseInt(efm));
				LocalTime evnFin=LocalTime.of(Integer.parseInt(eth), Integer.parseInt(etm));
				if(disponibiliteVControleur.addTemptrav( im,d,m,y, Integer.parseInt(sh),Integer.parseInt(sm),mornStart,mornFin,evnStart,evnFin)) {
				App.setRoot("DisponibiliteV");}else {
					afficherMessageTemporaire(er3, "An error in the database", 2);
				}
				}
		}
	}

	// Créer un Timeline pour effacer le texte après tp
	private void afficherMessageTemporaire(Text textElement, String message, int duree) {
	    textElement.setText(message);
	    Timeline timeline = new Timeline(
	        new KeyFrame(Duration.seconds(duree), e -> textElement.setText(""))
	    );
	    timeline.setCycleCount(1); // Exécuter une seule fois
	    timeline.play(); // Lancer le timer
	}
	
	public static boolean isValidHFormat(String str) {
	    
	    if (str.matches("\\d+")) {
	        int num = Integer.parseInt(str);
	        return num >= 1 && num <= 12;  
	    }
	    return false;
	}
	public static boolean isValidH2Format(String str) {
	    
	    if (str.matches("\\d+")) {
	        int num = Integer.parseInt(str);
	        return num >= 13 && num <= 23;  
	    }
	    return false;
	}
	public static boolean isValidMFormat(String str) {
	    
	    if (str.matches("\\d+")) {
	        int num = Integer.parseInt(str);
	        return num >= 0 && num <= 59;  
	    }
	    return false;
	}
	
	public static boolean isValidMonthFormat(String str) {
	    
	    if (str.matches("\\d+")) {
	        int num = Integer.parseInt(str);
	        return num >= 1 && num <= 12;  
	    }
	    
	    
	    if (str.matches("\\d+-\\d+")) {
	        String[] parts = str.split("-");
	        try {
	            int start = Integer.parseInt(parts[0]);
	            int end = Integer.parseInt(parts[1]);
	            return start >= 1 && start <= 12 && end >= 1 && end <= 12 && start <= end;
	        } catch (NumberFormatException e) {
	            return false;  
	        }
	    }
	    
	    return false;  
	}
	public static boolean isValidYearFormat(String str) {
	    
	    if (str.matches("\\d+")) {
	        int num = Integer.parseInt(str);
	        return num >= 2025;  
	    }
	    
	    
	    if (str.matches("\\d+-\\d+")) {
	        String[] parts = str.split("-");
	        try {
	            int start = Integer.parseInt(parts[0]);
	            int end = Integer.parseInt(parts[1]);
	            return start >= 2025  && end >= 2025 && start <= end;
	        } catch (NumberFormatException e) {
	            return false;  
	        }
	    }
	    
	    return false;  
	}

	public static boolean isValidDateFormat(String str) {
	    // ex "12"
	    if (str.matches("\\d+")) {
	        int num = Integer.parseInt(str);
	        return num >= 1 && num <= 31;  // Vérifie que le nombre est entre 1 et 31
	    }
	    
	    // ex "16-20"
	    if (str.matches("\\d+-\\d+")) {
	        String[] parts = str.split("-");
	        try {
	            int start = Integer.parseInt(parts[0]);
	            int end = Integer.parseInt(parts[1]);
	            return start >= 1 && start <= 31 && end >= 1 && end <= 31 && start <= end;
	        } catch (NumberFormatException e) {
	            return false;  //la chaîne n'est pas valide
	        }
	    }
	    
	    return false;  // Si ce n'est pas un nombre 
	}
	
	@FXML
	private void back() throws IOException {
		App.setRoot("DisponibiliteV");
	}
	@FXML
	private void scCode() throws IOException {
		App.setRoot("SeanceCode"); 
	}
	
	@FXML
	private void scConduit() throws IOException {
		App.setRoot("SeanceConduite"); 
	}
	@FXML
	private void home() throws IOException {
		App.setRoot("Home"); 
	}
	
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }

}
