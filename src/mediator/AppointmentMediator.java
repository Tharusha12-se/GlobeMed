package mediator;

import model.Patient;
import java.util.List;
import model.Appointment;

public interface AppointmentMediator {
    boolean addAppointmentWithNewPatient(Appointment appointment, Patient patient);
    boolean addAppointment(Appointment appointment, Patient patient);
    boolean updateAppointment(Appointment appointment, Patient patient);
    boolean deleteAppointment(int appointmentId);
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(int appointmentId);
    List<Patient> getAllPatients();
}