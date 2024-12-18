package ma.ensat.javafx.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ma.ensat.javafx.Models.Formation;
import ma.ensat.javafx.Models.FormationDAO;
import ma.ensat.javafx.Models.Student;
import ma.ensat.javafx.Models.StudentDAO;

public class MainController {


        @FXML
        private ComboBox<Formation> formationComboBox;
        @FXML
        private TableView<Student> studentTable;
        @FXML
        private TableColumn<Student, String> idColumn;
        @FXML
        private TableColumn<Student, String> nameColumn;
        @FXML
        private TableColumn<Student, Float> moyColumn;
        @FXML
        private Label formationAverageLabel;
        @FXML
        private Button addStudentButton;

        private FormationDAO formationDAO = new FormationDAO();
        private StudentDAO studentDAO = new StudentDAO();

        @FXML
        public void initialize() {
            // Configuration des colonnes
            idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
            nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
            moyColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleFloatProperty(data.getValue().getMoyenne()).asObject());

            // Charger les formations
            try {
                formationComboBox.setItems(FXCollections.observableArrayList(formationDAO.getAllFormations()));
            } catch (Exception e) {
                showErrorAlert("Erreur lors du chargement des formations", e.getMessage());
            }

            // Listener sur la ComboBox pour mettre à jour la liste des étudiants
            formationComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    loadStudentsForFormation(newVal.getId());
                }
            });

            addStudentButton.setOnAction(e -> openAddStudentDialog());
        }

        private void loadStudentsForFormation(Integer formation_id) {
            try {
                ObservableList<Student> studentList = FXCollections.observableArrayList(studentDAO.getStudentsByFormation(formation_id));
                studentTable.setItems(studentList);

                float avg = studentDAO.getAverageForFormation(formation_id);
                formationAverageLabel.setText(String.format("%.2f", avg));
            } catch (Exception e) {
                showErrorAlert("Erreur lors du chargement des étudiants", e.getMessage());
            }
        }

        @FXML
        private void openAddStudentDialog() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/studentformation2/AddStudent.fxml"));
                Parent root = loader.load();

                AddStudentController controller = loader.getController();
                Formation selectedFormation = formationComboBox.getValue();
                if (selectedFormation == null) {
                    showErrorAlert("Erreur", "Veuillez sélectionner une formation avant d'ajouter un étudiant.");
                    return;
                }

                controller.setFormation_id(selectedFormation.getId());

                Stage dialogStage = new Stage();
                dialogStage.setTitle("Ajouter un étudiant");
                dialogStage.setScene(new Scene(root));
                dialogStage.initModality(Modality.APPLICATION_MODAL);

                dialogStage.showAndWait();

                // Après fermeture du dialog, recharger les étudiants
                if (selectedFormation != null) {
                    loadStudentsForFormation(selectedFormation.getId());
                }

            } catch (Exception e) {
                showErrorAlert("Erreur", e.getMessage());
            }
        }

        private void showErrorAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
            alert.setTitle(title);
            alert.showAndWait();
        }
    }

