package api.repositories;

import api.DatabaseManager;
import models.Candidature;
import models.CandidatureStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe gère les opérations de base de données pour les candidatures.
 */
public class CandidatureRepository {
    private final DatabaseManager db;

    /**
     * Constructeur de la classe CandidatureRepository.
     *
     * @param db Le gestionnaire de base de données.
     */
    public CandidatureRepository(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Enregistre une nouvelle candidature dans la base de données.
     *
     * @param candidature La candidature à enregistrer.
     * @throws SQLException Si une erreur survient lors de l'enregistrement.
     */
    public void save(Candidature candidature) throws SQLException {
        String sql = """
            INSERT INTO candidatures (work_request_id, intervenant_email, status, resident_message, confirmed_by_intervenant)
            VALUES (?, ?, ?, ?, ?)
            RETURNING id;
        """;

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, candidature.getWorkRequestId());
            pstmt.setString(2, candidature.getIntervenantEmail());
            pstmt.setString(3, candidature.getStatus().name());
            pstmt.setString(4, candidature.getResidentMessage());
            pstmt.setInt(5, candidature.isConfirmedByIntervenant() ? 1 : 0);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    candidature.setId(rs.getLong(1));
                }
            }
        }

    }

    /**
     * Trouve une candidature par son identifiant.
     *
     * @param id L'identifiant de la candidature.
     * @return Un Optional contenant la candidature si elle est trouvée, sinon un Optional vide.
     * @throws SQLException Si une erreur survient lors de la recherche.
     */
    public Optional<Candidature> findById(Long id) throws SQLException {
        String sql = "SELECT * FROM candidatures WHERE id = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
            return Optional.empty();
        }
    }

    /**
     * Trouve les candidatures par l'identifiant de la demande de travaux.
     *
     * @param workRequestId L'identifiant de la demande de travaux.
     * @return La liste des candidatures pour la demande de travaux.
     * @throws SQLException Si une erreur survient lors de la recherche.
     */
    public List<Candidature> findByWorkRequestId(Long workRequestId) throws SQLException {
        String sql = "SELECT * FROM candidatures WHERE work_request_id = ?";
        List<Candidature> result = new ArrayList<>();
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, workRequestId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSet(rs));
            }
        }
        return result;
    }

    /**
     * Trouve une candidature par l'identifiant de la demande de travaux et l'email de l'intervenant.
     *
     * @param workRequestId L'identifiant de la demande de travaux.
     * @param intervenantEmail L'email de l'intervenant.
     * @return Un Optional contenant la candidature si elle est trouvée, sinon un Optional vide.
     * @throws SQLException Si une erreur survient lors de la recherche.
     */
    public Optional<Candidature> findByWorkRequestAndIntervenant(Long workRequestId, String intervenantEmail) throws SQLException {
        String sql = "SELECT * FROM candidatures WHERE work_request_id = ? AND intervenant_email = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, workRequestId);
            pstmt.setString(2, intervenantEmail);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
            return Optional.empty();
        }
    }

    /**
     * Trouve les candidatures par l'email de l'intervenant.
     *
     * @param intervenantEmail L'email de l'intervenant.
     * @return La liste des candidatures de l'intervenant.
     * @throws SQLException Si une erreur survient lors de la recherche.
     */
    public List<Candidature> findByIntervenantEmail(String intervenantEmail) throws SQLException {
        String sql = "SELECT * FROM candidatures WHERE intervenant_email = ?";
        List<Candidature> candidatures = new ArrayList<>();
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, intervenantEmail);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                candidatures.add(mapResultSet(rs));
            }
        }
        return candidatures;
    }

    /**
     * Trouve les candidatures pour les travaux soumis par l'email du résident connecté.
     *
     * @param residentEmail L'email de l'intervenant.
     * @return La liste des candidatures pour les travaux soumis par le résident connecté.
     * @throws SQLException Si une erreur survient lors de la recherche.
     */

    public List<Candidature> findByWorkRequestAndResidentEmail(String residentEmail) throws SQLException {
        String sql = """
                SELECT * FROM candidatures WHERE work_request_id IN (
                    SELECT id FROM work_requests WHERE resident_email = ?)
                """;

        List <Candidature> result = new ArrayList<>();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, residentEmail);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result.add(mapResultSet(rs));
            }
        return result;
        }
    }

    /**
     * Met à jour une candidature dans la base de données.
     *
     * @param candidature La candidature à mettre à jour.
     * @throws SQLException Si une erreur survient lors de la mise à jour.
     */
    public void update(Candidature candidature) throws SQLException {
        String sql = """
            UPDATE candidatures
            SET status = ?, resident_message = ?, confirmed_by_intervenant = ?
            WHERE id = ?
        """;
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, candidature.getStatus().name());
            pstmt.setString(2, candidature.getResidentMessage());
            pstmt.setInt(3, candidature.isConfirmedByIntervenant() ? 1 : 0);
            pstmt.setLong(4, candidature.getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Mappe un ResultSet à un objet Candidature.
     *
     * @param rs Le ResultSet à mapper.
     * @return L'objet Candidature mappé.
     * @throws SQLException Si une erreur survient lors du mapping.
     */
    private Candidature mapResultSet(ResultSet rs) throws SQLException {
        Candidature c = new Candidature();
        c.setId(rs.getLong("id"));
        c.setWorkRequestId(rs.getLong("work_request_id"));
        c.setIntervenantEmail(rs.getString("intervenant_email"));
        c.setStatus(CandidatureStatus.valueOf(rs.getString("status")));
        c.setResidentMessage(rs.getString("resident_message"));
        c.setConfirmedByIntervenant(rs.getInt("confirmed_by_intervenant") == 1);
        return c;
    }
}