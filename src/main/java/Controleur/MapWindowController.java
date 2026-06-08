package Controleur;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class MapWindowController {
    @FXML
    private WebView mapView; 
    private TextField latField; 
    private TextField lngField; 
    @FXML
    private TextField adressField; 

 
    public void setParentFields(TextField lat, TextField lng, TextField adresseField) {
        this.latField = lat;
        this.lngField = lng;
        this.adressField = adresseField;
    }

    @FXML
    public void initialize() {
        WebEngine webEngine = mapView.getEngine();

        String mapUrl = getClass().getResource("/projet/nermine/map.html").toExternalForm();
        webEngine.load(mapUrl);

        // Set up the JavaScript <-> Java communication bridge
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == javafx.concurrent.Worker.State.SUCCEEDED) {
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("javaConnector", new JavaConnector()); // Pass the JavaConnector to JS
            }
        });
    }

    // Java connector class to bridge communication between JS and Java
    public class JavaConnector {

        // This method will be called from JavaScript to pass the location data
        public void setLocation(double lat, double lng, String address) {
            System.out.println("Location received: " + address + " (Lat: " + lat + ", Lng: " + lng + ")");

            // Check if the address field is not null and update it
            if (adressField != null) {
                adressField.setText(address); // Set the address in the text field
            }

            // Set latitude and longitude in the respective text fields
            if (latField != null) {
                latField.setText(String.valueOf(lat)); // Convert double to String and set it
            }
            if (lngField != null) {
                lngField.setText(String.valueOf(lng)); // Convert double to String and set it
            }
        }
    }
}

