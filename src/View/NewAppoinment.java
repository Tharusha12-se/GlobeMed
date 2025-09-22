/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package View;

import com.formdev.flatlaf.FlatDarculaLaf;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import mediator.AppointmentMediator;
import mediator.AppointmentMediatorImpl;
import model.Appointment;
import model.MySQL;
import model.Patient;
import auth.SessionManager;
import model.User;

/**
 *
 * @author ASUS
 */
public class NewAppoinment extends javax.swing.JDialog {

    private AppointmentMediator appointmentMediator;

    /**
     * Creates new form NewAppoinment
     */
    public NewAppoinment(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       // loadBranch();
        loadDoctors();
        appointmentMediator = new AppointmentMediatorImpl();

    }
    
    
    private void loadDoctors (){
        
        try {
            
            ResultSet resultSet = MySQL.execute("SELECT * FROM `users` WHERE `user_role_id`='2'");
            
            Vector v = new Vector();
            v.add("SELECT DOCTOR");
            
            while (resultSet.next()) {                
                v.add(resultSet.getString("fname")+" "+resultSet.getString("lname"));
                
            }
            
            DefaultComboBoxModel model = (DefaultComboBoxModel) jComboBox1.getModel();
            model.removeAllElements();
            
            model.addAll(v);
            jComboBox1.setSelectedIndex(0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    
    private Integer getDoctorIdFromName(String doctorName) {
    try {
        String[] names = doctorName.split(" ");
        if (names.length >= 2) {
            String firstName = names[0];
            String lastName = names[1];
            
            String query = "SELECT id FROM users WHERE fname = '" + firstName + 
                          "' AND lname = '" + lastName + "' AND user_role_id = '2'";
            ResultSet rs = MySQL.execute(query);
            
            if (rs != null && rs.next()) {
                return rs.getInt("id");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

private Integer getCurrentUserBranchId() {
    try {
        String email = SessionManager.getEmail();
        if (email != null) {
            String query = "SELECT branch_id FROM users WHERE email = '" + email + "'";
            ResultSet rs = MySQL.execute(query);
            
            if (rs != null && rs.next()) {
                int branchId = rs.getInt("branch_id");
                
                // Verify this branch exists
                String checkQuery = "SELECT id FROM branch WHERE id = " + branchId;
                ResultSet checkRs = MySQL.execute(checkQuery);
                
                if (checkRs != null && checkRs.next()) {
                    return branchId;
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 1; // Fallback to branch ID 1 if available, or return null
}



private boolean createAppointmentInDB(String details, Date date, String time, Integer doctorId, Integer patientId) {
    try {
        // Format time to include seconds
        String formattedTime = time;
        if (time.length() == 5) {
            formattedTime = time + ":00";
        }
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = dateFormat.format(date);
        
        String query = "INSERT INTO appointment (details,time, users_id, appointmentDate, patient_id,  date ) " +
                      "VALUES ('" + details + "','" + formattedTime + "', " + doctorId + 
                      ", '" + dateStr + "', " + patientId + ",NOW() )";
        
        int result = MySQL.executeUpdate(query);
        return result > 0;
        
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}
    
    
    
    public void loadBranch() {
    System.out.println("=== loadBranch() called ===");
    
    // Debug session first
    SessionManager.debugSession();
    
    if (!SessionManager.isLoggedIn()) {
        System.out.println("User not logged in");
        jTextField7.setText("Please login first");
        return;
    }
    
    // Get email from session
    String email = SessionManager.getEmail();
    System.out.println("Email retrieved from session: " + email);
    
    if (email == null || email.isEmpty()) {
        System.out.println("Email is null or empty");
        jTextField7.setText("Email not found");
        return;
    }
    
    try {
        // Query to get branch information
        String query = "SELECT b.branch" +
                      "FROM users u " +
                      "INNER JOIN branch b ON u.branch_id = b.id " +
                      "WHERE u.email = '" + email + "'";
        
        System.out.println("Executing query: " + query);
        
        ResultSet resultSet = MySQL.execute(query);
        
        if (resultSet == null) {
            System.out.println("Query returned null ResultSet");
            jTextField7.setText("Database error");
            return;
        }
        
        if (resultSet.next()) {
            // Get branch information
            String branchName = resultSet.getString("branch_name");
            String branchCode = resultSet.getString("branch_code");
            
            // Set the text field
            String displayText = branchName + " (" + branchCode + ")";
            jTextField7.setText(displayText);
            
            System.out.println("Branch loaded successfully: " + displayText);
        } else {
            System.out.println("No branch found for email: " + email);
            jTextField7.setText("No branch assigned");
        }
        
    } catch (Exception e) {
        System.out.println("Error loading branch: " + e.getMessage());
        e.printStackTrace();
        jTextField7.setText("Error loading branch");
    }
    
    System.out.println("=== loadBranch() completed ===");
}
    
    
    

// Replace the getPatientIdByNIC method with this:
private Integer getPatientIdByNIC(String nic) {
    try {
        String query = "SELECT id FROM patient WHERE nic = '" + nic + "'";
        ResultSet rs = MySQL.execute(query);
        
        if (rs != null && rs.next()) {
            return rs.getInt("id");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

// Replace the createNewPatient method (if you have one) with this:
private Integer createNewPatient(String name, String mobile, String address, String age, String nic, Integer branchId) {
    try {
        String query = "INSERT INTO patient (name, mobile, address, age, nic, branch_id) " +
                      "VALUES ('" + name + "', '" + mobile + "', '" + address + "', '" + age + "', '" + nic + "', " + branchId + ")";
        
        ResultSet rs = MySQL.execute(query);
        if (rs != null) {
            // Get the generated ID
            String getIdQuery = "SELECT id FROM patient WHERE nic = '" + nic + "'";
            ResultSet idRs = MySQL.execute(getIdQuery);
            if (idRs != null && idRs.next()) {
                return idRs.getInt("id");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}

// Clear form method
private void clearForm() {
    jTextField1.setText("");
    jTextField6.setText("");
    jTextField3.setText("");
    jTextField4.setText("");
    jTextField5.setText("");
    jTextField7.setText("");
    jTextField8.setText("");
    jDateChooser1.setDate(null);
    jComboBox1.setSelectedIndex(0);
    jTextField1.requestFocus();
}
    


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel9 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jTextField6 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("New Appoinment");
        jPanel9.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 28, 157, 32));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Full Name");
        jPanel9.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 95, 28));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel9.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 614, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Details");
        jPanel9.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 95, 28));

        jTextField3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 614, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Nic Number");
        jPanel9.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 90, 28));

        jTextField4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        jPanel9.add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 220, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Mobile");
        jPanel9.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 73, 28));

        jTextField5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 200, 233, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Date");
        jPanel9.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 88, 28));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Doctor");
        jPanel9.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, 73, 28));

        jTextField7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 240, 233, -1));

        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Add");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel9.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 340, 105, -1));

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back");
        jButton3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255), 2));
        jPanel9.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 340, 110, -1));

        jDateChooser1.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooser1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, 220, 28));

        jTextField6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 614, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Address");
        jPanel9.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 95, 28));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Age");
        jPanel9.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 73, 28));

        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel9.add(jTextField8, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 220, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Time");
        jPanel9.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 240, 73, 28));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jPanel9.add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 280, 230, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
      
     try {
    // Get other field values
    String name = jTextField1.getText().trim();
    String address = jTextField6.getText().trim();
    String details = jTextField3.getText().trim();
    String nic = jTextField4.getText().trim();
    String mobile = jTextField5.getText().trim();
    String time = jTextField7.getText().trim();
    String age = jTextField8.getText().trim();
    String doctor = jComboBox1.getSelectedItem().toString();
    Date scheduledDate = jDateChooser1.getDate();

    // Validation
    if (name.isEmpty() || mobile.isEmpty() || address.isEmpty() || nic.isEmpty() || 
        time.isEmpty() || age.isEmpty() || "SELECT DOCTOR".equals(doctor) || scheduledDate == null) {
        JOptionPane.showMessageDialog(this, "Please fill all required fields", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validate mobile number
    if (!mobile.matches("\\d{10}")) {
        JOptionPane.showMessageDialog(this, "Please enter a valid 10-digit mobile number", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validate NIC format
    if (!nic.matches("\\d{9}[Vv]?|\\d{12}")) {
        JOptionPane.showMessageDialog(this, "Please enter a valid NIC number (9 digits with V or 12 digits)", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validate age
    try {
        int ageValue = Integer.parseInt(age);
        if (ageValue <= 0 || ageValue > 120) {
            JOptionPane.showMessageDialog(this, "Please enter a valid age (1-120)", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Please enter a valid age number", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get doctor ID from name
    Integer doctorId = getDoctorIdFromName(doctor);
    if (doctorId == null) {
        JOptionPane.showMessageDialog(this, "Error: Could not find selected doctor", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Get current user's branch ID (make sure it's valid)
    Integer branchId = getCurrentUserBranchId();
    if (branchId == null) {
        JOptionPane.showMessageDialog(this, "Error: Could not determine your branch. Please contact administrator.", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Check if patient already exists by NIC
    Integer patientId = getPatientIdByNIC(nic);
    
    if (patientId == null) {
        // Create new patient with proper branch_id
//        patientId = createNewPatient(name, mobile, address, age, nic, branchId);
//        if (patientId == null) {
//            JOptionPane.showMessageDialog(this, "Error: Could not create patient record", 
//                "Error", JOptionPane.ERROR_MESSAGE);
//            return;
//        }
    }

    // Create appointment in database
    boolean appointmentCreated = createAppointmentInDB(details, scheduledDate, time, doctorId, patientId);

    if (appointmentCreated) {
        JOptionPane.showMessageDialog(this, 
            "Appointment created successfully!\n" +
            "Patient: " + name + "\n" +
            "Date: " + new SimpleDateFormat("yyyy-MM-dd").format(scheduledDate) + " at " + time + "\n" +
            "Doctor: " + doctor, 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    } else {
        JOptionPane.showMessageDialog(this, "Failed to create appointment. Please try again.", 
            "Error", JOptionPane.ERROR_MESSAGE);
    }

} catch (Exception e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), 
        "Error", JOptionPane.ERROR_MESSAGE);
}
    
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        FlatDarculaLaf.setup();

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                NewAppoinment dialog = new NewAppoinment(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    // End of variables declaration//GEN-END:variables
}
