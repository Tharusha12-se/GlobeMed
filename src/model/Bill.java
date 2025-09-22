/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.Date;
/**
 *
 * @author User
 */
public class Bill {
    private int billId;
    private double amount;
    private String status; // PENDING, PAID, INSURANCE_PENDING, REJECTED, etc.
    private Date date;
    private int patientId;
    private int appointmentId;
    private String serviceType; // CONSULTATION, TREATMENT, MEDICATION
    private String insuranceProvider;
    private String insuranceClaimId;

    // Constructors
    public Bill() {}

    public Bill(int billId, double amount, String status, Date date, int patientId,
                int appointmentId, String serviceType, String insuranceProvider, String insuranceClaimId) {
        this.billId = billId;
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.patientId = patientId;
        this.appointmentId = appointmentId;
        this.serviceType = serviceType;
        this.insuranceProvider = insuranceProvider;
        this.insuranceClaimId = insuranceClaimId;
    }

    // Getters and Setters
    public int getBillId() { return billId; }
    public void setBillId(int billId) { this.billId = billId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getInsuranceProvider() { return insuranceProvider; }
    public void setInsuranceProvider(String insuranceProvider) { this.insuranceProvider = insuranceProvider; }
    public String getInsuranceClaimId() { return insuranceClaimId; }
    public void setInsuranceClaimId(String insuranceClaimId) { this.insuranceClaimId = insuranceClaimId; }
}