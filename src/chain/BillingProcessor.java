package chain;

import model.Bill;

public class BillingProcessor {
    private BillHandler chain;

    public BillingProcessor() {
        buildChain();
    }

    private void buildChain() {
        // Create handlers
        BillHandler validationHandler = new ValidationHandler();
        BillHandler insuranceHandler = new InsuranceHandler();
        BillHandler paymentHandler = new PaymentHandler();
        BillHandler notificationHandler = new NotificationHandler();

        // Build the chain: Validation -> Insurance -> Payment -> Notification
        validationHandler.setNextHandler(insuranceHandler);
        insuranceHandler.setNextHandler(paymentHandler);
        paymentHandler.setNextHandler(notificationHandler);

        this.chain = validationHandler;
    }

    public void processBill(Bill bill) {
        if (chain != null) {
            chain.processBill(bill);
        } else {
            System.err.println("Billing chain not initialized");
        }
    }

    // Method to get current chain for testing or modification
    public BillHandler getChain() {
        return chain;
    }
}
