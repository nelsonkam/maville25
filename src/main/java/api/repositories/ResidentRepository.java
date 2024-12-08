package api.repositories;

import api.DatabaseManager;
import models.Resident;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Cette classe gère les opérations de base de données pour les résidents.
 */
public class ResidentRepository {
    private final DatabaseManager db;

    /**
     * Constructeur de la classe ResidentRepository.
     *
     * @param db Le gestionnaire de base de données.
     */
    public ResidentRepository(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Enregistre un nouveau résident dans la base de données.
     *
     * @param resident Le résident à enregistrer.
     * @throws RuntimeException Si une erreur survient lors de l'enregistrement.
     */
    public void save(Resident resident) {
        String sql = "INSERT INTO residents (name, email, password, date_of_birth, phone_number, address) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, resident.getName());
            pstmt.setString(2, resident.getEmail());
            pstmt.setString(3, resident.getPassword());
            pstmt.setString(4, resident.getDateOfBirth().toString());
            pstmt.setString(5, resident.getPhoneNumber());
            pstmt.setString(6, resident.getAddress());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save resident", e);
        }
    }

    /**
     * Trouve un résident par email.
     *
     * @param email L'email du résident.
     * @return Un Optional contenant le résident s'il est trouvé, sinon un Optional vide.
     * @throws RuntimeException Si une erreur survient lors de la recherche.
     */
    public Optional<Resident> findByEmail(String email) {
        try {
            String sql = "SELECT * FROM residents WHERE email = ?";
            PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return Optional.of(new Resident(
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date_of_birth")),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("address")
                ));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to find resident", e);
        }
    }

    /**
     * Récupère tous les résidents.
     *
     * @return La liste de tous les résidents.
     * @throws RuntimeException Si une erreur survient lors de la récupération.
     */
    public List<Resident> findAll() {
        try {
            String sql = "SELECT * FROM residents";
            Statement stmt = db.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            List<Resident> residents = new ArrayList<>();
            while (rs.next()) {
                residents.add(new Resident(
                    rs.getString("name"),
                    LocalDate.parse(rs.getString("date_of_birth")),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("phone_number"),
                    rs.getString("address")
                ));
            }
            return residents;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch residents", e);
        }
    }
}