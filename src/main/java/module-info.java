module Auto_Ecolee.Auto_Ecolee {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.sql;
	requires javafx.base;
	requires javafx.graphics;
	requires java.desktop;

    opens Auto_Ecolee.Auto_Ecolee to javafx.fxml;
    opens Controleur to javafx.fxml;
    opens Entities to javafx.base;
    exports Auto_Ecolee.Auto_Ecolee;
    exports Controleur; 
}
