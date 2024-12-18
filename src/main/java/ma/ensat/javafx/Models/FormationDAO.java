package ma.ensat.javafx.Models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


    public class FormationDAO {
        public List<Formation> getAllFormations() throws SQLException {
            List<Formation> formations = new ArrayList<>();
            String sql = "SELECT id, name FROM Formation";
            try (Connection conn = new DatabaseConnection().getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    formations.add(new Formation(rs.getInt("id"), rs.getString("name")));
                }
            }
            return formations;
        }
    }
