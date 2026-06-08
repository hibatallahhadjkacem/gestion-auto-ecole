package Controleur;

import Entities.ExamenCode;
import Entities.ExamenConduite;
import Entities.Res;
import Service.ExamenCodeService;
import Service.ExamenConduiteService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import Auto_Ecolee.Auto_Ecolee.App;

public class AfficheExamController {

    @FXML private TextField num;

    @FXML private TableView<ExamenCode> table;
    @FXML private TableColumn<ExamenCode, LocalDate> dateCode;
    @FXML private TableColumn<ExamenCode, LocalTime> stimeCode;
    @FXML private TableColumn<ExamenCode, LocalTime> etimeCode;

    @FXML private TableView<ExamenCode> table1;
    @FXML private TableColumn<ExamenCode, Integer> moniteurCode;
    @FXML private TableColumn<ExamenCode, Res> resCode;
    @FXML private TableColumn<ExamenCode, Double> fraisCode;
    @FXML private TableColumn<ExamenCode, Void> actionsColumn;

    @FXML private TableView<ExamenConduite> table2;
    @FXML private TableColumn<ExamenConduite, LocalDate> dateConduite;
    @FXML private TableColumn<ExamenConduite, LocalTime> stimeConduite;
    @FXML private TableColumn<ExamenConduite, LocalTime> etimeConduite;
    @FXML private TableColumn<ExamenConduite, Integer> moniteurConduite;

    @FXML private TableView<ExamenConduite> table3;
    @FXML private TableColumn<ExamenConduite, String> vehicule;
    @FXML private TableColumn<ExamenConduite, Void> adress; // Changed from String to Void
    @FXML private TableColumn<ExamenConduite, String> resConduite;
    @FXML private TableColumn<ExamenConduite, Double> fraisConduite;
    @FXML private TableColumn<ExamenConduite, Void> actionsColumn1;

    @FXML private ImageView updateButton;
    @FXML private ImageView updateButton1;

    private ExamenCodeService exmenCodeService = new ExamenCodeService();
    private ExamenConduiteService examenConduiteService = new ExamenConduiteService();

    private List<ExamenCode> tousLesExamensCode = exmenCodeService.getAllExams();
    private List<ExamenConduite> tousLesExamensConduite = examenConduiteService.getAllExams();

    public void initialize() {
        Tooltip tooltip1 = new Tooltip("Update Code  Exam");
        Tooltip tooltip2 = new Tooltip("Update Conduite Exam ");

        updateButton.setOnMouseEntered(e -> tooltip1.show(updateButton, e.getScreenX(), e.getScreenY() + 10));
        updateButton.setOnMouseExited(e -> tooltip1.hide());

        updateButton1.setOnMouseEntered(e -> tooltip2.show(updateButton1, e.getScreenX(), e.getScreenY() + 10));
        updateButton1.setOnMouseExited(e -> tooltip2.hide());

        dateCode.setCellValueFactory(new PropertyValueFactory<>("date"));
        moniteurCode.setCellValueFactory(new PropertyValueFactory<>("moniteur_id"));
        resCode.setCellValueFactory(new PropertyValueFactory<>("resultat"));
        fraisCode.setCellValueFactory(new PropertyValueFactory<>("frais"));
        stimeCode.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        etimeCode.setCellValueFactory(new PropertyValueFactory<>("endTime"));

        dateConduite.setCellValueFactory(new PropertyValueFactory<>("date"));
        stimeConduite.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        etimeConduite.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        moniteurConduite.setCellValueFactory(new PropertyValueFactory<>("moniteur"));
        vehicule.setCellValueFactory(new PropertyValueFactory<>("vehicule"));
        resConduite.setCellValueFactory(new PropertyValueFactory<>("resultat"));
        fraisConduite.setCellValueFactory(new PropertyValueFactory<>("frais"));

        setupActionsColumn();
        setupActionsColumn1();
        setupAddressColumnWithButton(); // <--- Add setup method here
    }

    private void setupActionsColumn() {
        actionsColumn.setCellFactory(param -> new TableCell<ExamenCode, Void>() {
            private final ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/pics/poubelle.png")));
            private final Button deleteButton = new Button();
            private final Tooltip tooltip = new Tooltip("Delete Exam");

            {
                deleteButton.setTooltip(tooltip);
                tooltip.setShowDelay(Duration.ZERO);
                deleteIcon.setFitWidth(20);
                deleteIcon.setFitHeight(20);
                deleteButton.setGraphic(deleteIcon);
                deleteButton.setStyle("-fx-background-color: transparent; -fx-padding: 5;");
                deleteButton.setOnAction(event -> {
                    ExamenCode examenCode = getTableView().getItems().get(getIndex());
                    handleDelete(examenCode);
                });
            }

            private void handleDelete(ExamenCode examenCode) {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + ExamenCode.getNumero() + "?", ButtonType.YES, ButtonType.NO);
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.YES) {
                        if (exmenCodeService.deleteExam(examenCode.getNumero())) {
                            table.getItems().remove(examenCode);
                            showAlert("Success", "Exam deleted successfully!", Alert.AlertType.INFORMATION);
                        } else {
                            showAlert("Error", "Failed to delete Exam.", Alert.AlertType.ERROR);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton);
            }
        });
    }

    private void setupActionsColumn1() {
        actionsColumn1.setCellFactory(param -> new TableCell<ExamenConduite, Void>() {
            private final ImageView deleteIcon1 = new ImageView(new Image(getClass().getResourceAsStream("/pics/poubelle.png")));
            private final Button deleteButton1 = new Button();
            private final Tooltip tooltip = new Tooltip("Delete Exam");

            {
                deleteButton1.setTooltip(tooltip);
                tooltip.setShowDelay(Duration.ZERO);
                deleteIcon1.setFitWidth(20);
                deleteIcon1.setFitHeight(20);
                deleteButton1.setGraphic(deleteIcon1);
                deleteButton1.setStyle("-fx-background-color: transparent; -fx-padding: 5;");
                deleteButton1.setOnAction(event -> {
                    ExamenConduite examenConduite = getTableView().getItems().get(getIndex());
                    handleDelete1(examenConduite);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteButton1);
            }
        });
    }

    private void handleDelete1(ExamenConduite examenConduite) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete " + examenConduite.getNumero() + "?", ButtonType.YES, ButtonType.NO);
        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (examenConduiteService.deleteExam(examenConduite.getNumero())) {
                    table2.getItems().remove(examenConduite);
                    showAlert("Success", "Exam deleted successfully!", Alert.AlertType.INFORMATION);
                } else {
                    showAlert("Error", "Failed to delete Exam.", Alert.AlertType.ERROR);
                }
            }
        });
    }

    @FXML
    private void getExams() {
        String numCondidat = num.getText();

        if (numCondidat.isEmpty() || !numCondidat.matches("\\d+") || Integer.parseInt(numCondidat) < 0) {
            showAlert("Error", "Candidate number invalid!", AlertType.ERROR);
            return;
        }

        int condidatNum = Integer.parseInt(numCondidat);

        List<ExamenCode> examensCode = tousLesExamensCode.stream()
                .filter(ex -> ex.getCondidat_num() == condidatNum)
                .collect(Collectors.toList());

        List<ExamenConduite> examensConduite = tousLesExamensConduite.stream()
                .filter(ex -> ex.getCondidat() == condidatNum)
                .collect(Collectors.toList());

        if (examensCode.isEmpty()) {
            showAlert("No Exams", "This candidate has no Code exams.", AlertType.INFORMATION);
        }

        if (examensConduite.isEmpty()) {
            showAlert("No Exams", "This candidate has no Driving exams.", AlertType.INFORMATION);
        }

        if (!examensCode.isEmpty() || !examensConduite.isEmpty()) {
            loadCodeExam();
            loadDrivingExam();
        }
    }

    private void loadDrivingExam() {
        ObservableList<ExamenConduite> observableList = FXCollections.observableArrayList(tousLesExamensConduite);
        table2.setItems(observableList);
        table3.setItems(observableList);
    }

    private void loadCodeExam() {
        ObservableList<ExamenCode> observableList = FXCollections.observableArrayList(tousLesExamensCode);
        table.setItems(observableList);
        table1.setItems(observableList);
    }

    @FXML
    private void updateExamConduite(MouseEvent event) throws IOException {
        App.setRoot("updateExamConduite");
    }

    @FXML
    private void updateExamCode() throws IOException {
        App.setRoot("updateExamCode");
    }

    @FXML
    private void back() throws IOException {
        App.setRoot("examen");
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
    private void Exam() throws IOException {
        App.setRoot("examen");
    }

    @FXML
    private void color(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #082866; -fx-text-fill: white;");
    }

    @FXML
    private void color2(MouseEvent event) {
        ((Button) event.getSource()).setStyle("-fx-background-color: #5673a9; -fx-text-fill: white;");
    }

    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // 👇 New method to set up the "View Map" button
    private void setupAddressColumnWithButton() {
        adress.setCellFactory(param -> new TableCell<ExamenConduite, Void>() {
            private final Button mapButton = new Button("View Map");

            {
                mapButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-cursor: hand;");
                mapButton.setOnAction(event -> {
                    ExamenConduite exam = getTableView().getItems().get(getIndex());
                    if (exam.getAdress() != null && !exam.getAdress().isEmpty()) {
                        try {
                            openMapWindow(exam); // Pass the exam object
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        showAlert("No Address", "No address available for this exam.", Alert.AlertType.INFORMATION);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : mapButton);
            }
        });
    }

    @FXML
    public void openMapWindow(ExamenConduite exam) throws IOException {
        Stage mapStage = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/projet/nermine/map2Window.fxml"));
        Parent root = loader.load();

        Map2WindowController mapController = loader.getController();

        
        mapController.setLocationFromExam(exam);

        Scene mapScene = new Scene(root);
        mapStage.setTitle("Map Window");
        mapStage.setScene(mapScene);
        mapStage.show();
    }

}
