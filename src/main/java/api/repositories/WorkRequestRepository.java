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

public class WorkRequestRepository {
    private final DatabaseManager db;

    public WorkRequestRepository(DatabaseManager db) {
        this.db = db;
    }

    public void save(WorkRequest request) throws SQLException {
        String sql = """
            INSERT INTO work_requests 
            (title, description, work_type, desired_start_date, status, resident_email)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
        
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, request.getTitle());
            pstmt.setString(2, request.getDescription());
            pstmt.setString(3, request.getWorkType());
            pstmt.setString(4, request.getDesiredStartDate().toString());
            pstmt.setString(5, request.getStatus().name());
            pstmt.setString(6, request.getResidentEmail());

            pstmt.executeUpdate();
            
            // Get the last inserted ID
            try (Statement stmt = db.getConnection().createStatement()) {
                ResultSet rs = stmt.executeQuery("SELECT last_insert_rowid()");
                if (rs.next()) {
                    request.setId(rs.getLong(1));
                }
            }
        }
    }

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

    public boolean residentExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM residents WHERE email = ?";
        try (PreparedStatement pstmt = db.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

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
