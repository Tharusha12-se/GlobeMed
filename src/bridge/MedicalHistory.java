package bridge;

import java.sql.Date;

public class MedicalHistory {
    private int historyId;
    private String diagnosis;
    private Date dateDiagnosed;
    private String notes;
    private int patientId;
    private int createdBy;
    private Date createdAt;

    // Constructors
    public MedicalHistory() {}

    public MedicalHistory(int historyId, String diagnosis, Date dateDiagnosed, String notes, int patientId, int createdBy) {
        this.historyId = historyId;
        this.diagnosis = diagnosis;
        this.dateDiagnosed = dateDiagnosed;
        this.notes = notes;
        this.patientId = patientId;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public int getHistoryId() { return historyId; }
    public void setHistoryId(int historyId) { this.historyId = historyId; }
    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }
    public Date getDateDiagnosed() { return dateDiagnosed; }
    public void setDateDiagnosed(Date dateDiagnosed) { this.dateDiagnosed = dateDiagnosed; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getCreatedBy() { return createdBy; }
    public void setCreatedBy(int createdBy) { this.createdBy = createdBy; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}