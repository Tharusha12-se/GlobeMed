package mediator;

import model.Patient;
import model.MySQL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Appointment;

public class AppointmentMediatorImpl implements AppointmentMediator {

    @Override
    public boolean addAppointment(Appointment appointment, Patient patient) {
        Connection conn = null;
        try {
            conn = MySQL.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Check if patient exists or create new one
            int patientId = 0;
            String mobile = patient.getMobile();
            if (mobile != null && !mobile.trim().isEmpty()) {
                // Check if patient with this mobile number already exists
                String checkSql = "SELECT id FROM patient WHERE mobile = ?";
                try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                    checkStmt.setString(1, mobile);
                    try (ResultSet rs = checkStmt.executeQuery()) {
                        if (rs.next()) {
                            patientId = rs.getInt("id");
                        } else {
                            // Insert new patient
                            String patientSql = "INSERT INTO patient (name, mobile, address, age, nic, branch_id) VALUES (?, ?, ?, ?, ?, ?)";
                            try (PreparedStatement patientStmt = conn.prepareStatement(patientSql, Statement.RETURN_GENERATED_KEYS)) {
                                patientStmt.setString(1, patient.getName());
                                patientStmt.setString(2, patient.getMobile());
                                patientStmt.setString(3, patient.getAddress());
                                patientStmt.setString(4, patient.getAge());
                                patientStmt.setDouble(5, patient.getNic());
                                patientStmt.setInt(6, patient.getBranch_id());
                                patientStmt.executeUpdate();
                                
                                try (ResultSet generatedKeys = patientStmt.getGeneratedKeys()) {
                                    if (generatedKeys.next()) {
                                        patientId = generatedKeys.getInt(1);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                throw new IllegalArgumentException("Mobile number is required");
            }
            
            // 2. Insert appointment
            String appointmentSql = "INSERT INTO appointment (details, time, users_id, appointmentDate, patient_id) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement appointmentStmt = conn.prepareStatement(appointmentSql)) {
                appointmentStmt.setString(1, appointment.getDetails());
                appointmentStmt.setString(2, appointment.getTime());
                appointmentStmt.setInt(3, appointment.getUserId());
                appointmentStmt.setDate(4, new java.sql.Date(appointment.getChannelDate().getTime()));
                appointmentStmt.setInt(5, patientId);
                appointmentStmt.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateAppointment(Appointment appointment, Patient patient) {
        Connection conn = null;
        try {
            conn = MySQL.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Update patient
            String patientSql = "UPDATE patient SET name=?, mobile=?, address=?, age=?, nic=?, branch_id=? WHERE id=?";
            try (PreparedStatement patientStmt = conn.prepareStatement(patientSql)) {
                patientStmt.setString(1, patient.getName());
                patientStmt.setString(2, patient.getMobile());
                patientStmt.setString(3, patient.getAddress());
                patientStmt.setString(4, patient.getAge());
                patientStmt.setDouble(5, patient.getNic());
                patientStmt.setInt(6, patient.getBranch_id());
                patientStmt.setInt(7, patient.getId());
                patientStmt.executeUpdate();
            }
            
            // 2. Update appointment
            String appointmentSql = "UPDATE appointment SET details=?, time=?, users_id=?, appointmentDate=? WHERE id=?";
            try (PreparedStatement appointmentStmt = conn.prepareStatement(appointmentSql)) {
                appointmentStmt.setString(1, appointment.getDetails());
                appointmentStmt.setString(2, appointment.getTime());
                appointmentStmt.setInt(3, appointment.getUserId());
                appointmentStmt.setDate(4, new java.sql.Date(appointment.getChannelDate().getTime()));
                appointmentStmt.setInt(5, appointment.getAppointmentId());
                appointmentStmt.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean deleteAppointment(int appointmentId) {
        Connection conn = null;
        try {
            conn = MySQL.getConnection();
            conn.setAutoCommit(false);
            
            // Delete appointment
            String deleteAppSql = "DELETE FROM appointment WHERE id = ?";
            try (PreparedStatement appStmt = conn.prepareStatement(deleteAppSql)) {
                appStmt.setInt(1, appointmentId);
                appStmt.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<Appointment> getAllAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try {
            String sql = "SELECT a.id, a.details, a.time, a.users_id, a.appointmentDate, " +
                         "p.id as patient_id, p.name, p.mobile, p.address, p.age, p.nic, p.branch_id " +
                         "FROM appointment a " +
                         "JOIN patient p ON a.patient_id = p.id " +
                         "ORDER BY a.appointmentDate DESC";

            try (ResultSet rs = MySQL.execute(sql)) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("id"));
                    appointment.setDetails(rs.getString("details"));
                    appointment.setTime(rs.getString("time"));
                    appointment.setUserId(rs.getInt("users_id"));
                    appointment.setChannelDate(rs.getDate("appointmentDate"));

                    Patient patient = new Patient();
                    patient.setId(rs.getInt("patient_id"));
                    patient.setName(rs.getString("name"));
                    patient.setMobile(rs.getString("mobile"));
                    patient.setAddress(rs.getString("address"));
                    patient.setAge(rs.getString("age"));
                    patient.setNic(rs.getDouble("nic"));
                    patient.setBranch_id(rs.getInt("branch_id"));

                    appointment.setPatient(patient);
                    appointments.add(appointment);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appointments;
    }

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        try {
            String sql = "SELECT a.id, a.details, a.time, a.users_id, a.appointmentDate, " +
                         "p.id as patient_id, p.name, p.mobile, p.address, p.age, p.nic, p.branch_id " +
                         "FROM appointment a " +
                         "JOIN patient p ON a.patient_id = p.id " +
                         "WHERE a.id = " + appointmentId;
            
            try (ResultSet rs = MySQL.execute(sql)) {
                if (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setAppointmentId(rs.getInt("id"));
                    appointment.setDetails(rs.getString("details"));
                    appointment.setTime(rs.getString("time"));
                    appointment.setUserId(rs.getInt("users_id"));
                    appointment.setChannelDate(rs.getDate("appointmentDate"));
                    
                    Patient patient = new Patient();
                    patient.setId(rs.getInt("patient_id"));
                    patient.setName(rs.getString("name"));
                    patient.setMobile(rs.getString("mobile"));
                    patient.setAddress(rs.getString("address"));
                    patient.setAge(rs.getString("age"));
                    patient.setNic(rs.getDouble("nic"));
                    patient.setBranch_id(rs.getInt("branch_id"));
                    
                    appointment.setPatient(patient);
                    return appointment;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        try {
            String sql = "SELECT * FROM patient ORDER BY name";
            try (ResultSet rs = MySQL.execute(sql)) {
                while (rs.next()) {
                    Patient patient = new Patient();
                    patient.setId(rs.getInt("id"));
                    patient.setName(rs.getString("name"));
                    patient.setMobile(rs.getString("mobile"));
                    patient.setAddress(rs.getString("address"));
                    patient.setAge(rs.getString("age"));
                    patient.setNic(rs.getDouble("nic"));
                    patient.setBranch_id(rs.getInt("branch_id"));
                    patients.add(patient);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return patients;
    }

    @Override
    public boolean addAppointmentWithNewPatient(Appointment appointment, Patient patient) {
        Connection conn = null;
        try {
            conn = MySQL.getConnection();
            conn.setAutoCommit(false);
            
            // 1. Insert new patient
            String patientSql = "INSERT INTO patient (name, mobile, address, age, nic, branch_id) VALUES (?, ?, ?, ?, ?, ?)";
            int patientId = 0;
            
            try (PreparedStatement patientStmt = conn.prepareStatement(patientSql, Statement.RETURN_GENERATED_KEYS)) {
                patientStmt.setString(1, patient.getName());
                patientStmt.setString(2, patient.getMobile());
                patientStmt.setString(3, patient.getAddress());
                patientStmt.setString(4, patient.getAge());
                patientStmt.setDouble(5, patient.getNic());
                patientStmt.setInt(6, patient.getBranch_id());
                patientStmt.executeUpdate();
                
                try (ResultSet rs = patientStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        patientId = rs.getInt(1);
                    }
                }
            }
            
            // 2. Insert appointment
            String appointmentSql = "INSERT INTO appointment (details, time, users_id, appointmentDate, patient_id) VALUES (?, ?, ?, ?, ?)";
            
            try (PreparedStatement appointmentStmt = conn.prepareStatement(appointmentSql)) {
                appointmentStmt.setString(1, appointment.getDetails());
                appointmentStmt.setString(2, appointment.getTime());
                appointmentStmt.setInt(3, appointment.getUserId());
                appointmentStmt.setDate(4, new java.sql.Date(appointment.getChannelDate().getTime()));
                appointmentStmt.setInt(5, patientId);
                appointmentStmt.executeUpdate();
            }
            
            conn.commit();
            return true;
            
        } catch (Exception e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}