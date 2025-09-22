/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bridge;

import java.util.List;

/**
 *
 * @author User
 */
public abstract class PatientRecordManager {
    protected PatientRecordImplementor implementor;

    // Constructor with implementor
    public PatientRecordManager(PatientRecordImplementor implementor) {
        this.implementor = implementor;
    }

    // Medical History operations
    public abstract boolean addMedicalHistory(MedicalHistory history);
    public abstract List<MedicalHistory> getMedicalHistory(int patientId);

    // Treatment Plan operations
    public abstract boolean addTreatmentPlan(TreatmentPlan plan);
    public abstract List<TreatmentPlan> getTreatmentPlans(int patientId);

    // Patient information
    public abstract String getPatientInfo(int patientId);

}