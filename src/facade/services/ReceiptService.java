package facade.services;
import shop.Order;
import shop.PaymentResult;

public class ReceiptService {
    public String buildReceipt(Order order, PaymentResult result) {
        return "Order " + order.id() + "\n"+
                "Amount:" + order.amount() + "\n"+
                "Status:" + (result.success()? "PAID":"FAILED ") + "\n" +
                "Message: " + result.message();
    }
}
