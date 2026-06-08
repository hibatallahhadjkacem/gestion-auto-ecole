package Controleur;

import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import Entities.ExamenConduite;

public class Map2WindowController {

    @FXML
    private WebView mapView;

    private WebEngine webEngine;

    @FXML
    public void initialize() {
        webEngine = mapView.getEngine();
        // Load the map HTML
        webEngine.load(getClass().getResource("/projet/nermine/map2.html").toExternalForm());
    }

    public void setLocationFromExam(ExamenConduite exam) {
        double lat = exam.getLatitude();
        double lng = exam.getLongitude();
        String address = exam.getAdress();

        // Escape single quotes in the address string to avoid breaking JS
        String safeAddress = address.replace("'", "\\'");

        // Wait until the web page is fully loaded before executing JavaScript
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            switch (newState) {
                case SUCCEEDED:
                    String script = String.format("setLocation(%f, %f, '%s');", lat, lng, safeAddress);
                    webEngine.executeScript(script);
                    break;
                default:
                    break;
            }
        });
    }
}

