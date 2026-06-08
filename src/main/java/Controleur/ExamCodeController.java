package Controleur;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.ExamenCode;
import Entities.Res;
import Service.*;
import Dao.ExamenCodeDAO;
import Dao.ExamenConduiteDAO;
import Service.ExamenCodeService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

public class ExamCodeController {
	@FXML
	private DatePicker dateExam;
	@FXML
	private TextField stime;
	@FXML
	private TextField etime;
	@FXML
	private TextField moniteur;
	@FXML
	private TextField condidat;
	@FXML
	private TextField fraisField;
	@FXML
	private RadioButton pass,fail;
	private CondidatService condidatService = new CondidatService();
	private ExamenCodeService examenCodeService = new ExamenCodeService();
	@FXML
	public void initialize() {
	    dateExam.valueProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue != null && newValue.isAfter(LocalDate.now())) {
	            pass.setDisable(true);
	            fail.setDisable(true);
	        } else {
	            pass.setDisable(false);
	            fail.setDisable(false);
	        }
	    });
	}
	@FXML
	private void add() throws NumberFormatException, SQLException {
	    LocalDate date = dateExam.getValue();
	    String startTimeStr = stime.getText();
	    String endTimeStr = etime.getText();
	    String moniteur_id = moniteur.getText();
	    String condidat_num = condidat.getText();
	    Res resultat = null;
	    String frais = fraisField.getText();
	    
	    if (!isValidDate(date)) {
	        showAlert("Error", "Invalid Date!", AlertType.ERROR);
	        return;
	    }
	    LocalTime startTime = null;
	    LocalTime endTime = null;

	    try {
	        startTime = LocalTime.parse(startTimeStr);
	        endTime = LocalTime.parse(endTimeStr);
	    } catch (DateTimeParseException e) {
	        showAlert("Error", "Invalid time format! Please use HH:mm:ss format.", AlertType.ERROR);
	        return;
	    }

	    if (startTime.isAfter(endTime)) {
	        showAlert("Error", "Start time cannot be after end time.", AlertType.ERROR);
	        return;
	    }

	    if (startTime.equals(endTime)) {
	        showAlert("Error", "Start time cannot be the same as end time.", AlertType.ERROR);
	        return;
	    }

	   

	    if (moniteur_id.isEmpty() || !moniteur_id.matches("\\d+") || Integer.parseInt(moniteur_id) < 1) {
	        showAlert("Error", "Instructor id invalid!", AlertType.ERROR);
	        return;
	    }if((ExamenCodeDAO.findMoniteurById(Integer.parseInt(moniteur_id))==null)) {
	    	showAlert("Error", "Instructor does not exist!", AlertType.ERROR);
	    }if(!ExamenCodeDAO.isMoniteurAvailable(Integer.parseInt(moniteur_id),date, startTime,endTime)) {
	    	showAlert("Error", "Instructor is not available!", AlertType.ERROR);
	    	return;
	    }
	    if (condidat_num.isEmpty() || !condidat_num.matches("\\d+") || Integer.parseInt(condidat_num) < 1) {
	        showAlert("Error", "Candidate Number invalid!", AlertType.ERROR);
	        return;
	    }
	    if (condidatService.getCondidatById(Integer.parseInt(condidat_num)) == null) {
	        showAlert("Error", "Candidate number does not exist!", AlertType.ERROR);
	        return;
	    }
	    if (date.isAfter(LocalDate.now())) {
	        resultat = Res.Pending;
	    } else {
	        if (pass.isSelected()) {
	            resultat = Res.Pass;
	        } else if (fail.isSelected()) {
	            resultat = Res.Fail;
	        } else {
	            showAlert("Error", "Please select a result!", AlertType.ERROR);
	            return;
	        }
	    }
	    
	    if (frais.isEmpty()||!frais.matches("\\d+(\\.\\d+)?")||Double.parseDouble(frais) <= 0  || Double.parseDouble(frais) > 1000) {
	        showAlert("Error", "Costs invalid!", AlertType.ERROR);
	        return;
	    }
	    ExamenCodeDAO.updateMoniteurAvailability(Integer.parseInt(moniteur_id), date, endTime);

	    
	    ExamenCode examenCode = new ExamenCode(0, date, Integer.parseInt(moniteur_id), Integer.parseInt(condidat_num), startTime, endTime, resultat, Double.parseDouble(frais));

	    if (examenCodeService.addExam(examenCode)) {
	        showAlert("Success", "Exam added successfully.", AlertType.INFORMATION);
	    } else {
	        showAlert("Error", "Error while saving exam", AlertType.ERROR);
	    }
	}  public static boolean isValidDate(LocalDate date) {
        try {
            return date != null;
        } catch (DateTimeParseException e) {
            return false;
        }
    } 
    @FXML
    private void home() throws IOException {
    	App.setRoot("Home");
    }
    @FXML
    private void Condidat() throws IOException {
    	App.setRoot("page1");
    }
    @FXML
    private void color(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");    }
    @FXML
    private void color2(MouseEvent event) {
    	 ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");    }

    @FXML
    private void back() throws IOException {

        App.setRoot("examen");
    }
    @FXML
    private void Exam() throws IOException {
    	App.setRoot("examen");
    }
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();}
}
