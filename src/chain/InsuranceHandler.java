package chain;

import model.Bill;

import javax.swing.*;
import java.util.Random;

public class InsuranceHandler extends BillHandler {
    @Override
    public void processBill(Bill bill) {
        System.out.println("Processing insurance for bill: " + bill.getBillId());

        // Check if insurance is involved
        if (bill.getInsuranceProvider() != null && !bill.getInsuranceProvider().isEmpty()) {
            System.out.println("Submitting claim to insurance: " + bill.getInsuranceProvider());

            // Simulate insurance claim processing
            boolean claimApproved = simulateInsuranceClaim(bill);

            if (claimApproved) {
                bill.setStatus("INSURANCE_APPROVED");
                bill.setInsuranceClaimId(generateClaimId());
                JOptionPane.showMessageDialog(null, "Insurance claim approved! Claim ID: " + bill.getInsuranceClaimId(),
                        "Insurance Processing", JOptionPane.INFORMATION_MESSAGE);
            } else {
                bill.setStatus("INSURANCE_REJECTED");
                JOptionPane.showMessageDialog(null, "Insurance claim rejected. Patient must pay directly.",
                        "Insurance Processing", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            System.out.println("No insurance involved, proceeding to payment");
            bill.setStatus("READY_FOR_PAYMENT");
        }

        passToNextHandler(bill);
    }

    private boolean simulateInsuranceClaim(Bill bill) {
        // Simulate insurance approval (80% chance of approval)
        Random random = new Random();
        return random.nextDouble() < 0.8;
    }

    private String generateClaimId() {
        return "CLM-" + System.currentTimeMillis();
    }
}
