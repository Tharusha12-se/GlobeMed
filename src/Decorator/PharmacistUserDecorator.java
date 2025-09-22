/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Decorator;

import model.User;

/**
 *
 * @author User
 */


public class PharmacistUserDecorator extends UserDecorator {
    private static final String[] PHARMACIST_PERMISSIONS = {
        "view_patient_prescriptions", "dispense_medication", "manage_inventory",
        "check_drug_interactions"
    };
    
    public PharmacistUserDecorator(User user) {
        super(user);
    }
    
    @Override
    public boolean hasPermission(String permission) {
        for (String pharmacistPermission : PHARMACIST_PERMISSIONS) {
            if (pharmacistPermission.equals(permission)) {
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