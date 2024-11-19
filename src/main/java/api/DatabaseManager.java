package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = System.getProperty("test.database") != null 
        ? "jdbc:sqlite:maville-test.db" 
        : "jdbc:sqlite:maville.db";
    private static DatabaseManager instance;
    private Connection connection;
    
    public Connection getConnection() {
        return connection;
    }

    private DatabaseManager() {
        try {
            // Set connection properties to handle file permissions
            connection = DriverManager.getConnection(DB_URL);
            // Enable foreign keys and set journal mode to WAL for better concurrent access
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON;");
                stmt.execute("PRAGMA journal_mode = WAL;");
            }
            initializeTables();
        } catch (SQLException e) {
            String errorMsg = "Failed to initialize database: " + e.getMessage();
            if (e.getMessage().contains("readonly")) {
                errorMsg += "\nPlease check file permissions and ensure the application has write access to the database directory.";
            }
            throw new RuntimeException(errorMsg, e);
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initializeTables() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Create residents table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS residents (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    date_of_birth TEXT NOT NULL,
                    phone_number TEXT,
                    address TEXT NOT NULL
                )
            """);

            // Create intervenants table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS intervenants (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL,
                    type TEXT NOT NULL,
                    city_identifier TEXT NOT NULL
                )
            """);

            // Create work_requests table
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS work_requests (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    title TEXT NOT NULL,
                    description TEXT NOT NULL,
                    work_type TEXT NOT NULL,
                    desired_start_date TEXT NOT NULL,
                    status TEXT NOT NULL,
                    resident_email TEXT NOT NULL,
                    FOREIGN KEY (resident_email) REFERENCES residents(email) ON DELETE CASCADE
                )
            """);
        }
    }


    public void clearTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM residents");
            stmt.execute("DELETE FROM intervenants");
            stmt.execute("DELETE FROM work_requests");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear tables", e);
        }
    }
}
