/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visitor;

import bridge.MedicalHistory;
import bridge.TreatmentPlan;
import model.Patient;
import model.Bill;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TreatmentSummaryVisitor implements ReportVisitor {
    private StringBuilder report = new StringBuilder();
    private List<MedicalHistory> medicalHistories = new ArrayList<>();
    private List<TreatmentPlan> treatmentPlans = new ArrayList<>();
    private Patient currentPatient;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void visit(Patient patient) {
        this.currentPatient = patient;
        report.append("=== PATIENT TREATMENT SUMMARY ===\n\n");
        report.append("Patient: ").append(patient.getName()).append("\n");
        report.append("ID: ").append(patient.getId()).append("\n");
        report.append("Age: ").append(patient.getAge()).append("\n");
        report.append("Mobile: ").append(patient.getMobile()).append("\n");
        report.append("Address: ").append(patient.getAddress()).append("\n");
        report.append("\n");
    }

    @Override
    public void visit(MedicalHistory medicalHistory) {
        medicalHistories.add(medicalHistory);
    }

    @Override
    public void visit(TreatmentPlan treatmentPlan) {
        treatmentPlans.add(treatmentPlan);
    }

    @Override
    public void visit(Bill bill) {
        // Not used in treatment summary
    }

    @Override
    public String getReport() {
        generateMedicalHistorySection();
        generateTreatmentPlansSection();
        return report.toString();
    }

    private void generateMedicalHistorySection() {
        report.append("=== MEDICAL HISTORY ===\n");
        if (medicalHistories.isEmpty()) {
            report.append("No medical history records found.\n");
        } else {
            for (MedicalHistory history : medicalHistories) {
                report.append("Date: ").append(dateFormat.format(history.getDateDiagnosed())).append("\n");
                report.append("Diagnosis: ").append(history.getDiagnosis()).append("\n");
                report.append("Notes: ").append(history.getNotes()).append("\n");
                report.append("---\n");
            }
        }
        report.append("\n");
    }

    private void generateTreatmentPlansSection() {
        report.append("=== TREATMENT PLANS ===\n");
        if (treatmentPlans.isEmpty()) {
            report.append("No treatment plans found.\n");
        } else {
            for (TreatmentPlan plan : treatmentPlans) {
                report.append("Plan ID: ").append(plan.getPlanId()).append("\n");
                report.append("Period: ").append(dateFormat.format(plan.getStartDate()));
                if (plan.getEndDate() != null) {
                    report.append(" to ").append(dateFormat.format(plan.getEndDate()));
                }
                report.append("\n");
                report.append("Instructions: ").append(plan.getInstructions()).append("\n");
                report.append("Medicine ID: ").append(plan.getMedicineId()).append("\n");
                report.append("---\n");
            }
        }
    }

    @Override
    public void reset() {
        report = new StringBuilder();
        medicalHistories.clear();
        treatmentPlans.clear();
        currentPatient = null;
    }
}