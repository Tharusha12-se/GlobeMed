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

public class DiagnosticReportVisitor implements ReportVisitor {
    private StringBuilder report = new StringBuilder();
    private List<MedicalHistory> diagnoses = new ArrayList<>();
    private Patient currentPatient;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void visit(Patient patient) {
        this.currentPatient = patient;
        report.append("=== DIAGNOSTIC RESULTS REPORT ===\n\n");
        report.append("Patient: ").append(patient.getName()).append("\n");
        report.append("ID: ").append(patient.getId()).append("\n");
        report.append("Age: ").append(patient.getAge()).append("\n");
        report.append("\n");
    }

    @Override
    public void visit(MedicalHistory medicalHistory) {
        diagnoses.add(medicalHistory);
    }

    @Override
    public void visit(TreatmentPlan treatmentPlan) {
        // Not used in diagnostic report
    }

    @Override
    public void visit(Bill bill) {
        // Not used in diagnostic report
    }

    @Override
    public String getReport() {
        generateDiagnosesSection();
        generateStatisticsSection();
        return report.toString();
    }

    private void generateDiagnosesSection() {
        report.append("=== DIAGNOSES ===\n");
        if (diagnoses.isEmpty()) {
            report.append("No diagnostic records found.\n");
        } else {
            for (MedicalHistory diagnosis : diagnoses) {
                report.append("Date: ").append(dateFormat.format(diagnosis.getDateDiagnosed())).append("\n");
                report.append("Diagnosis: ").append(diagnosis.getDiagnosis()).append("\n");
                report.append("Clinical Notes: ").append(diagnosis.getNotes()).append("\n");
                report.append("---\n");
            }
        }
        report.append("\n");
    }

    private void generateStatisticsSection() {
        report.append("=== DIAGNOSTIC STATISTICS ===\n");
        report.append("Total Diagnoses: ").append(diagnoses.size()).append("\n");

        if (!diagnoses.isEmpty()) {
            report.append("First Diagnosis: ").append(dateFormat.format(diagnoses.get(diagnoses.size()-1).getDateDiagnosed())).append("\n");
            report.append("Most Recent: ").append(dateFormat.format(diagnoses.get(0).getDateDiagnosed())).append("\n");
        }
    }

    @Override
    public void reset() {
        report = new StringBuilder();
        diagnoses.clear();
        currentPatient = null;
    }
}