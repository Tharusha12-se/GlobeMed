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
import java.util.stream.Collectors;

public class FinancialReportVisitor implements ReportVisitor {
    private StringBuilder report = new StringBuilder();
    private List<Bill> bills = new ArrayList<>();
    private Patient currentPatient;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private double totalAmount = 0;
    private double paidAmount = 0;
    private double pendingAmount = 0;

    @Override
    public void visit(Patient patient) {
        this.currentPatient = patient;
        report.append("=== FINANCIAL REPORT ===\n\n");
        report.append("Person: ").append(patient.getName()).append("\n");
        report.append("ID: ").append(patient.getId()).append("\n");
        report.append("Mobile: ").append(patient.getMobile()).append("\n");
        report.append("\n");
    }

    @Override
    public void visit(MedicalHistory medicalHistory) {
        // Not used in financial report
    }

    @Override
    public void visit(TreatmentPlan treatmentPlan) {
        // Not used in financial report
    }

    @Override
    public void visit(Bill bill) {
        bills.add(bill);
        totalAmount += bill.getAmount();

        if ("PAID".equals(bill.getStatus()) || "INSURANCE_APPROVED".equals(bill.getStatus())) {
            paidAmount += bill.getAmount();
        } else {
            pendingAmount += bill.getAmount();
        }
    }

    @Override
    public String getReport() {
        generateBillDetailsSection();
        generateSummarySection();
        return report.toString();
    }

    private void generateBillDetailsSection() {
        report.append("=== BILL DETAILS ===\n");
        if (bills.isEmpty()) {
            report.append("No bills found.\n");
        } else {
            report.append(String.format("%-10s %-12s %-15s %-12s %-20s %-15s\n",
                    "Bill ID", "Date", "Amount", "Status", "Service Type", "Insurance"));
            report.append("--------------------------------------------------------------------------------\n");

            for (Bill bill : bills) {
                report.append(String.format("%-10d %-12s $%-14.2f %-12s %-20s %-15s\n",
                        bill.getBillId(),
                        dateFormat.format(bill.getDate()),
                        bill.getAmount(),
                        bill.getStatus(),
                        bill.getServiceType(),
                        bill.getInsuranceProvider() != null ? bill.getInsuranceProvider() : "N/A"));
            }
        }
        report.append("\n");
    }

    private void generateSummarySection() {
        report.append("=== FINANCIAL SUMMARY ===\n");
        report.append(String.format("Total Bills: %d\n", bills.size()));
        report.append(String.format("Total Amount: $%.2f\n", totalAmount));
        report.append(String.format("Paid Amount: $%.2f\n", paidAmount));
        report.append(String.format("Pending Amount: $%.2f\n", pendingAmount));
        report.append(String.format("Outstanding Balance: $%.2f\n", pendingAmount));
    }
    // Add this method to your existing FinancialReportVisitor class
    public String getAdminFinancialReport(List<Bill> allBills) {
        reset();

        // Create admin summary header
        report.append("=== ADMIN FINANCIAL SUMMARY REPORT ===\n\n");
        report.append("Generated on: ").append(new java.util.Date()).append("\n");
        report.append("Total Records: ").append(allBills.size()).append("\n\n");

        // Process all bills
        for (Bill bill : allBills) {
            visit(bill);
        }

        generateDetailedAnalysis();
        return report.toString();
    }

    private void generateDetailedAnalysis() {
        report.append("=== DETAILED ANALYSIS ===\n\n");

        // Status breakdown
        long paidCount = bills.stream().filter(b -> "PAID".equals(b.getStatus())).count();
        long pendingCount = bills.stream().filter(b -> "PENDING".equals(b.getStatus())).count();
        long insuranceCount = bills.stream().filter(b -> b.getInsuranceProvider() != null).count();

        report.append("Status Breakdown:\n");
        report.append(String.format("  Paid Bills: %d (%.1f%%)\n", paidCount, (paidCount * 100.0 / bills.size())));
        report.append(String.format("  Pending Bills: %d (%.1f%%)\n", pendingCount, (pendingCount * 100.0 / bills.size())));
        report.append(String.format("  Insurance Claims: %d\n", insuranceCount));
        report.append("\n");

        // Service type analysis
        report.append("Service Type Analysis:\n");
        bills.stream()
                .collect(Collectors.groupingBy(Bill::getServiceType, Collectors.counting()))
                .forEach((service, count) -> {
                    report.append(String.format("  %s: %d bills\n", service, count));
                });
        report.append("\n");

        // Monthly revenue trend (you'd need to implement this based on date data)
        report.append("Revenue Trends:\n");
        report.append("  [Monthly trend analysis would be implemented here]\n");
    }
    @Override
    public void reset() {
        report = new StringBuilder();
        bills.clear();
        currentPatient = null;
        totalAmount = 0;
        paidAmount = 0;
        pendingAmount = 0;
    }
}