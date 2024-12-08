package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.sql.Date;

public class DataInitializer {
    private final DatabaseManager db;

    public DataInitializer(DatabaseManager db) {
        this.db = db;
    }

    public void initializeData() throws Exception {
        Connection conn = db.getConnection();

        // 1. Insérer 5 résidents
        insertResident(conn, "Alice Martin", "alice@example.com", "password123", LocalDate.of(1990,1,1), "514-111-1111", "Quartier A, 123 Rue");
        insertResident(conn, "Bob Dupont", "bob@example.com", "password123", LocalDate.of(1985,2,15), "514-222-2222", "Quartier A, 456 Rue");
        insertResident(conn, "Charlie Dubois", "charlie@example.com", "password123", LocalDate.of(2000,3,10), "514-333-3333", "Quartier B, 789 Rue");
        insertResident(conn, "Diane Leroy", "diane@example.com", "password123", LocalDate.of(1992,4,20), "514-444-4444", "Quartier C, 101 Rue");
        insertResident(conn, "Eric Moreau", "eric@example.com", "password123", LocalDate.of(1988,5,5), "514-555-5555", "Quartier D, 202 Rue");

        // 2. Insérer 5 intervenants
        insertIntervenant(conn, "Entreprise Publique A", "int1@example.com", "password123", "Entreprise publique", "11111111");
        insertIntervenant(conn, "Entrepreneur Privé B", "int2@example.com", "password123", "Entrepreneur privé", "22222222");
        insertIntervenant(conn, "Particulier C", "int3@example.com", "password123", "Particulier", "33333333");
        insertIntervenant(conn, "Entreprise Publique D", "int4@example.com", "password123", "Entreprise publique", "44444444");
        insertIntervenant(conn, "Entrepreneur Privé E", "int5@example.com", "password123", "Entrepreneur privé", "55555555");

        // 3. Insérer 5 WorkRequests 
        insertWorkRequest(conn, 1L, "Trottoirs cassés", "Réparation trottoir", "Voirie", LocalDate.now().plusDays(30), "PENDING", "alice@example.com");
        insertWorkRequest(conn, 2L, "Lampadaire cassé", "Remplacer lampadaire", "Éclairage public", LocalDate.now().plusDays(10), "PENDING", "bob@example.com");
        insertWorkRequest(conn, 3L, "Fuite d'eau", "Réparer conduite", "Aqueduc", LocalDate.now().plusDays(20), "PENDING", "charlie@example.com");
        insertWorkRequest(conn, 4L, "Arbre dangereux", "Couper arbre malade", "Voirie", LocalDate.now().plusDays(15), "PENDING", "diane@example.com");
        insertWorkRequest(conn, 5L, "Marquage au sol", "Repeindre passages piétons", "Signalisation", LocalDate.now().plusDays(40), "PENDING", "eric@example.com");

        // Candidatures liées aux WorkRequests avec IDs fixes
        insertCandidature(conn, 1L, "int1@example.com", "SUBMITTED", null, false);
        insertCandidature(conn, 1L, "int2@example.com", "SUBMITTED", null, false);
        insertCandidature(conn, 2L, "int3@example.com", "SUBMITTED", null, false);

        // 4. Insérer 5 projets
        insertProject(conn, "Projet Voirie A", "Refaire chaussée", LocalDate.now().plusDays(10), "Prévu");
        insertProject(conn, "Projet Aqueduc B", "Remplacer conduite principale", LocalDate.now().plusDays(20), "Prévu");
        insertProject(conn, "Projet Égouts C", "Élargir égout", LocalDate.now().plusDays(25), "Prévu");
        insertProject(conn, "Projet Signalisation D", "Nouvelles pancartes", LocalDate.now().plusDays(5), "Prévu");
        insertProject(conn, "Projet Spécial E", "Grand chantier sur 3 mois", LocalDate.now().plusDays(90), "Prévu");

        conn.close();
    }

    private void insertResident(Connection conn, String name, String email, String password, LocalDate dob, String phone, String address) throws Exception {
        String sql = "INSERT INTO residents (name, email, password, date_of_birth, phone_number, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setDate(4, Date.valueOf(dob));
            pstmt.setString(5, phone);
            pstmt.setString(6, address);
            pstmt.executeUpdate();
        }
    }

    private void insertIntervenant(Connection conn, String name, String email, String password, String type, String cityId) throws Exception {
        String sql = "INSERT INTO intervenants (name, email, password, type, city_identifier) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.setString(4, type);
            pstmt.setString(5, cityId);
            pstmt.executeUpdate();
        }
    }

    private void insertWorkRequest(Connection conn, long id, String title, String description, String workType, LocalDate startDate, String status, String residentEmail) throws Exception {
        String sql = "INSERT INTO work_requests (id, title, description, work_type, desired_start_date, status, resident_email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, workType);
            pstmt.setDate(5, Date.valueOf(startDate));
            pstmt.setString(6, status);
            pstmt.setString(7, residentEmail);
            pstmt.executeUpdate();
        }
    }

    private void insertCandidature(Connection conn, long workRequestId, String intervenantEmail, String status, String residentMessage, boolean confirmed) throws Exception {
        String sql = "INSERT INTO candidatures (work_request_id, intervenant_email, status, resident_message, confirmed_by_intervenant) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, workRequestId);
            pstmt.setString(2, intervenantEmail);
            pstmt.setString(3, status);
            pstmt.setString(4, residentMessage);
            pstmt.setInt(5, confirmed ? 1 : 0);
            pstmt.executeUpdate();
        }
    }

    private void insertProject(Connection conn, String title, String description, LocalDate startDate, String status) throws Exception {
        String sql = "INSERT INTO projects (title, description, desired_start_date, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setDate(3, Date.valueOf(startDate));
            pstmt.setString(4, status);
            pstmt.executeUpdate();
        }
    }
}