/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package visitor;



import bridge.MedicalHistory;
import bridge.TreatmentPlan;
import java.util.ArrayList;
import model.Patient;
import java.sql.*;
import model.Bill;
import visitor.ReportVisitor;
import visitor.TreatmentSummaryVisitor;
import visitor.FinancialReportVisitor;
import visitor.DiagnosticReportVisitor;
import java.util.List;
import model.MySQL;


public class ReportService {

    public String generateTreatmentSummaryReport(Patient patient, List<MedicalHistory> medicalHistories, List<TreatmentPlan> treatmentPlans) {
        TreatmentSummaryVisitor visitor = new TreatmentSummaryVisitor();

        // Visit all elements
        visitor.visit(patient);
        for (MedicalHistory history : medicalHistories) {
            visitor.visit(history);
        }
        for (TreatmentPlan plan : treatmentPlans) {
            visitor.visit(plan);
        }

        return visitor.getReport();
    }

    public String generateFinancialReport(Patient patient, List<Bill> bills) {
        FinancialReportVisitor visitor = new FinancialReportVisitor();

        // Visit all elements
        visitor.visit(patient);
        for (Bill bill : bills) {
            visitor.visit(bill);
        }

        return visitor.getReport();
    }

    public String generateDiagnosticReport(Patient patient, List<MedicalHistory> medicalHistories) {
        DiagnosticReportVisitor visitor = new DiagnosticReportVisitor();

        // Visit all elements
        visitor.visit(patient);
        for (MedicalHistory history : medicalHistories) {
            visitor.visit(history);
        }

        return visitor.getReport();
    }
    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM billing ORDER BY date DESC";

        try {
            Connection conn = MySQL.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    Bill bill = new Bill();
                    bill.setBillId(rs.getInt("bill_id"));
                    bill.setAmount(rs.getDouble("amount"));
                    bill.setStatus(rs.getString("status"));
                    bill.setDate(rs.getDate("date"));
                    bill.setPatientId(rs.getInt("patient_id"));
                    bill.setAppointmentId(rs.getInt("appointment_id"));
                    bill.setServiceType(rs.getString("service_type"));
                    bill.setInsuranceProvider(rs.getString("insurance_provider"));
                    bill.setInsuranceClaimId(rs.getString("insurance_claim_id"));
                    bills.add(bill);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }


    public List<Bill> getBillsByStatus(String status) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE status = ? ORDER BY date DESC";

        try (Connection conn = MySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                // ... same mapping as getAllBills()
                bills.add(bill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }

    public List<Bill> getBillsByDateRange(java.sql.Date startDate, java.sql.Date endDate) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE date BETWEEN ? AND ? ORDER BY date DESC";

        try (Connection conn = MySQL.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, startDate);
            stmt.setDate(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Bill bill = new Bill();
                // ... same mapping as getAllBills()
                bills.add(bill);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }
    // Generic method to generate any type of report
    public String generateReport(ReportVisitor visitor, Patient patient, List<?> items) {
        visitor.reset();
        visitor.visit(patient);

        for (Object item : items) {
            if (item instanceof MedicalHistory) {
                visitor.visit((MedicalHistory) item);
            } else if (item instanceof TreatmentPlan) {
                visitor.visit((TreatmentPlan) item);
            } else if (item instanceof Bill) {
                visitor.visit((Bill) item);
            }
        }

        return visitor.getReport();
    }
}