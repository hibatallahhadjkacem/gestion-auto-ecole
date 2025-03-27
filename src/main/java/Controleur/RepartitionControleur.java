package Controleur;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import Auto_Ecolee.Auto_Ecolee.App;
import Entities.Repartition;
import Service.RepartitionService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class RepartitionControleur {
	@FXML
	private TextField immat;
	@FXML
	private DatePicker date;
	@FXML
	private TextField descrip;
	@FXML
	private TextField cost;
	@FXML
	private TextField proof;
    @FXML
    private Text er1;
    @FXML
    private Text er2;
    @FXML
    private Text er3;
    @FXML
    private Text er4;
    @FXML
    private Text er5;
	
	private RepartitionService repartitionService=new RepartitionService();
	
	
	

	
	@FXML
	private void next() throws IOException {
		String im = immat.getText().trim();
		LocalDate dt = date.getValue();
		String desc = descrip.getText().trim();
		String costt=cost.getText().trim();
		String prooff=proof.getText().trim();

		if(!im.isEmpty() && !desc.isEmpty() && dt!=null  && !costt.isEmpty() && !prooff.isEmpty() && isFloat(costt)) {
			float cost=Float.parseFloat(costt);
			if(repartitionService.addRepartition(dt,desc,cost,prooff,im)) {
				App.setRoot("Choix");
}
		}else {
			if (desc.isEmpty()) { 
				er2.setText("You must fill in the description");
			}
			if(dt==null) {
				er1.setText("You must select a date");
			}
			if(costt.isEmpty() || !isFloat(costt)) {
				er3.setText("You must fill in the cost with a number or a float");
			}
			if (prooff.isEmpty()) { 
				er4.setText("You must fill in the URL that leads to the proof");
			}
			if (im.isEmpty()) { 
				er5.setText("You must fill in the registration number");
			}
			
		
		}}
	@FXML
	private void back() throws IOException {
		App.setRoot("Choix");
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
    
    public boolean isFloat(String str) {
        String regex = "^[-+]?\\d*\\.?\\d+$"; 
        return str.matches(regex);
    }
    
	public List<Repartition> getReparationsByImmat(String immatricule){
		return repartitionService.getReparationsByImmat(immatricule);
	}

}
