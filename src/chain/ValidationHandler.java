package chain;

import model.Bill;

import javax.swing.*;

public class ValidationHandler extends BillHandler {
    @Override
    public void processBill(Bill bill) {
        System.out.println("Validating bill: " + bill.getBillId());

        // Validate bill amount
        if (bill.getAmount() <= 0) {
            JOptionPane.showMessageDialog(null, "Error: Bill amount must be greater than 0", "Validation Error", JOptionPane.ERROR_MESSAGE);
            bill.setStatus("REJECTED");
            return;
        }

        // Validate patient ID
        if (bill.getPatientId() <= 0) {
            JOptionPane.showMessageDialog(null, "Error: Invalid Patient ID", "Validation Error", JOptionPane.ERROR_MESSAGE);
            bill.setStatus("REJECTED");
            return;
        }

        // Validate service type
        if (bill.getServiceType() == null || bill.getServiceType().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Error: Service type is required", "Validation Error", JOptionPane.ERROR_MESSAGE);
            bill.setStatus("REJECTED");
            return;
        }

        System.out.println("Bill validation passed");
        bill.setStatus("VALIDATED");
        passToNextHandler(bill);
    }
}