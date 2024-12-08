package models;

public class Candidature {
    private Long id;
    private Long workRequestId;
    private String intervenantEmail;
    private CandidatureStatus status;
    private String residentMessage; // Message optionnel du résident lorsqu'il sélectionne une candidature
    private boolean confirmedByIntervenant ; // true une fois que l'intervenant a confirmé la candidature

    public Candidature() {}
    public Candidature(Long workRequestId, String intervenantEmail) {
        this.workRequestId = workRequestId;
        this.intervenantEmail = intervenantEmail;
        this.status = CandidatureStatus.SUBMITTED;
        this.confirmedByIntervenant = false;
    }

    public Long getId() 
    { 
        return id; 
    }
    public void setId(Long id) 
    {
         this.id = id;
    }

    public Long getWorkRequestId() 
    { 
        return workRequestId; 
    }
    public void setWorkRequestId(Long workRequestId) 
    {
         this.workRequestId = workRequestId;
    }

    public String getIntervenantEmail() 
    { 
        return intervenantEmail; 
    }

    public void setIntervenantEmail(String intervenantEmail) 
    {
         this.intervenantEmail = intervenantEmail;
    }

    public CandidatureStatus getStatus() 
    { 
        return status; 
    }

    public void setStatus(CandidatureStatus status) 
    {
         this.status = status;
    }

    public String getResidentMessage() 
    { 
        return residentMessage; 
    }

    public void setResidentMessage(String residentMessage) 
    {
         this.residentMessage = residentMessage;
    }

    public boolean isConfirmedByIntervenant() 
    { 
        return confirmedByIntervenant; 
    }

    public void setConfirmedByIntervenant(boolean confirmedByIntervenant) 
    {
         this.confirmedByIntervenant = confirmedByIntervenant;
    }

    @Override
    public String toString() {
        return String.format("Candidature #%d\nIntervenant: %s\nStatut: %s\nMessage Résident: %s\nConfirmée par Intervenant: %s",
        id, intervenantEmail, status, residentMessage == null ? "Aucun" : residentMessage, confirmedByIntervenant ? "Oui" : "Non");
    }

}
