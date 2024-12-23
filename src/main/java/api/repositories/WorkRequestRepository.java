package api.repositories;

import api.DatabaseManager;
import models.WorkRequest;
import models.WorkRequestStatus;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe gère les opérations de base de données pour les demandes de travaux.
 */
public class WorkRequestRepository {
    private final DatabaseManager db;

    /**
     * Constructeur de la classe WorkRequestRepository.
     *
     * @param db Le gestionnaire de base de données.
     */
    public WorkRequestRepository(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Enregistre une nouvelle demande de travaux dans la base de données.
     *
     * @param request La demande de travaux à enregistrer.
     * @throws SQLException Si une erreur survient lors de l'enregistrement.
     */
    public void save(WorkRequest request) throws SQLException {
        db.getConnection().setAutoCommit(false);
        String sql = """
            INSERT INTO work_requests 
            (title, description, work_type, desired_start_date, status, resident_email)
            VALUES (?, ?, ?, ?, ?, ?)
            RETURNING id
        """;
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getDescription());
            pstmt.setString(3, request.getWorkType());
            pstmt.setString(4, request.getDesiredStartDate().toString());
            pstmt.setString(5, request.getStatus().name());
            pstmt.setString(6, request.getResidentEmail());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    request.setId(rs.getLong(1));
                }
            }
        }
    }

    /**
     * Récupère les demandes de travaux d'un résident par email.
     *
     * @param email L'email du résident.
     * @return La liste des demandes de travaux du résident.
     * @throws SQLException Si une erreur survient lors de la récupération.
     */
    public List<WorkRequest> findByResidentEmail(String email) throws SQLException {
        String sql = "SELECT * FROM work_requests WHERE resident_email = ?";
        List<WorkRequest> requests = new ArrayList<>();
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                requests.add(mapResultSetToWorkRequest(rs));
            }
        }
        
        return requests;
    }

    /**
     * Récupère les demandes de travaux d'un intervenant par email.
     *
     * @param email L'email de l'intervenant.
     * @return La liste des demandes de travaux de l'intervenant.
     * @throws SQLException Si une erreur survient lors de la récupération.
     */
    public List<WorkRequest> findByIntervenantEmail(String email) throws SQLException {
        String sql = "SELECT * FROM work_requests WHERE intervenant_email = ?";
        List<WorkRequest> requests = new ArrayList<>();
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                requests.add(mapResultSetToWorkRequest(rs));
            }
        }
        
        return requests;
    }

    /**
     * Récupère une demande de travaux par son identifiant.
     *
     * @param id L'identifiant de la demande de travaux.
     * @return Une option contenant la demande de travaux si elle existe, sinon une option vide.
     * @throws SQLException Si une erreur survient lors de la récupération.
     */
    public Optional<WorkRequest> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM work_requests WHERE id = ?";
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return Optional.of(mapResultSetToWorkRequest(rs));
            }
        }
        
        return Optional.empty();
    }

    /**
     * Met à jour une demande de travaux dans la base de données.
     *
     * @param request La demande de travaux à mettre à jour.
     * @throws SQLException Si une erreur survient lors de la mise à jour.
     */
    public void update(WorkRequest request) throws SQLException {
        String sql = """
            UPDATE work_requests 
            SET title = ?, description = ?, work_type = ?, desired_start_date = ?, 
                status = ?, resident_email = ?
            WHERE id = ?
        """;
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getDescription());
            pstmt.setString(3, request.getWorkType());
            pstmt.setString(4, request.getDesiredStartDate().toString());
            pstmt.setString(5, request.getStatus().name());
            pstmt.setString(6, request.getResidentEmail());
            pstmt.setLong(7, request.getId());
            
            pstmt.executeUpdate();
        }
    }

    /**
     * Mappe un ResultSet à un objet WorkRequest.
     *
     * @param rs Le ResultSet à mapper.
     * @return L'objet WorkRequest mappé.
     * @throws SQLException Si une erreur survient lors du mapping.
     */
    private WorkRequest mapResultSetToWorkRequest(ResultSet rs) throws SQLException {
        WorkRequest request = new WorkRequest();
        request.setId(rs.getLong("id"));
        request.setTitle(rs.getString("title"));
        request.setDescription(rs.getString("description"));
        request.setWorkType(rs.getString("work_type"));
        request.setDesiredStartDate(LocalDate.parse(rs.getString("desired_start_date")));
        request.setStatus(WorkRequestStatus.valueOf(rs.getString("status")));
        request.setResidentEmail(rs.getString("resident_email"));
        return request;
    }

    /**
     * Vérifie si un résident existe par email.
     *
     * @param email L'email du résident.
     * @return true si le résident existe, false sinon.
     * @throws SQLException Si une erreur survient lors de la vérification.
     */
    public boolean residentExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM residents WHERE email = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    /**
     * Récupère toutes les demandes de travaux.
     *
     * @return La liste de toutes les demandes de travaux.
     * @throws SQLException Si une erreur survient lors de la récupération.
     */
    public List<WorkRequest> findAll() throws SQLException {
        String sql = "SELECT * FROM work_requests";
        List<WorkRequest> requests = new ArrayList<>();
        
        try (Statement stmt = db.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                requests.add(mapResultSetToWorkRequest(rs));
            }
        }
        
        return requests;
    }
}