package api.repositories;

import api.DatabaseManager;
import models.Intervenant;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe gère les opérations de base de données pour les intervenants.
 */
public class IntervenantRepository {
    private final DatabaseManager db;

    /**
     * Constructeur de la classe IntervenantRepository.
     *
     * @param db Le gestionnaire de base de données.
     */
    public IntervenantRepository(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Enregistre un nouvel intervenant dans la base de données.
     *
     * @param intervenant L'intervenant à enregistrer.
     * @throws RuntimeException Si une erreur survient lors de l'enregistrement.
     */
    public void save(Intervenant intervenant) {
        String sql = "INSERT INTO intervenants (name, email, password, type, city_identifier) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, intervenant.getName());
            pstmt.setString(2, intervenant.getEmail());
            pstmt.setString(3, intervenant.getPassword());
            pstmt.setString(4, intervenant.getType());
            pstmt.setString(5, intervenant.getCityIdentifier());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save intervenant", e);
        }
    }

    /**
     * Trouve un intervenant par email.
     *
     * @param email L'email de l'intervenant.
     * @return Un Optional contenant l'intervenant s'il est trouvé, sinon un Optional vide.
     * @throws RuntimeException Si une erreur survient lors de la recherche.
     */
    public Optional<Intervenant> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM intervenants WHERE email = ?";
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Intervenant(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("type"),
                    rs.getString("city_identifier")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find intervenant", e);
        }
    }

    /**
     * Récupère tous les intervenants.
     *
     * @return La liste de tous les intervenants.
     * @throws RuntimeException Si une erreur survient lors de la récupération.
     */
    public List<Intervenant> findAll() {
        try {
            String sql = "SELECT * FROM intervenants";
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Intervenant> intervenants = new ArrayList<>();
            while (rs.next()) {
                intervenants.add(new Intervenant(
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("type"),
                    rs.getString("city_identifier")
                ));
            }
            return intervenants;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch intervenants", e);
        }
    }
}