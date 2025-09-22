
package Decorator;

import model.User;


public class AdminUserDecorator extends UserDecorator {
    private static final String[] ADMIN_PERMISSIONS = {
        "view_patient_records", "edit_patient_records", "delete_patient_records",
        "schedule_appointments", "cancel_any_appointment", "manage_users",
        "generate_reports", "view_financial_data", "manage_system_settings"
    };
    
    public AdminUserDecorator(User user) {
        super(user);
    }
    
    @Override
    public boolean hasPermission(String permission) {
        for (String adminPermission : ADMIN_PERMISSIONS) {
            if (adminPermission.equals(permission)) {
                return true;
            }
        }
        return super.hasPermission(permission);
    }

    @Override
    public String getEmail() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
 
}