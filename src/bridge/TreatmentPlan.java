package bridge;

import java.sql.Date;

public class TreatmentPlan {
    private int planId;
    private Date startDate;
    private Date endDate;
    private String instructions;
    private int medicineId;
    private int patientId;
    private int prescribedBy;
    private Date createdAt;

    // Constructors
    public TreatmentPlan() {}

    public TreatmentPlan(int planId, Date startDate, Date endDate, String instructions, int patientId, int prescribedBy) {
        this.planId = planId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.instructions = instructions;
        this.patientId = patientId;
        this.prescribedBy = prescribedBy;
    }

    // Getters and Setters
    public int getPlanId() { return planId; }
    public void setPlanId(int planId) { this.planId = planId; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }
    public int getMedicineId() { return medicineId; }
    public void setMedicineId(int medicineId) { this.medicineId = medicineId; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getPrescribedBy() { return prescribedBy; }
    public void setPrescribedBy(int prescribedBy) { this.prescribedBy = prescribedBy; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}