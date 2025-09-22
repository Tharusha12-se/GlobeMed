package chain;

import model.Bill;
import model.MySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BillingService {

public boolean saveBill(Bill bill) {
    String sql = "INSERT INTO billing (amount, status, date, patient_id, appointment_id, service_type, insurance_provider, insurance_claim_id) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    // Validate appointment_id
    String checkSql = "SELECT COUNT(*) FROM appointment WHERE appointment_id = ?";
    Connection conn = null;
    try {
        conn = MySQL.getConnection(); // Get a new connection
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, bill.getAppointmentId());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) == 0) {
                throw new IllegalArgumentException("Invalid appointment_id: " + bill.getAppointmentId());
            }
        }
        
        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setDouble(1, bill.getAmount());
            stmt.setString(2, bill.getStatus());
            stmt.setDate(3, bill.getDate());
            stmt.setInt(4, bill.getPatientId());
            stmt.setInt(5, bill.getAppointmentId());
            stmt.setString(6, bill.getServiceType());
            stmt.setString(7, bill.getInsuranceProvider());
            stmt.setString(8, bill.getInsuranceClaimId());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        bill.setBillId(rs.getInt(1));
                    }
                }
                return true;
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        if (conn != null) {
            try {
                conn.close(); // Ensure the connection is closed manually if not managed by pool
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return false;
}

    public boolean updateBillStatus(int billId, String status) {
        String sql = "UPDATE billing SET status = ? WHERE bill_id = ?";

        try (Connection conn = MySQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, billId);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Bill> getBillsByPatient(int patientId) {
        List<Bill> bills = new ArrayList<>();
        String sql = "SELECT * FROM billing WHERE patient_id = ? ORDER BY date DESC";

        try (Connection conn = MySQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

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

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bills;
    }

    public Bill getBillById(int billId) {
        String sql = "SELECT * FROM billing WHERE bill_id = ?";

        try (Connection conn = MySQL.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, billId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
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

                return bill;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
