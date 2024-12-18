package ma.ensat.javafx.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ma.ensat.javafx.Models.Student;
import ma.ensat.javafx.Models.StudentDAO;

public class AddStudentController {


        @FXML
        private TextField idField;
        @FXML
        private TextField nameField;
        @FXML
        private TextField moyField;
        @FXML
        private Button saveButton;
        @FXML
        private Button cancelButton;

        private Integer formation_id;
        private StudentDAO studentDAO = new StudentDAO();

        public void setFormation_id(Integer formation_id) {
            this.formation_id = formation_id;
        }

        @FXML
        public void initialize() {
            saveButton.setOnAction(e -> saveStudent());
            cancelButton.setOnAction(e -> closeWindow());
        }

        @FXML
        private void saveStudent() {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String moyStr = moyField.getText().trim();

            if (id.isEmpty() || name.isEmpty() || moyStr.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            float moyenne;
            try {
                moyenne = Float.parseFloat(moyStr);
            } catch (NumberFormatException ex) {
                showAlert("Erreur", "La moyenne doit être un nombre.");
                return;
            }

            Student s = new Student(id, name, moyenne, formation_id);

            try {
                studentDAO.addStudent(s);
                closeWindow();
            } catch (IllegalArgumentException ex) {
                // Moyenne invalide
                showAlert("Erreur", ex.getMessage());
            } catch (Exception ex) {
                showAlert("Erreur", "Erreur lors de l'ajout de l'étudiant : " + ex.getMessage());
            }
        }

        @FXML
        private void closeWindow() {
            Stage stage = (Stage) saveButton.getScene().getWindow();
            stage.close();
        }

        private void showAlert(String title, String message) {
            Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.OK);
            alert.setTitle(title);
            alert.showAndWait();
        }
    }
