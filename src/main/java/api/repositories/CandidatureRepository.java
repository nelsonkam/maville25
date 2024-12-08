package api.repositories;

import api.DatabaseManager;
import models.Candidature;
import models.CandidatureStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CandidatureRepository {
    private final DatabaseManager db;

    public CandidatureRepository(DatabaseManager db) {
        this.db = db;
    }

    public void save(Candidature candidature) throws SQLException {
        String sql = """
            INSERT INTO candidatures (work_request_id, intervenant_email, status, resident_message, confirmed_by_intervenant)
            VALUES (?, ?, ?, ?, ?)
        """;

        // On n'utilise plus RETURN_GENERATED_KEYS
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setLong(1, candidature.getWorkRequestId());
            pstmt.setString(2, candidature.getIntervenantEmail());
            pstmt.setString(3, candidature.getStatus().name());
            pstmt.setString(4, candidature.getResidentMessage());
            pstmt.setInt(5, candidature.isConfirmedByIntervenant() ? 1 : 0);
            pstmt.executeUpdate();
        }

        // Récupération de l'ID via last_insert_rowid()
        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()")) {
            if (rs.next()) {
                candidature.setId(rs.getLong(1));
            }
        }
    }

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
