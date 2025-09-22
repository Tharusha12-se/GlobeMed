
package model;

import java.sql.Date;

public class Appointment {
    private int appointmentId;
    private String details;
    private String time;
    private int userId;
    private Date channelDate;
    private Patient patient;
    private String doctorName;
    // Constructors
    public Appointment() {}
    
   public Appointment(int appointmentId, String time, String details, int userId, Date channelDate) {
    this.appointmentId = appointmentId;
    this.time = time;
    this.details = details;
    this.userId = userId;
    this.channelDate = channelDate;
}

    // Getters and Setters
    public int getAppointmentId() { return appointmentId; }
    public void setAppointmentId(int appointmentId) { this.appointmentId = appointmentId; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public Date getChannelDate() { return channelDate; }
    public void setChannelDate(Date channelDate) { this.channelDate = channelDate; }
    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }
}
