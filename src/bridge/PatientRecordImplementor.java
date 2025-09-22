/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bridge;
import auth.SessionManager;
import model.MySQL;
import model.Patient;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author User
 */
public interface PatientRecordImplementor  {

    // Medical History operations
    boolean addMedicalHistory(MedicalHistory history);
    List<MedicalHistory> getMedicalHistory(int patientId);

    // Treatment Plan operations
    boolean addTreatmentPlan(TreatmentPlan plan);
    List<TreatmentPlan> getTreatmentPlans(int patientId);

    // Patient information
    String getPatientInfo(int patientId);
}