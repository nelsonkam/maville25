package models;

import java.time.LocalDateTime;

/**
 * Cette classe représente une notification pour un résident.
 */
public class Notification {
    private Long id;
    private String residentEmail;
    private String message;
    private LocalDateTime dateCreated;
    private boolean isRead;

    public Notification() {}

    public Notification(String residentEmail, String message) {
        this.residentEmail = residentEmail;
        this.message = message;
        this.dateCreated = LocalDateTime.now();
        this.isRead = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResidentEmail() {
        return residentEmail;
    }

    public void setResidentEmail(String residentEmail) {
        this.residentEmail = residentEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s", dateCreated.toString(), message);
    }
}
