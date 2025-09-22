package chain;

import model.Bill;

import javax.swing.*;
import java.util.Random;

public class PaymentHandler extends BillHandler {
    @Override
    public void processBill(Bill bill) {
        System.out.println("Processing payment for bill: " + bill.getBillId());

        // Check if insurance already covered
        if ("INSURANCE_APPROVED".equals(bill.getStatus())) {
            System.out.println("Payment covered by insurance");
            bill.setStatus("PAID");
            JOptionPane.showMessageDialog(null, "Payment completed by insurance", "Payment Processing", JOptionPane.INFORMATION_MESSAGE);
        }
        // Check if ready for patient payment
        else if ("READY_FOR_PAYMENT".equals(bill.getStatus()) || "INSURANCE_REJECTED".equals(bill.getStatus())) {
            System.out.println("Processing patient payment");

            // Simulate payment processing
            boolean paymentSuccess = simulatePaymentProcessing(bill);

            if (paymentSuccess) {
                bill.setStatus("PAID");
                JOptionPane.showMessageDialog(null, "Payment processed successfully!", "Payment Processing", JOptionPane.INFORMATION_MESSAGE);
            } else {
                bill.setStatus("PAYMENT_FAILED");
                JOptionPane.showMessageDialog(null, "Payment failed. Please try again or contact support.",
                        "Payment Processing", JOptionPane.ERROR_MESSAGE);
            }
        }

        passToNextHandler(bill);
    }

    private boolean simulatePaymentProcessing(Bill bill) {
        // Simulate payment processing (90% success rate)
        Random random = new Random();
        return random.nextDouble() < 0.9;
    }
}
