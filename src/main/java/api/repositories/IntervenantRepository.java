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

public class IntervenantRepository {
    private final DatabaseManager db;

    public IntervenantRepository(DatabaseManager db) {
        this.db = db;
    }

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
