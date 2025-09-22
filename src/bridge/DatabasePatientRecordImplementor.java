package bridge;

import model.MySQL;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabasePatientRecordImplementor implements PatientRecordImplementor {

    @Override
    public boolean addMedicalHistory(MedicalHistory history) {
        try (Connection conn = MySQL.getFreshConnection()) {
            return addMedicalHistory(history, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addMedicalHistory(MedicalHistory history, Connection conn) {
        String sql = "INSERT INTO medical_history (diagnosis, date_diagnosed, notes, createdBy, patient_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, history.getDiagnosis());
            stmt.setDate(2, new java.sql.Date(history.getDateDiagnosed().getTime()));
            stmt.setString(3, history.getNotes());
            stmt.setInt(4, history.getCreatedBy());
            stmt.setInt(5, history.getPatientId());
            int result = stmt.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            System.err.println("Error adding medical history: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<MedicalHistory> getMedicalHistory(int patientId) {
        List<MedicalHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM medical_history WHERE patient_id = ? ORDER BY date_diagnosed DESC";

        try (Connection conn = MySQL.getFreshConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    MedicalHistory history = new MedicalHistory();
                    history.setHistoryId(rs.getInt("historyid"));
                    history.setDiagnosis(rs.getString("diagnosis"));
                    history.setDateDiagnosed(rs.getDate("date_diagnosed"));
                    history.setNotes(rs.getString("notes"));
                    history.setPatientId(rs.getInt("patient_id"));
                    history.setCreatedBy(rs.getInt("createdBy"));
                    historyList.add(history);
                }
            }
            System.out.println("Retrieved " + historyList.size() + " medical history records for patient " + patientId);

        } catch (SQLException e) {
            System.err.println("Error getting medical history: " + e.getMessage());
            e.printStackTrace();
        }
        return historyList;
    }

    @Override
    public boolean addTreatmentPlan(TreatmentPlan plan) {
        try (Connection conn = MySQL.getFreshConnection()) {
            return addTreatmentPlan(plan, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addTreatmentPlan(TreatmentPlan plan, Connection conn) throws SQLException {
        String sql = "INSERT INTO treatment_plan (startDate, endDate, instructions, prescribedBy, patient_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(plan.getStartDate().getTime()));
            stmt.setDate(2, new java.sql.Date(plan.getEndDate().getTime()));
            stmt.setString(3, plan.getInstructions());
            stmt.setInt(4, plan.getPrescribedBy());
            stmt.setInt(5, plan.getPatientId());
            return stmt.executeUpdate() > 0;
        }
    }

    @Override
    public List<TreatmentPlan> getTreatmentPlans(int patientId) {
        List<TreatmentPlan> planList = new ArrayList<>();
        String sql = "SELECT * FROM treatment_plan WHERE patient_id = ? ORDER BY startDate DESC";

        try (Connection conn = MySQL.getFreshConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TreatmentPlan plan = new TreatmentPlan();
                    plan.setPlanId(rs.getInt("pland"));
                    plan.setStartDate(rs.getDate("startDate"));
                    plan.setEndDate(rs.getDate("endDate"));
                    plan.setInstructions(rs.getString("instructions"));
                    plan.setPatientId(rs.getInt("patient_id"));
                    plan.setPrescribedBy(rs.getInt("prescribedBy"));
                    planList.add(plan);
                }
            }
            System.out.println("Retrieved " + planList.size() + " treatment plans for patient " + patientId);

        } catch (SQLException e) {
            System.err.println("Error getting treatment plans: " + e.getMessage());
            e.printStackTrace();
        }
        return planList;
    }

    @Override
    public String getPatientInfo(int patientId) {
        String sql = "SELECT * FROM patient WHERE patient_id = ?";
        String patientInfo = "Patient not found";

        try (Connection conn = MySQL.getFreshConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    patientInfo = "Name: " + rs.getString("name") +
                                ", Mobile: " + rs.getString("mobile") +
                                ", Age: " + rs.getString("age") +
                                ", NIC: " + rs.getString("nic");
                    System.out.println("Patient info retrieved: " + patientInfo);
                }
            }

        } catch (SQLException e) {
            System.err.println("Error getting patient info: " + e.getMessage());
            e.printStackTrace();
        }
        return patientInfo;
    }

    public boolean updateAppointmentStatus(int appointmentId, int statusId, Connection conn) {
        String sql = "UPDATE appointment SET status_id = ? WHERE appointment_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, statusId);
            stmt.setInt(2, appointmentId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating appointment status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}