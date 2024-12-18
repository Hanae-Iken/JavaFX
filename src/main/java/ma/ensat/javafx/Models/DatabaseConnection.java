package ma.ensat.javafx.Models;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

        private static DatabaseConnection instance;
        private Connection connection;
        private final String url = "jdbc:postgresql://localhost:5432/JavaFX";
        private final String username = "hanae";
        private final String password = "hanae123";
        DatabaseConnection() {
            try{
                //Charger le driver JDBC
                System.out.println("Loading driver ..");
                Class.forName("org.postgresql.Driver");
                System.out.println("Driver loaded");
                System.out.println("Connecting to database...");
                //Creer la connexion
                connection = DriverManager.getConnection(url, username, password);
                System.out.println("Successfully connected to database");
            } catch (Exception e) {
                System.out.println("Failed to connect to database" +e.getMessage());
            }
        }
        public static DatabaseConnection getInstance() {
            if (instance == null) {
                instance = new DatabaseConnection();
            }
            return instance;
        }
        public Connection getConnection() {
            return connection;
        }
    }


