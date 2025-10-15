package facade;
import facade.services.NotificationService;
import facade.services.ReceiptService;
import shop.*;
import payment.Payment;
import payment.decorator.CashbackDecorator;
import payment.decorator.DiscountDecorator;
import payment.decorator.FraudDetectionDecorator;
import java.math.BigDecimal;

public class CheckoutFacade {
    private final ReceiptService receipt = new ReceiptService();
    private final NotificationService notify = new NotificationService();

    public static final class Options{
        public BigDecimal discount;
        public boolean fraud;
        public boolean cashback;
        public int cashbackUnit=50;
    }
    public PaymentResult processOrder(Cart cart, Customer customer, Payment base, Options options){
        Order order = new Order(customer, cart.subTotal());

        Payment decoratedPayment = base;
        if(options != null) {
            if (options.discount != null) decoratedPayment = new DiscountDecorator(decoratedPayment, options.discount);
            if (options.cashback) decoratedPayment = new CashbackDecorator(decoratedPayment,options.cashbackUnit);
            if (options.fraud) decoratedPayment = new FraudDetectionDecorator(decoratedPayment);
        }
        PaymentResult result = decoratedPayment.pay(order,order.amount());

        String receiptText = receipt.buildReceipt(order, result);
        notify.sendEmail(customer, "Your order: " + order.id(), receiptText);
        return result;
    }
}
