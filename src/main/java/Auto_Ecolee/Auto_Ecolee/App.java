package Auto_Ecolee.Auto_Ecolee;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import Controleur.ChoixControleur;
import Controleur.affichageVehiculeControleur;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("secondary"));
        stage.setScene(scene);
        stage.setScene(scene);
        stage.sizeToScene(); // Ajuste la fenêtre à la taille du FXML
        stage.setResizable(true); // Permet le redimensionnement
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        // Vérifier si la scène est attachée à une fenêtre avant de changer la taille
        if (scene.getWindow() instanceof Stage) {
            ((Stage) scene.getWindow()).sizeToScene(); // Ajuste la taille de la fenêtre
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();

    }

    public static void main(String[] args) {
        launch();
    }

}