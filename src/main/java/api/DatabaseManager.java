package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Cette classe gère la connexion à la base de données et l'initialisation des tables pour l'application MaVille.
 */
public class DatabaseManager {
    private static final String DB_URL = System.getProperty("test.database") != null 
        ? "jdbc:sqlite:maville-test.db" 
        : "jdbc:sqlite:maville.db";
    private static DatabaseManager instance;
    private Connection connection;
    
    /**
     * Retourne la connexion à la base de données.
     *
     * @return La connexion à la base de données.
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    /**
     * Constructeur privé de la classe DatabaseManager.
     * Initialise la connexion à la base de données et les tables.
     */
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

    /**
     * Retourne l'instance unique de DatabaseManager.
     *
     * @return L'instance unique de DatabaseManager.
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Initialise les tables de la base de données.
     *
     * @throws SQLException Si une erreur survient lors de l'initialisation des tables.
     */
    private void initializeTables() throws SQLException {
        Statement stmt = connection.createStatement();
        try {
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

            // Create notifications table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS notifications (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            resident_email TEXT NOT NULL,
                            message TEXT NOT NULL,
                            date_created TEXT NOT NULL,
                            is_read BOOLEAN NOT NULL DEFAULT 0,
                            FOREIGN KEY (resident_email) REFERENCES residents(email) ON DELETE CASCADE
                        )
                    """);

            // Create candidatures table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS candidatures (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            work_request_id INTEGER NOT NULL,
                            intervenant_email TEXT NOT NULL,
                            status TEXT NOT NULL,
                            resident_message TEXT,
                            confirmed_by_intervenant BOOLEAN NOT NULL DEFAULT 0,
                            FOREIGN KEY (work_request_id) REFERENCES work_requests(id) ON DELETE CASCADE,
                            FOREIGN KEY (intervenant_email) REFERENCES intervenants(email) ON DELETE CASCADE
                        )
                    """);

            // Create projects table
            stmt.execute("""
                        CREATE TABLE IF NOT EXISTS projects (
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            title TEXT NOT NULL,
                            description TEXT,
                            borough TEXT,
                            status TEXT NOT NULL,
                            desired_start_date TEXT NOT NULL
                        )
                    """);
        } catch (SQLException e) {
            String errorMsg = "Failed to initialize tables: " + e.getMessage();
            throw new RuntimeException(errorMsg, e);
        }
    }

    /**
     * Vide les tables de la base de données.
     */
    public void clearTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM residents");
            stmt.execute("DELETE FROM intervenants");
            stmt.execute("DELETE FROM work_requests");
            stmt.execute("DELETE FROM projects");
            stmt.execute("DELETE FROM notifications");
            stmt.execute("DELETE FROM candidatures");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear tables", e);
        }
    }
}
