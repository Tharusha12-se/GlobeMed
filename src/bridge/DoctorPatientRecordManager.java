package bridge;

import java.util.List;

public class DoctorPatientRecordManager extends PatientRecordManager {

    public DoctorPatientRecordManager(PatientRecordImplementor implementor) {
        super(implementor);
    }

    @Override
    public boolean addMedicalHistory(MedicalHistory history) {
        System.out.println("Doctor adding medical history for patient: " + history.getPatientId());
        return implementor.addMedicalHistory(history);
    }

    @Override
    public List<MedicalHistory> getMedicalHistory(int patientId) {
        System.out.println("Doctor accessing medical history for patient: " + patientId);
        return implementor.getMedicalHistory(patientId);
    }

    @Override
    public boolean addTreatmentPlan(TreatmentPlan plan) {
        System.out.println("Doctor adding treatment plan for patient: " + plan.getPatientId());
        return implementor.addTreatmentPlan(plan);
    }

    @Override
    public List<TreatmentPlan> getTreatmentPlans(int patientId) {
        System.out.println("Doctor accessing treatment plans for patient: " + patientId);
        return implementor.getTreatmentPlans(patientId);
    }

    @Override
    public String getPatientInfo(int patientId) {
        System.out.println("Doctor accessing patient info for: " + patientId);
        return implementor.getPatientInfo(patientId);
    }

    public String getPatientSummary(int patientId) {
        String info = getPatientInfo(patientId);
        List<MedicalHistory> history = getMedicalHistory(patientId);
        List<TreatmentPlan> plans = getTreatmentPlans(patientId);

        return "Patient Summary:\n" + info +
                "\nMedical History Entries: " + history.size() +
                "\nActive Treatment Plans: " + plans.size();
    }
}