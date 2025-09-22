package chain;

import model.Bill;

public abstract class BillHandler {
    protected BillHandler nextHandler;

    public void setNextHandler(BillHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract void processBill(Bill bill);

    protected void passToNextHandler(Bill bill) {
        if (nextHandler != null) {
            nextHandler.processBill(bill);
        }
    }
}