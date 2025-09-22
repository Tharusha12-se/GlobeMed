/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visitor;

import bridge.MedicalHistory;
import bridge.TreatmentPlan;
import model.Patient;

import model.Bill;

public interface ReportVisitor {
    void visit(Patient patient);
    void visit(MedicalHistory medicalHistory);
    void visit(TreatmentPlan treatmentPlan);
    void visit(Bill bill);

    String getReport();
    void reset();
}