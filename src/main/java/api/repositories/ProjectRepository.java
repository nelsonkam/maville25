package api.repositories;

import api.DatabaseManager;
import models.Project;
import models.ProjectStatus;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
/**
 * Cette classe gère les opérations de base de données pour les projets.
 */

public class ProjectRepository {

    private final DatabaseManager db;

    /**
     * Constructeur de la classe CandidatureRepository.
     *
     * @param db Le gestionnaire de base de données.
     */
    public ProjectRepository(DatabaseManager db) {
        this.db = db;
    }

    /**
     * Enregistre une nouvelle candidature dans la base de données.
     *
     * @param candidature La candidature à enregistrer.
     * @throws SQLException Si une erreur survient lors de l'enregistrement.
     */
    public void save (Project project) throws SQLException {
        String sql = """
                INSERT INTO projects (title, description, status, desired_start_date)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, project.getTitle());
            pstmt.setString(2, project.getDescription());
            pstmt.setString(3, project.getProjectStatus());
            pstmt.setString(4, project.getDesiredStartDate().toString());

            pstmt.executeUpdate();

            // Get the last inserted ID
            try (Statement stmt = db.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
                if (rs.next()) {
                    project.setId(rs.getLong(1));
                }            
            }
        }
    }


    /**
     * Récupère les projets.
     *
     * @return La liste des projets.
     * @throws SQLException Si une erreur survient lors de la récupération.
     */    
    public List<Project> findProject() throws SQLException{
        String sql = "SELECT * from projects";
        List<Project> projects = new ArrayList<>();

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                projects.add(mapResultSetToProject(rs));
            }
        }

        return projects;
    }


    /**
     * Met à jour un project dans la base de données.
     *
     * @param project Le projet à mettre à jour.
     * @throws SQLException Si une erreur survient lors de la mise à jour.
     */
    public void update(Project project) throws SQLException {
        String sql = """
            UPDATE projects
            SET title = ?, description = ?, status = ?, desired_start_date = ?
            WHERE id  = ?
        """;

        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, project.getTitle());
            pstmt.setString(2, project.getDescription());
            pstmt.setString(3, project.getProjectStatus());
            pstmt.setString(4, project.getDesiredStartDate().toString());

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
    private Project mapResultSetToProject(ResultSet rs) throws SQLException{
        Project project = new Project();

        project.setId(rs.getLong("id"));
        project.setTitle(rs.getString("title"));
        project.setDescription(rs.getString("description"));
        project.setProjectStatus(ProjectStatus.valueOf(rs.getString("status")));
        project.setDesiredStartDate(LocalDate.parse(rs.getString("desired_start_date")));

        return project;
    }
}
