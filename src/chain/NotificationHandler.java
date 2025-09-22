package chain;

import model.Bill;

import javax.swing.*;

public class NotificationHandler extends BillHandler {
    @Override
    public void processBill(Bill bill) {
        System.out.println("Sending notifications for bill: " + bill.getBillId());

        // Send appropriate notification based on bill status
        switch (bill.getStatus()) {
            case "PAID":
                JOptionPane.showMessageDialog(null, "Notification: Bill #" + bill.getBillId() + " has been paid successfully.",
                        "Notification Sent", JOptionPane.INFORMATION_MESSAGE);
                break;

            case "INSURANCE_APPROVED":
                JOptionPane.showMessageDialog(null, "Notification: Insurance claim for Bill #" + bill.getBillId() + " was approved.",
                        "Notification Sent", JOptionPane.INFORMATION_MESSAGE);
                break;

            case "INSURANCE_REJECTED":
                JOptionPane.showMessageDialog(null, "Notification: Insurance claim for Bill #" + bill.getBillId() + " was rejected. Patient must pay.",
                        "Notification Sent", JOptionPane.WARNING_MESSAGE);
                break;

            case "PAYMENT_FAILED":
                JOptionPane.showMessageDialog(null, "Notification: Payment for Bill #" + bill.getBillId() + " failed. Please contact patient.",
                        "Notification Sent", JOptionPane.ERROR_MESSAGE);
                break;

            case "REJECTED":
                JOptionPane.showMessageDialog(null, "Notification: Bill #" + bill.getBillId() + " was rejected during validation.",
                        "Notification Sent", JOptionPane.ERROR_MESSAGE);
                break;
        }

        // Always pass to next handler (even if it's the end of the chain)
        passToNextHandler(bill);
    }
}
