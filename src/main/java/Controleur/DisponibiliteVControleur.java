package Controleur;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.DisponibiliteV;
import Service.DisponibiliteVService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DisponibiliteVControleur implements Initializable{
	@FXML
	private TableView<DisponibiliteV> tableC;
	@FXML
	private TableColumn<DisponibiliteV, LocalTime> Ctp;
	@FXML
	private TableColumn<DisponibiliteV, String> Cimmat;
	@FXML
	private TableView<DisponibiliteV> tableT;
	@FXML
	private TableColumn<DisponibiliteV, LocalTime> Ttp;
	@FXML
	private TableColumn<DisponibiliteV, String> Timmat;
	@FXML
	private TableView<DisponibiliteV> tableM;
	@FXML
	private TableColumn<DisponibiliteV, LocalTime> Mtp;
	@FXML
	private TableColumn<DisponibiliteV, String> Mimmat;
	@FXML
	private DatePicker date;
	@FXML
	private TextField tp;
	@FXML
	private Text text;
	@FXML
    private Text er1;
    @FXML
    private Text er2;
	
	private ObservableList<DisponibiliteV> disponibiliteVListC = FXCollections.observableArrayList();
	private ObservableList<DisponibiliteV> disponibiliteVListT = FXCollections.observableArrayList();
	private ObservableList<DisponibiliteV> disponibiliteVListM = FXCollections.observableArrayList();


	
	private DisponibiliteVService disponibiliteVService=new DisponibiliteVService();
	
	public void chargerDisponibiliteVC() {
        if (disponibiliteVService != null) {
            List<DisponibiliteV> disponibiliteVs = disponibiliteVService.getCarAuj();
            disponibiliteVListC.clear();
            disponibiliteVListC.addAll(disponibiliteVs);
            tableC.refresh(); //  mettre à jour l'affichage
        } 
	}
	public void chargerDisponibiliteVT() {
        if (disponibiliteVService != null) {
            List<DisponibiliteV> disponibiliteVs = disponibiliteVService.getTruckAuj();
            disponibiliteVListT.clear();
            disponibiliteVListT.addAll(disponibiliteVs);
            tableT.refresh(); 
        } 
	}
	
	public void chargerDisponibiliteVM() {
        if (disponibiliteVService != null) {
            List<DisponibiliteV> disponibiliteVs = disponibiliteVService.getMotoAuj();
            disponibiliteVListM.clear();
            disponibiliteVListM.addAll(disponibiliteVs);
            tableM.refresh(); 
        } 
	}
	
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		//car
	    // Pour afficher uniquement l'heure depuis LocalDateTime
	    Ctp.setCellValueFactory(cellData -> 
	        new javafx.beans.property.SimpleObjectProperty<>(
	            cellData.getValue().getDateTime().toLocalTime()
	        )
	    );        
	    Cimmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
	    tableC.setItems(disponibiliteVListC);
	    chargerDisponibiliteVC();
	    //truck
	    Ttp.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleObjectProperty<>(
            cellData.getValue().getDateTime().toLocalTime()
        )
	    );        
	    Timmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
	    tableT.setItems(disponibiliteVListT);
	    chargerDisponibiliteVT();
	    //moto
	    Mtp.setCellValueFactory(cellData -> 
        new javafx.beans.property.SimpleObjectProperty<>(
            cellData.getValue().getDateTime().toLocalTime()
        )
	    );        
	    Mimmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
	    tableM.setItems(disponibiliteVListM);
	    chargerDisponibiliteVM();
	    
	}
	////////
	public void chargerDisByDateM(LocalDate dt) {
        if (disponibiliteVService != null) {
            List<DisponibiliteV> disponibiliteVs =disponibiliteVService.getDisByDate(dt,"Motorcycle");
            disponibiliteVListM.clear();
            disponibiliteVListM.addAll(disponibiliteVs);
            tableM.refresh(); 
        } 
	}
	
	public void chargerDisByDateT(LocalDate dt) {
        if (disponibiliteVService != null) {
            if (disponibiliteVService != null) {
                List<DisponibiliteV> disponibiliteVs =disponibiliteVService.getDisByDate(dt,"Truck");
                disponibiliteVListT.clear();
                disponibiliteVListT.addAll(disponibiliteVs);
                tableT.refresh();
            }}
	}
	
	public void chargerDisByDateC(LocalDate dt) {
        if (disponibiliteVService != null) {
            List<DisponibiliteV> disponibiliteVs =disponibiliteVService.getDisByDate(dt,"Car");
            disponibiliteVListC.clear();
            disponibiliteVListC.addAll(disponibiliteVs);
            tableC.refresh(); 
        } 
	}
	////
	
	public void chargerDisByDateTpM(LocalDate dt,LocalTime temp) {
        if (disponibiliteVService != null) {
            List<DisponibiliteV> disponibiliteVs =disponibiliteVService.getDisByDateTp(dt,temp,"Motorcycle");
            disponibiliteVListM.clear();
            disponibiliteVListM.addAll(disponibiliteVs);
            tableM.refresh(); 
        } 
	}
	
	public void chargerDisByDateTpT(LocalDate dt,LocalTime temp) {
        if (disponibiliteVService != null) {
            if (disponibiliteVService != null) {
                List<DisponibiliteV> disponibiliteVs =disponibiliteVService.getDisByDateTp(dt,temp,"Truck");
                disponibiliteVListT.clear();
                disponibiliteVListT.addAll(disponibiliteVs);
                tableT.refresh();
            }}
	}
	
	public void chargerDisByDateTpC(LocalDate dt,LocalTime temp) {
        if (disponibiliteVService != null) {
            List<DisponibiliteV> disponibiliteVs =disponibiliteVService.getDisByDateTp(dt,temp,"Car");
            disponibiliteVListC.clear();
            disponibiliteVListC.addAll(disponibiliteVs);
            tableC.refresh(); 
        } 
	}
	
	////
	
	@FXML
	private void show()throws IOException{
		String temp=tp.getText().trim();
		LocalDate dt=date.getValue();
		
		if(!temp.isEmpty()&& convertToLocalTime(temp)==null) {
			afficherMessageTemporaire(er1, "You must fill in the time correctly hh:mm", 2);
		}else if(!temp.isEmpty()&&dt==null) {
			afficherMessageTemporaire(er1, "In this case, you must fill in the date", 2);
		}
		else if(temp.isEmpty()&&dt!=null) {
			text.setText("The vehicles that are available on "+dt.toString());
			//car
		    Ctp.setCellValueFactory(cellData -> 
		        new javafx.beans.property.SimpleObjectProperty<>(
		            cellData.getValue().getDateTime().toLocalTime()
		        )
		    );        
		    Cimmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
		    tableC.setItems(disponibiliteVListC);
		    chargerDisByDateC( dt);
		    //truck
		    Ttp.setCellValueFactory(cellData -> 
	        new javafx.beans.property.SimpleObjectProperty<>(
	            cellData.getValue().getDateTime().toLocalTime()
	        )
		    );        
		    Timmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
		    tableT.setItems(disponibiliteVListT);
		    chargerDisByDateT( dt);
		    //moto
		    Mtp.setCellValueFactory(cellData -> 
	        new javafx.beans.property.SimpleObjectProperty<>(
	            cellData.getValue().getDateTime().toLocalTime()
	        )
		    );        
		    Mimmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
		    tableM.setItems(disponibiliteVListM);
		    chargerDisByDateM( dt);
			
		}else if(!temp.isEmpty()&&dt!=null) {
			text.setText("The vehicles that are available on "+dt.toString()+" at "+temp);
			//car
		    Ctp.setCellValueFactory(cellData -> 
		        new javafx.beans.property.SimpleObjectProperty<>(
		            cellData.getValue().getDateTime().toLocalTime()
		        )
		    );        
		    Cimmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
		    tableC.setItems(disponibiliteVListC);
		    chargerDisByDateTpC( dt,convertToLocalTime(temp));
		    //truck
		    Ttp.setCellValueFactory(cellData -> 
	        new javafx.beans.property.SimpleObjectProperty<>(
	            cellData.getValue().getDateTime().toLocalTime()
	        )
		    );        
		    Timmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
		    tableT.setItems(disponibiliteVListT);
		    chargerDisByDateTpT( dt,convertToLocalTime(temp));
		    //moto
		    Mtp.setCellValueFactory(cellData -> 
	        new javafx.beans.property.SimpleObjectProperty<>(
	            cellData.getValue().getDateTime().toLocalTime()
	        )
		    );        
		    Mimmat.setCellValueFactory(new PropertyValueFactory<>("immatricule"));
		    tableM.setItems(disponibiliteVListM);
		    chargerDisByDateTpM( dt,convertToLocalTime(temp));
			
		}
		
		
	}
	
    // Method to validate and convert the time
	public LocalTime convertToLocalTime(String timeString) {
	    // Remove spaces
	    timeString = timeString.trim();

	    //  HH:mm
	    Pattern pattern = Pattern.compile("^(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9])$");
	    Matcher matcher = pattern.matcher(timeString);

	    if (matcher.matches()) {
	        return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
	    } else {
	        return null;
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
	public int rechImmat(String immat) {
		return disponibiliteVService.rechImmat(immat);
	}
	
	public boolean addTemptrav(String im, String d,String m,String y,int sh,int sm
			,LocalTime mornStart,LocalTime mornFin,LocalTime evnStart,LocalTime evnFin) {
		return disponibiliteVService.addTemptrav(im, d, m, y,sh,sm, mornStart, mornFin, evnStart, evnFin);
	}
	
	public List<DisponibiliteV> ViewdispVehic(String d, String m, String y,String immat) {
		// TODO Auto-generated method stub
		return disponibiliteVService.ViewdispVehic( d,  m,  y,immat);
	}
	
	@FXML
	private void add() throws IOException {
		App.setRoot("AddTempTravV");
	}
	@FXML
	private void update() throws IOException {
		App.setRoot("TempTravV");
	}
	@FXML
	private void back() throws IOException {
		App.setRoot("Choix");
	}
	@FXML
	private void home() throws IOException {
		App.setRoot("Home"); 
	}
	
	@FXML
	private void scConduit() throws IOException {
		App.setRoot("SeanceConduite"); 
	}
	@FXML
	private void scCode() throws IOException {
		App.setRoot("SeanceCode"); 
	}
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }
	public boolean updateDispoByImmatricule(String im,LocalDate d,LocalTime t) {
		 return disponibiliteVService.updateDispoByImmatricule(im,d,t) ;
	}


}
