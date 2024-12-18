package ma.ensat.javafx.Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

        public List<Student> getStudentsByFormation(Integer formation_id) throws SQLException {
            List<Student> students = new ArrayList<>();
            String sql = "SELECT id, name, moyenne, formation_id FROM Student WHERE formation_id = ?";
            try (Connection conn = new DatabaseConnection().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, formation_id);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        students.add(new Student(rs.getString("id"),
                                rs.getString("name"),
                                rs.getFloat("moyenne"),
                                rs.getInt("formation_id")));
                    }
                }
            }
            return students;
        }

        public void addStudent(Student student) throws SQLException, IllegalArgumentException {
            // Validation côté applicatif
            if (student.getMoyenne() < 0 || student.getMoyenne() > 20) {
                throw new IllegalArgumentException("La moyenne doit être comprise entre 0 et 20.");
            }

            String sql = "INSERT INTO Student (id, name, moyenne, formation_id) VALUES (?, ?, ?, ?)";
            try (Connection conn = new DatabaseConnection().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, student.getId());
                pst.setString(2, student.getName());
                pst.setFloat(3, student.getMoyenne());
                pst.setInt(4, student.getFormation_id());
                pst.executeUpdate();
            }
        }

        public float getAverageForFormation(Integer formation_id) throws SQLException {
            String sql = "SELECT AVG(moyenne) as avg_moy FROM Student WHERE formation_id = ?";
            try (Connection conn = new DatabaseConnection().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, formation_id);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        return rs.getFloat("avg_moy");
                    }
                }
            }
            return 0f;
        }
    }

